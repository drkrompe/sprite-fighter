import common.FrameProperties
import java.awt.Dimension
import javax.swing.JFrame

class ApplicationFrame : JFrame(){
    init{
        title = FrameProperties.title
        size = Dimension(FrameProperties.width, FrameProperties.height)
        isResizable = false
        setLocationRelativeTo(null)
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        isVisible = true
        add(ApplicationPanel())
        println("Done")
    }
}