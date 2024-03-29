
[![](https://jitpack.io/v/vpriscan/Kost.svg)](https://jitpack.io/#vpriscan/Kost)


#### Example
Kost DSL example:
```kotlin
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
```

Kost builder uses maps and lists to build the data structure. 
Resulting root object is just a `Map<String, Any?>`.

Serialize to eg. JSON with your favourite serialization library:
```kotlin
println(gson.toJson(dogsKost))
println(jackson.writeValueAsString(dogsKost))
```

Resulting JSON example:
```json
{
  "dogName": "Daisy",
  "dog": {
    "name": "Daisy",
    "breed": "Beagle",
    "age": 2
  },
  "dogBuilder": {
    "name": "Daisy",
    "breed": "Beagle",
    "age": 2
  },
  "dogs": [
    {
      "name": "Bailey0",
      "breed": "Beagle",
      "age": 0
    },
    {
      "name": "Bailey1",
      "breed": "Beagle",
      "age": 1
    },
    {
      "name": "Bailey2",
      "breed": "Beagle",
      "age": 2
    }
  ],
  "dogsBuilder": [
    {
      "name": "Daisy",
      "breed": "Beagle",
      "age": 2
    },
    {
      "name": "Daisy",
      "breed": "Beagle",
      "age": 2
    },
    {
      "name": "Bailey0",
      "breed": "Beagle",
      "age": 0
    },
    {
      "name": "Bailey1",
      "breed": "Beagle",
      "age": 1
    },
    {
      "name": "Bailey2",
      "breed": "Beagle",
      "age": 2
    }
  ]
}
```

#### Add dependency
Kost is available on Jitpack.

##### Gradle

Add Jitpack repository:
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
```

Add Kost dependency:
```groovy
implementation("com.github.vpriscan:Kost:1.0.0")
```

##### Maven

Add Jitpack repository:
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

Add Kost dependency:
```xml
<dependency>
    <groupId>com.github.vpriscan</groupId>
    <artifactId>Kost</artifactId>
    <version>1.0.0</version>
</dependency>
```