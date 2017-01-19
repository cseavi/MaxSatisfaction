package com.techolution;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication
public class MaxSatisfactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaxSatisfactionApplication.class, args);
		
		MaxSatisfactionApplication ms = new MaxSatisfactionApplication();
		ms.getMaxSatisfaction(args[0]);
	}
	
	//expecting the file to be on same path
	public int getMaxSatisfaction(String fileName){
		
		ValuesToOperate vto = getDataFromFile(fileName);
		int maxSatisfaction = calculateMaxSatisfaction(vto.getSatisafactionValue(), vto.getEatingTime(), 
				vto.getMaxTime());
		System.out.println("Maximum Time Provided = " + vto.getMaxTime());
		System.out.println("Maximum Satisfaction = " + maxSatisfaction);
	
		return maxSatisfaction;
	}


	private int calculateMaxSatisfaction(int satisafactionValue[], int eatingTime[], int maxTime) {
		int totalItems = eatingTime.length; // Get the total number of items
		int[][] matrix = new int[totalItems + 1][maxTime + 1]; // matrix - satisfaction in rows & time in columns
		// capacity may be 0 so setting all columns at row 0 to be 0
		for (int col = 0; col <= maxTime; col++) {
			matrix[0][col] = 0;
		}
		// there may be no item so fill the first row with 0
		for (int row = 0; row <= totalItems; row++) {
			matrix[row][0] = 0;
		}
		for (int item = 1; item <= totalItems; item++) {
			for (int weight = 1; weight <= maxTime; weight++) {
				if (eatingTime[item - 1] <= weight) {
					matrix[item][weight] = Math.max(satisafactionValue[item - 1]
							+ matrix[item - 1][weight - eatingTime[item - 1]], matrix[item - 1][weight]);
				} else {
					matrix[item][weight] = matrix[item - 1][weight];
				}
			}
		}
		return matrix[totalItems][maxTime];
	}

	private ValuesToOperate getDataFromFile(String fileName) {
		BufferedReader br = null;
		ValuesToOperate vto = new ValuesToOperate();
		try {
			br = new BufferedReader(new FileReader(new ClassPathResource(fileName).getFile()));

			String metaData = br.readLine();

			String[] metaDataValues = metaData.split(" ");
			vto.setMaxTime(Integer.valueOf(metaDataValues[0]));
			int numberOfItems = Integer.valueOf(metaDataValues[1]);

			// now extract satisfaction value and time to work on
			int satisafactionValue[] = new int[numberOfItems];
			int eatingTime[] = new int[numberOfItems];

			String data = null;
			for (int i = 1; i <= numberOfItems; i++) {
				data = br.readLine();
				String[] dataValues = data.split(" ");
				satisafactionValue[i - 1] = Integer.valueOf(dataValues[0]);
				eatingTime[i - 1] = Integer.valueOf(dataValues[1]);
			}
			vto.setEatingTime(eatingTime);
			vto.setSatisafactionValue(satisafactionValue);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {

			}
		}
		return vto;
	}

	class ValuesToOperate {
		int maxTime;
		int satisafactionValue[];
		int eatingTime[];

		public int getMaxTime() {
			return maxTime;
		}

		public void setMaxTime(int maxTime) {
			this.maxTime = maxTime;
		}

		public int[] getSatisafactionValue() {
			return satisafactionValue;
		}

		public void setSatisafactionValue(int[] satisafactionValue) {
			this.satisafactionValue = satisafactionValue;
		}

		public int[] getEatingTime() {
			return eatingTime;
		}

		public void setEatingTime(int[] eatingTime) {
			this.eatingTime = eatingTime;
		}
	}
}
