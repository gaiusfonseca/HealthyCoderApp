package com.healthycoderapp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

class DietPlannerTest {

	
	private DietPlanner dietPlanner;
	
	@BeforeEach
	void setup() {
		dietPlanner = new DietPlanner(20, 30, 50);	
	}
	
	@AfterEach
	void afterEach() {
		System.out.println("executes after each test");
	}
	
	@RepeatedTest(value = 10, name = RepeatedTest.LONG_DISPLAY_NAME)
	void should_ReturnCorrectDietPlan_When_CorrectCoder() {
		//given
		Coder coder = new Coder(1.82, 75, 26, Gender.MALE);
		DietPlan expected = new DietPlan(2202, 110, 73, 275);
		
		//when
		DietPlan actual = dietPlanner.calculateDiet(coder);
		
		//then
		assertAll(
				() -> assertEquals(expected.getCalories(), actual.getCalories()),
				() -> assertEquals(expected.getCarbohydrate(), actual.getCarbohydrate()),
				() -> assertEquals(expected.getFat(), actual.getFat()),
				() -> assertEquals(expected.getProtein(), actual.getProtein())
		);
	}

}
