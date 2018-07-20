package logic.unitLogic

sealed class UnitStatus {
    class Moving : UnitStatus()
    class Attacking : UnitStatus()
    class Dead : UnitStatus()
}