package PersistenceManagement;

import GameState.Board;

import java.io.*;

/**
 * Created by Austin Seber2 on 11/13/2016.
 */
public class LoadBoard {

    public static Board loadFromFile(String filename) {

        File loadFile = new File(filename + ".dat");
        Board board = null;

        try {

            ObjectInputStream reader = new ObjectInputStream(new FileInputStream(loadFile));
            board = (Board) reader.readObject();
            reader.close();

        } catch (ClassNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return board;

    }

}
