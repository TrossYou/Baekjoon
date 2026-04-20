package Java.G3_1516;

import java.io.*;
import java.util.*;


public class Main {
    static int N;
    static Queue<Node> waitQ;
    static boolean[] isDone;
    static Node[] instances;
    static int[] minTime;

    static class Node{
        int idx, t;
        ArrayList<Integer> parents;

        Node(int idx, int t){
            
            this.t = t;
            parents = new ArrayList<>();
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        minTime = new int[N+1];
        waitQ = new LinkedList<>();

        instances = new Node[N+1]; // 객체 저장 
        isDone = new boolean[N+1];

        StringTokenizer st;
        boolean isPossible = true;
        for(int i = 0; i< N; i++){
            st = new StringTokenizer(br.readLine());
            int t = Integer.parseInt(st.nextToken());
            Node node = new Node(i, t);
            instances[i] = node;
            isPossible = true;
            while(true){ 
                int p = Integer.parseInt(st.nextToken());
                if(p == -1) break; 
                node.parents.add(p);
                if(!isDone[p]) isPossible = false;
            }

            if(isPossible){
                int maxP = 0;
                for(int p : node.parents){
                    maxP = Math.max(maxP, instances[p].t);
                }
                minTime[i] = maxP + t;
                isDone[i] = true;
            }else{
                waitQ.add(node);
            }
        }

        while(!waitQ.isEmpty()){
            Node n = waitQ.poll();
            if(isPossible(n.parents)){
                int maxP = 0;
                for()
            }else{
                waitQ.add(n);
            }
        }
    }    

    public static boolean isPossible(List<Integer> parents){
        for(int p : parents){
            if(!isDone[p]) return false;
        }
        return true;
    }
    
}   
