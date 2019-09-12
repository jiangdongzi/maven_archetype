package com.xiaomi.cashpay.async.service;

public class AClazz {
    private int a;
    private BClazz bClazz;
    public BClazz getbClazz() {
        return bClazz;
    }

    public void init() {
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setbClazz(BClazz bClazz) {
        this.bClazz = bClazz;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }
}
