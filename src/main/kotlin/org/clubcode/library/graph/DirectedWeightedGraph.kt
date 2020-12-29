package org.clubcode.library.graph

class DirectedWeightedGraph<T, K> : WeightedGraph<T, K> {
    private val successorsEdges: MutableMap<T, MutableSet<InnerEdge>> = mutableMapOf()
    private val predecessorsEdges: MutableMap<T, MutableSet<InnerEdge>> = mutableMapOf()

    override fun addNode(el: T) {
        successorsEdges.getOrPut(el) { mutableSetOf() }
        predecessorsEdges.getOrPut(el) { mutableSetOf() }
    }

    override fun removeNode(el: T) {
        val e = InnerEdge(el, null)
        for (x in successorsEdges[el]!!) {
            predecessorsEdges[x.to]!!.remove(e)
        }
        for (x in predecessorsEdges[el]!!) {
            successorsEdges[x.to]!!.remove(e)
        }
        successorsEdges.remove(el)
        predecessorsEdges.remove(el)
    }

    override fun addEdge(from: T, to: T, weight: K) {
        addNode(from)
        addNode(to)
        getEdgesSuccessorsFrom(from).add(InnerEdge(to, weight))
        getEdgesPredecessorsFrom(to).add(InnerEdge(from, weight))
    }

    override fun removeEdge(from: T, to: T) {
        getEdgesSuccessorsFrom(from).remove(InnerEdge(to, null))
        getEdgesPredecessorsFrom(to).remove(InnerEdge(from, null))
    }

    override fun clear() {
        successorsEdges.clear()
        predecessorsEdges.clear()
    }

    override operator fun contains(el: T): Boolean = successorsEdges[el] != null

    override fun contains(edge: Edge<T>): Boolean {
        return successorsEdges[edge.from]?.contains(InnerEdge(edge.to, null)) ?: false
    }

    private fun getEdgesSuccessorsFrom(el: T): MutableSet<InnerEdge> {
        return successorsEdges[el] ?: noSuchElement(el)
    }

    private fun getEdgesPredecessorsFrom(el: T): MutableSet<InnerEdge> {
        return predecessorsEdges[el] ?: noSuchElement(el)
    }

    override fun getEdgesSuccessors(el: T): Set<ValueEdge<T, K>> {
        return getEdgesSuccessorsFrom(el).map { ValueEdge<T, K>(el, it.to, it.weight!!) }.toSet()
    }

    override fun getEdgesPredecessors(el: T): Set<ValueEdge<T, K>> {
        return getEdgesPredecessorsFrom(el).map { ValueEdge<T, K>(el, it.to, it.weight!!) }.toSet()
    }

    override fun getNodes(): Set<T> = successorsEdges.keys

    operator fun get(el: T) = el ?: noSuchElement(el)

    override fun getSuccessors(el: T): Set<T> {
        return getEdgesSuccessorsFrom(el).map { it.to }.toSet()
    }

    override fun getPredecessors(el: T): Set<T> {
        return getEdgesPredecessorsFrom(el).map { it.to }.toSet()
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
            if (other !is DirectedWeightedGraph<*, *>.InnerEdge) return false

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
        return successorsEdges.map { "  ${it.key} -> ${it.value}" }.joinToString("\n", "Graph:\n")
    }
}

