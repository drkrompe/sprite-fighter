package threads

import shared.resources.Teams

class TeamThreadBehavior(private val team: Int, private val loopBehavior: LoopBehaviorThreaded) : Runnable {

    override fun run() {
        println("\t\tTeam<$team>starting tick compute")
        tickAction()
        println("\t\tTeam<$team> tick done -> releaseSyncLock")
        TeamSyncSemaphore.release()
    }

    private fun tickAction() {
        Teams.getTeam(which = team).entityList.getList().map {
            doLoopCycleBehavior()
        }
    }

    private fun doLoopCycleBehavior() {
        loopBehavior.loopCycle(team = team)
    }
}