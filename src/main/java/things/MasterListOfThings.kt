package things

import logic.unitLogic.units.UnitLogic
import java.util.*

object MasterListOfThings {
    val list = LinkedList<Entity>()

    fun find(uuid: UUID?): Entity? {
        return list.find { it.id == uuid }
    }
}