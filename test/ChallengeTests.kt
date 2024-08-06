import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ChallengeTests {

    /**
     * Datos de prueba:
     *  # Productos [id, nombre, precio]
     *      - 1, "Carne", 10.0
     *      - 2, "Pescado", 20.0
     *      - 3, "Pollo", 30.0
     *      - 4, "Cerdo", 45.0
     *      - 5, "Ternera", 50.0
     *      - 6, "Cordero", 65.0
     *
     *  # Supermercado [id, nombre]
     *    - 1, "Supermercado A"
     *    - 2, "Supermercado B"
     *    - 3, "Supermercado C"
     * */

    internal lateinit var product1: Product
    internal lateinit var product2: Product
    internal lateinit var product3: Product
    internal lateinit var product4: Product
    internal lateinit var product5: Product
    internal lateinit var product6: Product
    internal lateinit var supermarket1: Supermarket
    internal lateinit var supermarket2: Supermarket
    internal lateinit var supermarket3: Supermarket
    internal lateinit var supermarketChain: SupermarketChain

    @BeforeEach
    fun setUp() {
        product1 = Product(1, "Carne", 10.0)
        product2 = Product(2, "Pescado", 20.0)
        product3 = Product(3, "Pollo", 30.0)
        product4 = Product(4,"Cerdo",45.0)
        product5 = Product(5,"Ternera",50.0)
        product6 = Product(6,"Cordero",65.0)
        
        val products = listOf(
                product1,
                product2,
                product3,
                product4,
                product5,
                product6
        )

        supermarket1 = Supermarket(1, "Supermercado A")
        supermarket1.addProducts(products)
        supermarket1.initializeStock(50)

        supermarket2 = Supermarket(2, "Supermercado B")
        supermarket2.addProducts(products)
        supermarket2.initializeStock(50)

        supermarket3 = Supermarket(3, "Supermercado C")
        supermarket3.addProducts(products)
        supermarket3.initializeStock(50)

        supermarketChain = SupermarketChain(listOf(
                supermarket1,
                supermarket2,
                supermarket3
        ))
    }

    @Test
    fun testRegisterSale() {
        val quantitySold = supermarket1.registerSale(product1.id, 10)
        assertEquals(100.0, quantitySold)
    }

    @Test
    fun testGetQuantitySold() {
        supermarket1.registerSale(product1.id, 10)
        val quantitySold = supermarket1.getQuantitySold(product1.id)
        assertEquals(10, quantitySold)
    }

    @Test
    fun testGetRevenueByProduct() {
        supermarket1.registerSale(product1.id, 10)
        val revenue = supermarket1.getRevenueByProduct(product1.id)
        assertEquals(100.0, revenue)
    }

    @Test
    fun testGetTotalRevenue() {
        supermarket1.registerSale(product1.id, 10)
        supermarket2.registerSale(product1.id, 5)
        val totalRevenue = supermarket1.getTotalRevenue() + supermarket2.getTotalRevenue()
        assertEquals(150.0, totalRevenue)
    }

    @Test
    fun testGetTop5Products() {
        supermarket1.registerSale(product1.id, 10)
        supermarket1.registerSale(product2.id, 20)
        supermarket2.registerSale(product1.id, 15)
        supermarket2.registerSale(product2.id, 5)
        val top5Products = supermarketChain.getTop5Products()
        assertEquals("Carne: 25-Pescado: 25", top5Products)
    }

    @Test
    fun testGetTotalRevenueChain() {
        supermarket1.registerSale(product1.id, 10)
        supermarket2.registerSale(product1.id, 5)
        val totalRevenue = supermarketChain.getTotalRevenue()
        assertEquals(150.0, totalRevenue)
    }

    @Test
    fun testGetSupermarketWithHighestRevenue() {
        supermarket1.registerSale(product1.id, 10)
        supermarket2.registerSale(product1.id, 15)
        val highestRevenueSupermarket = supermarketChain.getTopSupermarket()
        assertEquals("Supermercado B (2). Ingresos totales: 150.0", highestRevenueSupermarket)
    }

}