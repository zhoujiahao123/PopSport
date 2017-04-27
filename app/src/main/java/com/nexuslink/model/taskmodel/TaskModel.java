package com.nexuslink.model.taskmodel;

import com.nexuslink.model.BaseModel;

/**
 * Created by ASUS-NB on 2017/2/18.
 */

public interface TaskModel extends BaseModel {
    void upLoadTask(int uId, int type, int taskNum, com.nexuslink.model.taskmodel.OnCallBackListener listener);
}
