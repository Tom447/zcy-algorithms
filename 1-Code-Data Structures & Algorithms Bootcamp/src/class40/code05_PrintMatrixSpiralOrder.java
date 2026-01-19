package class40;

import java.util.Random;

public class code05_PrintMatrixSpiralOrder {

    public static void printatrixSpiralOrder1(int[][] matrix){
        int tR = 0;
        int tC = 0;
        int dR = matrix.length - 1;
        int dC = matrix[0].length - 1;
        while (tR <= dR && tC <= dC) {
            printEdge(matrix, tR++, tC++, dR--, dC--);
        }
    }

    public static void printEdge(int[][] matrix, int tR, int tC, int dR, int dC){

        if (tR == dR){
            for (int i = tC; i <= dC; i++){
                System.out.print(matrix[tR][i] + " ");
            }
        }else if (tC == dC){
            for (int i = tR; i <= dR; i++){
                System.out.print(matrix[i][tC] +" ");
            }
        }else{
            int curC = tC;
            int curR = tR;
            while (curC != dC){
                System.out.print(matrix[curR][curC] + " ");
                curC++;
            }
            while (curR != dR){
                System.out.print(matrix[curR][curC] + " ");
                curR++;
            }
            while (curC != tC){
                System.out.print(matrix[curR][curC] + " ");
                curC--;
            }
            while (curR != tR){
                System.out.print(matrix[curR][curC] + " ");
                curR--;
            }
        }
    }
    // 随机生成矩阵的方法
    public static int[][] randomMatrix(int rows, int cols, int maxValue) {
        Random random = new Random();
        int[][] matrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextInt(maxValue); // 生成从0到maxValue-1之间的随机整数
            }
        }
        return matrix;
    }



    // 打印矩阵的方法
    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int val : row) {
                System.out.printf("%3d ", val);
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        // 定义矩阵的行数和列数
        int rows = 4; // 可以根据需要更改
        int cols = 5; // 可以根据需要更改

        // 随机生成矩阵
        int[][] matrix = randomMatrix(rows, cols, 100); // 100是每个元素的最大值

        // 打印原始矩阵
        System.out.println("Original Matrix:");
        printMatrix(matrix);

        // 按顺时针螺旋顺序打印矩阵
        System.out.println("\nSpiral Order Print:");
        printatrixSpiralOrder1(matrix);
    }
}
