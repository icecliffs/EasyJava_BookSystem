package moe.iloli.dao;

import moe.iloli.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

public class BookDAO {
    private String sql = "";
    private ResultSet resultSet;
    private Connection connection;
    private PreparedStatement preparedStatement = null;
    private Statement statement;
    private ArrayList<Book> books = new ArrayList<>();
//    数据库连接
    private Connection getConnection(){
        try {
            if ((connection == null) || connection.isClosed()) {
                DB db = new DB();
                connection = db.getConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    //数据库释放
    public void close() {
        try {
            if (preparedStatement != null) preparedStatement.close();
            if (resultSet != null) resultSet.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            preparedStatement = null;
            resultSet = null;
            connection = null;
        }
    }
    public ArrayList<Book> listBook(){
        getConnection();
        try {
            sql = "select * from book;";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setBookname(resultSet.getString("bookname"));
                book.setAuthor(resultSet.getString("author"));
                book.setPress(resultSet.getString("press"));
                book.setPubdate(resultSet.getString("pubdate"));
                book.setPrice(resultSet.getFloat("price"));
                book.setIsbn(resultSet.getString("isbn"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return books;
    }
//    书籍删除
    public boolean deleteBook(int id){
        int result = 0;
        getConnection();
        try {
            sql = "delete from book where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result > 0;
    }
//    添加书籍
    public boolean addBook(Book book){
        getConnection();
        int result = 0;
        try {
            System.out.println(book.getBookname());
            sql = "insert into book(id,bookname,author,press,pubdate,price,isbn) values(?,?,?,?,?,?,?)";
            preparedStatement = connection.prepareStatement(sql);
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select MAX(id) from book;");
            while (rs.next()) {
                preparedStatement.setInt(1, rs.getInt(1) + 1);
            }
            preparedStatement.setString(2, book.getBookname());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setString(4, book.getPress());
            preparedStatement.setString(5, book.getPubdate());
            preparedStatement.setFloat(6, book.getPrice());
            preparedStatement.setString(7, book.getIsbn());
            result = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result > 0;
    }
//    修改书本
    public boolean updateBook(Book book){
        getConnection();
        int result = 0;
        try {
            sql = "update book set bookname = ?, author = ?, press = ?, pubdate = ?, price = ? where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, book.getBookname());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getPress());
            preparedStatement.setString(4, book.getPubdate());
            preparedStatement.setFloat(5, book.getPrice());
            preparedStatement.setInt(6, book.getId());
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result > 0;
    }
    public Book getById(int id){
        getConnection();
        try{
            sql = "select * from book where id = ?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setBookname(resultSet.getString("bookname"));
                book.setAuthor(resultSet.getString("author"));
                book.setPress(resultSet.getString("press"));
                book.setPubdate(resultSet.getString("pubdate"));
                book.setPrice(resultSet.getFloat("price"));
                book.setIsbn(resultSet.getString("isbn"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return books.get(0);
    }
    public ArrayList<Book> getBooks(String bookname){
        getConnection();
        if(bookname == null)
            bookname = "";
        try{
            CallableStatement callableStatement = connection.prepareCall("{call getByNameProc(?)}");
            callableStatement.setString(1, bookname);
            resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setBookname(resultSet.getString("bookname"));
                book.setAuthor(resultSet.getString("author"));
                book.setPress(resultSet.getString("press"));
                book.setPubdate(resultSet.getString("pubdate"));
                book.setPrice(resultSet.getFloat("price"));
                book.setIsbn(resultSet.getString("isbn"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return books;
    }

    public ArrayList<Book> getByRs(ResultSet resultSet) {
        try {
            if (resultSet == null || !resultSet.next()) {
                return null;
            }
            books.clear();
            do {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setBookname(resultSet.getString("bookname"));
                book.setAuthor(resultSet.getString("author"));
                book.setPress(resultSet.getString("press"));
                book.setPubdate(resultSet.getString("pubdate"));
                book.setPrice(resultSet.getFloat("price"));
                book.setIsbn(resultSet.getString("isbn"));
                books.add(book);
            } while (resultSet.next());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return books;
    }
}