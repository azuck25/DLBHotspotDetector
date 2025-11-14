import java.util.*;



public class DLBHotspotDetector implements HotspotDetector {

    // ----------------------------
    // Interface methods (TODO)
    // ----------------------------
    private static DLB dlbSource, dlbSubstrings;
    public static Set<Hotspot> hotspotStruct = new LinkedHashSet<>();
    public static Set<String> subStrings = new LinkedHashSet<>();
    public static DLB.countStruct countStructure;
    private Iterable<String> q1, q2;
    

    public static void main(String[] args){
        DLBHotspotDetector det = new DLBHotspotDetector();
        det.addLeakedPassword(args[0], 1, 6);
    }

    // public static void extractSubstring(String keys, DLB node, int keyStart){
    //         int length = keys.length();
    //         int keyIndex = keyStart + 2;

    //         boolean isEnd1 = false;
    //         boolean found = false;
    //         int count = 0;
    //         boolean foundDup = false;
    //         //System.out.println("(" + keys + ")");
            

            
    //         while (!isEnd1) {
    //             if(keyIndex < length && length > 1){++keyIndex;}else{break;}
    //             String subString = keys.substring(keyStart, keyIndex);
                
    //             //check against the queue for duplicate keys before adding to DLB
    //             if(!queue.isEmpty() && queue.contains(subString) || subString.length()  <= 3 && subString.length() <= 6){
    //                 foundDup = true;
    //             }
                
    //             if(!foundDup){
    //                 count = node.checkSubstring(subString);
    //                 if(count >= 10000){
    //                     found = true;
    //                 }
    //             }
    //             foundDup = false;
                
    //             if(found){
    //                 System.out.println(subString + " : " + count);
    //                 queue.add(subString);
    //                 found = false;
    //             }
        
    //         }
    //         if(keyStart != length - 1){extractSubstring(keys, node, ++keyStart);}
    // }

    @Override
    public void addLeakedPassword(String filename, int minN, int maxN) {
        if (filename == null)
            throw new IllegalArgumentException("null leakedPassword");
        if (minN < 1 || maxN < minN)
            throw new IllegalArgumentException("invalid n-range");
        try {
            dlbSource = new DLB();      
            dlbSource = dlbSource.createDLB(filename,null);
            q1 = dlbSource.keys();   
        } catch (Exception e) {
            System.out.println(e);
        }
        //For the keys within the queue, we start at the first position within
        //the queue. How we define the window of the prefix of the words should be
        //constrained by MinN and MaxN. 
        
        // for(String keys : q1){
        //     int keyStart = 0;

        //     if(keys.length() >= 3){
        //         extractSubstring(keys, dlbSource,keyStart);
        //     }
        // }
        try {
            subStrings = dlbSource.returnSubstrings();
            hotspotStruct = dlbSource.returnHotspotStruct();
            dlbSubstrings = new DLB();
            dlbSubstrings = dlbSubstrings.createDLB(null, subStrings);

        } catch (Exception e) {
            System.out.println("Error : " + e);
        }
        int sum = 0;
        for(String substringsString : dlbSubstrings.keys()){
            System.out.println(substringsString);
            sum += 1;
        }
        System.out.println(sum);
        System.out.println(dlbSource.contains("password."));
        System.out.println(dlbSubstrings.contains("password"));
        System.out.println();


        // TODO:
        // 1) Optionally clear a per-password "seen" set to compute docFreq on first
        // occurrence only.
        // Set<String> seen = new HashSet<>();
        //
        // 2) For each n in [minN..maxN], slide a window over leakedPassword and extract
        // substrings.
        // For each substring s = leakedPassword[i..i+n):
        // - Insert s into the DLB (create nodes as needed).
        // - Mark terminal node, increment freq.
        // - If password not in 'seen', increment docFreq and add to 'seen'.
        // - Update begin/middle/end counts based on position i and (i+n == len).

    }

    @Override
    public Set<Hotspot> hotspotsIn(String candidatePassword) {
        if (candidatePassword == null)
            throw new IllegalArgumentException("null candidatePassword");

        // TODO:
        // - For i = 0..candidate.length()-1:
        // Start at the root of the DLB.
        // Walk character by character (candidate.charAt(j)) until no matching child
        // exists.
        // Every time you reach a terminal node, that substring is a hotspot → aggregate
        // it.
        return new LinkedHashSet<>();
    }
}
