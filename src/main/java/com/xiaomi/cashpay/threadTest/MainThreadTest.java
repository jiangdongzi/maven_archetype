package com.xiaomi.cashpay.threadTest;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainThreadTest {

    @Test
    public void testThread() throws InterruptedException {

    }


    public static void main(String[] args) throws InterruptedException {
//        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
//        Constants.toBeStart.start();
//        Thread.sleep(1000);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
        int a = 1;
    }


    @Test
    public void testJsonParse() throws ParseException {
        ExpiredTokenInfo exTokInfo = new ExpiredTokenInfo();
        exTokInfo.setCardNo("6227002470170278192");
        exTokInfo.setFundChannelCode("UPOP30111");
        exTokInfo.setInstOrderNo("CCB");
        exTokInfo.setMemberId("10000007");
        exTokInfo.setSignNo("9098fafaggghh");
        Gson gson = new Gson();
        String s = gson.toJson(exTokInfo);
        System.out.println(s);
        ExpiredTokenInfo exInfo = gson.fromJson(s, ExpiredTokenInfo.class);
        System.out.println(exInfo);
    }

}

class ExpiredTokenInfo implements Serializable {
    private static final long serialVersionUID = 488114487378571824L;
    private String memberId;
    private String cardNo;
    private String signNo;
    private String fundChannelCode;
    private String instOrderNo;

    public ExpiredTokenInfo() {
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
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

    public String getInstOrderNo() {
        return instOrderNo;
    }

    public void setInstOrderNo(String instOrderNo) {
        this.instOrderNo = instOrderNo;
    }

    @Override
    public String toString() {
        return "ExpiredTokenInfo{" +
                "memberId='" + memberId + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", signNo='" + signNo + '\'' +
                ", fundChannelCode='" + fundChannelCode + '\'' +
                ", instOrderNo='" + instOrderNo + '\'' +
                '}';
    }
}

class LoopTask implements Runnable {
    private int c;

    @Override
    public void run() {
        while (true) {
            System.out.println("c = " + c);
        }
    }
}
