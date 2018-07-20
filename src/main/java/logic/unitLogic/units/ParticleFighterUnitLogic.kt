package logic.unitLogic.units

import jdk.nashorn.internal.runtime.JSType.toDouble
import logic.unitLogic.logics.Targeting.dist
import logic.unitLogic.UnitAction
import logic.unitLogic.UnitStatus
import things.MasterListOfThings
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

    override fun determineNextAction(): UnitAction {
        return when (particleFighter.statusCompare(UnitStatus.Dead())) {
            true -> UnitAction.NoAction()
            else -> {
                when (isInAttackRange()) {
                    true -> UnitAction.AttackAction()
                    false -> UnitAction.MoveAction()
                }
            }
        }
    }

    private fun isInAttackRange(): Boolean {
        val tarId = currentTargetId
        return when (tarId) {
            null -> false
            else -> {
                val other = MasterListOfThings.find(tarId)?.body
                when (other) {
                    null -> false
                    else -> {
                        val centerOfMe = Point(particleFighter.location.x + (particleFighter.dimension.width / 2), particleFighter.location.y + (particleFighter.dimension.height / 2))
                        val centerOfOther = Point(other.location.x + (other.dimension.width / 2), other.location.y + (other.dimension.height / 2))
                        val distance = dist(MasterListOfThings.find(tarId)?.body?.location
                                ?: Point(Int.MAX_VALUE, Int.MAX_VALUE), particleFighter.location)
                        when (distance < toDouble(range)) {
                            true -> true
                            false -> false
                        }
                    }
                }
            }
        }
    }

}