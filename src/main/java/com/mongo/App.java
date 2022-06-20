package com.mongo;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Map;

public class App {
    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, Charset.defaultCharset()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Read: " + line);
                sb.append(line);
            }
        }
        String json = sb.toString();
        System.out.println(json);
        ObjectMapper mapper = new ObjectMapper();

        try {

            // convert JSON string to Map
            Map<String, String> map = mapper.readValue(json, Map.class);

            // it works
            //Map<String, String> map = mapper.readValue(json, new TypeReference<Map<String, String>>() {});

            System.out.println(map);

            json = mapper.writeValueAsString(map);

            System.out.println(json);   // compact-print

            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);

            System.out.println(json);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
