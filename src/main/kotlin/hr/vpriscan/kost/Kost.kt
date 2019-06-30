@file:Suppress("SpellCheckingInspection")

package hr.vpriscan.kost

@DslMarker
annotation class KostMarker

object Kost {

    operator fun get(builder: KostArray.() -> Unit): MutableList<Any?> {
        return KostArray()
            .apply(builder)
    }

    operator fun invoke(builder: KostObject.() -> Unit): MutableMap<String, Any?> {
        return KostObject()
            .apply(builder)
    }
}

@KostMarker
class KostArray : ArrayList<Any?>() {

    object ElementHook

    @Suppress("PropertyName")
    val a get() = ElementHook

    operator fun ElementHook.minus(value: Any?) {
        this@KostArray.add(value)
    }

    operator fun ElementHook.invoke(builder: KostObject.() -> Unit) {
        KostObject()
            .apply(builder)
            .let { this@KostArray.add(it) }
    }

    operator fun ElementHook.get(builder: KostArray.() -> Unit) {
        KostArray()
            .apply(builder)
            .let { this@KostArray.add(it) }
    }
}

@KostMarker
class KostObject : LinkedHashMap<String, Any?>() {

    operator fun String.minus(value: Any?) {
        this@KostObject[this@minus] = value
    }

    operator fun String.invoke(builder: KostObject.() -> Unit) {
        KostObject()
            .apply(builder)
            .let { this@KostObject[this@invoke] = it }
    }

    operator fun String.get(builder: KostArray.() -> Unit) {
        KostArray()
            .apply(builder)
            .let { this@KostObject[this@get] = it }
    }
}