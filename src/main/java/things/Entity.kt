package things

import drawable.two.dimensional.things.Draw
import logic.unitLogic.units.UnitLogic
import java.util.*
import shared.resources.Entity as SharedEntity

data class Entity(
        val body: Thing,
        val soul: UnitLogic,
        val draw: Draw,
        var dead: Boolean = false,
        val team: Int,
        val id: UUID = UUID.randomUUID()
)

fun toCopy(entity: SharedEntity): Entity {
    return Entity(
            body = entity.body,
            soul = entity.soul,
            draw = entity.draw,
            dead = entity.dead,
            id = entity.id,
            team = entity.team
    )
}
