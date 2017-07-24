package pl.wiewiogr.msgService;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Collection;
import java.util.List;

@Document
public class Conversation {

    @Id
    @GeneratedValue
    private String id;

    private List<String> participantsNames;

    private List<Message> messages;

    Conversation(){

    }

    public List<String> getParticipantsNames() {
        return participantsNames;
    }

    public void setParticipantsNames(List<String> participantsNames) {
        this.participantsNames = participantsNames;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
