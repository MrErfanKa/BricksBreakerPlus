package game.bricksbreakerplus.saveAndLoad;

import game.bricksbreakerplus.controls.StartControl;
import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class Saver {
    public static int size = 0;
    public static ArrayList<Pair<Pair<String, String>, Pair<Integer, String>>> games = new ArrayList<>();
    public static File file = new File("src/main/java/game/bricksbreakerplus/saveAndLoad/file.txt");
    public static void add(String name, int score, String difficulty){
        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        games.add(new Pair<>(new Pair<>(name, currentDate), new Pair<>(score, difficulty)));
    }
    public static void save() throws FileNotFoundException {
        PrintStream printStream = new PrintStream(file);
//        printStream.println(games.size());
        for(Pair pair : games) {
            printStream.println(((Pair)pair.getKey()).getKey());
            printStream.println(((Pair)pair.getKey()).getValue());
            printStream.println(((Pair)(pair.getValue())).getKey());
            printStream.println(((Pair)(pair.getValue())).getValue());
        }
    }
    public static void load() throws FileNotFoundException {
        Scanner scanner = new Scanner(file);

        games.clear();
        while (scanner.hasNext()){
            String name = scanner.nextLine();
            String date = scanner.nextLine();
            int score = Integer.parseInt(scanner.nextLine());
            String dif = scanner.nextLine();
            games.add(new Pair<>(new Pair<>(name, date), new Pair<>(score, dif)));
//            StartControl.update(name, score, dif);
        }
//        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//        System.out.println(currentDate);
//        System.out.println(games);
    }
}
