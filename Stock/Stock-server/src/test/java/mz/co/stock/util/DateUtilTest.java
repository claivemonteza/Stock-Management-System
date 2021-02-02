package mz.co.stock.util;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.opentest4j.MultipleFailuresError;

/**
 * @author Claive Monteza
 *
 */
@DisplayName("Date Format Test")
public class DateUtilTest {
	
	private static DateUtil dateUtil;
	private static Date date;
	
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
		date = new Date();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		date = null;
		
	}

	/**
	 * Test method for {@link mz.co.stock.util.DateUtil#parse()}.
	 */
	@Test
	@Order(1)
	@DisplayName("Show format dd-MM-yyyy")
	void testParse() {
		assertThrows(MultipleFailuresError.class, () -> {
		assertAll(() -> assertEquals(date, dateUtil.parse(),"Expeted to be equals!"),
				() -> assertNotEquals(date, dateUtil.parse(),"Expeted to be equals!"));
		}, "A mistake:"); 
		
	}

	/**
	 * Test method for {@link mz.co.stock.util.DateUtil#parse(java.util.Date)}.
	 */
	@Test
	@Order(2)
	@DisplayName("Convert to a format dd-MM-yyyy")
	void testParseDate() {
		assertThrows(MultipleFailuresError.class, () -> {
			assertAll(() -> assertEquals(date, dateUtil.parse(new Date()),"Expeted to be equals!"),
					() -> assertNotEquals(date, dateUtil.parse(new Date()),"Expeted to be equals!"));
			}, "A mistake:"); 
	}

	/**
	 * Test method for {@link mz.co.stock.util.DateUtil#parse(java.lang.String)}.
	 */
	@Test
	@Order(3)
	@DisplayName("Convert a string to date")
	void testParseString() {
		assertThrows(MultipleFailuresError.class, () -> {
			assertAll(() -> assertEquals("22-01-2021", dateUtil.parse("22/01/2021"),"Expeted to be equals!"),
					() -> assertNotEquals("22-01-2021", dateUtil.parse("22/01/2021"),"Expeted to be equals!"));
			}, "A mistake:"); 
	}

	/**
	 * Test method for {@link mz.co.stock.util.DateUtil#calendaryConvert(int)}.
	 */
	@Test
	@Order(4)
	@DisplayName("Convert to number of days")
	void testCalendaryConvert() {
		assertThrows(MultipleFailuresError.class, () -> {
			assertAll(() -> assertEquals("31-01-2021", dateUtil.calendaryConvert(7),"Expeted to be equals!"),
					() -> assertNotEquals("31-01-2021", dateUtil.calendaryConvert(7),"Expeted to be equals!"));
			}, "A mistake:"); 
	}

	/**
	 * Test method for {@link mz.co.stock.util.DateUtil#getYear()}.
	 */
	@Test
	@Order(5)
	@DisplayName("Show Year")
	void testGetYear() {
		assertThrows(MultipleFailuresError.class, () -> {
			assertAll(() -> assertEquals("2021", dateUtil.getYear(),"Expeted to be equals!"),
					() -> assertNotEquals("2021", dateUtil.getYear(),"Expeted to be equals!"));
			}, "A mistake:"); 
	}

	/**
	 * Test method for {@link mz.co.stock.util.DateUtil#getTime()}.
	 */
	@Test
	@Order(6)
	@DisplayName("Show time")
	void testGetTime() {
		assertThrows(MultipleFailuresError.class, () -> {
		assertAll(() -> assertEquals("Mon Jan 25 16:47:30 SAST 2021", dateUtil.getTime(),"Expeted to be equals!"),
				() -> assertNotEquals("Mon Jan 25 16:47:30 SAST 2021", dateUtil.getTime(),"Expeted to be equals!"));
		}, "A mistake:"); 
	}

}
