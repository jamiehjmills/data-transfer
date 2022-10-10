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
import java.util.Arrays;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = "classpath:application-token.properties")
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

        String url = "";
        String user = "";
        String pw = "";
        String table = "data_transfer";

        dbConnector.init(url, user, pw, table);
        ArrayList<String> row = new ArrayList<>();
        row.add("test5");
        row.add("test6");
        row.add("test7");
        row.add("test8");


        dbConnector.saveIntoDB(row);

        String test = "'hi','hi2','hi3','hi4'";

        //jdbc:postgresql://localhost:5454/postgres
    }

    @Test
    void token() {

        String original = "testsrsss";
        tokenize = new Tokenize(postgresConnection, hashCreator);
        tokenize.init();
        String token = tokenize.encode(original);
        //Assert.assertEquals("3562157357135634-357", token);
        Assert.assertEquals("1223", tokenize.decode("1223"));

    }

    @Test
    void csvControllerTest() {

        String test = "test2, test3, test4";
        String[] list = test.split(",");

        System.out.println(Arrays.toString(list));

        //TODO: need to think how we can do the testing here. Probably we have to use a mockup testing class
    }

    @Test
    void anyTest() {

    }

}
