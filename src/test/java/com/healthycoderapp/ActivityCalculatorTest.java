package com.healthycoderapp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class ActivityCalculatorTest {

	@Nested
	class RateActivityLevelTests {
		
		@Test
		@DisplayName("Should return bad when activity level is below 20")
		void should_ReturnBad_When_ActivityLevelBelow20() {
			//given
			int weeklyCardioMins = 40;
			int weeklyWorkouts = 1;
			
			//when
			String actual = ActivityCalculator.rateActivityLevel(weeklyCardioMins, weeklyWorkouts);
			
			//then
			assertEquals("bad", actual);
			
		}
		
		@Test
		@DisplayName("Should return average when activity level is between 20 and 40")
		void should_ReturnAverage_When_AcitvityLevelBetween20and40() {
			//given
			int weeklyCardioMins = 40;
			int weeklyWorkouts = 3;
			
			//when
			String actual = ActivityCalculator.rateActivityLevel(weeklyCardioMins, weeklyWorkouts);
			
			//then
			assertEquals("average", actual);
		}
		
		@Test
		@DisplayName("Should return good when activity level is greater than 40")
		void should_ReturnGood_When_ActivityLevelGreaterThan40() {
			//given
			int weeklyCardioMins = 40;
			int weeklyWorkouts = 7;
			
			//when
			String actual = ActivityCalculator.rateActivityLevel(weeklyCardioMins, weeklyWorkouts);
			
			//then
			assertEquals("good", actual);
		}
		
		@Test
		@DisplayName("should throw an exception when input below 0")
		void should_ThrowException_When_InputBelowZero() {
			//given
			int weeklyCardioMins = -40;
			int weeklyWorkouts = 7;
			
			//when
			Executable executable = () -> ActivityCalculator.rateActivityLevel(weeklyCardioMins, weeklyWorkouts); 
			
			//then
			assertThrows(RuntimeException.class, executable);
		}
	}

}
