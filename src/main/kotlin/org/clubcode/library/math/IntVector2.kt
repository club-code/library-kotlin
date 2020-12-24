package org.clubcode.library.math

import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.pow
import kotlin.math.sqrt

data class IntVector2(val x: Int, val y: Int){
    fun getManhattanDistance(v: IntVector2) = abs(x-v.x) + abs(y-v.y)

    fun getEuclideanDistance(v: IntVector2) = sqrt((x-v.x).toDouble().pow(2.0)+(y-v.y).toDouble().pow(2.0))

    fun getDistance(v: IntVector2, alpha: Double) =  ((x-v.x).toDouble().pow(alpha)+(y-v.y).toDouble().pow(alpha)).pow(1/alpha)

    fun getAngle(v: IntVector2) = acos((x*v.x+y*v.y)/(norm*v.norm))

    fun rotateDegree(angle: Int): IntVector2 {
        val a = angle%360
        return when (if(a < 0) a + 360 else a) {
            0 -> this
            90 -> IntVector2(-this.y, this.x)
            180 -> IntVector2(-this.x, -this.y)
            270 -> IntVector2(this.y, -this.x)
            else -> throw IllegalArgumentException("Must be multiple of 90")
        }
    }

    operator fun minus(v: IntVector2) = IntVector2(x-v.x,y-v.y)

    operator fun plus(v: IntVector2) = IntVector2(x+v.x,y+v.y)

    fun dot(v: IntVector2): Int = x*v.x+y*v.y

    val angle: Double by lazy {
        acos(x/norm)
    }

    val norm: Double by lazy {
        sqrt(x.toDouble().pow(2.0) + y.toDouble().pow(2.0))
    }
}

operator fun Int.times(v: IntVector2) = IntVector2(this*v.x, this*v.y)
