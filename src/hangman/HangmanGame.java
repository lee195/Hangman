package hangman;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import hangman.HangmanDict;

/**
 * HangmanGame
 */
public class HangmanGame {

    public static void main(String[] args) {
    	init();
        run();
        // shouldn't be reached, but just making sure
        System.exit(0);
    }

    private static String secret;
    private static int lifeCount;
    private static ArrayList<Integer> revealed;
    private static ArrayList<String> correctGuesses;
    private static ArrayList<String> wrongGuesses;
    
    private static void init() {
    	revealed = new ArrayList<Integer>();
    	correctGuesses = new ArrayList<String>();
    	wrongGuesses = new ArrayList<String>();
    }

    public static void run() {
        HangmanDict lex = new HangmanDict();
        resetGame(lex);
        ArrayList<Integer> newlyRevealed = new ArrayList<>();
        
        printStartMessage();
        
        // Outer loop serves to ignore NoSuchElementException of the Scanner
        while (true) {
            try (Scanner in = new Scanner(System.in)) {
            	// keep repeating the game with random words until exit signal
                while (true) {
                    // print current game state before each guess
                    printGameState();
                    // prompt user input
                    System.out.print("Your guess: ");
                    String guess = in.nextLine();
                    // handle exit signal
                    if (guess.equals("exit")) {
                        System.out.println("Exiting game now.");
                        in.close();
                        System.exit(0);
                    }
                    // make guess processing is case insensitive
                    guess = guess.toLowerCase();

                    newlyRevealed = checkGuess(guess);
                    if (newlyRevealed == null) {
                    	System.out.println("Whops. Something went wrong.");
                        continue;
                    } else if (newlyRevealed.size() == 0) {
                    	wrongGuesses.add(guess);
                        lifeCount--;
                    } else if (correctGuesses.contains(guess)) {
                        continue;
                    } else {
                        correctGuesses.add(guess);
                        revealed.addAll(newlyRevealed);
                    }

                    if (revealed.size() == secret.length()) {
                        printWinMessage();
                        resetGame(lex);
                    }

                    if (lifeCount == 0) {
                        printLoseMessage();
                        resetGame(lex);
                    }
                }
            } catch (NoSuchElementException nsee) {
                continue;
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(2);
            }

        }
    }
    
    private static void printStartMessage() {
    	System.out.println("Welcome to Hangman.");
    	System.out.println("Type a letter and press enter to make a guess.");
    	System.out.println("Type \"exit\" to exit the game.");
    }
    
    private static void printWinMessage() {
    	System.out.println(secret + " has been guessed correctly!");
        System.out.println("You had " + String.valueOf(lifeCount) + " lives remaining.");
        System.out.println("Congratulations, you won!");
        System.out.println("Starting new game.");
    }
    
    private static void printLoseMessage() {
    	System.out.println("You lost!");
        System.out.println("The word was " + secret + ".");
        System.out.println("Starting new game.");
    }
    
    private static void resetGame(HangmanDict lex) {
        secret = lex.getRandWord();
        lifeCount = 8;
        revealed.clear();
        correctGuesses.clear();
        wrongGuesses.clear();
    }
    
    /**
     * Checks a guess against the secret.
     * @param guess : current guess of the player
     * @return : a list of the indices for which guess matches a char in secret
     */
    private static ArrayList<Integer> checkGuess(String guess) {
        // input sanitation
        if (guess.length() > 1 || !guess.matches("[a-z]")) {
            System.out.println("Input must be single character.");
            return null;
        }
        // add all indices for which the guess matches a char in the secret
        ArrayList<Integer> indices = new ArrayList<>();
        int curr = secret.indexOf(guess);
        while (curr >= 0) {
            indices.add(curr);
            curr = secret.indexOf(guess, curr + 1);
        }

        return indices;
    }

    private static void printGameState() {
        System.out.println("---------------------------------------------------");
        // current shows the correctly guessed chars and "-" otherwise
        String current = "";
        for (int i = 0; i < secret.length(); i++) {
            if (revealed.contains(i)) {
                current += secret.charAt(i);
            } else {
                current += "-";
            }
        }
        System.out.println("Current: " + current);
        System.out.println("Lives remaining: " + String.valueOf(lifeCount));
        System.out.println("Correct guesses: " + correctGuesses.toString());
        System.out.println("Wrong guesses: " + wrongGuesses.toString());
    }
}