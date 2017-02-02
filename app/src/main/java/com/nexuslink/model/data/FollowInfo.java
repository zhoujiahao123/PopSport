package com.nexuslink.model.data;

/**
 * Created by ASUS-NB on 2017/1/22.
 */

public class FollowInfo{
    private int flag;
    private FollowInfoBean followInfoBean;

    public FollowInfoBean getFollowInfoBean() {
        return followInfoBean;
    }

    public void setFollowInfoBean(FollowInfoBean followInfoBean) {
        this.followInfoBean = followInfoBean;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }



    public static class FollowInfoBean {
        private int followFlag;
        private int fFansNum;

        public int getFollowFlag() {
            return followFlag;
        }

        public void setFollowFlag(int followFlag) {
            this.followFlag = followFlag;
        }

        public int getfFansNum() {
            return fFansNum;
        }

        public void setfFansNum(int fFansNum) {
            this.fFansNum = fFansNum;
        }
    }
}


