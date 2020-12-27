package org.clubcode.library.math

import java.util.*
import kotlin.NoSuchElementException

class WeightedDirectedGraph<T, K> : Graph<T, K> {
    private val successorsEdges: MutableMap<T, MutableSet<InnerEdge>> = mutableMapOf()
    private val predecessorsEdges: MutableMap<T, MutableSet<InnerEdge>> = mutableMapOf()

    override fun addNode(el: T) {
        successorsEdges.getOrPut(el) { mutableSetOf() }
        predecessorsEdges.getOrPut(el) { mutableSetOf() }
    }

    override fun removeNode(el: T) {
        val e = InnerEdge(el, null)
        for (x in successorsEdges[el]!!) {
            predecessorsEdges[x.to]!!.remove(e)
        }
        for (x in predecessorsEdges[el]!!) {
            successorsEdges[x.to]!!.remove(e)
        }
        successorsEdges.remove(el)
        predecessorsEdges.remove(el)
    }

    override fun addEdge(from: T, to: T, weight: K) {
        addNode(from)
        addNode(to)
        getEdgesSuccessorsFrom(from).add(InnerEdge(to, weight))
        getEdgesPredecessorsFrom(to).add(InnerEdge(from, weight))
    }

    override fun removeEdge(from: T, to: T) {
        getEdgesSuccessorsFrom(from).remove(InnerEdge(to, null))
        getEdgesPredecessorsFrom(to).remove(InnerEdge(from, null))
    }

    override fun clear() {
        successorsEdges.clear()
        predecessorsEdges.clear()
    }

    override operator fun contains(el: T): Boolean = successorsEdges[el] != null

    private fun getEdgesSuccessorsFrom(el: T): MutableSet<InnerEdge> {
        return successorsEdges[el] ?: noSuchElement(el)
    }

    private fun getEdgesPredecessorsFrom(el: T): MutableSet<InnerEdge> {
        return predecessorsEdges[el] ?: noSuchElement(el)
    }

    override fun getEdgesSuccessors(el: T): Set<WeightedEdge<T, K>> {
        return getEdgesSuccessorsFrom(el).map { it.toWeightedEdge(el) }.toSet()
    }


    override fun getEdgesPredecessors(el: T): Set<WeightedEdge<T, K>> {
        return getEdgesPredecessorsFrom(el).map { it.toWeightedEdge(el) }.toSet()
    }

    fun getAllEdges(): Set<WeightedEdge<T, K>> {
        return getNodes().map { it.getEdges() }.flatten().toSet()
    }

    override fun getNodes(): Set<Node<T, K>> = successorsEdges.keys.map { Node(it, this) }.toSet()

    operator fun get(el: T) = Node(el ?: noSuchElement(el), this)

    override fun getSuccessors(el: T): Set<Node<T, K>> {
        return getEdgesSuccessorsFrom(el).map { Node(it.to, this) }.toSet()
    }

    override fun getPredecessors(el: T): Set<Node<T, K>> {
        return getEdgesPredecessorsFrom(el).map { Node(it.to, this) }.toSet()
    }

    fun getReverse(): WeightedDirectedGraph<T, K> {
        val g = WeightedDirectedGraph<T, K>()
        g.addEdges(getAllEdges().map { it.reverse() as WeightedEdge<T, K> })
        return g
    }


    fun breadthFirstSearch(node: T, f: (T) -> Unit): Set<T> {
        val queue = LinkedList<T>()
        val marked = mutableSetOf<T>()

        queue.add(node)
        marked.add(node)
        while (queue.isNotEmpty()) {
            val s = queue.remove()
            f(s)
            for (neighbor in getEdgesSuccessorsFrom(s).map { it.to }) {
                if (neighbor !in marked) {
                    queue.add(neighbor)
                    marked.add(neighbor)
                }
            }
        }
        return marked
    }

    private fun noSuchElement(el: T): Nothing = throw NoSuchElementException("$el is not a vertex of the graph")

    private inner class InnerEdge(val to: T, val weight: K?) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is WeightedDirectedGraph<*, *>.InnerEdge) return false

            if (to != other.to) return false

            return true
        }

        override fun hashCode(): Int {
            return to.hashCode()
        }

        fun toWeightedEdge(from: T): WeightedEdge<T, K> = WeightedEdge(from, to, weight!!)

        override fun toString(): String {
            return "($to, $weight)"
        }
    }

    override fun toString(): String {
        System.err.println(predecessorsEdges.map { "  ${it.key} -> ${it.value}" }.joinToString("\n", "Graph:\n"))
        return successorsEdges.map { "  ${it.key} -> ${it.value}" }.joinToString("\n", "Graph:\n")
    }
}

fun <T> WeightedDirectedGraph<T, Int>.dijkstra(start: T, end: T): Pair<Int, List<T>> {
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