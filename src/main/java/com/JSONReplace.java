package com;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JSONReplace {


    private static final String SERVICE_DATA = "serviceData";
    private static final String SERVICES = "services";

    public static void main(String[] args) throws Exception {

        FileWriter fw = new FileWriter("newivyjxj.json");

        BufferedWriter bfw = new BufferedWriter(fw);


        String s = readFileCfg();

        JSONObject jo = new JSONObject(s);

        JSONArray ja = jo.getJSONObject(SERVICE_DATA).getJSONArray(SERVICES);


        bfw.write(jo.toString());

        bfw.close();

        String name = ja.getJSONObject(0).getJSONObject("title").getString("zh_CN");
        System.out.println(name);
        for (int i = 0; i < ja.length(); i++) {

        }


        String urls = readFileUrls();
        System.out.println(urls);
    }

    public static String readFileCfg() throws IOException {
        File file = new File("C:\\work\\mvn_proj_hub\\maven_archetype\\src\\main\\resources\\wallet_new_feature.json");
        return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
    }

    public static String readFileUrls() throws IOException {
        File file = new File("C:\\work\\mvn_proj_hub\\maven_archetype\\src\\main\\resources\\iconUrls");
        return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
    }


}
