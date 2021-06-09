package pl.irc.frontend;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MyRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void getMessages() throws Exception {
        mvc.perform(get("/api/messages"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Siema")));
    }

    @Test
    void postMessage() throws Exception {
        mvc.perform(post("/api/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"text\": \"Ala ma kotka\" }"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Ala ma kotka")));

        mvc.perform(get("/api/messages"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Ala ma kotka")))
                .andExpect(jsonPath("$.length()", Matchers.is(3)));
    }
}