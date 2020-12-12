package org.clubcode.library.collection

fun checkIndexOverflow(index: Int): Int {
    if (index < 0) {
        throw ArithmeticException("Index overflow has happened.")
    }
    return index
}

fun checkCountOverflow(count: Int): Int {
    if (count < 0) {
        throw ArithmeticException("Count overflow has happened.")
    }
    return count
}

inline fun <T> Iterable<T>.countIndexed(predicate: (index: Int, T) -> Boolean): Int {
    if (this is Collection && isEmpty()) return 0
    var count = 0
    var index = 0
    for (element in this) if(predicate(checkIndexOverflow(index++), element)) checkCountOverflow(++count)
    return count
}

inline fun <T> List<T>.split(predicate: (T) -> Boolean): List<List<T>> {
    return this.fold(mutableListOf<MutableList<T>>(mutableListOf())) { l, it ->
        if(predicate(it)) l.add(mutableListOf()) else l[l.size - 1].add(it)
        l
    }
}

inline fun <T> Sequence<T>.split(predicate: (T) -> Boolean): List<List<T>> {
    return this.fold(mutableListOf<MutableList<T>>(mutableListOf())) { l, it ->
        if(predicate(it)) l.add(mutableListOf()) else l[l.size - 1].add(it)
        l
    }
}
