package hj.project.datatransfer.controllers;

import hj.project.token.services.MainTokenizer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TokenizerOrDeTokenizer {

    public ArrayList<String> init(ArrayList<String> list,
                                  ArrayList<String> tokenize,
                                  ArrayList<String> deTokenize,
                                  MainTokenizer tokenizer) {

        if (tokenize == null && deTokenize == null) return list;

        return updateElements(list, tokenize, deTokenize, tokenizer);

    }


    public ArrayList<String> updateElements(ArrayList<String> list,
                               ArrayList<String> tokenize,
                               ArrayList<String> deTokenize,
                               MainTokenizer tokenizer) {

        ArrayList<String> afterTokenApplied = new ArrayList<>();

//        if (theyAreDuplicates(tokenize, deTokenize)) {
//            throw new RuntimeException("tokenize and deTokenize should not overlap their values");
//        }

        for (int i = 0; i < list.size(); i++) {
            afterTokenApplied.add(list.get(i));
            if (tokenize.contains(i)) {
                afterTokenApplied.set(i, tokenizer.encode(list.get(i)));
            } else if (deTokenize.contains(i)) {
                afterTokenApplied.set(i, tokenizer.decode(list.get(i)));
            }
        }

        return afterTokenApplied;

    }

    public Boolean theyAreDuplicates(ArrayList<String> tokenize,
                                     ArrayList<String> deTokenize) {

        for (String index : tokenize) {
            if (deTokenize.contains(index)) {
                return true;
            }
        }
        return false;
    }

}
