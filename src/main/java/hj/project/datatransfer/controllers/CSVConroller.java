package hj.project.datatransfer.controllers;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/api/csv")
public class CSVConroller {

    public final String FILE_TYPE = "text/csv";

    @PostMapping("/test") // this works! - ONLY FOR TESTING
    public String test(@RequestParam("test") String input) {
        return "Given value is a " + input;
    }

    @PostMapping("/upload") // this works!
    public String uploadFile(@RequestParam("file") MultipartFile file) {

        try {
            if (FILE_TYPE.equals(file.getContentType())) {
                InputStreamReader isr = new InputStreamReader(file.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(isr);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

                for (CSVRecord csvRecord : csvParser) {
                    System.out.println(csvRecord);
                }
            }
            return "GOOD";
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/uploadData") // this works!
    public String uploadData(@RequestParam("data") String file) {

        return null;
    }

}


