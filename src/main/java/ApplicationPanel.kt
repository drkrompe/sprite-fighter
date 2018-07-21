import properties.ScenerioProperties
import drawable.two.dimensional.background.BackgroundDefault
import drawable.two.dimensional.things.sprite.ImageFighter
import drawable.two.dimensional.things.sprite.ParticleFighter
import shared.resources.Teams
import things.toCopy
import threads.TeamSyncThreadBehavior
import java.awt.Graphics
import javax.swing.JPanel

class ApplicationPanel : JPanel(), Runnable {
    val threadLoop = Thread(this, "Game-Thread")

    init {
        isVisible = true
        threadLoop.start()
    }

    override fun run() {
        ScenerioProperties.setup()
        Thread(TeamSyncThreadBehavior, "Master-TeamSync-Thread").start()
        println("Created master thread")
        while(true){
            repaint()
        }
    }

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        BackgroundDefault.draw(g)
        Teams.getTeams().map {
            it.list.getList().map {
                it.lock.acquire()
                val copiedEntity = toCopy(it)
                it.lock.release()
                when(copiedEntity.draw){
                    is ParticleFighter -> {
                        when(copiedEntity.dead){
                            true -> copiedEntity.draw.draw(g, copiedEntity.body.location, copiedEntity.dead, copiedEntity.team)
                        }
                    }
                }
            }
        }
        Teams.getTeams().map {
            it.list.getList().map {
                it.lock.acquire()
                val copiedEntity = toCopy(it)
                it.lock.release()
                when(copiedEntity.draw){
                    is ParticleFighter -> {
                        when(copiedEntity.dead){
                            false -> copiedEntity.draw.draw(g, copiedEntity.body.location, copiedEntity.dead, copiedEntity.team)
                        }
                    }
                }
            }
        }
    }

}