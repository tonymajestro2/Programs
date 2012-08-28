import static org.junit.Assert.*;
import org.junit.Test;


public class TestMethods {

	@Test
	public void testRollThreeTimes()
	{
		Monopoly m = new Monopoly();
		int r1 = 2; int r2 = 3;
		int hashcode = String.format("%d %d", r1, r2).hashCode();
		m.pastMoves.add(hashcode);
		m.pastMoves.add(hashcode);
		assertTrue(m.sameRollThreeTimes(3, 2));
		assertFalse(m.sameRollThreeTimes(3, 3));
		assertFalse(m.sameRollThreeTimes(3, 3));
		assertTrue(m.sameRollThreeTimes(3, 3));
	}

}
