package org.clubcode.library.graph

interface UnweightedGraph<T>: Graph<T, Edge<T>> {
    override fun addEdge(edge: Edge<T>) {
        addEdge(edge.from, edge.to)
    }

    fun addEdge(from: T, to: T)

    fun removeEdge(from: T, to: T)

    override fun removeEdge(edge: Edge<T>) {
        removeEdge(edge.from, edge.to)
    }
}
