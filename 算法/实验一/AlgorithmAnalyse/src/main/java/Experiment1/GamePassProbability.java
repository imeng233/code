package Experiment1;

import java.math.BigDecimal;

public class GamePassProbability {

    public double calculatePassProbability(int[] p, int num) {
        double pass = 0.0d;
        if (num == 0) {
            return 1;
        }
        int i = 0;
        int j = 0;
        double x[][] = new double[num + 1][num + 1];
        x[0][0] = 1;
        for (i = 1; i <= num; i++) {
            x[i][0] = x[i - 1][0] * (1 - p[i - 1] * 0.01);
            for (j = 1; j <= i; j++) {
                x[i][j] = x[i - 1][j] * (1 - p[i - 1] * 0.01) + x[i - 1][j - 1] * p[i - 1] * 0.01;
        }
        }
        for (i = (int) Math.ceil(0.7 * num); i <= num; i++) { //向上取整遍历累加所有符合条件的结果
            pass += x[num][i];
        }
        BigDecimal bd = new BigDecimal(pass);
        Double result = bd.setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
        return result;
    }
}
