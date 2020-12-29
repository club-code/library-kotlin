package org.clubcode.library.math

import org.clubcode.library.graph.ValueEdge
import org.clubcode.library.graph.DirectedWeightedGraph
import org.clubcode.library.graph.breadthFirstSearch
import org.clubcode.library.graph.dijkstra
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TestGraph {
    val graph: DirectedWeightedGraph<Int, Int> = DirectedWeightedGraph()

    @Before
    fun init() {
        graph.clear()
        graph.addEdges(listOf(
            ValueEdge(0, 1, 10),
            ValueEdge(0, 2, 1),
            ValueEdge(1, 2, 5),
            ValueEdge(2,3, 2),
            ValueEdge(3,2, 4),
            ValueEdge(4,5, -1),
        ))
    }

    @Test
    fun printGraph() {
        println(graph)
    }

    @Test
    fun removeGraph() {
        graph.removeNode(2)
        assertEquals(1, graph.getSuccessors(0).size)
        assertTrue(graph.getSuccessors(1).isEmpty())
        assertTrue(graph.getSuccessors(3).isEmpty())
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
        assertEquals(0, node)
    }

    @Test
    fun testGetSuccessors() {
        val neighbors = graph.getSuccessors(0)
        assertEquals(2, neighbors.size)
        assertTrue(1 in neighbors)
        assertTrue(2 in neighbors)
    }

    @Test
    fun testGetPredecessors() {
        val neighbors = graph.getPredecessors(2)
        assertEquals(3, neighbors.size)
        assertTrue(0 in neighbors)
        assertTrue(1 in neighbors)
        assertTrue(3 in neighbors)
    }

    @Test
    fun testGetAdjacents() {
        val neighbors = graph.getAdjacents(2)
        assertEquals(3, neighbors.size)
        assertTrue(0 in neighbors)
        assertTrue(1 in neighbors)
        assertTrue(3 in neighbors)
    }

    @Test
    fun testDijsktra() {
        println(graph.dijkstra(0, 3))
    }
}