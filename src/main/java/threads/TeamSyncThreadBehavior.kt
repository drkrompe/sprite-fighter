package threads

import logic.loops.MoveAndFightTeamThreaded
import properties.TeamsProperties

object TeamSyncThreadBehavior : Runnable {
    override fun run() {
        val threadList = mutableListOf<Thread>()
        println("\tSync Thread")
        while (true) {
            Thread.sleep(100)
            TeamSyncSemaphore.acquire(TeamsProperties.numberOfTeams)
            println("\tSync Thread - acquiredSyncLock")
            threadList.removeAll(threadList)
            for (i in 0 until TeamsProperties.numberOfTeams) {
                threadList.add(Thread(TeamThreadBehavior(i, MoveAndFightTeamThreaded), "TeamThreadBehavior$i"))
                threadList[i].start()
            }
        }
    }
}