package com.company;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class junitTest {
	Main m=new Main();
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void test() throws IOException {
		//读取文件
		List<String>strList= m.readTestFile("D:\\喵\\cifafenxi\\专用符号\\test7.txt");
		String [] list=null;
		for(int i=0;i<strList.size();i++){
			System.out.println("进行第"+(i+1)+"行的判断：");
			list=m.Divide(strList.get(i));
			m.Sort(list);
			System.out.println("判断完成");
		}
	}

}
