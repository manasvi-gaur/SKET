package com.example.sket.model;

public class post_model {
    private String postid;
    private String postimage;
    private String postedby;
    private  String postdescription;
    private long postedat;
    private  int postlike;
    private  int commentcount ;







    public post_model(String postid, String postimage, String postedby, String postdescription, long postedat) {
        this.postid = postid;
        this.postimage = postimage;
        this.postedby = postedby;
        this.postdescription = postdescription;
        this.postedat = postedat;
    }

    public post_model() {
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }

    public String getPostedby() {
        return postedby;
    }

    public void setPostedby(String postedby) {
        this.postedby = postedby;
    }

    public String getPostdescription() {
        return postdescription;
    }

    public void setPostdescription(String postdescription) {
        this.postdescription = postdescription;
    }

    public long getPostedat() {
        return postedat;
    }

    public void setPostedat(long postedat) {
        this.postedat = postedat;
    }

    public int getPostlike() {
        return postlike;
    }

    public void setPostlike(int postlike) {
        this.postlike = postlike;
    }
    public int getCommentcount() {
        return commentcount;
    }

    public void setCommentcount(int commentcount) {
        this.commentcount = commentcount;
    }
}
