// package boj1012;  // 패키지 선언 (주석 처리됨)

import java.io.*;  // 입출력 관련 클래스 임포트
import java.util.*;  // 유틸리티 관련 클래스 임포트

public class Main {

    public static void main(String[] args) throws Exception {
        // System.setIn(new FileInputStream("src/input.txt"));  // 파일 입력 설정 (주석 처리됨)
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  // 표준 입력을 받기 위한 BufferedReader
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));  // 표준 출력을 위한 BufferedWriter

        int T = Integer.parseInt(br.readLine());  // 테스트 케이스 수 입력 받기
        for (int t = 0; t < T; t++) {  // 테스트 케이스 수만큼 반복
            StringTokenizer st = new StringTokenizer(br.readLine());  // 한 줄을 읽고 공백으로 분리
            int N = Integer.parseInt(st.nextToken());  // 배추밭의 가로 길이
            int M = Integer.parseInt(st.nextToken());  // 배추밭의 세로 길이
            int K = Integer.parseInt(st.nextToken());  // 배추가 심어져 있는 위치의 개수
            // System.out.println(N + " " + M + " " + K);  // 디버깅용 출력 (주석 처리됨)
            visited = new boolean[N][M];  // 방문 여부를 체크하는 2차원 배열
            board = new boolean[N][M];  // 배추의 위치를 저장하는 2차원 배열
            int answer = 0;  // 필요한 지렁이 수를 저장하는 변수
            for (int i = 0; i < K; i++) {  // 배추의 위치를 입력 받아서 board 배열에 저장
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                board[a][b] = true;  // 배추가 있는 위치를 true로 설정
            }
            // System.out.println(Arrays.deepToString(board));  // 디버깅용 출력 (주석 처리됨)
            for (int i = 0; i < N; i++) {  // 배추밭의 모든 위치를 순회
                for (int j = 0; j < M; j++) {
                    // if (dfs1(N, M, i, j)) {  // DFS1을 사용한 경우 (주석 처리됨)
                    //     answer++;
                    // }
                    // if (dfs2(N, M, i, j)) {  // DFS2를 사용한 경우 (주석 처리됨)
                    //     answer++;
                    // }
                    if (bfs(N, M, i, j)) {  // BFS를 사용한 경우
                        answer++;  // 새로운 배추 그룹을 찾았을 때, 지렁이 수 증가
                    }
                }
            }
            bw.write(answer + "");  // 결과 출력
            bw.newLine();  // 줄바꿈
        }        
        bw.flush();  // 출력 버퍼 비우기
        br.close();  // 입력 스트림 닫기
        bw.close();  // 출력 스트림 닫기
    }
    
    public static boolean[][] visited;  // 방문 여부를 저장하는 배열
    public static boolean[][] board;  // 배추의 위치를 저장하는 배열
    
    // DFS1을 사용한 방법 (재귀 호출)
    public static boolean dfs1(int N, int M, int a, int b) {
        if (visited[a][b] || !board[a][b]) {  // 이미 방문했거나 배추가 없는 위치일 경우
            return false;
        }
        visited[a][b] = true;  // 현재 위치를 방문한 것으로 표시
        int[] di = {-1, 1, 0, 0};  // 상하좌우 이동을 위한 배열
        int[] dj = {0, 0, -1, 1};
        for (int d = 0; d < 4; d++) {  // 상하좌우로 이동하면서 DFS 수행
            int ni = a + di[d]; 
            int nj = b + dj[d];
            if (ni < 0 || nj < 0 || ni >= N || nj >= M) continue;  // 범위를 벗어난 경우
            dfs1(N, M, ni, nj);  // 재귀 호출
        }
        return true;  // 새로운 배추 그룹을 찾았음을 반환
    }
    
    // DFS2를 사용한 방법 (스택 사용)
    public static boolean dfs2(int N, int M, int a, int b) {
        if (visited[a][b] || !board[a][b]) {  // 이미 방문했거나 배추가 없는 위치일 경우
            return false;
        }
        int[] di = {-1, 1, 0, 0};  // 상하좌우 이동을 위한 배열
        int[] dj = {0, 0, -1, 1};
        Stack<int[]> stack = new Stack<>();  // 스택 생성
        int[] el = {a, b};
        stack.push(el);  // 현재 위치를 스택에 추가
        while (!stack.isEmpty()) {  // 스택이 빌 때까지 반복
            el = stack.pop();  // 스택에서 위치를 꺼내기
            visited[el[0]][el[1]] = true;  // 방문한 것으로 표시
            for (int d = 0; d < 4; d++) {  // 상하좌우로 이동하면서 DFS 수행
                int ni = el[0] + di[d]; 
                int nj = el[1] + dj[d];
                if (ni < 0 || nj < 0 || ni >= N || nj >= M) continue;  // 범위를 벗어난 경우
                if (visited[ni][nj] || !board[ni][nj]) {
                    continue;
                }
                int[] newEl = {ni, nj};  // 새로운 위치를 배열로 생성
                stack.push(newEl);  // 스택에 추가
            }            
        }
        return true;  // 새로운 배추 그룹을 찾았음을 반환
    }
    
    // BFS를 사용한 방법 (큐 사용)
    public static boolean bfs(int N, int M, int a, int b) {
        if (visited[a][b] || !board[a][b]) {  // 이미 방문했거나 배추가 없는 위치일 경우
            return false;
        }
        int[] di = {-1, 1, 0, 0};  // 상하좌우 이동을 위한 배열
        int[] dj = {0, 0, -1, 1};
        Queue<int[]> queue = new LinkedList<>();  // 큐 생성
        int[] el = {a, b};
        queue.offer(el);  // 현재 위치를 큐에 추가
        while (!queue.isEmpty()) {  // 큐가 빌 때까지 반복
            el = queue.poll();  // 큐에서 위치를 꺼내기
            visited[el[0]][el[1]] = true;  // 방문한 것으로 표시
            for (int d = 0; d < 4; d++) {  // 상하좌우로 이동하면서 BFS 수행
                int ni = el[0] + di[d]; 
                int nj = el[1] + dj[d];
                if (ni < 0 || nj < 0 || ni >= N || nj >= M) continue;  // 범위를 벗어난 경우
                if (visited[ni][nj] || !board[ni][nj]) {
                    continue;
                }
                int[] newEl = {ni, nj};  // 새로운 위치를 배열로 생성
                queue.offer(newEl);  // 큐에 추가
            }            
        }
        return true;  // 새로운 배추 그룹을 찾았음을 반환
    }
}
