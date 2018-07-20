package logic.unitLogic.units

import logic.unitLogic.UnitAction

interface UnitLogic {
    fun determineNextAction() : UnitAction
}