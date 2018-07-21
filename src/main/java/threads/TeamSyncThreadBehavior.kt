package threads

import logic.loops.MoveAndFight
import logic.loops.MoveAndFightTeamThreaded
import properties.TeamsProperties

object TeamSyncThreadBehavior : Runnable{
    override fun run() {
        val threadList = mutableListOf<Thread>()

        while(true){
            TeamSyncSemaphore.acquire(TeamsProperties.numberOfTeams)
            threadList.removeAll(threadList)
            for (i in 0 until TeamsProperties.numberOfTeams){
                threadList.add(Thread(TeamThreadBehavior(i, MoveAndFightTeamThreaded), "TeamThreadBehavior$i"))
                threadList[i].start()
            }
        }
    }
}