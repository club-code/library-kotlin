package org.clubcode.library.graph

import java.util.LinkedList

fun <T> Graph<T, *>.breadthFirstSearch(node: T, f: (T) -> Unit): Set<T> {
    val queue = LinkedList<T>()
    val marked = mutableSetOf<T>()

    queue.add(node)
    marked.add(node)
    while (queue.isNotEmpty()) {
        val s = queue.remove()
        f(s)
        for (neighbor in getSuccessors(s)) {
            if (neighbor !in marked) {
                queue.add(neighbor)
                marked.add(neighbor)
            }
        }
    }
    return marked
}
