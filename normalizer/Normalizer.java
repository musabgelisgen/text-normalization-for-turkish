package normalizer;

import java.util.ArrayList;

public class Normalizer{

	//ATTRIBUTES
	private DerivationTree DerivationTree;
	private double T_max = 0; //This variable keeps the max T
	private DerivedWord T_max_word; // This string will be the word with max T

	//first stage of correction, we will start with this method
	public DerivedWord correctWord(DerivedWord word) {

		//We are checking whether the root word is in the dictionary or not
		double frequency = searchFrequency(word);

		//if the root word is in the dictionary, then its assumed to be correct. THIS WILL BE CHANGED AT V2
		if( frequency != 0)
			return word;
		else {
			return findCorrectWord(word);
		}

	}

	//This method finds the most accurate answer by controlling change methods
	public DerivedWord findCorrectWord(DerivedWord word) {

		//one change
		firstEdit(word);

		//if we hit desired result, do not go further
		if (T_max >= 0.9)
			return T_max_word;

		//two change
		secondEdit(word);

		//if we hit desired result, do not go further
		if (T_max >= 0.9)
			return T_max_word;

		//two change
		thirdEdit(word);

		//return this value, this is stop limit.
		return T_max_word;


	}

	//This method finds T_MAX after first change on the root word
	public void firstEdit(DerivedWord word) {
		//We are creating our DerivationTree structure for this word
		this.DerivationTree = new DerivationTree(word);

		//derivedWordsOfRoot keeps the derived words of root
		ArrayList<DerivedWord> derivedWordsOfRoot = DerivedWord.getDerivations(word);

		//We are going through derived Words from main word
		for (DerivedWord derivedWord : derivedWordsOfRoot )
		{
			//we are searching the word in dictionary
			double frequencyOfDerivedWord = searchFrequency(derivedWord);

			//if the derivedWord is in the library, we are finding its TruthProbabilty
			if(frequencyOfDerivedWord != 0) {
				//find truth probability
				double[] properties = derivedWord.getDerivationProperties();
				double truthProb = getTruthProbability(properties, frequencyOfDerivedWord);

				//if the truth Probabilty is the biggest until now, keep it. FOR VERSION_2, KEEP THEM IN THE LIST
				if( truthProb > this.T_max) {
					this.T_max = truthProb;
					this.T_max_word = derivedWord;
				}
			}else {
				//creating new node
				DerivationTree.Node newNode = new DerivationTree.Node(derivedWord);
				//adding it to the children list of root
				DerivationTree.getRoot().addToList(newNode);
			}

		}

	}


	//This method finds T_MAX after second change on the root word
	public void secondEdit(DerivedWord word) {
		//This temporary arraylist keeps the children list of root, this was created in first edit method.
		ArrayList<DerivationTree.Node> tempChildrenOfRoot = DerivationTree.getRoot().getList();

		//Going through each node in the root children list
		for(DerivationTree.Node node : tempChildrenOfRoot) {
			//taking the derivedWord data in the node
			DerivedWord tempDerivedWord = node.getData();

			//taking the derivatives of the derivedWord in the node
			ArrayList<DerivedWord> derivedWordsOfNode = DerivedWord.getDerivations(tempDerivedWord);

			//going through each derivedWord in this list
			for(DerivedWord derivedWord : derivedWordsOfNode) {
				//we are searching the derivedWord in dictionary
				double frequencyOfDerivedWord = searchFrequency(derivedWord);

				//if the derivedWord is in the library, we are finding its TruthProbabilty
				if(frequencyOfDerivedWord != 0) {
					//find truth probability
					double[] properties = derivedWord.getDerivationProperties();
					double truthProb = getTruthProbability(properties, frequencyOfDerivedWord);

					//if the truth probabilty is the biggest until now, keep it
					if( truthProb > this.T_max) {
						this.T_max = truthProb;
						this.T_max_word = derivedWord;
					}
				}else {
					//creating new node
					DerivationTree.Node newNode = new DerivationTree.Node(derivedWord);
					//adding it to the children list of root
					node.addToList(newNode);
				}
			}

		}

	}

	//This method finds T_MAX after third change on the root word
	public void thirdEdit(DerivedWord word) {
		//This temporary arraylist keeps the children list of root, this was created in first edit method.
		ArrayList<DerivationTree.Node> tempChildrenOfRoot = DerivationTree.getRoot().getList();

		//Going through each node in the root children list
		for(DerivationTree.Node node : tempChildrenOfRoot) {
			ArrayList<DerivationTree.Node> tempChildrenOfNode = node.getList();

			//Going through each node in the children list of node
			for(DerivationTree.Node childNode : tempChildrenOfNode) {
				//taking the derivedWord data in the childNode
				DerivedWord tempDerivedWord = childNode.getData();

				//taking the derivatives of the derivedWord in the node
				ArrayList<DerivedWord> derivedWordsOfNode = DerivedWord.getDerivations(tempDerivedWord);

				//going through each derivedWord in this list
				for(DerivedWord derivedWord : derivedWordsOfNode) {
					//we are searching the derivedWord in dictionary
					double frequencyOfDerivedWord = searchFrequency(derivedWord);

					//if the derivedWord is in the library, we are finding its TruthProbabilty
					if(frequencyOfDerivedWord != 0) {
						//find truth probability
						double[] properties = derivedWord.getDerivationProperties();
						double truthProb = getTruthProbability(properties, frequencyOfDerivedWord);

						//if the truth probabilty is the biggest until now, keep it
						if( truthProb > this.T_max) {
							this.T_max = truthProb;
							this.T_max_word = derivedWord;
						}
						//if the derivedword is not in the dict, dont put into DerivationTree because we will not go further.
					}
				}

			}

		}
	}

}
