import java.lang.RuntimeException
import java.time.DayOfWeek
import java.time.LocalTime

class SupermarketChain(val supermarkets: List<Supermarket>) {

    /**
     * Method to get the top 5 best-selling products
     * @return a String with the name and quantity of each product
     */
    fun getTop5Products(): String {
        //Create a map to accumulate the quantity sold of each product in the entire chain
        val productSales = mutableMapOf<Int, Int>()

        //Iterate each supermarket and accumulate the solds
        supermarkets.forEach { supermarket ->
            supermarket.sales.forEach { (product, quantity) ->
                productSales[product.id] = productSales.getOrDefault(product.id, 0) + quantity
            }
        }

        //Get all the products
        if(supermarkets.isEmpty())
            throw RuntimeException("There is no supermarkets in the chain.")
        val productById = supermarkets.get(0).getProducts().associateBy { it.id }

        //Get the top 5 of sales of each product in the entire chain
        return productSales.entries
                .sortedByDescending { it.value }  //Order desc
                .take(5)  //Take first five
                .joinToString("-") { entry ->
                    val product = productById[entry.key]
                    "${product?.name ?: "Unknown"}: ${entry.value}"
                }
    }

    /**
     * Method to get the total revenue in the entire chain
     * @return the total revenue
     */
    fun getTotalRevenue(): Double {
        return supermarkets.sumOf { it.getTotalRevenue() }
    }

    /**
     * Method to get the supermarket with more sales revenue
     * @return the name of the supermarket and the revenue
     */
    fun getTopSupermarket(): String {
        val topSupermarket = supermarkets.maxByOrNull { it.getTotalRevenue() }
                ?: throw IllegalArgumentException("No supermarkets available.")
        return "${topSupermarket.name} (${topSupermarket.id}). Total revenue: ${topSupermarket.getTotalRevenue()}"
    }

    /**
     * Method to get the total revenue in the entire chain
     * @return the total revenue
     */
    fun getOpenSupermarkets(day: DayOfWeek, time: LocalTime): String {
        return supermarkets.filter { it.isSupermarketOpen(day, time) }
                .joinToString(", ") { "${it.name} (${it.id})" }
    }
}