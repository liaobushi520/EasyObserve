package com.liaobusi.easyobserve;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.liaobusi.runtime.ObservableInfo;
import com.liaobusi.runtime.ObservableRepository;
import com.liaobusi.runtime.ObserveInfo;
import com.liaobusi.runtime.ObserveRepository;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liaozhongjun on 2017/11/6.
 */

public class EasyObserve {


    private static ObservableRepository observableRepository;

    private static ObserveRepository observeRepository;

    public static void observe(final FragmentActivity activity) {
        try {
            if (observeRepository == null) {
                String observeRepositoryName = "com.liaobusi.ObserveRepositoryImp";
                observeRepository = (ObserveRepository) Class.forName(observeRepositoryName).newInstance();
            }
            if (observableRepository == null) {
                String observableRepositoryName = "com.liaobusi.ObservableRepositoryImp";
                observableRepository = (ObservableRepository) Class.forName(observableRepositoryName).newInstance();
            }
            for (ObserveInfo observeInfo : observeRepository.getObserveInfos()) {
                ObservableInfo observableInfo = observableRepository.getObservableInfo(observeInfo.getObservableId());
                observeInner(activity, observeInfo.getMethodName(), observeInfo.getMethodParamTypeName(), observableInfo.getFieldName(), observableInfo.getViewModelName());
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }


    private static void observeInner(final FragmentActivity activity, final String methodName, final String paramTypeClassName, String fieldName, String viewModelClassName) {
        try {
            Class viewModelClass = Class.forName(viewModelClassName);
            ViewModel viewModel = ViewModelProviders.of(activity).get(viewModelClass);
            Field field = viewModelClass.getDeclaredField(fieldName);
            LiveData liveData = (LiveData) field.get(viewModel);
            liveData.observe(activity, new Observer() {
                @Override
                public void onChanged(@Nullable Object o) {
                    try {
                        Class paramClass = Class.forName(paramTypeClassName);
                        Method method = activity.getClass().getDeclaredMethod(methodName, paramClass);
                        method.invoke(activity, o);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }


}
