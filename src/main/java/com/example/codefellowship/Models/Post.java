package com.example.codefellowship.Models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String body;
    @CreationTimestamp
    private Date createdAt;

    @ManyToOne
    ApplicationUser applicationUser;

    public Post(String body,  ApplicationUser applicationUser) {
        this.body = body;
        this.createdAt = getCreatedAt();
        this.applicationUser = applicationUser;
    }

    public Post() {
    }

    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", applicationUser=" + applicationUser.getFirstName() +
                " " +applicationUser.getLastName() +
                '}';
    }
}
