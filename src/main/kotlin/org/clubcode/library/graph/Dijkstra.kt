package org.clubcode.library.graph

import java.util.*

fun <T> DirectedWeightedGraph<T, Int>.dijkstra(start: T, end: T): Pair<Int, List<T>> {
    data class Head(val node: T, val value: Int) : Comparable<Head> {
        override fun compareTo(other: Head): Int {
            return (value - other.value)
        }
    }

    val distance = mutableMapOf<T, Int>()
    val parent = mutableMapOf<T, T>()

    val priorityQueue = PriorityQueue<Head>()

    priorityQueue.add(Head(start, 0))
    distance[start] = 0

    while (priorityQueue.isNotEmpty()) {
        val n = priorityQueue.poll()
        if (n.node == end)
            return n.value to parent.findPath(start, end)
        val d = distance[n.node]!!
        for (v in this.getEdgesSuccessors(n.node)) {
            val newDistance = d + v.weight
            if (distance.getOrDefault(v.to, Int.MAX_VALUE) > newDistance) {
                parent[v.to] = v.from
                distance[v.to] = newDistance
                priorityQueue.add(Head(v.to, newDistance))
            }
        }
    }
    return Int.MAX_VALUE to listOf()
}

fun <T> Map<T, T>.findPath(from: T, to: T): List<T> {
    val result = mutableListOf<T>()

    var x = to
    while (x != from) {
        result.add(x)
        x = this[x]!!
    }
    result.add(from)

    return result.reversed()
}
