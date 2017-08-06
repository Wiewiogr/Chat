package pl.wiewiogr.msgService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.testng.collections.Lists;

import java.util.Collection;
import java.util.List;

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
        User user = getUserOrThrow(userName);
        Collection<Conversation> conversations = user.getConversations();
        if(conversations != null){
            return conversations;
        } else {
            return Lists.newArrayList();
        }
    }

    private User getUserOrThrow(@PathVariable String userName) {
        User user = userRepository.findByName(userName);
        if(user == null) throw new UserNotFoundException();
        return user;
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> createConversation(@PathVariable String userName, @RequestBody Conversation conversation){
        conversationRepository.save(conversation);
        conversation
                .getParticipantsNames()
                .forEach(name -> {
                    System.out.println("create conversation for user : " + name);
                    User user = userRepository.findByName(name);
                    List<Conversation> userConversations = user.getConversations();
                    if(userConversations != null){
                        userConversations.add(conversation);
                    } else {
                        userConversations = Lists.newArrayList(conversation);
                    }
                    user.setConversations(userConversations);
                    userRepository.save(user);
                });

        conversationRepository.save(conversation);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{conversationId}" )
    ResponseEntity<?> sendMessage(@PathVariable String userName,
                                  @PathVariable String conversationId, @RequestBody Message inputMessage){
        User user = getUserOrThrow(userName);
        Conversation conversation = conversationRepository
                .findOne(conversationId);

        conversation.getMessages().add(inputMessage);
        conversationRepository.save(conversation);

        return new ResponseEntity<Object>(HttpStatus.OK);
    }

}
