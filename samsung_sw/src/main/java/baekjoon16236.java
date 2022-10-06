import java.util.*;
import java.io.*;

public class baekjoon16236 {
    private static class Fish{
        int x,y,time;

        public Fish(int x, int y, int time){
            super();
            this.x=x;
            this.y=y;
            this.time=time;
        }
    }

    static int N;
    static int map [][];
    static boolean visit [][];

    static Fish shark; // 상어의 위치 기록
    static int size = 2;
    static int eaten = 0;

    static ArrayList<Fish> feedList = new ArrayList<>();
    static int answer;

    static int dx [] = {-1,1,0,0}; // 좌우상하
    static int dy [] = {0,0,-1,1};

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.valueOf(st.nextToken());
        map = new int [N][N];
        visit = new boolean [N][N];

        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for (int j=0; j<N; j++){
                map[i][j]=Integer.valueOf(st.nextToken());
                if(map[i][j]==9){
                    shark=new Fish(i,j,0);
                    map[i][j]=0;
                }
            }
        }

        move();
        bw.write(answer+ "\n");
        bw.flush();
        bw.close();
    }

    private static void move(){
        Queue<Fish> q = new LinkedList<>();
        q.add(shark);
        visit[shark.x][shark.y]=true;

        // 현재 상어의 위치 기준으로 먹이 리스트와 이동 가능한 경로 찾기(나중에 상어의 위치를 옮기면 다시 시작)
        while(true){
            while(!q.isEmpty()){
                Fish now = q.poll();
                int time = now.time;

                for(int i=0; i<4; i++){
                    int nextX=now.x+dx[i];
                    int nextY=now.y+dy[i];

                    if(nextX<0 || nextY<0 || nextX>=N || nextY>=N || visit[nextX][nextY])
                        continue;

                    // 식사 가능
                    if (map[nextX][nextY]<size && map[nextX][nextY]!=0){
                        feedList.add(new Fish(nextX,nextY,time+1));
                        q.add(new Fish(nextX,nextY,time+1));
                        visit[nextX][nextY]=true;
                    }

                    // 이동 가능
                    if (map[nextX][nextY]==0 || map[nextX][nextY]==size){
                        q.add(new Fish(nextX,nextY,time+1));
                        visit[nextX][nextY]=true;
                    }
                }
            }

            if (!feedList.isEmpty()){
                eat();

                // 식사가 끝나면 방문 배열 초기화
                q.clear();
                visit=new boolean[N][N];

                // 다시 이동하는 큐에 현재 상어 추가
                q.add(shark);
                visit[shark.x][shark.y]=true;
            }
            else
                return;
        }
    }

    private static void eat(){
        // 정렬을 통해 우선순위별로 먹이 리스트 재정렬
        Collections.sort(feedList, new Comparator<Fish>(){
            @Override
            public int compare(Fish o1, Fish o2){
                if (o1.time==o2.time){
                    if (o1.x==o2.x){
                        if (o1.y>o2.y)
                            return 1;
                        else
                            return -1;
                    }else {
                        if (o1.x>o2.x)
                            return 1;
                        else
                            return -1;
                    }
                }else if (o1.time> o2.time)
                    return 1;
                else
                    return -1;
            };
        });


        Fish now = feedList.get(0);
        shark.x= now.x;
        shark.y= now.y;
        eaten++;

        if (eaten==size){
            size++;
            eaten=0;
        }

        answer+= now.time;

        // 먹으면 해당 위치 0
        map[now.x][now.y]=0;

        // 먹이 리스트 초기화
        feedList.clear();
    }
}
