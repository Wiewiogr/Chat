package pl.wiewiogr.msgService;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Message {
    private String from;
    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
