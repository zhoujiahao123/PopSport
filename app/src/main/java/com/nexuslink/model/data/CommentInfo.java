package com.nexuslink.model.data;

/**
 * Created by 猿人 on 2017/2/12.
 */

public class CommentInfo {
    private int flag;
    private CommunityInfo.CommunityBean communityBean;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public CommunityInfo.CommunityBean getCommunityBean() {
        return communityBean;
    }

    public void setCommunityBean(CommunityInfo.CommunityBean communityBean) {
        this.communityBean = communityBean;
    }

    public static class CommentBean{
        private int aId;
        private int cId;

        public int getaId() {
            return aId;
        }

        public void setaId(int aId) {
            this.aId = aId;
        }

        public int getcId() {
            return cId;
        }

        public void setcId(int cId) {
            this.cId = cId;
        }
    }
}
