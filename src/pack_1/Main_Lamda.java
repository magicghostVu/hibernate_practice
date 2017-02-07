package pack_1;

import java.util.function.IntPredicate;

/**
 * Created by magic_000 on 08/02/2017.
 */
public class Main_Lamda {
    public static void main(String[] args) {
        Functionalnterface fi= (String s)-> s.length();

        int l=fi.getStringLength("ajsdk");


        IntPredicate i= (index) -> index%2 ==0;

        System.out.println(i);



    }
}
