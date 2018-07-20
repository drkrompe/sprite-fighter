package logic.loops

import logic.unitLogic.logics.Movement
import logic.unitLogic.units.ParticleFighterUnitLogic
import logic.unitLogic.logics.Targeting
import logic.unitLogic.UnitAction
import things.Entity
import things.MasterListOfThings
import things.sprite.ParticleFighter

object MoveOnly {

    fun loop() {
        MasterListOfThings.list.map { entity -> orderOfOperations(entity) }
        Thread.sleep(20)
    }

    private fun orderOfOperations(entity: Entity) {
        when (entity.soul) {
            is ParticleFighterUnitLogic -> {
                when (entity.soul.currentTargetId) {
                    null -> entity.soul.currentTargetId = Targeting.findNearestNonDeadTarget(self = entity, listOfOthers = MasterListOfThings.list)
                    else -> {
                        when (MasterListOfThings.find(entity.soul.currentTargetId)) {
                            null -> entity.soul.currentTargetId = Targeting.findNearestNonDeadTarget(self = entity, listOfOthers = MasterListOfThings.list)
                        }
                    }
                }
                when (entity.soul.determineNextAction()) {
                    is UnitAction.MoveAction -> {
                        entity.body.location = Movement.determineNextLocation(self = entity.body, target = MasterListOfThings.find(entity.soul.currentTargetId)?.body)
                        entity.soul.particleFighter = (entity.body as ParticleFighter)
                    }
                    is UnitAction.AttackAction -> {

                    }
                }
            }
        }
    }
}