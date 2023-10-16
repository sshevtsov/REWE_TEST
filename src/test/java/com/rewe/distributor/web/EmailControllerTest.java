package com.rewe.distributor.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rewe.distributor.dto.EmailDto;
import com.rewe.distributor.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(EmailController.class)
public class EmailControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmailService emailService;

    @Test
    public void send_email_test() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        EmailDto emailDto = new EmailDto();

        this.mockMvc.perform(post("/email/send")
                        .content(objectMapper.writeValueAsString(emailDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Email was sent successfully")));
    }

    @Test
    public void send_random_email_test() throws Exception {
        this.mockMvc.perform(post("/email/send/random")
                        .param("topic", "test_topic")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Random email was sent successfully")));
    }
}
