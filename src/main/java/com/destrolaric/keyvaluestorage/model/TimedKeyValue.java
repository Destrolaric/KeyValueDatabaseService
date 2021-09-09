package com.destrolaric.keyvaluestorage.model;

import javax.annotation.Generated;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table
public class TimedKeyValue implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "key")
    private String key;

    @Column(name = "content")
    private String content;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private Date date;

    @PrePersist
    protected void onCreate() {
        date = new Date();
    }

    @Column(name = "ttl")
    private int ttl;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }
}
