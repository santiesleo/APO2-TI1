package model;

public class ScoreRegistry {

    // Data
    private Player root;

    /**
     * Add a new player to a Binary Search Tree of players
     */
    public void add(Player player) {
        // If the root is null, assign the new player as the root of the tree
        if (root == null) {
            root = player;
        } else {
            // If the root already exists, call the private add method to recursively add the player to the tree
            add(root, player);
        }
    }

    private void add(Player current, Player player) {
        // Compare the scores of the current node and the new player to determine which side to add the player
        if (player.getScore() < current.getScore()) {
            // If the new player's score is less than the current node's score, add the player to the left side of the current node
            if (current.getLeft() == null) {
                current.setLeft(player);
            } else {
                add(current.getLeft(), player);
            }
        } else if (player.getScore() > current.getScore()) {
            // If the new player's score is greater than the current node's score, add the player to the right side of the current node
            if (current.getRight() == null) {
                current.setRight(player);
            } else {
                add(current.getRight(), player);
            }
        } else {
            // If the new player's score is equal to the current node's score, do nothing
        }
    }

    /**
     * Prints a table of players and their scores in reverse order
     */
    public void reverseInOrder() {
        // Print the table headers with appropriate spacing and formatting
        System.out.printf("|%10s\t | %4s |", "PLAYER", "SCORE");
        // Move to a new line for the next line of the table
        System.out.println();
        // Call the private recursive method that will traverse the binary tree in reverse order starting from the root
        reverseInOrder(root);
    }

    private void reverseInOrder(Player current) {
        if (current == null) {
            return;
        }
        // Recursively call this method on the right subtree to start at the node with the highest score
        reverseInOrder(current.getRight());
        // Print the current player's name and score with appropriate formatting
        System.out.format("|%7s\t   |    %.2f    |", current.getName(), current.getScore());
        // Move to a new line for the next line of the table
        System.out.println();
        // Recursively call this method on the left subtree to traverse the rest of the tree in reverse order.
        reverseInOrder(current.getLeft());
    }

}
