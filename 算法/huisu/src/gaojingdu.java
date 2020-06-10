
public class gaojingdu {
    int[] a;
    int[] b;
    int n = 3;

    public void backTracking4MaxNumber(int t) {

        if (t > n) {
            assignA2B(a, b);
        } else {
            int j;
            if (t == 1) j = 1; //第一位不能取0
            else j = 0; //其它位可以
            for (; j <= 9; j++) {
                a[t] = j;
                if (OK(a, t)) {
                    backTracking4MaxNumber(t + 1);
                }
                a[t] = -1;
            }
        }
    }

    public void assignA2B(int[] a, int[] b) {
        for (int i = 1; i < a.length; i++) {
            b[i] = a[i];
        }
    }

    public boolean OK(int[] a, int t) {
        int r = 0;
//        for(int i = 1; i <= t; i++) {
//            r = r*10 + a[i];
//            r = r % t;//整数倍乘以10没用故只用余数，防止溢出
//        }
//        r = r % t;
//        if(r == 0) return true;
//        return false;
        for (int i = 1; i <= t; i++) {
            r = r * 10 + a[i];
        }
        if (r%t == 0) return true;
        else return false;
    }

    public void gaojingdu(int n) {
        b = new int[n + 1];
        a = new int[n + 1];
        for (int i = 1; i < n + 1; i++) {
            b[i] = 0;
        }
        for (int i = 1; i < n + 1; i++) {
            a[i] = 0;
        }
        backTracking4MaxNumber(1);
    }

    public static void main(String[] args) {
        gaojingdu a1 = new gaojingdu();
        int n = 3;
        a1.gaojingdu(n);
        for (int i = 1; i < a1.b.length; i++) {
            System.out.print(a1.b[i]);

        }
    }
}
