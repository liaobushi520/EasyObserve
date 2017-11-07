package com.liaobusi.runtime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liaozhongjun on 2017/11/7.
 */

public abstract class ObserveRepository {

    protected List<ObserveInfo> observeInfos = new ArrayList<>();

    protected void addObserveInfo(String observableId, String methodName, String paramTypeName) {
        ObserveInfo observeInfo = new ObserveInfo(observableId, methodName, paramTypeName);
        observeInfos.add(observeInfo);
    }

    public List<ObserveInfo> getObserveInfos() {
        return observeInfos;
    }
}
