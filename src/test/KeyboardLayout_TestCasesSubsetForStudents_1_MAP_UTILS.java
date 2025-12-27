package test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

import utils.MapUtils_Martinez;

public class KeyboardLayout_TestCasesSubsetForStudents_1_MAP_UTILS extends KeyboardLayout_TestCasesSubsetForStudents_0_ARRAY_UTILS
{	
	//part of post: getDistinctCharacterSet("abcd") == {'a', 'b', 'c', 'd'}
	private static Set<Character> getDistinctCharacterSet(String str)
	{
		assert str != null : "str is null!";
		Set<Character> distinctCharacterSet = new HashSet<Character>();
		for(int i = 0; i < str.length(); i++)
		{
			char ch_i = str.charAt(i);
			distinctCharacterSet.add(ch_i);
		}
		return distinctCharacterSet;
	}
	
	//Note: this method produces the same output for a given input
	//EX: getRandomCuts("abcdefghijklmnopqrstuvwxyz") produces something like
	//			1 -> {'a', 'b', 'c'}
	//			2 -> {'d', 'e'}
	//			3 -> {'f', 'g', 'h', 'i'}
	//			4 -> {}
	//			5 -> {'j', 'k', 'l'}
	//			6 -> {'m', 'n', 'o', 'p'}
	//			7 -> {'q', 'r', 's'}
	//			8 -> {'t', 'u', 'v', 'w', 'x'}
	//			9 -> {'y', 'z'}
	private static Map<Integer, Set<Character>> getRandomCuts(String str)
	{	
		Random rng = new Random(9223372036854775807l);
		int previousIndex = 0;
		int currentIndex = 0;
		final int MAX_RUN_LENGTH = 5;
		Map<Integer, Set<Character>> indexToCharacterSet = new HashMap<Integer, Set<Character>>();

		int keyValuePairCount = 0;
		while(previousIndex < str.length())
		{
			currentIndex = Math.min(currentIndex + (rng.nextInt(MAX_RUN_LENGTH + 1)), str.length());
			String substringPrevIndexToCurrentIndex = str.substring(previousIndex, currentIndex);
			Set<Character> distinctCharacterSet = getDistinctCharacterSet(substringPrevIndexToCurrentIndex);
			indexToCharacterSet.put(keyValuePairCount, distinctCharacterSet);
			keyValuePairCount++;
			previousIndex = currentIndex;
		}
		return indexToCharacterSet;
	}
	
	@Points(value=10)
	@Test
	public void noMapValueIsNull_True_Straightforward()
	{
		String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Map<Integer, Set<Character>> indexToCharacterSet = getRandomCuts(characters);
		
		boolean expected = true;
		boolean actual = MapUtils_Martinez.noMapValueIsNull(indexToCharacterSet);
		assertEquals(expected, actual);
	}

	@Points(value=10)
	@Test
	public void noMapValueIsNull_False_Straightforward()
	{
		Map<String, String> stringToStringMap = new HashMap<String, String>();
		stringToStringMap.put("", null);
		
		boolean expected = false;
		boolean actual = MapUtils_Martinez.noMapValueIsNull(stringToStringMap);
		assertEquals(expected, actual);
	}

	@Points(value=10)
	@Test
	public void noMapValueIsNull_True_Extreme()
	{
		Map<String, String> stringToStringMap = new HashMap<String, String>();
		
		boolean expected = true;
		boolean actual = MapUtils_Martinez.noMapValueIsNull(stringToStringMap);
		assertEquals(expected, actual);
	}

	@Points(value=10)
	@Test(expected=AssertionError.class)
	public void noMapValueIsNull_Bizotic()
	{
		Map<String, String> stringToStringMap = null;
		MapUtils_Martinez.noMapValueIsNull(stringToStringMap);
	}

	@Points(value=10)
	@Test
	public void sumAllSetSizes_Straightforward1()
	{
		String distinctCharacters = "0123456789";
		Map<Integer, Set<Character>> indexToCharacterSet = getRandomCuts(distinctCharacters);

		int expected = distinctCharacters.length();
		int actual = MapUtils_Martinez.sumAllSetSizes(indexToCharacterSet);
		assertEquals(expected, actual);
	}

	@Points(value=10)
	@Test
	public void sumAllSetSizes_Straightforward2()
	{
		String distinctCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Map<Integer, Set<Character>> indexToCharacterSet = getRandomCuts(distinctCharacters);

		int expected = distinctCharacters.length();
		int actual = MapUtils_Martinez.sumAllSetSizes(indexToCharacterSet);
		assertEquals(expected, actual);
	}

	@Points(value=10)
	@Test
	public void isSymmetric_True_Straightforward()
	{
		Map<Integer, Set<Integer>> elementToSetMap = new HashMap<Integer, Set<Integer>>();
		
		Integer A = 1;
		Integer B = 2;
		
		Set<Integer> set_A = new HashSet<Integer>();
		Set<Integer> set_B = new HashSet<Integer>();
		set_A.add(B);
		set_B.add(A);
		
		elementToSetMap.put(A, set_A);
		elementToSetMap.put(B, set_B);
		
		boolean expected = true;
		boolean actual = MapUtils_Martinez.isSymmetric(elementToSetMap);
		assertEquals(expected, actual);
	}

	@Points(value=10)
	@Test
	public void isSymmetric_False_Straightforward()
	{
		Map<Integer, Set<Integer>> elementToSetMap = new HashMap<Integer, Set<Integer>>();
		
		Integer A = 1;
		Integer B = 2;
		
		Set<Integer> set_A = new HashSet<Integer>();
		Set<Integer> set_B = new HashSet<Integer>();
		//set_A.add(B);
		set_B.add(A);
		
		elementToSetMap.put(A, set_A);
		elementToSetMap.put(B, set_B);
		
		boolean expected = false;
		boolean actual = MapUtils_Martinez.isSymmetric(elementToSetMap);
		assertEquals(expected, actual);
	}

	@Points(value=10)
	@Test
	public void isSymmetric_True_Extreme()
	{
		Map<Integer, Set<Integer>> elementToSetMap = new HashMap<Integer, Set<Integer>>();
		
		boolean expected = true;
		boolean actual = MapUtils_Martinez.isSymmetric(elementToSetMap);
		assertEquals(expected, actual);
	}

	@Points(value=10)
	@Test(expected=AssertionError.class)
	public void isSymmetric_True_Bizotic()
	{
		Map<Integer, Set<Integer>> elementToSetMap = null;
		MapUtils_Martinez.isSymmetric(elementToSetMap);
	}
}
