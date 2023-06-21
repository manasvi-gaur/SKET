package com.example.sket.model;

public class chats_model {
    private  String chatprofile;
    private  String name;
    private String Uid;

    public chats_model(String chatprofile, String name,String Uid) {
        this.chatprofile = chatprofile;
        this.name = name;
        this.Uid = Uid;

    }

    public String getChatprofile() {
        return chatprofile;
    }

    public void setChatprofile(String chatprofile) {
        this.chatprofile = chatprofile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }
}
