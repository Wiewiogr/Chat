package pl.wiewiogr.msgService;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.junit.Test;
import org.testng.collections.Lists;

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

    @Before
    public void setUp(){
        User tom = new User();
        tom.setName("Tomasz");
        User aga = new User();
        aga.setName("Aga");
        userRepository.save(tom);
        userRepository.save(aga);
    }

    @After
    public void cleanUp(){
        userRepository.deleteAll();
    }

    @Test
    public void conversationShouldBeCreatedAndSaved(){
        Conversation conversation = new Conversation();
        List<User> participants = new ArrayList<>();
        participants.add(userRepository.findOne(1L));
        participants.add(userRepository.findOne(2L));
        conversation.setParticipants(participants);
        conversationRepository.save(conversation);
    }

}
