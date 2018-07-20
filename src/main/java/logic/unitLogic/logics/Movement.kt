package logic.unitLogic.logics

import things.Thing
import java.awt.Point
import java.lang.Math.abs

object Movement {

    fun determineNextLocation(self: Thing, target: Thing?): Point {
        self.nextLocation = Point(self.location.x, self.location.y)
        if (target == null) {
            return self.location
        }

        val xSlope = target.location.x - self.location.x
        val ySlope = target.location.y - self.location.y
        self.nextLocation = someDistanceLogic(xSlope = xSlope, ySlope = ySlope, self = self)
        val checkedPoint = collisionCheck(self = self, target = target)

        return when (self.nextLocation == checkedPoint) {
            true -> self.nextLocation
            false -> {checkedPoint}
        }
    }

    private fun collisionCheck(self: Thing, target: Thing?): Point {
        val ponderedPoint = Point(self.nextLocation.x, self.nextLocation.y)
        if (target == null) {
            return self.nextLocation
        }
        // check collision right/left
        when (ponderedPoint.x != self.location.x) {
            true -> {
                when (ponderedPoint.x - self.location.x) {
                    1 -> {
                        when (ponderedPoint.x + self.dimension.width > target.location.x) {
                            true -> ponderedPoint.x = self.location.x
                        }
                    }
                    -1 -> {
                        when (ponderedPoint.x < target.location.x + target.dimension.width) {
                            true -> ponderedPoint.x = self.location.x
                        }
                    }
                }
            }
        }
        // check collision down/up
        when (ponderedPoint.y != self.location.y) {
            true -> {
                when (ponderedPoint.y - self.location.y) {
                    1 -> {
                        when (ponderedPoint.y + self.dimension.height > target.location.y) {
                            true -> ponderedPoint.y = self.location.y
                        }
                    }
                    -1 -> {
                        when (ponderedPoint.y < target.location.y + target.dimension.height) {
                            true -> ponderedPoint.y = self.location.y
                        }
                    }
                }
            }
        }
        return ponderedPoint
    }

    private fun someDistanceLogic(xSlope: Int, ySlope: Int, self: Thing): Point {
        val checkedPoint = Point(self.location.x, self.location.y)
        if (abs(xSlope) > abs(ySlope)) {
            when (xSlope > 0) {
                true -> checkedPoint.x++
                false -> checkedPoint.x--
            }
        } else if (abs(ySlope) > abs(xSlope)) {
            when (ySlope > 0) {
                true -> checkedPoint.y++
                false -> checkedPoint.y--
            }
        } else if (xSlope == ySlope && xSlope != 0) {
            if (ySlope > 0) {
                checkedPoint.y++
                checkedPoint.x++
            } else {
                checkedPoint.y--
                checkedPoint.x--
            }
        } else if (abs(xSlope) == abs(ySlope) && xSlope != 0) {
            if (xSlope > 0 && ySlope < 0) {
                checkedPoint.y--
                checkedPoint.x++
            } else {
                checkedPoint.y++
                checkedPoint.x--
            }
        }
        return checkedPoint
    }

}