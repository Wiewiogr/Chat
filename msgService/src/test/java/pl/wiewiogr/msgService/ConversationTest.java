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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @Before
    public void setUp(){
        User tom = new User();
        tom.setName(firstUserName);
        User aga = new User();
        aga.setName(secondUserName);
        userRepository.save(tom);
        userRepository.save(aga);
    }

    @After
    public void cleanUp(){
        userRepository.deleteAll();
    }

    @Test
    public void conversationShouldBeCreatedAndSaved(){
        User user1 = userRepository.findByName(firstUserName);
        User user2 = userRepository.findByName(secondUserName);
        List<User> participants = Lists.newArrayList(user1, user2);
        Conversation conversation = new Conversation();
        conversation.setParticipants(participants);
        Message message = new Message();
        message.setBody("Message body");
        message.setFrom(user1);
        message.setTo(user2);
        conversation.setMessages(Lists.newArrayList(message));
        conversationRepository.save(conversation);
    }

}
