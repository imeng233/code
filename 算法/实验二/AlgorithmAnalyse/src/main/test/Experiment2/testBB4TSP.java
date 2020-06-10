package Experiment2;

import org.junit.Assert;
import org.junit.Test;

public class testBB4TSP {

	@Test
	public void testbb4TSP() {

		int[][] d={
				{0,3,1,5,8},
				{3,0,6,7,9},
				{1,6,0,4,2},
				{5,7,4,0,3}
				//{8,9,2,3,0}
		};
		int n=4;
		BBTSP bb4TSP = new BBTSP(d,n);
		long startTime = System.nanoTime();
		Node node=bb4TSP.solve();
		Assert.assertTrue(node.lb == 15);
		long endTime = System.nanoTime();
		long duration = (endTime - startTime);
		System.out.println("”√ ±:"+duration);
	}
}
