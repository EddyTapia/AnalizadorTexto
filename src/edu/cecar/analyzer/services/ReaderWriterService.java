package edu.cecar.analyzer.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Stream;

public final class ReaderWriterService {
    public Stream<String> readTextFile(String path) throws FileNotFoundException, IOException {
        
        String absolutePath = "";
        if (path.startsWith("/")) {
            absolutePath = System.getProperty("user.home") + path;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(absolutePath))) {
            
            return reader.lines();
        } 
    }

    public static void createTextFile(String absolutePath, String[] content) throws IOException {

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(absolutePath))) {
            
            for (String line : content) {
                writer.write(line);
            }
        }
    }
}