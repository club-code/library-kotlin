package org.clubcode.library.graph

open class Edge<T>(val from: T, val to: T) {
    open fun reverse(): Edge<T> {
        return Edge(to, from)
    }

    override fun toString(): String {
        return "($from -> $to)"
    }
}

class ValueEdge<T, K>(from: T, to: T, var weight: K): Edge<T>(from, to) {
    override fun reverse(): ValueEdge<T, K> {
        return ValueEdge(to, from, weight)
    }

    override fun toString(): String {
        return "($from -> $to, $weight)"
    }
}

