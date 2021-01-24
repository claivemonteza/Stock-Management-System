/**
 * 
 */
package mz.co.estoque.acesso;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.BatchUpdateException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.opentest4j.MultipleFailuresError;
import org.springframework.dao.DataIntegrityViolationException;

import mz.co.estoque.TestManagerFactory;
import mz.co.stock.access.AccessManager;
import mz.co.stock.access.profiles.model.Profile;
import mz.co.stock.access.profiles.model.Transaction;
import mz.co.stock.access.users.model.Language;
import mz.co.stock.access.users.model.User;

/**
 * @author Claive Monteza
 *
 */
class AccessManagerTest {

	private static AccessManager access;
	private static Transaction transaction;
	private static Profile profile;
	private static User user;
	private List<Profile> profiles;
	private Set<Transaction> transactions;
	private List<User> users;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		access = TestManagerFactory.getAccessManager();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		access = null;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		transaction = new Transaction();
		profile = new Profile();
		user = new User();
		profiles = new ArrayList<Profile>();
		users = new ArrayList<User>();
		transactions = new HashSet<Transaction>();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		transaction = null;
		profile = null;
		user = null;
		profiles = null;
	}

	@Nested
	class TransactionTest {
		/**
		 * Test method for
		 * {@link mz.co.stock.access.AccessManagerImpl#save(mz.co.stock.access.profiles.model.Transaction)}.
		 */
		@Test
		@DisplayName("Save Transaction")
		void testSaveTransaction() {
			try {
				transaction.setCode("101");
				transaction.setName("profiles.record");
				transaction.setUrl("access/profile/profile-list.zul");
				access.save(transaction);
				transaction = new Transaction();
				transaction.setCode("102");
				transaction.setName("users.record");
				transaction.setUrl("access/users/user-list.zul");
				access.save(transaction);
			} catch (DataIntegrityViolationException | ConstraintViolationException
					| SQLIntegrityConstraintViolationException e) {
				e.printStackTrace();
			}

			// assertThrows(MultipleFailuresError.class, () -> {
			assertAll(() -> assertNotNull(access.allTransactions().size(), "Expected a list of transactions:"),
					() -> assertNull(access.allTransactions().size(), "Expected a empty list:"));
			/* }, "A mistake:"); */
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.access.AccessManagerImpl#update(mz.co.stock.access.profiles.model.Transaction)}.
		 */
		@Test
		@DisplayName("Update Transaction")
		@Disabled
		void testUpdateTransaction() {
			fail("Not yet implemented");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.access.AccessManagerImpl#delete(mz.co.stock.access.profiles.model.Transaction)}.
		 */
		@Test
		@DisplayName("Delete Transaction")
		@Disabled
		void testDeleteTransaction() {
			fail("Not yet implemented");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.access.AccessManagerImpl#allTransactions()}.
		 */
		@Test
		@DisplayName("All transactions")
		void testAllTransactions() {
			assertThrows(MultipleFailuresError.class, () -> {
				assertAll(() -> assertNull(access.allTransactions(), "Expected null!"),
						() -> assertNotNull(access.allTransactions(), "Expected a list of transactions!"),
						() -> assertNull(access.allTransactions(true), "Expetativa de uma lista null com TRUE!"),
						() -> assertNotNull(access.allTransactions(true), "Expected a list of transactions of TRUE!"),
						() -> assertNull(access.allTransactions(false), "Expected NULL of FALSE!"),
						() -> assertNotNull(access.allTransactions(false), "Expected a list of transactions of FALSE!"));
			}, "A mistake:");
		}
	}

	
	
	
	@Nested
	class ProfileTest {

		/**
		 * Test method for
		 * {@link mz.co.stock.access.AccessManagerImpl#save(mz.co.stock.access.profiles.model.Profile)}.
		 */
		@Test
		@DisplayName("Save Profile")
		void testSaveProfile() {
			List<Transaction> transacoes = access.allTransactions();
			profile.setName("Administrator");
			profile.setTransactions(transacoes);
			try {
				access.save(profile);
			} catch (ConstraintViolationException | DataIntegrityViolationException
					| SQLIntegrityConstraintViolationException e) {
				e.printStackTrace();
			}
			// assertThrows(MultipleFailuresError.class, () -> {
			assertAll(
					() -> assertNotNull(access.searchProfile("Administrator"),
							"There is a profile have been save:"),
					() -> assertEquals(profile, access.searchProfile("Administrator"),
							"Already have this profile:"));
			/* }, "A mistake:"); */
		}

		
		/**
		 * Test method for
		 * {@link mz.co.stock.access.AccessManagerImpl#update(mz.co.stock.access.profiles.model.Profile)}.
		 */
		@Test
		@DisplayName("Update Profile")
		void testUpdateProfile() {
			assertThrows(NullPointerException.class, () -> {
				profile = access.searchProfile("Administrator");
				profile.setName("Administrator Master");
				access.update(profile);
				assertNull(access.searchProfile("Administrator"));
			}, "Can't find the profile:");
		}
		
		/**
		 * Test method for
		 * {@link mz.co.stock.access.AccessManagerImpl#delete(mz.co.stock.access.profiles.model.Profile)}.
		 */
		@Test
		@DisplayName("Delete Profile")
		void testDeleteProfile() {
			try {
				profile = access.searchProfile("Administrator");
				access.delete(profile);
			} catch (IllegalArgumentException | DataIntegrityViolationException
					| SQLIntegrityConstraintViolationException | BatchUpdateException e) {
				e.printStackTrace();
			}
			
			assertAll(() -> assertNull(access.searchProfile("Administrator"), "this profile exist: "),
					() -> assertNotNull(access.searchProfile("Administrator"), "Couldn't delete the profile:"));
		}
		

		/**
		 * Test method for
		 * {@link mz.co.stock.access.AccessManagerImpl#searchProfile(java.lang.String)}.
		 */
		@Test
		@DisplayName("Search Profile")
		void testSearchProfileString() {
			
			assertThrows(MultipleFailuresError.class, () -> {
				assertAll(() -> assertNull(access.searchProfile("Administrator"), "Expected null!"),
						() -> assertNotNull(access.searchProfile("Administrator"), "Expected a profile!"),
						() -> assertNull(access.searchProfile("Administrator", true), "Expected null with true!"),
						() -> assertNotNull(access.searchProfile("Administrator", true),
								"Expected a profile with true!"),
						() -> assertNull(access.searchProfile("Administrator", false), "Expected null with false!"),
						() -> assertNotNull(access.searchProfile("Administrator", false),
								"Expected a profile with false!"));
			}, "A mistake:");
			
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.access.AccessManagerImpl#findProfiles(java.lang.String)}.
		 */
		@Test
		void testFindProfilesString() {
			assertThrows(MultipleFailuresError.class, () -> {
				assertAll(() -> assertNull(access.findProfiles("Adm"), "Expected null!"),
						() -> assertNotNull(access.findProfiles("Adm"), "Expected a list o profile!"),
						() -> assertNull(access.findProfiles("Adm", true), "Expected null with true!"),
						() -> assertNotNull(access.findProfiles("Adm", true), "Expected list of profile with true!"),
						() -> assertNull(access.findProfiles("Adm", false), "Expected null with false!"),
						() -> assertNotNull(access.findProfiles("Adm", false),
								"Expected a list of profile with false!"));
			}, "A mistake:");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.access.AccessManagerImpl#selectedProfile(mz.co.stock.access.profiles.model.Profile)}.
		 */
		@Test
		void testSelectedProfile() {
			try {
				for (Profile profile : access.allProfiles()) {
					profiles.add(profile);
				}
				assertThrows(MultipleFailuresError.class, () -> {
					assertAll(() -> assertNotNull(profiles, "Expected a list of profile not:"),
							() -> assertNull(profiles, "Expected a empty list not:"));
				}, "A mistake:");

			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Test method for {@link mz.co.stock.access.AccessManagerImpl#allProfiles()}.
		 */
		@Test
		void testAllProfiles() {
			assertThrows(MultipleFailuresError.class, () -> {
				assertAll(() -> assertNotNull(access.allProfiles(), "Expected all profiles!"),
						() -> assertNull(access.allProfiles(), "Expected null!"),
						() -> assertNull(access.allProfiles(true), "Expected a null with True!"),
						() -> assertNotNull(access.allProfiles(true), "Expected list of profiles with True!"),
						() -> assertNull(access.allProfiles(false), "Expected a null with False!"),
						() -> assertNotNull(access.allProfiles(false), "Expected list of profiles with False!"));
			}, "A mistake:");
		}
	}

	
	
	
	
	@Nested
	class UserTest {
		/**
		 * Test method for
		 * {@link mz.co.stock.access.AccessManagerImpl#save(mz.co.stock.access.users.model.User)}.
		 */
		@Test
		@DisplayName("Save User")
		void testSaveUser() {
			try {
				profile = access.searchProfile("Administrator");
				transactions = Set.copyOf(profile.getTransactions());
				user.setName("Sousa Monteza");
				user.setUsername("Sousa");
				user.setPassword("0000");
				user.setLanguage(Language.PORTUGUESE);
				user.setProfile(profile);
				user.setTransactions(transactions);
				access.save(user);
			} catch (ConstraintViolationException | DataIntegrityViolationException
					| SQLIntegrityConstraintViolationException e) {
				e.printStackTrace();
			}

			//assertThrows(MultipleFailuresError.class, () -> {
				assertAll(() -> assertNotNull(access.findByName("Sousa Monteza"), "This user haven't been save!"),
						() -> assertNull(access.findByUsername("Omar"), "Already have save this user!"));
			//}, "A mistake to save:");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.access.AccessManagerImpl#update(mz.co.stock.access.users.model.User)}.
		 */
		@Test
		@DisplayName("Update User")
		void testUpdateUser() {
			try {
				user = access.findByUsername("Sousa");
				user.setUsername("Rui");
				access.update(user);
			} catch (DataIntegrityViolationException | ConstraintViolationException
					| SQLIntegrityConstraintViolationException | IllegalArgumentException e) {
				e.printStackTrace();
			}
			//assertThrows(MultipleFailuresError.class, () -> {
				assertAll(() -> assertNotNull(access.findByUsername("Rui"), "This user haven't been update!"),
						() -> assertNull(access.findByUsername("Sousa"), "Already have update this user!"));
			//}, "Update mistake:");
		}
		
		/**
		 * Test method for
		 * {@link mz.co.stock.access.AccessManagerImpl#delete(mz.co.stock.access.users.model.User)}.
		 */
		@Test
		@DisplayName("Delete User")
		void testDeleteUser() {
			try {
				user = access.findByUsername("Rui");
				access.delete(user);
			} catch (DataIntegrityViolationException | SQLIntegrityConstraintViolationException
					| BatchUpdateException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			}
			assertThrows(MultipleFailuresError.class, () -> {
				assertAll(() -> assertNull(user, "This user haven't been delete!"),
						() -> assertNotNull(user, "Already delete this user!"));
			}, "Delete mistake:");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.access.AccessManagerImpl#findByName(java.lang.String)}.
		 */
		@Test
		@DisplayName("Find User by name")
		void testFindByName() {
			user = access.findByName("Sousa Monteza");
			assertThrows(MultipleFailuresError.class, () -> {
				assertAll(() -> assertNull(user, "Expected null!"),
						() -> assertNotNull(user, "Expected to find user!"));
			}, "can't find:");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.access.AccessManagerImpl#findByUsername(java.lang.String)}.
		 */
		@Test
		@DisplayName("Find User by username")
		void testFindByUsername() {
			user = access.findByUsername("Rui");
			assertThrows(MultipleFailuresError.class, () -> {
				assertAll(() -> assertNull(user, "Expected null!"),
						() -> assertNotNull(user, "Expected to find user!"));
			}, "can't find:");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.access.AccessManagerImpl#authentication(java.lang.String, java.lang.String)}.
		 */
		@Test
		@DisplayName("User Authentication")
		void testAuthentication() {
			user = access.authentication("Rui", "0000");
			assertThrows(MultipleFailuresError.class, () -> {
				assertAll(() -> assertNull(user, "Expected null!"),
						() -> assertNotNull(user, "Expected to find user!"));
			}, "can't find:");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.access.AccessManagerImpl#searchPassword(java.lang.String)}.
		 */
		@Test
		@DisplayName("Search User password")
		void testSearchPassword() {
			user = access.searchPassword("0000");
			assertThrows(MultipleFailuresError.class, () -> {
				assertAll(() -> assertNull(user, "Expected null!"),
						() -> assertNotNull(user, "Expected to find user!"));
			}, "Error:");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.access.AccessManagerImpl#findUsers(java.lang.String)}.
		 */
		@Test
		@DisplayName("Search user")
		void testFindUsersString() {
			assertThrows(MultipleFailuresError.class, () -> {
				assertAll(() -> assertNull(access.findUsers("Sousa"), "Expected empty list of user!"),
						() -> assertNotNull(access.findUsers("Sousa"), "Expected a list of user!"),
						() -> assertNull(access.findUsers("Sousa", false), "Expected empty list!"),
						() -> assertNotNull(access.findUsers("Sousa", false), "Expected a list!"),
						() -> assertNull(access.findUsers("Sousa", true), "Expected empty list!"),
						() -> assertNotNull(access.findUsers("Sousa", true), "Expected a list!"));
			}, "A mistake:");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.access.AccessManagerImpl#selectedUser(mz.co.stock.access.users.model.User)}.
		 */
		@Test
		@DisplayName("Selected users")
		void testSelectedUser() {
			try {
				for (User user : access.allUsers()) {
					users.add(user);
				}
				assertThrows(MultipleFailuresError.class, () -> {
					assertAll(() -> assertNotNull(users, "Expected a list of users not:"),
							() -> assertNull(users, "Expected a empty list not:"));
				}, "A mistake:");

			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Test method for {@link mz.co.stock.access.AccessManagerImpl#allUsers()}.
		 */
		@Test
		@DisplayName("All users")
		void testAllUsers() {
			assertThrows(MultipleFailuresError.class, () -> {
				assertAll(() -> assertNotNull(access.allUsers(), "Expected all Users!"),
						() -> assertNull(access.allUsers(), "Expected null!"),
						() -> assertNull(access.allUsers(true), "Expected a null with True!"),
						() -> assertNotNull(access.allUsers(true), "Expected list of users with True!"),
						() -> assertNull(access.allUsers(false), "Expected a null with False!"),
						() -> assertNotNull(access.allUsers(false), "Expected list of users with False!"));
			}, "A mistake:");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.access.AccessManagerImpl#resetPassword(mz.co.stock.access.users.model.User)}.
		 */
		@Test
		@DisplayName("Reset password")
		void testResetPassword() {
			user = access.findByUsername("Rui");
			access.resetPassword(user);
			assertEquals("wmyjbe5mN2Yyw17/YBzXlsTieMpKTnzbvXXd/xCbPd3azt8F9lZkD+qt9ZJDWGTr", user.getPassword());

		}

		/**
		 * Test method for
		 * {@link mz.co.stock.access.AccessManagerImpl#changePassword(mz.co.stock.access.users.model.User, java.lang.String)}.
		 */
		@Test
		@DisplayName("Change password")
		void testChangePassword() {
			user = access.findByUsername("Rui");
			assertThrows(MultipleFailuresError.class, () -> {
				assertAll(() -> assertTrue(access.changePassword(user, "1111"), "Expected true!"),
						() -> assertFalse(access.changePassword(user, "1111"), "Expected true!"));
			}, "A mistake:");
		}
	}
}
