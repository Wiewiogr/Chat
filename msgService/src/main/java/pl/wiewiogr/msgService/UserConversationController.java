package pl.wiewiogr.msgService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.testng.collections.Lists;

import java.util.Collection;

@RestController
@RequestMapping("/{userName}/conversation")
public class UserConversationController {

    private final UserRepository userRepository;

    private final ConversationRepository conversationRepository;

    @Autowired
    public UserConversationController(UserRepository userRepository, ConversationRepository conversationRepository) {
        this.userRepository = userRepository;
        this.conversationRepository = conversationRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Conversation> listUserConversation(@PathVariable String userName){
        return Lists.newArrayList();
    }
}
