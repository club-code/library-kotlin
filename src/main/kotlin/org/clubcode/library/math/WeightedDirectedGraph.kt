package org.clubcode.library.math

import java.util.*
import kotlin.NoSuchElementException

class WeightedDirectedGraph<T, K: Number> : Graph<T, K> {
    private val l: MutableMap<T, MutableSet<InnerEdge>> = mutableMapOf()

    override fun addNode(el: T) {
        val set = l.getOrDefault(el, mutableSetOf())
        l[el] = set
    }

    override fun removeNode(el: T) {
        l.remove(el)
        for (x in l)
            x.value.removeIf { it.to == el }
    }

    override fun addEdge(edge: WeightedEdge<T, K>) {
        addNode(edge.from)
        addNode(edge.to)
        getEdgesFrom(edge.from).add(InnerEdge(edge.to, edge.weight))
    }

    override fun removeEdge(edge: SimpleEdge<T>) {
        val set = getEdgesFrom(edge.from)
        set.removeIf { it.to == edge.to }
    }

    override fun clear() {
        l.clear()
    }

    override operator fun contains(el: T): Boolean = l[el] != null

    private fun getEdgesFrom(el: T): MutableSet<InnerEdge> {
        return l[el] ?: noSuchElement(el)
    }

    override fun getEdges(el: T): Set<WeightedEdge<T, K>> {
        return getEdgesFrom(el).map { it.toWeightedEdge(el) }.toSet()
    }

    fun getEdges(): Set<WeightedEdge<T, K>> {
        return getNodes().map { it.getEdges() }.flatten().toSet()
    }

    override fun getNodes(): Set<Node<T, K>> = l.keys.map { Node(it, this) }.toSet()

    operator fun get(el: T) = Node(el ?: noSuchElement(el), this)

    override fun getNeighbors(el: T): Set<Node<T, K>> {
        return getEdgesFrom(el).map { Node(it.to, this) }.toSet()
    }

    fun getReverse(): WeightedDirectedGraph<T, K> {
        val g = WeightedDirectedGraph<T, K>()
        g.addEdges(getEdges().map { it.reverse() as WeightedEdge<T, K> })
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
            for (neighbor in getEdgesFrom(s).map { it.to }) {
                if (neighbor !in marked) {
                    queue.add(neighbor)
                    marked.add(neighbor)
                }
            }
        }
        return marked
    }

    private fun noSuchElement(el: T): Nothing = throw NoSuchElementException("$el is not a vertex of the graph")

    private inner class InnerEdge(val to: T, var weight: K) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is WeightedDirectedGraph<*,*>.InnerEdge) return false

            if (to != other.to) return false

            return true
        }

        override fun hashCode(): Int {
            return to.hashCode()
        }

        fun toWeightedEdge(from: T): WeightedEdge<T, K> = WeightedEdge(from, to, weight)

        override fun toString(): String {
            return "($to, $weight)"
        }
    }



    override fun toString(): String {
        return l.map { "  ${it.key} -> ${it.value}" }.joinToString("\n", "Graph:\n")
    }
}

