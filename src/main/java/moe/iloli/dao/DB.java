package moe.iloli.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.InputMismatchException;
import java.util.Properties;

public class DB {
    private Properties properties;
    private String driver;
    private String url;
    private String username;
    private String password;
    private Connection connection;
    public Connection getConnection(){
        return connection;
    }
    public DB(){
        try{
            properties = new Properties();
//            InputStream inputStream = this.getClass().getResourceAsStream("./application.properties");
            BufferedReader bufferedReader = new BufferedReader(new FileReader("D:\\Work\\XMUT\\WEB开发\\第八次课\\CHUANTONG\\src\\main\\resources\\application.properties"));
            properties.load(bufferedReader);
            driver = properties.getProperty("driver");
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
