package org.clubcode.library.graph

open class Edge<T>(val from: T, val to: T) {
    open fun reverse(): Edge<T> {
        return Edge(to, from)
    }

    override fun toString(): String {
        return "($from -> $to)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Edge<*>) return false

        if (from != other.from) return false
        if (to != other.to) return false

        return true
    }

    override fun hashCode(): Int {
        var result = from?.hashCode() ?: 0
        result = 31 * result + (to?.hashCode() ?: 0)
        return result
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

