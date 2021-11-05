package com.example.penalties;

import java.util.Date;

public class BookWithPenalty {
    private Long id;
    private Date endTime;
    private Long penalty;
    private String name;
    private String author;

    public BookWithPenalty(Long id, Date endTime, Long penalty, String name, String author) {
        this.id = id;
        this.endTime = endTime;
        this.penalty = penalty;
        this.name = name;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getPenalty() {
        return penalty;
    }

    public void setPenalty(Long penalty) {
        this.penalty = penalty;
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
}
