package threads

class TeamThreadBehavior(private val i: Int) : Runnable {
    override fun run() {
        Thread.sleep(2000)
        println("\tChild $i release")
        TeamSyncSemaphore.release()
    }
}