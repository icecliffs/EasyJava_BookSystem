package moe.iloli.dao;

import moe.iloli.model.Comment;
import moe.iloli.model.Press;

import java.sql.*;
import java.util.ArrayList;

public class PressDAO {
    private String sql = "";
    private ResultSet resultSet;
    private Connection connection;
    private PreparedStatement preparedStatement = null;
    private Statement statement;
    private ArrayList<Press> presses = new ArrayList<>();
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
    public ArrayList<Press> listPress(){
        try {
            getConnection();
            sql = "select * from press;";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Press press = new Press();
                press.setId(resultSet.getInt("pressid"));
                press.setAddress(resultSet.getString("address"));
                press.setPressname(resultSet.getString("pressname"));
                press.setTelephone(resultSet.getString("telephone"));
                presses.add(press);
            };
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return presses;
    }
}
