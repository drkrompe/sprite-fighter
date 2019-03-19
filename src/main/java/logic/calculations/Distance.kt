package logic.calculations

import jdk.nashorn.internal.runtime.JSType
import java.awt.Point

interface Distance {

    fun distanceBetween(d1: Point, d2: Point): Double {
        return Math.sqrt(((JSType.toDouble(d1.x) - JSType.toDouble(d2.x)) * (JSType.toDouble(d1.x) - JSType.toDouble(d2.x)))
                + ((JSType.toDouble(d1.y) - JSType.toDouble(d2.y)) * ((JSType.toDouble(d1.y) - JSType.toDouble(d2.y)))))
    }

}