package pl.wiewiogr.msgService;

import com.google.common.collect.Lists;
import com.mongodb.util.JSON;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = MsgServiceApplication.class)
@RunWith(SpringRunner.class)
@WebAppConfiguration

public class UserConversationControllerTest extends HttpTester {

    @Autowired
    ConversationRepository conversationRepository;

    @Autowired
    UserRepository userRepository;


    String firstUserName = "Tomasz";
    String secondUserName = "Aga";
    String messageBody = "Message body";

    @Before
    public void setUp(){
        super.setUp();
        User tom = new User();
        tom.setName(firstUserName);
        User aga = new User();
        aga.setName(secondUserName);


        List<String> participants = Lists.newArrayList(firstUserName, secondUserName);
        Conversation conversation = new Conversation();
        conversation.setParticipantsNames(participants);

        tom.setConversations(Lists.newArrayList(conversation));
        aga.setConversations(Lists.newArrayList(conversation));
        Message message = new Message();
        message.setBody(messageBody);
        message.setFrom(firstUserName);
        conversation.setMessages(Lists.newArrayList(message));
        conversationRepository.save(conversation);

        userRepository.save(tom);
        userRepository.save(aga);

    }

    @After
    public void cleanUp(){
        userRepository.deleteAll();
        conversationRepository.deleteAll();
    }

    @Test
    public void listAllUserConversations() throws Exception {
        User user = userRepository.findByName(firstUserName);

        mockMvc.perform(get("/" + firstUserName + "/conversation"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType));
    }

    @Test
    public void sendMessage() throws Exception {
        User user = userRepository.findByName(firstUserName);
        String conversationId = user.getConversations().get(0).getId();
        String sentMessageBody = "New message";

        Message message = new Message();
        message.setBody(sentMessageBody);
        message.setFrom(firstUserName);


        mockMvc.perform(post("/" + firstUserName + "/conversation/" + conversationId)
        .content(this.json(message))
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}