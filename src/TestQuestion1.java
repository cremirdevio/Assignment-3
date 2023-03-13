import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import question_1.RestructureableNodeBinaryTree;

/**
* This class contains a main method that tests the functionality of a RestructureableNodeBinaryTree.
* It creates a tree with a number of nodes, finds and deletes a specific node, and then rotates another node in the tree.
* The output of each operation is printed to the console for verification.
*/
public class TestQuestion1 {
    
    /**
    * The main method creates a new RestructureableNodeBinaryTree instance, inserts several nodes into it,
    * and performs various operations on the tree to test its functionality.
    * @param args the command-line arguments passed to the program
    */
    public static void main(String[] args) {
        RestructureableNodeBinaryTree<Integer> tree = new RestructureableNodeBinaryTree<>();

        // Add nodes to the tree
        tree.insert(50);
        tree.insert(30);
        tree.insert(70);
        tree.insert(20);
        tree.insert(40);
        tree.insert(60);
        tree.insert(80);

        // Test finding a node in the tree
        System.out.println("Finding node 40 in the tree: " + tree.find(40));

        // Test deleting a node from the tree
        tree.delete(40);
        System.out.println("After deleting node 40: ");
        tree.traverseInOrder();

        // Test rotating a node in the tree
        RestructureableNodeBinaryTree<Integer>.Node<Integer> nodeToRotate = tree.find(30);
        System.out.println("Before rotation:");
        tree.traverseInOrder();
        tree.restructure(nodeToRotate);
        System.out.println("After rotation:");
        tree.traverseInOrder();
    }
}
