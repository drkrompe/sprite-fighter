package logic.unitLogic.logics

import logic.calculations.Distance
import shared.resources.Teams
import things.Entity
import things.toCopy
import java.util.*
import shared.resources.Entity as SharedEntity

interface TargetingThreadSafe : Distance {

    fun findNearestNonDeadTarget(self: Entity): UUID? {
        return distanceFromSelfToEnemyEntities(self)
                .minBy { it.value }
                ?.key
    }

    private fun distanceFromSelfToEnemyEntities(self: Entity): Map<UUID, Double> {
        val distancesToOthersMap = mutableMapOf<UUID, Double>()
        self.calculateDistancesToOthers(distancesToOthersMap)
        return distancesToOthersMap.toMap()
    }

    private fun Entity.calculateDistancesToOthers(distancesToOthersMap: MutableMap<UUID, Double>) {
        Teams.getOtherTeam(this.team)?.run {
            this.entityList.getList().map { otherTeamEntity ->

                otherTeamEntity.lock.acquire()
                val otherEntity = toCopy(otherTeamEntity)
                otherTeamEntity.lock.release()

                if (otherEntity.isNotDead()) {
                    val distanceToOther = distanceBetween(this@calculateDistancesToOthers.body.location, otherEntity.body.location)
                    distancesToOthersMap[otherEntity.id] = distanceToOther
                }
            }
        }
    }

    private fun Entity.isNotDead(): Boolean {
        return !this.dead
    }
}