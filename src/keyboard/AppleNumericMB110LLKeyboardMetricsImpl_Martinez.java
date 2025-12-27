package keyboard;

import static keyboard.Key.A;
import static keyboard.Key.B;
import static keyboard.Key.C;
import static keyboard.Key.D;
import static keyboard.Key.E;
import static keyboard.Key.F;
import static keyboard.Key.G;
import static keyboard.Key.H;
import static keyboard.Key.I;
import static keyboard.Key.J;
import static keyboard.Key.K;
import static keyboard.Key.L;
import static keyboard.Key.M;
import static keyboard.Key.N;
import static keyboard.Key.O;
import static keyboard.Key.P;
import static keyboard.Key.Q;
import static keyboard.Key.R;
import static keyboard.Key.S;
import static keyboard.Key.T;
import static keyboard.Key.SHIFT_1;
import static keyboard.Key.SHIFT_2;
import static keyboard.Key.SPACEBAR_1;
import static keyboard.Key.SPACEBAR_2;
import static keyboard.Key.SPACEBAR_3;
import static keyboard.Key.SPACEBAR_4;
import static keyboard.Key.SPACEBAR_5;
import static keyboard.Key.U;
import static keyboard.Key.V;
import static keyboard.Key.W;
import static keyboard.Key.X;
import static keyboard.Key.Y;
import static keyboard.Key.Z;
import static keyboard.Key.ZERO;
import static keyboard.Key.ONE;
import static keyboard.Key.TWO;
import static keyboard.Key.THREE;
import static keyboard.Key.FOUR;
import static keyboard.Key.FIVE;
import static keyboard.Key.SIX;
import static keyboard.Key.SEVEN;
import static keyboard.Key.EIGHT;
import static keyboard.Key.NINE;
import static keyboard.Key.TAB;
import static keyboard.Key.BACKTICK;
import static keyboard.Key.COMMA;
import static keyboard.Key.PERIOD;
import static keyboard.Key.SEMICOLON;
import static keyboard.Key.FORESLASH;
import static keyboard.Key.MINUS;
import static keyboard.Key.EQUALS;
import static keyboard.Key.LEFT_BRACKET;
import static keyboard.Key.TICK;
import static keyboard.Key.RIGHT_BRACKET;
import static keyboard.Key.RETURN;
import static keyboard.Key.BACKSLASH;
import static keyboard.KeyLayout.COLEMAK;
import static keyboard.KeyLayout.DVORAK;
import static keyboard.KeyLayout.QWERTY;
import static keyboard.KeyLayout.ROTATION_13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import combinatorics.PermutationImpl_Martinez;
import combinatorics.Permutation;
import utils.MapUtils_Martinez;
import utils.ArrayUtils_Martinez;

/**
 * @author Jeremiah Martinez
 *
 */
public class AppleNumericMB110LLKeyboardMetricsImpl_Martinez implements KeyboardMetrics {
	
	private List<Key> vertexLabels;
	private int[][] adjacencyMatrix;
	private int[][] distanceMatrix;
	private Key homeKey;
	
	private static Map<KeyLayout, Key> keyLayoutToHomeKeyMap;
	private static Map<KeyLayout, Map<Key, Set<Key>>> keyLayoutToKeyToNeighborMapMap;
	
	static
	{
		keyLayoutToHomeKeyMap = new HashMap<KeyLayout, Key>();
		keyLayoutToHomeKeyMap.put(QWERTY, J);
		keyLayoutToHomeKeyMap.put(DVORAK, H);
		keyLayoutToHomeKeyMap.put(COLEMAK, N);
		keyLayoutToHomeKeyMap.put(ROTATION_13, W);
		
		keyLayoutToKeyToNeighborMapMap = new HashMap<KeyLayout, Map<Key, Set<Key>>>();
		//A --> {Q, W, S, Z, SHIFT_1}
		Map<Key, Set<Key>> keyToNeighborMap_QWERTY = getKeyToNeighborMap_QWERTY();
		//A --> {Q, W, S, Z, SHIFT_1} + Q2D_PERMUTATION =
		//A --> {TICK, COMMA, O, SEMICOLON, SHIFT_1}
		Map<Key, Set<Key>> keyToNeighborMap_DVORAK = applyPermutationToMap(keyToNeighborMap_QWERTY, getQWERTYToDvorakPermutation());
		Map<Key, Set<Key>> keyToNeighborMap_COLEMAK = applyPermutationToMap(keyToNeighborMap_QWERTY, getQWERTYToColemakPermutation());
		Map<Key, Set<Key>> keyToNeighborMap_ROT_13 = applyPermutationToMap(keyToNeighborMap_QWERTY, getQWERTYToRotation13Permutation());
		keyLayoutToKeyToNeighborMapMap.put(QWERTY, keyToNeighborMap_QWERTY);
		keyLayoutToKeyToNeighborMapMap.put(DVORAK, keyToNeighborMap_DVORAK);
		keyLayoutToKeyToNeighborMapMap.put(COLEMAK, keyToNeighborMap_COLEMAK);
		keyLayoutToKeyToNeighborMapMap.put(ROTATION_13, keyToNeighborMap_ROT_13);
	}
	
	public AppleNumericMB110LLKeyboardMetricsImpl_Martinez(KeyLayout keyLayout)
	{
		this.homeKey = keyLayoutToHomeKeyMap.get(keyLayout);
		Map<Key, Set<Key>> keyToNeighborsMap = keyLayoutToKeyToNeighborMapMap.get(keyLayout);
		init(keyToNeighborsMap, new ArrayList<Key>(keyToNeighborsMap.keySet()));
	}
	
	public void init(Map<Key, Set<Key>> physicalKeyToNeighborsMap, List<Key> vertexLabels)
	{
		this.vertexLabels = vertexLabels;
		this.adjacencyMatrix = getAdjacencyMatrix(physicalKeyToNeighborsMap, vertexLabels);
		this.distanceMatrix = getDistanceMatrix(adjacencyMatrix);
	}
	
	private static int[][] getAdjacencyMatrix(Map<Key, Set<Key>> physicalKeyToNeighborsMap, List<Key> vertexLabels)
	{
		assert physicalKeyToNeighborsMap.keySet().equals(new HashSet<Key>(vertexLabels)) : "vertexLabels inconsistent with physicalKeyToNeighborsMap! : vertexLabels = " + vertexLabels + " physicalKeyToNeighborsMap.keySet() = " + physicalKeyToNeighborsMap.keySet();
		final int SIZE = physicalKeyToNeighborsMap.keySet().size();
		int[][] adjacencyMatrix = new int[SIZE][SIZE];
		
		//build adjacencyMatrix here...
		for (int row = 0; row < vertexLabels.size(); row++) {
			
			for (int col = 0; col < vertexLabels.size(); col++) {
				
				if (physicalKeyToNeighborsMap.get(vertexLabels.get(row)).contains(vertexLabels.get(col))) {
					adjacencyMatrix[row][col] = 1;
				} 
				// int default values are 0 and can't be null in the array
			}
		}
		
		return adjacencyMatrix;
	}
	
	//Matrix multiplication
	private static int[][] multiply(int[][] A, int[][] B)
	{
		int rowCount_A = A.length;
		assert rowCount_A > 0 : "rowCount_A = 0!";
		int columnCount_A = A[0].length;
		int rowCount_B = B.length;
		assert rowCount_B > 0 : "rowCount_B = 0!";
		int columnCount_B = B[0].length;
		assert columnCount_A == rowCount_B : "columnCount_A = " + columnCount_A + " <> " + rowCount_B + " = rowCount_B!";
		
		int[][] C = new int[rowCount_A][columnCount_B];
        for (int i = 0; i < rowCount_A; i++)
            for (int j = 0; j < columnCount_B; j++)
                for (int k = 0; k < columnCount_A; k++)
                    C[i][j] += A[i][k] * B[k][j];
        
        return C;
	}
	
	private static int[][] getDistanceMatrix(int[][] adjacencyMatrix)
	{
		int vertexCount = adjacencyMatrix.length;
		assert vertexCount > 0 : "rowCount = 0!";
		assert !ArrayUtils_Martinez.containsNull(adjacencyMatrix);
		assert ArrayUtils_Martinez.isSquare(adjacencyMatrix);
		assert ArrayUtils_Martinez.isSymmetric(adjacencyMatrix);
		assert ArrayUtils_Martinez.isAllZerosOnMainDiagonal(adjacencyMatrix);
		
		
		int[][] distanceMatrix = new int[vertexCount][vertexCount];
		
		//Figure out distanceMatrix here...
		for (int row = 0; row < adjacencyMatrix.length; row++) {
			
			distanceMatrix[row] = BFS(adjacencyMatrix, row);
			
		}
		
		return distanceMatrix;
	}
	
	// Helper method for distance matrix
	private static int[] BFS(int[][] adjacencyMatrix, int startIndex) {
		
		int[] distances = new int[adjacencyMatrix.length];
		Arrays.fill(distances, -1); // -1 indicates not visited
		Queue<Integer> queue = new LinkedList<>();
		
		distances[startIndex] = 0;
		queue.add(startIndex);
		
		while (!queue.isEmpty()) {
			int currentNode = queue.remove();
			
			for (int i = 0; i < adjacencyMatrix.length; i++) {
				
				if (adjacencyMatrix[currentNode][i] == 1 && distances[i] == -1 && i != currentNode) {
					distances[i] = distances[currentNode] + 1;
					queue.add(i);
				}
			}
			
		}
		
		return distances;
	}
	
	private static void printMatrix(int[][] matrix) {
        System.out.println("Distance Matrix:");
        for (int[] row : matrix) {
            for (int d : row) {
                System.out.print((d == -1 ? "∞" : d) + "\t");
            }
            System.out.println();
        }
    }
	
	/* (non-Javadoc)
	 * @see keyboard.KeyboardMeasurements#getDistance(keyboard.PhysicalKey, keyboard.PhysicalKey)
	 */
	@Override
	public double getDistance(Key key1, Key key2) {
		int index1 = getIndex(vertexLabels, key1);
		int index2 = getIndex(vertexLabels, key2);
		return distanceMatrix[index1][index2];
	}

	private static <E> int getIndex(List<E> list, E element)
	{
		boolean foundIndex = false;
		int i = 0;
		while(!foundIndex && i < list.size())
		{
			foundIndex = (list.get(i) == element);
			if(!foundIndex) i++;
		}
		int rv = -1;
		if(foundIndex) rv = i;
		return rv;
	}

	@Override
	//EX: "jet"
	//calculation = d('j', 'J') + d('J', 'e') + d('e', 't') + d('t', ' ')
	// 'j' --> {J}
	//J = getClosestKey({J}, J)
	//...
	// 't' --> {T}
	//T = getClosetKey({T}, E)
	//...
	//' ' --> {BAR_1, BAR_2, ..., BAR_5}
	//BAR_2 = getClosetKey({BAR_1, BAR_2, ..., BAR_5}, T)
	//calculation = d(J,    J)   + d(J,   E)  + d(E,    T)
	//= 0 + 5 + 2 = 7
	public double getDistance(String str) {
		double distance = 0;
		Key currentKey = homeKey;
		
		//Calculate distance here
		// assume homeKey is QUERTY for right now
		char[] strChars = str.toCharArray();
		List<Key> keyList = new ArrayList<Key>();
		List<Set<Key>> keySets = new ArrayList<Set<Key>>();
		
		
		// Need to transfer chars to keys
		for (char c: strChars) {
			keySets.add(getKeySet(c));
		}
		
		for (Set<Key> keys: keySets) {
			if (keys.size() > 1) {
				if (keyList.size() > 0) {
					keyList.add(getClosestKey(keys, keyList.get(keyList.size() - 1)));
				} else {
					// Spacebar is first Shift can't be passed
					for (Set<Key> keys2: keySets) {
						if (keys.size() == 1) {
							keyList.addAll(keys2);
							keyList.add(0, getClosestKey(keys, keyList.get(keyList.size() - 1)));
							break;
						}
					}
				}
				
			} else {
				keyList.addAll(keys);
			}
		}
		
		// Use distance matrix
		if (keyList.size() == 0) {
			distance = 0;
		} else {
			// Sum the distances
			keyList.add(0, currentKey);
			for (int i = 0; i < keyList.size() - 1; i++) {
				Key key1 = keyList.get(i);
				Key key2 = keyList.get(i+1);
				distance = distance + getDistance(key1, key2);
			}
		}
		
		
		return distance;
	}
	
	// use the distance matrix it is already there
	private Key getClosestKey(Set<Key> keySet, Key key)
	{
		double minDistance = 0.0;
		List<Key> keyList = new ArrayList<Key>(keySet);
		Key minDistanceKey = null;
		
		//DO SOMETHING HERE...
		//getDistance() is involved...
		List<Double> distances = new ArrayList<>();
		
		for (Key k: keyList) {
			distances.add(getDistance(k, key));
		}
		
		minDistance = Collections.min(distances);
		int indexOfMinKey = distances.indexOf(minDistance);
		minDistanceKey = keyList.get(indexOfMinKey);
		
		return minDistanceKey;
	}

	//getKeySet('j') --> {Key.J}
	//getKeySet('J') --> {Key.J}
	//getKeySet('e') --> {Key.E}
	//getKeySet('t') --> {Key.T}
	//getKeySet(' ') --> {Key.SPACEBAR_1, ..., Key.SPACEBAR_5}
	//private static Key getKeySet(char character)???
	private static Set<Key> getKeySet(char character)
	{
		List<Key> keyList = Arrays.asList(Key.values());
		Set<Key> characterProducingKeysSet = new HashSet<Key>();
		for(int i = 0; i < keyList.size(); i++)
		{
			Key key = keyList.get(i);
			assert key != null : "key is null!";
			boolean keyProducesCharacter = (key.getNormalCharacter() != null && key.getNormalCharacter() == character) || (key.getShiftModifiedCharacter() != null && key.getShiftModifiedCharacter() == character);
			if(keyProducesCharacter) characterProducingKeysSet.add(key);
		}
		return characterProducingKeysSet;
	}
	
	private static Map<Key, Set<Key>> getKeyToNeighborMap_QWERTY()
	{
		Map<Key, Set<Key>> keyToNeighborSetMap = new HashMap<Key, Set<Key>>();
		
		//Produce keyToNeighborSetMap here
		//You might want to take a look at getSet()
		keyToNeighborSetMap.put(A, getSet(Q, W, S, Z, SHIFT_1));
		keyToNeighborSetMap.put(B, getSet(G, H, N, SPACEBAR_3, V));
		keyToNeighborSetMap.put(C, getSet(V, F, D, SPACEBAR_1, X));
		keyToNeighborSetMap.put(D, getSet(S, X, C, F, R, E));
		keyToNeighborSetMap.put(E, getSet(W, S, D, R, FOUR, THREE));
		keyToNeighborSetMap.put(F, getSet(D, C, V, G, T, R));
		keyToNeighborSetMap.put(G, getSet(F, V, B, H, Y, T));
		keyToNeighborSetMap.put(H, getSet(G, B, N, J, U, Y));
		keyToNeighborSetMap.put(I, getSet(U, J, K, O, NINE, EIGHT));
		keyToNeighborSetMap.put(J, getSet(H, N, M, K, I, U));
		keyToNeighborSetMap.put(K, getSet(J, M, COMMA, L, O, I));
		keyToNeighborSetMap.put(L, getSet(K, COMMA, PERIOD, SEMICOLON, P, O));
		keyToNeighborSetMap.put(M, getSet(N, SPACEBAR_5, COMMA, K, J));
		keyToNeighborSetMap.put(N, getSet(B, SPACEBAR_4, M, J, H));
		keyToNeighborSetMap.put(O, getSet(I, K, L, P, ZERO, NINE));
		keyToNeighborSetMap.put(P, getSet(O, L, SEMICOLON, LEFT_BRACKET, MINUS, ZERO));
		keyToNeighborSetMap.put(Q, getSet(TAB, A, W, TWO, ONE));
		keyToNeighborSetMap.put(R, getSet(E, D, F, T, FIVE, FOUR));
		keyToNeighborSetMap.put(S, getSet(A, Z, X, D, E, W));
		keyToNeighborSetMap.put(T, getSet(R, F, G, Y, SIX, FIVE));
		keyToNeighborSetMap.put(U, getSet(Y, H, J, I, EIGHT, SEVEN));
		keyToNeighborSetMap.put(V, getSet(C, SPACEBAR_2, B, G, F));
		keyToNeighborSetMap.put(W, getSet(Q, A, S, E, THREE, TWO));
		keyToNeighborSetMap.put(X, getSet(Z, C, D, S));
		keyToNeighborSetMap.put(Y, getSet(T, G, H, U, SEVEN, SIX));
		keyToNeighborSetMap.put(Z, getSet(SHIFT_1, X, S, A));
		
		keyToNeighborSetMap.put(ONE, getSet(BACKTICK, TAB, Q, TWO));
		keyToNeighborSetMap.put(TWO, getSet(ONE, Q, W, THREE));
		keyToNeighborSetMap.put(THREE, getSet(TWO, W, E, FOUR));
		keyToNeighborSetMap.put(FOUR, getSet(THREE, E, R, FIVE));
		keyToNeighborSetMap.put(FIVE, getSet(FOUR, R, T, SIX));
		keyToNeighborSetMap.put(SIX, getSet(FIVE, T, Y, SEVEN));
		keyToNeighborSetMap.put(SEVEN, getSet(SIX, Y, U, EIGHT));
		keyToNeighborSetMap.put(EIGHT, getSet(SEVEN, U, I, NINE));
		keyToNeighborSetMap.put(NINE, getSet(EIGHT, I, O, ZERO));
		keyToNeighborSetMap.put(ZERO, getSet(NINE, O, P, MINUS));
		
		keyToNeighborSetMap.put(BACKTICK, getSet(TAB, ONE));
		keyToNeighborSetMap.put(TAB, getSet(BACKTICK, ONE, Q));
		keyToNeighborSetMap.put(SHIFT_1, getSet(A, Z));
		keyToNeighborSetMap.put(SPACEBAR_1, getSet(C));
		keyToNeighborSetMap.put(SPACEBAR_2, getSet(V));
		keyToNeighborSetMap.put(SPACEBAR_3, getSet(B));
		keyToNeighborSetMap.put(SPACEBAR_4, getSet(N));
		keyToNeighborSetMap.put(SPACEBAR_5, getSet(M));
		keyToNeighborSetMap.put(COMMA, getSet(M, PERIOD, L, K));
		keyToNeighborSetMap.put(PERIOD, getSet(COMMA, FORESLASH, SEMICOLON, L));
		keyToNeighborSetMap.put(SEMICOLON, getSet(L, PERIOD, FORESLASH, TICK, LEFT_BRACKET, P));
		keyToNeighborSetMap.put(MINUS, getSet(ZERO, P, LEFT_BRACKET, EQUALS));
		keyToNeighborSetMap.put(FORESLASH, getSet(PERIOD, SHIFT_2, TICK, SEMICOLON));
		keyToNeighborSetMap.put(LEFT_BRACKET, getSet(P, SEMICOLON, TICK, RIGHT_BRACKET, EQUALS, MINUS));
		keyToNeighborSetMap.put(TICK, getSet(SEMICOLON, FORESLASH, SHIFT_2, RETURN, RIGHT_BRACKET, LEFT_BRACKET));
		keyToNeighborSetMap.put(EQUALS, getSet(MINUS, LEFT_BRACKET, RIGHT_BRACKET));
		keyToNeighborSetMap.put(RIGHT_BRACKET, getSet(EQUALS, LEFT_BRACKET, TICK, RETURN, BACKSLASH));
		keyToNeighborSetMap.put(SHIFT_2, getSet(FORESLASH, RETURN, TICK));
		keyToNeighborSetMap.put(RETURN, getSet(TICK, SHIFT_2, BACKSLASH, RIGHT_BRACKET));
		keyToNeighborSetMap.put(BACKSLASH, getSet(RIGHT_BRACKET, RETURN));
		
		//...
		final int VERTEX_COUNT = 56;//?????????? 64 ? not on graph or keyboard
		assert keyToNeighborSetMap.size() == VERTEX_COUNT;
		final int EDGE_COUNT = 124;//????? 122
		assert MapUtils_Martinez.noMapValueIsNull(keyToNeighborSetMap);
		assert MapUtils_Martinez.sumAllSetSizes(keyToNeighborSetMap) == 2*EDGE_COUNT;
		assert MapUtils_Martinez.isSymmetric(keyToNeighborSetMap);
		return keyToNeighborSetMap;
	}
	
	private static Map<Key, Set<Key>> getSmallSubsetKeyToNeighborMap_QWERTY()
	{
		Map<Key, Set<Key>> keyToNeighborSetMap = new HashMap<Key, Set<Key>>();
		
		keyToNeighborSetMap.put(J, getSet(H, U, I, K, M, N));
		keyToNeighborSetMap.put(H, getSet(U, J, N));
		keyToNeighborSetMap.put(U, getSet(I, J, H));
		keyToNeighborSetMap.put(I, getSet(K, J, U));
		keyToNeighborSetMap.put(K, getSet(J, I, M));
		keyToNeighborSetMap.put(M, getSet(N, J, K));
		keyToNeighborSetMap.put(N, getSet(H, J, M));
		
		assert keyToNeighborSetMap.size() == 7 : "keyToNeighborSetMap.size() = " + keyToNeighborSetMap.size() + " <>  7!";
		
		assert MapUtils_Martinez.sumAllSetSizes(keyToNeighborSetMap) == (1*6 + 6*3) : "sumAllSetSizes(keyToNeighborSetMap) = " + MapUtils_Martinez.sumAllSetSizes(keyToNeighborSetMap) + " <> " + (1*6 + 6*3) + " = (1*6 + 6*3)";
		
		return keyToNeighborSetMap;
	}
	
	private static Map<Key, Set<Key>> getMiniKeyToNeighborMap()
	{
		Map<Key, Set<Key>> keyToNeighborSetMap = new HashMap<Key, Set<Key>>();
		
		keyToNeighborSetMap.put(A, getSet(E, I, O, U));
		keyToNeighborSetMap.put(E, getSet(I, O, U));
		keyToNeighborSetMap.put(I, getSet(O, U));
		keyToNeighborSetMap.put(O, getSet(U));
		keyToNeighborSetMap.put(U, getSet());

		assert keyToNeighborSetMap.size() == 5 : "keyToNeighborSetMap.size() = " + keyToNeighborSetMap.size() + " <>  5!";
		
		assert MapUtils_Martinez.sumAllSetSizes(keyToNeighborSetMap) == (4 + 3 + 2 + 1 + 0) : "sumAllSetSizes(keyToNeighborSetMap) = " + MapUtils_Martinez.sumAllSetSizes(keyToNeighborSetMap) + " <> " + (4 + 3 + 2 + 1 + 0) + " = (4 + 3 + 2 + 1 + 0)";
		
		return keyToNeighborSetMap;
	}
	
	private static Set<Key> getSet(Key... keys)
	{
		return new HashSet<Key>(Arrays.asList(keys));
	}
	
	//Map<Key, Key> qwertyToDvorakPermutation;
	
	private static Permutation<Key> getQWERTYToDvorakPermutation()
	{
		Map<Key, Set<Key>> keyToNeighborMap_QWERTY = getKeyToNeighborMap_QWERTY();
		
		Set<Key> domain = new HashSet<Key>(keyToNeighborMap_QWERTY.keySet());
		Set<List<Key>> cycles = new HashSet<List<Key>>();
		
		List<Key> currentCycle = new ArrayList<Key>();
		
		currentCycle = Arrays.asList(J, H, D, E, PERIOD, V, K, T, Y, F, U, G, I, C);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(Q, TICK, MINUS, LEFT_BRACKET, FORESLASH, Z, SEMICOLON, S, O, R, P, L, N, B, X);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(W, COMMA);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(RIGHT_BRACKET, EQUALS);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(A);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(BACKSLASH);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(M);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(ONE);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(TWO);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(THREE);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(FOUR);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(FIVE);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(SIX);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(SEVEN);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(EIGHT);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(NINE);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(ZERO);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(BACKTICK);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(TAB);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(SHIFT_1);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(SHIFT_2);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(RETURN);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(SPACEBAR_1);
		cycles.add(currentCycle);
		currentCycle = Arrays.asList(SPACEBAR_2);
		cycles.add(currentCycle);
		currentCycle = Arrays.asList(SPACEBAR_3);
		cycles.add(currentCycle);
		currentCycle = Arrays.asList(SPACEBAR_4);
		cycles.add(currentCycle);
		currentCycle = Arrays.asList(SPACEBAR_5);
		cycles.add(currentCycle);
		
		return new PermutationImpl_Martinez<Key>(cycles, domain);	
	}
	
	private static Permutation<Key> getQWERTYToColemakPermutation()
	{
		Map<Key, Set<Key>> keyToNeighborMap_QWERTY = getKeyToNeighborMap_QWERTY();
		
		Set<Key> domain = new HashSet<Key>(keyToNeighborMap_QWERTY.keySet());
		Set<List<Key>> cycles = new HashSet<List<Key>>();
		
		List<Key> currentCycle = new ArrayList<Key>();
		
		currentCycle = Arrays.asList(J, N, K, E, F, T, G, D, S, R, P, SEMICOLON, O, Y);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(U, L, I);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(Q);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(W);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(A);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(RIGHT_BRACKET);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(LEFT_BRACKET);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(TICK);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(BACKSLASH);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(M);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(ONE);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(TWO);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(THREE);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(FOUR);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(FIVE);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(SIX);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(SEVEN);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(EIGHT);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(NINE);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(ZERO);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(COMMA);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(MINUS);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(EQUALS);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(FORESLASH);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(BACKSLASH);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(PERIOD);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(Z);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(X);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(C);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(V);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(B);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(BACKTICK);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(TAB);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(SHIFT_1);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(SHIFT_2);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(RETURN);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(SPACEBAR_1);
		cycles.add(currentCycle);
		currentCycle = Arrays.asList(SPACEBAR_2);
		cycles.add(currentCycle);
		currentCycle = Arrays.asList(SPACEBAR_3);
		cycles.add(currentCycle);
		currentCycle = Arrays.asList(SPACEBAR_4);
		cycles.add(currentCycle);
		currentCycle = Arrays.asList(SPACEBAR_5);
		cycles.add(currentCycle);
		
		return new PermutationImpl_Martinez<Key>(cycles, domain);	
	}
	
	private static Permutation<Key> getQWERTYToRotation13Permutation()
	{
		Map<Key, Set<Key>> keyToNeighborMap_QWERTY = getKeyToNeighborMap_QWERTY();
		
		Set<Key> domain = new HashSet<Key>(keyToNeighborMap_QWERTY.keySet());
		Set<List<Key>> cycles = new HashSet<List<Key>>();
		
		List<Key> currentCycle = new ArrayList<Key>();
		
		currentCycle = Arrays.asList(A, N);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(B, O);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(C, P);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(D, Q);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(E, R);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(F, S);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(G, T);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(H, U);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(I, V);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(J, W);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(K, X);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(L, Y);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(M, Z);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(BACKSLASH);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(FORESLASH);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(COMMA);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(PERIOD);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(RIGHT_BRACKET);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(LEFT_BRACKET);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(TICK);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(MINUS);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(EQUALS);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(ONE);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(TWO);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(THREE);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(FOUR);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(FIVE);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(SIX);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(SEVEN);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(EIGHT);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(NINE);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(ZERO);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(BACKTICK);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(TAB);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(SHIFT_1);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(SHIFT_2);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(RETURN);
		cycles.add(currentCycle);
		
		currentCycle = Arrays.asList(SPACEBAR_1);
		cycles.add(currentCycle);
		currentCycle = Arrays.asList(SPACEBAR_2);
		cycles.add(currentCycle);
		currentCycle = Arrays.asList(SPACEBAR_3);
		cycles.add(currentCycle);
		currentCycle = Arrays.asList(SPACEBAR_4);
		cycles.add(currentCycle);
		currentCycle = Arrays.asList(SPACEBAR_5);
		cycles.add(currentCycle);
		
		return new PermutationImpl_Martinez<Key>(cycles, domain);
	}
	
	private static <E> Map<E, Set<E>> applyPermutationToMap(Map<E, Set<E>> map, Permutation<E> permutation)
	{
		//Ex: map = {A -> {B, E}, B -> {A, C, D}, C -> {B}, D -> {B}, E -> {A}}, permutation(A) = B, permutation(B) = C, permutation(C) = A
		Map<E, Set<E>> newMap = new HashMap<E, Set<E>>();

		//DO SOMETHING HERE...
		
		for (E key: map.keySet()) {
			E keyVal = permutation.getImage(key);
			Set<E> newKeyVals = new HashSet<E>();
			
			for (E key2: map.get(key)) {
				newKeyVals.add(permutation.getImage(key2));
			}
			
			newMap.put(keyVal, newKeyVals);
		}
		//Ex: map = {C -> {B, E}, A -> {A, C, D}, B -> {B}, D -> {B}, E -> {A}}, permutation(A) = B, permutation(B) = C, permutation(C) = A
		//Ex: map = {C -> {A, E}, A -> {C, B, D}, B -> {A}, D -> {A}, E -> {C}}, permutation(A) = B, permutation(B) = C, permutation(C) = A
		return newMap;
	}
	
	@Override
	public String toString()
	{
		printMatrix(distanceMatrix);
		return "";
	}
}