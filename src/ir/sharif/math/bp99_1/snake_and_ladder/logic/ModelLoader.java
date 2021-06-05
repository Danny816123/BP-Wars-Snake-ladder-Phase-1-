package ir.sharif.math.bp99_1.snake_and_ladder.logic;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Board;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;
import ir.sharif.math.bp99_1.snake_and_ladder.util.Config;

import java.io.*;
import java.util.Scanner;

public class ModelLoader {
    private final File boardFile, playersDirectory, archiveFile;


    /**
     * DO NOT CHANGE ANYTHING IN CONSTRUCTOR.
     */
    public ModelLoader() {
        boardFile = Config.getConfig("mainConfig").getProperty(File.class, "board");
        playersDirectory = Config.getConfig("mainConfig").getProperty(File.class, "playersDirectory");
        archiveFile = Config.getConfig("mainConfig").getProperty(File.class, "archive");
        if (!playersDirectory.exists()) playersDirectory.mkdirs();
    }


    /**
     * read file "boardFile" and craete a Board
     * <p>
     * you can use "BoardBuilder" class for this purpose.
     * <p>
     * pay attention add your codes in "try".
     */
    public Board loadBord() {
        try {
            Scanner beholder = new Scanner(boardFile);
            BoardBuilder carpenter = new BoardBuilder(beholder);
            Board boardy = carpenter.build();
            if (boardy != null)
                return boardy;
            return null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("could not find board file");
            System.exit(-2);
        }
        return null;
    }

    /**
     * load player.
     * if no such a player exist, create an account(file) for him/her.
     * <p>
     * you can use "savePlayer" method of this class for that purpose.
     * <p>
     * add your codes in "try" block .
     */
    public Player loadPlayer(String name, int playerNumber) {
        try {
            File playerFile = getPlayerFile(name);

            if (playerFile != null){
                Scanner scanner = new Scanner(playerFile);
                scanner.nextLine();
                int emtiaz = scanner.nextInt();
                return new Player(name, emtiaz, 0, playerNumber);
            }else {
                Player newp = new Player(name, 0, 0, playerNumber);
                savePlayer(newp);
                return newp;
            }
        } catch (FileNotFoundException | IllegalArgumentException e) {
            e.printStackTrace();
            System.err.println("could not find player file");
            System.exit(-2);
        }
        return null;
    }

    /**
     * if player does not have a file, create one.
     * <p>
     * else update his/her file.
     * <p>
     * add your codes in "try" block .
     */
    public void savePlayer(Player player) {
        try {
            // add your codes in this part
            File file = getPlayerFile(player.getName());
            if (file == null){
                file = new File("playersDirectory\\" + player.getName() + ".txt");
                file.createNewFile();
            }
            PrintStream printStream = new PrintStream(new FileOutputStream(file));
            printStream.println(player.getName());
            printStream.println(player.getScore());
            printStream.flush();
            printStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("could not find player file");
            System.exit(-2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * give you a name (player name), search for its file.
     * return the file if exist.
     * return null if not.
     */
    private File getPlayerFile(String name) {
        File pfile = new File("playersDirectory\\" + name + ".txt");
        if (pfile.exists()) return pfile;
        else return null;
    }

    /**
     * at the end of the game save game details
     */
    public void archive(Player player1, Player player2) {
        try {
            // add your codes in this part
            PrintStream printStream = new PrintStream(new FileOutputStream(archiveFile, true));
            printStream.println(player1.getName());
            printStream.println(player1.getScore());
            printStream.println(player2.getName());
            printStream.println(player2.getScore());
            if (player1.getScore() > player2.getScore()){
                printStream.println(player1.getName());
            } else if (player1.getScore() < player2.getScore()){
                printStream.println(player2.getName());
            } else printStream.println("Draw");
            printStream.flush();
            printStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    //public static void main(String[] args) {
    //    ModelLoader bruh = new ModelLoader();
    //    if (bruh.loadBord() == null) System.out.println("null");
    //    else System.out.println("ok");
    //}

}
