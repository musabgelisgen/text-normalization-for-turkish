/**
 * DerivedWord class
 * Holds a single word in a Word object
 * Contains word manipulation methods
 * that can be used to create new words
 * (as in "derived words")
 *
 * There are four derivation methods:
 * 1) Deletion:    Delete a single letter from the root word
 * 2) Insertion:   Add a single letter to the root word
 * 3) Replacement: Replace a single letter of the root word with another letter
 * 4) Swapping:    Swap the positions of two letters in the root word
 *
 * @author Mert Alp Taytak
 * @version prototype-1
 */

package normalizer;

// IMPORTS
import java.util.ArrayList;

public class DerivedWord implements Comparable<DerivedWord>
{
	// PROPERTIES

	Word word;

	// Turkish character set
	private static final char[] charSet =  {
				'a', 'b', 'c', 'ç', 'd',
				'e', 'f', 'g', 'ğ', 'h',
				'ı', 'i', 'j', 'k', 'l',
				'm', 'n', 'o', 'ö', 'p',
				'r', 's', 'ş', 't', 'u',
				'ü', 'v', 'y', 'z'
	};

	// Turkish Keyboard Matrix
	private static final char[][] keyboard = {
		{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '*', '-'},
		{'q', 'w', 'e', 'r', 't', 'y', 'u', 'ı', 'o', 'p', 'ğ', 'ü'},
		{'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'ş', 'i', ','},
		{'<', 'z', 'x', 'c', 'v', 'b', 'n', 'm', 'ö', 'ç', '.','\0'},
		{'\0','\0','\0',' ', ' ', ' ', ' ', ' ','\0','\0','\0','\0'}
	};

	private static final int NO_OF_KEY_ROWS = 5;
	private static final int NO_OF_KEY_COLS = 12;

	// How many derivations from the original root word to this one?
	int editDistance;

	// More specific information about the nature of the derivations
	int charactersDeleted;            // How many characters were deleted?
	int charactersInserted;           // How many characters were inserted?
	int characterReplacementDistance; // How close are the letters replaced on the keyboard?
	int characterSwapDistance;        // How far are the letters swapped on the word?

	// CONSTRUCTORS

	public DerivedWord()
	{
		// Call Word constructor
		word = new Word();

		// Set defaults
		setDefaults();
	}

	public DerivedWord(String word)
	{
		// Call Word constructor
		this.word = new Word(word);

		// Set defaults
		setDefaults();
	}

	public DerivedWord(Word word)
	{
		// Call Word copy constructor
		this.word = new Word(word);

		// Set defaults
		setDefaults();
	}

	/*
	 * METHODS
	 */

	public String toString()
	{
		return word.toString();
	}

	@Override
	public int compareTo(DerivedWord word)
	{
		// Edit distance is priority
		if (this.editDistance != word.editDistance)
		{
			return (this.editDistance - word.editDistance);
		}

		// Else lexicographic order is priority
		return (this.word.compareTo(word.getWord()));
	}

	// GET DERIVATIONS

	public static ArrayList<DerivedWord> getDerivations(DerivedWord word)
	{
		// Create the list to hold all the derivations
		ArrayList<DerivedWord> list = new ArrayList<DerivedWord>();

		// Add deletions
		list.addAll(getDeletions(word));

		// Add insertions
		list.addAll(getInsertions(word));

		// Add replacements
		list.addAll(getReplacements(word));

		// Add swaps
		list.addAll(getSwaps(word));

		// Return
		return list;
	}

	public static ArrayList<DerivedWord> getDerivations(Word word)
	{
		return getDerivations(new DerivedWord(word));
	}

	public static ArrayList<DerivedWord> getDerivations(String word)
	{
		return getDerivations(new DerivedWord(word));
	}

	/*
	 * DERIVATION METHODS
	 */

	// DELETIONS

	public static ArrayList<DerivedWord> getDeletions(DerivedWord word)
	{
		// Create the list to hold all the derivations
		ArrayList<DerivedWord> list = new ArrayList<DerivedWord>();

		// Temporary holder of derivations
		DerivedWord temp;

		// Copy the word and its properties
		Word root = word.getWord();
		int rootLength = root.getLength();

		for (int i = 0; i < rootLength; i++)
		{
			// Derivation is the concatenation of two substrings
			temp = new DerivedWord(root.getSubstring(0, i) + root.getSubstring(i + 1));

			// Change relevant derivation properties
			temp.editDistance += 1;
			temp.charactersDeleted += 1;

			// Add to list
			list.add(temp);
		}

		return list;
	}

	public static ArrayList<DerivedWord> getDeletions(Word word)
	{
		return getDeletions(new DerivedWord(word));
	}

	public static ArrayList<DerivedWord> getDeletions(String word)
	{
		return getDeletions(new DerivedWord(word));
	}

	// INSERTIONS

	public static ArrayList<DerivedWord> getInsertions(DerivedWord word)
	{
		// Create the list to hold all the derivations
		ArrayList<DerivedWord> list = new ArrayList<DerivedWord>();

		// Temporary holder of derivations
		DerivedWord temp;

		// Copy the word and its properties
		Word root = word.getWord();
		int rootLength = root.getLength();

		for (int i = 0; i < rootLength; i++)
		{
			for (int j = 0; j < charSet.length; j++)
			{
				// Derivation is the concatenation of two substrings with a character between
				temp = new DerivedWord(root.getSubstring(0, i) + charSet[j] + root.getSubstring(i));

				// Change relevant derivation properties
				temp.editDistance += 1;
				temp.charactersInserted += 1;

				// Add to list
				list.add(temp);
			}
		}

		return list;
	}

	public static ArrayList<DerivedWord> getInsertions(Word word)
	{
		return getInsertions(new DerivedWord(word));
	}

	public static ArrayList<DerivedWord> getInsertions(String word)
	{
		return getInsertions(new DerivedWord(word));
	}

	// REPLACEMENTS

	public static ArrayList<DerivedWord> getReplacements(DerivedWord word)
	{
		// Create the list to hold all the derivations
		ArrayList<DerivedWord> list = new ArrayList<DerivedWord>();

		// Temporary holder of derivations
		DerivedWord temp;

		// Copy the word and its properties
		Word root = word.getWord();
		int rootLength = root.getLength();

		for (int i = 0; i < rootLength; i++)
		{
			for (int j = 0; j < charSet.length; j++)
			{
				// Don't create duplicates, instead skip to next iteration
				if (root.getCharAt(i) == charSet[j])
				{
					continue;
				}

				// Derivation is the concatenation of two substrings with the character between replaced
				temp = new DerivedWord(root.getSubstring(0, i) + charSet[j] + root.getSubstring(i + 1));

				// Change relevant derivation properties
				temp.editDistance += 1;
				temp.characterReplacementDistance = getKeyDistance(root.getCharAt(i), charSet[j]);

				// Add to list
				list.add(temp);
			}
		}

		return list;
	}

	public static ArrayList<DerivedWord> getReplacements(Word word)
	{
		return getReplacements(new DerivedWord(word));
	}

	public static ArrayList<DerivedWord> getReplacements(String word)
	{
		return getReplacements(new DerivedWord(word));
	}

	// SWAPS

	public static ArrayList<DerivedWord> getSwaps(DerivedWord word)
	{
		// Create the list to hold all the derivations
		ArrayList<DerivedWord> list = new ArrayList<DerivedWord>();

		// Temporary holder of derivations
		DerivedWord temp;

		// Copy the word and its properties
		Word root = word.getWord();
		int rootLength = root.getLength();

		for (int i = 0; i < rootLength; i++)
		{
			for (int j = i + 1; j < rootLength; j++)
			{
				// Derivation is the concatenation of three substrings with the characters between swapped
				temp = new DerivedWord(
						root.getSubstring(0, i) + root.getCharAt(j) +
						root.getSubstring(i + 1, j) + root.getCharAt(i) + root.getSubstring(j + 1)
						);

				// Change relevant derivation properties
				temp.editDistance += 1;
				temp.characterSwapDistance =
						(temp.characterSwapDistance > (j - i)) ? temp.characterSwapDistance : (j - i);

				// Add to list
				list.add(temp);
			}
		}

		return list;
	}

	public static ArrayList<DerivedWord> getSwaps(Word word)
	{
		return getSwaps(new DerivedWord(word));
	}

	public static ArrayList<DerivedWord> getSwaps(String word)
	{
		return getSwaps(new DerivedWord(word));
	}

	/*
	 * SETTERS
	 */

	private void setDefaults()
	{
		editDistance = 0;

		charactersDeleted = 0;
		charactersInserted = 0;
		characterReplacementDistance = 0;
		characterSwapDistance = 0;
	}

	/*
	 * GETTERS
	 */

	// Returns the distance of two keys on the keyboard
	private static int getKeyDistance(char a, char b)
	{
		// Starting value of editDistance
		int distance = -1;

		// Starting values for key positions
		int aRow = -1;
		int aCol = -1;
		int bRow = -1;
		int bCol = -1;

		// Finding row and column number of char a
		for (int i = 0; i < NO_OF_KEY_ROWS; i++)
		{
			for (int j = 0; j < NO_OF_KEY_COLS; j++)
			{
				if (keyboard[i][j] == a)
				{
					aRow = i;
					aCol = j;
				}
			}
		}

		// Finding row and column number of char b
		for (int i = 0; i < NO_OF_KEY_ROWS; i++)
		{
			for (int j = 0; j < NO_OF_KEY_COLS; j++)
			{
				if (keyboard[i][j] == b)
				{
					bRow = i;
					bCol = j;
				}
			}
		}

		// Checking if both keys were found
		if (aRow == -1 || bRow == -1)
		{
			System.out.println("Error: keys not found in DerivedWord.getKeyDistance method.");
			return -1;
		}

		// Calculating distance
		distance = Math.abs(aRow - bRow) + Math.abs(aCol - bCol);

		// Return key distance
		return distance;
	}

	// Getters for Word properties

	public Word getWord()
	{
		return word;
	}

	// Getters for derivation properties

	public double[] getDerivationProperties()
	{
		double[] properties = new double[5];

		properties[0] = editDistance;
		properties[1] = charactersDeleted;
		properties[2] = charactersInserted;
		properties[3] = characterReplacementDistance;
		properties[4] = characterSwapDistance;

		return properties;
	}

	public int getEditDistance()
	{
		return editDistance;
	}

	public int getCharactersDeleted()
	{
		return charactersDeleted;
	}

	public int getCharactersInserted()
	{
		return charactersInserted;
	}

	public int getCharacterReplacementDistance()
	{
		return characterReplacementDistance;
	}

	public int getCharacterSwapDistance()
	{
		return characterSwapDistance;
	}



}
