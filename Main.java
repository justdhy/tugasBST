import java.util.*;

public class Main {
    static class Node {
        int data;
        Node kiri, kanan;
        int height;
        
        Node(int data) {
            this.data = data; 
            this.kiri = null;
            this.kanan = null;
            this.height = 1;
        }
    }
    
    static class BST {
        Node root;
        BST() {
            root = null;
        }
        
        private int getHeight(Node node) {
            if (node == null) {
                return 0;
            }
            return node.height;
        }
        
        private int getBalance(Node node) {
            if (node == null) {
                return 0;
            }
            return getHeight(node.kiri) - getHeight(node.kanan);
        }

        private void updateHeight(Node node) {
            if (node != null) {
                node.height = 1 + Math.max(getHeight(node.kiri), getHeight(node.kanan));
            }
        }
        
        private Node rotateRight(Node y) {
            Node x = y.kiri;
            Node T2 = x.kanan;
            x.kanan = y;
            y.kiri = T2;
            
            updateHeight(y);
            updateHeight(x);
            return x;
        }
        private Node rotateLeft(Node x) {
            Node y = x.kanan;
            Node T2 = y.kiri;
            y.kiri = x;
            x.kanan = T2;
            
            updateHeight(x);
            updateHeight(y);
            return y;
        }
        
        public void insert(int data) {
            root = insertRec(root, data);
        }
        private Node insertRec(Node node, int data) {
            if (node == null) {
                return new Node(data);
            }
            if (data < node.data) {
                node.kiri = insertRec(node.kiri, data);
            } else if (data > node.data) {
                node.kanan = insertRec(node.kanan, data);
            } else {
                return node;
            }

            updateHeight(node);
            int balance = getBalance(node);
            if (balance > 1 && data < node.kiri.data) {
                return rotateRight(node);
            }
            if (balance < -1 && data > node.kanan.data) {
                return rotateLeft(node);
            }
            if (balance > 1 && data > node.kiri.data) {
                node.kiri = rotateLeft(node.kiri);
                return rotateRight(node);
            }
            if (balance < -1 && data < node.kanan.data) {
                node.kanan = rotateRight(node.kanan);
                return rotateLeft(node);
            }
            return node;
        }
        
        public void delete(int data) {
            root = deleteRec(root, data);
        }
        private Node deleteRec(Node node, int data) {
            if (node == null) {
                return null;
            }
            // node yg akan dihapus
            if (data < node.data) {
                node.kiri = deleteRec(node.kiri, data);
            } else if (data > node.data) {
                node.kanan = deleteRec(node.kanan, data);
            } else {
                //leaf node
                if (node.kiri == null && node.kanan == null) {
                    return null;
                }
                // 1 anak
                if (node.kiri == null) {
                    return node.kanan;
                }
                if (node.kanan == null) {
                    return node.kiri;
                }  
                // 2 anak
                Node minNode = findMin(node.kanan);
                node.data = minNode.data;
                node.kanan = deleteRec(node.kanan, minNode.data);
            }
    
            updateHeight(node);        
            int balance = getBalance(node);
            if (balance > 1 && getBalance(node.kiri) >= 0) {
                return rotateRight(node);
            }
            
            if (balance > 1 && getBalance(node.kiri) < 0) {
                node.kiri = rotateLeft(node.kiri);
                return rotateRight(node);
            }
            
            if (balance < -1 && getBalance(node.kanan) <= 0) {
                return rotateLeft(node);
            }
            
            if (balance < -1 && getBalance(node.kanan) > 0) {
                node.kanan = rotateRight(node.kanan);
                return rotateLeft(node);
            }
            
            return node;
        }
        
        private Node findMin(Node root) {
            while (root.kiri != null) {
                root = root.kiri;
            }
            return root;
        }
        
        public void inOrder() {
            inOrderRec(root);
            System.out.println();
        }
    
        private void inOrderRec(Node root) {
            if (root != null) {
                inOrderRec(root.kiri);
                System.out.print(root.data + " ");
                inOrderRec(root.kanan);
            }
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BST bst = new BST();
        
        System.out.print("Berapa banyak angka yang ingin dimasukkan? ");
        int n = sc.nextInt();
        System.out.println("Masukkan " + n + " angka:");
        for (int i = 0; i < n; i++) {
            int x = sc.nextInt();
            bst.insert(x);
        }
        System.out.print("\nBST setelah insert (InOrder): ");
        bst.inOrder();
        
        System.out.print("\nMasukkan angka yang ingin dihapus: ");
        int del = sc.nextInt();
        
        bst.delete(del);
        
        System.out.print("BST setelah delete: ");
        bst.inOrder();
        
        sc.close();
    }

}