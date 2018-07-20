package logic.unitLogic.logics

import jdk.nashorn.internal.runtime.JSType
import things.Entity
import java.awt.Point
import java.util.*

object Targeting {

    fun findNearestNonDeadTarget(self: Entity, listOfOthers: List<Entity>): UUID? {
        val mapPositionToDistance = mutableMapOf<UUID, Double>()
        listOfOthers.map { other: Entity ->
            when(other.id != self.id){
                true -> {
                    when(other.dead){
                        false -> {
                            mapPositionToDistance[other.id] = dist(self.body.location, other.body.location)
                        }
                    }
                }
            }
        }
        val minPair = mapPositionToDistance.minBy { it.value }
        return minPair?.key
    }

    val dist = { d1: Point, d2: Point ->
        Math.sqrt(((JSType.toDouble(d1.x) - JSType.toDouble(d2.x)) * (JSType.toDouble(d1.x) - JSType.toDouble(d2.x)))
                + ((JSType.toDouble(d1.y) - JSType.toDouble(d2.y)) * ((JSType.toDouble(d1.y) - JSType.toDouble(d2.y)))))
    }

}