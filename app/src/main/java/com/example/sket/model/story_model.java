package com.example.sket.model;

public class story_model {
    int story_imageview,story_profile_view;
    String story_name;

    public story_model(int story_imageview, int story_profile_view, String story_name) {
        this.story_imageview = story_imageview;
        this.story_profile_view = story_profile_view;
        this.story_name = story_name;
    }

    public int getStory_imageview() {
        return story_imageview;
    }

    public void setStory_imageview(int story_imageview) {
        this.story_imageview = story_imageview;
    }

    public int getStory_profile_view() {
        return story_profile_view;
    }

    public void setStory_profile_view(int story_profile_view) {
        this.story_profile_view = story_profile_view;
    }

    public String getStory_name() {
        return story_name;
    }

    public void setStory_name(String story_name) {
        this.story_name = story_name;
    }
}
