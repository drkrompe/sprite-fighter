package logic.unitLogic

sealed class UnitStatus {
    object Moving : UnitStatus()
    object Attacking : UnitStatus()
    object Dead : UnitStatus()
}