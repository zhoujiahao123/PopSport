package com.nexuslink.model.data;

/**
 * Created by ASUS-NB on 2017/2/9.
 */

public class User1 {


        private Long id;
        private Integer uid;
        private String uName;
        private String uGender;
        private String uImg;
        private Integer uHeight;
        private Integer uWeight;
        private Integer uFansnum;
        private Integer uExp;
        private Long uHistoryStep;
        private Long uHistoryMileage;
        private Long uBestRecordStep;
        private Long uBestRecordMileage;
        private int[] uAchievements;
        private Integer uFollowedNum;

        public User1() {
        }

        public User1(Long id) {
            this.id = id;
        }

        public User1(Long id, Integer uid, String uName, String uGender, String uImg, Integer uHeight, Integer uWeight, Integer uFansNum, Integer uExp, Long uHistoryStep, Long uHistoryMileage, Long uBestRecordStep, Long uBestRecordMileage, int[] uAchievements, Integer uFollowedNum) {
            this.id = id;

            this.uid = uid;
            this.uName = uName;
            this.uGender = uGender;
            this.uImg = uImg;
            this.uHeight = uHeight;
            this.uWeight = uWeight;
            this.uFansnum = uFansNum;
            this.uExp = uExp;
            this.uHistoryStep = uHistoryStep;
            this.uHistoryMileage = uHistoryMileage;
            this.uBestRecordStep = uBestRecordStep;
            this.uBestRecordMileage = uBestRecordMileage;
            this.uAchievements = uAchievements;
            this.uFollowedNum = uFollowedNum;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
        public Integer getUid() {
            return uid;
        }

        public void setUid(Integer uid) {
            this.uid = uid;}


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

        public Integer getUHeight() {
            return uHeight;
        }

        public void setUHeight(Integer uHeight) {
            this.uHeight = uHeight;
        }

        public Integer getUWeight() {
            return uWeight;
        }

        public void setUWeight(Integer uWeight) {
            this.uWeight = uWeight;
        }

        public Integer getUFansNum() {
            return uFansnum;
        }

        public void setUFansNum(Integer uFansNum) {
            this.uFansnum = uFansNum;
        }

        public Integer getUExp() {
            return uExp;
        }

        public void setUExp(Integer uExp) {
            this.uExp = uExp;
        }

        public Long getUHistoryStep() {
            return uHistoryStep;
        }

        public void setUHistoryStep(Long uHistoryStep) {
            this.uHistoryStep = uHistoryStep;
        }

        public Long getUHistoryMileage() {
            return uHistoryMileage;
        }

        public void setUHistoryMileage(Long uHistoryMileage) {
            this.uHistoryMileage = uHistoryMileage;
        }

        public Long getUBestRecordStep() {
            return uBestRecordStep;
        }

        public void setUBestRecordStep(Long uBestRecordStep) {
            this.uBestRecordStep = uBestRecordStep;
        }

        public Long getUBestRecordMileage() {
            return uBestRecordMileage;
        }

        public void setUBestRecordMileage(Long uBestRecordMileage) {
            this.uBestRecordMileage = uBestRecordMileage;
        }

        public int[] getUAchievements() {
            return uAchievements;
        }

        public void setUAchievements(int[] uAchievements) {
            this.uAchievements = uAchievements;
        }

        public Integer getUFollowedNum() {
            return uFollowedNum;
        }

        public void setUFollowedNum(Integer uFollowedNum) {
            this.uFollowedNum = uFollowedNum;
        }



}
