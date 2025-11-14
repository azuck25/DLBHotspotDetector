import java.io.*;
import java.util.*;


/*
 * An implementation of the DLB Trie 
 *   from
 * Algorithm 5.4 Trie symbol table

    Robert, Sedgewick; Wayne Kevin. Algorithms (pp. 736-737). Pearson Education. Kindle Edition. 
 */

public class DLB {
    //declare root node of DLB
    private DLBnode root;
    private static String filename;
    private static Scanner s1;
    private static Locale usLocale = new Locale("en", "US");
    
    //main function to build trie from file input
    public static void main(String[] args){
        
        filename = args[0];


        if(args.length > 0){
            try {
                //Set the input to recieve a text data stream from standard input
                
                System.setIn(new java.io.FileInputStream("passwords.txt"));
                
            } catch (java.io.FileNotFoundException e) {
            }
        }
        
        //declare an instance of the data structure
        DLB st = new DLB();
        s1 = getScanner();
        
        System.out.println("Adding keys from file...\n\n");
        
        while(!s1.hasNext()){
            String key = s1.next();
            st.put(key);
        }

    //     System.out.println("\nEnumerating all keys in the trie:");
    //     System.out.println("=================================");
    // //     for (String key : st.keys()) {
    // //     System.out.println("(" + key + ": " + st.contains(key) + ")");
    // //   }
    }
    //static {s1.useLocale(usLocale);}

    //file handling
    private static Scanner getScanner(){
        try {
            System.setIn(new java.io.FileInputStream(filename));
        } catch (Exception e) {
            System.out.println("File not found.\n");
        }
        Scanner scanner = new Scanner(new BufferedInputStream(System.in),"UTF-8");
        return scanner;
    }

    public void put(String key){
        if (key == null)
            throw new IllegalArgumentException("called put() on a null key.");
        if(key.length() == 0){
            return;
        }

        if(root == null){
            root = new DLBnode(key.charAt(0), 0);
        }
        DLBnode scout = root;
        

        for(int i =0; i < key.length(); i++){
            char c = key.charAt(i);
            while(scout.sibling != null){
                if(scout.letter == c){
                    break;
                }
                scout = scout.sibling;
            }

            if(i != key.length() - 1){
                char next = key.charAt(i + 1);
                if(scout.child == null){
                    scout.child = new DLBnode(next, i);
                    scout = scout.child;
                }
                else if(scout.child != null){
                    scout = scout.child;
                }
            }
        }
        scout.isWord = true;
    }

    public Iterable<String> keys(){
        Queue<String> queue = new Queue<>();
        collect(root,queue,new StringBuilder());
        return queue;
    }

    public void collect(DLBnode x, Queue<String> queue, StringBuilder scout){
        if(x == null)
            return;
        DLBnode curr = x;
        while(curr != null) {
            scout.append(curr.letter);
            if(curr.isWord){
                queue.enqueue(scout.toString());
            }
            collect(curr.child,queue,scout);
            scout.deleteCharAt(scout.length() - 1);
            curr = curr.sibling;
        }
    }


    
    private class DLBnode{
        private char letter;
        private boolean isWord;
        private DLBnode sibling;
        private DLBnode child;
        private int level;

        private DLBnode(char c, int level){
            this.letter = c;
            this.level = level;
        }
    }

}

