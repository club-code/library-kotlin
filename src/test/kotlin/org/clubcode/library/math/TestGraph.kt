package org.clubcode.library.math

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TestGraph {
    val graph: WeightedDirectedGraph<Int, Int> = WeightedDirectedGraph()

    @Before
    fun init() {
        graph.clear()
        graph.addEdges(listOf(
            WeightedEdge(0, 1, 10),
            WeightedEdge(0, 2, 1),
            WeightedEdge(1, 2, 5),
            WeightedEdge(2,3, 2),
            WeightedEdge(3,2, 4),
            WeightedEdge(4,5, -1),
        ))
    }

    @Test
    fun printGraph() {
        println(graph)
    }

    @Test
    fun removeGraph() {
        graph.removeNode(2)
        assertEquals(1, graph[0].getNeighbors().size)
        assertTrue(graph[1].getNeighbors().isEmpty())
        assertTrue(graph[3].getNeighbors().isEmpty())
        assertEquals(false, graph.contains(2))
    }

    @Test
    fun printBreadth() {
        graph.breadthFirstSearch(0) {
            println(it)
        }

    }

    @Test
    fun getNode() {
        val node = graph[0]
        assertEquals(0, node.element)
    }

    @Test
    fun getNeighbors() {
        val neighbors = graph[0].getNeighbors()
        assertEquals(2, neighbors.size)
        val s = neighbors.map { it.element }.toSet()
        assertTrue(1 in s)
        assertTrue(2 in s)
    }
}