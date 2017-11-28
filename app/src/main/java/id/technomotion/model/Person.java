package id.technomotion.model;

import java.io.Serializable;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * Created by omayib on 18/09/17.
 */

public class Person implements Serializable{
    private  String id;
    private  String name;
    private  String email;
    private  String job;
    private  String avatarUrl;

    public Person()  {

    }

    public Person(String id, String name, String email, String job) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.job = job;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
