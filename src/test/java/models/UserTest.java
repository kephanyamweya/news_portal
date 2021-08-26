package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void getId_idInstantiatesCorrectly() {
        User user = setUpUser();
        user.setId(1);
        assertEquals(1, user.getId());
    }

    @Test
    public void getName_instantiatesCorrectlyWithName() {
        User user = setUpUser();
        assertEquals("kepha", user.getName());
    }

    @Test
    public void getRole_instantiatesCorrectlyWithRole() {
        User user = setUpUser();
        assertEquals("leader", user.getRole());
    }

    @Test
    public void getDepartmentId_instantiatesCorrectlyWithDepartmentId() {
        User user = setUpUser();
        assertEquals(1, user.getDepartmentId());
    }

    @Test
    public void setId_idIsSetCorrectlyWithSetter() {
        User user = setUpUser();
        user.setId(1);
        assertEquals(1, user.getId());
    }

    @Test
    public void testEquals_usersWithSameNameAndRoleAreEqual() {
        User user = setUpUser();
        User otherUser = setUpUser();
        assertEquals(true, user.equals(otherUser));
    }

    @Test
    public void testHashCode_usersWithSameNameAndRoleAreEqual() {
        User user = setUpUser();
        User otherUser = setUpUser();
        assertEquals(true, user.equals(otherUser));
    }

    //helper
    public User setUpUser() {
        return new User("kepha", "leader", 1);
    }
}
