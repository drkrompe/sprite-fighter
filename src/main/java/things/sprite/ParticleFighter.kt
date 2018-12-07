package things.sprite

import logic.unitLogic.units.ParticleFighterUnitLogic
import logic.unitLogic.units.UnitLogic
import logic.unitLogic.UnitStatus
import things.Thing
import java.awt.Dimension
import java.awt.Point

class ParticleFighter(override var location: Point, override val dimension: Dimension) : Thing {
    override var health: Int = 100
    override var nextLocation: Point = Point()
    val unitLogic: UnitLogic = ParticleFighterUnitLogic(this)
    private var status = UnitStatus.Moving()

    fun statusCompare(other: UnitStatus): Boolean {
        return (this.status.javaClass) == other.javaClass
    }
}