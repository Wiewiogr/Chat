package pl.wiewiogr.msgService;

import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MsgServiceApplication.class)
@RunWith(SpringRunner.class)
@WebAppConfiguration

public class UserConversationControllerCreateConversationTest extends HttpTester {
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
    public void createConversation() throws Exception {
        int numberOfConversationsBefore = 0;

        Conversation conversation = new Conversation();
        conversation.setParticipantsNames(Lists.newArrayList(firstUserName, secondUserName));

        mockMvc.perform(post("/" + firstUserName + "/conversation/")
                .content(this.json(conversation))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        int numberOfConversationsAfter = userRepository.findByName(firstUserName).getConversations().size();

        assertThat(numberOfConversationsAfter)
                .isGreaterThan(numberOfConversationsBefore);
    }

}
