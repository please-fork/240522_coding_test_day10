// package boj21736;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        // System.setIn(new FileInputStream("src/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken()); // 행의 수
        int M = Integer.parseInt(st.nextToken()); // 열의 수
        // System.out.println(N + " " + M);
        
        visited = new boolean[N][M]; // 방문 여부를 저장할 배열
        board = new char[N][M]; // 보드를 저장할 배열

        int a = -1; // 시작점의 행 인덱스
        int b = -1; // 시작점의 열 인덱스
        for (int i = 0; i < N; i++) {
            String tmp = br.readLine();
            for (int j = 0; j < M; j++) {
                board[i][j] = tmp.charAt(j);
                if (board[i][j] == 'I') { // 시작점 찾기
                    a = i;
                    b = j;
                }
            }
        }
        
        // System.out.println(Arrays.deepToString(board));
        // System.out.println(a + " " + b);
        
        // dfs1(N, M, a, b); // 재귀를 이용한 DFS
        // dfs2(N, M, a, b); // 스택을 이용한 DFS
        bfs(N, M, a, b); // 큐를 이용한 BFS
        
        if (answer == 0) {
            bw.write("TT");
        } else {
            bw.write(answer + "");            
        }
        bw.flush();
        br.close();
        bw.close();
    }
    
    public static boolean[][] visited; // 방문 여부 배열
    public static char[][] board; // 보드 배열
    public static int answer = 0; // 만난 사람의 수

    // 재귀를 이용한 DFS
    public static void dfs1(int N, int M, int a, int b) {
        // 델타 탐색을 위한 방향 배열
        int[] di = {-1, 1, 0, 0}; // 행의 변화: 위, 아래, 변화 없음, 변화 없음
        int[] dj = {0, 0, -1, 1}; // 열의 변화: 변화 없음, 변화 없음, 왼쪽, 오른쪽
        // System.out.println(a + " " + b);
        for (int d = 0; d < 4; d++) {
            int ni = a + di[d]; // 현재 위치에서 행의 변화 적용
            int nj = b + dj[d]; // 현재 위치에서 열의 변화 적용
            if (ni < 0 || nj < 0 || ni >= N || nj >= M) continue; // 경계 체크: 보드 범위를 벗어나는지 확인
            if (visited[ni][nj]) continue; // 방문 여부 체크: 이미 방문한 위치인지 확인
            visited[ni][nj] = true; // 방문 처리
            // System.out.println(board[ni][nj]);
            if (board[ni][nj] == 'P') {
                answer++; // 만난 사람의 수 증가
            }
            if (board[ni][nj] != 'X') {
                dfs1(N, M, ni, nj); // 재귀 호출로 다음 위치 탐색
            }
        }
    }
    
    // 스택을 이용한 DFS
    public static void dfs2(int N, int M, int a, int b) {
        // 델타 탐색을 위한 방향 배열
        int[] di = {-1, 1, 0, 0}; // 행의 변화: 위, 아래, 변화 없음, 변화 없음
        int[] dj = {0, 0, -1, 1}; // 열의 변화: 변화 없음, 변화 없음, 왼쪽, 오른쪽
        Stack<int[]> stack = new Stack<>();
        int[] el = {a, b};
        stack.push(el);
        while (!stack.isEmpty()) {
            int[] val = stack.pop();
            // System.out.println(Arrays.toString(val));
            for (int d = 0; d < 4; d++) {
                int ni = val[0] + di[d]; // 현재 위치에서 행의 변화 적용
                int nj = val[1] + dj[d]; // 현재 위치에서 열의 변화 적용
                if (ni < 0 || nj < 0 || ni >= N || nj >= M) continue; // 경계 체크: 보드 범위를 벗어나는지 확인
                if (visited[ni][nj]) continue; // 방문 여부 체크: 이미 방문한 위치인지 확인
                visited[ni][nj] = true; // 방문 처리
                // System.out.println(board[ni][nj]);
                if (board[ni][nj] == 'P') {
                    answer++; // 만난 사람의 수 증가
                }
                if (board[ni][nj] != 'X') {
                    int[] newVal = {ni, nj};
                    stack.push(newVal); // 스택에 다음 위치 추가
                }
            }
        }
    }
    
    // 큐를 이용한 BFS
    public static void bfs(int N, int M, int a, int b) {
        // 델타 탐색을 위한 방향 배열
        int[] di = {-1, 1, 0, 0}; // 행의 변화: 위, 아래, 변화 없음, 변화 없음
        int[] dj = {0, 0, -1, 1}; // 열의 변화: 변화 없음, 변화 없음, 왼쪽, 오른쪽
        Queue<int[]> queue = new LinkedList<>();
        int[] el = {a, b};
        queue.offer(el);
        while (!queue.isEmpty()) {
            int[] val = queue.poll();
            // System.out.println(Arrays.toString(val));
            for (int d = 0; d < 4; d++) {
                int ni = val[0] + di[d]; // 현재 위치에서 행의 변화 적용
                int nj = val[1] + dj[d]; // 현재 위치에서 열의 변화 적용
                if (ni < 0 || nj < 0 || ni >= N || nj >= M) continue; // 경계 체크: 보드 범위를 벗어나는지 확인
                if (visited[ni][nj]) continue; // 방문 여부 체크: 이미 방문한 위치인지 확인
                visited[ni][nj] = true; // 방문 처리
                // System.out.println(board[ni][nj]);
                if (board[ni][nj] == 'P') {
                    answer++; // 만난 사람의 수 증가
                }
                if (board[ni][nj] != 'X') {
                    int[] newVal = {ni, nj};
                    queue.offer(newVal); // 큐에 다음 위치 추가
                }
            }
        }
    }
}
