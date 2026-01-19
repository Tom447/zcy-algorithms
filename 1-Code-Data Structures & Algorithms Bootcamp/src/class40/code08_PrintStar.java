package class40;

public class code08_PrintStar {


    public static void printStart(int N){

        char[][] martix = new char[N][N];


        for(int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                martix[i][j] = ' ';
            }
        }

        int leftUp = 0;
        int rightDown = N - 1;

        while (leftUp <= rightDown){
            set(martix, leftUp, rightDown);
            leftUp += 2;
            rightDown -= 2;
        }

        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                System.out.print(martix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void set(char[][] m, int leftUp, int rightDown){
        //从左往右
        for (int col = leftUp; col <= rightDown; col++){
            m[leftUp][col] = '*';
        }
        //从上到下,跳过右面第一个
        for (int row = leftUp + 1; row <= rightDown; row++){
            m[row][rightDown] = '*';
        }
        //从右下往左，跳过右下第一个，截止到left的前一个
        for (int col = rightDown - 1; col >= leftUp + 1; col--){
            m[rightDown][col] = '*';
        }
        //从左下往上,跳过左下第一个
        for (int row = rightDown - 1; row >= leftUp + 2; row--){
            m[row][leftUp + 1] = '*';
        }
    }

    public static void main(String[] args) {
        printStart(20);
    }
}
