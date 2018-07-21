package threads

import logic.loops.LoopBehaviorThreaded
import shared.resources.Teams

class TeamThreadBehavior(private val team: Int, private val loopBehavior: LoopBehaviorThreaded) : Runnable {

    val debug: Boolean = true

    override fun run() {
        tickAction()
        println("Team<$team> tick done -> release")
        TeamSyncSemaphore.release()
    }

    private fun tickAction() {
        Teams.getTeam(which = team).list.getList().map {
            doLoopCycleBehavior()
        }
    }

    private fun doLoopCycleBehavior() {
        loopBehavior.loopCycle(team = team)
    }
}