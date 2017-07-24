package pl.wiewiogr.msgService;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collection;

@Document
public class Conversation {

    @Id
    @GeneratedValue
    private String id;

    private Collection<User> participants;

    private Collection<Message> messages;

    Conversation(){

    }

    public Collection<User> getParticipants() {
        return participants;
    }

    public void setParticipants(Collection<User> participants) {
        this.participants = participants;
    }

    public Collection<Message> getMessages() {
        return messages;
    }

    public void setMessages(Collection<Message> messages) {
        this.messages = messages;
    }

    public String getId() {
        return id;
    }
}
