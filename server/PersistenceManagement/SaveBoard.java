package PersistenceManagement;

import GameState.Board;

import java.io.*;

/**
 * Created by Austin Seber2 on 11/13/2016.
 */
public class SaveBoard {

    public static void saveToFile(String filename, Board board) {

        File saveFile = new File(filename + ".dat");

        try {

            ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(saveFile));
            writer.writeObject(board);
            writer.close();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}
