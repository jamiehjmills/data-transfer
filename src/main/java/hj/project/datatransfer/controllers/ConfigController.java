package hj.project.datatransfer.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import hj.project.datatransfer.configs.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ConfigController {

    @Autowired
    Config config;

    @JsonProperty("database")
    @PostMapping("/config")
    public String getDatabase(@RequestBody Map<String, Object> data) {
        config.setTokenize((ArrayList<String>)data.get("tokenize"));
        config.setTokenize((ArrayList<String>)data.get("detokenize"));
        ArrayList<Map<String,String>> list = (ArrayList<Map<String,String>>) data.get("database");
        config.setDatabase(list);
        return "ok";
    }

}
