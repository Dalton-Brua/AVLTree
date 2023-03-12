public class AVLTree {

    static TreeNode head;

    AVLTree(int num) {
        head = new TreeNode(num);
    }

    public static void main(String[] args) {

        AVLTree tree = new AVLTree(56);
        tree.insert(23,head);

    }

    public void insert(int num, TreeNode node) {
        // If node is smaller go left
        if (num < node.value) {
            // If node exists go left
            if (node.left != null) {
                insert(num, node.left);
            } else {
                // If no node, create node
                node.left = new TreeNode(num);
            }
        } else {
            // If node is not smaller go right
            if (node.right != null) {
                insert(num, node.right);
            } else {
                // If no node, create node
                node.right = new TreeNode(num);
            }
        }
        // Adjust height after insertion
        adjustHeight(node);

        // Balance the tree if necessary
        balance(node);
    }
    public void adjustHeight(TreeNode node) {

        // If leaf node then height is 0
        node.height = 0;

        // If it has both nodes, make the height equal to the greater height + 1
        if (node.right != null && node.left != null) {
            if (node.right.height > node.left.height) {
                node.height = node.right.height + 1;
            } else {
                node.height = node.left.height + 1;
            }

            // If it only has one node make it the height of that node + 1
        } else if (node.right != null) {
            node.height = node.right.height + 1;
        } else if (node.left != null) {
            node.height = node.left.height + 1;
        }
    }
    public int getBalance(TreeNode node) {

        // If a node has no child then the height on that side is -1
        int leftHeight = -1;
        int rightHeight = -1;
        int balance = 0;

        // If a node has children, use the height of the children
        if (node.left != null) leftHeight = node.left.height;
        if (node.right != null) rightHeight = node.right.height;

        balance = leftHeight - rightHeight;

        return balance;
    }
    public void balance(TreeNode node) {

        // If a node has no children, then the height of the children is -1
        int leftBalance = 0;
        int rightBalance = 0;
        int nodeBalance = getBalance(node);

        // If a node has children, get the height of the children
        if (node.left != null) leftBalance = getBalance(node.left);
        if (node.right != null) rightBalance = getBalance(node.right);

        if (nodeBalance < -1) {
            if (rightBalance > 0) {
                // RL scenario

                // Do special right rotation
                RL(node);

                // Do left rotation
                L(node);

            } else if (rightBalance < 0) {
                // RR scenario

                // Do left rotation
                L(node);
            }


        } else if (nodeBalance > 1) {
            if (leftBalance > 0) {
                // LL scenario

                // Do right rotation
                R(node);

            } else if (leftBalance < 0) {
                // LR Scenario

                // Do special left rotation
                LR(node);

                // Do right rotation
                R(node);

            }
        }
    }

    public TreeNode copyOf(TreeNode n) {

        // Create a new TreeNode elsewhere in memory
        TreeNode copy = new TreeNode(n.value);

        // Copy values from previous node to new node
        copy.height = n.height;
        copy.deleted = n.deleted;
        copy.left = n.left;
        copy.right = n.right;

        return copy;
    }

    public void R(TreeNode node) {

        // Makes a note of the left subtree
        TreeNode tempLeft = node.left.right;

        // Makes a copy of the node so that changing this one does not change the child
        node.right = copyOf(node);

        // Changes all values of the current node to the values of the left node
        node.value = node.left.value;
        node.deleted = node.left.deleted;
        node.left = node.left.left;

        // Changes the left child of the copied node to the left subtree
        node.right.left = tempLeft;

        // Adjust heights of nodes that were moved
        adjustHeight(node.right);
        adjustHeight(node);

    }

    public void L(TreeNode node) {

        // Makes a note of the right subtree
        TreeNode tempRight = node.right.left;

        // Makes a copy of the node so that changing this one does not change the child

        node.left = copyOf(node);

        // Changes all values of the current node to the values of the left node
        node.value = node.right.value;
        node.deleted = node.right.deleted;
        node.right = node.right.right;


        // Changes the right child of the copied node to the right subtree
        node.left.right = tempRight;

        // Adjust heights of nodes that were moved
        adjustHeight(node.left);
        adjustHeight(node);

    }

    public void LR(TreeNode node) {

        // Saves the right subtree for future use
        TreeNode tempRight = node.left.right;

        // Sever right subtree from left node
        node.left.right = null;

        // Adds the left node onto the end of the right subtree so it becomes a line
        tempRight.left = node.left;

        // Makes the left node be the root of the modified right subtree
        node.left = tempRight;

        // Adjust heights of nodes that were moved
        adjustHeight(node.left.left);
        adjustHeight(node.left);

    }

    public void RL(TreeNode node) {

        // Saves left subtree
        TreeNode tempLeft = node.right.left;

        // Sever left subtree from right node
        node.right.left = null;

        // Adds the right node onto the end of the left subtree so it becomes a line
        tempLeft.right = node.right;

        // Makes the right node be the root of the modified left subtree
        node.right = tempLeft;

        // Adjust heights of nodes that were moved
        adjustHeight(node.right.right);
        adjustHeight(node.right);

    }
}
