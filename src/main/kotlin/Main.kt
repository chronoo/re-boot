fun main() {
    val maps: List<Map<String, Dto>> = listOf(
        mapOf(
            "1" to Dto(setOf(1,2,3)),
            "2" to Dto(setOf(4,5)),
        ),
        mapOf(
            "1" to Dto(setOf(6))
        )
    )
    println(
        getResult(maps)
    )
}

private fun getResult(maps: List<Map<String, Dto>>): Map<String, Dto> {
    val flatten: List<Map.Entry<String, Dto>> = maps.flatMap { it.entries }
    val res = flatten
        .groupBy({ it.key }) { it.value }
    return res.mapValues {
        it.value.reduce { acc, dto -> reduceDto(acc, dto) }
    }
}

private fun reduceDto(acc: Dto, dto: Dto) = acc.copy(items = acc.items + dto.items)

data class Dto(val items: Set<Int> = setOf())