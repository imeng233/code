package Experiment2;

public class Back4TSP {

	int NoEdge = -1;
	int bigInt = Integer.MAX_VALUE;
	int[][] a; // �ڽӾ���
	int cc = 0; // �洢��ǰ����
	int bestc = bigInt;// ��ǰ���Ŵ���
	int[] x; // ��ǰ��
	int[] bestx;// ��ǰ���Ž�
	int n = 0; // �������

	private void backtrack(int i) {//iΪ��ʼ���
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
		bestx = new int[n + 1];//����5*5����ֻ�õ���4*4
		a = b;
		backtrack(2);
	}
}

