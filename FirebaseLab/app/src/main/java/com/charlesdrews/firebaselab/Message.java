package com.charlesdrews.firebaselab;

/**
 * Created by charlie on 3/23/16.
 */
public class Message {
    private String username, messageText;

    public Message(){}

    public Message(String username, String messageText) {
        this.username = username;
        this.messageText = messageText;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}
