package com.scank.organizer.model;

/**
 * Created on 12.11.2020.
 */

public class ResponseWalkthroughModel extends StaticModel {
    java.util.List<ItemModelWalktrough> List;
    java.util.List<String> ExplorePhoto;
    String About;

    public java.util.List<String> getExplorePhoto() {
        return ExplorePhoto;
    }

    public void setExplorePhoto(java.util.List<String> explorePhoto) {
        ExplorePhoto = explorePhoto;
    }

    public String getAbout() {
        return About;
    }

    public void setAbout(String about) {
        About = about;
    }

    public java.util.List<ItemModelWalktrough> getList() {
        return List;
    }

    public void setList(java.util.List<ItemModelWalktrough> list) {
        List = list;
    }
}
