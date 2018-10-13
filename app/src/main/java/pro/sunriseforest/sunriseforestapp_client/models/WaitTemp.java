package pro.sunriseforest.sunriseforestapp_client.models;


import java.util.concurrent.TimeUnit;


public class WaitTemp {

    public static void delay() {
        try {
            int a = (int) (Math.random()*1);
            TimeUnit.SECONDS.sleep(a);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

