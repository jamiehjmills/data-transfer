package hj.project.datatransfer.controllers;

import hj.project.datatransfer.configs.Config;
import hj.project.datatransfer.services.DBConnector;
import hj.project.token.services.MainTokenizer;
import hj.project.token.services.Tokenize;
import hj.project.token.services.connections.PostgresConnection;
import hj.project.token.services.hashing.Base64Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    MainTokenizer tokenizer;

    @Autowired
    PostgresConnection postgresConnection;

    @Autowired
    Base64Hash hashCreator;

    @Autowired
    TokenizerOrDeTokenizer tokenizerOrDeTokenizer;

    public final String URL = "url";
    public final String USER = "username";
    public final String PW = "password";
    public final String TABLE = "table";

    public ArrayList<String> finalDataRow;
    private static final Logger logger = LoggerFactory.getLogger(Processor.class);

    @PostMapping("/start")
    public String start() {

        logger.info("started the process..");

        if (config.getData() == null || config.getDatabase() == null) {
            logger.info("no data or information for database is provided");
            return "no data or database is provided to process it";
        }

        Map<Integer, ArrayList<String>> dataset = config.getData();
        ArrayList<Map<String, String>> db = config.getDatabase();
        ArrayList<String> tokenize = config.getTokenize();
        ArrayList<String> deTokenize = config.getDetokenize();

        logger.info(String.format("total %d of the database(s) will be processed", db.size()));

        //TODO: need to
        for (int j = 0; j < db.size(); j++) {

            String url = db.get(j).get(URL);
            String user = db.get(j).get(USER);
            String pw = db.get(j).get(PW);
            String table = db.get(j).get(TABLE);

            logger.info(String.format("the url for the database is : %s", url));

            //init database
            dbConnector.init(url, user, pw, table);

            //init the tokenizer service
            tokenizer = new Tokenize(postgresConnection, hashCreator);
            tokenizer.init();

            logger.info("start inserting the rows of the dataset to the database");

            //skip the heading so start with i = 1
            for (int i = 1; i < dataset.size(); i++) {

                ArrayList<String> row = dataset.get(i);
                logger.info(String.format("row : %s", row));

                dbConnector.saveIntoDB(
                        tokenizerOrDeTokenizer.init(row,
                        tokenize,
                        deTokenize,
                        tokenizer));

            }
            logger.info("Inserting the rows to the database has been completed");
        }
        logger.info("Completed the whole process. You can close it now.");
        return "OK";
    }

}
