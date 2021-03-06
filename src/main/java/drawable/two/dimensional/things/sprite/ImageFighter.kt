package drawable.two.dimensional.things.sprite

import drawable.two.dimensional.things.Draw
import things.sprite.ParticleFighter
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Image
import java.awt.Point
import java.io.File
import javax.imageio.ImageIO

object ImageFighter : Draw {
    val dimension = Dimension(30, 30)
    private val warriorAlive: Image = ImageIO.read(File("E:\\ideaWorkspace\\sprite-fighter\\resources\\warrior.png"))
    private val warriorDead: Image = ImageIO.read(File("E:\\ideaWorkspace\\sprite-fighter\\resources\\warriordead.png"))

    private val drawAlive: (Graphics?, Point) -> Unit = { g, p -> g?.drawImage(warriorAlive, p.x, p.y, dimension.width, dimension.height, null) }
    private val drawDead: (Graphics?, Point) -> Unit = { g, p -> g?.drawImage(warriorDead, p.x, p.y, dimension.width, dimension.height, null) }
}