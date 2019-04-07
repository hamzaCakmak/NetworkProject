/*
 * Score.java
 *
 * Created on 25 January 2006, 12:16
 *
 * This works out the given score of an array of dice following
 * the yahtzee rule book
 *
 */
package game;

import java.util.*;
import java.util.stream.IntStream;

/**
 *
 * @author John Bartlett
 */
public class Score {

    public int score;

    /**
     * Creates a new instance of Score
     */
    public Score() {
    }

    /**
     *
     * Gets the score for the roll
     *
     * @return score
     *
     */
    public int getScore() {
        return score;
    }

    /**
     *
     * Checks wot score and what type of hand from a roll
     *
     * @param array contains roll values
     * @return String formatted message
     *
     */
    public String getHand(int[] diceSet) {

        String result = "";
        
        Arrays.sort(diceSet);

        if (isFullHouse(diceSet)) {
            result = "fullHouse";
            score = 25;
        } else if (isThreeOfAKind(diceSet)) {
            result = "threeOfaKind";
        } else if (isFourOfAKind(diceSet)) {
            result = "fourOfaKind";
        } else if (isYahtzee(diceSet)) {
            result = "yahtzee";
            score = 50;
        } else if (isStraight(diceSet)) {
            result = "straight";
            score = 40;
        } else if (isSmallStraight(diceSet)) {
            result = "smallStraight";
            score = 30;
        } else {
            result = "chance";
            chanceScore(diceSet);
        }

        return result;
    }

    /**
     * *********************************************************************
     * Set of Boolean methods checking different possiblilties
     *
     * three of a kind (3 dice of the same kind) score: sum of all the dice four
     * of a kind (4 dice of the same kind) score: sum of all the dice full house
     * (2 of one kind, 3 of another) score: 25 points small straight (4
     * consecutive numbers) score: 30 points straight (5 consecutive numbers)
     * score: 40 points Yahtzee (5 dice of the same kind) score: 50 points
     * Chance (any combination) score: sum of all the dice
     *
     **********************************************************************
     */
    private void chanceScore(int[] diceSet) {
        int sum = 0;
        for (int i : diceSet) {
            sum += i;
        }
        score = sum;
    }

    /**
     *
     * Checks to see whether is three of a kind e.g 55536
     *
     * @param array contains roll values
     * @return true if it is three of a kind
     *
     */
    private boolean isThreeOfAKind(int[] diceSet) {
        boolean threeOfAKind = false;
        int sum = 0;
        for (int i = 1; i <= 6; i++) {
            int count = 0;
            for (int j = 0; j < 5; j++) {
                if (diceSet[j] == i) {
                    count++;
                }
                if (count > 2) {
                    threeOfAKind = true;
                }
            }
        }

        if (threeOfAKind) {
            for (int k = 0; k < 5; k++) {
                sum += diceSet[k];
            }
        }
        score = sum;

        return threeOfAKind;

    }

    /**
     *
     * Checks to see whether is four of a kind e.g. 44443
     *
     * @param array contains roll values
     * @return true if is four of a kind
     *
     */
    private boolean isFourOfAKind(int[] diceSet) {

        boolean fourOfAKind = false;
        int sum = 0;
        for (int i = 1; i <= 6; i++) {
            int count = 0;
            for (int j = 0; j < 5; j++) {
                if (diceSet[j] == i) {
                    count++;
                }

                if (count > 3) {
                    fourOfAKind = true;
                }
            }
        }

        if (fourOfAKind) {
            for (int k = 0; k < 5; k++) {
                sum += diceSet[k];
            }
        }
        score = sum;

        return fourOfAKind;

    }

    /**
     *
     * checks to see whether is Yahtzee e.g. 55555
     *
     * @param array contains roll values
     * @return true if is yahtzee
     *
     */
    private boolean isYahtzee(int[] diceSet) {
        boolean flag = true;
        int first = diceSet[0];
        for (int i = 1; i < 5 && flag; i++) {
            if (diceSet[i] != first) {
                flag = false;
            }
        }
        return flag;

    }

    /**
     *
     * check to see whether is a straight e.g. 12345
     *
     * @param array contains roll values
     * @return true if is a striaght
     *
     */
    private boolean isStraight(int[] diceSet) {
        boolean result = false;

        if (diceSet[0] == 1
                && diceSet[1] == 2
                && diceSet[2] == 3
                && diceSet[3] == 4
                && diceSet[4] == 5) {
            result = true;
        }

        if (diceSet[0] == 2
                && diceSet[1] == 3
                && diceSet[2] == 4
                && diceSet[3] == 5
                && diceSet[4] == 6) {
            result = true;
        }
        
        score = 40;
        return result;
    }

    /**
     *
     * check to see is small straight e.g. 42345
     *
     * @param array contains roll values
     * @return true if is a small straight
     *
     */
    private boolean isSmallStraight(int[] diceSet) {

        int[] noDuplicates = IntStream.of(diceSet).distinct().toArray();
        int length = noDuplicates.length;
        Arrays.sort(noDuplicates);
        if(length < 4){
            return false;
        }
        else if(length == 4){
            return ((noDuplicates[3] == noDuplicates[2] + 1) && (noDuplicates[2] == noDuplicates[1] + 1)
                && (noDuplicates[1] == noDuplicates[0] + 1));
        }else{
        return ((noDuplicates[4] == noDuplicates[3] + 1) && (noDuplicates[3] == noDuplicates[2] + 1)
                && (noDuplicates[2] == noDuplicates[1] + 1));
        }

        

    }

    /**
     *
     * check to see if full house e.g. 33355
     *
     * @param array contains roll values
     * @return true if is a full house
     *
     */
    private boolean isFullHouse(int[] diceSet) {

        if ((((diceSet[0] == diceSet[1]) && (diceSet[1] == diceSet[2]))
                && (diceSet[3] == diceSet[4])
                && // Two of a Kind
                (diceSet[2] != diceSet[3])) // make sure they are of different values

                // or two of a kind and a three of a kind
                || ((diceSet[0] == diceSet[1])
                && // Two of a Kind
                // Three of a kind
                ((diceSet[2] == diceSet[3]) && (diceSet[3] == diceSet[4]))
                // make sure they are of different values
                && (diceSet[1] != diceSet[2]))) {
            return true;
        } else {
            return false;
        }
    }
}
