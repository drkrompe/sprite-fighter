package logic.unitLogic.units

import jdk.nashorn.internal.runtime.JSType.toDouble
import logic.calculations.Distance
import logic.unitLogic.UnitAction
import logic.unitLogic.UnitStatus
import shared.resources.Teams
import things.Thing
import things.sprite.ParticleFighter
import java.awt.Point
import java.lang.Math.sqrt
import java.util.*

class ParticleFighterUnitLogic(var particleFighter: ParticleFighter) : UnitLogic, Distance {
    var currentTargetId: UUID? = null

    private val range = sqrt(square(particleFighter.dimension.width) + square(particleFighter.dimension.height)) + 1

    private fun square(x: Int): Double {
        return toDouble(x) * toDouble(x)
    }

    override fun determineNextAction(team: Int): UnitAction {
        if (particleFighter.status is UnitStatus.Dead) {
            return UnitAction.NoAction
        }
        return when (isNowInAttackRangeOfEnemy(team)) {
            true -> UnitAction.AttackAction
            false -> UnitAction.MoveAction
        }
    }

    private fun isNowInAttackRangeOfEnemy(team: Int): Boolean {
        val tarId = currentTargetId ?: return false
        val otherEntitiesBody = Teams.findOtherEntityByUUID(tarId, team)?.body ?: return false

        val selfLocation = particleFighter.toCoordinatePoint()
        val othersLocation = otherEntitiesBody.toCoordinatePoint()

        return distanceBetween(selfLocation, othersLocation).checkIsInRange()
    }

    private fun Double.checkIsInRange(): Boolean {
        return this < toDouble(range)
    }

    private fun Thing.toCoordinatePoint(): Point {
        return Point(this.location.x + (this.dimension.width / 2), this.location.y + (this.dimension.height / 2))
    }

}