package test;

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
import static keyboard.Key.ONE;
import static keyboard.Key.P;
import static keyboard.Key.Q;
import static keyboard.Key.R;
import static keyboard.Key.S;
import static keyboard.Key.T;
import static keyboard.Key.U;
import static keyboard.Key.V;
import static keyboard.Key.W;
import static keyboard.Key.X;
import static keyboard.Key.Y;
import static keyboard.Key.Z;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import keyboard.AppleNumericMB110LLKeyboardMetricsImpl_Martinez;
import keyboard.Key;
import keyboard.KeyLayout;
import keyboard.KeyboardMetrics;

public class KeyboardLayout_TestCasesSubsetForStudents_3_QWERTY_GETDISTANCE_KEY_KEY extends KeyboardLayout_TestCasesSubsetForStudents_2_ENVIRONMENT
{
	public static final String PREFIX = CLASS_NAME;
	public static final String SUFFIX = "";
	protected static KeyboardMetrics keyboardMetrics_QWERTY_STUDENT;
	protected static KeyboardMetrics keyboardMetrics_DVORAK_STUDENT;
	protected static KeyboardMetrics keyboardMetrics_COLEMAK_STUDENT;
	protected static KeyboardMetrics keyboardMetrics_ROTATION_13_STUDENT;
	protected static KeyboardMetrics keyboardMetrics_STUDENT;

	protected static Map<KeyLayout, KeyboardMetrics> layoutToKeyboardMetricsMap;
	protected static String TEST_GOAL_MESSAGE;
	
	protected final static int TIMEOUT_IN_MILLISECONDS = 3000000*1;
	
	protected KeyboardMetrics getKeyboardMetrics(KeyLayout keyLayout)
	{
		return new AppleNumericMB110LLKeyboardMetricsImpl_Martinez(keyLayout);
	}

	@Points(value=5)
	@Test(timeout=TIMEOUT_IN_MILLISECONDS)
	public void simpleDistancesQwerty()
	{
		TEST_GOAL_MESSAGE = "Calculate \"simple Qwerty\" distances correctly";
		keyboardMetrics_STUDENT = getKeyboardMetrics(KeyLayout.QWERTY);
		
		assertEquals(6.0, keyboardMetrics_STUDENT.getDistance(J, A), 0.0);
		assertEquals(5.0, keyboardMetrics_STUDENT.getDistance(A, B), 0.0);
		assertEquals(2.0, keyboardMetrics_STUDENT.getDistance(B, C), 0.0);
		assertEquals(1.0, keyboardMetrics_STUDENT.getDistance(C, D), 0.0);
		assertEquals(1.0, keyboardMetrics_STUDENT.getDistance(D, E), 0.0);
		assertEquals(2.0, keyboardMetrics_STUDENT.getDistance(E, F), 0.0);
		assertEquals(1.0, keyboardMetrics_STUDENT.getDistance(F, G), 0.0);
		assertEquals(1.0, keyboardMetrics_STUDENT.getDistance(G, H), 0.0);
		assertEquals(2.0, keyboardMetrics_STUDENT.getDistance(H, I), 0.0);
		assertEquals(1.0, keyboardMetrics_STUDENT.getDistance(I, J), 0.0);
		assertEquals(1.0, keyboardMetrics_STUDENT.getDistance(J, K), 0.0);
		assertEquals(1.0, keyboardMetrics_STUDENT.getDistance(K, L), 0.0);
		assertEquals(2.0, keyboardMetrics_STUDENT.getDistance(L, M), 0.0);
		assertEquals(1.0, keyboardMetrics_STUDENT.getDistance(M, N), 0.0);
		assertEquals(3.0, keyboardMetrics_STUDENT.getDistance(N, O), 0.0);
		assertEquals(1.0, keyboardMetrics_STUDENT.getDistance(O, P), 0.0);
		assertEquals(9.0, keyboardMetrics_STUDENT.getDistance(P, Q), 0.0);
		assertEquals(3.0, keyboardMetrics_STUDENT.getDistance(Q, R), 0.0);
		assertEquals(2.0, keyboardMetrics_STUDENT.getDistance(R, S), 0.0);
		assertEquals(3.0, keyboardMetrics_STUDENT.getDistance(S, T), 0.0);
		assertEquals(2.0, keyboardMetrics_STUDENT.getDistance(T, U), 0.0);
		assertEquals(3.0, keyboardMetrics_STUDENT.getDistance(U, V), 0.0);
		assertEquals(4.0, keyboardMetrics_STUDENT.getDistance(V, W), 0.0);
		assertEquals(2.0, keyboardMetrics_STUDENT.getDistance(W, X), 0.0);
		assertEquals(4.0, keyboardMetrics_STUDENT.getDistance(X, Y), 0.0);
		assertEquals(5.0, keyboardMetrics_STUDENT.getDistance(Y, Z), 0.0);
		assertEquals(3.0, keyboardMetrics_STUDENT.getDistance(Z, ONE), 0.0);
	}

	@Points(value=5)
	@Test(timeout=TIMEOUT_IN_MILLISECONDS)
	public void zeroDistancesQwerty()
	{
		TEST_GOAL_MESSAGE = "Calculate \"zero Qwerty\" distances correctly";
		keyboardMetrics_STUDENT = getKeyboardMetrics(KeyLayout.QWERTY);
		
		List<Key> allKeys = Arrays.asList(Key.values());
		for(int i = 0; i < allKeys.size(); i++)
		{
			Key key_i = allKeys.get(i);
			assertEquals(0.0, keyboardMetrics_STUDENT.getDistance(key_i, key_i), 0.0);
		}
	}
	
//	@Points(value=5)
//	@Test(timeout=TIMEOUT_IN_MILLISECONDS)
//	public void testRun()
//	{
//		TEST_GOAL_MESSAGE = "Calculate \"zero Qwerty\" distances correctly";
//		keyboardMetrics_STUDENT = getKeyboardMetrics(KeyLayout.QWERTY);
//		keyboardMetrics_STUDENT.toString();
//	}
}
