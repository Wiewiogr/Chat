package pl.wiewiogr.msgService;

import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = MsgServiceApplication.class)
@RunWith(SpringRunner.class)
@WebAppConfiguration

public class ConversationTest {
    @Autowired
    ConversationRepository conversationRepository;

    @Autowired
    UserRepository userRepository;

    String firstUserName = "Tomasz";
    String secondUserName = "Aga";
    String messageBody = "Message body";

    @Before
    public void setUp(){
        User tom = new User(firstUserName);
        User aga = new User(secondUserName);
        userRepository.save(tom);
        userRepository.save(aga);
    }

    @After
    public void cleanUp(){
        userRepository.deleteAll();
        conversationRepository.deleteAll();
    }

    @Test
    public void conversationShouldBeCreatedAndSaved(){
        List<String> participants = Lists.newArrayList(firstUserName, secondUserName);
        Conversation conversation = new Conversation();
        conversation.setParticipantsNames(participants);

        Message message = new Message(firstUserName, messageBody);
        conversation.setMessages(Lists.newArrayList(message));
        conversationRepository.save(conversation);

        Conversation conversationFromRepo = conversationRepository
                .findAll()
                .get(0);

        assertThat(conversationFromRepo.getParticipantsNames())
                .contains(firstUserName)
                .contains(secondUserName);

        Message messageFromRepo = conversationFromRepo.getMessages().get(0);
        assertThat(messageFromRepo.getBody())
                .isEqualTo(messageBody);
    }

}
