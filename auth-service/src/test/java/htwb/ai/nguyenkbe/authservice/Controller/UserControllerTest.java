package htwb.ai.nguyenkbe.authservice.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import htwb.ai.nguyenkbe.authservice.DAO.UserDAO;
import htwb.ai.nguyenkbe.authservice.Entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static htwb.ai.nguyenkbe.authservice.TestHelper.asJsonString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest {

    private static MockMvc mockMvc;

    @BeforeAll
    public static void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup (
                new UserController(new UserDAO("TEST-PU-nguyenkbe"))).build();
    }

    @Test
    void postUserShouldReturn201() throws Exception {
        User user = initUser();
        System.out.println(user.toString());
        mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                .content(asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @Test
    void postUserWrongPassShouldReturn401() throws Exception {
        User user = initUser();
        user.setPassword("wrong");
        mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                .content(asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));
    }

    @Test
    void postUserWrongUserIdShouldReturn401() throws Exception {
        User user = initUser();
        user.setUserId("user");
        mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                .content(asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(401));
    }


    @Test
    void postUserWithoutPayloadUserShouldReturn400() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/auth")
                .content(asJsonString(null))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }


    @Test
    void getForTokenCheckReturnOwnerId() throws Exception {
        User user = initUser();
        user.setUserId("user");
        mockMvc.perform(MockMvcRequestBuilders.get("/auth/checktoken/default token")
                .content(asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("nguyen"))
                .andExpect(status().isOk());
    }

    @Test
    void getForTokenCheckReturnInvaild() throws Exception {
        User user = initUser();
        user.setUserId("user");
        mockMvc.perform(MockMvcRequestBuilders.get("/auth/checktoken/wrong token")
                .content(asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("invaild"))
                .andExpect(status().isOk());
    }

    public static User initUser(){
        return new User("mmuster","pass1234","Max","Muster");
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}