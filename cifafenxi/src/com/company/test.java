package com.company;

import java.io.*;
import java.util.ArrayList;

public class test {
    public static ArrayList arrayList = new ArrayList();
    public static String[] word;//读入最开始的代码
    public static String[][] outPut;//输出结果储存的数组
    public static int No = 0;//记录当前是第几个单词
    private static void judge(String wordLine, int line){
        int state = 0;//自动机的状态
        int num = 0;//第几个字符
        String letter;//字符
        boolean save = true;//是否储存该字符
        String W = "";//单词
        String partOfSpeech=null;//词性
        while (num<wordLine.length() && wordLine.substring(num,num+1)!=null){
            //自动机
            letter=wordLine.substring(num,num+1);
            switch (state){
                case (0) ://判断第一个字符，推进状态
                    if (isDigit(letter)){
                        state=1;
                    }else {
                        if (isLetter(letter)){
                            state=2;
                        }else {
                            if (isOpSymbol(letter)){
                                state=3;
                            }else {
                                if (isAnd(letter)){
                                    state=4;
                                }else {
                                    if (isEdgeSymbol(letter)){
                                        state=5;
                                    }else {
                                        state=6;
                                    }
                                }
                            }
                        }
                    }
                    break;
                case (1)://IntegerLiteral
                    if(!isDigit(letter)){
                        state=0;
                        save = false;
                        partOfSpeech = "IntegerLiteral";
                    }
                    break;
                case (2):
                    int identifierState=1;
                    switch (identifierState){
                        case 1:
                            if (letter.equals("_")){
                                identifierState=2;
                            }else {
                                if (!isDigit(letter)&&!isLetter(letter)){
                                    save=false;
                                    partOfSpeech="Identifier";
                                    state=6;
                                }
                            }
                            break;
                        case 2:
                            if (isLetter(letter)||isDigit(letter)){
                                identifierState=1;
                            }else {
                                identifierState=1;
                                save=false;
                                partOfSpeech="Identifier";
                                state=6;
                            }
                            break;
                        default:
                            break;
                    }
                    break;
                case (3)://OpSymbol
                    state=0;
                    save=false;
                    partOfSpeech = "OpSymbol";
                    break;
                case (4)://&&
                    if(!isAnd(letter)&&W.length()==2){
                        state=0;
                        save=false;
                        partOfSpeech="Symbol";
                    }else {
                        if (!isAnd(letter)&&W.length()!=2){
                            save=false;
                            partOfSpeech="Error";
                            state=0;
                        }
                    }
                    break;
                case (5)://EdgeSymbol
                    save=false;
                    partOfSpeech = "EdgeSymbol";
                    break;
                case (6)://其他情况，包含终止状态

                    break;
            };
            if (save){//储存新的字符
                W = W + letter;
            }else {//将单词及词性储存
                
                outPut[No][0]=W;
                outPut[No][1]=String.valueOf(line+1);
                outPut[No][2]=partOfSpeech;
                No++;
                W = "";
            }
            num++;
            System.out.println(W);
        }
        outPut[No][0]=W;
        outPut[No][1]=String.valueOf(line+1);
        outPut[No][2]=partOfSpeech;
        No++;
    }
    private static boolean isOpSymbol(String letter) {
        String[] symbol = {"+","-","*","=","<","!"};
        int a = symbol.length;
        for (int j = 0; j < a; j++) {
            if (letter.equals(symbol[j])) {
                return true;
            }
        }
        return false;
    }

    private static boolean isEdgeSymbol(String letter) {
        String[] symbol = {"[", "]", "(", ")", "{", "}", ",", ";", "."};
        int a = symbol.length;
        for (int j = 0; j < a; j++) {
            if (letter.equals(symbol[j])) {
                return true;
            }
        }
        return false;
    }

    private static boolean isAnd(String letter) {
        String and = "&";
        if (letter.equals(and)){
            return true;
        };
        return false;
    }

    private static boolean isLetter(String letter) {
        String[] identifier_letter = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        for (int i = 0; i < identifier_letter.length; i++) {
            if (letter.equals(identifier_letter[i])) {
                return true;
            }
        }
        return false;
    }

    private static boolean isDigit(String letter) {
        String[] digit = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        for (int i = 0; i < digit.length; i++) {
            if (letter.equals(digit[i])) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args){
        String wordline = "ab";
        int line = 0;
        judge(wordline,line);
        System.out.println(outPut[0][0]);
        System.out.println(outPut[0][2]);
    }
}
