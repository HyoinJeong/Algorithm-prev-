import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;



public class Baekjoon1260 {
    public static int N;
    public static int M;
    public static int V;
    public static int [][] branch;
    public static boolean[] visit;
    public static Queue<Integer> queue;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        N = scanner.nextInt(); // 정점
        M = scanner.nextInt(); // 간선
        V =scanner.nextInt(); // 시작 번호

        branch=new int[1001][1001];
        visit=new boolean[1001];

        // 인접행렬
        for (int i=0; i<M; i++){
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            // 양방향 연결 잊지말자
            branch[a][b]=branch[b][a]=1;
        }

        dfs(V);
        System.out.println();

        // dfs 끝난 후 visit 초기화
        Arrays.fill(visit,false);
        bfs(V);
    }

    private static void dfs(int start) {
        visit[start]=true;
        System.out.print(start+" ");
        for (int i=1;i<=N;i++){
            if (branch[start][i]==1 && visit[i]==false){
                dfs(i);
            }
        }
    }

    private static void bfs(int start) {
        queue=new LinkedList<Integer>();
        queue.add(start);
        visit[start]=true;
        System.out.print(start+" ");

        while (!queue.isEmpty()){
            int tmp= queue.poll();

            for (int i=1;i<branch.length;i++){
                if(branch[tmp][i]==1&& !visit[i]){
                    queue.add(i);
                    visit[i]=true;
                    System.out.print(i+" ");
                }
            }
        }
    }

}
