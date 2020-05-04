package com.gavinkim.controller;

import com.gavinkim.controller.auth.AuthController;
import com.gavinkim.controller.auth.SignInResource;
import com.gavinkim.model.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("develop")
@RunWith(SpringRunner.class)
@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;
    @MockBean
    private SignInResource signInResource;

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
        mvc.perform(get("/auth/username/validation")
                .param("username", username)
        ).andExpect(status().isOk());

        verify(userService).checkUniqueUsername(username);
    }
}