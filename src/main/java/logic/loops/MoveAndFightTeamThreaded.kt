package logic.loops

import logic.unitLogic.UnitAction
import logic.unitLogic.logics.Attacking
import logic.unitLogic.logics.Movement
import logic.unitLogic.logics.TargetingThreadSafe
import logic.unitLogic.units.ParticleFighterUnitLogic
import shared.resources.Teams
import things.Entity
import things.sprite.ParticleFighter
import things.toCopy
import java.util.*

object MoveAndFightTeamThreaded : LoopBehaviorThreaded {

    override fun loopCycle(team: Int) {
        Teams.getTeam(team).list.getList().map {
            it.lock.acquire()
            val copiedEntity = toCopy(it)
            it.lock.release()
            when (copiedEntity.dead) {
                false -> orderOfOperations(copiedEntity, team)
            }
        }
    }

    private fun orderOfOperations(copiedSelf: Entity, team: Int) {
        when (copiedSelf.soul) {
            is ParticleFighterUnitLogic -> {
                val copiedCurrentTargetId = copiedSelf.soul.currentTargetId
                when (copiedCurrentTargetId) {
                    is UUID -> {
                        when (Teams.findOtherEntityCopy(otherId = copiedCurrentTargetId, selfTeam = team)?.dead) {
                            true -> {
                                copiedSelf.soul.currentTargetId = TargetingThreadSafe.findNearestNonDeadTarget(self = copiedSelf, team = team)
                                updateSelf(copiedSelf)
                            }
                        }
                    }
                    else -> {
                        copiedSelf.soul.currentTargetId = TargetingThreadSafe.findNearestNonDeadTarget(self = copiedSelf, team = team)
                        updateSelf(copiedSelf)
                    }
                }
                val copiedOther = Teams.findOtherEntityCopy(copiedCurrentTargetId, team)
                when (copiedSelf.soul.determineNextAction()) {
                    is UnitAction.MoveAction -> {
                        copiedSelf.body.location = Movement.determineNextLocation(self = copiedSelf.body, target = copiedOther?.body)
                        copiedSelf.soul.particleFighter = (copiedSelf.body as ParticleFighter)
                        updateSelf(copiedSelf)
                    }
                    is UnitAction.AttackAction -> {
                        Attacking.attack(copiedOther, 1)
                        updateOther(copiedOther)
                    }
                }
            }
        }
    }

    private fun updateOther(other: Entity?) {
        when (other) {
            is Entity -> {
                Teams.getTeam(other.team).list.updateEntity(other.id, other)
            }
        }
    }

    private fun updateSelf(self: Entity) {
        Teams.getTeam(self.team).list.updateEntity(self.id, self)
    }
}