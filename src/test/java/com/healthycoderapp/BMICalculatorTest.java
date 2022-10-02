package com.healthycoderapp;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class BMICalculatorTest {

	private String environment = "prod";
	
	@BeforeAll
	static void breforeAll() {
		System.out.println("it should contain expensive operations tha must run once before all tests. It must be static");
	}
	
	@AfterAll
	static void afterAll() {
		System.out.println("Finish the resources created before all tests. It must be static.");
	}
	
	@Nested
	class IsDietRecommendedTests {
		
		@ParameterizedTest(name = "wheigth={0}, height={1}")
		@CsvFileSource(resources = "/diet-recommended-input-data.csv", numLinesToSkip = 1)
		void should_Return_True_When_DietRecommended(Double coderWeight, Double coderHeight) {
			
			//givern
			double wheight = coderWeight;
			double height = coderHeight;
			
			//when
			boolean recommended = BMICalculator.isDietRecommended(wheight, height);
			
			//then
			assertTrue(recommended);
		}
		
		@Test
		void should_Return_False_When_DietNotRecommended() {
			
			//givern
			double wheight = 50.0;
			double height = 1.92;
			
			//when
			boolean recommended = BMICalculator.isDietRecommended(wheight, height);
			
			//then
			assertFalse(recommended);
		}
		
		@Test
		void should_ThrowArithmeticException_When_HeightZero() {
			
			//givern
			double wheight = 50.0;
			double height = 0.00;
			
			//when
			
			Executable executable = () -> BMICalculator.isDietRecommended(wheight, height);
			
			//then
			assertThrows(ArithmeticException.class, executable);
		}
	}
	
	@Nested
	@DisplayName("Coder With Worst BMI Tests")
	class FindCoderWithWorstBMITests {
		
		@Test
		@DisplayName("should return coder with worst bmi when coder list is not empty")
		@DisabledOnOs(OS.WINDOWS)
		void should_ReturnCoderWithWWorstBMI_When_CoderListIsNotEmpty() {
			//given
			List<Coder> coders = new ArrayList<>();
			coders.add(new Coder(1.80, 60.0));
			coders.add(new Coder(1.82, 98.0));
			coders.add(new Coder(1.82, 64.7));
			
			//when
			Coder coderWithWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);
			
			//then
			assertAll(
				() -> assertEquals(1.82, coderWithWorstBMI.getHeight()),
				() -> assertEquals(98.0, coderWithWorstBMI.getWeight())
			);
		}
		
		@Test
		void should_ReturnNullWorstBMICoder_When_CoderListEmpty() {
			//given
			List<Coder> coders = new ArrayList<>();
			
			//when
			Coder coderWithWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);
			
			//then
			assertNull(coderWithWorstBMI);
		}
		
		@Test
		void should_ReturnCoderWithWorstBMIIn1Ms_When_CoderListHas10000Elements() {
			//given
			assumeTrue(BMICalculatorTest.this.environment.equals("prod"));
			List<Coder> coders = new ArrayList<>();
			for(int i = 0; i < 10000; i++) {
				coders.add(new Coder(1.0 + i, 10.0 + i));
			}
			
			//when
			Executable executable = () -> BMICalculator.findCoderWithWorstBMI(coders);
			
			//then
			assertTimeout(Duration.ofMillis(500), executable);
			
		}
	}
	
	@Nested
	class GetBMIScoresTests {
		
		@Test
		void should_ReturnCorrectBMIScoreArray_When_CoderListNotEmpty() {
			//given
			List<Coder> coders = new ArrayList<>();
			coders.add(new Coder(1.80, 60.0));
			coders.add(new Coder(1.82, 98.0));
			coders.add(new Coder(1.82, 64.7));
			double[] expected = {18.52, 29.59, 19.53}; 
			
			//when
			double[] bmiScores = BMICalculator.getBMIScores(coders);
			
			//then
			assertArrayEquals(expected, bmiScores);
		}
	}
}
