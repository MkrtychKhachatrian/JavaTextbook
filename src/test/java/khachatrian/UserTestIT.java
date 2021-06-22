package khachatrian;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import khachatrian.context.Application;
import khachatrian.controller.UserController;
import khachatrian.exception.ObjectNotFoundException;
import khachatrian.model.RoleUser;
import khachatrian.model.User;
import khachatrian.repository.RoleRepository;

import java.util.LinkedList;
import java.util.List;


public class UserTestIT {

    AnnotationConfigApplicationContext context;

    UserController userController;
    RoleRepository roleRepository;

    List<User> expected = new LinkedList<>();
    Long savedID = null;

    @Before
    public void setUp () {
        context = new AnnotationConfigApplicationContext(Application.class);

        userController = context.getBean(UserController.class);

        expected = userController.findAllUsers();
    }
    @Test
    public void findAllUsers_isFindCorrect_true () {
        //GIVEN
        roleRepository = context.getBean(RoleRepository.class);
        List<User> expected1 = new LinkedList<>();
        expected1.add(new User(1L, "Ivan","Ivanov", roleRepository.findById(1L).orElseThrow(() -> new ObjectNotFoundException(RoleUser.class.getName(), 1L))));
        expected1.add(new User(2L, "Stepan","Stepanov", roleRepository.findById(2L).orElseThrow()));
        expected1.add(new User(3L, "Mkrtych","Khachatrian", roleRepository.findById(3L).orElseThrow()));
        //WHEN
        List<User> actual = userController.findAllUsers();
        //THEN
        Assert.assertEquals(expected1,actual);
    }
    @Test
    public void findUserByID_isFindCorrect_true () {
        //GIVEN
        //WHEN
        User actual = userController.findUserById(1L);
        //THEN
        Assert.assertEquals(expected.get(0),actual);
    }
    @Test(expected = ObjectNotFoundException.class)
    public void findUserByID_whenIdIsInvalid_true () {
        //GIVEN
        //WHEN
        userController.findUserById(100L);
        //THEN
    }
    @Test
    public void saveUser_isSaveCorrect_true () {
        //GIVEN
        User test = new User("testName", "testSurname");
        expected.add(test);
        //WHEN
        userController.saveUser(test);
        List<User> actual = userController.findAllUsers();
        savedID = actual.get(actual.size()-1).getId();
        expected.get(expected.size()-1).setId(savedID);
        //THEN
        Assert.assertEquals(expected,actual);
    }
    @Test
    public void updateUser_isUpdateCorrect_true () {
        //GIVEN
        User test = new User("testName", "testSurname");
        userController.saveUser(test);
        List<User> actual = userController.findAllUsers();
        savedID = actual.get(actual.size()-1).getId();

        User test1 = new User("testUPDATENAME", "testUPDATESURNAME");
        expected.add(test1);
        //WHEN
        actual = userController.updateUser(savedID,test1);
        //THEN
        Assert.assertEquals(expected,actual);
    }
    @Test
    public void deleteUser_isDeleteCorrect_true () {
        //GIVEN
        User test = new User("testName", "testSurname");
        userController.saveUser(test);
        List<User> actual = userController.findAllUsers();
        savedID = actual.get(actual.size()-1).getId();
        //WHEN
        actual = userController.deleteUser(savedID);
        savedID = null;
        //THEN
        Assert.assertEquals(expected,actual);
    }
    @Test
    public void addRoleID_isAddCorrect_true () {
        //GIVEN
        roleRepository = context.getBean(RoleRepository.class);
        User expectedUser = new User("testName", "testSurname");
        userController.saveUser(expectedUser);

        List<User> actual = userController.findAllUsers();
        savedID = actual.get(actual.size()-1).getId();
        //WHEN
        userController.addRoleId(savedID,1L);
        expectedUser.setRole(roleRepository.findById(1L).orElseThrow());
        //THEN
        Assert.assertEquals(expectedUser,userController.findUserById(savedID));
    }

    @After
    public void tearDown () {
        if (savedID!=null)
            userController.deleteUser(savedID);
    }
}
