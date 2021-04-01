package com.example.mywallet.utils;

public class WaitRequestResult implements Runnable {

    private final Object synchronizedObject;
    private boolean hasHadResponseDelivered;

    public WaitRequestResult(Object synchronizedObject) {
        this.synchronizedObject = synchronizedObject;
        hasHadResponseDelivered = false;
    }

    public void setHasHadResponseDelivered(boolean hasHadResponseDelivered) {
        this.hasHadResponseDelivered = hasHadResponseDelivered;
    }

    @Override
    public void run() {

        synchronized (synchronizedObject) {
            while (!hasHadResponseDelivered) {
                try {
                    synchronizedObject.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Passed");

    }
}
