package org.clubcode.library.math

import java.util.*
import kotlin.NoSuchElementException

class WeightedDirectedGraph<T> {
    private val l: MutableMap<T, MutableSet<Edge>> = mutableMapOf()

    fun addNode(el: T) {
        val set = l.getOrDefault(el, mutableSetOf())
        l[el] = set
    }

    fun addNodes(c: Collection<T>) {
        for (v in c)
            addNode(v)
    }

    fun removeNode(el: T) {
        val e = Edge(el, 0)
        l.remove(el)
        for (x in l)
            x.value.remove(e)
    }

    fun removeNodes(c: Collection<T>) {
        for (v in c)
            removeNode(v)
    }

    fun addEdge(from: T, to: T, weight: Number) {
        addNode(from)
        addNode(to)
        getEdges(from).add(Edge(to, weight))
    }

    fun addEdges(edges: Collection<Triple<T, T, Number>>) {
        for (edge in edges) {
            addEdge(edge.first, edge.second, edge.third)
        }
    }

    fun removeEdge(from: T, to: T) {
        val set = getEdges(from)
        set.remove(Edge(to, 0))
    }

    fun removeEdges(edges: Collection<Pair<T, T>>) {
        for (edge in edges) {
            removeEdge(edge.first, edge.second)
        }
    }

    fun clear() {
        l.clear()
    }

    operator fun contains(el: T): Boolean = l[el] != null

    operator fun contains(node: Node) = node.element in this

    private fun getEdges(el: T): MutableSet<Edge> {
        return l[el] ?: noSuchElement(el)
    }

    fun getNode(): Set<Node> = l.keys.map { Node(it) }.toSet()

    operator fun get(el: T) = Node(el ?: noSuchElement(el))

    inner class Node(val element: T) {
        fun getNeighbors(): Set<Node> {
            return getEdges(element).map { Node(it.to) }.toSet()
        }

        fun getEdges(): Set<NodeEdge> = getEdges(element).map { it.toNodeEdge() }.toSet()

        override fun toString(): String {
            return "$element"
        }
    }

    val revert: WeightedDirectedGraph<T>
        get() {
            val g = WeightedDirectedGraph<T>()
            g.addEdges(l.map { it.value.map { v -> Triple(v.to, it.key, v.weight) } }.flatten())
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
            for (neighbor in getEdges(s).map { it.to }) {
                if (neighbor !in marked) {
                    queue.add(neighbor)
                    marked.add(neighbor)
                }
            }
        }
        return marked
    }

    private fun noSuchElement(el: T): Nothing = throw NoSuchElementException("$el is not a vertex of the graph")

    private inner class Edge(val to: T, var weight: Number) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is WeightedDirectedGraph<*>.Edge) return false

            if (to != other.to) return false

            return true
        }

        override fun hashCode(): Int {
            return to.hashCode()
        }

        fun toNodeEdge(): NodeEdge = NodeEdge(Node(this.to), weight)

        override fun toString(): String {
            return "($to, $weight)"
        }
    }

    inner class NodeEdge(val to: Node, var weight: Number) {
        override fun toString(): String {
            return "($to, $weight)"
        }
    }

    override fun toString(): String {
        return l.map { "  ${it.key} -> ${it.value}" }.joinToString("\n", "Graph:\n")
    }
}

