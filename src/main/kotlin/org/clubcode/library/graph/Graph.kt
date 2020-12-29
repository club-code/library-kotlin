package org.clubcode.library.graph

interface Graph<T, E: Edge<T>> {
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

    fun addEdge(edge: E)

    fun addEdges(edges: Collection<E>) {
        for (edge in edges) {
            addEdge(edge)
        }
    }

    fun removeEdge(edge: Edge<T>)

    fun removeEdges(edges: Collection<Edge<T>>) {
        for (edge in edges) {
            removeEdge(edge)
        }
    }

    fun getNodes() : Set<T>

    fun getSuccessors(el: T) : Set<T>

    fun getPredecessors(el: T) : Set<T>

    fun getAdjacents(el: T) : Set<T> {
        return getSuccessors(el) + getPredecessors(el)
    }

    fun getEdgesSuccessors(el: T): Set<E>

    fun getEdgesPredecessors(el: T): Set<E>

    fun getEdgesAdjacents(el: T): Set<E> {
        return getEdgesPredecessors(el) + getEdgesSuccessors(el)
    }

    fun getAllEdges(): Set<E> {
        return getNodes().map { getEdgesAdjacents(it) }.flatten().toSet()
    }

    operator fun contains(el: T): Boolean

    operator fun contains(edge: Edge<T>): Boolean

    fun clear()

}