package com.example.sket.model;

public class comment_model {
    private String commentbody;
    private  long commentedat;
    private String commentedby;

    public comment_model() {
    }

    public String getCommentbody() {
        return commentbody;
    }

    public void setCommentbody(String commentbody) {
        this.commentbody = commentbody;
    }

    public long getCommentedat() {
        return commentedat;
    }

    public void setCommentedat(long commentedat) {
        this.commentedat = commentedat;
    }

    public String getCommentedby() {
        return commentedby;
    }

    public void setCommentedby(String commentedby) {
        this.commentedby = commentedby;
    }
}
