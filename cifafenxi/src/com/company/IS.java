package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class IS {
    GetSortNum g = new GetSortNum();


    /**
     * 将按间隔符分好的list进行判断，判断是否是合法的子串
     *
     * @param list
     * @return
     */
    @SuppressWarnings("rawtypes")
    public List<Map> GetStringAndSortNum(String[] list) throws IOException {
        char firstChar;//用于记录第一个首字符
        String keyword = "", sortNum = "";//需要输出返回的关键字，种别码
        String cType, word;//第一个首字符的类型

        //mlist用于返回整个List判断完成后的含有的字符和种别码
        List<Map> mList = new ArrayList<Map>();
        File file = new File("testout7.txt");

        for (int i = 0; i < list.length; i++) {
            Map<String, String> map = new HashMap<String, String>();//m用于保存最后返回的已经判别成功的字和种别码
            //word代表需要进行处理判断的字
            word = list[i];

            //判断word是不是空的串，因为有可能根据空格分割的串中有空的换行符或者空串，不进行处理
            //if (word == "" || word == null || word.trim() == "") continue;
            if (word.isEmpty()) continue;

            firstChar = word.charAt(0);
            cType = GetCharType(firstChar);
            //获取这个字符的类型

            switch(cType){
                case "letter":
                    if (firstChar == 'p' || firstChar == 'c' || firstChar == 's' || firstChar == 'v'
                            || firstChar == 'm' || firstChar == 'S' || firstChar == 'e'
                            || firstChar == 'r' || firstChar == 'i' || firstChar == 'b'
                            || firstChar == 'l' || firstChar == 't' || firstChar == 'w'
                            || firstChar == 'f' || firstChar == 'n') {
                        //获得keyword词
                        Map<String, String> m = new HashMap<String, String>();//m用于保存最后返回的已经判别成功的字和种别码
                        m = GetKeyWord(word);

                        //取出m的值，如果是关键字
                        if (m.get("keyword") != "1") {
                            keyword = m.get("keyword");
                            sortNum = m.get("sortNum");
                            map.put("keyword", keyword);
                            map.put("sortNum", sortNum);
                            FileWriter fw = new FileWriter(file.getName(), true);
                            System.out.println("(" + sortNum + "," + keyword + ")");
                            fw.write("(" + sortNum + "," + keyword + ")"+"\n");
                            fw.close();
                        }
                        //不是关键字，但是包含关键字的前一个字符串
                        else {
                            if (word.contains(".")) {
                                StringBuilder sb = new StringBuilder(word);
                                sb.insert(sb.indexOf(".") + 1, " ");
                                sb.insert(sb.indexOf("."), " ");
                                word = sb.toString();
                            }
                            String[] again;
                            again = word.split("\\s+");
                            String aw;

                            for (int k = 0; k < again.length; k++) {
                                aw = again[k];

                                if (IsID(aw)) {
                                    keyword = aw;
                                    sortNum = g.getSortNum("Identifier") + "";
                                    map.put("keyword", keyword);
                                    map.put("sortNum", sortNum);
                                    FileWriter fw = new FileWriter(file.getName(), true);
                                    System.out.println("(" + sortNum + "," + keyword + ")");
                                    fw.write("(" + sortNum + "," + keyword + ")"+"\n");
                                    fw.close();

                                }
                                else if(aw.equals(".")){
                                    sortNum = g.getSortNum(".") + "";
                                    keyword = aw;
                                    map.put("keyword", keyword);
                                    map.put("sortNum", sortNum);
                                    FileWriter fw = new FileWriter(file.getName(), true);
                                    System.out.println("(" + sortNum + "," + keyword + ")");
                                    fw.write("(" + sortNum + "," + keyword + ")"+"\n");
                                    fw.close();

                                }
                                else {
                                    System.out.println("这个" + aw + "不是合法的Identifier字符，所在的位置在：第" + (i + 2) + "个单词");
                                    FileWriter fw = new FileWriter(file.getName(), true);
                                    fw.write("这个" + aw + "不是合法的Identifier字符，所在的位置在：第" + (i + 2) + "个单词" + "\n");
                                    fw.close();
                                }
                            }
                        }

                    } else {//首字母为字符，但是需要进一步判断是不是合法的ID
                        if (word.contains(".")) {
                            StringBuilder sb = new StringBuilder(word);
                            sb.insert(sb.indexOf(".") + 1, " ");
                            sb.insert(sb.indexOf("."), " ");
                            word = sb.toString();
                        }
                        String[] again;
                        again = word.split("\\s+");
                        String aw;

                        for (int k = 0; k < again.length; k++) {
                            aw = again[k];

                            if (IsID(aw)) {
                                keyword = aw;
                                sortNum = g.getSortNum("Identifier") + "";
                                map.put("keyword", keyword);
                                map.put("sortNum", sortNum);
                                FileWriter fw = new FileWriter(file.getName(), true);
                                System.out.println("(" + sortNum + "," + keyword + ")");
                                fw.write("(" + sortNum + "," + keyword + ")"+"\n");
                                fw.close();
                            }
                            else if(aw.equals(".")){
                                keyword = aw;
                                sortNum = g.getSortNum(".") + "";
                                map.put("keyword", keyword);
                                map.put("sortNum", sortNum);
                                FileWriter fw = new FileWriter(file.getName(), true);
                                System.out.println("(" + sortNum + "," + keyword + ")");
                                fw.write("(" + sortNum + "," + keyword + ")"+"\n");
                                fw.close();
                            }
                            else {
                                System.out.println("这个" + aw + "不是合法的Identifier字符，所在的位置在：第" + (i + 1) + "个单词");
                                FileWriter fw = new FileWriter(file.getName(), true);
                                fw.write("这个" + aw + "不是合法的Identifier字符，所在的位置在：第" + (i + 1) + "个单词" + "\n");
                                fw.close();
                            }
                        }
                    }
                    break;
                case "digit":
                    if (IsNum(word)) {
                        keyword = word;
                        sortNum = g.getSortNum("IntegerLiteral") + "";
                        map.put("keyword", keyword);
                        map.put("sortNum", sortNum);
                        FileWriter fw = new FileWriter(file.getName(), true);
                        System.out.println("(" + sortNum + "," + keyword + ")");
                        fw.write("(" + sortNum + "," + keyword + ")"+"\n");
                        fw.close();
                    } else {
                        System.out.println("这个" + word + "不是合法的IntegerLiteral字符，所在的位置在：第" + (i + 1) + "个单词");
                        FileWriter fw = new FileWriter(file.getName(), true);
                        fw.write("这个" + word + "不是合法的IntegerLiteral字符，所在的位置在：第" + (i + 1) + "个单词" + "\n");
                        fw.close();
                    }
                    break;
                case "opts":
                    //获取这个word的长度，如果是一个进行单运算符的判断，如果是2进行多运算符的判断
                    int len = word.length();
                    if (len == 1) {
                        if (IsSingleOpt(word)) {
                            keyword = word;
                            sortNum = g.getSortNum(word) + "";
                            map.put("keyword", keyword);
                            map.put("sortNum", sortNum);
                            FileWriter fw = new FileWriter(file.getName(), true);
                            System.out.println("(" + sortNum + "," + keyword + ")");
                            fw.write("(" + sortNum + "," + keyword + ")"+"\n");
                            fw.close();
                        } else if (IsEndOpt(word)) {
                            keyword = word;
                            sortNum = g.getSortNum(word) + "";
                            map.put("keyword", keyword);
                            map.put("sortNum", sortNum);
                            FileWriter fw = new FileWriter(file.getName(), true);
                            System.out.println("(" + sortNum + "," + keyword + ")");
                            fw.write("(" + sortNum + "," + keyword + ")"+"\n");
                            fw.close();
                        } else {
                            System.out.println("这个" + word + "不是合法的专用符号字符，所在的位置在：第" + (i + 1) + "个单词");
                            FileWriter fw = new FileWriter(file.getName(), true);
                            fw.write("这个" + word + "不是合法的专用符号字符，所在的位置在：第" + (i + 1) + "个单词" + "\n");
                            fw.close();
                        }
                    } else if (len == 2) {
                        if (IsDoubleOpt(word)) {
                            keyword = word;
                            sortNum = g.getSortNum(word) + "";
                            map.put("keyword", keyword);
                            map.put("sortNum", sortNum);
                            FileWriter fw = new FileWriter(file.getName(), true);
                            System.out.println("(" + sortNum + "," + keyword + ")");
                            fw.write("(" + sortNum + "," + keyword + ")"+"\n");
                            fw.close();
                        } else {
                            System.out.println("这个" + word + "不是合法的专用符号字符，所在的位置在：第" + (i + 1) + "个单词");
                            FileWriter fw = new FileWriter(file.getName(), true);
                            fw.write("这个" + word + "不是合法的专用符号字符，所在的位置在：第" + (i + 1) + "个单词" + "\n");
                            fw.close();
                        }
                    } else {
                        System.out.println("这个" + word + "不是合法的专用符号字符，所在的位置在：第" + (i + 1) + "个单词");
                        FileWriter fw = new FileWriter(file.getName(), true);
                        fw.write("这个" + word + "不是合法的专用符号字符，所在的位置在：第" + (i + 1) + "个单词" + "\n");
                        fw.close();
                    }
                    break;

            }

        }
        return mList;
    }


    /**
     * 根据字符判断这个字符是什么类型
     *
     * @param c
     * @return
     */
    public String GetCharType(char c) {
        String type = "";

        //('a' <= ch && ch <= 'z') || ('A' <= ch && ch <= 'Z') || ('0' <= ch && ch <= '9');
        if (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z')) {
            type = "letter";
        } else if ('0' <= c && c <= '9') {
            type = "digit";
        } else {
            type = "opts";
        }
        return type;
    }

    /**
     * 判断从现在index开始是否是关键字
     *
     * @param word
     * @return Map<String, String>
     */
    public Map<String, String> GetKeyWord(String word) {
        Map<String, String> m = new HashMap<String, String>();
        m.put("sortNum", "1");
        m.put("keyword", "1");
        char firstChar = word.charAt(0);
        switch (firstChar) {
            case 'p':
                if (word == "public" || word.equals("public")) {
                    String sortNum = g.getSortNum(word) + "";
                    m.put("sortNum", sortNum);
                    m.put("keyword", "public");
                }
                break;

            case 'c':
                if (word == "class" || word.equals("class")) {
                    String sortNum = g.getSortNum(word) + "";
                    m.put("sortNum", sortNum);
                    m.put("keyword", "class");
                }
                break;

            case 's':
                if (word == "static" || word.equals("static")) {
                    String sortNum = g.getSortNum(word) + "";
                    m.put("sortNum", sortNum);
                    m.put("keyword", "static");
                }
                break;

            case 'v':
                if (word == "void" || word.equals("void")) {
                    String sortNum = g.getSortNum(word) + "";
                    m.put("sortNum", sortNum);
                    m.put("keyword", "void");
                }
                break;

            case 'm':
                if (word == "main" || word.equals("main")) {
                    String sortNum = g.getSortNum(word) + "";
                    m.put("sortNum", sortNum);
                    m.put("keyword", "main");
                }
                break;

            case 'S':
                if (word == "String" || word.equals("String")) {
                    String sortNum = g.getSortNum(word) + "";
                    m.put("sortNum", sortNum);
                    m.put("keyword", "String");
                }
                if (word == "System.out.println" || word.equals("System.out.println")) {
                    String sortNum = g.getSortNum(word) + "";
                    m.put("sortNum", sortNum);
                    m.put("keyword", "System.out.println");
                }
                break;

            case 'e':
                if (word == "extends" || word.equals("extends")) {
                    String sortNum = g.getSortNum(word) + "";
                    m.put("sortNum", sortNum);
                    m.put("keyword", "extends");
                }
                if (word == "else" || word.equals("else")) {
                    String sortNum = g.getSortNum(word) + "";
                    m.put("sortNum", sortNum);
                    m.put("keyword", "else");
                }
                break;
            case 'r':
                if (word == "return" || word.equals("return")) {
                    String sortNum = g.getSortNum(word) + "";
                    m.put("sortNum", sortNum);
                    m.put("keyword", "return");
                }
                break;
            case 'i':
                if (word == "int" || word.equals("int")) {
                    String sortNum = g.getSortNum(word) + "";
                    m.put("sortNum", sortNum);
                    m.put("keyword", "int");
                }
                if (word == "if" || word.equals("if")) {
                    String sortNum = g.getSortNum(word) + "";
                    m.put("sortNum", sortNum);
                    m.put("keyword", "if");
                }
                break;
            case 'b':
                if (word == "boolean" || word.equals("boolean")) {
                    String sortNum = g.getSortNum(word) + "";
                    m.put("sortNum", sortNum);
                    m.put("keyword", "boolean");
                }
                break;
            case 'w':
                if (word == "while" || word.equals("while")) {
                    String sortNum = g.getSortNum(word) + "";
                    m.put("sortNum", sortNum);
                    m.put("keyword", "while");
                }
                break;
            case 'l':
                if (word == "length" || word.equals("length")) {
                    String sortNum = g.getSortNum(word) + "";
                    m.put("sortNum", sortNum);
                    m.put("keyword", "length");
                }
                break;
            case 't':
                if (word == "true" || word.equals("true")) {
                    String sortNum = g.getSortNum(word) + "";
                    m.put("sortNum", sortNum);
                    m.put("keyword", "true");
                }
                if (word == "this" || word.equals("this")) {
                    String sortNum = g.getSortNum(word) + "";
                    m.put("sortNum", sortNum);
                    m.put("keyword", "this");
                }
                break;
            case 'f':
                if (word == "false" || word.equals("false")) {
                    String sortNum = g.getSortNum(word) + "";
                    m.put("sortNum", sortNum);
                    m.put("keyword", "false");
                }
                break;
            case 'n':
                if (word == "new" || word.equals("new")) {
                    String sortNum = g.getSortNum(word) + "";
                    m.put("sortNum", sortNum);
                    m.put("keyword", "new");
                }
                break;
        }

        return m;
    }

    /**
     * 判断一个字符是不是合法的ID,如果是，则返回true，否则，返回这个word的错误码false
     *
     * @param word
     * @return boolean
     */
    public boolean IsID(String word) {
        char c;
        char firstChar = word.charAt(0);//获取第一个字符


        if (GetCharType(firstChar) == "letter") {//判断第一个字符是不是letter，否则肯定不是合法的
            for (int i = 1; i < word.length(); i++) {//从第二个字符开始
                c = word.charAt(i);
                if (c == '_')
                    continue;
                else if (GetCharType(c) == "letter")//如果是letter,下一个字符
                    continue;
                else if (GetCharType(c) == "digit")//如果是digit,下一个字符
                    continue;
                else//否则报错
                    return false;
            }
            return true;
        }
        return false;
    }


    /**
     * 判断一个串是不是合法的digit
     *
     * @param word
     * @return
     */
    public boolean IsNum(String word) {
        char c;
        char firstChar = word.charAt(0);//获取第一个字符

        if (GetCharType(firstChar) == "digit") {//判断第一个字符是不是digit，否则肯定不是合法的
            for (int i = 1; i < word.length(); i++) {//从第二个字符开始
                c = word.charAt(i);
                if (GetCharType(c) == "digit")//如果是digit,下一个字符
                    continue;
                else//否则报错
                    return false;
            }
            return true;
        }
        return false;
    }


    /**
     * 判断是否是双目运算符
     *
     * @param word
     * @return
     */
    private boolean IsDoubleOpt(String word) {
        String optDouble[] = {"&&"};//双目运算符
        for (int i = 0; i < optDouble.length; i++)
            if (word.equals(optDouble[i])) return true;
        return false;
    }

    /**
     * 判断是否是单目运算符
     *
     * @param word
     * @return
     */
    private boolean IsSingleOpt(String word) {
        char optSingle[] = {'+', '-', '*', '<', '=', '.', '!'};//单目运算符
        for (int i = 0; i < optSingle.length; i++)
            if (word.equals(optSingle[i] + "")) return true;
        return false;
    }

    /**
     * 判断是否是终结符
     *
     * @param word
     * @return
     */
    private boolean IsEndOpt(String word) {
        char End[] = {'[', ']', '{', '}', ';', '(', ')', ','};//终结符
        for (int i = 0; i < End.length; i++)
            if (word.equals(End[i] + "")) return true;
        return false;
    }

}
