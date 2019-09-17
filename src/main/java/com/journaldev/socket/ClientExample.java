
package com.journaldev.socket;

import com.google.common.base.Strings;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This class implements java socket client
 * @author pankaj
 *
 */
public class ClientExample {

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
        //get the localhost IP address, if server is running on some other IP, you need to use that
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        for(int i=0; i<5;i++){
            //establish socket connection to server
            socket = new Socket(host.getHostName(), 9876);
            //write to socket using ObjectOutputStream
            oos = new ObjectOutputStream(socket.getOutputStream());
            InputStream is = socket.getInputStream();
            while (true) {
                System.out.println("Sending request to Socket Server");
                if(i==4)oos.writeObject("exit");
                else oos.writeObject(""+i);
                //read the server response message
                System.out.println("read in client");
                int count = is.read();
                System.out.println("Message_count: " + count);
            }
        }
    }

    private String deleteExSignId(String oriSignId, RmExpireTokenRequest request) {
        Map<String, String> oriSignIdMap = SignIdUtils.stringToMap(oriSignId);

        if (oriSignIdMap.containsKey(request.getFundChannelCode()) && request.getSignNo().equals(oriSignIdMap.get(request.getFundChannelCode()))) {
            oriSignIdMap.remove(request.getFundChannelCode());
        }
        return SignIdUtils.mapToString(oriSignIdMap);

    }

    @Test
    public void testRmExTlk() {
        RmExpireTokenRequest req = new RmExpireTokenRequest("UPOP30110", "6235240000063391953");
        String originSignId = "{UPOP10110=6235240000063391953&UPOP30110=6235240000063391953}";
        String s = deleteExSignId(originSignId, req);
        System.out.println(s);
    }

    @Test
    public void testRandomStr() {
        String s = RandomStringUtils.randomNumeric(17);
        System.out.println(s);
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = dayFormat.format(new Date());
        System.out.println(format);
    }
}

class RmExpireTokenRequest {
    private String fundChannelCode;
    private String signNo;

    public RmExpireTokenRequest(String fundChannelCode, String signNo) {
        this.fundChannelCode = fundChannelCode;
        this.signNo = signNo;
    }

    public String getFundChannelCode() {
        return fundChannelCode;
    }

    public String getSignNo() {
        return signNo;
    }
}

class SignIdUtils {
    public SignIdUtils() {
    }

    public static boolean isSignIdEmpty(String signId) {
        return StringUtils.isBlank(signId) || !signId.startsWith("{") || !signId.endsWith("}");
    }

    public static String mapToString(Map<String, String> map) {
        StringBuffer buf = new StringBuffer();
        buf.append("{");
        Iterator<Map.Entry<String, String>> i = map.entrySet().iterator();
        boolean hasNext = i.hasNext();

        while(hasNext) {
            Map.Entry<String, String> e = (Map.Entry)i.next();
            String key = (String)e.getKey();
            String value = StringUtils.isBlank((CharSequence)e.getValue()) ? "" : (String)e.getValue();
            buf.append(key);
            buf.append("=");
            buf.append(value);
            hasNext = i.hasNext();
            if (hasNext) {
                buf.append("&");
            }
        }

        buf.append("}");
        return buf.toString();
    }

    public static Map<String, String> stringToMap(String mapText) {
        if (StringUtils.isBlank(mapText)) {
            return new HashMap();
        } else {
            mapText = mapText.substring(1, mapText.length() - 1);
            Map<String, String> map = new HashMap();
            String[] text = mapText.split("&");
            String[] var3 = text;
            int var4 = text.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String str = var3[var5];
                if (!StringUtils.isBlank(str)) {
                    String[] keyText = str.split("=");
                    String key = "";
                    String value = "";
                    if (keyText.length >= 1) {
                        if (keyText.length == 1) {
                            key = keyText[0];
                        } else {
                            key = keyText[0];
                            value = keyText[1];
                        }

                        map.put(key, value);
                    }
                }
            }

            return map;
        }
    }

    public static String mergeMapString(String source, String increase) {
        Map<String, String> sourceMap = stringToMap(source);
        Map<String, String> increaseMap = stringToMap(increase);
        sourceMap.putAll(increaseMap);
        return mapToString(sourceMap);
    }

    public static String convertSignNo(String channel, String signNo) {
        if (Strings.isNullOrEmpty(channel)) {
            return "{}";
        } else {
            HashMap<String, String> map = new HashMap();
            map.put(channel, signNo);
            return mapToString(map);
        }
    }

}

