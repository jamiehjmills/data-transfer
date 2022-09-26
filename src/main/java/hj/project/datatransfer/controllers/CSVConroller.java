package hj.project.datatransfer.controllers;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
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

    //https://www.bezkoder.com/spring-boot-upload-csv-file/
    //https://mkyong.com/java/how-to-read-and-parse-csv-file-in-java/

    @PostMapping("/upload") // this works!
    public String upload(@RequestParam("file") MultipartFile file) {

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

}

/**
 * CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build(); // custom separator
 * try(CSVReader reader = new CSVReaderBuilder(
 * new FileReader(fileName))
 * .withCSVParser(csvParser)   // custom CSV parser
 * .withSkipLines(1)           // skip the first line, header info
 * .build()){
 * List<String[]> r = reader.readAll();
 * r.forEach(x -> System.out.println(Arrays.toString(x)));
 * }
 */
