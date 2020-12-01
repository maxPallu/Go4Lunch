package com.maxpallu.go4lunch;

import com.maxpallu.go4lunch.api.User;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestsUnitaires {

    private User user;

    @Before
    public void userCreation() {
        user = new User("1111", "Test", null, null, "test", "2222");
    }

    @Test
    public void testUserInfo() {
        assertEquals("1111", user.getId());
        assertEquals("Test", user.getName());
        assertEquals(null, user.getEmail());
        assertEquals(null, user.getUrlPicture());
        assertEquals("test", user.getRestaurantName());
        assertEquals("2222", user.getRestaurantId());
    }

    @Test
    public void testUserSetInfo() {
        user.setId("1234");
        user.setName("Name");
        user.setEmail("Mail");
        user.setUrlPicture(null);
        user.setRestaurantName("Test");
        user.setRestaurantId("4321");

        assertEquals("1234", user.getId());
        assertEquals("Name", user.getName());
        assertEquals("Mail", user.getEmail());
        assertEquals(null, user.getUrlPicture());
        assertEquals("Test", user.getRestaurantName());
        assertEquals("4321", user.getRestaurantId());
    }
}
