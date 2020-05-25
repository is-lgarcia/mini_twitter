package com.luisg.minitwitter.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Tweet {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("mensaje")
    @Expose
    private String mensaje;
    @SerializedName("likes")
    @Expose
    private List<Like> likes = new ArrayList<Like>();
    @SerializedName("user")
    @Expose
    private User user;

    /**
     * No args constructor for use in serialization
     */
    public Tweet() {
    }

    public Tweet(Tweet newTweet) {
        this.id = newTweet.getId();
        this.mensaje = newTweet.getMensaje();
        this.likes = newTweet.getLikes();
        this.user = newTweet.getUser();
    }

    /**
     * @param id
     * @param likes
     * @param mensaje
     * @param user
     */
    public Tweet(Integer id, String mensaje, List<Like> likes, User user) {
        super();
        this.id = id;
        this.mensaje = mensaje;
        this.likes = likes;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}

