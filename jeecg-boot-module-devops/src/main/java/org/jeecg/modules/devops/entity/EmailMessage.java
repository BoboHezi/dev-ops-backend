package org.jeecg.modules.devops.entity;

public class EmailMessage {
    private String title;
    private String[] receiver;
    private String content;

    public EmailMessage(String[] receiver, String title, String content) {
        this.title = title;
        this.receiver = receiver;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getReceiver() {
        return receiver;
    }

    public void setReceiver(String[] receiver) {
        this.receiver = receiver;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
