/**
 * Word class
 * Holds a single word in a String object
 * Contains some String type functionality methods
 *
 * @author Mert Alp Taytak
 * @version prototype-1
 */

package normalizer;

public class Word implements Comparable<Word>
{
	// PROPERTIES

	String word;
	int length;

	// CONSTRUCTORS

	/**
	 * Default constructor with no parameters
	 */
	Word()
	{
		word = "";
		length = 0;
	}

	/**
	 * Default constructor with a string parameter
	 *
	 * @param word String to keep in this Word
	 */
	Word(String word)
	{
		this.word = word;
		length = word.length();
	}

	/**
	 * Copy constructor that creates a complete copy
	 *
	 * @param word Other word to copy into a new one
	 */
	Word(Word word)
	{
		this.word = word.word;
		this.length = word.length;
	}

	// METHODS

	// Functional methods

	public String toString()
	{
		return word;
	}

	/**
	 * Implementation of the Comparable interface
	 *
	 * @param word Word to compare to this Word
	 * @return Result of string compareTo between string
	 * properties of the two words
	 * @exception NullPointerException if input is NULL
	 */
	@Override
	public int compareTo(Word word)
	{
		try {
			return (this.getLowerCase()).compareTo(word.getLowerCase());
		} catch (NullPointerException e) {
			System.out.println("NullPointerException in Word.compareTo method.");
			return 0;
		}
	}

	/**
	 * Checks if this Word and other Word is equal
	 * in their string properties
	 *
	 * @param word Other Word to compare to this Word
	 * @return True if they are equal, false if not
	 */
	boolean equals(Word word)
	{
		return this.word.equals(word.word);
	}

	char getCharAt(int index)
	{
		return word.charAt(index);
	}

	String getLowerCase()
	{
		return word.toLowerCase();
	}

	String getUpperCase()
	{
		return word.toUpperCase();
	}

	String getSubstring(int firstIndex)
	{
		try {
			return word.substring(firstIndex);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("IndexOutOfBoundsException in the Word.getSubstring method.");
			return null;
		}
	}

	String getSubstring(int firstIndex, int lastIndex)
	{
		try {
			return word.substring(firstIndex, lastIndex);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("IndexOutOfBoundsException in the Word.getSubstring method.");
			return null;
		}
	}

	// Property setters and getters

	String getWord()
	{
		return word;
	}

	int getLength()
	{
		return length;
	}

	void setWord(String word)
	{
		this.word = word;
		length = word.length();
	}

	void setWord(Word word)
	{
		this.word = word.word;
		this.length = word.length;
	}
}
