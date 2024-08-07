import Crypt.*;
import DataEngine.*;
import JsonHandler.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] Args){
        EncryptAlgorithm c = new Picarra(16);
        JsonHandler j = new SimpleJsonHandler();
        DataEngine eng = new DataEngine(j, c, "key4567890123456");

        ArrayList<Entry> entries = eng.open("teste.json");

        System.out.println(entries);

        eng.save("teste.json", entries);
    }
}
