package logic.unitLogic

import logic.unitLogic.logics.Movement
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import things.sprite.ParticleFighter
import java.awt.Dimension
import java.awt.Point

class MovementTest {

    @Test
    fun `when target down then will move down`() {
        val selfLocation = Point(0,0)
        val targetLocation = Point(0,100)

        val dim = Dimension(10,10)
        val thing1 = ParticleFighter(selfLocation, dim)
        val thing2 = ParticleFighter(targetLocation, dim)

        val expectedNextLocation = Point(0,1)
        val determinedNextLocation = Movement.determineNextLocation(thing1, thing2)

        assertThat(determinedNextLocation).isEqualTo(expectedNextLocation)
    }

    @Test
    fun `when target up then will move up`() {
        val selfLocation = Point(0,100)
        val targetLocation = Point(0,0)

        val dim = Dimension(10,10)
        val thing1 = ParticleFighter(selfLocation, dim)
        val thing2 = ParticleFighter(targetLocation, dim)

        val expectedNextLocation = Point(0,99)
        val determinedNextLocation = Movement.determineNextLocation(thing1, thing2)

        assertThat(determinedNextLocation).isEqualTo(expectedNextLocation)
    }

    @Test
    fun `when target right then will move right`(){
        val selfLocation = Point(0,0)
        val targetLocation = Point(100,0)

        val dim = Dimension(10,10)
        val thing1 = ParticleFighter(selfLocation, dim)
        val thing2 = ParticleFighter(targetLocation, dim)

        val expectedNextLocation = Point(1,0)
        val determinedNextLocation = Movement.determineNextLocation(thing1, thing2)

        assertThat(determinedNextLocation).isEqualTo(expectedNextLocation)
    }

    @Test
    fun `when target left then will move left`(){
        val selfLocation = Point(100,0)
        val targetLocation = Point(0,0)

        val dim = Dimension(10,10)
        val thing1 = ParticleFighter(selfLocation, dim)
        val thing2 = ParticleFighter(targetLocation, dim)

        val expectedNextLocation = Point(99, 0)
        val determinedNextLocation = Movement.determineNextLocation(thing1, thing2)

        assertThat(determinedNextLocation).isEqualTo(expectedNextLocation)
    }

    @Test
    fun `when target bottomRight then will move bottomRight`(){
        val selfLocation = Point(0,0)
        val targetLocation = Point(100,100)

        val dim = Dimension(10,10)
        val thing1 = ParticleFighter(selfLocation, dim)
        val thing2 = ParticleFighter(targetLocation, dim)

        val expectedNextLocation = Point(1, 1)
        val determinedNextLocation = Movement.determineNextLocation(thing1, thing2)

        assertThat(determinedNextLocation).isEqualTo(expectedNextLocation)
    }

    @Test
    fun `when target topRight then will move topRight`(){
        val selfLocation = Point(0,100)
        val targetLocation = Point(100,0)

        val dim = Dimension(10,10)
        val thing1 = ParticleFighter(selfLocation, dim)
        val thing2 = ParticleFighter(targetLocation, dim)

        val expectedNextLocation = Point(1, 99)
        val determinedNextLocation = Movement.determineNextLocation(thing1, thing2)

        assertThat(determinedNextLocation).isEqualTo(expectedNextLocation)
    }

    @Test
    fun `when target topLeft then will move topLeft`(){
        val selfLocation = Point(100,100)
        val targetLocation = Point(0,0)

        val dim = Dimension(10,10)
        val thing1 = ParticleFighter(selfLocation, dim)
        val thing2 = ParticleFighter(targetLocation, dim)

        val expectedNextLocation = Point(99, 99)
        val determinedNextLocation = Movement.determineNextLocation(thing1, thing2)

        assertThat(determinedNextLocation).isEqualTo(expectedNextLocation)
    }

    @Test
    fun `when target bottomLeft then will move bottomLeft`(){
        val selfLocation = Point(100,0)
        val targetLocation = Point(0,100)

        val dim = Dimension(10,10)
        val thing1 = ParticleFighter(selfLocation, dim)
        val thing2 = ParticleFighter(targetLocation, dim)

        val expectedNextLocation = Point(99, 1)
        val determinedNextLocation = Movement.determineNextLocation(thing1, thing2)

        assertThat(determinedNextLocation).isEqualTo(expectedNextLocation)
    }

    @Test
    fun `when movement right would cause collision then do not move further`(){
        val selfLocation = Point(0, 0)
        val targetLocation = Point (10,0)

        val dim = Dimension(10,10)
        val thing1 = ParticleFighter(selfLocation, dim)
        val thing2 = ParticleFighter(targetLocation, dim)

        val expectedNextLocation = selfLocation
        val determinedNextLocation = Movement.determineNextLocation(thing1, thing2)

        assertThat(determinedNextLocation).isEqualTo(expectedNextLocation)
    }

    @Test
    fun `when movement left would cause collision then do not move further`(){
        val selfLocation = Point(10, 0)
        val targetLocation = Point (0,0)

        val dim = Dimension(10,10)
        val thing1 = ParticleFighter(selfLocation, dim)
        val thing2 = ParticleFighter(targetLocation, dim)

        val expectedNextLocation = selfLocation
        val determinedNextLocation = Movement.determineNextLocation(thing1, thing2)

        assertThat(determinedNextLocation).isEqualTo(expectedNextLocation)
    }

    @Test
    fun `when movement down would cause collision then do not move further`(){
        val selfLocation = Point(0, 0)
        val targetLocation = Point (0,10)

        val dim = Dimension(10,10)
        val thing1 = ParticleFighter(selfLocation, dim)
        val thing2 = ParticleFighter(targetLocation, dim)

        val expectedNextLocation = selfLocation
        val determinedNextLocation = Movement.determineNextLocation(thing1, thing2)

        assertThat(determinedNextLocation).isEqualTo(expectedNextLocation)
    }

    @Test
    fun `when movement up would cause collision then do not move further`(){
        val selfLocation = Point(0, 10)
        val targetLocation = Point (0,0)

        val dim = Dimension(10,10)
        val thing1 = ParticleFighter(selfLocation, dim)
        val thing2 = ParticleFighter(targetLocation, dim)

        val expectedNextLocation = selfLocation
        val determinedNextLocation = Movement.determineNextLocation(thing1, thing2)

        assertThat(determinedNextLocation).isEqualTo(expectedNextLocation)
    }


}