package com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ProcessCCBData {

    private final static String basePath = "C:\\work\\temp\\ccbMigate\\";
    private static Map<String, String> seqIds;
    public static void main(String[] args) {
        String finalSourceName = "finalSource.txt";
        seqIds = buildSourceMap(finalSourceName);
        String signedFileName = "xmxd_plqy_20200417021850000000001_R.txt";
        readFileLineByLine(signedFileName);

//        File[] files = new File(basePath).listFiles();
//        for (File file : files) {
//            if (file.isFile()) {
//                String name = file.getName();
//                System.out.println(name);
//            }
//        }
//        System.out.println(seqIds.size());
    }

    private static Map<String, String> buildSourceMap (String sourceName) {
        Map<String, String> ret = new HashMap<>();
        int c = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(basePath + sourceName))) {

            String line;
            while ((line = br.readLine()) != null) {
                c++;
                String[] split = line.split("\\s+");
                if (split.length != 3) {
                    continue;
                }
                ret.put(split[0], split[1] + "|" + split[2]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(c);
        return ret;
    }


    private static void readFileLineByLine (String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(basePath + fileName))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split("\\|+");
                if (split.length != 6 || !split[3].equals("签约成功")) {
                    System.out.println(Arrays.toString(split));
                    continue;
                }
                String seqNo = split[0];
                String cardNo = split[1];
                String name = split[2];
                if (!seqIds.containsKey(seqNo)) {
                    continue;
                }
                String userIdBindId = seqIds.get(seqNo);
                String finalStr = userIdBindId + "|" + cardNo + "|" + name;
                System.out.println(finalStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }







}
