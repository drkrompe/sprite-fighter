package logic.unitLogic.logics

import jdk.nashorn.internal.runtime.JSType
import shared.resources.Team
import shared.resources.Teams
import things.Entity
import things.toCopy
import shared.resources.Entity as SharedEntity
import java.awt.Point
import java.util.*

object TargetingThreadSafe {

    fun findNearestNonDeadTarget(self: Entity, team: Int): UUID? {
        val mapPositionToDistance = mutableMapOf<UUID, Double>()
        val otherTeam = Teams.getOther(team)
        when(otherTeam){
            is Team -> {
                otherTeam.list.getList().map {
                    it.lock.acquire()
                    val copiedOtherEntity = toCopy(it)
                    it.lock.release()
                    when(copiedOtherEntity.dead){
                        false -> {
                            mapPositionToDistance[copiedOtherEntity.id] = dist(self.body.location, copiedOtherEntity.body.location)
                        }
                    }
                }
            }
        }
        val minPair = mapPositionToDistance.minBy { it.value }
        return minPair?.key
    }

    private val dist = { d1: Point, d2: Point ->
        Math.sqrt(((JSType.toDouble(d1.x) - JSType.toDouble(d2.x)) * (JSType.toDouble(d1.x) - JSType.toDouble(d2.x)))
                + ((JSType.toDouble(d1.y) - JSType.toDouble(d2.y)) * ((JSType.toDouble(d1.y) - JSType.toDouble(d2.y)))))
    }

}