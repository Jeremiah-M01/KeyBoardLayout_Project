package test;

import static keyboard.Key.EIGHT;
import static keyboard.Key.FIVE;
import static keyboard.Key.FOUR;
import static keyboard.Key.J;
import static keyboard.Key.NINE;
import static keyboard.Key.ONE;
import static keyboard.Key.PERIOD;
import static keyboard.Key.SEVEN;
import static keyboard.Key.SIX;
import static keyboard.Key.THREE;
import static keyboard.Key.TWO;
import static keyboard.Key.ZERO;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import keyboard.Key;
import keyboard.KeyLayout;

public class KeyboardLayout_TestCasesSubsetForStudents_4_INTERMEDIATE extends KeyboardLayout_TestCasesSubsetForStudents_3_QWERTY_GETDISTANCE_KEY_KEY
{
	@Points(value=5)
	@Test(timeout=TIMEOUT_IN_MILLISECONDS)
	public void simpleTestQwerty()
	{
		keyboardMetrics_STUDENT = getKeyboardMetrics(KeyLayout.QWERTY);
		String testString = "jjjjjjjjjjjjjjjjjj";
		TEST_GOAL_MESSAGE = "Calculate getDistance(\"" + testString + "\") correctly";
		assertEquals(0.0, keyboardMetrics_STUDENT.getDistance("jjjjjjjjjjjjjjjjjj"), 0.0);
	}
	
	@Points(value=10)
	@Test(timeout=TIMEOUT_IN_MILLISECONDS)
	public void shiftTestQwerty()
	{
		keyboardMetrics_STUDENT = getKeyboardMetrics(KeyLayout.QWERTY);
		String normalAndShiftModifiedCharacters = "jJuU7&6^5%4$3#2@1!";
		TEST_GOAL_MESSAGE = "Calculate getDistance(\"" + normalAndShiftModifiedCharacters + "\") correctly";
		assertEquals(normalAndShiftModifiedCharacters.length()/2-1, keyboardMetrics_STUDENT.getDistance(normalAndShiftModifiedCharacters), 0.0);
	}
	
	@Points(value=5)
	@Test(timeout=TIMEOUT_IN_MILLISECONDS)
	public void newlineTestQwerty()
	{
		keyboardMetrics_STUDENT = getKeyboardMetrics(KeyLayout.QWERTY);
		String testString = "jkl;\'\n]\n";
		TEST_GOAL_MESSAGE = "Calculate getDistance(\"" + testString + "\") correctly";
		assertEquals(testString.length() - 1, keyboardMetrics_STUDENT.getDistance(testString), 0.0);
	}
	
	@Points(value=10)
	@Test(timeout=TIMEOUT_IN_MILLISECONDS)
	public void numberTest()
	{
		TEST_GOAL_MESSAGE = "Calculate typing distances related to pi correctly";
		keyboardMetrics_STUDENT = getKeyboardMetrics(KeyLayout.QWERTY);
		
		Map<Integer, Key> integerToKeyMap = new HashMap<Integer, Key>();
		integerToKeyMap.put(0, ZERO);
		integerToKeyMap.put(1, ONE);
		integerToKeyMap.put(2, TWO);
		integerToKeyMap.put(3, THREE);
		integerToKeyMap.put(4, FOUR);
		integerToKeyMap.put(5, FIVE);
		integerToKeyMap.put(6, SIX);
		integerToKeyMap.put(7, SEVEN);
		integerToKeyMap.put(8, EIGHT);
		integerToKeyMap.put(9, NINE);
		
		String pi_approximation = "3.141592653589793238462643383279502884197169399375105820974944";
		assertEquals(6, keyboardMetrics_STUDENT.getDistance(J, THREE), 0.0);
		assertEquals(9, keyboardMetrics_STUDENT.getDistance(THREE, PERIOD), 0.0);
		assertEquals(11, keyboardMetrics_STUDENT.getDistance(PERIOD, ONE), 0.0);
		double distance = 6 + 9 + 11;
		char currentChar = '1';
		for(int i = 3; i < pi_approximation.length(); i++)
		{
			int lastDigitTyped = Integer.parseInt("" + currentChar);
			Key lastKeyTyped = integerToKeyMap.get(lastDigitTyped);
			char digitCharacter = pi_approximation.charAt(i);
			int nextDigitToType = Integer.parseInt("" + digitCharacter);
			Key nextKeyToType = integerToKeyMap.get(nextDigitToType);
			double lastKeyToNextKeyDistance = keyboardMetrics_STUDENT.getDistance(lastKeyTyped, nextKeyToType);
			int digitDistance = Math.abs((nextDigitToType == 0 ? 10 : nextDigitToType) - (lastDigitTyped == 0 ? 10 : lastDigitTyped));
			assertEquals(digitDistance, lastKeyToNextKeyDistance, 0.0);
			distance = distance + digitDistance;
			assertEquals(distance, keyboardMetrics_STUDENT.getDistance(pi_approximation.substring(0, i + 1)), 0.0);
			currentChar = digitCharacter;
		}
	}
	
	@Points(value=10)
	@Test(timeout=TIMEOUT_IN_MILLISECONDS)
	public void simpleTest2Qwerty()
	{
		keyboardMetrics_STUDENT = getKeyboardMetrics(KeyLayout.QWERTY);
		String testString = "8*";
		TEST_GOAL_MESSAGE = "Calculate getDistance(\"" + testString + "\") correctly";
		assertEquals(2.0, keyboardMetrics_STUDENT.getDistance(testString), 0.0);
	}
	
	@Points(value=10)
	@Test(timeout=TIMEOUT_IN_MILLISECONDS)
	public void neighborToNeighborTestQwerty()
	{
		TEST_GOAL_MESSAGE = "Calculate neighbor to neighbor path distance correctly";
		keyboardMetrics_STUDENT = getKeyboardMetrics(KeyLayout.QWERTY);
		String pathOfAdjacentKeysStartingAtHomeKey = "jhgfdsasdfghjkl;\']\\][poiuyhgtrfvgt567uyhnjm mnbhn nbhgv vfc cvfdxzsaqw23ewqwerty";
		assertEquals(pathOfAdjacentKeysStartingAtHomeKey.length() - 1, keyboardMetrics_STUDENT.getDistance(pathOfAdjacentKeysStartingAtHomeKey), 0.0);
	}

	@Points(value=10)
	@Test(timeout=TIMEOUT_IN_MILLISECONDS)
	public void skipNeighborTestQwerty()
	{
		TEST_GOAL_MESSAGE = "Calculate \"skip neighbor\" distances correctly";
		keyboardMetrics_STUDENT = getKeyboardMetrics(KeyLayout.QWERTY);
		String pathOfDistanceTwoJumps = "jl9&tdzq3r6hi0=;]piyrw\tadgj";
		assertEquals(2*(pathOfDistanceTwoJumps.length() - 1), keyboardMetrics_STUDENT.getDistance(pathOfDistanceTwoJumps), 0.0);
	}

	@Points(value=10)
	@Test(timeout=TIMEOUT_IN_MILLISECONDS)
	public void simpleDistancesQwerty2()
	{
		TEST_GOAL_MESSAGE = "Calculate \"simple Qwerty\" distances correctly for strings";
		assertEquals(71.0, keyboardMetrics_STUDENT.getDistance("abcdefghijklmnopqrstuvwxyz!"), 0.0);
		assertEquals(71.0, keyboardMetrics_STUDENT.getDistance("AbCdEfGhIjKlMnOpQrStUvWxYz!"), 0.0);
		assertEquals(66.0, keyboardMetrics_STUDENT.getDistance("mnopqrstuvwxyz!abcdefghijkl"), 0.0);
	}
}
