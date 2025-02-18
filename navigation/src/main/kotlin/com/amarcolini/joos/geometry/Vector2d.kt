package com.amarcolini.joos.geometry

import com.amarcolini.joos.util.*
import org.apache.commons.math3.util.FastMath
import kotlin.math.acos
import kotlin.math.sqrt

/**
 * Class for representing 2D vectors (x and y).
 */
data class Vector2d @JvmOverloads constructor(
    @JvmField var x: Double = 0.0,
    @JvmField var y: Double = 0.0
) {
    companion object Static {
        /**
         * Returns a vector in Cartesian coordinates `(x, y)` from one in polar coordinates `(r, theta)`.
         */
        @JvmStatic
        fun polar(r: Double, theta: Angle) =
            Vector2d(r * theta.cos(), r * theta.sin())
    }

    /**
     * Returns the magnitude of this vector.
     */
    fun norm(): Double = sqrt(x * x + y * y)

    /**
     * Returns the angle of this vector.
     */
    fun angle(): Angle = Angle(FastMath.atan2(y, x), AngleUnit.Radians)

    /**
     * Calculates the angle between two vectors (in radians).
     */
    infix fun angleBetween(other: Vector2d): Angle =
        Angle(acos((this dot other) / (norm() * other.norm())), AngleUnit.Radians)

    /**
     * Adds two vectors.
     */
    operator fun plus(other: Vector2d): Vector2d =
        Vector2d(x + other.x, y + other.y)

    /**
     * Subtracts two vectors.
     */
    operator fun minus(other: Vector2d): Vector2d =
        Vector2d(x - other.x, y - other.y)

    /**
     * Multiplies this vector by a scalar.
     */
    operator fun times(scalar: Double): Vector2d = Vector2d(scalar * x, scalar * y)

    /**
     * Divides this vector by a scalar.
     */
    operator fun div(scalar: Double): Vector2d = Vector2d(x / scalar, y / scalar)

    /**
     * Returns the negative of this vector.
     */
    operator fun unaryMinus(): Vector2d = Vector2d(-x, -y)

    /**
     * Returns the dot product of two vectors.
     */
    infix fun dot(other: Vector2d): Double = x * other.x + y * other.y

    /**
     * Returns the 2D cross product of two vectors.
     */
    infix fun cross(other: Vector2d): Double = x * other.y - y * other.x

    /**
     * Returns the distance between two vectors.
     */
    infix fun distTo(other: Vector2d): Double = (this - other).norm()

    /**
     * Returns the projection of this vector onto another.
     */
    infix fun projectOnto(other: Vector2d): Vector2d = other * (this dot other) / (other dot other)

    /**
     * Rotates this vector by [angle].
     */
    fun rotated(angle: Angle): Vector2d {
        val newX = x * angle.cos() - y * angle.sin()
        val newY = x * angle.sin() + y * angle.cos()
        return Vector2d(newX, newY)
    }

    /**
     * Rotates this vector by [angle], where [angle] is in [Angle.defaultUnits].
     */
    fun rotated(angle: Double): Vector2d = rotated(Angle(angle))

    /**
     * Returns whether two vectors are approximately equal (within [EPSILON]).
     */
    infix fun epsilonEquals(other: Vector2d): Boolean =
        x epsilonEquals other.x && y epsilonEquals other.y

    override fun toString(): String = String.format("(%.3f, %.3f)", x, y)
}