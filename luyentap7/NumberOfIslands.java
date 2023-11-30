package lab13.baitapthem.luyentap7;

public class NumberOfIslands {
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int rows = grid.length;
        int cols = grid[0].length;
        int islandsCount = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '1') {
                    islandsCount++;
                    dfs(grid, i, j);
                }
            }
        }

        return islandsCount;
    }

    private void dfs(char[][] grid, int row, int col) {
        int rows = grid.length;
        int cols = grid[0].length;

        if (row < 0 || col < 0 || row >= rows || col >= cols || grid[row][col] == '0') {
            return;
        }

        grid[row][col] = '0'; // Mark the current cell as visited

        // Check all eight directions
        int[] dr = {-1, 0, 1, 0, -1, -1, 1, 1};
        int[] dc = {0, 1, 0, -1, -1, 1, -1, 1};

        for (int i = 0; i < 8; i++) {
            int newRow = row + dr[i];
            int newCol = col + dc[i];
            dfs(grid, newRow, newCol);
        }
    }

    public static void main(String[] args) {
        NumberOfIslands numberOfIslands = new NumberOfIslands();

        char[][] grid = {
                {'1', '1', '0', '0', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '1', '0', '0'},
                {'0', '0', '0', '1', '1'}
        };

        int result = numberOfIslands.numIslands(grid);
        System.out.println("Number of islands: " + result);
    }
}
