package com.company;

public class GetSortNum {
	/**
	 * 输入单词获取单词对应的种别码
	 * @param word
	 * @return int
	 */
	public int getSortNum(String word){
		int sortNum=0;
		switch(word){
			case "public":
				sortNum= 1;
				break;
			case "class":
				sortNum= 2;
				break;
			case "static":
				sortNum= 3;
				break;
			
			case "void":
				sortNum= 4;
				break;
			
			case "main":
				sortNum= 5;
				break;
			case "String":
				sortNum= 6;
				break;
			case "extends":
				sortNum= 10;
				break;
			case "return":
				sortNum= 11;
				break;
			case "int":
				sortNum= 12;
				break;
			case "boolean":
				sortNum= 13;
				break;
			case "if":
				sortNum= 14;
				break;
			case "else":
				sortNum= 15;
				break;
			case "while":
				sortNum= 16;
				break;
			case "System.out.println":
				sortNum= 17;
				break;
			case "length":
				sortNum= 18;
				break;
			case "true":
				sortNum= 19;
				break;
			case "false":
				sortNum= 20;
				break;
			case "this":
				sortNum= 21;
				break;
			case "new":
				sortNum= 22;
				break;
			case "[":
				sortNum= 23;
				break;
			case "]":
				sortNum= 24;
				break;
			case "{":
				sortNum= 25;
				break;
			case "}":
				sortNum= 26;
				break;
			case ";":
				sortNum= 27;
				break;
			case "(":
				sortNum= 28;
				break;
			case ")":
				sortNum= 29;
				break;
			case ",":
				sortNum= 30;
				break;
			case "&&":
				sortNum= 31;
				break;
			case "+":
				sortNum= 32;
				break;
			case "-":
				sortNum= 33;
				break;
			case "*":
				sortNum= 34;
				break;
			case "<":
				sortNum= 35;
				break;
			case "=":
				sortNum= 36;
				break;
			case ".":
				sortNum= 37;
				break;
			case "!":
				sortNum= 38;
				break;
			case "Identifier":
				sortNum= 39;
				break;
			case "IntegerLiteral":
				sortNum= 40;
				break;
			default:
				sortNum=-1;
				break;
		}
		return sortNum;
	}
}
