package com.neoutils.core.math

import kotlin.math.abs
import kotlin.math.sqrt

/** A collision shape, centered on its owner's position. */
sealed interface Shape {
    data class Circle(val radius: Float) : Shape
    data class Aabb(val size: Size) : Shape
}

/** Overlap contact; [normal] is a unit vector from A toward B. */
data class Collision(
    val normal: Vec2,
    val depth: Float,
)

/** The [Collision] if the shapes (centered at [aPos]/[bPos]) overlap, else `null`. */
fun intersect(a: Shape, aPos: Vec2, b: Shape, bPos: Vec2): Collision? = when {
    a is Shape.Circle && b is Shape.Circle -> circleCircle(a, aPos, b, bPos)
    a is Shape.Aabb && b is Shape.Aabb -> aabbAabb(a, aPos, b, bPos)
    a is Shape.Circle && b is Shape.Aabb -> circleAabb(a, aPos, b, bPos)
    a is Shape.Aabb && b is Shape.Circle -> circleAabb(b, bPos, a, aPos)?.let { it.copy(normal = -it.normal) }
    else -> null
}

private fun circleCircle(a: Shape.Circle, aPos: Vec2, b: Shape.Circle, bPos: Vec2): Collision? {
    val between = bPos - aPos
    val minDistance = a.radius + b.radius

    if (between.lengthSquared() >= minDistance * minDistance) return null

    val distance = between.length()
    // Degenerate concentric case: pick an arbitrary axis to push along.
    val normal = if (distance > 0f) between / distance else Vec2(1f, 0f)

    return Collision(normal, depth = minDistance - distance)
}

private fun aabbAabb(a: Shape.Aabb, aPos: Vec2, b: Shape.Aabb, bPos: Vec2): Collision? {
    val d = bPos - aPos
    val overlapX = (a.size.width + b.size.width) / 2f - abs(d.x)
    if (overlapX <= 0f) return null
    val overlapY = (a.size.height + b.size.height) / 2f - abs(d.y)
    if (overlapY <= 0f) return null

    // Separate along the axis of least penetration; normal points from A toward B.
    return if (overlapX < overlapY) {
        Collision(Vec2(if (d.x < 0f) -1f else 1f, 0f), overlapX)
    } else {
        Collision(Vec2(0f, if (d.y < 0f) -1f else 1f), overlapY)
    }
}

private fun circleAabb(circle: Shape.Circle, circlePos: Vec2, box: Shape.Aabb, boxPos: Vec2): Collision? {
    val half = Vec2(box.size.width / 2f, box.size.height / 2f)

    val closest = Vec2(
        x = circlePos.x.coerceIn(boxPos.x - half.x, boxPos.x + half.x),
        y = circlePos.y.coerceIn(boxPos.y - half.y, boxPos.y + half.y),
    )
    val diff = circlePos - closest
    val distanceSquared = diff.lengthSquared()

    if (distanceSquared > circle.radius * circle.radius) return null

    if (distanceSquared > 0f) {
        val distance = sqrt(distanceSquared)
        val outward = diff / distance // from the box toward the circle (A)
        return Collision(normal = -outward, depth = circle.radius - distance)
    }

    // Circle center is inside the box: push out along the least-penetrated face.
    val d = circlePos - boxPos
    val overlapX = half.x - abs(d.x)
    val overlapY = half.y - abs(d.y)

    return if (overlapX < overlapY) {
        val outwardX = if (d.x < 0f) -1f else 1f
        Collision(normal = Vec2(-outwardX, 0f), depth = overlapX + circle.radius)
    } else {
        val outwardY = if (d.y < 0f) -1f else 1f
        Collision(normal = Vec2(0f, -outwardY), depth = overlapY + circle.radius)
    }
}
