package drawable.two.dimensional.things.sprite

import drawable.two.dimensional.things.Draw
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Point

object ParticleFighter : Draw {
    val dimension = Dimension(10, 10)

    val draw: (Graphics?, location: Point, dead: Boolean, team: Int) -> Unit = { g, location, dead, team ->
        run {
            when (dead) {
                true -> g?.color = Color.RED
                false -> {
                    when(team){
                        0 -> g?.color = Color.cyan
                        1 -> g?.color = Color.green
                        else -> g?.color = Color.BLACK
                    }
                }
            }
            g?.fillOval(location.x, location.y, dimension.width, dimension.height)
        }
    }
}