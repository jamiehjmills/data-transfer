package hj.project.datatransfer.controllers;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/csv")
public class CSVConroller {

    public final String FILE_TYPE = "text/csv";

    public  Map<Integer, ArrayList<String>> dataset;

    @PostMapping("/test") // this works! - ONLY FOR TESTING
    public String test(@RequestParam("test") String input) {
        return "Given value is a " + input;
    }

    @PostMapping("/upload") // this works!
    public String uploadFile(@RequestParam("file") MultipartFile file) {

        dataset = new HashMap<>();

        try {
            if (FILE_TYPE.equals(file.getContentType())) {
                InputStreamReader isr = new InputStreamReader(file.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(isr);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

                for (CSVRecord csvRecord : csvParser) {
                    System.out.println(csvRecord.getClass()); //org.apache.commons.csv.CSVRecord -> TODO: save them to Map<Integer,ArrayList<String>> = Integer the number of row and ArrayList<String> is the colunm
                    for (int i = 0; i < csvRecord.size(); i++) {
                        System.out.println(csvRecord.get(i));
                    }
                }

                //config.setData(dataset)

            }
            return "GOOD";
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/uploadData") // this works!
    public String uploadData(@RequestBody String data) {

        dataset = new HashMap<>();

        String[] list = data.split(" ");

        dataset = new HashMap<>();

        for (int i = 0; i < list.length; i++) {
            System.out.println(list[i].getClass()); //java.lang.String -> TODO: save them to Map<Integer,ArrayList<String>> = Integer the number of row and ArrayList<String> is the colunm
        }

        //config.setData(dataset)

        return "done";
    }


}


