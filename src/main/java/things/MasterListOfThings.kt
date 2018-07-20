package things

import logic.unitLogic.units.UnitLogic
import java.util.*

object MasterListOfThings {
    val list = LinkedList<Entity>()

    fun find(uuid: UUID?): Entity? {
        return list.find { it.id == uuid }
    }

    fun remove(uuid: UUID?) {
        //TODO: Currently not thread safe
        list.removeIf { it.id == uuid }
    }
}

class Entity(val body: Thing, val soul: UnitLogic, var dead: Boolean = false, val id: UUID = UUID.randomUUID())