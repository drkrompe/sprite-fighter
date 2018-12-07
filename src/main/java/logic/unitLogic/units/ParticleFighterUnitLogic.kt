package logic.unitLogic.units

import jdk.nashorn.internal.runtime.JSType.toDouble
import logic.unitLogic.UnitAction
import logic.unitLogic.UnitStatus
import logic.unitLogic.logics.TargetingThreadSafe.dist
import shared.resources.Teams
import things.Thing
import things.sprite.ParticleFighter
import java.awt.Point
import java.lang.Math.sqrt
import java.util.*

class ParticleFighterUnitLogic(var particleFighter: ParticleFighter) : UnitLogic {
    var currentTargetId: UUID? = null
    val range = sqrt(square(particleFighter.dimension.width) + square(particleFighter.dimension.height)) + 1

    private fun square(x: Int): Double {
        return toDouble(x) * toDouble(x)
    }

    override fun determineNextAction(team: Int): UnitAction {
        return when (particleFighter.statusCompare(UnitStatus.Dead())) {
            true -> UnitAction.NoAction()
            else -> {
                when (isInAttackRange(team)) {
                    true -> UnitAction.AttackAction()
                    false -> UnitAction.MoveAction()
                }
            }
        }
    }

    fun isInAttackRange(team: Int): Boolean {
        val tarId = currentTargetId
        return when (tarId) {
            null -> false
            else -> {
                val copyOther = Teams.findOtherEntityAndMakeCopy(tarId, team)?.body
                when (copyOther) {
                    is Thing -> {
                        val centerOfMe = Point(particleFighter.location.x + (particleFighter.dimension.width / 2), particleFighter.location.y + (particleFighter.dimension.height / 2))
                        val centerOfOther = Point(copyOther.location.x + (copyOther.dimension.width / 2), copyOther.location.y + (copyOther.dimension.height / 2))
                        val distance = dist(centerOfMe, centerOfOther)
                        when (distance < toDouble(range)) {
                            true -> true
                            false -> false
                        }
                    }
                    else -> {
                        return false
                    }
                }
            }
        }
    }

}