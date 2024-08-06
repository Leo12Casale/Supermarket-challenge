import java.time.LocalTime;
import java.time.DayOfWeek;

class Supermarket(
        val id: Int,
        val name: String

) {
    //List of products shared by all the supermarkets
    companion object {
        val products: MutableList<Product> = mutableListOf()
    }

    internal val sales: MutableMap<Product, Int> = mutableMapOf()  //Product and quantity sold
    private val revenue: MutableMap<Product, Double> = mutableMapOf()  //Product and total revenue
    private val productsIdStock: MutableMap<Product, Int> = products.associateWith { 0 }.toMutableMap()
    internal val openHours: Pair<LocalTime, LocalTime>? = null  //Opening and closing times
    internal val openDays: List<DayOfWeek>? = null  //Days when the supermarket is open

    // Add a method to initialize stock for each product
    fun initializeStock(quantity: Int) {
        for (product in products) {
            productsIdStock[product] = quantity
        }
    }

    fun addProduct(product: Product) {
        if (products.none { it.id == product.id })
            products.add(product)
    }

    fun addProducts(listOfProducts: List<Product>) {
        listOfProducts.forEach { product ->
            products.add(product)
        }
    }

    /**
     * Method to register a sale.
     * @property productId the ID of the product to sale.
     * @property quantity the quantity to sale.
     * @return total sale price.
     */
    fun registerSale(productId: Int, quantity: Int): Double {
        //The product exists?
        val product = getProductById(productId)
        //Is there enough stock of that product in this supermarket?
        val currentStock = productsIdStock[product] ?: 0
        if (currentStock < quantity) throw IllegalArgumentException("Insufficient stock for product ID $productId.")

        //Update stock, sales amount and revenue of the supermarket
        productsIdStock[product] = currentStock - quantity
        sales[product] = sales.getOrDefault(product, 0) + quantity
        revenue[product] = revenue.getOrDefault(product, 0.0) + product.price * quantity

        return product.price * quantity
    }

    /**
     * Method to return the quantity sold of a particular product
     * @property productId the ID of the product sold.
     * @return quantity sold.
     */
    fun getQuantitySold(productId: Int): Int {
        val product = getProductById(productId)
        return sales[product] ?: 0
    }

    /**
     * Method to return revenue from sales of a given product
     * @property productId the ID of the product sold.
     * @return amount of revenue.
     */
    fun getRevenueByProduct(productId: Int): Double {
        val product = getProductById(productId)
        return revenue[product] ?: 0.0
    }

    /**
     * Method to return total revenue from sales
     * @return amount of total revenue.
     */
    fun getTotalRevenue(): Double {
        return revenue.values.sum()
    }

    /**
     * Method to determinate if a supermarket is open.
     * @property day day of the week
     * @property time time of the day
     * @return false if it's closed or true if it's open.
     */
    fun isSupermarketOpen(day: DayOfWeek, time: LocalTime): Boolean {
        return openDays?.contains(day) == true && openHours?.let { (open, close) ->
            time >= open && time <= close
        } == true
    }

    /**
     * Method to get a given product by its id.
     * @property productId id of the product.
     * @return the product
     * @throws IllegalArgumentException if the product is not found
     */
    fun getProductById(productId: Int) : Product {
        return products.find { it.id == productId }
                ?: throw IllegalArgumentException("Product with ID $productId not found.")
    }

    fun getProducts() : MutableList<Product> {
        return products;
    }
}