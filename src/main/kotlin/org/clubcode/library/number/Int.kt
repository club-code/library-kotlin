package org.clubcode.library.number

fun Int.pow(pow: Int): Int {
    return when {
        pow == 0 -> 1
        pow == 1 -> this
        pow and 1 == 0 -> (this * this).pow(pow / 2)
        else -> this * ((this * this).pow(pow / 2))
    }
}
