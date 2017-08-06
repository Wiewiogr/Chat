package pl.wiewiogr.msgService;

import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = MsgServiceApplication.class)
@RunWith(SpringRunner.class)
@WebAppConfiguration

public class UserConversationControllerListMessageTest extends HttpTester {

    @Autowired
    ConversationRepository conversationRepository;

    @Autowired
    UserRepository userRepository;

    String firstUserName = "Tomasz";
    String secondUserName = "Aga";

    @Before
    public void setUp(){
        super.setUp();
        User firstUser = new User(firstUserName);
        User secondUser = new User(secondUserName);

        userRepository.save(firstUser);
        userRepository.save(secondUser);
    }

    @After
    public void cleanUp(){
        userRepository.deleteAll();
        conversationRepository.deleteAll();
    }

    @Test
    public void listAllUserConversations() throws Exception {
        User firstUser = userRepository.findByName(firstUserName);
        User secondUser = userRepository.findByName(firstUserName);

        List<String> participants = Lists.newArrayList(firstUserName, secondUserName);
        Conversation conversation = new Conversation();
        conversation.setParticipantsNames(participants);

        firstUser.setConversations(Lists.newArrayList(conversation));
        secondUser.setConversations(Lists.newArrayList(conversation));

        conversationRepository.save(conversation);
        userRepository.save(firstUser);
        userRepository.save(secondUser);

        mockMvc.perform(get("/" + firstUserName + "/conversation"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void listUserConversationsWhenHeHasNone() throws Exception {
        mockMvc.perform(get("/" + firstUserName + "/conversation"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void listConversationsOfUserThatDoesNotExists() throws Exception {
        String userNameThatDoesNotExists = "User_that_does_not_exists";
        mockMvc.perform(get("/" + userNameThatDoesNotExists + "/conversation"))
                .andExpect(status().isNotFound());
    }
}