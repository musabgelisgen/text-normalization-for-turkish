package normalizer;

import java.util.ArrayList;

//THIS IS THE TREE DATA STRUCTURE, IT CONTAINS DERIVEDWORD DATA
public class DerivationTree {
	//root of tree
    private Node root;

    //Constructor
    public DerivationTree(DerivedWord rootData) {
        root = new Node(rootData);
    }

    //get root
    public Node getRoot() {
    	return this.root;
    }

    //INNER CLASS
    public static class Node {
	//data of node
    private DerivedWord data;
    //children list of node
    private ArrayList<Node> children;

    //constructor
    Node(DerivedWord data){
		this.data = data;
		children = new ArrayList<Node>();
    }

    //This method will append derived words to main word
    public void addToList(Node newChildren) {
    	this.children.add(newChildren);
    }

    //getter for list of child nodes
    public ArrayList<Node> getList(){
    	return this.children;
    }

    //getter for node data
    public DerivedWord getData() {
    	return this.data;
    }

}

}
