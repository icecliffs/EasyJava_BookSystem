package moe.iloli.dao;

import java.sql.*;

public class UserDAO {
    private String sql = "";
    private ResultSet resultSet;
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement = null;
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
//    判断登录
    public boolean getUser(String username, String pwd){
        getConnection();
        Boolean flag = false;
        try {
            sql = "select * from users where username = '"+username+"' and pwd = '"+pwd+"';";
            System.out.println(sql);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return flag;
    }
}
