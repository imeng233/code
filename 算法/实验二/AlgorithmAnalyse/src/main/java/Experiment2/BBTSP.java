package Experiment2;

import java.util.*;
import java.util.List;

class Node implements Comparable {
    int[] visp;//标记哪些点走了
    int st;//起点
    int ed;//终点
    int k;//走过的点数
    int sumv;//经过路径的距离
    int lb;//目标函数的值
    Map<Integer, Integer> map_edge = new HashMap<>();//记录已经加入的边

    @Override
    public int compareTo(Object o) {
        Node node = (Node) o;
        if (node.lb < this.lb)
            return 1;
        else if (node.lb > this.lb)
            return -1;
        return 0;
    }
}

public class BBTSP {
    private int[][] mp;
    int n;
    int up = 16;//路径总和上界,第一次为无穷大，后面取每个可行分支的最小值
    int low;//路径和最小值

    private PriorityQueue<Node> q = new PriorityQueue<>();
    private PriorityQueue<Node> q_last = new PriorityQueue<>();//记录每条路径的最后一个节点，以及对应的路径值

    public BBTSP(int[][] mp, int n) {
        this.mp = mp;
        this.n = n;
    }

    void get_low() { //计算TSP的下界
        low = 0;
        for (int i = 0; i < n; i++) {
            /*通过排序求两个最小值*/
            double[] tmpA = new double[n];
            for (int j = 0; j < n; j++) {
                tmpA[j] = mp[i][j];
            }
            Arrays.sort(tmpA);//对临时的数组进行排序
            low += tmpA[1] + tmpA[2];

        }
        low = low % 2 == 0 ? low / 2 : (low / 2 + 1);
    }

    public int get_lb(Node p) { //TSP部分解的下界
        int ret = p.sumv * 2;//路径上的点的距离
        double min1 = Double.MAX_VALUE, min2 = Double.MAX_VALUE;//起点和终点连出来的边
        Map<Integer, Integer> map = p.map_edge;

        for (int i = 0; i < n; i++) {
            boolean flag1 = false;//该点为出点
            boolean flag2 = false;//该点为入点

            int end = -1;
            int start = -1;

            if (map.containsKey(i)) {
                flag1 = true;
                end = map.get(i);
            }
            if (map.containsValue(i)) {
                flag2 = true;
                for (Map.Entry<Integer, Integer> entry : map.entrySet())
                    if (entry.getValue() == i) start = entry.getKey();
            }
            if (flag1 && flag2) continue;

            List<Integer> array = new ArrayList<>();
            if (!flag1 && flag2) {//该点只有入点，没有出点
                for (int j = 0; j < n; j++) {
                    if (i == j || j == start) continue;
                    array.add(mp[i][j]);
                }
                Collections.sort(array);

                ret += array.get(0);
            }

            if (!flag2 && flag1) {
                array = new ArrayList<>();
                for (int j = 0; j < n; j++) {
                    if (i == j || j == end) continue;
                    array.add(mp[j][i]);
                }
                Collections.sort(array);
                ret += array.get(0);
            }

            if (!flag1 && !flag2) {
                array = new ArrayList<>();
                for (int j = 0; j < n; j++) {
                    if (i == j) continue;
                    array.add(mp[i][j]);
                }
                Collections.sort(array);
                ret += array.get(0) + array.get(1);
            }
        }
        return ret % 2 == 0 ? (ret / 2) : (ret / 2 + 1);
    }

    public Node solve() {
        get_low();
        /*设置初始点,默认从1开始 */
        Node star = new Node();
        star.st = 0;
        star.ed = 0;
        star.k = 1;
        star.visp = new int[n];
        for (int i = 0; i < n; i++) star.visp[i] = 0;
        star.visp[0] = 1;
        star.sumv = 0;
        star.lb = low;
        /*ret为问题的解*/

        q.add(star); //把第一个城市加入到队列中
        while (!q.isEmpty()) {
            Node tmp = q.peek();

            if (!q_last.isEmpty()) {
                Node last = q_last.peek();
                if (last.lb <= tmp.lb) return last;
            }
            Iterator<Node> it = q.iterator();
            while (it.hasNext()) {
                Node no = it.next();
            }

            Map<Integer, Integer> tmp_map = tmp.map_edge;
            q.poll();
            if (tmp.k == n - 1) {
                /*找最后一个没有走的点*/
                int p = 0;
                for (int i = 0; i < n; i++) {
                    if (tmp.visp[i] == 0) {
                        p = i;
                        break;
                    }
                }

                Node next = new Node();
                next.visp = new int[n];
                next.st = tmp.ed;
                next.ed = p;
                int ans = tmp.sumv + mp[p][0] + mp[tmp.ed][p];//最终的最短路径
                next.sumv = ans;
                next.k = tmp.k + 1;
                next.map_edge.putAll(tmp.map_edge);
                next.map_edge.put(next.st, next.ed);
                next.map_edge.put(next.ed, 0);
                next.lb = ans;
                Node judge = q.peek();
                /*如果当前的路径和比所有的目标函数值都小则跳出*/
                if (ans <= judge.lb || judge == null) {
                    return next;
                }
                /*否则继续求其他可能的路径和，并更新上界*/
                else {
                    up = Math.min(up, ans);
                    q_last.add(next);
                    continue;
                }
            }
            /*当前点可以向下扩展的点入优先级队列*/

            for (int i = 0; i < n; i++) {
                if (tmp.visp[i] == 0) {
                    Node next = new Node();
                    next.visp = new int[n];
                    next.st = tmp.ed;

                    /*更新路径和*/
                    next.sumv = tmp.sumv + mp[tmp.ed][i];

                    /*更新最后一个点*/
                    next.ed = i;

                    /*更新顶点数*/
                    next.k = tmp.k + 1;

                    /*更新经过的顶点*/
                    for (int j = 0; j < n; j++) next.visp[j] = tmp.visp[j];
                    next.visp[i] = 1;

                    /*求目标函数*/
                    Map<Integer, Integer> next_map = new HashMap<>();
                    next_map.putAll(tmp_map);
                    next_map.put(next.st, next.ed);
                    next.map_edge = next_map;

                    next.lb = get_lb(next);

                    /*如果大于上界就不加入队列*/

                    if (next.lb > up) {
                        next_map.remove(next.st);
                        continue;
                    }
                    q.add(next);
                }
            }
        }
        return null;
    }
}

