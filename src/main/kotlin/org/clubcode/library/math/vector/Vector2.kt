package org.clubcode.library.math.vector

import kotlin.math.*

data class Vector2(val x: Double, val y: Double){
    fun getManhattanDistance(v: Vector2) = abs(x-v.x) + abs(y-v.y)

    fun getEuclideanDistance(v: Vector2) = sqrt((x-v.x).pow(2.0)+(y-v.y).pow(2.0))

    fun getDistance(v: Vector2, alpha: Double) =  ((x-v.x).pow(alpha)+(y-v.y).pow(alpha)).pow(1/alpha)

    fun getAngle(v: Vector2) = acos((x*v.x+y*v.y)/(norm*v.norm))

    fun rotate(angle: Double): Vector2 {
        val c = cos(angle)
        val s = sin(angle)

        return Vector2(c * x - s * y, s * x + c * y)
    }

    operator fun minus(v: Vector2) = Vector2(x-v.x,y-v.y)

    operator fun plus(v: Vector2) = Vector2(x+v.x,y+v.y)

    fun dot(v: Vector2): Double = x*v.x+y*v.y

    fun crossNorm(v: Vector2) = x*v.y-y*v.x

    val angle: Double by lazy {
        acos(x/norm)
    }

    val norm: Double by lazy {
        sqrt(x.pow(2.0) + y.pow(2.0))
    }
}

operator fun Int.times(v: Vector2) = Vector2(this*v.x, this*v.y)

fun Double.toDegrees() = this/PI*180
fun Double.toRadian() = this/180*PI

