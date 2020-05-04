package com.gavinkim.controller;

import com.gavinkim.model.ValidationException;
import com.gavinkim.model.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("develop")
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void signup() {
        //Todo
    }

    @Test
    public void signin() {
        //Todo
    }

    @Test
    public void checkUsername() throws Exception {
        String username = "gavin";
        given(userService.checkUniqueUsername(username))
                .willReturn(true);
        mvc.perform(get("/users/validation")
                .param("username", username)
        ).andExpect(status().isOk());

        verify(userService).checkUniqueUsername(username);
    }
}