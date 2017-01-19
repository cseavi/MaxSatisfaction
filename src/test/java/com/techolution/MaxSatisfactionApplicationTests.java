package com.techolution;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MaxSatisfactionApplicationTests {

	@Autowired
	private MaxSatisfactionApplication application;

	// happy scenario
	@Test
	public void testGetMaxSatisfaction() {
		String fileName = "data.txt";
		// String[] input = { fileName };
		int maxSatisfaction = application.getMaxSatisfaction(fileName);
		assertNotNull(maxSatisfaction);
	}

	// non happy scenario
	@Test(expected = Exception.class)
	public void testGetMaxSatisfactionWrongFileName() {
		String fileName = "hello.txt";
		application.getMaxSatisfaction(null);
	}

	@Test(expected = Exception.class)
	public void testGetMaxSatisfactionNoFileName() {
		application.getMaxSatisfaction(null);
	}

	@Test
	public void contextLoads() {
	}

}
