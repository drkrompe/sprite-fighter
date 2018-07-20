package things

import java.awt.Dimension
import java.awt.Point

interface Thing {
    var location: Point
    val dimension: Dimension
    var nextLocation: Point
    var health: Int
}