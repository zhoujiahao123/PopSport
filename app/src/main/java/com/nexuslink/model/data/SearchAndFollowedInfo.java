package com.nexuslink.model.data;

/**
 * Created by ASUS-NB on 2017/2/23.
 */

public class SearchAndFollowedInfo {
    FollowedInfo followedInfo;
    SearchInfo searchInfo;

    public FollowedInfo getFollowedInfo() {
        return followedInfo;
    }

    public void setFollowedInfo(FollowedInfo followedInfo) {
        this.followedInfo = followedInfo;
    }

    public SearchInfo getSearchInfo() {
        return searchInfo;
    }

    public void setSearchInfo(SearchInfo searchInfo) {
        this.searchInfo = searchInfo;
    }
}
