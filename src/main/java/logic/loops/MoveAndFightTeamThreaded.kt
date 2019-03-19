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

interface MoveAndFightTeamThreaded : LoopBehaviorThreaded, Attacking, Movement, TargetingThreadSafe {

    override fun loopCycle(team: Int) {
        Teams.getTeam(team).entityList.getList().map {
            it.lock.acquire()
            println("\t\t\tTeam<$team> acquiredLock")
            val copiedEntity = toCopy(it)
            it.lock.release()
            println("\t\t\tTeam<$team> releasedLock")

            if (!copiedEntity.dead) {
                println("\t\t\tTeam<$team> notDead")
                orderOfOperations(copiedEntity, team)
            }
        }
    }

    private fun orderOfOperations(copiedSelf: Entity, team: Int) {
        when (copiedSelf.soul) {
            is ParticleFighterUnitLogic -> {
                println("\t\t\t\tParticleFighterUnitLogic")

                copiedSelf.soul.updateCurrentTargetIfRequired(copiedSelf)
                updateSelf(copiedSelf)

                val copiedOther = Teams.findOtherEntityByUUID(copiedSelf.soul.currentTargetId, team)
                takeActionInRelationToOther(copiedSelf, copiedOther, copiedSelf.soul)
            }
        }
    }

    private fun ParticleFighterUnitLogic.updateCurrentTargetIfRequired(self: Entity): ParticleFighterUnitLogic {
        when (this.currentTargetId) {
            is UUID -> {
                val otherEntityCopy = Teams.findOtherEntityByUUID(otherId = this.currentTargetId, selfTeamId = self.team)
                when (otherEntityCopy?.dead) {
                    true -> {
                        this.currentTargetId = findNearestNonDeadTarget(self = self)
                    }
                }
            }
            else -> {
                this.currentTargetId = findNearestNonDeadTarget(self = self)
            }
        }
        return this
    }

    private fun takeActionInRelationToOther(copiedSelf: Entity, copiedOther: Entity?, soul: ParticleFighterUnitLogic) {
        when (copiedSelf.soul.determineNextAction(copiedSelf.team)) {
            is UnitAction.MoveAction -> {
                println("\t\t\t\tMove Action")
                copiedSelf.body.location = determineNextLocation(self = copiedSelf.body, target = copiedOther?.body)
                soul.particleFighter = (copiedSelf.body as ParticleFighter)
                updateSelf(copiedSelf)
            }
            is UnitAction.AttackAction -> {
                println("\t\t\t\tAttack Action")
                attack(copiedOther, 1)
                updateOther(copiedOther)
            }
        }
    }

    private fun updateOther(other: Entity?) {
        when (other) {
            is Entity -> {
                Teams.getTeam(other.team).entityList.updateEntity(other.id, other)
            }
        }
    }

    private fun updateSelf(self: Entity) {
        Teams.getTeam(self.team).entityList.updateEntity(self.id, self)
    }
}