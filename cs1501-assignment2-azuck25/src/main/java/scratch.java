public class scratch {
    public static void main(String[] args) {
        int x = search("word","password123");
        System.out.println(x);
    }
    public static int search(String p, String txt){
        int j,M = p.length();
        int i, N = txt.length();
        System.out.println("p length : " + M);
        System.out.println("txt length : " + N);
        System.out.println();
        for (i = 0,j = 0; i < M && j < N; i++) {
            //System.out.println("i : " + i);
            //System.out.println("j : " + j);
            System.out.println(txt.charAt(i) + " : " + p.charAt(j));
            if(txt.charAt(i) == p.charAt(j)) j++;
            else{i -= j; j=0;}
        }
        if(j==M){
            System.out.println("found"); 
            return i - M;
        }      
        else {
            System.out.println("not found");
            return N;
        }
            
    }
}
