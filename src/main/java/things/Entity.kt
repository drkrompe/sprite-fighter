package things

import logic.unitLogic.units.UnitLogic
import java.util.*
import shared.resources.Entity as SharedEntity

data class Entity(
        val body: Thing,
        val soul: UnitLogic,
        var dead: Boolean = false,
        val team: Int,
        val id: UUID = UUID.randomUUID()
)

fun toCopy(entity: SharedEntity): Entity {
    return Entity(
            body = entity.body,
            soul = entity.soul,
            dead = entity.dead,
            id = entity.id,
            team = entity.team
    )
}
