package org.clubcode.library.math

interface Graph<T, K> {
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

    fun addEdge(edge: WeightedEdge<T, K>) {
        addEdge(edge.from, edge.to, edge.weight)
    }

    fun addEdge(from: T, to: T, weight: K)

    fun addEdges(edges: Collection<WeightedEdge<T, K>>) {
        for (edge in edges) {
            addEdge(edge)
        }
    }

    fun removeEdge(from: T, to: T)

    fun removeEdge(edge: Edge<T, K>) {
        removeEdge(edge.from, edge.to)
    }

    fun removeEdge(edge: SimpleEdge<T>) {
        removeEdge(edge.from, edge.to)
    }

    fun removeEdges(edges: Collection<Edge<T, K>>) {
        for (edge in edges) {
            removeEdge(edge)
        }
    }

    fun getEdgesSuccessors(el: T): Set<WeightedEdge<T, K>>

    fun getEdgesPredecessors(el: T): Set<WeightedEdge<T, K>>

    fun getEdgesAdjacents(el: T): Set<WeightedEdge<T, K>> {
        return getEdgesPredecessors(el) + getEdgesSuccessors(el)
    }

    fun getNodes() : Set<Node<T, K>>

    fun getSuccessors(el: T) : Set<Node<T, K>>

    fun getPredecessors(el: T) : Set<Node<T, K>>

    fun getAdjacents(el: T) : Set<Node<T, K>> {
        return getSuccessors(el) + getPredecessors(el)
    }

    operator fun contains(el: T): Boolean

    fun clear()
}

interface Edge<T, K> {
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

class WeightedEdge<T, K>(override val from: T, override val to: T, override var weight: K) : Edge<T, K> {
    fun reverse(): Edge<T, K> {
        return WeightedEdge(to, from, weight)
    }

    override fun toString(): String {
        return "($from -> $to, $weight)"
    }
}

class Node<T, K> internal constructor(val element: T, private val graph: Graph<T, K>) {
    fun getSuccessors(): Set<Node<T, K>> = graph.getSuccessors(element)

    fun getPredecessors(): Set<Node<T, K>> = graph.getPredecessors(element)

    fun getEdges(): Set<WeightedEdge<T, K>> = graph.getEdgesSuccessors(element)

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

