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
            println("\t\t\tTeam<$team> acquiredLock")
            val copiedEntity = toCopy(it)
            it.lock.release()
            println("\t\t\tTeam<$team> releasedLock")
            when (copiedEntity.dead) {
                false -> {
                    println("\t\t\tTeam<$team> notDead")
                    orderOfOperations(copiedEntity, team)
                }
            }
        }
    }

    private fun orderOfOperations(copiedSelf: Entity, team: Int) {
        when (copiedSelf.soul) {
            is ParticleFighterUnitLogic -> {
                println("\t\t\t\tParticleFighterUnitLogic")
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
                when (copiedSelf.soul.determineNextAction(copiedSelf.team)) {
                    is UnitAction.MoveAction -> {
                        println("\t\t\t\tMove Action")
                        copiedSelf.body.location = Movement.determineNextLocation(self = copiedSelf.body, target = copiedOther?.body)
                        copiedSelf.soul.particleFighter = (copiedSelf.body as ParticleFighter)
                        updateSelf(copiedSelf)
                    }
                    is UnitAction.AttackAction -> {
                        println("\t\t\t\tAttack Action")
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