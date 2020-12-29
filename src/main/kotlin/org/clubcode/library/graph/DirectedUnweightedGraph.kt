package org.clubcode.library.graph

class DirectedUnweightedGraph<T> : UnweightedGraph<T> {
    private val successorsEdges: MutableMap<T, MutableSet<T>> = mutableMapOf()
    private val predecessorsEdges: MutableMap<T, MutableSet<T>> = mutableMapOf()

    override fun addNode(el: T) {
        successorsEdges.getOrPut(el) { mutableSetOf() }
        predecessorsEdges.getOrPut(el) { mutableSetOf() }
    }

    override fun removeNode(el: T) {
        for (x in successorsEdges[el]!!) {
            predecessorsEdges[x]!!.remove(el)
        }
        for (x in predecessorsEdges[el]!!) {
            successorsEdges[x]!!.remove(el)
        }
        successorsEdges.remove(el)
        predecessorsEdges.remove(el)
    }

    override fun addEdge(from: T, to: T) {
        addNode(from)
        addNode(to)
        getEdgesSuccessorsFrom(from).add(to)
        getEdgesPredecessorsFrom(to).add(from)
    }

    override fun removeEdge(from: T, to: T) {
        getEdgesSuccessorsFrom(from).remove(to)
        getEdgesPredecessorsFrom(to).remove(from)
    }

    override fun clear() {
        successorsEdges.clear()
        predecessorsEdges.clear()
    }

    override operator fun contains(el: T): Boolean = successorsEdges[el] != null

    override fun contains(edge: Edge<T>): Boolean {
        return successorsEdges[edge.from]?.contains(edge.to) ?: false
    }

    private fun getEdgesSuccessorsFrom(el: T): MutableSet<T> {
        return successorsEdges[el] ?: noSuchElement(el)
    }

    private fun getEdgesPredecessorsFrom(el: T): MutableSet<T> {
        return predecessorsEdges[el] ?: noSuchElement(el)
    }

    override fun getEdgesSuccessors(el: T): Set<Edge<T>> {
        return getEdgesSuccessorsFrom(el).map { Edge(it, el) }.toSet()
    }

    override fun getEdgesPredecessors(el: T): Set<Edge<T>> {
        return getEdgesPredecessorsFrom(el).map { Edge(el, it) }.toSet()
    }

    override fun getNodes(): Set<T> = successorsEdges.keys

    operator fun get(el: T) = el ?: noSuchElement(el)

    override fun getSuccessors(el: T): Set<T> {
        return getEdgesSuccessorsFrom(el)
    }

    override fun getPredecessors(el: T): Set<T> {
        return getEdgesPredecessorsFrom(el)
    }

    fun getReverse(): DirectedUnweightedGraph<T> {
        val g = DirectedUnweightedGraph<T>()
        g.addEdges(getAllEdges().map { it.reverse() })
        return g
    }

    private fun noSuchElement(el: T): Nothing = throw NoSuchElementException("$el is not a vertex of the graph")

    override fun toString(): String {
        return successorsEdges.map { "  ${it.key} -> ${it.value}" }.joinToString("\n", "Graph:\n")
    }
}

