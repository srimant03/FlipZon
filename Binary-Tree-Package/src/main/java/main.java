import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class node {
    int data;
    node left;
    node right;

    node(int data) {
        this.data = data;
        left = null;
        right = null;
    }
}

class binary_tree {
    private node root;
    binary_tree() {
        root = null;
    }

    public node getRoot() {
        return root;
    }
    public int height(node root) {
        if (root == null) {
            return 0;
        } else {
            int lheight = height(root.left);
            int rheight = height(root.right);
            if (lheight > rheight) {
                return lheight + 1;
            } else {
                return rheight + 1;
            }
        }
    }

    public void time_height(node root) {
        double start = System.nanoTime();
        int height = height(root);
        double end = System.nanoTime();
        double time = (end - start) / 1000000;
        System.out.println("Height of the tree is: " + height);
        System.out.println("Time taken to calculate height of the tree without threading is: " + time + "ms");
    }

    public int getBalance(node root) {
        if (root == null) {
            return 0;
        } else {
            return height(root.left) - height(root.right);
        }
    }

    public node leftRotate(node root) {
        node new_root = root.right;
        root.right = new_root.left;
        new_root.left = root;
        return new_root;
    }

    public node rightRotate(node root) {
        node new_root = root.left;
        root.left = new_root.right;
        new_root.right = root;
        return new_root;
    }

    public node insert(node root, int data) {
        if (root == null) {
            root = new node(data);
            return root;
        } else if (data < root.data) {
            root.left = insert(root.left, data);
        } else if (data > root.data) {
            root.right = insert(root.right, data);
        } else {
            return root;
        }
        int balance = getBalance(root);
        if (balance > 1 && data < root.left.data) {
            return rightRotate(root);
        }
        if (balance < -1 && data > root.right.data) {
            return leftRotate(root);
        }
        if (balance > 1 && data > root.left.data) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }
        if (balance < -1 && data < root.right.data) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }
        return root;
    }

    public void create_tree(int arr[], int begin, int n) {
        double start = System.nanoTime();
        for (int i = begin; i < n; i++) {
            root = insert(root, arr[i]);
        }
        double end = System.nanoTime();
        double time = (end - start) / 1000000;
        System.out.println("Time taken to create the tree without threading is: " + time + "ms");
    }

    public void createTree(int arr[], int begin, int n) {
        for (int i = begin; i < n; i++) {
            root = insert(root, arr[i]);
        }
    }

    // search function
    public node search(node root, int data) {
        if (root == null) {
            return null;
        }
        if (root.data == data){
            return root;
        }
        search(root.left, data);
        search(root.right, data);
        return null;
    }

    public void time_search(node root, int data) {
        double start = System.nanoTime();
        search(root, data);
        double end = System.nanoTime();
        // time in milliseconds
        double time = (end - start) / 1000000;
        System.out.println("Time taken to search " + data + " without threading is " + time + " ms");
    }

    public void inorder(node root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.data + " ");
            inorder(root.right);
        }
    }

    public void threading_height(int x, binary_tree tree) {
        if (x == 2) {
            height_thread h1 = new height_thread(root.left, tree);
            height_thread h2 = new height_thread(root.right, tree);
            double start = System.nanoTime();
            Thread t1 = new Thread(h1);
            Thread t2 = new Thread(h2);
            t1.start();
            t2.start();
            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            double end = System.nanoTime();
            double time = (end - start) / 1000000;
            int h = 1 + Math.max(h1.height, h2.height);
            System.out.println("Height of the tree is: " + h);
            System.out.println("Time taken to calculate height of the tree with 2 threads is: " + time + "ms");
        } else if (x == 4) {
            height_thread h1 = new height_thread(root.left.left, tree);
            height_thread h2 = new height_thread(root.left.right, tree);
            height_thread h3 = new height_thread(root.right.left, tree);
            height_thread h4 = new height_thread(root.right.right, tree);
            double start = System.nanoTime();
            Thread t1 = new Thread(h1);
            Thread t2 = new Thread(h2);
            Thread t3 = new Thread(h3);
            Thread t4 = new Thread(h4);
            t1.start();
            t2.start();
            t3.start();
            t4.start();
            try {
                t1.join();
                t2.join();
                t3.join();
                t4.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            double end = System.nanoTime();
            double time = (end - start) / 1000000;
            int h = 1 + Math.max(1 + Math.max(h1.height, h2.height), 1 + Math.max(h3.height, h4.height));
            System.out.println("Height of the tree is: " + h);
            System.out.println("Time taken to calculate height of the tree with 4 threads is: " + time + "ms");
        }
    }

    public void threading_search(int x, int data, binary_tree tree) {
        if (x == 2) {
            search_thread s1 = new search_thread(root.left, data, tree);
            search_thread s2 = new search_thread(root.right, data, tree);
            double start = System.nanoTime();
            Thread t1 = new Thread(s1);
            Thread t2 = new Thread(s2);
            t1.start();
            t2.start();
            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            double end = System.nanoTime();
            // time in milliseconds
            double time = (end - start) / 1000000;
            System.out.println("Time taken to search " + data + " with 2 threads is " + time + " ms");
        } else if (x == 4) {
            search_thread s1 = new search_thread(root.left.left, data, tree);
            search_thread s2 = new search_thread(root.left.right, data, tree);
            search_thread s3 = new search_thread(root.right.left, data, tree);
            search_thread s4 = new search_thread(root.right.right, data, tree);
            double start = System.nanoTime();
            Thread t1 = new Thread(s1);
            Thread t2 = new Thread(s2);
            Thread t3 = new Thread(s3);
            Thread t4 = new Thread(s4);
            t1.start();
            t2.start();
            t3.start();
            t4.start();
            try {
                t1.join();
                t2.join();
                t3.join();
                t4.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            double end = System.nanoTime();
            // time in milliseconds
            double time = (end - start) / 1000000;
            System.out.println("Time taken to search " + data + " with 4 threads is " + time + " ms");
        }
    }

    public void threading_create(int x, int arr[], int begin, int n, binary_tree tree) {
        if (x == 2) {
            create_thread c1 = new create_thread(arr, begin, (begin + n) / 2, tree);
            create_thread c2 = new create_thread(arr, (begin + n) / 2, n, tree);
            double start = System.nanoTime();
            Thread t1 = new Thread(c1);
            Thread t2 = new Thread(c2);
            t1.start();
            t2.start();
            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            double end = System.nanoTime();
            // time in milliseconds
            double time = (end - start) / 1000000;
            System.out.println("Time taken to create tree with 2 threads is " + time + " ms");
        } else if (x == 4) {
            create_thread c1 = new create_thread(arr, begin, (begin + n) / 4, tree);
            create_thread c2 = new create_thread(arr, (begin + n) / 4, (begin + n) / 2, tree);
            create_thread c3 = new create_thread(arr, (begin + n) / 2, (begin + n) * 3 / 4, tree);
            create_thread c4 = new create_thread(arr, (begin + n) * 3 / 4, n, tree);
            double start = System.nanoTime();
            Thread t1 = new Thread(c1);
            Thread t2 = new Thread(c2);
            Thread t3 = new Thread(c3);
            Thread t4 = new Thread(c4);
            t1.start();
            t2.start();
            t3.start();
            t4.start();
            try {
                t1.join();
                t2.join();
                t3.join();
                t4.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            double end = System.nanoTime();
            // time in milliseconds
            double time = (end - start) / 1000000;
            System.out.println("Time taken to create tree with 4 threads is " + time + " ms");
        }
    }
}

class height_thread implements Runnable {
    node root;
    binary_tree tree;
    int height;

    height_thread(node root, binary_tree tree) {
        this.root = root;
        this.tree = tree;
    }

    public void run() {
        height = tree.height(root);
    }
}

class search_thread implements Runnable {
    node root;
    int data;
    binary_tree tree;

    search_thread(node root, int data, binary_tree tree) {
        this.root = root;
        this.data = data;
        this.tree = tree;
    }

    public void run() {
        tree.search(root, data);
    }
}

class create_thread implements Runnable {
    int[] arr;
    int first;
    int n;
    binary_tree tree;

    create_thread(int[] arr, int first, int n, binary_tree tree) {
        this.arr = arr;
        this.first = first;
        this.n = n;
        this.tree = tree;
    }

    public void run() {
        tree.createTree(arr, first, n);
    }
}

public class main {
    public static void main(String[] args) {
        int node_size[] = { 10, 1000, 10000,50000,100000,1000000};

        for (int i = 0; i < 6; i++) {
            int arr_nodes[] = new int[node_size[i]];
            // randomly generate nodes between -10**9 to 10**9
            for (int j = 0; j < node_size[i]; j++) {
                arr_nodes[j] = (int) (Math.random() * 2000000000) - 1000000000;
            }

            try {
                FileWriter myWriter = new FileWriter("nodes.txt");
                //write node to be searched in file
                int a=arr_nodes[node_size[i]/2];
                myWriter.write(a+"");
                myWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Number of nodes: " + node_size[i]);
            binary_tree tree = new binary_tree();
            tree.create_tree(arr_nodes, 0, node_size[i]);
            System.out.println("\n");
            node n1 =tree.getRoot();
            tree.time_height(n1);
            tree.threading_height(2, tree);
            tree.threading_height(4, tree);
            System.out.println("\n");
            tree.time_search(n1, arr_nodes[(node_size[i]) / 2]);
            tree.threading_search(2, arr_nodes[(node_size[i]) / 2], tree);
            tree.threading_search(4, arr_nodes[(node_size[i]) / 2], tree);
            System.out.println("\n");
        }
        //test
    }
}
