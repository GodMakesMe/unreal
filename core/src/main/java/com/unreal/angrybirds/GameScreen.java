package com.unreal.angrybirds;
import java.io.*;

// Example class with serialization
public class GameScreen implements Serializable {
    private static final long serialVersionUID = 1L; // Recommended for Serializable classes

    private int score;
    private transient String nonSerializableData; // Not serialized, restored as null/default

    public GameScreen(int score) {
        this.score = score;
        this.nonSerializableData = "Temp Data";
    }

    public void display() {
        System.out.println("Score: " + score + ", Non-Serializable Data: " + nonSerializableData);
    }

    // Serialize method
    public static void saveGameScreen(GameScreen screen, String fileName) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(screen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Deserialize method
    public static GameScreen loadGameScreen(String fileName) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return (GameScreen) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
