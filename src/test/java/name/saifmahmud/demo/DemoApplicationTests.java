package name.saifmahmud.demo;

import name.saifmahmud.demo.services.EmailService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmailService emailService;

    @Test
    void givenUsers_whenGetUsers_thenCount6() throws Exception {
        mvc.perform(get("/users").header("Request-Id", "0503d6c1-fabd-43ca-838e-4d692dc6939b"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(6)));
    }

    @Test
    void givenUsersBooks_whenIssuedToUser_thenStatus204SendEmail() throws Exception {
        mvc.perform(put("/books/7/users/1").header("Request-Id", "84b36f6d-6611-4936-8efc-7cc583b6bf34"))
                        .andExpect(status().isNoContent());

        Mockito.verify(emailService, Mockito.times(1)).sendSimpleMessage(
                "saif@example.com",
                "Book issued to you",
                "We have successfully issued Harry Potter and the Philosopher's Stone to you."
        );
    }

    @Test
    void givenUsersBooks_whenIssuedToUserMoreThan5_thenStatus403() throws Exception {
        mvc.perform(put("/books/8/users/2").header("Request-Id", "0a08254d-568c-4f70-b8d4-cd8fed467d66"))
                .andExpect(status().isNoContent());
        mvc.perform(put("/books/9/users/2").header("Request-Id", "aa9be421-7585-4db0-ba4b-4da3f4a8b5a4"))
                .andExpect(status().isNoContent());
        mvc.perform(put("/books/10/users/2").header("Request-Id", "d1219a76-9ec6-4b0b-b687-a5bd7adff5bc"))
                .andExpect(status().isNoContent());
        mvc.perform(put("/books/11/users/2").header("Request-Id", "240e0f1f-c0d6-4bbe-802a-15fd73f7ad10"))
                .andExpect(status().isNoContent());
        mvc.perform(put("/books/12/users/2").header("Request-Id", "dc1b2d64-a09e-4824-8400-208565c9117f"))
                .andExpect(status().isNoContent());
        mvc.perform(put("/books/13/users/2").header("Request-Id", "ff3e3984-e08d-45dd-9f2c-7156c2ae21e2"))
                .andExpect(status().isForbidden());
    }

}
