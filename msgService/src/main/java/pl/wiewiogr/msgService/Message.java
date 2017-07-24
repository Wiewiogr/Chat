package pl.wiewiogr.msgService;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Message {
    private User from;
    private User to;
    private String body;

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
