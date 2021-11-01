package com.example.catalog.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Books {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String author;
    private Long usedBy;
    private Long takenTime;
    private Long endTime;

    public Books() {
    }

    public Books(String name, String author) {
        this.name = name;
        this.author = author;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getUsedBy() {
        return usedBy;
    }

    public void setUsedBy(Long usedBy) {
        this.usedBy = usedBy;
    }

    public Long getTakenTime() {
        return takenTime;
    }

    public void setTakenTime(Long takenTime) {
        this.takenTime = takenTime;
    }
}
