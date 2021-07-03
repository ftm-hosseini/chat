package com.example.chatapp.Model;

public class User {

    private String id;
    private String imageURL;
    private String search;
    private String status;
    private String username;

    public User() {
    }

    public User(String id, String imageURL2, String status2, String username2, String search2) {
        this.id = id;
        this.username = username2;
        this.imageURL = imageURL2;
        this.status = status2;
        this.search = search2;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username2) {
        this.username = username2;
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public void setImageURL(String imageURL2) {
        this.imageURL = imageURL2;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status2) {
        this.status = status2;
    }

    public String getSearch() {
        return this.search;
    }

    public void setSearch(String search2) {
        this.search = search2;
    }
}
