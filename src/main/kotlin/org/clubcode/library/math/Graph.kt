package org.clubcode.library.math

interface Graph<T, K: Number> {
    fun addNode(el: T)

    fun addNodes(c: Collection<T>) {
        for (node in c) {
            addNode(node)
        }
    }

    fun removeNode(el: T)

    fun removeNodes(c: Collection<T>) {
        for (v in c)
            removeNode(v)
    }

    fun addEdge(edge: WeightedEdge<T, K>)

    fun addEdges(edges: Collection<WeightedEdge<T, K>>) {
        for (edge in edges) {
            addEdge(edge)
        }
    }

    fun removeEdge(edge: SimpleEdge<T>)

    fun removeEdges(edges: Collection<SimpleEdge<T>>) {
        for (edge in edges) {
            removeEdge(edge)
        }
    }

    fun getEdges(el: T): Set<WeightedEdge<T, K>>

    fun getNodes() : Set<Node<T, K>>

    fun getNeighbors(el: T) : Set<Node<T, K>>

    operator fun contains(el: T): Boolean

    fun clear()
}

interface Edge<T, K: Number> {
    var weight: K
    val from: T
    val to: T

    fun toSimpleEdge(): SimpleEdge<T> = SimpleEdge(from, to)
}

class SimpleEdge<T>(override val from: T, override val to: T): Edge<T, Int> {
    fun reverse(): Edge<T, Int> {
        return SimpleEdge(to, from)
    }

    override var weight = 1

    override fun toString(): String {
        return "($from -> $to)"
    }
}

class WeightedEdge<T, K: Number>(override val from: T, override val to: T, override var weight: K) : Edge<T, K> {
    fun reverse(): Edge<T, K> {
        return WeightedEdge(to, from, weight)
    }

    override fun toString(): String {
        return "($from -> $to, $weight)"
    }
}

class Node<T, K: Number>(val element: T, private val graph: Graph<T, K>) {
    fun getNeighbors(): Set<Node<T, K>> = graph.getNeighbors(element)

    fun getEdges(): Set<WeightedEdge<T, K>> = graph.getEdges(element)

    fun exists() = graph.contains(element)

    override fun toString(): String {
        return "$element"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Node<*,*>) return false

        if (element != other.element) return false

        return true
    }

    override fun hashCode(): Int {
        return element.hashCode()
    }
}

