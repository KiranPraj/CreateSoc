package com.icspl.createsoc.Model;

public class CreateMeetingModel {
    public String discription;
    public String date;

    public CreateMeetingModel(String discription, String date) {
        this.discription = discription;
        this.date = date;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
