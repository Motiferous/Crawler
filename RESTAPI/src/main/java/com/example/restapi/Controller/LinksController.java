package com.example.restapi.Controller;

import com.example.restapi.Model.Links;
import com.example.restapi.Repository.RepositoryLinks;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class LinksController {

    private final RepositoryLinks linkRep;

    public LinksController(RepositoryLinks linkRep) {
        this.linkRep = linkRep;
    }

    //Command to get sorted results.
    @GetMapping("/resultstop10")
    public ResponseEntity<?> top10() {
        List<Links> linksFound = linkRep.findAll();

        if (linksFound.size() > 0) {
            List<String> answers = getWords(linksFound);
            //We separate results trough a function and then sort them.
            SortAnswers(answers);
            return new ResponseEntity<>(answers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No links available", HttpStatus.NOT_FOUND);
        }

    }

    //Command which returns the default results.
    @GetMapping("/resultsdefault")
    public ResponseEntity<?> defaults() {
        List<Links> linksFound = linkRep.findAll();

        if (linksFound.size() > 0) {
            List<String> answers = getWords(linksFound);
            return new ResponseEntity<>(answers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No links available", HttpStatus.NOT_FOUND);
        }


    }

    //Command which gets every value in database.
    @GetMapping("/geteverything")
    public ResponseEntity<?> everything() {
        List<Links> linksFound = linkRep.findAll();

        if (linksFound.size() > 0) {
            return new ResponseEntity<>(linksFound, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No links available", HttpStatus.NOT_FOUND);
        }
    }

    //Command which gets links and words and then sends them to be processed to.
    @PostMapping("/setlinks")
    public ResponseEntity<?> CrawlTrough(@RequestBody Links newLinks) {
        try {
            newLinks.setAnswers(getResults(newLinks).trim());
            linkRep.save(newLinks);
            return new ResponseEntity<>(newLinks, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //Function which adds words into a single list.
    public List<String> getWords(List<Links> linksFound) {
        ArrayList<String> answers = new ArrayList<>();
        for (Links t : linksFound) {
            answers.add(t.getAnswers());
        }
        return answers;
    }

    //Function sets the text to input file and then sends the results to run.sh file to be processed.
    @SneakyThrows
    public String getResults(Links link) {
        //GET input.txt PATH MANUALLY FROM production FOLDER

        File file = new File("/home/kristupasj/Documents/out/production/input.txt");
        file.createNewFile();

        try (PrintWriter out = new PrintWriter(file)) {
            out.println(link.getLinks() + "\n" + link.getWordsAllOne());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //GET run.sh PATH MANUALLY FROM production FOLDER
        Process proc = Runtime.getRuntime().exec("/home/kristupasj/Documents/out/production/run.sh");
        proc.waitFor();
        //GET output.txt PATH MANUALLY FROM production FOLDER
        return Files.readString(Path.of("/home/kristupasj/Documents/out/production/output.txt"), StandardCharsets.US_ASCII);

    }

    //Command to delete url by id.
    @DeleteMapping("/deleteurl/{id}")
    public ResponseEntity<?> deleteByLink(@PathVariable("id") String id) {
        try {
            linkRep.deleteById(id);
            return new ResponseEntity<>("Deleted link " + id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Function which sorts result list by the last number(sum).
    public void SortAnswers(List<String> answers) {
        ArrayList<Integer> order = new ArrayList<>();
        for (String s : answers) {
            order.add(Integer.valueOf(s.substring(s.lastIndexOf(',') + 1).trim()));
        }
        int n = order.size();
        int index;
        List<Integer> temp = new ArrayList<>();

        for (int i = 0; i < order.size()-1; i++) {
            temp = order.subList(i,n);
            index = temp.indexOf(Collections.max(temp))+i;


            Collections.swap(answers, i, index);
            Collections.swap(order, i, index);

        }


    }


}
