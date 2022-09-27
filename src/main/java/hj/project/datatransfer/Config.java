package hj.project.datatransfer;

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

    public ArrayList<String> tokenize;
    public ArrayList<String> detokenize;
    public ArrayList<Map<String,String>>  database;

}
