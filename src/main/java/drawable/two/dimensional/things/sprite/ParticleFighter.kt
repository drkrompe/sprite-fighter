package drawable.two.dimensional.things.sprite

import drawable.two.dimensional.things.Draw
import things.MasterListOfThings
import things.sprite.ParticleFighter
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Point

object ParticleFighter : Draw {
    val dimension = Dimension(10, 10)
    val draw: (Graphics?, location: Point) -> Unit = { g, location -> g?.fillOval(location.x, location.y, dimension.width, dimension.height) }


    val drawAllParticleFighters: (g: Graphics?) -> Unit = { g ->
        MasterListOfThings.list.map {
            when (it.body) {
                is ParticleFighter -> {
                    when (it.dead) {
                        true -> g?.color = Color.red
                        false -> g?.color = Color.white
                    }
                    draw(g, it.body.location)
                }
            }
        }
    }
}