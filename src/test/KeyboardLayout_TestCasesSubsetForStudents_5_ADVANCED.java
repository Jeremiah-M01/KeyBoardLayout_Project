package test;

import static keyboard.Key.A;
import static keyboard.Key.B;
import static keyboard.Key.C;
import static keyboard.Key.COMMA;
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
import static keyboard.Key.PERIOD;
import static keyboard.Key.Q;
import static keyboard.Key.R;
import static keyboard.Key.SEMICOLON;
import static keyboard.Key.T;
import static keyboard.Key.TICK;
import static keyboard.Key.U;
import static keyboard.Key.X;
import static keyboard.Key.Y;
import static keyboard.Key.ZERO;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import keyboard.KeyLayout;

public class KeyboardLayout_TestCasesSubsetForStudents_5_ADVANCED extends KeyboardLayout_TestCasesSubsetForStudents_4_INTERMEDIATE
{
	@Points(value=7)
	@Test(timeout=TIMEOUT_IN_MILLISECONDS)
	public void simpleDistancesDvorak()
	{
		TEST_GOAL_MESSAGE = "Calculate \"simple Dvorak\" distances correctly";
		keyboardMetrics_STUDENT = getKeyboardMetrics(KeyLayout.DVORAK);
		//keyboardMetrics_STUDENT.toString();
		
		assertEquals(6.0, keyboardMetrics_STUDENT.getDistance(H, A), 0.0);
		assertEquals(5.0, keyboardMetrics_STUDENT.getDistance(A, X), 0.0);
		assertEquals(2.0, keyboardMetrics_STUDENT.getDistance(X, J), 0.0);
		assertEquals(1.0, keyboardMetrics_STUDENT.getDistance(J, E), 0.0);
		assertEquals(1.0, keyboardMetrics_STUDENT.getDistance(E, PERIOD), 0.0);
		assertEquals(2.0, keyboardMetrics_STUDENT.getDistance(PERIOD, U), 0.0);
		assertEquals(1.0, keyboardMetrics_STUDENT.getDistance(U, I), 0.0);
		assertEquals(1.0, keyboardMetrics_STUDENT.getDistance(I, D), 0.0);
		assertEquals(2.0, keyboardMetrics_STUDENT.getDistance(D, C), 0.0);
		assertEquals(1.0, keyboardMetrics_STUDENT.getDistance(C, H), 0.0);
		assertEquals(1.0, keyboardMetrics_STUDENT.getDistance(H, T), 0.0);
		assertEquals(1.0, keyboardMetrics_STUDENT.getDistance(T, N), 0.0);
		assertEquals(2.0, keyboardMetrics_STUDENT.getDistance(N, M), 0.0);
		assertEquals(1.0, keyboardMetrics_STUDENT.getDistance(M, B), 0.0);
		assertEquals(3.0, keyboardMetrics_STUDENT.getDistance(B, R), 0.0);
		assertEquals(1.0, keyboardMetrics_STUDENT.getDistance(R, L), 0.0);
		assertEquals(9.0, keyboardMetrics_STUDENT.getDistance(L, TICK), 0.0);
		assertEquals(3.0, keyboardMetrics_STUDENT.getDistance(TICK, P), 0.0);
		assertEquals(2.0, keyboardMetrics_STUDENT.getDistance(P, O), 0.0);
		assertEquals(3.0, keyboardMetrics_STUDENT.getDistance(O, Y), 0.0);
		assertEquals(2.0, keyboardMetrics_STUDENT.getDistance(Y, G), 0.0);
		assertEquals(3.0, keyboardMetrics_STUDENT.getDistance(G, K), 0.0);
		assertEquals(4.0, keyboardMetrics_STUDENT.getDistance(K, COMMA), 0.0);
		assertEquals(2.0, keyboardMetrics_STUDENT.getDistance(COMMA, Q), 0.0);
		assertEquals(4.0, keyboardMetrics_STUDENT.getDistance(Q, F), 0.0);
		assertEquals(5.0, keyboardMetrics_STUDENT.getDistance(F, SEMICOLON), 0.0);
		assertEquals(3.0, keyboardMetrics_STUDENT.getDistance(SEMICOLON, ONE), 0.0);
		assertEquals(71.0, keyboardMetrics_STUDENT.getDistance("axje.uidchtnmbrl'poygk,qf;!"), 0.0);
		assertEquals(71.0, keyboardMetrics_STUDENT.getDistance("AxJe.UiDcHtNmBrL'PoYgK,Qf:!"), 0.0);
		assertEquals(66.0, keyboardMetrics_STUDENT.getDistance("mbrl'poygk,qf;!axje.uidchtn"), 0.0);
	}
	
	@Points(value=7)
	@Test(timeout=TIMEOUT_IN_MILLISECONDS)
	public void simpleDistancesColemak()
	{
		TEST_GOAL_MESSAGE = "Calculate \"simple Colemak\" distances correctly";
		keyboardMetrics_STUDENT = getKeyboardMetrics(KeyLayout.COLEMAK);
		//keyboardMetrics_STUDENT.toString();
		
		assertEquals(4.0, keyboardMetrics_STUDENT.getDistance(ZERO, J), 0.0);
		
		//assertEquals(66.0, keyboardMetrics_STUDENT.getDistance("mbrl'poygk,qf;!axje.uidchtn"), 0.0);
	}
	
	@Points(value=7)
	@Test(timeout=TIMEOUT_IN_MILLISECONDS)
	public void simpleDistancesROT13()
	{
		TEST_GOAL_MESSAGE = "Calculate \"simple ROT_13\" distances correctly";
		keyboardMetrics_STUDENT = getKeyboardMetrics(KeyLayout.ROTATION_13);
		//keyboardMetrics_STUDENT.toString();
		
		assertEquals(3.0, keyboardMetrics_STUDENT.getDistance(ONE, M), 0.0);
		
		//assertEquals(66.0, keyboardMetrics_STUDENT.getDistance("mbrl'poygk,qf;!axje.uidchtn"), 0.0);
	}
}
