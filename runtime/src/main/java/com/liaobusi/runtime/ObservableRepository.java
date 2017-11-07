package com.liaobusi.runtime;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liaozhongjun on 2017/11/7.
 */

public abstract class ObservableRepository {
    protected Map<String, ObservableInfo> observableInfoMap = new HashMap<>();

    protected void addObservableInfo(String id, String fieldName, String viewModelName) {
        ObservableInfo observableInfo = new ObservableInfo(id, fieldName, viewModelName);
        observableInfoMap.put(id, observableInfo);
    }

    public ObservableInfo getObservableInfo(String id) {
        return observableInfoMap.get(id);
    }

}
