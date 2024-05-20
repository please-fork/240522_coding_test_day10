// package boj2178;  // 패키지 선언 (주석 처리됨)

import java.io.*;  // 입출력 관련 클래스 임포트
import java.util.*;  // 유틸리티 관련 클래스 임포트

public class Main {

    public static void main(String[] args) throws Exception {
        // System.setIn(new FileInputStream("src/input.txt"));  // 파일 입력 설정 (주석 처리됨)
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));  // 표준 입력을 받기 위한 BufferedReader
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));  // 표준 출력을 위한 BufferedWriter

        StringTokenizer st = new StringTokenizer(br.readLine());  // 첫 번째 줄 입력을 공백 기준으로 분리
        int N = Integer.parseInt(st.nextToken());  // 미로의 세로 크기
        int M = Integer.parseInt(st.nextToken());  // 미로의 가로 크기
        visited = new boolean[N][M];  // 방문 여부를 체크하는 2차원 배열
        maze = new int[N][M];  // 미로의 상태를 저장하는 2차원 배열

        // 미로 상태 입력 받기
        for (int i = 0; i < N; i++) {
            String tmp = br.readLine();  // 각 줄의 미로 상태를 입력 받음
            for (int j = 0; j < tmp.length(); j++) {
                maze[i][j] = tmp.charAt(j) - '0';  // 문자를 숫자로 변환하여 저장
            }
        }
//      System.out.println(Arrays.deepToString(visited));  // 디버깅용 출력 (주석 처리됨)
        bw.write(bfs(N, M) + "");  // BFS를 수행하여 결과 출력
        bw.flush();  // 출력 버퍼 비우기
        br.close();  // 입력 스트림 닫기
        bw.close();  // 출력 스트림 닫기
    }

    public static boolean[][] visited;  // 방문 여부를 저장하는 배열
    public static int[][] maze;  // 미로의 상태를 저장하는 배열

    // BFS를 사용한 방법 (큐 사용)
    public static int bfs(int N, int M) {
        int[] start = {0, 0, 1};  // 시작 지점과 시작 거리
        Queue<int[]> queue = new LinkedList<>();  // 큐 생성
        queue.offer(start);  // 시작 위치를 큐에 추가

        int[] di = {-1, 1, 0, 0};  // 상하좌우 이동을 위한 배열
        int[] dj = {0, 0, -1, 1};

        while (!queue.isEmpty()) {  // 큐가 빌 때까지 반복
            int[] el = queue.poll();  // 큐에서 위치를 꺼내기
            int i = el[0];  // 현재 위치의 행
            int j = el[1];  // 현재 위치의 열
            int dist = el[2];  // 현재까지의 거리

            if (i == N - 1 && j == M - 1) {  // 도착 지점에 도달한 경우
                return dist;  // 도착 지점까지의 거리 반환
            }

            if (visited[i][j]) {  // 이미 방문한 경우
                continue;  // 다음 위치로 이동
            }

            visited[i][j] = true;  // 현재 위치를 방문한 것으로 표시

            for (int d = 0; d < 4; d++) {  // 상하좌우로 이동하면서 BFS 수행
                int ni = i + di[d];  // 이동 후의 행
                int nj = j + dj[d];  // 이동 후의 열
                if (ni >= 0 && nj >= 0 && ni < N && nj < M && maze[ni][nj] == 1 && !visited[ni][nj]) {  // 이동 가능한 경우
                    queue.offer(new int[]{ni, nj, dist + 1});  // 큐에 추가
                }
            }
        }

        return -1;  // 도착 지점에 도달할 수 없는 경우
    }
}
