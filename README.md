```kotlin
class Foo(val bar: String)

val kost: Map<String, Any?> = Kost {
    "string" - "hello"
    "number" - 6
    "boolean" - true
    "null" - null
    "objDsl" {
        "number" - 5.0
        "obj" - Foo("bar")
        "objDsl" {
            "string" - "earth"
            "array" - arrayOf(2, 3, 4, 5)
        }
        "string" - "world"
    }
    "array" - arrayOf(2, 3, 4, "five")
    "arrayDsl"[{
        _a - "first"
        _a - 2
        _a {
            "string" - "third"
            "boolean" - true
            "arrayDsl"[{
                _a - "primus"
                _a - 2.0
            }]
        }
    }]
}
```