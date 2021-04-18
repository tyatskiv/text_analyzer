package com.yatskiv.textprocessor.print;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class FilePrinter implements TextPrinter {

    private final String fileName;

    public FilePrinter(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void print(Map<String, List<String>> text) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            text.forEach((k, v) -> {
                try {
                    writer.write(k + " --> " + v);
                    writer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
