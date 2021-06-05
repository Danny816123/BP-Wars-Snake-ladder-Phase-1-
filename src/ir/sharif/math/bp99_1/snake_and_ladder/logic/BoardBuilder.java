package ir.sharif.math.bp99_1.snake_and_ladder.logic;

import ir.sharif.math.bp99_1.snake_and_ladder.model.Board;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Color;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Wall;
import ir.sharif.math.bp99_1.snake_and_ladder.model.prizes.Prize;
import ir.sharif.math.bp99_1.snake_and_ladder.model.transmitters.Transmitter;

import java.util.HashMap;
import java.util.Scanner;


public class BoardBuilder {
    Scanner beholder;

    public BoardBuilder(Scanner scanner) {
        this.beholder = scanner;
    }

    /**
     * give you a string in constructor.
     * <p>
     * you should read the string and create a board according to it.
     */
    public Board build() {
        Board myBoard = new Board();
        this.beholder.next();
        int x = this.beholder.nextInt();
        int y = this.beholder.nextInt();
        this.beholder.nextLine();
        for (int i = 1; i <= x; i++){
            for (int j = 1; j <= y; j++){
                String q = this.beholder.next();
                myBoard.getCells().add(new Cell(Color.valueOf(q), i, j));
            }
            this.beholder.nextLine();
        }
        this.beholder.nextLine();
        this.beholder.next();
        int stcellnum = this.beholder.nextInt();
        this.beholder.nextLine();
        for (int k = 0; k < stcellnum; k++){
            int xx = this.beholder.nextInt();
            int yy = this.beholder.nextInt();
            int pnum = this.beholder.nextInt();
            if (pnum == 1){
                myBoard.getStartingCells().put(myBoard.getCell(xx, yy), 1);
            }else {
                myBoard.getStartingCells().put(myBoard.getCell(xx, yy), 2);
            }
            this.beholder.nextLine();
        }
        this.beholder.nextLine();
        this.beholder.next();
        int wallnum = this.beholder.nextInt();
        this.beholder.nextLine();
        for (int t = 0; t < wallnum; t++){
            int x1 = this.beholder.nextInt();
            int y1 = this.beholder.nextInt();
            int x2 = this.beholder.nextInt();
            int y2 = this.beholder.nextInt();
            myBoard.getWalls().add(new Wall(myBoard.getCell(x1, y1), myBoard.getCell(x2, y2)));
            this.beholder.nextLine();
        }
        this.beholder.nextLine();
        this.beholder.next();
        int snakenum = this.beholder.nextInt();
        this.beholder.nextLine();
        for (int u = 0; u < snakenum; u++){
            int x11 = this.beholder.nextInt();
            int y11 = this.beholder.nextInt();
            int x22 = this.beholder.nextInt();
            int y22 = this.beholder.nextInt();
            Transmitter trans = new Transmitter(myBoard.getCell(x11, y11), myBoard.getCell(x22, y22));
            myBoard.getTransmitters().add(trans);
            myBoard.getCell(x11, y11).setTransmitter(trans);
            myBoard.getCell(x22, y22).setTransmitter(trans);
            this.beholder.nextLine();
        }
        this.beholder.nextLine();
        this.beholder.next();
        int prizenum = this.beholder.nextInt();
        this.beholder.nextLine();
        for (int e = 0; e < prizenum; e++){
            int xxx = this.beholder.nextInt();
            int yyy = this.beholder.nextInt();
            int s = this.beholder.nextInt();
            int c = this.beholder.nextInt();
            int n = this.beholder.nextInt();
            myBoard.getCell(xxx, yyy).setPrize(new Prize(myBoard.getCell(xxx, yyy), s, c, n));
            this.beholder.nextLine();
        }

        return myBoard;
    }



}
