package pl.wiewiogr.msgService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        System.out.println("listUserConversation for : " + userName);
        Collection<Conversation> conversations = userRepository.findByName(userName).getConversations();
        return conversations;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{conversationId}" )
    ResponseEntity<?> sendMessage(@PathVariable String userName,
                                  @PathVariable String conversationId, @RequestBody Message inputMessage){
        System.out.println("sendMessage from : " + userName + " message body" + inputMessage.getBody());
        Conversation conversation = conversationRepository
                .findOne(conversationId);

        conversation.getMessages().add(inputMessage);
        conversationRepository.save(conversation);

        return new ResponseEntity<Object>(HttpStatus.OK);
    }

}
