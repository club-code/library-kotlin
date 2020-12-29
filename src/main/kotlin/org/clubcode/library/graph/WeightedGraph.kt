package org.clubcode.library.graph

interface WeightedGraph<T, K>: Graph<T, ValueEdge<T, K>> {
    override fun addEdge(edge: ValueEdge<T, K>) {
        addEdge(edge.from, edge.to, edge.weight)
    }

    fun addEdge(from: T, to: T, weight: K)

    fun removeEdge(from: T, to: T)

    override fun removeEdge(edge: Edge<T>) {
        removeEdge(edge.from, edge.to)
    }

}
