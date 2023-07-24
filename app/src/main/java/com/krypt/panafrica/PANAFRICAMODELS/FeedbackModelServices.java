package com.krypt.panafrica.PANAFRICAMODELS;

public class FeedbackModelServices {
    String comment;
    String reply;
    String Id;

    public FeedbackModelServices(String comment, String reply, String id) {
        this.comment = comment;
        this.reply = reply;
        Id = id;
    }

    public String getComment() {
        return comment;
    }

    public String getReply() {
        return reply;
    }

    public String getId() {
        return Id;
    }
}
