package ir.sharif.math.bp99_1.snake_and_ladder.logic;


import ir.sharif.math.bp99_1.snake_and_ladder.graphic.GraphicalAgent;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Board;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Cell;
import ir.sharif.math.bp99_1.snake_and_ladder.model.GameState;
import ir.sharif.math.bp99_1.snake_and_ladder.model.Player;
import ir.sharif.math.bp99_1.snake_and_ladder.model.pieces.Piece;

import java.util.ArrayList;

/**
 * This class is an interface between logic and graphic.
 * some methods of this class, is called from graphic.
 * DO NOT CHANGE ANY PART WHICH WE MENTION.
 */
public class LogicalAgent {
    private final ModelLoader modelLoader;
    private final GraphicalAgent graphicalAgent;
    private final GameState gameState;

    /**
     * DO NOT CHANGE CONSTRUCTOR.
     */
    public LogicalAgent() {
        this.graphicalAgent = new GraphicalAgent(this);
        this.modelLoader = new ModelLoader();
        this.gameState = loadGameState();
    }


    /**
     * NO CHANGES NEEDED.
     */
    private GameState loadGameState() {
        Board board = modelLoader.loadBord();
        Player player1 = modelLoader.loadPlayer(graphicalAgent.getPlayerNames(1), 1);
        Player player2;
        do {
            player2 = modelLoader.loadPlayer(graphicalAgent.getPlayerNames(2), 2);
        } while (player1.equals(player2));
        player1.setRival(player2);
        player2.setRival(player1);
        return new GameState(board, player1, player2);
    }

    /**
     * NO CHANGES NEEDED.
     */
    public void initialize() {
        graphicalAgent.initialize(gameState);
    }

    /**
     * Give a number from graphic,( which is the playerNumber of a player
     * who clicks "ReadyButton".) you should somehow change that player state.
     * if both players are ready. then start the game.
     */
    public void readyPlayer(int playerNumber) {
        gameState.getPlayer(playerNumber).setReady(!gameState.getPlayer(playerNumber).isReady());
        if (gameState.getPlayer1().isReady() && gameState.getPlayer2().isReady()){
            gameState.setTurn();
            ArrayList<Cell> player1cells = new ArrayList<>();
            ArrayList<Cell> player2cells = new ArrayList<>();
            for (Cell i : gameState.getBoard().getStartingCells().keySet()){
                if (gameState.getBoard().getStartingCells().get(i) == 1) player1cells.add(i);
            }
            for (Cell j : gameState.getBoard().getStartingCells().keySet()){
                if (gameState.getBoard().getStartingCells().get(j) == 2) player2cells.add(j);
            }
            for (Cell a : player1cells){
                for (Piece aa : gameState.getPlayer1().getPieces()){
                    if (a.getColor().equals(aa.getColor())){
                        aa.setCurrentCell(a);
                        aa.setCurrentBourd(gameState.getBoard());
                        a.setPiece(aa);

                    }
                }
            }
            for (Cell b : player2cells){
                for (Piece bb : gameState.getPlayer2().getPieces()){
                    if (b.getColor().equals(bb.getColor())){
                        bb.setCurrentCell(b);
                        bb.setCurrentBourd(gameState.getBoard());
                        b.setPiece(bb);
                    }
                }
            }
//            for (int w = 0; w < 4; w++){
//                gameState.getPlayer1().getPieces().get(w).setCurrentCell(player1cells.get(w));
//                player1cells.get(w).setPiece(gameState.getPlayer1().getPieces().get(w));
//                gameState.getPlayer1().getPieces().get(w).setCurrentBourd(gameState.getBoard());
//                gameState.getPlayer2().getPieces().get(w).setCurrentCell(player2cells.get(w));
//                player2cells.get(w).setPiece(gameState.getPlayer2().getPieces().get(w));
//                gameState.getPlayer2().getPieces().get(w).setCurrentBourd(gameState.getBoard());
//            }
        }
        // dont touch this line
        graphicalAgent.update(gameState);
    }

    /**
     * give x,y (coordinates of a cell) :
     * you should handle if user want to select a piece
     * or already selected a piece and now want to move it to a new cell
     */
    // ***
    public void selectCell(int x, int y) {
        Cell mycell = gameState.getBoard().getCell(x, y);
        if (mycell.getPiece() != null){
            Player thatplayer = gameState.getCurrentPlayer();
            if (mycell.getPiece().getPlayer().equals(thatplayer)){
                if (mycell.getPiece().isSelected()){
                    thatplayer.setSelectedPiece(null);
                    mycell.getPiece().setSelected(false);
                }
                else {
                    thatplayer.setSelectedPiece(mycell.getPiece());
                    mycell.getPiece().setSelected(true);
                }
            }
        }else {
            for (Cell h : gameState.getBoard().getCells()){
                if(h.getPiece() != null){
                    if (h.getPiece().isSelected()){
                        if (h.getPiece().isValidMove(mycell, h.getPiece().getPlayer().getMoveLeft())){
                            gameState.nextTurn();
                            gameState.setTurn();
                            h.getPiece().moveTo(mycell);
                        }
                        break;
                    }
                }
            }
        }
        // dont touch this line
        graphicalAgent.update(gameState);
        checkForEndGame();
    }

    /**
     * check for endgame and specify winner
     * if player one in winner set winner variable to 1
     * if player two in winner set winner variable to 2
     * If the game is a draw set winner variable to 3
     */
    private void checkForEndGame() {
        if (gameState.getTurn() == 30) {
            // game ends
            int winner;
            if (gameState.getPlayer1().getScore() > gameState.getPlayer2().getScore()) winner = 1;
            else if (gameState.getPlayer1().getScore() < gameState.getPlayer2().getScore()) winner = 2;
            else winner = 3;
            // dont touch it
            graphicalAgent.playerWin(winner);
            /* save players*/
            modelLoader.savePlayer(gameState.getPlayer1());
            modelLoader.savePlayer(gameState.getPlayer2());
            modelLoader.archive(gameState.getPlayer1(), gameState.getPlayer2());
            LogicalAgent logicalAgent = new LogicalAgent();
            logicalAgent.initialize();
        }
    }


    /**
     * Give a number from graphic,( which is the playerNumber of a player
     * who left clicks "dice button".) you should roll his/her dice
     * and update *****************
     */
    public void rollDice(int playerNumber) {
        Player thatplayer = gameState.getPlayer(playerNumber);
        if (thatplayer.equals(gameState.getCurrentPlayer())){
            if (!thatplayer.isDicePlayedThisTurn()){
                thatplayer.setMoveLeft(thatplayer.getDice().roll());
                thatplayer.setDicePlayedThisTurn(true);
                if (thatplayer.getMoveLeft() == 6) thatplayer.applyOnScore(4);
                if (!thatplayer.hasMove(gameState.getBoard(), thatplayer.getMoveLeft())){
                    thatplayer.applyOnScore(-3);
                    gameState.nextTurn();
                    gameState.setTurn();
                    checkForEndGame();
                }
            }
        }
        // dont touch this line
        graphicalAgent.update(gameState);
    }


    /**
     * Give a number from graphic,( which is the playerNumber of a player
     * who right clicks "dice button".) you should return the dice detail of that player.
     * you can use method "getDetails" in class "Dice"(not necessary, but recommended )
     */
    public String getDiceDetail(int playerNumber) {return gameState.getPlayer(playerNumber).getDice().getDetails();}
}
