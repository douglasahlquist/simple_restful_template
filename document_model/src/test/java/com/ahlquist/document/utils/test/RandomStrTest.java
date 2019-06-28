package com.ahlquist.document.utils.test;

import java.util.*;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.ahlquist.document.utils.RandomStr;
public class RandomStrTest {
	
	public final int length = 20;
	
	@Test(enabled = false)
	public void testRandomStr() {
		
		List<String> list = new ArrayList<>();
		//tests in a limited fashion the uniqueness of the string generation
		for(int i=0 ; i<1_000_000 ; i++) {
			String s = RandomStr.generateUUID(length);
			//System.out.print(s + " ");
			Assert.assertEquals(list.contains(s),false);

			list.add(s);
		}
		
	}

}
