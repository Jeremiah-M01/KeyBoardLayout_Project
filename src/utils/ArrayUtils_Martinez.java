package utils;

public class ArrayUtils_Martinez
{
	private ArrayUtils_Martinez()
	{
		throw new RuntimeException("DO NOT INSTANTIATE!");
	}
	
	public static boolean containsNull(int[][] matrix)
	{
		assert matrix != null : "matrix is null!";
		//..
		boolean containsNull = false;
		
		for (int row = 0; row < matrix.length; row++) {
			if (matrix[row] == null) {
				containsNull = true;
			}
			
		}
		
		return containsNull;
	}
	
	public static boolean isSquare(int[][] squareMatrix)
	{
		assert squareMatrix != null : "squareMatrix is null!";
		//..
		boolean isSquare = true;
		int rowLength = squareMatrix.length;
		
		for (int[] row: squareMatrix) {
			//int columnLength = row.length;
			if (row.length != rowLength) {
				isSquare = false;
			}
			
		}
		
		return isSquare;
	}
	
	public static boolean isSymmetric(int[][] squareMatrix)
	{
		assert isSquare(squareMatrix) : "squareMatrix is not square!";
		//..
		boolean isSymmetric = true;
		
		//int indexOfDiagonal = 0;
		for (int row = 0; row < squareMatrix.length; row++) {
			for (int column = 0; column < squareMatrix[row].length; column++) {
				if (column == row) {
					continue;
				}
				
				if (squareMatrix[row][column] != squareMatrix[column][row]) {
					isSymmetric = false;
				}
			}
			
			//indexOfDiagonal++;
		}
		
		return isSymmetric;
	}
	
	public static int sumAllValues(int[][] matrix)
	{
		assert matrix != null : "matrix is null!";
		assert !containsNull(matrix) : "matrix contains null!";
		//..
		int sumAllValues = 0;
		
		for (int[] row: matrix) {
			for (int column: row) {
				sumAllValues += column;
			}
		}
		
		return sumAllValues;
	}
	
	public static boolean isAllZerosOnMainDiagonal(int[][] squareMatrix)
	{
		assert isSquare(squareMatrix) : "squareMatrix is not square!";
		//..
		boolean isAllZerosOnMainDiagonal = true;
		
		int indexOfDiagonal = 0;
		for (int row = 0; row < squareMatrix.length; row++) {
			if (squareMatrix[row][indexOfDiagonal] != 0) {
				isAllZerosOnMainDiagonal = false;
			}
			indexOfDiagonal++;
		}
		
		return isAllZerosOnMainDiagonal;
	}
}