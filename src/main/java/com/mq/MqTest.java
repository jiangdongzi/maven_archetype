package com.mq;

import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MqTest {
//XW30113=&UPOP30110=6227002007510134766
    public static void main(String[] args) {
        String signId = "{XW30113=&UPOP30110=6227002007510134766}";
        Map<String, String> signIdMap = SignIdUtils.stringToMap(signId);
//        System.out.println(signIdMap.size());

        RmExpireTokenRequest req = new RmExpireTokenRequest();
        req.setFundChannelCode("UPOP30110");
        req.setFundChannelCode("UPOP30110");
        req.setSignNo("6227002007510134766");
        String s = deleteExSignId(signId, req);
        System.out.println(s);
    }


    private static String deleteExSignId(String oriSignId, RmExpireTokenRequest request) {
        Map<String, String> oriSignIdMap = SignIdUtils.stringToMap(oriSignId);

        if (oriSignIdMap.containsKey(request.getFundChannelCode()) && request.getSignNo().equals(oriSignIdMap.get(request.getFundChannelCode()))) {
            oriSignIdMap.remove(request.getFundChannelCode());
        }
        return SignIdUtils.mapToString(oriSignIdMap);

    }

}

class RmExpireTokenRequest {
    public String logId;
    public String signNo;
    public String fundChannelCode;
    public String cardNo;
    public long userId;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getSignNo() {
        return signNo;
    }

    public void setSignNo(String signNo) {
        this.signNo = signNo;
    }

    public String getFundChannelCode() {
        return fundChannelCode;
    }

    public void setFundChannelCode(String fundChannelCode) {
        this.fundChannelCode = fundChannelCode;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "RmExpireTokenRequest{" +
                "logId='" + logId + '\'' +
                ", signNo='" + signNo + '\'' +
                ", fundChannelCode='" + fundChannelCode + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", userId=" + userId +
                '}';
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