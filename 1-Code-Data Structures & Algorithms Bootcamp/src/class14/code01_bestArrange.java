package class14;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class code01_bestArrange {


    public static class Program{
        public int start;
        public int end;

        public Program(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public static class myComparator implements Comparator<Program>{


        @Override
        public int compare(Program o1, Program o2) {
            return o1.end - o2.end;
        }
    }

    public static int getBestArrange1(Program[] programs){
        if (programs == null || programs.length == 0){
            return 0;
        }

        Arrays.sort(programs, new myComparator());
        int result = 0;
        int testline = 0;
        for (int i = 0; i < programs.length; i++){
            if (testline <= programs[i].start){
                testline = programs[i].end;
                result++;
            }
        }
        return result;
    }

    public static int getBestArrange2(Program[] programs){
        if (programs == null || programs.length == 0){
            return 0;
        }
        return process(programs, 0, 0);
    }


    //done 是已经开了多少个会了， timeLine是上一个会议结束的时间
    public static int process(Program[] programs, int done, int timeLine){
        if (programs.length == 0){
            return done;
        }
        int max = done;
        for (int i = 0; i < programs.length; i++){
            if (programs[i].start >= timeLine){
                Program[] next = copyAndExcept(programs, i);
                max = Math.max(max, process(next, done + 1, programs[i].end));
            }
        }
        return max;
    }

    public static Program[] copyAndExcept(Program[] programs, int index){
        Program[] ans = new Program[programs.length - 1];
        int cur = 0;
        for (int i = 0; i < programs.length; i++){
            if (i != index){
                ans[cur++] = programs[i];
            }
        }
        return ans;
    }

    public static Program[] generateRandomArray(int maxValue, int maxSize){
        Random random = new Random();
        int size = random.nextInt(maxSize) + 1;
        Program[] ans = new Program[size];
        for (int i = 0; i < size; i++){
            int a = random.nextInt(maxValue);
            int b = random.nextInt(maxValue);
            if (a == b){
                ans[i] = new Program(a, a + 1);
            }else{
                ans[i] = new Program(Math.min(a, b), Math.max(a, b));
            }
        }
        return ans;
    }

    public static Program[] copyPrograms(Program[] programs){
        Program[] copyProgram = new Program[programs.length];
        for (int i = 0; i < programs.length; i++){
            copyProgram[i] = programs[i];
        }
        return copyProgram;
    }

    public static void main(String[] args) {
        int testTimes = 10000;
        int maxSize = 10;
        int maxValue = 20;
        for (int i = 0 ; i < testTimes; i++){
            Program[] programs = generateRandomArray(maxValue, maxSize);
            Program[] copyPrograms = copyPrograms(programs);
            if (getBestArrange1(programs) != getBestArrange2(programs)){
                System.out.println("oops");
                break;
            }
        }
    }
}
