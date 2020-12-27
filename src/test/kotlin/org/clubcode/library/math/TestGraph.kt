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
        assertEquals(1, graph[0].getSuccessors().size)
        assertTrue(graph[1].getSuccessors().isEmpty())
        assertTrue(graph[3].getSuccessors().isEmpty())
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
    fun testGetSuccessors() {
        val neighbors = graph.getSuccessors(0)
        assertEquals(2, neighbors.size)
        val s = neighbors.map { it.element }.toSet()
        assertTrue(1 in s)
        assertTrue(2 in s)
    }

    @Test
    fun testGetPredecessors() {
        val neighbors = graph.getPredecessors(2)
        assertEquals(3, neighbors.size)
        val s = neighbors.map { it.element }.toSet()
        assertTrue(0 in s)
        assertTrue(1 in s)
        assertTrue(3 in s)
    }

    @Test
    fun testGetAdjacents() {
        val neighbors = graph.getAdjacents(2)
        assertEquals(3, neighbors.size)
        val s = neighbors.map { it.element }.toSet()
        assertTrue(0 in s)
        assertTrue(1 in s)
        assertTrue(3 in s)
    }

    @Test
    fun testDijsktra() {
        println(graph.dijkstra(0, 3))
    }
}