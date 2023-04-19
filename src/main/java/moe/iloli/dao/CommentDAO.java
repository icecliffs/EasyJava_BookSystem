package moe.iloli.dao;

import moe.iloli.model.Book;
import moe.iloli.model.Comment;
import java.util.Date;
import java.sql.*;
import java.util.ArrayList;

public class CommentDAO {
    private String sql = "";
    private ResultSet resultSet;
    private Connection connection;
    private PreparedStatement preparedStatement = null;
    private Statement statement;
    private ArrayList<Comment> comments = new ArrayList<>();
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
    public ArrayList<Comment> getAllComment(int id){
        try {
            getConnection();
            sql = "select * from comment where bookid=?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Comment comment = new Comment();
                comment.setBookid(resultSet.getInt("bookid"));
                comment.setUsername(resultSet.getString("username"));
                comment.setComment(resultSet.getString("comment"));
                comment.setCommenttime(resultSet.getString("commentTime"));
                comment.setAvatarurl("avatarurl");
                comment.setIp(resultSet.getString("ip"));
                comments.add(comment);
            };
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return comments;
    }
    public ArrayList<Comment> getByRs(ResultSet resultSet) {
        try {
            if (resultSet == null || !resultSet.next()) {
                return null;
            }
            comments.clear();
            do {
                Comment comment = new Comment();
                comment.setBookid(resultSet.getInt("bookid"));
                comment.setUsername(resultSet.getString("username"));
                comment.setComment(resultSet.getString("comment"));
                comment.setCommenttime(resultSet.getString("commentTime"));
                comment.setIp(resultSet.getString("ip"));
                comments.add(comment);
            } while (resultSet.next());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return comments;
    }
    public boolean add(Comment comment) {
        int result = 0;
        try {
            sql = "insert into comment(username,bookid,comment,ip,commenttime) values(?,?,?,?,?)";
            Date date = new Date();
            String commenttime = date.toLocaleString();
            preparedStatement = getConnection().prepareStatement(sql);
            preparedStatement.setString(1, comment.getUsername());
            preparedStatement.setInt(2, comment.getBookid());
            preparedStatement.setString(3,comment.getComment());
            preparedStatement.setString(4, comment.getIp());
            preparedStatement.setString(5,commenttime);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result > 0;
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

}
