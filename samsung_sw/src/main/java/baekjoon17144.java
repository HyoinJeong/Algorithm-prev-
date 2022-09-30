import java.util.*;
import java.io.*;

public class baekjoon17144 {
    static int R,C,T,map[][];
    static int[] dx={1, -1, 0, 0};
    static int[] dy={0, 0, 1, -1};

    static class Dust{
        int x,y,w;

        public Dust(int x, int y, int w){
            this.x=x;
            this.y=y;
            this.w=w;

        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken()); // 행
        C = Integer.parseInt(st.nextToken()); // 열
        T = Integer.parseInt(st.nextToken()); // 초

        map=new int[R][C];
        int clean=-1;

        for(int i=0; i<R; i++){
            st=new StringTokenizer(br.readLine());
            for(int j=0; j<C;j++){
                int a=Integer.parseInt(st.nextToken());
                map[i][j]=a;
                if (a==-1&&clean==-1)
                    clean=i;
            }
        }

        while (T-->0){
            spread();
            move(clean);
        }

        int sum = 0;
        for(int i = 0; i<R; i++)
            for (int j = 0; j < C; j++)
                sum += map[i][j];

        System.out.println(sum+2);
    }

    // 미세먼지 확산
    public static void spread(){
        List<Dust> list = new ArrayList<>();

        // 확산될 미세먼지 저장
        for(int i=0; i<R; i++){
            for(int j=0; j<C;j++){
                if(map[i][j]>=5)
                    list.add(new Dust(i,j,map[i][j]));
            }
        }

        for (Dust dust : list){
            int count=0;

            int r=dust.x;
            int c= dust.y;
            int w= dust.w;

            for (int i=0; i<4; i++){
                int dr=r+dx[i];
                int dc=c+dy[i];

                if (dr<0 || dc<0 || dr>=R || dc>=C)
                    continue;
                if (map[dr][dc]==-1)
                    continue;

                map[dr][dc]=map[dr][dc]+w/5;
                count++;
            }

            map[r][c]=map[r][c]-w/5*count;
        }
    }

    // 공기청정기 가동
    public static void move(int clean){
        // 윗부분(반시계방향)
        // 아래로 당기기
        for(int i=clean-1; i>0; i--)
            map[i][0]=map[i-1][0];
        // 왼쪽으로 당기기
        for (int i=0; i<C-1; i++)
            map[0][i]=map[0][i+1];
        // 위로 당기기
        for(int i=0; i<clean; i++)
            map[i][C-1]=map[i+1][C-1];
        // 오른쪽으로 당기기
        for(int i=C-1; i>1; i--)
            map[clean][i]=map[clean][i-1];
        map[clean][1]=0;

        // 아래부분(시계방향)
        int down=clean+1;
        // 위로 당기기
        for(int i=down+1; i<R-1; i++)
            map[i][0]=map[i+1][0];
        // 왼쪽으로 당기기
        for(int i=0; i<C-1; i++)
            map[R-1][i]=map[R-1][i+1];
        // 아래로 당기기
        for(int i=R-1; i>down; i--)
            map[i][C-1]=map[i-1][C-1];
        // 오른쪽으로 당기기
        for(int i=C-1; i>1; i--)
            map[down][i]=map[down][i-1];
        map[down][1]=0;
    }
}
