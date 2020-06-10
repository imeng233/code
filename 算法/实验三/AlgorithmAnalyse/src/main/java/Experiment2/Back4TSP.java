package Experiment2;

public class Back4TSP {

	int NoEdge = -1;
	int bigInt = Integer.MAX_VALUE;
	int[][] a; // 邻接矩阵
	int cc = 0; // 存储当前代价
	int bestc = bigInt;// 当前最优代价
	int[] x; // 当前解
	int[] bestx;// 当前最优解
	int n = 0; // 顶点个数

	private void backtrack(int i) {//i为初始深度
		if (i > n) {
			//TODO
			bestc = cc;
			for (int p = 0; p <= n; p++) {
				bestx[p] = x[p];
			}
		} else {
			//TODO
			for (int j = i; j <= n; j++) {
				if (check(i, j)) {
					swap(i, j);
					if (i < n && cc + a[x[i - 1]][x[i]] < bestc) {
						cc = cc + a[x[i - 1]][x[i]];
						backtrack(i + 1);
						cc = cc - a[x[i - 1]][x[i]];

					}
					if (i == n && cc + a[x[i - 1]][x[i]] + a[x[n]][x[1]] < bestc) {
						cc = cc + a[x[i - 1]][x[i]] + a[x[n]][x[1]];
						backtrack(i + 1);
						cc = cc - a[x[i - 1]][x[i]] - a[x[n]][x[1]];

					}
					swap(i, j);
				}
			}
		}
	}

	private void swap(int i, int j) {
		int temp = x[i];
		x[i] = x[j];
		x[j] = temp;
	}

	public boolean check(int i, int j) {
		//TODO
		if (i < 2) {
			return true;
		}

		if (i < n && a[x[i - 1]][x[j]] != NoEdge) return true;
		if (i == n && a[x[i - 1]][x[j]] != NoEdge && a[x[j]][x[1]] != NoEdge) return true;
		return false;
	}

	public void backtrack4TSP(int[][] b, int num) {
		n = num;
		x = new int[n + 1];
		for (int i = 0; i <= n; i++)
			x[i] = i;
		bestx = new int[n + 1];//创建5*5矩阵，只用到了4*4
		a = b;
		backtrack(2);
	}
}

