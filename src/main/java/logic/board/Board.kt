package logic.board

import common.BoardProperties
import java.awt.Point


class Board {

    fun relativeToDrawPosition(gameLocation: Point): Point {
        return Point(gameLocation.x * BoardProperties.pixelsPerUnitSpace, gameLocation.y * BoardProperties.pixelsPerUnitSpace)
    }

}