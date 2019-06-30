package hr.vpriscan.kost

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.GsonBuilder
import org.junit.Test

class KostPrintTest {

    class Dog(val name: String,
              val breed: String,
              val age: Int? = null)

    class Car(val name: String)

    class Gun

    class Denomination(val amount: Double,
                       val currency: String)
    class Contract(
        val type: String,
        val active: Boolean,
        val denomination: Denomination,
        val history: List<Denomination>?
    )

    fun lotsOfGuns(): List<Gun> {
        return List(100) { Gun() }
    }

    fun archivedContracts(): List<Contract> {
        return emptyList()
    }

    fun listOfDogs(): List<Dog> {
        return List(3) { Dog("Bailey$it", "Beagle", it) }
    }

    val gson = GsonBuilder().setPrettyPrinting().create()

    val jackson = ObjectMapper().writerWithDefaultPrettyPrinter()

    @Test
    fun printDogs() {
        val dogsKost = Kost { // root object builder
            "dogName" - "Daisy" // simple property
            "dog" - Dog("Daisy", "Beagle", 2) // object property
            "dogBuilder" { // object property builder
                "name" - "Daisy"
                "breed" - "Beagle"
                "age" - 2
            }
            "dogs" - listOfDogs()  // array property
            "dogsBuilder"[{    // array property builder
                a - Dog("Daisy", "Beagle", 2) // array element
                a { // array element object builder
                    "name" - "Daisy"
                    "breed" - "Beagle"
                    "age" - 2
                }
                // arbitrary control flow
                for (dog in listOfDogs()) {
                    a {
                        // custom object mapping logic
                        "name" - dog.name
                        "breed" - dog.breed
                        if (dog.age != null) {
                            "age" - dog.age
                        }
                    }
                }
            }]
        }
        println(gson.toJson(dogsKost))
    }

    @Test
    fun printJon() {
        val jonKost: MutableMap<String, Any?> = Kost { // root object builder

            "name" - "Jon Wik" //property assignment
            "age" - 55
            "alive" - true
            "vehicle" - Car("Jon Wik's car")
            "wife" - null
            "dogs" - listOfDogs()
            "socialStatus" - "excommunicated"
            "equipment" { // object builder
                "guns" - lotsOfGuns()
                "suits" - 2
            }
            "aliases"[{ // array builder
                a - "Baba Yaga" // array element assignment
                a - "The Boogeyman"
            }]
            "contracts"[{

                a { // array element object builder
                    "type" - "open"
                    "denomination" {
                        "amount" - 15_000_000.0
                        "currency" - "USD"
                        "history"[{
                            a - 7_000_000
                            a - 14_000_000
                        }]
                    }
                    "active" - true
                }

                // arbitrary control flow
                for (contract in archivedContracts()) {
                    if (contract.active) {
                        a - contract
                    } else {
                        a {
                            // custom object mapping logic
                            "type" - contract.type
                            "denomination" - contract.denomination
                            if (!contract.history.isNullOrEmpty()) {
                                "history" - contract.history
                            }
                            "active" - false
                        }
                    }
                }
            }]
        }
        println(jackson.writeValueAsString(jonKost))
    }

    @Test
    fun printMatrix() {
        val matrixKost = Kost[{
            for (i in 0..5) {
                a[{
                    for (j in 0..5) {
                        a - (i + j)
                    }
                }]
            }
        }]
        println(gson.toJson(matrixKost))
    }
}