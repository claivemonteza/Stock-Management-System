/**
 * 
 */
package sms.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sms.util.Verifications;

/**
 * @author Claive Monteza
 *
 */
public class VerificationsTest {

	private static Verifications verification;
	private static List<String> palavras ;

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
		verification = new Verifications();
		palavras = new ArrayList<String>();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		verification = null;
		palavras = null;
	}



	/**
	 * Test method for {@link sgs.util.Verifications#duplication(java.util.List)}.
	 */
	@SuppressWarnings("static-access")
	@Test
	void testDuplication() {
		palavras.add("Ola");
		palavras.add("Hi");
		palavras.add("Holla");
		palavras.add("Banana");
		palavras.add("Apple");
		palavras.add("Hi");
		palavras.add("Love");
		assertNotEquals(palavras, verification.duplication(palavras));

	}

	/**
	 * Test method for {@link sgs.util.Verifications#emptyOrNull(java.util.List)}.
	 */
	@SuppressWarnings("static-access")
	@Test
	void testEmptyOrNull() {
		palavras.add(" ");
		assertNotEquals(palavras, verification.emptyOrNull(palavras));
		
	}

	/**
	 * Test method for {@link sgs.util.Verifications#characters(java.lang.String)}.
	 */
	@SuppressWarnings("static-access")
	@Test
	void testCharacters() {
		assertEquals(true, verification.characters(""));
	}

	/**
	 * Test method for {@link sgs.util.Verifications#password(java.lang.String)}.
	 */
	@SuppressWarnings("static-access")
	@Test
	void testPassword() {
		assertEquals(true, verification.password("passWord@1"));
	}

}
