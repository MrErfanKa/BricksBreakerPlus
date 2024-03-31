package game.bricksbreakerplus;

import javafx.scene.paint.Color;

public class Resource {
    public static String name, difficulty;
    public static Color color;
    public static boolean showArrows = true, save = true, playMusic = true;

    public static boolean isPlayMusic() {
        return playMusic;
    }

    public static void setPlayMusic(boolean playMusic) {
        Resource.playMusic = playMusic;
    }

    public static boolean isSave() {
        return save;
    }

    public static void setSave(boolean save) {
        Resource.save = save;
    }

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
