package org.clubcode.library.math

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TestGraph {
    val graph: WeightedDirectedGraph<Int> = WeightedDirectedGraph()

    @Before
    fun init() {
        graph.clear()
        graph.addEdges(listOf(
            Triple(0, 1, 10),
            Triple(0, 2, 1),
            Triple(1, 2, 5),
            Triple(2,3, 2),
            Triple(3,2, 4),
            Triple(4,5, -1),
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
}