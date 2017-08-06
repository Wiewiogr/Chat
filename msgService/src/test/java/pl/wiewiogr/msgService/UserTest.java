package pl.wiewiogr.msgService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = MsgServiceApplication.class)
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    @After
    public void cleanUp(){
        userRepository.deleteAll();
    }

    @Test
    public void shouldFindUsers(){
        //given
        User tom = new User();
        tom.setName("Tomasz");
        User aga = new User();
        aga.setName("Aga");
        userRepository.save(tom);
        userRepository.save(aga);

        //when
        User firstUser = userRepository.findByName("Tomasz");
        Iterable<User> allUsers = userRepository.findAll();

        //then
        assertThat(firstUser.getName())
                .isEqualTo("Tomasz");

        assertThat(allUsers)
                .extracting("name")
                .contains("Tomasz")
                .contains("Aga");
    }

}