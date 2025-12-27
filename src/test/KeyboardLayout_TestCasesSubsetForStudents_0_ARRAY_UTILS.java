package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import utils.ArrayUtils_Martinez;

public class KeyboardLayout_TestCasesSubsetForStudents_0_ARRAY_UTILS
{
	@Points(value=10)
	@Test
	public void containsNull_False()
	{
		int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
		boolean expected = false;
		boolean actual = ArrayUtils_Martinez.containsNull(matrix);
		assertEquals(expected, actual);
	}

	@Points(value=10)
	@Test
	public void containsNull_True()
	{
		int[][] matrix = {{1}, {1, 2}, null};
		boolean expected = true;
		boolean actual = ArrayUtils_Martinez.containsNull(matrix);
		assertEquals(expected, actual);
	}

	@Points(value=10)
	@Test
	public void containsSquare_Straightforward()
	{
		int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
		boolean expected = true;
		boolean actual = ArrayUtils_Martinez.isSquare(matrix);
		assertEquals(expected, actual);
	}

	@Points(value=10)
	@Test
	public void containsSquare_Extreme()
	{
		int[][] matrix = {};
		boolean expected = true;
		boolean actual = ArrayUtils_Martinez.isSquare(matrix);
		assertEquals(expected, actual);
	}

	@Points(value=10)
	@Test(expected=AssertionError.class)
	public void containsSquare_Bizotic()
	{
		int[][] matrix = null;
		ArrayUtils_Martinez.isSquare(matrix);
	}

	@Points(value=10)
	@Test
	public void isSymmetric_Straightforward()
	{
		int[][] matrix = {{1, 2, 3}, {2, 4, 5}, {3, 5, 6}};
		boolean expected = true;
		boolean actual = ArrayUtils_Martinez.isSymmetric(matrix);
		assertEquals(expected, actual);
	}

	@Points(value=10)
	@Test(expected=AssertionError.class)
	public void isSymmetric_Extreme()
	{
		int[][] matrix = {{}, {}, {}};
		ArrayUtils_Martinez.isSymmetric(matrix);
	}

	@Points(value=10)
	@Test
	public void isSymmetric_Bizotic()
	{
		int[][] matrix = {{7}};
		boolean expected = true;
		boolean actual = ArrayUtils_Martinez.isSymmetric(matrix);
		assertEquals(expected, actual);
	}

	@Points(value=10)
	@Test
	public void isZeroOnMainDiagonal_Straightforward()
	{
		int[][] matrix = {{0, 1, 2}, {3, 0, 4}, {5, 6, 0}};
		boolean expected = true;
		boolean actual = ArrayUtils_Martinez.isAllZerosOnMainDiagonal(matrix);
		assertEquals(expected, actual);
	}

	@Points(value=10)
	@Test
	public void isZeroOnMainDiagonal_Extreme()
	{
		int[][] matrix = {{0}};
		boolean expected = true;
		boolean actual = ArrayUtils_Martinez.isAllZerosOnMainDiagonal(matrix);
		assertEquals(expected, actual);
	}

	@Points(value=10)
	@Test
	public void isZeroOnMainDiagonal_Bizotic()
	{
		int[][] matrix = {};
		boolean expected = true;
		boolean actual = ArrayUtils_Martinez.isAllZerosOnMainDiagonal(matrix);
		assertEquals(expected, actual);
	}
}
