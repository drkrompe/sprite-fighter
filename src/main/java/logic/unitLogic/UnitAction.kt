package logic.unitLogic

sealed class UnitAction {
    object AttackAction : UnitAction()
    object MoveAction : UnitAction()
    object NoAction : UnitAction()
}