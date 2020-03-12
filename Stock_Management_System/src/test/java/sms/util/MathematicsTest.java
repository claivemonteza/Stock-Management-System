/**
 * 
 */
package sms.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sms.util.Mathematics;

/**
 * @author Claive Monteza
 *
 */
public class MathematicsTest {

	private static Mathematics mat;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {

	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		mat = new Mathematics();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		mat = null;
	}

	/**
	 * Test method for
	 * {@link sgs.util.Mathematics#calculcatePriceForSale(double, double)}.
	 */
	@SuppressWarnings("static-access")
	@Test
	void testCalculcatePriceForSale() {
		assertEquals(80, mat.calculcatePriceForSale(50, 6));
	}

	/**
	 * Test method for
	 * {@link sgs.util.Mathematics#calculcatePercentage(double, double)}.
	 */
	@SuppressWarnings("static-access")
	@Test
	void testCalculcatePercentage() {
		
		assertEquals(6, mat.calculcatePercentage(80, 50));
		
	}

	/**
	 * Test method for
	 * {@link sgs.util.Mathematics#calculateTotalUnity(int, double)}.
	 */
	@SuppressWarnings("static-access")
	@Test
	void testCalculateTotalUnity() {

		assertEquals(5000, mat.calculateTotalUnity(100, 50));
	}

	/**
	 * Test method for {@link sgs.util.Mathematics#unpaid(double, double)}.
	 */
	@SuppressWarnings("static-access")
	@Test
	void testUnpaid() {
		assertEquals(3000, mat.unpaid(5000, 2000));
	}

	/**
	 * Test method for {@link sgs.util.Mathematics#change(double, double)}.
	 */
	@SuppressWarnings("static-access")
	@Test
	void testChange() {
		assertEquals(0, mat.change(150, 150));
	}

}
