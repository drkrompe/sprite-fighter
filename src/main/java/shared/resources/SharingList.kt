package shared.resources

import java.util.*

class SharingList {
    private val list = mutableListOf<Entity>()

    fun getEntity(id: UUID): Entity? {
        return list.find { it.id == id }
    }

    fun updateEntity(id: UUID, copiedEntity: things.Entity) {
        val entity = getEntity(id)
        when (entity) {
            null -> {
            }
            else -> {
                entity.lock.acquire()
                shared.resources.updateEntity(entity, copiedEntity)
                entity.lock.release()
            }
        }
    }

    fun addEntity(entity: Entity): UUID {
        list.add(entity)
        return entity.id
    }

    fun removeEntity(id: UUID) {
        val entity = getEntity(id)
        entity?.lock?.acquire()
        list.remove(entity)
    }

    fun getList(): List<Entity> {
        return list
    }
}