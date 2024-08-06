fun main(args: Array<String>) {
    /*
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
     */
    val products = listOf(
            Product(1,"Carne",10.0),
            Product(2,"Pescado",20.0),
            Product(3,"Pollo",30.0),
            Product(4,"Cerdo",45.0),
            Product(5,"Ternera",50.0),
            Product(6,"Cordero",65.0)
    )

    val supermarkets = listOf(
            Supermarket(1, "Supermercado A"),
            Supermarket(2, "Supermercado B"),
            Supermarket(3, "Supermercado C")
    )

    println("Productos:")
    products.forEach { println(it) }
    println()
    println("Supermercados:")
    supermarkets.forEach { println(it) }
}