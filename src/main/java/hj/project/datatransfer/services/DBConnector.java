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
    public void saveIntoDB(ArrayList<String> list) {

        try {

            String sql = String.format("INSERT INTO %s" +
                    "VALUES(?,?,?,?)", table);
            PreparedStatement stmt = connection.prepareStatement(sql);
            for(String cell: list){
                stmt.setString(1, cell);
                stmt.addBatch();
            }
            stmt.executeBatch();
            //tokenConn.prepareStatement(sql).execute();
            //logger.info("Inserting has been completed");

        } catch (SQLException e) {
            //logger.warn(e.getMessage());
        }


    }

}
