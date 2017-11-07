package com.liaobusi.compiler;

import com.liaobusi.runtime.Observable;
import com.liaobusi.runtime.ObservableRepository;
import com.liaobusi.runtime.Observe;
import com.liaobusi.runtime.ObserveRepository;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

import jdk.nashorn.internal.codegen.TypeMap;

@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes({"com.liaobusi.runtime.Observable", "com.liaobusi.runtime.Observe"})
public class EasyObserveProcessor extends AbstractProcessor {

    private Filer filer;
    private Messager messager;
    private Elements elementUtils;


    private Map<String, List<String>> observableSet = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elementUtils = processingEnvironment.getElementUtils();
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();
    }


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> observableElements = roundEnvironment.getElementsAnnotatedWith(Observable.class);
        if (observableElements != null && !observableElements.isEmpty()) {
            ClassName superClassName = ClassName.get(ObservableRepository.class);
            TypeSpec.Builder builder = TypeSpec.classBuilder("ObservableRepositoryImp").superclass(superClassName).addModifiers(Modifier.PUBLIC);
            MethodSpec.Builder methodBuilder = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC);
            for (Element element : observableElements) {
                processObservableAnnotation(methodBuilder, element);
            }
            builder.addMethod(methodBuilder.build());
            try {
                JavaFile.builder("com.liaobusi", builder.build()).build().writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Set<? extends Element> observeElements = roundEnvironment.getElementsAnnotatedWith(Observe.class);
        if (observeElements != null && !observeElements.isEmpty()) {
            ClassName superClassName = ClassName.get(ObserveRepository.class);
            TypeSpec.Builder builder = TypeSpec.classBuilder("ObserveRepositoryImp").superclass(superClassName).addModifiers(Modifier.PUBLIC);
            MethodSpec.Builder methodBuilder = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC);
            for (Element element : observeElements) {
                processObserveAnnotation(methodBuilder, element);
            }
            builder.addMethod(methodBuilder.build());
            try {
                JavaFile.builder("com.liaobusi", builder.build()).build().writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    private void processObservableAnnotation(MethodSpec.Builder builder, Element element) {
        TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
        String viewModelName = enclosingElement.getQualifiedName().toString();
        Observable observable = element.getAnnotation(Observable.class);
        builder.addStatement("addObservableInfo($S,$S,$S)", observable.value(), element.getSimpleName(), viewModelName);
    }

    private void processObserveAnnotation(MethodSpec.Builder builder, Element element) {
        ExecutableElement executableElement = (ExecutableElement) element;
        List<? extends VariableElement> variableElements = executableElement.getParameters();
        if (variableElements == null) {
            return;
        }
        if (variableElements.size() == 1) {
            Observe observable = element.getAnnotation(Observe.class);
            builder.addStatement("addObserveInfo($S,$S,$S)", observable.value(), element.getSimpleName(), variableElements.get(0).asType().toString());
        }


    }


    private void brewIdsJava(String packageName, Map<String, List<String>> set) {

        TypeSpec.Builder typeSpec = TypeSpec.classBuilder("Ids").addModifiers(Modifier.PUBLIC).addModifiers(Modifier.FINAL);
        Set<Map.Entry<String, List<String>>> entrySet = set.entrySet();
        Iterator<Map.Entry<String, List<String>>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, List<String>> entry = iterator.next();
            String viewModelName = entry.getKey();
            for (String field : entry.getValue()) {
                ClassName stringClass = ClassName.get(String.class);
                String[] spiltViewModelName = viewModelName.split("\\.");
                messager.printMessage(Diagnostic.Kind.WARNING, viewModelName + spiltViewModelName.length);
                String fieldName = spiltViewModelName[spiltViewModelName.length - 1].toUpperCase() + "_" + field.toUpperCase();
                FieldSpec fieldSpec = FieldSpec.builder(stringClass, fieldName, Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC).initializer("$S", viewModelName + "_" + field).build();
                typeSpec.addField(fieldSpec);
            }
        }
        try {
            JavaFile.builder(packageName, typeSpec.build()).build().writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
