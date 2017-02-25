package com.nexuslink.model.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 猿人 on 2017/2/8.
 */

public class CommunityInfo implements Parcelable {


    private int code;
    private List<ArticlesBean> articles;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ArticlesBean> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticlesBean> articles) {
        this.articles = articles;
    }

    public static class ArticlesBean implements Parcelable {

        /**
         * articleId : 13
         * userId : 15
         * userBean : {"uid":15,"uName":"张兴锐","uGender":"M","uImg":"user15.jpg","uHeight":0,"uWeight":0,"uFansnum":0,"uExp":0,"uHistoryStep":0,"uHistoryMileage":0,"uAchievements":null}
         * text : 继续
         * images : []
         * commentNum : 3
         * likeNum : 1
         * lookNum : 0
         * date : 2017-02-20
         * time : 13:17:35
         * likeArticle : true
         */

        private int articleId;
        private int userId;
        private UserBeanBean userBean;
        private String text;
        private int commentNum;
        private int likeNum;
        private int lookNum;
        private String date;
        private String time;
        private boolean likeArticle;
        private List<String> images;

        public int getArticleId() {
            return articleId;
        }

        public void setArticleId(int articleId) {
            this.articleId = articleId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public UserBeanBean getUserBean() {
            return userBean;
        }

        public void setUserBean(UserBeanBean userBean) {
            this.userBean = userBean;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
        }

        public int getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(int likeNum) {
            this.likeNum = likeNum;
        }

        public int getLookNum() {
            return lookNum;
        }

        public void setLookNum(int lookNum) {
            this.lookNum = lookNum;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public boolean isLikeArticle() {
            return likeArticle;
        }

        public void setLikeArticle(boolean likeArticle) {
            this.likeArticle = likeArticle;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public static class UserBeanBean implements Parcelable {


            private int uid;
            private String uName;
            private String uGender;
            private String uImg;
            private int uHeight;
            private int uWeight;
            private int uFansnum;
            private int uExp;
            private int uHistoryStep;
            private int uHistoryMileage;
            private Boolean[] uAchievements;

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
            }

            public String getUName() {
                return uName;
            }

            public void setUName(String uName) {
                this.uName = uName;
            }

            public String getUGender() {
                return uGender;
            }

            public void setUGender(String uGender) {
                this.uGender = uGender;
            }

            public String getUImg() {
                return uImg;
            }

            public void setUImg(String uImg) {
                this.uImg = uImg;
            }

            public int getUHeight() {
                return uHeight;
            }

            public void setUHeight(int uHeight) {
                this.uHeight = uHeight;
            }

            public int getUWeight() {
                return uWeight;
            }

            public void setUWeight(int uWeight) {
                this.uWeight = uWeight;
            }

            public int getUFansnum() {
                return uFansnum;
            }

            public void setUFansnum(int uFansnum) {
                this.uFansnum = uFansnum;
            }

            public int getUExp() {
                return uExp;
            }

            public void setUExp(int uExp) {
                this.uExp = uExp;
            }

            public int getUHistoryStep() {
                return uHistoryStep;
            }

            public void setUHistoryStep(int uHistoryStep) {
                this.uHistoryStep = uHistoryStep;
            }

            public int getUHistoryMileage() {
                return uHistoryMileage;
            }

            public void setUHistoryMileage(int uHistoryMileage) {
                this.uHistoryMileage = uHistoryMileage;
            }

            public Boolean[] getUAchievements() {
                return uAchievements;
            }

            public void setUAchievements(Boolean[] uAchievements) {
                this.uAchievements = uAchievements;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.uid);
                dest.writeString(this.uName);
                dest.writeString(this.uGender);
                dest.writeString(this.uImg);
                dest.writeInt(this.uHeight);
                dest.writeInt(this.uWeight);
                dest.writeInt(this.uFansnum);
                dest.writeInt(this.uExp);
                dest.writeInt(this.uHistoryStep);
                dest.writeInt(this.uHistoryMileage);
                dest.writeArray(this.uAchievements);
            }

            public UserBeanBean() {
            }

            protected UserBeanBean(Parcel in) {
                this.uid = in.readInt();
                this.uName = in.readString();
                this.uGender = in.readString();
                this.uImg = in.readString();
                this.uHeight = in.readInt();
                this.uWeight = in.readInt();
                this.uFansnum = in.readInt();
                this.uExp = in.readInt();
                this.uHistoryStep = in.readInt();
                this.uHistoryMileage = in.readInt();
                this.uAchievements = (Boolean[]) in.readArray(Boolean[].class.getClassLoader());
            }

            public static final Creator<UserBeanBean> CREATOR = new Creator<UserBeanBean>() {
                @Override
                public UserBeanBean createFromParcel(Parcel source) {
                    return new UserBeanBean(source);
                }

                @Override
                public UserBeanBean[] newArray(int size) {
                    return new UserBeanBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.articleId);
            dest.writeInt(this.userId);
            dest.writeParcelable(this.userBean, flags);
            dest.writeString(this.text);
            dest.writeInt(this.commentNum);
            dest.writeInt(this.likeNum);
            dest.writeInt(this.lookNum);
            dest.writeString(this.date);
            dest.writeString(this.time);
            dest.writeByte(this.likeArticle ? (byte) 1 : (byte) 0);
            dest.writeStringList(this.images);
        }

        public ArticlesBean() {
        }

        protected ArticlesBean(Parcel in) {
            this.articleId = in.readInt();
            this.userId = in.readInt();
            this.userBean = in.readParcelable(UserBeanBean.class.getClassLoader());
            this.text = in.readString();
            this.commentNum = in.readInt();
            this.likeNum = in.readInt();
            this.lookNum = in.readInt();
            this.date = in.readString();
            this.time = in.readString();
            this.likeArticle = in.readByte() != 0;
            this.images = in.createStringArrayList();
        }

        public static final Creator<ArticlesBean> CREATOR = new Creator<ArticlesBean>() {
            @Override
            public ArticlesBean createFromParcel(Parcel source) {
                return new ArticlesBean(source);
            }

            @Override
            public ArticlesBean[] newArray(int size) {
                return new ArticlesBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeList(this.articles);
    }

    public CommunityInfo() {
    }

    protected CommunityInfo(Parcel in) {
        this.code = in.readInt();
        this.articles = new ArrayList<ArticlesBean>();
        in.readList(this.articles, ArticlesBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<CommunityInfo> CREATOR = new Parcelable.Creator<CommunityInfo>() {
        @Override
        public CommunityInfo createFromParcel(Parcel source) {
            return new CommunityInfo(source);
        }

        @Override
        public CommunityInfo[] newArray(int size) {
            return new CommunityInfo[size];
        }
    };
}
