package hj.project.datatransfer.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/csv")
public class CSVConroller {

    @PostMapping("/upload") // this works!
    public String test(@RequestParam("test")String input){
        return "Given value is a " + input;
    }

}
