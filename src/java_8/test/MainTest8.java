package java_8.test;

/**
 * Created by magic_000 on 11/02/2017.
 */
public class MainTest8 {
    public static void main(String[] args) {
        new Thread(()->{
            System.out.println("Thread ran");
        }).start();

    }
}
