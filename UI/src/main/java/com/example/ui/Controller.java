package com.example.ui;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

public class Controller {
    public static HashMap<String, String> configValues = new HashMap<>();

    @FXML
    private TextArea input;
    @FXML
    private TextField inputFile;
    @FXML
    private TextField outputFile;
    @FXML
    private ToggleGroup SortRadio;
    @FXML
    private TextField outSeparator;
    @FXML
    private TextField inSeparator;
    @FXML
    private TextField MaxLinks;
    @FXML
    private TextField MaxDepth;
    @FXML
    private RadioButton top_10;
    @FXML
    private RadioButton defaultcust;
    @FXML
    private TextArea output;

    //Function which gets parameters values.
    private static void GetConfig() throws FileNotFoundException {

        Scanner myReader = Reader("config.txt");

        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            String[] dataParts = data.split("=");
            configValues.put(dataParts[0], dataParts[1]);

        }

    }

    //Function which prepares for reading a file.
    public static Scanner Reader(String file) throws FileNotFoundException {

        Scanner myReader;
        File input = cleanFile(file);
        myReader = new Scanner(input);
        return myReader;

    }

    //Function which gets full path of a file.
    public static File cleanFile(String file) {
        String temp = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(".")).getPath();
        temp = temp.substring(0, temp.lastIndexOf('/'));
        temp = temp.substring(0, temp.lastIndexOf('/')) + "/" + file;

        return new File(temp);
    }

    @FXML
    //Function which runs when the button is pressed.
    public void Compile() throws IOException, InterruptedException {
        //Setting config and input files to the values from JavaFX application.
        SetTextFile(input.getText(), inputFile.getText());
        SetTextFile(FormatText(), "config.txt");

        //Running run.sh to launch program(Main) which gets the word matches .
        Process proc = Runtime.getRuntime().exec(String.valueOf(cleanFile("run.sh")));
        proc.waitFor();

        //Set the JavaFX output text field to the result.
        output.setText(GetText(outputFile.getText()));


    }

    public void initialize() throws FileNotFoundException {
        //Setting up the text in the scenes.
        Startup();
    }

    // Function which formats JavaFX settings into text file. Building everything into a big string and then adding to the text file.
    private String FormatText() {
        StringBuilder temp = new StringBuilder();

        temp.append("MAX_LINKS=");
        temp.append(MaxLinks.getText());
        temp.append("\n");
        temp.append("MAX_DEPTH=");
        temp.append(MaxDepth.getText());
        temp.append("\n");
        temp.append("OUTPUT_SEPARATED_BY=");
        temp.append(outSeparator.getText());
        temp.append("\n");
        temp.append("INPUT_SEPARATED_BY=");
        temp.append(inSeparator.getText());
        temp.append("\n");
        temp.append("INPUT_NAME=");
        temp.append(inputFile.getText());
        temp.append("\n");
        temp.append("OUTPUT_NAME=");
        temp.append(outputFile.getText());
        temp.append("\n");
        temp.append("REPORT_FORM=");
        if (((RadioButton) SortRadio.getSelectedToggle()).getText().equals("From highest to lowest"))
            temp.append("top_10");
        else temp.append("default");
        return temp.toString();


    }

    //Function which sets chosen text to chosen file.
    private void SetTextFile(String text, String fileName) throws FileNotFoundException {
        File file = cleanFile(fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter toFile = new PrintWriter(file);
        toFile.println(text);
        toFile.close();


    }

    //Function which sets the main scene on startup.
    public void Startup() throws FileNotFoundException {
        //Getting infoRMATION from config text file.
        GetConfig();
        //And then adding it to the JavaFX window.
        SetConfig();
        input.setText(GetText(inputFile.getText()));


    }

    //Function which adds data from HashMap to the JavaFX scene.
    private void SetConfig() {
        MaxLinks.setText(configValues.get("MAX_LINKS"));
        MaxDepth.setText(configValues.get("MAX_DEPTH"));
        outSeparator.setText(configValues.get("OUTPUT_SEPARATED_BY"));
        inSeparator.setText(configValues.get("INPUT_SEPARATED_BY"));
        inputFile.setText(configValues.get("INPUT_NAME"));
        outputFile.setText(configValues.get("OUTPUT_NAME"));
        if (configValues.get("REPORT_FORM").equals("top_10")) top_10.setSelected(true);
        else {
            defaultcust.setSelected(true);
        }
    }

    //Function which gets all file text into one string;
    public String GetText(String file) throws FileNotFoundException {

        return new Scanner(cleanFile(file)).useDelimiter("\\Z").next();

    }


}

