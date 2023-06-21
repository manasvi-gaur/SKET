package com.example.sket.model;

public class followers_model {
    private  String followedby;
    private  long followedat;

    public followers_model() {
    }

    public String getFollowedby() {
        return followedby;
    }

    public void setFollowedby(String followedby) {
        this.followedby = followedby;
    }

    public long getFollowedat() {
        return followedat;
    }

    public void setFollowedat(long followedat) {
        this.followedat = followedat;
    }
}
