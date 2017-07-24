package pl.wiewiogr.msgService;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Message {
    private User from;
    private User to;
    private String body;
}
