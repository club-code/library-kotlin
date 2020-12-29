package org.clubcode.library.graph

class UndirectedWeightedGraph<T, K> : WeightedGraph<T, K> {
    private val adjacentsEdges: MutableMap<T, MutableSet<InnerEdge>> = mutableMapOf()

    override fun addNode(el: T) {
        adjacentsEdges.getOrPut(el) { mutableSetOf() }
    }

    override fun removeNode(el: T) {
        val e = InnerEdge(el, null)
        for (x in adjacentsEdges[el]!!) {
            adjacentsEdges[x.to]!!.remove(e)
        }
        adjacentsEdges.remove(el)
    }

    override fun addEdge(from: T, to: T, weight: K) {
        addNode(from)
        addNode(to)
        getEdgesFrom(from).add(InnerEdge(to, weight))
        getEdgesFrom(to).add(InnerEdge(from, weight))
    }

    override fun removeEdge(from: T, to: T) {
        getEdgesFrom(from).remove(InnerEdge(to, null))
        getEdgesFrom(to).remove(InnerEdge(from, null))
    }

    override fun clear() {
        adjacentsEdges.clear()
    }

    override operator fun contains(el: T): Boolean = adjacentsEdges[el] != null

    override operator fun contains(edge: Edge<T>) : Boolean {
        return adjacentsEdges[edge.from]?.contains(InnerEdge(edge.to, null)) ?: false
    }

    private fun getEdgesFrom(el: T): MutableSet<InnerEdge> {
        return adjacentsEdges[el] ?: noSuchElement(el)
    }

    override fun getEdgesSuccessors(el: T): Set<ValueEdge<T, K>> {
        return getEdgesAdjacents(el)
    }

    override fun getEdgesPredecessors(el: T): Set<ValueEdge<T, K>> {
        return getEdgesFrom(el).map { ValueEdge<T, K>(it.to, el, it.weight!!) }.toSet()
    }

    override fun getEdgesAdjacents(el: T): Set<ValueEdge<T, K>> {
        return getEdgesFrom(el).map { ValueEdge<T, K>(el, it.to, it.weight!!) }.toSet()
    }

    override fun getNodes(): Set<T> = adjacentsEdges.keys

    operator fun get(el: T) = el ?: noSuchElement(el)

    override fun getSuccessors(el: T): Set<T> {
        return getAdjacents(el)
    }

    override fun getPredecessors(el: T): Set<T> {
        return getAdjacents(el)
    }

    override fun getAdjacents(el: T): Set<T> {
        return getEdgesFrom(el).map { it.to }.toSet()
    }

    fun getReverse(): DirectedWeightedGraph<T, K> {
        val g = DirectedWeightedGraph<T, K>()
        g.addEdges(getAllEdges().map { it.reverse() })
        return g
    }

    private fun noSuchElement(el: T): Nothing = throw NoSuchElementException("$el is not a vertex of the graph")

    private inner class InnerEdge(val to: T, val weight: K?) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is UndirectedWeightedGraph<*, *>.InnerEdge) return false

            if (to != other.to) return false

            return true
        }

        override fun hashCode(): Int {
            return to.hashCode()
        }

        override fun toString(): String {
            return "($to, $weight)"
        }
    }

    override fun toString(): String {
        return adjacentsEdges.map { "  ${it.key} -> ${it.value}" }.joinToString("\n", "Graph:\n")
    }
}

