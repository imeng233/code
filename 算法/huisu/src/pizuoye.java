public class pizuoye {

        int num=3;
        int[][] f; //任务加工矩阵
        int[] best; //最短加工顺序
        int[] x; //当前加工顺序
        int bestf = 10000; //最短完成时间
        int cf = 0; //当前完成时间
        int m1 = 0; //机器1完成时间
        int[] m2; //机器2完成时间

    public void backtrack(int t){
        if(t>num){
            bestf=cf;
            for(int i=1;i<=num;i++) {
                best[i] = x[i];
            }
            }
            else{
                    for (int i = t; i<= num; i++){
                swap(t, i);
                m1 += f[1][x[t]];
                m2[t] = max(t, m1) + f[2][x[t]];
                cf += m2[t];
                if (check(bestf, cf)) {//检查bound
                    backtrack(t + 1);}
                m1 -= f[1][x[t]]; //恢复现场
                cf -= m2[t];
                swap(t, i);
            }
            }
        }

    private void swap(int t, int i) {
        int temp = x[t];
        x[t] = x[i];
        x[i] = temp;
    }

    private int max(int a,int b){
        if(m2[a-1]>b){
            return m2[a-1];
        }
        else return b;
    }

    private boolean check(int a,int b){
        if(bestf <= cf) {return false;}
        else {return true;}
    }

    public void pizuoye(int[][] b, int n) {
        num = n;
        f=b;
        best = new int[n+1];
        x = new int[n+1];
        for(int i=1;i<=n;i++){
            x[i] = i;
        }
        m2 = new int[n+1];
        backtrack(1);
    }

    public static void main(String[] args) {
            pizuoye a1 = new pizuoye();
            int[][] b = {{-1,-1,-1,-1},{-1,2,3,2},{-1,1,1,3}};
            int n = 3;
            a1.pizuoye(b, n);
        System.out.println(a1.bestf);


    }

}
