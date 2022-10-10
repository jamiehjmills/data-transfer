package hj.project.datatransfer.configs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@Component
public class Config {

    public ArrayList<Integer> tokenize;
    public ArrayList<Integer> detokenize;
    public ArrayList<Map<String, String>> database;
    public Map<Integer,ArrayList<String>> data;

}
