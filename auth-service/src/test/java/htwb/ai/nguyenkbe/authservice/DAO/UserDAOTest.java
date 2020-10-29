package htwb.ai.nguyenkbe.authservice.DAO;

import htwb.ai.nguyenkbe.authservice.Controller.UserController;
import htwb.ai.nguyenkbe.authservice.Entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    static UserDAO userDAO;

    @BeforeAll
    public static void setUpDao() {
        userDAO = new UserDAO ("TEST-PU-nguyenkbe");
    }


    @Test
    public void getUserbyUserIdShouldSucces(){
        User gettingUser = userDAO.getUser("mmuster");
        assertTrue(gettingUser.getFirstName().equals(initUser().getFirstName()));
    }

    @Test
    public void getNonExistingUserReturnNull(){
        User gettingUser = userDAO.getUser("hello");
        assertTrue(gettingUser == null);
    }


    public User initUser(){
        return User.builder().withFirstname("Maxime").withLastname("Muster").withPassword("pass1234").withUserId("mmuster").build();
    }

    @Test
    public void testCloseEMF(){
       UserDAO userDAO2 = new UserDAO ("TEST-PU-nguyenkbe");
        userDAO2.closeEMF();
    }


}