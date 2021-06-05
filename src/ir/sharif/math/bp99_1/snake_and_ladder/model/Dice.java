package ir.sharif.math.bp99_1.snake_and_ladder.model;


import java.util.ArrayList;
import java.util.Random;

public class Dice {
    /**
     * add some fields to store :
     * 1) chance of each dice number ( primary chance of each number, should be 1 )
     * currently our dice has 1 to 6.
     * 2) generate a random number
     * <p>
     * initialize these fields in constructor.
     */
    int[] diceField = new int[7];
    int p;
    public Dice() {
        diceField[1] = 1;
        diceField[2] = 1;
        diceField[3] = 1;
        diceField[4] = 1;
        diceField[5] = 1;
        diceField[6] = 1;
    }
    /**
     * create an algorithm generate a random number(between 1 to 6) according to the
     * chance of each dice number( you store them somewhere)
     * return the generated number
     */
    public int roll() {
        Random rand = new Random();
        ArrayList<Integer> diceList = new ArrayList<>();
        for (int i = 1; i < 7; i++){
            for (int j = 0; j < diceField[i]; j++){
                diceList.add(i);
            }
        }
        p = rand.nextInt(diceList.size() - 1);
        return diceList.get(p);
    }
    /**
     * give a dice number and a chance, you should UPDATE chance
     * of that number.
     * pay attention chance of none of the numbers must not be negetive(it can be zero)
     */
    public void addChance(int number, int chance) {
        if (diceField[number] + chance >= 0) diceField[number] += chance;
        else diceField[number] = 0;
    }
    /**
     * you should return the details of the dice number.
     * sth like:
     * "1 with #1 chance.
     * 2 with #2 chance.
     * 3 with #3 chance
     * .
     * .
     * . "
     * where #i is the chance of number i.
     */
    public String getDetails() {
        StringBuilder details = new StringBuilder();
        for (int i = 1; i < 7; i++){
            details.append(i).append(" ").append("with").append(" ").append("#").append(diceField[i]).append(" ").append("chance. ");
        }
        return details.toString();
    }
}
