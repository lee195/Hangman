package hangman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


/**
 * Class representing a dictionary for the HangmanGame.
 * Words were chosen from https://github.com/mrdziuban/Hangman/blob/master/dictionary.txt
 * @author jisu
 * 
 */
public class HangmanDict {
	private final List<String> wordList = new ArrayList<>(
			Arrays.asList(
					"abstraction",
				    "bartender",
				    "conclusion",
				    "doorstep",
				    "electronics",
				    "firewall",
				    "guidance",
				    "henchman",
				    "ideology",
				    "joker",
				    "kickoff",
				    "lambda",
				    "mislead",
				    "narrow",
				    "odor",
				    "permanent",
				    "quadruple",
				    "robot",
				    "shift",
				    "travel",
				    "unbalanced",
				    "vineyard",
				    "weather",
				    "xenon",
				    "yellow",
				    "zoom"
					));
	
	/**
	 * @return : A random word from this.wordList
	 */
	public String getRandWord() {
		//storing the index is not necessary, but it's nicer to read
		int randIndex = ThreadLocalRandom.current().nextInt(0, this.wordList.size());
		return this.wordList.get(randIndex);
	}
	
}
