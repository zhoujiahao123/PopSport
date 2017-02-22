package com.nexuslink.model.data;

import java.util.List;

/**
 * Created by 猿人 on 2017/2/2.
 */

public class RunHouseInfo {

    private int flag;
    private List<RunHouseBean> runHouseBean;

    public RunHouseInfo(int flag, List<RunHouseBean> runHouseBean) {
        this.flag = flag;
        this.runHouseBean = runHouseBean;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public List<RunHouseBean> getRunHouseBean() {
        return runHouseBean;
    }

    public void setRunHouseBean(List<RunHouseBean> runHouseBean) {
        this.runHouseBean = runHouseBean;
    }

    public static class RunHouseBean{
        private String currentPersons;
        private String imageUrl;
        private String name;
        private String startTime;
        private int runType;
        private String runDetail;
        private long runHouseId;

        public String getCurrentPersons() {
            return currentPersons;
        }

        public void setCurrentPersons(String currentPersons) {
            this.currentPersons = currentPersons;
        }

        public String getRunDetail() {
            return runDetail;
        }

        public void setRunDetail(String runDetail) {
            this.runDetail = runDetail;
        }

        public long getRunHouseId() {
            return runHouseId;
        }

        public void setRunHouseId(int runHouseId) {
            this.runHouseId = runHouseId;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public int getRunType() {
            return runType;
        }

        public void setRunType(int runType) {
            this.runType = runType;
        }

        public String getRunDetial() {
            return runDetail;
        }

        public void setRunDetial(String runDetail) {
            this.runDetail = runDetail;
        }
    }


}
