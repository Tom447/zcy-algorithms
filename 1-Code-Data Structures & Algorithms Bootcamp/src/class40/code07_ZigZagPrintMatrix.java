package class40;

public class code07_ZigZagPrintMatrix {




//    tR, tC：当前层的右上角（起点）
//    dR, dC：当前层的左下角（终点）


        public static void printMatrixZigZag(int[][] matrix) {
            int tR = 0;
            int tC = 0;
            int dR = 0;
            int dC = 0;
            int endR = matrix.length - 1;
            int endC = matrix[0].length - 1;
            boolean fromUp = false;
            while (tR != endR + 1) {
                printLevel(matrix, tR, tC, dR, dC, fromUp);
                tR = tC == endC ? tR + 1 : tR;
                tC = tC == endC ? tC : tC + 1;
                dC = dR == endR ? dC + 1 : dC;
                dR = dR == endR ? dR : dR + 1;
                fromUp = !fromUp;
            }
            System.out.println();
        }
        //    tR, tC：当前层的右上角（起点）
        //    dR, dC：当前层的左下角（终点）
        public static void printLevel(int[][] m, int tR, int tC, int dR, int dC, boolean f) {
            if (f) {
                //起点行号恰好超过终点行号时结束
                while (tR != dR + 1) {
                    System.out.print(m[tR++][tC--] + " ");
                }
            } else {
                //
                while (dR != tR - 1) {
                    System.out.print(m[dR--][dC++] + " ");
                }
            }
        }

//        1 2 5 9 6 3 4 7 10 11 8 12
    public static void main(String[] args) {
            int[][] matrix = { { 1, 2, 3, 4 },
                               { 5, 6, 7, 8 },
                              { 9, 10, 11, 12 } };
            printMatrixZigZag(matrix);

        }


}
