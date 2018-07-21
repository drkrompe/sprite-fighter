import properties.ScenerioProperties
import drawable.two.dimensional.background.BackgroundDefault
import drawable.two.dimensional.things.sprite.ImageFighter
import logic.loops.MoveAndFight
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
        val testMulti = Thread(TeamSyncThreadBehavior, "Master").start()
        while (true) {
            repaint()
            MoveAndFight.loop()
        }
    }

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        BackgroundDefault.draw(g)
        ImageFighter.drawAllImageFighters(g)
    }

}