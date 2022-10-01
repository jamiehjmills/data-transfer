package hj.project.datatransfer.controllers;

import hj.project.datatransfer.configs.Config;
import hj.project.datatransfer.services.DBConnector;
import hj.project.token.services.MainTokenizer;
import hj.project.token.services.connections.PostgresConnection;
import hj.project.token.services.hashing.Base64Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class Processor {

    @Autowired
    Config config;

    @Autowired
    DBConnector dbConnector;

    MainTokenizer tokenize;

    @Autowired
    PostgresConnection postgresConnection;

    @Autowired
    Base64Hash hashCreator;

    public ArrayList<String> finalDataSet;

    @PostMapping("/start") //TODO: need to do a testing to ensure it works
    public String start() {

        Map<Integer, ArrayList<String>> dataset = config.getData();
        ArrayList<Map<String, String>> db = config.getDatabase();

        for (int j = 0; j < db.size(); j++) {

            String url = db.get(j).get("url");
            String user = db.get(j).get("user");
            String pw = db.get(j).get("pw");
            String table = db.get(j).get("table");

            //init database
            dbConnector.init(url, user, pw, table);

            //skip the heading so start with i = 1
            for (int i = 1; i < dataset.size(); i++) {

                ArrayList<String> row = dataset.get(i);

                for (String cell : row) {
                    finalDataSet = new ArrayList<>();

                    //TODO: tokenize in here

                    finalDataSet.add(cell);
                }

                //pass finalDataSet to dbConnector to save them into database
                dbConnector.saveIntoDB(finalDataSet);

            }

        }

        return "OK";

    }


}
