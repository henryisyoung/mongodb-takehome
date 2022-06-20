package com.mongo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, Charset.defaultCharset()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String json = sb.toString().replaceAll("\\s", "");
            System.out.println("Before format: \n" + prettyJson(json));

            String compressedJson = reformatJson(json);
            System.out.println("After format: \n" + prettyJson(compressedJson));
        } catch (IOException e) {
            System.out.println("System IO Exception: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("NullPointerException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }

    private static String prettyJson(String json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(json);
        String prettyJsonString = gson.toJson(je);
        return prettyJsonString;
    }

    public static String reformatJson(String json) {
        if (json == null || json.length() == 0) return "";
        Object obj= JSONValue.parse(json);
        JSONObject jsonObject = (JSONObject) obj;
        Iterator<String> keys = jsonObject.keySet().iterator();

        Map<String, Object> map = new HashMap();
        while(keys.hasNext()) {
            String key = keys.next();
            Map<String, Object> segmentMap = compressJson(key, jsonObject.get(key));
            for (Map.Entry<String, Object> entry : segmentMap.entrySet()) {
                map.put(entry.getKey(), entry.getValue());
            }
        }
        // return compressed json string
        String jsonText = JSONValue.toJSONString(map);
        return jsonText;
    }

    private static Map<String, Object> compressJson(String key, Object obj) {
        if (obj instanceof JSONObject) {
            // recursively DFS the end value, if obj is a json object
            JSONObject jsonObject = (JSONObject) obj;
            Iterator<String> childrenKeys = jsonObject.keySet().iterator();
            Map<String, Object> result = new HashMap();

            // iterate all next children json object
            while(childrenKeys.hasNext()) {
                String childKey = childrenKeys.next();
                Map<String, Object> map = compressJson(childKey, jsonObject.get(childKey));
                for (String mapKey : map.keySet()) {
                    // concat new compressed key
                    String newKey = key + "." + mapKey;
                    result.put(newKey, map.get(mapKey));
                }
            }
            return result;
        } else {
            // this is the end of search, bubble up the end value
            Map<String, Object> map = new HashMap();
            map.put(key, obj);
            return map;
        }
    }
}
