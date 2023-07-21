fun main() {
    val maps: List<Map<String, Dto>> = listOf(
        mapOf(
            "1" to Dto(setOf(1, 2, 3)),
            "2" to Dto(setOf(4, 5)),
        ),
        mapOf(
            "1" to Dto(setOf(6)),
            "3" to Dto(setOf(7,8))
        )
    )
    println(
        maps.merge { acc, value ->
            acc.copy(items = acc.items + value.items)
        }
    )
}

data class Dto(
    val items: Set<Int> = setOf()
)

private inline fun <K, V> List<Map<K, V>>.merge(reducer: (acc: V, value: V) -> V): Map<K, V> =
    flatMap { map -> map.entries }
        .groupBy({ entry -> entry.key }) { entry -> entry.value }
        .mapValues { entry -> entry.value.reduce(reducer) }

