package name.saifmahmud.demo;

import name.saifmahmud.demo.services.EmailService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmailService emailService;

    @Test
    void givenUsers_whenGetUsers_thenCount6() throws Exception {
        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(6)));
    }

    @Test
    void givenUsersBooks_whenIssuedToUser_thenStatus204SendEmail() throws Exception {
        mvc.perform(put("/books/7/users/1"))
                        .andExpect(status().isNoContent());

        Mockito.verify(emailService, Mockito.times(1)).sendSimpleMessage(
                "saif@example.com",
                "Book issued to you",
                "We have successfully issued Harry Potter and the Philosopher's Stone to you."
        );
    }

    @Test
    void givenUsersBooks_whenIssuedToUserMoreThan5_thenStatus403() throws Exception {
        mvc.perform(put("/books/8/users/2"))
                .andExpect(status().isNoContent());
        mvc.perform(put("/books/9/users/2"))
                .andExpect(status().isNoContent());
        mvc.perform(put("/books/10/users/2"))
                .andExpect(status().isNoContent());
        mvc.perform(put("/books/11/users/2"))
                .andExpect(status().isNoContent());
        mvc.perform(put("/books/12/users/2"))
                .andExpect(status().isNoContent());
        mvc.perform(put("/books/13/users/2"))
                .andExpect(status().isForbidden());
    }

}
