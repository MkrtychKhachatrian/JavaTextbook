package khachatrian;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import khachatrian.context.Application;
import khachatrian.controller.TextbookController;
import khachatrian.exception.ObjectNotFoundException;
import khachatrian.model.Textbook;
import khachatrian.model.enums.Subject;
import khachatrian.model.enums.Color;
import khachatrian.model.enums.Size;
import khachatrian.model.enums.Material;

import java.util.LinkedList;
import java.util.List;

public class TextbookTestIT {
    AnnotationConfigApplicationContext context;
    TextbookController textbookController;
    List<Textbook> expected = new LinkedList<>();
    Long savedID = null;

    @Before
    public void setUp () {
        context = new AnnotationConfigApplicationContext(Application.class);

        textbookController = context.getBean(TextbookController.class);

        expected = textbookController.allTextbooks();
    }
    @Test
    public void findAllTextbooks_isFindCorrect_true() {
        //GIVEN
        List<Textbook> expected1 = new LinkedList<>();
        expected1.add(new Textbook(1L, "textbook1", 100,  Size.A4,  Color.ORANGE,  Subject.MATH,   Material.PAPER));
        expected1.add(new Textbook(2L, "textbook2", 150,  Size.SRA5,  Color.BLACK,  Subject.SCIENCE,  Material.GLOSS));
        expected1.add(new Textbook(3L, "textbook3", 100,  Size.A5, Color.WHITE,   Subject.BIOLOGY, Material.PAPER));
        expected1.add(new Textbook(4L, "textbook4", 105, Size.A4,  Color.BLUE, Subject.HISTORY,  Material.GLOSS));
        expected1.add(new Textbook(5L, "textbook5", 170,  Size.A5, Color.PINK,  Subject.ENGLISH,  Material.PAPER));

        //WHEN
        List<Textbook> actual = textbookController.allTextbooks();
        //THEN
        Assert.assertEquals(expected1,actual);
    }
    @Test
    public void findTextbookByID_isFindCorrect_true() {
        //GIVEN
        //WHEN
        Textbook actual = textbookController.findTextbookById(2L);
        //THEN
        Assert.assertEquals(expected.get(1),actual);
    }
    @Test(expected = ObjectNotFoundException.class)
    public void findTextbookByID_whenIdIsInvalid_true() {
        //GIVEN
        //WHEN
        textbookController.findTextbookById(100L);
        //THEN
    }
    @Test
    public void findTextbookByName_isFindCorrect_true() {
        //GIVEN
        //WHEN
        Textbook actual = textbookController.findTextbookByName("textbook1");
        //THEN
        Assert.assertEquals(expected.get(0),actual);
    }
    @Test(expected = ObjectNotFoundException.class)
    public void findTextbookByName_whenNameIsInvalid_true() {
        //GIVEN
        //WHEN
        textbookController.findTextbookByName("ErrorName");
        //THEN
    }
    @Test
    public void saveTextbook_isSaveCorrect_true() {
        //GIVEN
        Textbook test = new Textbook("textbook6", 500,  Size.A4, Color.WHITE,  Subject.ENGLISH,  Material.PAPER);
        expected.add(test);
        //WHEN
        textbookController.saveTextbook(test);
        List<Textbook> actual = textbookController.allTextbooks();
        savedID = actual.get(actual.size()-1).getId();
        expected.get(expected.size()-1).setId(savedID);
        //THEN
        Assert.assertEquals(expected,actual);
    }
    @Test
    public void updateTextbook_isUpdateCorrect_true() {
        //GIVEN
        Textbook test = new Textbook("textbook6", 50,  Size.A4, Color.BLACK,  Subject.ENGLISH,  Material.PAPER);
        textbookController.saveTextbook(test);
        List<Textbook> actual = textbookController.allTextbooks();
        savedID = actual.get(actual.size()-1).getId();

        Textbook test2 = new Textbook("update", 10,  Size.A4, Color.BLACK,  Subject.BIOLOGY,  Material.PAPER);
        expected.add(test2);
        //WHEN
        actual = textbookController.updateTextbook(savedID,test2);
        //THEN
        Assert.assertEquals(expected,actual);
    }
    @Test
    public void deleteTextbook_isDeleteCorrect_true() {
        //GIVEN
        Textbook test = new Textbook("textbook6", 50,  Size.A4, Color.BLACK,  Subject.ENGLISH,  Material.PAPER);
        textbookController.saveTextbook(test);
        List<Textbook> actual = textbookController.allTextbooks();
        savedID = actual.get(actual.size()-1).getId();
        //WHEN
        textbookController.deleteTextbook(savedID);
        actual = textbookController.allTextbooks();
        savedID = null;
        //THEN
        Assert.assertEquals(expected,actual);
    }
    @After
    public void tearDown () {
        if (savedID!=null)
            textbookController.deleteTextbook(savedID);
    }
}

