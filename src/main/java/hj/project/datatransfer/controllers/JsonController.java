package hj.project.datatransfer.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import hj.project.datatransfer.configs.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class JsonController {

    @Autowired
    Config config;
    private static final Logger logger = LoggerFactory.getLogger(JsonController.class);

    @JsonProperty("database")
    @PostMapping("/config")
    public String getDatabase(@RequestBody Map<String, Object> data) {

        try {
            config.setTokenize((ArrayList<Integer>) data.get("tokenize"));
            config.setDetokenize((ArrayList<Integer>) data.get("detokenize"));
            ArrayList<Map<String, String>> list = (ArrayList<Map<String, String>>) data.get("database");
            config.setDatabase(list);
            logger.info("received configuration for toeknize, detokenize and database");
            return "ok";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
