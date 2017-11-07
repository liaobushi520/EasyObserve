package com.liaobusi.runtime;

/**
 * Created by liaozhongjun on 2017/11/7.
 */

public class ObserveInfo {

    private String observableId;

    private String methodName;

    private String methodParamTypeName;

    public ObserveInfo(String observableId, String methodName, String methodParamTypeName) {
        this.observableId = observableId;
        this.methodName = methodName;
        this.methodParamTypeName = methodParamTypeName;
    }

    public String getObservableId() {
        return observableId;
    }

    public void setObservableId(String observableId) {
        this.observableId = observableId;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodParamTypeName() {
        return methodParamTypeName;
    }

    public void setMethodParamTypeName(String methodParamTypeName) {
        this.methodParamTypeName = methodParamTypeName;
    }
}
