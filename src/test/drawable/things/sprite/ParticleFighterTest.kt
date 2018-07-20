package drawable.things.sprite

import common.FrameProperties
import common.PanelProperties
import drawable.background.BackgroundDefault
import org.junit.Test
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Point
import javax.swing.JFrame
import javax.swing.JPanel

class ParticleFighterTest {

    @Test
    fun `when panel added to frame will show the particlefighter as being drawn`() {
        val panel = HelperPanel(ParticleFighter.draw)
        val frame = HelperFrame(panel)

        Thread.sleep(6000)
    }

}

class HelperPanel(val funct: (Graphics?, location: Point) -> Unit) : JPanel() {
    init {
        isVisible = true
    }

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)
        BackgroundDefault.draw(g)
        g?.color = Color.white
        funct(g, Point(10, 10))
    }
}

class HelperFrame(val panel: JPanel) : JFrame() {
    init {
        title = FrameProperties.title
        size = Dimension(FrameProperties.width, FrameProperties.height)
        isResizable = false
        setLocationRelativeTo(null)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        isVisible = true
        add(panel)
        println("Done")
    }
}