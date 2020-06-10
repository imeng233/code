package com.company;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.xml.sax.HandlerBase;


public class Main {
    static char optSingle[] = {'+', '-', '*', '<', '=', '.', '!'};//单目运算符
    static String optDouble[] = {"&&"};//双目运算符
    static char End[] = {'[', ']', '{', '}', ';', '(', ')', ','};//终结符
    static String keyWords[] = {"public", "class", "static", "void",
            "main", "String", "extends", "return", "int", "boolean",
            "if", "else", "while", "System.out.println", "length",
            "true", "false", "this", "new"};//关键词

    IS i = new IS();

    /**
     * 读取一个文件的内容，分行将所有的内容保存到一个list集合中
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public List<String> readTestFile(String filename) throws IOException {
        List<String> list = new ArrayList<String>();
        String fileContent = "";
        FileReader fread = new FileReader(filename);
        BufferedReader bf = new BufferedReader(fread);
        String strLine = bf.readLine();
        while (strLine != null) {
            if (!strLine.isBlank()) {
                System.out.println("strLine:" + strLine);
                list.add(strLine);
            }
            strLine = bf.readLine();

        }
        bf.close();
        fread.close();
        return list;
    }

    /**
     * 将整行的字符串进行划分，有终结符用空格分隔
     *
     * @param str 代表整行需要处理的字符
     * @return 返回一个根据空格划分的list
     */
    public String[] Divide(String str) {
        String[] list;
        if (str.contains(";")) {
            StringBuilder sb = new StringBuilder(str);
            sb.insert(sb.indexOf(";"), " ");
            str = sb.toString();
        }
        if (str.contains(",")) {
            StringBuilder sb = new StringBuilder(str);
            sb.insert(sb.indexOf(","), " ");
            str = sb.toString();
        }
        if (str.contains("[")) {
            StringBuilder sb = new StringBuilder(str);
            sb.insert(sb.indexOf("["), " ");
            sb.insert(sb.indexOf("[")+1, " ");
            str = sb.toString();
        }
        if (str.contains("]")) {
            StringBuilder sb = new StringBuilder(str);
            sb.insert(sb.indexOf("]"), " ");
            str = sb.toString();
        }
        if (str.contains("(")) {
            StringBuilder sb = new StringBuilder(str);
            sb.insert(sb.indexOf("(")+1, " ");
            sb.insert(sb.indexOf("("), " ");
            sb.insert(sb.lastIndexOf("(")," ");
            sb.insert(sb.lastIndexOf("(")+1," ");
            str = sb.toString();
        }
        if (str.contains(")")) {
            StringBuilder sb = new StringBuilder(str);
            sb.insert(sb.indexOf(")"), " ");
            sb.insert(sb.lastIndexOf(")")," ");
            str = sb.toString();
        }
        if (str.contains("{")) {
            StringBuilder sb = new StringBuilder(str);
            sb.insert(sb.indexOf("{")+1, " ");
            sb.insert(sb.indexOf("{"), " ");
            str = sb.toString();
        }
        if (str.contains("}")) {
            StringBuilder sb = new StringBuilder(str);
            sb.insert(sb.indexOf("}"), " ");
            str = sb.toString();
        }
        if (str.contains("!")) {
            StringBuilder sb = new StringBuilder(str);
            sb.insert(sb.indexOf("!")+1, " ");
            str = sb.toString();
        }
        if (str.contains(">")) {
            StringBuilder sb = new StringBuilder(str);
            sb.insert(sb.indexOf(">")+1, " ");
            sb.insert(sb.indexOf(">"), " ");
            str = sb.toString();
        }
        if (str.contains("<")) {
            StringBuilder sb = new StringBuilder(str);
            sb.insert(sb.indexOf("<")+1, " ");
            sb.insert(sb.indexOf("<"), " ");
            str = sb.toString();
        }
        if (str.contains("+")) {
            StringBuilder sb = new StringBuilder(str);
            sb.insert(sb.indexOf("+")+1, " ");
            sb.insert(sb.indexOf("+"), " ");
            str = sb.toString();
        }
        if (str.contains("-")) {
            StringBuilder sb = new StringBuilder(str);
            sb.insert(sb.indexOf("-")+1, " ");
            sb.insert(sb.indexOf("-"), " ");
            str = sb.toString();
        }
        System.out.println(str);
        list = str.split("\\s+");
        return list;
    }

    /**
     * 根据输入的list进行划分和判断，并将判断的结果输出
     *
     * @param list
     */
    public void Sort(String[] list) throws IOException {
        //map里面的key有word,sortNum
        List<Map> mList = new ArrayList<Map>();
        //获取经过处理的种别码和关键字系列集合
        mList = i.GetStringAndSortNum(list);
        //写文件
        File file = new File("testout1.txt");
        if(!file.exists()){
            file.createNewFile();
        }
//        FileWriter fw = new FileWriter(file.getName(),true);
//
//
//        for (Map m0 : mList) {
//            int k = 0;
//            String keyword = (String) m0.get("keyword");
//            String sortNum = (String) m0.get("sortNum");
//            System.out.println("(" + sortNum + "," + keyword + ")");
//            fw.write("(" + sortNum + "," + keyword + ")"+"\n");
//            k++;
//            System.out.println(k);
//        }
//        fw.close();
    }

}
