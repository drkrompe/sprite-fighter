package logic.unitLogic.logics

import jdk.nashorn.internal.runtime.JSType
import shared.resources.Teams
import things.Entity
import things.toCopy
import java.awt.Point
import java.util.*
import shared.resources.Entity as SharedEntity

object TargetingThreadSafe {

    fun findNearestNonDeadTarget(self: Entity, selfTeam: Int): UUID? {
        return distanceFromSelfToOpposingTeamMembers(self, selfTeam)
                .minBy { it.value }
                ?.key
    }

    private fun distanceFromSelfToOpposingTeamMembers(self: Entity, team: Int): Map<UUID, Double> {
        val selfToTargetsDistance = mutableMapOf<UUID, Double>()

        Teams.getOther(team)?.run {
            entityList.getList().map { otherTeamEntity ->

                otherTeamEntity.lock.acquire()
                val copiedOtherEntity = toCopy(otherTeamEntity)
                otherTeamEntity.lock.release()

                if (!copiedOtherEntity.dead) {
                    selfToTargetsDistance[copiedOtherEntity.id] = dist(self.body.location, copiedOtherEntity.body.location)
                }
            }
        }

        return selfToTargetsDistance.toMap()
    }

    val dist = { d1: Point, d2: Point ->
        Math.sqrt(((JSType.toDouble(d1.x) - JSType.toDouble(d2.x)) * (JSType.toDouble(d1.x) - JSType.toDouble(d2.x)))
                + ((JSType.toDouble(d1.y) - JSType.toDouble(d2.y)) * ((JSType.toDouble(d1.y) - JSType.toDouble(d2.y)))))
    }

}