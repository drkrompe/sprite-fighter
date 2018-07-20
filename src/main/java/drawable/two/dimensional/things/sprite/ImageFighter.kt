package drawable.two.dimensional.things.sprite

import things.MasterListOfThings
import things.sprite.ParticleFighter
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Image
import java.awt.Point
import java.io.File
import javax.imageio.ImageIO

object ImageFighter {
    val dimension = Dimension(30, 30)
    val warriorAlive: Image = ImageIO.read(File("E:\\ideaWorkspace\\sprite-fighter\\resources\\warrior.png"))
    val warriorDead: Image = ImageIO.read( File("E:\\ideaWorkspace\\sprite-fighter\\resources\\warriordead.png"))

//    fun setup(){
//        warriorAlive = ImageIO.read(File("E:\\ideaWorkspace\\sprite-fighter\\resources\\warrior.png"))
//        warriorDead = ImageIO.read( File("E:\\ideaWorkspace\\sprite-fighter\\resources\\warriordead.png"))
//    }

    val drawAlive: (Graphics?, Point) -> Unit = { g, p -> g?.drawImage(warriorAlive, p.x, p.y, dimension.width, dimension.height, null) }
    val drawDead: (Graphics?, Point) -> Unit = { g, p -> g?.drawImage(warriorDead, p.x, p.y, dimension.width, dimension.height, null) }

    val drawAllParticleFighters: (g: Graphics?) -> Unit = { g ->
        MasterListOfThings.list.map {
            when (it.body) {
                is ParticleFighter -> {
                    when (it.dead) {
                        true -> drawDead(g, it.body.location)
                        false -> drawAlive(g, it.body.location)
                    }
                }
            }
        }
    }
}