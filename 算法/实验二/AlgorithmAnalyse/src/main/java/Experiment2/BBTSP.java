package Experiment2;

import java.util.*;
import java.util.List;

class Node implements Comparable {
    int[] visp;//�����Щ������
    int st;//���
    int ed;//�յ�
    int k;//�߹��ĵ���
    int sumv;//����·���ľ���
    int lb;//Ŀ�꺯����ֵ
    Map<Integer, Integer> map_edge = new HashMap<>();//��¼�Ѿ�����ı�

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
    int up = 16;//·���ܺ��Ͻ�,��һ��Ϊ����󣬺���ȡÿ�����з�֧����Сֵ
    int low;//·������Сֵ

    private PriorityQueue<Node> q = new PriorityQueue<>();
    private PriorityQueue<Node> q_last = new PriorityQueue<>();//��¼ÿ��·�������һ���ڵ㣬�Լ���Ӧ��·��ֵ

    public BBTSP(int[][] mp, int n) {
        this.mp = mp;
        this.n = n;
    }

    void get_low() { //����TSP���½�
        low = 0;
        for (int i = 0; i < n; i++) {
            /*ͨ��������������Сֵ*/
            double[] tmpA = new double[n];
            for (int j = 0; j < n; j++) {
                tmpA[j] = mp[i][j];
            }
            Arrays.sort(tmpA);//����ʱ�������������
            low += tmpA[1] + tmpA[2];

        }
        low = low % 2 == 0 ? low / 2 : (low / 2 + 1);
    }

    public int get_lb(Node p) { //TSP���ֽ���½�
        int ret = p.sumv * 2;//·���ϵĵ�ľ���
        double min1 = Double.MAX_VALUE, min2 = Double.MAX_VALUE;//�����յ��������ı�
        Map<Integer, Integer> map = p.map_edge;

        for (int i = 0; i < n; i++) {
            boolean flag1 = false;//�õ�Ϊ����
            boolean flag2 = false;//�õ�Ϊ���

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
            if (!flag1 && flag2) {//�õ�ֻ����㣬û�г���
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
        /*���ó�ʼ��,Ĭ�ϴ�1��ʼ */
        Node star = new Node();
        star.st = 0;
        star.ed = 0;
        star.k = 1;
        star.visp = new int[n];
        for (int i = 0; i < n; i++) star.visp[i] = 0;
        star.visp[0] = 1;
        star.sumv = 0;
        star.lb = low;
        /*retΪ����Ľ�*/

        q.add(star); //�ѵ�һ�����м��뵽������
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
                /*�����һ��û���ߵĵ�*/
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
                int ans = tmp.sumv + mp[p][0] + mp[tmp.ed][p];//���յ����·��
                next.sumv = ans;
                next.k = tmp.k + 1;
                next.map_edge.putAll(tmp.map_edge);
                next.map_edge.put(next.st, next.ed);
                next.map_edge.put(next.ed, 0);
                next.lb = ans;
                Node judge = q.peek();
                /*�����ǰ��·���ͱ����е�Ŀ�꺯��ֵ��С������*/
                if (ans <= judge.lb || judge == null) {
                    return next;
                }
                /*����������������ܵ�·���ͣ��������Ͻ�*/
                else {
                    up = Math.min(up, ans);
                    q_last.add(next);
                    continue;
                }
            }
            /*��ǰ�����������չ�ĵ������ȼ�����*/

            for (int i = 0; i < n; i++) {
                if (tmp.visp[i] == 0) {
                    Node next = new Node();
                    next.visp = new int[n];
                    next.st = tmp.ed;

                    /*����·����*/
                    next.sumv = tmp.sumv + mp[tmp.ed][i];

                    /*�������һ����*/
                    next.ed = i;

                    /*���¶�����*/
                    next.k = tmp.k + 1;

                    /*���¾����Ķ���*/
                    for (int j = 0; j < n; j++) next.visp[j] = tmp.visp[j];
                    next.visp[i] = 1;

                    /*��Ŀ�꺯��*/
                    Map<Integer, Integer> next_map = new HashMap<>();
                    next_map.putAll(tmp_map);
                    next_map.put(next.st, next.ed);
                    next.map_edge = next_map;

                    next.lb = get_lb(next);

                    /*��������Ͻ�Ͳ��������*/

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

