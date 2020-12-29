package org.clubcode.library.graph

class UndirectedUnweightedGraph<T> : UnweightedGraph<T> {
    private val adjacentsEdges: MutableMap<T, MutableSet<T>> = mutableMapOf()

    override fun addNode(el: T) {
        adjacentsEdges.getOrPut(el) { mutableSetOf() }
    }

    override fun removeNode(el: T) {
        for (x in adjacentsEdges[el]!!) {
            adjacentsEdges[x]!!.remove(el)
        }
        adjacentsEdges.remove(el)
    }

    override fun addEdge(from: T, to: T) {
        addNode(from)
        addNode(to)
        getEdgesFrom(from).add(to)
        getEdgesFrom(to).add(from)
    }

    override fun removeEdge(from: T, to: T) {
        getEdgesFrom(from).remove(to)
        getEdgesFrom(to).remove(from)
    }

    override fun clear() {
        adjacentsEdges.clear()
    }

    override operator fun contains(el: T): Boolean = adjacentsEdges[el] != null

    override fun contains(edge: Edge<T>): Boolean {
        return adjacentsEdges[edge.from]?.contains(edge.to) ?: false
    }

    private fun getEdgesFrom(el: T): MutableSet<T> {
        return adjacentsEdges[el] ?: noSuchElement(el)
    }

    override fun getEdgesSuccessors(el: T): Set<Edge<T>> {
        return getEdgesAdjacents(el)
    }

    override fun getEdgesPredecessors(el: T): Set<Edge<T>> {
        return getEdgesFrom(el).map { Edge(it, el) }.toSet()
    }

    override fun getEdgesAdjacents(el: T): Set<Edge<T>> {
        return getEdgesFrom(el).map { Edge(el, it) }.toSet()
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
        return getEdgesFrom(el)
    }

    fun getReverse(): UndirectedUnweightedGraph<T> {
        val g = UndirectedUnweightedGraph<T>()
        g.addEdges(getAllEdges().map { it.reverse() })
        return g
    }

    private fun noSuchElement(el: T): Nothing = throw NoSuchElementException("$el is not a vertex of the graph")

    override fun toString(): String {
        return adjacentsEdges.map { "  ${it.key} -> ${it.value}" }.joinToString("\n", "Graph:\n")
    }
}

