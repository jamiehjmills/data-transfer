package hj.project.datatransfer.controllers;

import hj.project.datatransfer.configs.Config;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/csv")
public class CSVConroller {

    public final String FILE_TYPE = "text/csv";

    public Map<Integer, ArrayList<String>> dataset;

    @Autowired
    Config config;

    @PostMapping("/upload") // this works!
    public String uploadFile(@RequestParam("file") MultipartFile file) {

        dataset = new HashMap<>();
        int row = 0;

        if (config.getData() != null) {
            return "dataset is already filled";
        }

        try {
            if (FILE_TYPE.equals(file.getContentType())) {
                InputStreamReader isr = new InputStreamReader(file.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(isr);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

                for (CSVRecord csvRecord : csvParser) {
                    // Integer the number of row and ArrayList<String> is the column
                    ArrayList<String> column = new ArrayList<>();
                    dataset.put(row, column);
                    for (int i = 0; i < csvRecord.size(); i++) {
                        dataset.get(row).add(csvRecord.get(i));
                    }
                    row++;
                }
                config.setData(dataset);
                test(dataset);
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

        if (config.getData() != null) {
            return "dataset is already filled";
        }

        try {
            String[] row = data.split(" ");
            for (int i = 0; i < row.length; i++) {
                //System.out.println("row: " + row[i]); //java.lang.String -> TODO: save them to Map<Integer,ArrayList<String>> = Integer the number of row and ArrayList<String> is the colunm
                ArrayList<String> column = new ArrayList<>();
                splitRow(row[i].split(","), column); //<- TODO: need to fix it here.
            }
            config.setData(dataset);
            test(dataset);
            return "GOOD";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void splitRow(String[] row, ArrayList<String> column) {
        for (String cell : row) {
            System.out.println("cell: "+ cell);
            column.add(cell);
        }
    }

    public void test(Map<Integer, ArrayList<String>> dataset) {

        for(int i = 0; i < dataset.size(); i++){

            System.out.println(dataset.get(i));

        }

    }
}


