import java.io.*;
import java.util.*;

public class Main {
    static class DirectoryNode{
        String name;
        TreeMap<String, DirectoryNode> children;

        DirectoryNode(String name){
            this.name = name;
            children = new TreeMap<>();
        }

        public void insert(String[] parts, int idx) {
            if (idx >= parts.length) return;
            
            String dirName = parts[idx];
            DirectoryNode child = children.get(dirName);
            if (child == null) {
                child = new DirectoryNode(dirName);
                children.put(dirName, child);
            }
            child.insert(parts, idx + 1);
        }

        public void print(StringBuilder sb, int depth){
            if(this.name != null) {
                for(int d = 1 ; d < depth ; d++) sb.append(' ');
                sb.append(this.name).append('\n');
            }
            for(DirectoryNode node : children.values()){
                node.print(sb, depth + 1);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        DirectoryNode root = new DirectoryNode(null);
        for(int i = 0 ; i < N ; i++){
            String[] parts = br.readLine().split("\\\\");
            root.insert(parts, 0);
        }

        StringBuilder sb = new StringBuilder();
        root.print(sb,0);
        System.out.print(sb);
    }
}
