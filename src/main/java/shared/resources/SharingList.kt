package shared.resources

import java.util.*

class SharingList {
    private val list = mutableListOf<Entity>()

    private fun getEntity(id: UUID): Entity? {
        return list.find { it.id == id }
    }

    fun updateEntity(id: UUID, copiedEntity: things.Entity) {
        getEntity(id)?.run {
            println("\t\t\t\tUpdatingEntity")
            shared.resources.updateEntity(this, copiedEntity)
            println("\t\t\t\tUpdatingEntity - finish")
        }
    }

    fun addEntity(entity: Entity): UUID {
        list.add(entity)
        return entity.id
    }

    fun removeEntity(id: UUID) {
        val entity = getEntity(id)
        entity?.lock?.acquire().run {
            list.remove(entity)
        }
    }

    fun getList(): List<Entity> {
        return list
    }
}