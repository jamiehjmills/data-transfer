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

        if (theyAreDuplicates(tokenize, deTokenize)) {
            throw new RuntimeException("the values from each tokenize and deTokenize shouldn't be overlapped");
        }

        if (tokenize != null || deTokenize != null) updateElements(list, tokenize, deTokenize, tokenizer, true);

        return list;

    }

    public void updateElements(ArrayList<String> list,
                               ArrayList<String> tokenize,
                               ArrayList<String> deTokenize,
                               MainTokenizer tokenizer,
                               boolean isTokenize) {

        if (isTokenize) {
            for (int i = 0; i < list.size(); i++) {
                if (tokenize.contains(i)) {
                    list.set(i, tokenizer.encode(list.get(i)));
                }
            }
        } else {
            updateElements(list, deTokenize, tokenizer);
        }
    }

    public void updateElements(ArrayList<String> list,
                               ArrayList<String> deTokenize,
                               MainTokenizer tokenizer) {

        for (int i = 0; i < list.size(); i++) {
            if (deTokenize.contains(i)) {
                list.set(i, tokenizer.decode(list.get(i)));
            }
        }

    }

    public Boolean theyAreDuplicates(ArrayList<String> tokenize,
                                     ArrayList<String> deTokenize) {

        for (int i = 0; i < tokenize.size(); i++) {
            if (deTokenize.contains(tokenize.get(i))) {
                return true;
            }
        }
        return false;
    }

}
