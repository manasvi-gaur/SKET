package com.example.sket;

public class Users {
    String name,username,email,password;
    private String coverpage;
    private String userID;
    private String profilepicture;
    private  int followercount,followingCount;

    public int getPostcount() {
        return postcount;
    }

    public void setPostcount(int postcount) {
        this.postcount = postcount;
    }

    private int postcount;

    public Users(String profilepicture) {
        this.profilepicture = profilepicture;
    }

    public String getProfilepicture() {
        return profilepicture;
    }

    public void setProfilepicture(String profilepicture) {
        this.profilepicture = profilepicture;
    }



    public Users() {
    }

    public String getCoverpage() {
        return coverpage;
    }

    public void setCoverpage(String coverpage) {
        this.coverpage = coverpage;
    }





    public Users(String name, String username, String email, String password,String default_cover,String default_profile){
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.coverpage = default_cover;
        this.profilepicture = default_profile;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getFollowercount() {
        return followercount;
    }

    public void setFollowercount(int followercount) {
        this.followercount = followercount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }
}
