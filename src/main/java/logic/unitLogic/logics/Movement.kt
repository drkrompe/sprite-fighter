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
        return (self to self.naiveNextLocation(xSlope = xSlope, ySlope = ySlope))
                .ifCollisionWithTargetReturnCorrectedLocation(target)
    }

    private fun Pair<Thing, Point>.ifCollisionWithTargetReturnCorrectedLocation(target: Thing?): Point {
        val ponderedPoint = Point(second.x, second.y)
        if (target == null) {
            return second
        }
        // check collision right/left
        if (ponderedPoint.x != first.location.x) {
            when (ponderedPoint.x - first.location.x) {
                1 ->
                    if (ponderedPoint.x + first.dimension.width > target.location.x) {
                        ponderedPoint.x = first.location.x
                    }
                -1 ->
                    if (ponderedPoint.x < target.location.x + target.dimension.width) {
                        ponderedPoint.x = first.location.x
                    }

            }
        }
        // check collision down/up
        if (ponderedPoint.y != first.location.y) {
            when (ponderedPoint.y - first.location.y) {
                1 ->
                    if (ponderedPoint.y + first.dimension.height > target.location.y) {
                        ponderedPoint.y = first.location.y
                    }
                -1 ->
                    if (ponderedPoint.y < target.location.y + target.dimension.height) {
                        ponderedPoint.y = first.location.y
                    }
            }
        }
        return ponderedPoint
    }

    private fun Thing.naiveNextLocation(xSlope: Int, ySlope: Int): Point {
        val checkedPoint = Point(location.x, location.y)
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
            when (ySlope > 0) {
                true -> {
                    checkedPoint.y++
                    checkedPoint.x++
                }
                false -> {
                    checkedPoint.y--
                    checkedPoint.x--
                }
            }
        } else if (abs(xSlope) == abs(ySlope) && xSlope != 0) {
            when (xSlope > 0 && ySlope < 0) {
                true -> {
                    checkedPoint.y--
                    checkedPoint.x++
                }
                false -> {
                    checkedPoint.y++
                    checkedPoint.x--
                }
            }
        }
        return checkedPoint
    }

}