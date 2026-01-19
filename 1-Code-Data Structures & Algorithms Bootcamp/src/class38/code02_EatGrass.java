package class38;

public class code02_EatGrass {

    public static String whoWin(int n){
        if (n < 5){
            return  (n == 0 || n == 2) ? "后手" : "先手";
        }

        int want = 1;

        while (want <= n){
            if (whoWin(n - want).equals("后手")){
                return "先手";
            }
            if (want <= (n / 4)) {
                want *= 4;
            } else {
                break;
            }
//            want *= 4;
        }
        return "后手";
    }

    //规律是从0开始，后0 先1，后2 先3 先4  也就说偶数为后手，奇数为先手   且每五个一组
    public static String whoWin2(int num){
        if (num % 5 == 0 || num % 5 == 2){
            return "后手";
        }else{
            return "先手";
        }
    }
    public static void main(String[] args) {
        for (int i = 0; i <= 50; i++) {
            System.out.println(i + " : " + whoWin2(i));
        }
    }
}
