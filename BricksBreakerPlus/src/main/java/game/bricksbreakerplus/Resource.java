package game.bricksbreakerplus;

import javafx.scene.paint.Color;

public class Resource {
    public static String name, difficulty;
    public static Color color;
    public static boolean showArrows;

    public static boolean isShowArrows() {
        return showArrows;
    }


    public static String getName() {
        return name;
    }

    public static String getDifficulty() {
        return difficulty;
    }

    public static Color getColor() {
        return color;
    }

    public static void setName(String name) {
        Resource.name = name;
    }

    public static void setDifficulty(String difficulty) {
        Resource.difficulty = difficulty;
    }

    public static void setColor(Color color) {
        Resource.color = color;
    }

    public static void setShowArrows(boolean showArrows) {
        Resource.showArrows = showArrows;
    }
}
