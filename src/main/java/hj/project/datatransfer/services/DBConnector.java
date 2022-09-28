package hj.project.datatransfer.services;

import hj.project.datatransfer.configs.Config;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Map;

@Service
public class DBConnector {

    @Autowired
    Config config;

    private Connection connection;

    @SneakyThrows
    public void init(String url, String user, String pw){

        connection = DriverManager.getConnection(url, user, pw);

    }


}
