package com.nexuslink.model.data;

import java.util.List;

/**
 * Created by 猿人 on 2017/2/24.
 */

public class PublishImagesResult {

    private int code;
    private List<String> aImgs;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<String> getAImgs() {
        return aImgs;
    }

    public void setAImgs(List<String> aImgs) {
        this.aImgs = aImgs;
    }
}
