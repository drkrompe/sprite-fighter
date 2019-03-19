package things.sprite

import logic.unitLogic.UnitStatus
import logic.unitLogic.units.ParticleFighterUnitLogic
import logic.unitLogic.units.UnitLogic
import things.Thing
import java.awt.Dimension
import java.awt.Point

class ParticleFighter(override var location: Point, override val dimension: Dimension) : Thing {
    val unitLogic: UnitLogic = ParticleFighterUnitLogic(this)
    override var health: Int = 100
    override var nextLocation: Point = Point()
    var status: UnitStatus = UnitStatus.Moving
}