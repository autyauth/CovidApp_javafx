package sample;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseDateConnection {
    public Connection dataBaseLink;
    public Connection getDataBaseLink(){
        String dataBaseName = "coviddata";
        String dataBaseUser = "test";
        String dataBasePassword = "64010342";
        String url = "jdbc:mysql://localhost/" + dataBaseName;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dataBaseLink = DriverManager.getConnection(url,dataBaseUser,dataBasePassword);
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
        return dataBaseLink;
    }
}
