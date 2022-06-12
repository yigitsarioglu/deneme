package project;

public class Node {
	private String data;
	private Node left;
	private Node right;

	public Node(String data) {
		this.data=data;
		this.right=null;
		this.left=null;
	}

	public Node getRight() {
		return this.right;
	}
	
	public void setRight(Node right) {
		this.right = right;
	}
	
	public Node getLeft() {
		return this.left;
	}
	
	public void setLeft(Node left) {
		this.left = left;
	}

	public String getData() {

		return this.data;
	}

}