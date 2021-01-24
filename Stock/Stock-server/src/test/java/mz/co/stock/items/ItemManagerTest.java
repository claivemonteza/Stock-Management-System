package mz.co.stock.items;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.BatchUpdateException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.opentest4j.MultipleFailuresError;
import org.springframework.dao.DataIntegrityViolationException;

import mz.co.estoque.TestManagerFactory;
import mz.co.stock.items.batches.model.Batch;
import mz.co.stock.items.products.model.Product;

/**
 * @author Claive Monteza
 *
 */
class ItemManagerTest {

	private static ItemManager itemManager;
	private static Batch batch;
	private static Product product;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		itemManager = TestManagerFactory.getItemManager();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		itemManager = null;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		batch = new Batch();
		product = new Product();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		batch = null;
		product = null;
	}

	@Nested
	class ProductTest {
		/**
		 * Test method for
		 * {@link mz.co.stock.items.ItemManagerImpl#save(mz.co.stock.items.products.model.Product)}.
		 */
		@Test
		@DisplayName("Save Product")
		void testSaveProduct() {
			try {
				product.setCode("697088540826");
				product.setName("Bacterial Filtration Efficiency");
				product.setVat(0.17);
				product.setFreeVat(false);
				product.setCategory("MD");
				product.setBrand("KUNKKA");
				product.setAmount(50);
				product.setAmountMin(1);
				product.setAmountMax(1000);
				itemManager.save(product);
			} catch (DataIntegrityViolationException | ConstraintViolationException
					| SQLIntegrityConstraintViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// assertThrows(MultipleFailuresError.class, () -> {
			assertAll(() -> assertNotNull(itemManager.allProducts().size(), "Expected a list of products:"),
					() -> assertNull(itemManager.allProducts().size(), "Expected a empty list:"));
			/* }, "A mistake:"); */

		}

		/**
		 * Test method for
		 * {@link mz.co.stock.items.ItemManagerImpl#update(mz.co.stock.items.products.model.Product)}.
		 */
		@Test
		@DisplayName("Update Product")
		void testUpdateProduct() {
			try {
				product = itemManager.findByName("Bacterial Filtration Efficiency");
				product.setName("Filtration Efficiency");
				itemManager.update(product);
			} catch (DataIntegrityViolationException | ConstraintViolationException
					| SQLIntegrityConstraintViolationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// assertThrows(MultipleFailuresError.class, () -> {
			assertAll(() -> assertNotNull(itemManager.findByName("Filtration Efficiency"), "Expected a product:"),
					() -> assertNull(itemManager.findByName("Bacterial Filtration Efficiency"), "Expected null:"));
			/* }, "A mistake:"); */

		}

		/**
		 * Test method for
		 * {@link mz.co.stock.items.ItemManagerImpl#delete(mz.co.stock.items.products.model.Product)}.
		 */
		@Test
		@DisplayName("Delete Product")
		void testDeleteProduct() {
			try {
				product = itemManager.findByName("Filtration Efficiency");
				itemManager.delete(product);
			} catch (DataIntegrityViolationException | SQLIntegrityConstraintViolationException
					| IllegalArgumentException | BatchUpdateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// assertThrows(MultipleFailuresError.class, () -> {
			assertNull(itemManager.findByName("Filtration Efficiency"), "Expected to product be delete:");
			/* }, "A mistake:"); */
		}

		/**
		 * Test method for {@link mz.co.stock.items.ItemManagerImpl#allCategories()}.
		 */
		@Test
		@DisplayName("All categories")
		void testAllCategories() {
			List<String> categories = itemManager.allCategories();
			// assertThrows(MultipleFailuresError.class, () -> {
			assertAll(() -> assertNotNull(categories, "Expected a product:"),
					() -> assertNull(categories, "Expected null:"));
			/* }, "A mistake:"); */
		}

		
		
		/**
		 * Test method for {@link mz.co.stock.items.ItemManagerImpl#allProducts()}.
		 */
		@Test
		@DisplayName("All products")
		void testAllProducts() {
			assertThrows(MultipleFailuresError.class, () -> {
			assertAll(() -> assertNotNull(itemManager.allProducts(), "Expected all products!"),
						() -> assertNull(itemManager.allProducts(), "Expected null!"),
						() -> assertNull(itemManager.allProducts(true), "Expected a null with True!"),
						() -> assertNotNull(itemManager.allProducts(true), "Expected list of products with True!"),
						() -> assertNull(itemManager.allProducts(false), "Expected a null with False!"),
						() -> assertNotNull(itemManager.allProducts(false), "Expected list of products with False!"));
			}, "A mistake:");
		}
		
		
		/**
		 * Test method for
		 * {@link mz.co.stock.items.ItemManagerImpl#findByName(java.lang.String)}.
		 */
		@Test
		void testFindByNameString() {
			fail("Not yet implemented");
		}
		

		/**
		 * Test method for
		 * {@link mz.co.stock.items.ItemManagerImpl#allBatches(mz.co.stock.items.products.model.Product)}.
		 */
		@Test
		void testAllBatchesProduct() {
			fail("Not yet implemented");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.items.ItemManagerImpl#findByCode(java.lang.String)}.
		 */
		@Test
		void testFindByCodeString() {
			fail("Not yet implemented");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.items.ItemManagerImpl#calculateAmount(mz.co.stock.items.products.model.Product)}.
		 */
		@Test
		void testCalculateAmount() {
			fail("Not yet implemented");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.items.ItemManagerImpl#selectedProduct(mz.co.stock.items.products.model.Product)}.
		 */
		@Test
		void testSelectedProduct() {
			fail("Not yet implemented");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.items.ItemManagerImpl#find(java.lang.String)}.
		 */
		@Test
		void testFindString() {
			fail("Not yet implemented");
		}
	}

	@Nested
	class BatchTest {
		/**
		 * Test method for
		 * {@link mz.co.stock.items.ItemManagerImpl#save(mz.co.stock.items.batches.model.Batch)}.
		 */
		@Test
		@DisplayName("Save Batch")
		void testSaveBatch() {
			try {
				batch.setCode("");
				batch.setDateManufacturing(null);
				batch.setExpirationDate(null);
				batch.setCostPrice(0);
				batch.setSalePrice(0);
				batch.setAmount(0);
				batch.setProduct(product);
				itemManager.save(batch);
			} catch (DataIntegrityViolationException | ConstraintViolationException
					| SQLIntegrityConstraintViolationException e) {
				e.printStackTrace();
			}

			// assertThrows(MultipleFailuresError.class, () -> {
			assertAll(() -> assertNotNull(itemManager.allBatches().size(), "Expected a list of Batch:"),
					() -> assertNull(itemManager.allBatches().size(), "Expected a empty list:"));
			/* }, "A mistake:"); */
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.items.ItemManagerImpl#update(mz.co.stock.items.batches.model.Batch)}.
		 */
		@Test
		@DisplayName("Update Batch")
		void testUpdateBatch() {
			fail("Not yet implemented");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.items.ItemManagerImpl#delete(mz.co.stock.items.batches.model.Batch)}.
		 */
		@Test
		void testDeleteBatch() {
			fail("Not yet implemented");
		}

		/**
		 * Test method for {@link mz.co.stock.items.ItemManagerImpl#allBatches()}.
		 */
		@Test
		void testAllBatches() {
			fail("Not yet implemented");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.items.ItemManagerImpl#findByBatchCode(java.lang.String)}.
		 */
		@Test
		void testFindByBatchCodeString() {
			fail("Not yet implemented");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.items.ItemManagerImpl#outOfDateBatches(java.util.Date)}.
		 */
		@Test
		void testOutOfDateBatches() {
			fail("Not yet implemented");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.items.ItemManagerImpl#findBatch(java.lang.String)}.
		 */
		@Test
		void testFindBatchString() {
			fail("Not yet implemented");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.items.ItemManagerImpl#selectedBatch(mz.co.stock.items.batches.model.Batch)}.
		 */
		@Test
		void testSelectedBatch() {
			fail("Not yet implemented");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.items.ItemManagerImpl#calculateAmountBatch(mz.co.stock.items.products.model.Product)}.
		 */
		@Test
		void testCalculateAmountBatch() {
			fail("Not yet implemented");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.items.ItemManagerImpl#calculateCost(mz.co.stock.items.products.model.Product)}.
		 */
		@Test
		void testCalculateCost() {
			fail("Not yet implemented");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.items.ItemManagerImpl#calculateSale(mz.co.stock.items.products.model.Product)}.
		 */
		@Test
		void testCalculateSale() {
			fail("Not yet implemented");
		}
	}
}
