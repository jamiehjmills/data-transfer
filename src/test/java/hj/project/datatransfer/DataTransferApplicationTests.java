package hj.project.datatransfer;

import hj.project.datatransfer.services.DBConnector;
import hj.project.token.services.MainTokenizer;
import hj.project.token.services.Tokenize;
import hj.project.token.services.connections.PostgresConnection;
import hj.project.token.services.hashing.Base64Hash;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations="classpath:application-token.properties")
class DataTransferApplicationTests {

    @Autowired
    DBConnector dbConnector;


    MainTokenizer tokenize;

    @Autowired
    PostgresConnection postgresConnection;
    @Autowired
    Base64Hash hashCreator;


    @Test
    void contextLoads() {

        String url = "jdbc:postgresql://jamie-1.ckeesvebqsog.ap-southeast-2.rds.amazonaws.com:5432/postgres";
        String user = "postgres";
        String pw = "postgres123";
        String table = "data_transfer";

        dbConnector.init(url,user,pw,table);
        ArrayList<String> row = new ArrayList<>();
        row.add("test5");
        row.add("test6");
        row.add("test7");
        row.add("test8");

        dbConnector.saveIntoDB(row);

        String test = "'hi','hi2','hi3','hi4'";


    }

    @Test
    void token(){

        String original = "testsrsss";
        tokenize = new Tokenize(postgresConnection, hashCreator);
        tokenize.init();
        String token = tokenize.encode(original);
        Assert.assertEquals("3562157357135634-141357", token);
        Assert.assertEquals(original,tokenize.decode(token));

    }

}
