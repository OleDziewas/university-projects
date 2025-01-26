import java.util.LinkedList;
import java.util.Queue;

/**
 * This class implements a binary search tree. It holds a separate {@link Node}
 * class which is used for the elements of the tree.
 */
public class BinarySearchTree {

	Node root = null; // Keep public, do NOT set to private
	/**
	 * A class to handle the nodes of the tree. It holds an integer value as key and
	 * has references to both left and right children.
	 */
	class Node {
		int key;
		Node left;
		Node right;

		/**
		 * Creates a new node with no children and a given integer key as value.
		 * 
		 * @param key 
		 * 		the integer value of the node
		 */
		Node(int key) {
			this.key = key;
			this.left = null;
			this.right = null;
		}
	}

	/**
	 * Adds a new node with the given key to the binary search tree. Remember to
	 * store new nodes at their proper position.
	 * 
	 * @param key 
	 * 		The integer value of the new node.
	 */
	public void add(int key) {
		if (this.root == null){
			root = new Node(key);
		}else{
			Node nd = root;
			while(nd != null){
				if(key == nd.key){
					System.out.println("Element ist schon vorhanden");
					break;
				}else if(key < nd.key){
					if (nd.left == null) {
						nd.left = new Node(key);
						break;
					}
					nd = nd.left;
				}else{
					if (nd.right == null) {
						nd.right = new Node(key);
						break;
					}
					nd = nd.right;
				}
			}
		}
		/* TODO: Implement */
	}

	/**
	 * Returns true if the binary search tree is empty, false otherwise.
	 * 
	 * @return 
	 * 		true if the binary search tree is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Counts the number of nodes stored in the tree.
	 * 
	 * @return 
	 * 		the number of nodes in the tree
	 */
	public int getSize() {
		int size = 0;
		size = getSizeRecursive(this.root, size);
		return size;
	}

	public int getSizeRecursive(Node nd, int size){
		if (nd == null){
			return size;
		}
		size++;
		size = getSizeRecursive(nd.left, size);
		size = getSizeRecursive(nd.right, size);
		return size;
	}

	/**
	 * Returns true if a given key is contained in the tree, false otherwise.
	 * 
	 * @param key 
	 * 		the key to find.
	 * @return 
	 * 		true if the given key is contained in the tree, false otherwise.
	 */
	public boolean containsKey(int key) {
		Node nd = root;
		while(nd != null) {
			if (key == nd.key) {
				return true;
			}else if(key < nd.key){
				nd = nd.left;
			}else{
				nd = nd.right;
			}
		}
		return false;
	}

	/**
	 * Deletes all nodes with the given key from the tree. If the key is not found,
	 * the tree is not changed. Every node with two children that gets deleted must 
	 * be replaced with the inorder successor (the leftmost child of the right subtree).
	 * 
	 * @param key 
	 * 		the value to be deleted from the tree.
	 */
	public void delete(int key) {
		this.root = deleteRecursive(this.root, key);
		/* TODO: Implement */
	}

	public Node deleteRecursive(Node nd, int key){
		if(nd == null){
			return null;
		}
		if (key == nd.key){
			if(nd.right== null){
				return nd.left;
			}
			if (nd.left == null){
				return nd.right;
			}

			nd.key = findSmallestKey(nd.right);
			nd.right = deleteRecursive(nd.right, nd.key);
		}else if(key <nd.key) {
			nd.left = deleteRecursive(nd.left, key);
		}else{
			nd.right = deleteRecursive(nd.right, key);
		}
		return nd;
	}

	public int findSmallestKey(Node nd){
		while(nd.left != null){
			nd = nd.left;
		}
		return nd.key;
	}
	/**
	 * Creates a string in depth first pre-order structure. The final String should
	 * contain the values of all nodes in this particular order. If the tree is empty, 
         * the empty string is returned.
	 * 
	 * @return 
	 * 		The keys of the nodes in the tree in depth first pre-order.
	 */
	public String PreOrder() {
		StringBuilder builder = new StringBuilder();
		if (root == null){
			return "";
		}else{
			builder = PreOrderRecursive(this.root,builder);
		}
		builder.deleteCharAt(builder.length()-1);
		return builder.toString();
	}

	public StringBuilder PreOrderRecursive(Node nd, StringBuilder builder){
		if (nd == null){
			return builder;
		}
		builder.append(nd.key +" ");
		PreOrderRecursive(nd.left, builder);
		PreOrderRecursive(nd.right, builder);
		return builder;
	}
	/**
	 * Creates a string in depth first in-order structure. The final String should
	 * contain the values of all nodes in this particular order. If the tree is empty, 
         * the empty string is returned.
	 * 
	 * @return 
	 * 		The keys of the nodes in the tree in depth first in-order.
	 */
	public String InOrder() {
		StringBuilder builder = new StringBuilder();
		if (root == null){
			return "";
		}else{
			builder = InOrderRecursive(this.root,builder);
		}
		builder.deleteCharAt(builder.length()-1);
		return builder.toString();
	}
	public StringBuilder InOrderRecursive(Node nd, StringBuilder builder){
		if (nd == null){
			return builder;
		}
		InOrderRecursive(nd.left, builder);
		builder.append(nd.key +" ");
		InOrderRecursive(nd.right, builder);
		return builder;
	}

	/**
	 * Creates a string in depth first post-order structure. The final String should
	 * contain the values of all nodes in this particular order.  If the tree is empty, 
         * the empty string is returned.
	 * 
	 * @return 
	 * 		The keys of the nodes in the tree in depth first post-order.
	 */
	public String PostOrder() {
		StringBuilder builder = new StringBuilder();
		if (root == null){
			return "";
		}else{
			builder = PostOrderRecursive(this.root,builder);
		}
		builder.deleteCharAt(builder.length()-1);
		return builder.toString();
	}
	public StringBuilder PostOrderRecursive(Node nd, StringBuilder builder){
		if (nd == null){
			return builder;
		}
		PostOrderRecursive(nd.left, builder);
		PostOrderRecursive(nd.right, builder);
		builder.append(nd.key +" ");
		return builder;
	}

	/**
	 * Creates a string in breadth first / level-order. I.e. First all nodes on
	 * level 0, then 1, etc. are returned. The final String should contain the
	 * values of all nodes in this particular order. If the tree is empty, 
         * the empty string is returned.
	 * 
	 * @return 
	 * 		The keys of the nodes in the tree in breadth first / level-order.
	 */
	public String BFS() {
		StringBuilder builder = new StringBuilder();
		if (root == null){
			return "";
		}
		Queue<Node>  nodes = new LinkedList<>();
		nodes.add(this.root);
		while(!nodes.isEmpty()){
			Node node = nodes.remove();
			builder.append(node.key + " ");
			if (node.left!= null) {
				nodes.add(node.left);
			}
			if(node.right!= null) {
				nodes.add(node.right);
			}
		}
		builder.deleteCharAt(builder.length()-1);
		return builder.toString();
	}


	/**
	 * Checks if a tree is a balanced AVL tree, i.e. the difference between heights
	 * of left and right subtrees are less than one for all nodes.
	 */
	public boolean isBalanced() {

		return isBalancedRecursive(this.root);
	}

	public boolean isBalancedRecursive(Node nd){
		if(nd == null){
			return true;
		}

		int lh = height(nd.left);
		int rh = height(nd.right);

		if (Math.abs(lh-rh)<= 1 && isBalancedRecursive(nd.left) && isBalancedRecursive(nd.right)){
			return true;
		}
		return false;
	}
	public int height(Node nd){
		if (nd == null) {
			return 0;
		}
		int lh = height(nd.left);
		int rh = height(nd.right);
		if (lh > rh) {
			return (lh + 1);
		}else{
			return (rh +1);
		}
	}
	/**
	 * Returns a {@link LinkedList} of all integer keys at a given depth/level of
	 * the tree, as they appear in the tree (from left to right).
	 * 
	 * @param depth 
	 * 		The depth of the tree at which all nodes should be retrieved
	 * @return 
	 * 		A {@link LinkedList} of all keys at the given depth of the tree.
	 */
	public LinkedList<Integer> keysAtDepth(int depth) {
		LinkedList<Node>  nodes = new LinkedList<>();
		if (root == null){
			return new LinkedList<Integer>();
		}
		nodes.add(this.root);
		int counter = 0;
		LinkedList<Node> children = new LinkedList<>();
		while(counter <depth){
			for (Node node : nodes){
				if (node.left!= null) {
					children.add(node.left);
				}
				if(node.right!= null) {
					children.add(node.right);
				}
			}
			nodes.clear();
			for (Node node: children){
				nodes.add(node);
			}
			children.clear();
			counter++;
		}
		LinkedList<Integer> nodeValues = new LinkedList<>();
		for (Node node : nodes){
			nodeValues.add(node.key);
		}
		return nodeValues;
	}

	/**
	 * Returns a BinarySearchTree that is an AVL-balanced tree representation of
	 * the input BST tree
	 * 
	 * @return 
	 * 		An AVLified BinarySearchTree.
	 */
	public BinarySearchTree AVLify() {
		LinkedList<Node> nodes = new LinkedList<>();
		nodes = findNodesRecursive(this.root, nodes);
		BinarySearchTree tree = new BinarySearchTree();
		tree = buildAVLTreeRecursiv(nodes, tree, 0, nodes.size()-1);
		return tree;
	}

	public BinarySearchTree buildAVLTreeRecursiv(LinkedList<Node> nodes, BinarySearchTree tree, int start, int end){
		if (start>end){
			return tree;
		}
		int mid = (start+end)/2;
		tree.add(nodes.get(mid).key);
		tree = buildAVLTreeRecursiv(nodes,tree,start, mid-1);
		tree = buildAVLTreeRecursiv(nodes, tree,mid+1, end);
		return tree;
	}

	public LinkedList<Node> findNodesRecursive(Node nd, LinkedList<Node> nodes){
		if (nd == null){
			return nodes;
		}
		nodes = findNodesRecursive(nd.left, nodes);
		nodes.add(nd);
		nodes = findNodesRecursive(nd.right, nodes);
		return nodes;
	}


}
