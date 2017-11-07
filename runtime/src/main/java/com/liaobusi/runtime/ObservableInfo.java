package com.liaobusi.runtime;

/**
 * Created by liaozhongjun on 2017/11/7.
 */

public class ObservableInfo {

    private String id;

    private String fieldName;

    private String viewModelName;

    public ObservableInfo(String id, String fieldName, String viewModelName) {
        this.id = id;
        this.fieldName = fieldName;
        this.viewModelName = viewModelName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getViewModelName() {
        return viewModelName;
    }

    public void setViewModelName(String viewModelName) {
        this.viewModelName = viewModelName;
    }
}
