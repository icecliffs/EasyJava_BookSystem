package moe.iloli.controller;

import moe.iloli.dao.BookDAO;
import moe.iloli.dao.CommentDAO;
import moe.iloli.dao.PressDAO;
import moe.iloli.dao.UserDAO;
import moe.iloli.model.Book;
import moe.iloli.model.Comment;
import moe.iloli.model.Press;
import org.apache.tomcat.util.security.MD5Encoder;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Base64;

public class testController {
    @Test
    public void Run(){
        BookDAO bookDAO = new BookDAO();
//        ArrayList<Book> arrayList = new ArrayList<>();
//        UserDAO userDAO = new UserDAO();
//        System.out.println(userDAO.getUser("1admin", "admin"));
//        String pwd = "admin";
//        String encStr = new String(Base64.getEncoder().encode(pwd.getBytes()));
//        System.out.println(encStr);
//        ArrayList<Book> arrayList = new ArrayList<Book>();
//        ArrayList<Book> arrayList = bookDAO.listBook();
//        for (Book t:
//             arrayList) {
//            System.out.println(t.getBookname());
//        }
//        System.out.println("------------------------------");
//        ArrayList<Book> arrayListSer = bookDAO.getBooks("大学");
//        System.out.println(arrayListSer);
//        Book boos = bookDAO.getById(1);
//        System.out.println(boos.getBookname());
        PressDAO pressDAO = new PressDAO();
        ArrayList<Press> presses = pressDAO.listPress();
        for (Press p:
             presses) {
            System.out.println(p.getAddress());
        }
        CommentDAO commentDAO = new CommentDAO();
        Comment comment = new Comment();
        comment.setComment("shabi");
        comment.setBookid(2);
        comment.setCommenttime("2021-32-18 03:32:20");
        comment.setIp("114.4.1.5");
        comment.setUsername("IceCliffs");
        commentDAO.add(comment);
        //        bookDAO.getBooks();
//        System.out.println(bookDAO.getBooks());
        //        bookDAO.getBooks();
//        System.out.println(bookDAO.getBooks().get(0).getBookname());
//        System.out.println(bookDAO.getBooks().get(1).getBookname());
//        System.out.println(bookDAO.getBooks().get(2).getBookname());
//        System.out.println(bookDAO.getBooks().get(3).getBookname());
    }
}
