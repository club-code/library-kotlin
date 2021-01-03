package org.clubcode.library.math.vector

import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.pow
import kotlin.math.sqrt

data class LongVector2(val x: Long, val y: Long){
    fun getManhattanDistance(v: LongVector2) = abs(x-v.x) + abs(y-v.y)

    fun getEuclideanDistance(v: LongVector2) = sqrt((x-v.x).toDouble().pow(2.0)+(y-v.y).toDouble().pow(2.0))

    fun getDistance(v: LongVector2, alpha: Double) =  ((x-v.x).toDouble().pow(alpha)+(y-v.y).toDouble().pow(alpha)).pow(1/alpha)

    fun getAngle(v: LongVector2) = acos((x*v.x+y*v.y)/(norm*v.norm))

    fun rotateDegree(angle: Long): LongVector2 {
        val a = angle%360
        return when (if(a < 0) a + 360 else a) {
            0L -> this
            90L -> LongVector2(-this.y, this.x)
            180L -> LongVector2(-this.x, -this.y)
            270L -> LongVector2(this.y, -this.x)
            else -> throw IllegalArgumentException("Must be multiple of 90")
        }
    }

    operator fun minus(v: LongVector2) = LongVector2(x-v.x,y-v.y)

    operator fun plus(v: LongVector2) = LongVector2(x+v.x,y+v.y)

    fun dot(v: LongVector2): Long = x*v.x+y*v.y

    fun crossNorm(v: LongVector2) = x*v.y-y*v.x

    val angle: Double by lazy {
        acos(x/norm)
    }

    val norm: Double by lazy {
        sqrt(x.toDouble().pow(2.0) + y.toDouble().pow(2.0))
    }
}

operator fun Long.times(v: LongVector2) = LongVector2(this*v.x, this*v.y)
