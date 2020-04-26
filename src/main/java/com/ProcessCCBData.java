package com;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ProcessCCBData {

    private final static String basePath = "C:\\work\\temp\\ccbMigate\\";
    private static Map<String, String> seqIds;
    private static BufferedWriter bfw;
    private static final Set<String> filterSet = new HashSet<>();
    public static void main(String[] args) throws IOException {
        bfw = new BufferedWriter(new FileWriter(basePath + "processedSignIds"));

        String finalSourceName = "finalSource.txt";
        seqIds = buildSourceMap(finalSourceName);

        File[] files = new File(basePath + "signed_files\\utf8_signed_result").listFiles();
        System.out.println(files.length);
        for (File file : files) {
            if (file.isFile()) {
                String name = file.getName();
                System.out.println(name);
                readFileLineByLine(name);
            }
        }
        bfw.close();
    }

    private static Map<String, String> buildSourceMap (String sourceName) {
        Map<String, String> ret = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(basePath + sourceName))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split("\\s+");
                if (split.length != 3) {
                    continue;
                }
                ret.put(split[0], split[1] + "|" + split[2]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }


    private static void readFileLineByLine (String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(basePath + "signed_files\\utf8_signed_result\\" + fileName))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split("\\|+");
                if (split.length != 6 || !split[3].equals("签约成功")) {
                    continue;
                }
                String seqNo = split[0];
                String cardNo = split[1];
                String name = split[2];
                if (!seqIds.containsKey(seqNo) || filterSet.contains(seqNo)) {
                    continue;
                }
                filterSet.add(seqNo);
                String userIdBindId = seqIds.get(seqNo);
                String finalStr = userIdBindId + "|" + cardNo + "|" + name;
                bfw.write(finalStr);
                bfw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }







}
