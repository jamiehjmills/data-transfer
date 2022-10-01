package hj.project.datatransfer.controllers;

import hj.project.datatransfer.configs.Config;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/csv")
public class CSVConroller {

    public final String FILE_TYPE = "text/csv";
    public Map<Integer, ArrayList<String>> dataset;
    private static final Logger logger = LoggerFactory.getLogger(CSVConroller.class);
    @Autowired
    Config config;

    @PostMapping("/upload") // this works!
    public String uploadFile(@RequestParam("file") MultipartFile file) {

        dataset = new HashMap<>();
        int row = 0;

        if (config.getData() != null) {
            logger.info("the dataset is already filled");
            return "the dataset is already filled";
        }

        try {
            if (FILE_TYPE.equals(file.getContentType())) {
                InputStreamReader isr = new InputStreamReader(file.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(isr);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

                for (CSVRecord csvRecord : csvParser) {
                    ArrayList<String> column = new ArrayList<>();
                    dataset.put(row, column);
                    for (int i = 0; i < csvRecord.size(); i++) {
                        dataset.get(row).add(csvRecord.get(i));
                    }
                    row++;
                }
                config.setData(dataset);
            }
            logger.info("data is ready");
            return "GOOD";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/uploadData") // this works!
    public String uploadData(@RequestBody String data) {

        dataset = new HashMap<>();
        int row = 0;

        if (config.getData() != null) {
            logger.info("the dataset is already filled");
            return "the dataset is already filled";
        }

        try {
            String[] list = data.split(" ");
            for (int i = 0; i < list.length; i++) {
                ArrayList<String> column = new ArrayList<>();
                splitRow(list[i].split(","), column);
                dataset.put(row, column);
            }
            config.setData(dataset);
            logger.info("data is ready");
            return "GOOD";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void splitRow(String[] row, ArrayList<String> column) {
        for (String cell : row) {
            column.add(cell);
        }
    }

}


