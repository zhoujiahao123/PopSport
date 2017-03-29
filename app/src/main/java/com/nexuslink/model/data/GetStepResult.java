package com.nexuslink.model.data;

import java.util.List;

/**
 * Created by 猿人 on 2017/3/29.
 */

public class GetStepResult {

    /**
     * code : 200
     * record : [{"userId":0,"step":1937,"date":"2017-03-28"},{"userId":0,"step":4675,"date":"2017-03-27"},{"userId":0,"step":6446,"date":"2017-03-26"},{"userId":0,"step":5815,"date":"2017-03-25"},{"userId":0,"step":2975,"date":"2017-03-24"},{"userId":0,"step":6067,"date":"2017-03-23"},{"userId":0,"step":7185,"date":"2017-03-22"},{"userId":0,"step":8863,"date":"2017-03-21"},{"userId":0,"step":8076,"date":"2017-03-20"},{"userId":0,"step":2297,"date":"2017-03-19"},{"userId":0,"step":3,"date":"2017-03-15"},{"userId":0,"step":3,"date":"2017-03-15"},{"userId":0,"step":3,"date":"2017-03-15"},{"userId":0,"step":3,"date":"2017-03-15"},{"userId":0,"step":3,"date":"2017-03-15"},{"userId":0,"step":3,"date":"2017-03-15"},{"userId":0,"step":10,"date":"2017-03-15"},{"userId":0,"step":3,"date":"2017-03-15"},{"userId":0,"step":3,"date":"2017-03-15"},{"userId":0,"step":3,"date":"2017-03-15"},{"userId":0,"step":3,"date":"2017-03-15"},{"userId":0,"step":3,"date":"2017-03-15"},{"userId":0,"step":3,"date":"2017-03-15"},{"userId":0,"step":3,"date":"2017-03-15"},{"userId":0,"step":3,"date":"2017-03-15"},{"userId":0,"step":3,"date":"2017-03-15"},{"userId":0,"step":3,"date":"2017-03-15"},{"userId":0,"step":3,"date":"2017-03-15"},{"userId":0,"step":3,"date":"2017-03-15"},{"userId":0,"step":1000,"date":"2017-03-14"},{"userId":0,"step":1000,"date":"2017-03-14"},{"userId":0,"step":1000,"date":"2017-03-14"}]
     */

    private int code;
    private List<RecordBean> record;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<RecordBean> getRecord() {
        return record;
    }

    public void setRecord(List<RecordBean> record) {
        this.record = record;
    }

    public static class RecordBean {
        /**
         * userId : 0
         * step : 1937
         * date : 2017-03-28
         */

        private int userId;
        private int step;
        private String date;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getStep() {
            return step;
        }

        public void setStep(int step) {
            this.step = step;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
