package shared.resources

import drawable.two.dimensional.things.Draw
import logic.unitLogic.units.ParticleFighterUnitLogic
import logic.unitLogic.units.UnitLogic
import things.Thing
import java.util.*
import java.util.concurrent.Semaphore
import things.Entity as CopiedEntity

data class Entity(
        val body: Thing,
        val soul: UnitLogic,
        val draw: Draw,
        val team: Int,
        var dead: Boolean = false,
        val id: UUID = UUID.randomUUID(),
        val lock: Semaphore = Semaphore(1)
)

fun updateEntity(entity: Entity, copiedEntity: CopiedEntity) {
    entity.lock.acquire()
    entity.body.location = copiedEntity.body.location
    entity.body.health = copiedEntity.body.health
    entity.body.nextLocation = copiedEntity.body.nextLocation
    entity.dead = copiedEntity.dead
    when (entity.soul) {
        is ParticleFighterUnitLogic -> {
            when (copiedEntity.soul) {
                is ParticleFighterUnitLogic -> {
                    entity.soul.currentTargetId = copiedEntity.soul.currentTargetId
                }
            }
        }
    }
    entity.lock.release()
}