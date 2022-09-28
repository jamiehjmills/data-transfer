package hj.project.datatransfer.services;

import hj.project.datatransfer.configs.Config;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

@Service
public class DBConnector {

    @Autowired
    Config config;

    public String table;

    private Connection connection;

    @SneakyThrows
    public void init(String url, String user, String pw, String table) {

        connection = DriverManager.getConnection(url, user, pw);
        this.table = table;

    }

    @SneakyThrows
    public void saveIntoDB(ArrayList<String> row) {

        try {

            StringBuilder sb = new StringBuilder();

            for(String s : row) {
                sb.append(String.format("'%s'", s));
                sb.append(",");
            }

            String values = sb.substring(0,sb.length()-1);

            String sql = String.format("INSERT INTO %s " +
                    "VALUES(%s)", table, values);

            connection.prepareStatement(sql).execute();
            //logger.info("Inserting has been completed");

        } catch (SQLException e) {
            //logger.warn(e.getMessage());
        }


    }

}
