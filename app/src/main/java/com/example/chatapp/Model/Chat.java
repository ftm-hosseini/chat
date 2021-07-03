package com.example.chatapp.Model;

public class Chat {
    private boolean isseen;
    private String message;
    private String receiver;
    private String sender;

    public Chat(String sender2, String receiver2, String message2, boolean isseen2) {
        sender = sender2;
        receiver = receiver2;
        message = message2;
        isseen = isseen2;
    }

    public Chat() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender2) {
        sender = sender2;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver2) {
        receiver = receiver2;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message2) {
        message = message2;
    }

    public boolean isIsseen() {
        return isseen;
    }

    public void setIsseen(boolean isseen2) {
        isseen = isseen2;
    }
}
