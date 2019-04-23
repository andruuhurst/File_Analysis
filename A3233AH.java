/* Andrew Hurst  /   andrew_hurst1@my.cuesta.edu
 * CIS 233  / Scovil
 * Assignment 3
 */

package cis233.a3;

import java.io.*;
import java.util.Scanner;

public class A3233AH {

    /*
     *
     *
     */
    static class A3233AHurAVL<AnyType extends Comparable<? super AnyType>>
    {
        /**
         * Construct the tree.
         */
        A3233AHurAVL() {
            root = null;
        }

        /**
         * Remove from the tree. Nothing is done if x is not found.
         *
         * @param x the item to remove.
         */
        void remove(AnyType x) {
            root = remove(x, root);
        }


        /**
         * Internal method to remove from a subtree.
         *
         * @param x the item to remove.
         * @param t the node that roots the subtree.
         * @return the new root of the subtree.
         */
        private AvlNode<AnyType> remove(AnyType x, AvlNode<AnyType> t) {
            if (t == null)
                return t;   // Item not found; do nothing

            int compareResult = x.compareTo(t.element);

            if (compareResult < 0)
                t.left = remove(x, t.left);
            else if (compareResult > 0)
                t.right = remove(x, t.right);
            else if (compareResult == 0 && t.dupCount > 1) {
                t.dupCount--;
                return balance(t);
            } else if (t.left != null && t.right != null) // Two children
            {
                t.element = findMin(t.right).element;
                t.right = remove(t.element, t.right);
            } else
                t = (t.left != null) ? t.left : t.right;
            return balance(t);
        }


        /**
         * Test if the tree is logically empty.
         * @return true if empty, false otherwise.
         */
        boolean isEmpty() {
            return root == null;
        }

        private static final int ALLOWED_IMBALANCE = 1;

        // Assume t is either balanced or within one of being balanced
        private AvlNode<AnyType> balance(AvlNode<AnyType> t) {
            if (t == null)
                return t;

            if (height(t.left) - height(t.right) > ALLOWED_IMBALANCE)
                if (height(t.left.left) >= height(t.left.right))
                    t = rotateWithLeftChild(t);
                else
                    t = doubleWithLeftChild(t);
            else if (height(t.right) - height(t.left) > ALLOWED_IMBALANCE)
                if (height(t.right.right) >= height(t.right.left))
                    t = rotateWithRightChild(t);
                else
                    t = doubleWithRightChild(t);

            t.height = Math.max(height(t.left), height(t.right)) + 1;
            return t;
        }

        void checkBalance() {
            checkBalance(root);
        }

        private int checkBalance(AvlNode<AnyType> t) {
            if (t == null)
                return -1;

            if (t != null) {
                int hl = checkBalance(t.left);
                int hr = checkBalance(t.right);
                if (Math.abs(height(t.left) - height(t.right)) > 1 ||
                        height(t.left) != hl || height(t.right) != hr)
                    System.out.println("OOPS!!");
            }

            return height(t);
        }



        private AvlNode<AnyType> tieBreak( AvlNode<AnyType> x , AvlNode<AnyType> t )
        {
            int compareResult = x.element.compareTo(t.element);

            if (compareResult < 0)
                t.left = tieBreakInsert(x, t.left);
            if (compareResult > 0)
                t.right = tieBreakInsert(x, t.right);
            return balance(t);
        }


        void insertByFreq( AvlNode<AnyType> x)
        {
            root = insertByFreq(x , root);
        }


        private AvlNode<AnyType> insertByFreq( AvlNode<AnyType> x , AvlNode<AnyType> t)
        {
           if ( t == null)
               return new AvlNode<>(x.element, null, null, x.dupCount);

           int compareResult = x.dupCount.compareTo(t.dupCount);

           if (compareResult == 0 )
               tieBreak(x , t);
           if ( compareResult < 0 )
               t.left = insertByFreq( x , t.left);
           if ( compareResult > 0 )
               t.right = insertByFreq( x , t.right);

           return balance( t );
        }


        /**
         * Internal method to insert into a subtree.
         *
         * @param x the item to insert.
         * @param t the node that roots the subtree.
         * @return the new root of the subtree.
         */
        private AvlNode<AnyType> insert(AnyType x, AvlNode<AnyType> t) {
            if (t == null)
                return new AvlNode<>(x, null, null);

            int compareResult = x.compareTo(t.element);

            if (compareResult == 0)
                t.dupCount++;
            if (compareResult < 0)
                t.left = insert(x, t.left);
            if (compareResult > 0)
                t.right = insert(x, t.right);

            return balance(t);
        }


        private AvlNode<AnyType> tieBreakInsert(AvlNode<AnyType> x , AvlNode<AnyType> t) {
            if (t == null)
                return x;

            int compareResult = x.element.compareTo(t.element);

            if (compareResult == 0)
                t.dupCount++;
            if (compareResult < 0)
                t.left = tieBreakInsert(x, t.left);
            if (compareResult > 0)
                t.right = tieBreakInsert(x, t.right);

            return balance(t);
        }



        /**
         * Insert into the tree;
         *
         * @param x the item to insert.
         */
        void insert(AnyType x)
        {
            root = insert(x, root);
        }



        /**
         * Internal method to find the smallest item in a subtree.
         *
         * @param t the node that roots the tree.
         * @return node containing the smallest item.
         */
        private AvlNode<AnyType> findMin(AvlNode<AnyType> t) {
            if (t == null)
                return t;

            while (t.left != null)
                t = t.left;
            return t;
        }

        /**
         * Internal method to find the largest item in a subtree.
         *
         * @param t the node that roots the tree.
         * @return node containing the largest item.
         */
        private AvlNode<AnyType> findMax(AvlNode<AnyType> t) {
            if (t == null)
                return t;

            while (t.right != null)
                t = t.right;
            return t;
        }


        /*
         * Recursively transfers words put in sorted order with alphabtically in order to get frequency --
         * To tree that will be sorted by frequency.
         * Tie breaks on alphabetical order
         */

        void transferTree( A3233AHurAVL tree)
        {
            if ( isEmpty())
                System.out.println("Tree is currently Empty");
            else
                transferTree( root , tree);

        }

        private void transferTree( AvlNode<AnyType> t, A3233AHurAVL tree )
        {
            if( t != null )
            {
                transferTree(t.left , tree);
                tree.insertByFreq(t);
                distCount++;
                transferTree(t.right, tree);
            }
        }

        /*
         * Print the tree contents in sorted order.
         */
        void printTree()
        {
            if (isEmpty())
                System.out.println("Tree is currently Empty");
            else
                printTree(root);
        }

        /**
         * Internal method to print a subtree in sorted order.
         *
         * @param t the node that roots the tree.
         */
        private void printTree(AvlNode<AnyType> t) {
            if (t != null)
            {
                printTree(t.left);
                //for (int i = 0; i < t.dupCount; i++)
                    System.out.println(t);
                printTree(t.right);
            }
        }


        /*
         * Writes printBalTree() to file:
         * A3-233AHur.txt
         */
        public void writeBalTree(boolean ascend) throws IOException {
            PrintStream writeTree = new PrintStream(String.valueOf(new FileWriter("A3-233AHur.txt")));

            if (isEmpty())
                System.out.println("Tree is currently Empty");
            else {
                if (ascend != false)
                    writeAscendTree(root , writeTree);
                else
                    writeDescendTree(root, writeTree);
            }
            writeTree.close();
        }

        private void writeAscendTree(AvlNode<AnyType> t , PrintStream writeTree) throws IOException {

            if (t != null) {
                writeAscendTree(t.left, writeTree);
                for (int i = 0; i < t.dupCount; i++)
                    writeTree.println(t.element);
                writeAscendTree(t.right, writeTree);
            }
        }

        private void writeDescendTree(AvlNode<AnyType> t , PrintStream writeTree) throws IOException {
            if (t != null) {
                writeDescendTree(t.right, writeTree);
                for (int i = 0; i < t.dupCount; i++)
                    writeTree.println(t.element);
                writeDescendTree(t.left , writeTree);
            }
        }

        /**
         * Return the height of node t, or -1, if null.
         */
        private int height(AvlNode<AnyType> t) {
            return t == null ? -1 : t.height;
        }

        /**
         * Rotate binary tree node with left child.
         * For AVL trees, this is a single rotation for case 1.
         * Update heights, then return new root.
         */
        private AvlNode<AnyType> rotateWithLeftChild(AvlNode<AnyType> k2) {
            AvlNode<AnyType> k1 = k2.left;
            k2.left = k1.right;
            k1.right = k2;
            k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
            k1.height = Math.max(height(k1.left), k2.height) + 1;
            return k1;
        }

        /**
         * Rotate binary tree node with right child.
         * For AVL trees, this is a single rotation for case 4.
         * Update heights, then return new root.
         */
        private AvlNode<AnyType> rotateWithRightChild(AvlNode<AnyType> k1) {
            AvlNode<AnyType> k2 = k1.right;
            k1.right = k2.left;
            k2.left = k1;
            k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
            k2.height = Math.max(height(k2.right), k1.height) + 1;
            return k2;
        }

        /**
         * Double rotate binary tree node: first left child
         * with its right child; then node k3 with new left child.
         * For AVL trees, this is a double rotation for case 2.
         * Update heights, then return new root.
         */
        private AvlNode<AnyType> doubleWithLeftChild(AvlNode<AnyType> k3) {
            k3.left = rotateWithRightChild(k3.left);
            return rotateWithLeftChild(k3);
        }

        /**
         * Double rotate binary tree node: first right child
         * with its left child; then node k1 with new right child.
         * For AVL trees, this is a double rotation for case 3.
         * Update heights, then return new root.
         */
        private AvlNode<AnyType> doubleWithRightChild(AvlNode<AnyType> k1) {
            k1.right = rotateWithLeftChild(k1.right);
            return rotateWithRightChild(k1);
        }


        static class AvlNode<AnyType> {
            // Constructors
            AvlNode(AnyType theElement) {
                this(theElement, null, null);
            }

            AvlNode(AnyType theElement, AvlNode<AnyType> lt, AvlNode<AnyType> rt) {
                element = theElement;
                left = lt;
                right = rt;
                height = 0;
                dupCount = 1;
            }

            AvlNode(AnyType theElement, AvlNode<AnyType> lt, AvlNode<AnyType> rt, Integer dup) {
                element = theElement;
                left = lt;
                right = rt;
                height = 0;
                dupCount = dup;
            }

            private int getBalance() {
                if ((left == null && right == null) || (left != null && right != null)) {
                    return 0;
                } else
                    return 1;
            }



            public String toString() {

                return element + "\tfrequency:  " + dupCount;
            }

            AnyType element;                 // The data in the node
            AvlNode<AnyType> left;           // Left child
            AvlNode<AnyType> right;          // Right child
            int height;                      // Height
            Integer dupCount;                // Amount of duplicate values

        }

        /**
         * The tree root.
         */
        private AvlNode<AnyType> root;
    }


    static class UnderflowException extends RuntimeException
    {
        /**
         * Construct this exception object.
         * @param message the error message.
         */
        public UnderflowException( String message )
        {
            super( message );
        }

        public UnderflowException() {
            System.out.println("Error: Underflow exception ");
        }
    }

    public static void parseFile( File file )
    {
        A3233AHurAVL wordTree = new A3233AHurAVL();
        A3233AHurAVL freqTree = new A3233AHurAVL();
        String word;
        try{
            Scanner scan = new Scanner(file).useDelimiter("\\W*\\s");

            while(scan.hasNext())
            {
                wordTree.insert(scan.next().toLowerCase());
                totCount++;
            }
            wordTree.transferTree(freqTree);

            System.out.println("\n\n\n");

            freqTree.printTree();

            System.out.println( "Total words:\t" + totCount);
            System.out.println( "Distinct words:\t" + distCount);

            freqTree.writeBalTree(true);
        }
        catch(FileNotFoundException error)
        {
            error.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static int totCount;           // The total amount of words in the file
    static int distCount;          // Amount of distinct words in the file
}
