import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


/*
 * An implementation of the DLB Trie 
 *   from
 * Algorithm 5.4 Trie symbol table

    Robert, Sedgewick; Wayne Kevin. Algorithms (pp. 736-737). Pearson Education. Kindle Edition. 
 */

public class DLB {
    //declare root node of DLB
    private DLBnode root;
    public static countStruct rootcS;
    private static String filename;
    public static Integer minAppearance = 50;
    public static Set<Hotspot> q = new LinkedHashSet<>();
    public static Set<String> subStrings = new LinkedHashSet<>(); 
    public static AtomicInteger count = new AtomicInteger(0);
    public static AtomicInteger charI = new AtomicInteger(0);
    private static final Locale usLocale = new Locale("en", "US");
  
    
    //main function to build trie from file input
    public static void main(String[] args){
        
        if(args.length > 0){
            filename = args[0];
            System.out.println(filename);
            
            // try {
            //     //Set the input to recieve a text data stream from standard input
            //     System.setIn(new java.io.FileInputStream(args[0]));
            // } catch (java.io.FileNotFoundException e) {
            // }
        }
        // //declare an instance of the data structure
        // DLB st = new DLB();

        // // initialize scanner after filename is set
        // s1 = getScanner();
        // s1.useLocale(usLocale);

        // System.out.println("Adding keys from file...\n\n");

        // while(s1.hasNext()){
        //     String key = s1.next();
        //     st.put(key);
        // }
        // System.out.println("Finished adding keys.\n\n");

        // for(String key : st.keys()){
        //     System.out.println("(" + key + ": " + st.contains(key) + ")\n");
        // }


    }
    
    
    //file handling
    public DLB createDLB(String filename, Set<String> h){
        DLB st = new DLB();
        
        if(filename != null){
            try {
            System.out.println("cwd=" + System.getProperty("user.dir"));
            File f = new File(filename);
            System.out.println("Trying to open: " + f.getAbsolutePath() + " exists=" + f.exists());
            System.setIn(new java.io.FileInputStream(f));
        } catch (Exception e) {
            System.out.println("File not found " + e);
        }
        Scanner scanner = new Scanner(new BufferedInputStream(System.in),"UTF-8");
        scanner.useLocale(usLocale);
        
        while(scanner.hasNext()){
            String key = scanner.next();
            st.put(key);
        }
        }
        else{
            for(String key : h){
                st.put(key);
            }
        }
        
        return st;
    
    }
    //private static Scanner s1 = getScanner();
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
        StringBuilder g = new StringBuilder();
        for(int i = 0; i < key.length(); i++){
            char c = key.charAt(i);
            while(scout.sibling != null){
                if(scout.letter == c){
                    g.append(scout.letter);
                    if(scout.isWord && i == key.length()-1){
                        Hotspot s = new Hotspot(key, 0, 0, 0, 0, 0, false, 0, false);
                        String string = g.toString();
                        if(g.length() >= 3){
                            //System.out.println(g.toString());
                            s.ngram = string;
                            s.docFreq++;
                            q.add(s);
                            subStrings.add(string);
                        }
                    }
                    break;
                }
                if(g.length() > 0){g.delete(0, g.length()-1);}
                scout = scout.sibling;
            }
            if(scout.letter != c){
                scout.sibling = new DLBnode(c,i);
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


    public Set<Hotspot> returnHotspotStruct(){
        return q;
    }

    public Set<String> returnSubstrings(){
        return subStrings;
    }
    
    // public void isSubstring(String pattern, DLBnode x, StringBuilder scout){   
    //     if(pattern == null){
    //         throw new IllegalArgumentException("calling get() on a null key.");
    //     }
    //     DLBnode curr = x; 
      
    //     //System.out.println(pattern.length());
    //     while(curr != null && count.intValue() < 10000){
           
    //        scout.append(curr.letter);
    //        //System.out.println(curr.letter);
    //        if(curr.isWord){
    //             for(int i = 0; i < scout.length(); i++){
    //                 char a = pattern.charAt(charI.intValue());
    //                 //System.out.println(a + " : " + scout.charAt(i));
                    
    //                 if(scout.charAt(i) == a){
    //                     boolean k = false;
    //                     if (charI.intValue() == pattern.length()-1) {
    //                         k = true;
    //                         count.incrementAndGet();
    //                         charI.getAndSet(0);
                            
    //                     }
    //                     if(k != true){
    //                         charI.incrementAndGet();
    //                     }
    //                 }
    //                 else{
    //                     charI.getAndSet(0);
    //                 }
    //             }
    //        }
    //        isSubstring(pattern,curr.child,scout);
    //        scout.deleteCharAt(scout.length()-1);
    //        curr = curr.sibling;
    //        charI.getAndSet(0);
    //     }
    // }

    // public int checkSubstring(String pattern){
    //     isSubstring(pattern, root, new StringBuilder());
    //     int checkCount = count.intValue();
    //     charI.getAndSet(0);
    //     count.getAndSet(0);
    //     return checkCount;
    // }

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

    public Iterable<String> keys(){
        Queue<String> queue = new Queue<>();
        collect(root,queue,new StringBuilder());
        return queue;
    }

    public boolean contains(String key){

        if(key == null)
            throw new IllegalArgumentException("calling get() on a null key");
        
        if(root == null)return false;
        if(root != null){
            DLBnode scout = root;
            for (int i = 0; i < key.length(); i++) {
                if (scout == null)
                    return false;
                char c = key.charAt(i);
                while (scout.sibling != null ) { 
                    if(scout.letter == c && i < (key.length())){
                        break;
                    }
                    scout = scout.sibling;
                }          
                if(i < key.length()-1)
                    scout = scout.child;

                }
                if (scout.isWord == false)
                    return false;
        }
        return true;
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

    public class countStruct{
        private int initDocCount;
        private countStruct next;
        private String s;
        
        public countStruct(String s, int initDocCount) {
            this.s = s;
            next = null;
            this.initDocCount += initDocCount;
        }

    }
    

}

