package logic.unitLogic

sealed class UnitAction {
    class AttackAction : UnitAction()
    class MoveAction : UnitAction()
    class NoAction: UnitAction()
}