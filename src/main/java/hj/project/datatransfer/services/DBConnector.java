package hj.project.datatransfer.services;

import hj.project.datatransfer.configs.Config;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

@Service
public class DBConnector {

    @Autowired
    Config config;

    public String table;

    private Connection connection;

    private static final Logger logger = LoggerFactory.getLogger(DBConnector.class);

    @SneakyThrows
    public void init(String url, String user, String pw, String table) {

        System.out.println("url:" + url);
        System.out.println("user:" + user);
        System.out.println("pw:" + pw);
        System.out.println("table:" + table);

        connection = DriverManager.getConnection(url, user, pw);
        this.table = table;

        logger.info("initiate the database process..");

    }

    @SneakyThrows
    public void saveIntoDB(ArrayList<String> row) {

        try {

            StringBuilder sb = new StringBuilder();

            for (String s : row) {
                sb.append(String.format("'%s'", s));
                sb.append(",");
            }

            String values = sb.substring(0, sb.length() - 1);

            String sql = String.format("INSERT INTO %s " +
                    "VALUES(%s)", table, values);

            connection.prepareStatement(sql).execute();
            logger.info("Inserting the data to the database has been completed");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
