package logic.unitLogic

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import things.Entity
import things.MasterListOfThings
import things.sprite.ParticleFighter
import java.awt.Dimension
import java.awt.Point

class TargetingTest {

    @Test
    fun `when call findTarget will return next closest target that is not self`() {
        val particleFighter1 = ParticleFighter(Point(0, 0), Dimension(10,10))
        val particleFighter2 = ParticleFighter(Point(0, 1), Dimension(10,10))
        val particleFighter3 = ParticleFighter(Point(0, 2), Dimension(10,10))

        val particleFighterUnitLogic1 = ParticleFighterUnitLogic(particleFighter1)
        val particleFighterUnitLogic2 = ParticleFighterUnitLogic(particleFighter2)
        val particleFighterUnitLogic3 = ParticleFighterUnitLogic(particleFighter3)

        MasterListOfThings.list.add(Entity(particleFighter1, particleFighterUnitLogic1))
        MasterListOfThings.list.add(Entity(particleFighter2, particleFighterUnitLogic2))
        MasterListOfThings.list.add(Entity(particleFighter3, particleFighterUnitLogic3))

        val nextTarget = Targeting.findNearestTarget(particleFighter1, MasterListOfThings.list)

        assertThat(nextTarget).isNotNull
        assertThat(nextTarget?.body?.location).isEqualTo(MasterListOfThings.list[1].body.location)
    }

}