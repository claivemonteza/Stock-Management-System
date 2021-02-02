package mz.co.stock.items;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.BatchUpdateException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
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

import mz.co.stock.TestManagerFactory;
import mz.co.stock.items.batches.model.Batch;
import mz.co.stock.items.products.model.Product;
import mz.co.stock.util.DateUtil;

/**
 * @author Claive Monteza
 *
 */
@DisplayName("Items test")
public class ItemManagerTest {

	private static ItemManager itemManager;
	private static Batch batch;
	private static Product product;
	private static List<Product> products;
	private static List<Batch> batches;
	
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
		products = new ArrayList<Product>();
		batches = new ArrayList<Batch>();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		batch = null;
		product = null;
		products = null;
		batches = null;
	}

	@Nested
	@DisplayName("Product Test")
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

			assertThrows(MultipleFailuresError.class, () -> {
				assertAll(() -> assertNotNull(itemManager.allProducts().size(), "Expected a list of products:"),
						() -> assertNull(itemManager.allProducts().size(), "Expected a empty list:"));
			}, "A mistake:");

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
		 * Test method for
		 * {@link mz.co.stock.items.ItemManagerImpl#findByName(java.lang.String)}.
		 */
		@Test
		@DisplayName("Find product by name")
		void testFindByNameString() {
			assertThrows(MultipleFailuresError.class, () -> {
				assertAll(
						() -> assertNotNull(itemManager.findByName("Bacterial Filtration Efficiency"),
								"Expected a product!"),
						() -> assertNull(itemManager.findByName("Bacterial Filtration Efficiency"), "Expected null!"));
			}, "A mistake:");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.items.ItemManagerImpl#findByCode(java.lang.String)}.
		 */
		@Test
		@DisplayName("Find product by code")
		void testFindByCodeString() {
			assertThrows(MultipleFailuresError.class, () -> {
				assertAll(() -> assertNotNull(itemManager.findByCode("697088540826"), "Expected a product!"),
						() -> assertNull(itemManager.findByCode("697088540826"), "Expected null!"));
			}, "A mistake:");
		}

		/**
		 * Test method for {@link mz.co.stock.items.ItemManagerImpl#allCategories()}.
		 */
		@Test
		@DisplayName("All categories")
		void testAllCategories() {
			List<String> categories = itemManager.allCategories();
			assertThrows(MultipleFailuresError.class, () -> {
				assertAll(() -> assertNotNull(categories, "Expected list of categories:"),
						() -> assertNull(categories, "Expected null:"));
			}, "A mistake:");
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
		 * {@link mz.co.stock.items.ItemManagerImpl#calculateAmount(mz.co.stock.items.products.model.Product)}.
		 */
		@Test
		@DisplayName("Calculate amount of product")
		void testCalculateAmount() {
			product = itemManager.findByCode("697088540826");
			int quantity = itemManager.calculateAmount(product);
			assertThrows(MultipleFailuresError.class, () -> {
				assertAll(() -> assertEquals(0, quantity, "Expected to be true!"),
						() -> assertNotEquals(0, quantity, "Expected to be false!"));
			}, "A mistake:");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.items.ItemManagerImpl#selectedProduct(mz.co.stock.items.products.model.Product)}.
		 */
		@Test
		@DisplayName("Show selected product")
		void testSelectedProduct() {
			product = itemManager.findByCode("697088540826");
			products = itemManager.selectedProduct(product);
			assertThrows(MultipleFailuresError.class, () -> {
				assertAll(() -> assertNotNull(products, "Expected all products!"),
						() -> assertNull(products, "Expected null!"));
			}, "A mistake:");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.items.ItemManagerImpl#find(java.lang.String)}.
		 */
		@Test
		@DisplayName("Find product")
		void testFindString() {
			products = itemManager.find("Filtration");
			products = itemManager.find("Efficiency", true);
			assertThrows(MultipleFailuresError.class, () -> {
				assertAll(() -> assertNotNull(products, "Expected all products!"),
						() -> assertNull(products, "Expected null!"));
			}, "A mistake:");
		}
	}

	@Nested
	@DisplayName("Batch Test")
	class BatchTest {
		/**
		 * Test method for
		 * {@link mz.co.stock.items.ItemManagerImpl#save(mz.co.stock.items.batches.model.Batch)}.
		 */
		@Test
		@DisplayName("Save Batch")
		void testSaveBatch() {
			try {
				product = itemManager.findByCode("697088540826");
				batch.setCode("202000601");
				batch.setDateManufacturing(DateUtil.parse("01-06-2020"));
				batch.setExpirationDate(DateUtil.parse("31-05-2022"));
				batch.setCostPrice(300);
				batch.setSalePrice(500);
				batch.setAmount(300);
				batch.setProduct(product);
				itemManager.save(batch);
			} catch (DataIntegrityViolationException | ConstraintViolationException
					| SQLIntegrityConstraintViolationException e) {
				e.printStackTrace();
			}
			assertThrows(MultipleFailuresError.class, () -> {
				assertAll(() -> assertNotNull(itemManager.allBatches().size(), "Expected a list of Batch:"),
						() -> assertNull(itemManager.allBatches().size(), "Expected a empty list:"));
			}, "A mistake:");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.items.ItemManagerImpl#update(mz.co.stock.items.batches.model.Batch)}.
		 */
		@Test
		@DisplayName("Update Batch")
		void testUpdateBatch() {
			try {
				batch.setCode("20200601");
				batch.setDateManufacturing(DateUtil.parse("01-06-2020"));
				batch.setExpirationDate(DateUtil.parse("31-05-2022"));
				batch.setCostPrice(350);
				batch.setSalePrice(600);
				batch.setAmount(250);
				itemManager.update(batch);
			} catch (DataIntegrityViolationException | ConstraintViolationException
					| SQLIntegrityConstraintViolationException e) {
				e.printStackTrace();
			}

			assertThrows(MultipleFailuresError.class, () -> {
				assertAll(() -> assertNull(itemManager.findByBatchCode("20200601"), "Erro! Expected to find a Batch!"),
						() -> assertNotNull(itemManager.findByBatchCode("20200601"), "Expected to find a Batch!"),
						() -> assertNull(itemManager.findByBatchCode("202000601"), "Expected null!"),
						() -> assertNotNull(itemManager.findByBatchCode("202000601"), "Erro! Expected null!"));
			}, "A mistake:");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.items.ItemManagerImpl#delete(mz.co.stock.items.batches.model.Batch)}.
		 */
		@Test
		@DisplayName("Delete Batch")
		void testDeleteBatch() {

			try {
				batch = itemManager.findByBatchCode("20200601");
				itemManager.delete(batch);
			} catch (DataIntegrityViolationException | SQLIntegrityConstraintViolationException
					| IllegalArgumentException | BatchUpdateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			assertThrows(MultipleFailuresError.class, () -> {
				assertAll(() -> assertNull(itemManager.findByBatchCode("20200601"), "Expected null!"),
						() -> assertNotNull(itemManager.findByBatchCode("20200601"), "Expected to be deleted!"),
						() -> assertNull(itemManager.findByBatchCode("202000601"), "Expected null!"),
						() -> assertNotNull(itemManager.findByBatchCode("202000601"), "Erro! Expected null!"));
			}, "A mistake:");
		}

		/**
		 * Test method for {@link mz.co.stock.items.ItemManagerImpl#allBatches()}.
		 */
		@Test
		@DisplayName("All batches")
		void testAllBatches() {
			assertThrows(MultipleFailuresError.class, () -> {
				assertAll(() -> assertNull(itemManager.allBatches(), "Expected null!"),
						() -> assertNotNull(itemManager.allBatches(), "Expected list of batches!"),
						() -> assertNull(itemManager.allBatches(true), "Expected null with TRUE!"),
						() -> assertNotNull(itemManager.allBatches(true), "Expected list of batches with TRUE!"),
						() -> assertNull(itemManager.allBatches(false), "Expected null with FALSE!"),
						() -> assertNotNull(itemManager.allBatches(false), "Expected list of batches with FALSE!!"));
			}, "A mistake:");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.items.ItemManagerImpl#allBatches(mz.co.stock.items.products.model.Product)}.
		 */
		@Test
		@DisplayName("All batches of product")
		void testAllBatchesProduct() {
			product = itemManager.findByCode("697088540826");

			assertThrows(MultipleFailuresError.class, () -> {
				assertAll(() -> assertNull(itemManager.allBatches(product), "Expected null!"),
						() -> assertNotNull(itemManager.allBatches(product), "Expected list of batches!"),
						() -> assertNull(itemManager.allBatches(product, true), "Expected null with TRUE!"),
						() -> assertNotNull(itemManager.allBatches(product, true),
								"Expected list of batches with TRUE!"),
						() -> assertNull(itemManager.allBatches(product, false), "Expected null with FALSE!"),
						() -> assertNotNull(itemManager.allBatches(product, false),
								"Expected list of batches with FALSE!!"));
			}, "A mistake:");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.items.ItemManagerImpl#findByBatchCode(java.lang.String)}.
		 */
		@Test
		@DisplayName("Find batch by code")
		void testFindByBatchCodeString() {
			assertThrows(MultipleFailuresError.class, () -> {
				assertAll(() -> assertNull(itemManager.findByBatchCode("20200601"), "Expected null!"),
						() -> assertNotNull(itemManager.findByBatchCode("20200601"), "Expected to be deleted!"),
						() -> assertNull(itemManager.findByBatchCode("202000601"), "Expected null!"),
						() -> assertNotNull(itemManager.findByBatchCode("202000601"), "Erro! Expected null!"));
			}, "A mistake:");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.items.ItemManagerImpl#outOfDateBatches(java.util.Date)}.
		 */
		@Test
		@DisplayName("Find all batches out of date")
		void testOutOfDateBatches() {
			batches = itemManager.outOfDateBatches(new Date());
			assertThrows(MultipleFailuresError.class, () -> {
				assertAll(() -> assertNull(batches, "Expected null!"),
						() -> assertNotNull(batches, "Expected list of bacthes!"));
			}, "A mistake:");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.items.ItemManagerImpl#findBatch(java.lang.String)}.
		 */
		@Test
		@DisplayName("Find batches with a code")
		void testFindBatchString() {
			batches = itemManager.findBatch("2020");
			assertThrows(MultipleFailuresError.class, () -> {
				assertAll(() -> assertNull(batches, "Expected null!"),
						() -> assertNotNull(batches, "Expected list of bacthes!"));
			}, "A mistake:");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.items.ItemManagerImpl#selectedBatch(mz.co.stock.items.batches.model.Batch)}.
		 */
		@Test
		@DisplayName("Selected list of batches")
		void testSelectedBatch() {
			batch = itemManager.findByBatchCode("20200601");
			batches.add(batch);
			assertThrows(MultipleFailuresError.class, () -> {
				assertAll(() -> assertNull(batches, "Expected null!"),
						() -> assertNotNull(batches, "Expected list of bacthes!"));
			}, "A mistake:");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.items.ItemManagerImpl#calculateAmountBatch(mz.co.stock.items.products.model.Product)}.
		 */
		@Test
		@DisplayName("Calculate amount of batch")
		void testCalculateAmountBatch() {
			product = itemManager.findByCode("697088540826");
			int amount = itemManager.calculateAmountBatch(product);
			int amount2 = itemManager.calculateAmountOfBatchActive(product,true);
			int amount3 =itemManager.calculateAmountOfBatchActive(product,false);
			
			assertThrows(MultipleFailuresError.class, () -> {
				assertAll(() -> assertTrue(0<amount, "Expected to be true!"),
						() -> assertTrue(0==amount, "Expected to be equal!"),
						() -> assertFalse(0>amount, "Expected to be false!"),
						() -> assertTrue(0<amount2, "Expected to be true!"),
						() -> assertTrue(0==amount2, "Expected to be equal!"),
						() -> assertFalse(0>amount2, "Expected to be false!"),
						() -> assertTrue(0<amount3, "Expected to be true!"),
						() -> assertTrue(0==amount3, "Expected to be equal!"),
						() -> assertFalse(0>amount3, "Expected to be false!")
						);
			}, "A mistake:");
			
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.items.ItemManagerImpl#calculateCost(mz.co.stock.items.products.model.Product)}.
		 */
		@Test
		@DisplayName("Calculate Cost")
		void testCalculateCost() {
			product = itemManager.findByCode("697088540826");
			double cost = itemManager.calculateCost(product);
			double cost2 = itemManager. calculateCostOfBatchActive(product,true);
			double cost3 = itemManager. calculateCostOfBatchActive(product,false);
			
			assertThrows(MultipleFailuresError.class, () -> {
				assertAll(() -> assertTrue(0<cost, "Expected to be true!"),
						() -> assertTrue(0==cost, "Expected to be equal!"),
						() -> assertFalse(0>cost, "Expected to be false!"),
						() -> assertTrue(0<cost2, "Expected to be true!"),
						() -> assertTrue(0==cost2, "Expected to be equal!"),
						() -> assertFalse(0>cost2, "Expected to be false!"),
						() -> assertTrue(0<cost3, "Expected to be true!"),
						() -> assertTrue(0==cost3, "Expected to be equal!"),
						() -> assertFalse(0>cost3, "Expected to be false!")
						);
			}, "A mistake:");
		}

		/**
		 * Test method for
		 * {@link mz.co.stock.items.ItemManagerImpl#calculateSale(mz.co.stock.items.products.model.Product)}.
		 */
		@Test
		@DisplayName("Calculate sale")
		void testCalculateSale() {
			product = itemManager.findByCode("697088540826");
			double sale = itemManager.calculateSale(product);
			double sale2 = itemManager. calculateSaleOfBatchActive(product,true);
			double sale3 = itemManager. calculateSaleOfBatchActive(product,false);
			
			assertThrows(MultipleFailuresError.class, () -> {
				assertAll(() -> assertTrue(0<sale, "Expected to be true!"),
						() -> assertTrue(0==sale, "Expected to be equal!"),
						() -> assertFalse(0>sale, "Expected to be false!"),
						() -> assertTrue(0<sale2, "Expected to be true!"),
						() -> assertTrue(0==sale2, "Expected to be equal!"),
						() -> assertFalse(0>sale2, "Expected to be false!"),
						() -> assertTrue(0<sale3, "Expected to be true!"),
						() -> assertTrue(0==sale3, "Expected to be equal!"),
						() -> assertFalse(0>sale3, "Expected to be false!")
						);
			}, "A mistake:");
		}
	}
}
