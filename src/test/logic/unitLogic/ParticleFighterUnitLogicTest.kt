import common.AGamesProperties
import logic.unitLogic.ParticleFighterUnitLogic
import logic.unitLogic.UnitAction.AttackAction
import logic.unitLogic.UnitAction.MoveAction
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import things.Entity
import things.MasterListOfThings
import things.sprite.ParticleFighter
import java.awt.Dimension
import java.awt.Point


class ParticleFighterUnitLogicTest {

    val newParticleFighter: (Point, Dimension) -> ParticleFighter = { p, d -> ParticleFighter(p, d) }
    val newParticleFighterUnitLogic: (ParticleFighter) -> ParticleFighterUnitLogic = { it -> ParticleFighterUnitLogic(it) }
    val newEntity: (ParticleFighterUnitLogic) -> Entity = { it -> Entity(it.particleFighter, it) }

    @Before
    fun setup(){
        MasterListOfThings.list.removeAll(MasterListOfThings.list)
    }

    @Test
    fun `when is in range inRange will see inRange`() {
        listOf(Point(0, 0), Point(0, 1))
                .map { newParticleFighter(it, Dimension(10, 10)) }
                .map { newParticleFighterUnitLogic(it) }
                .map { newEntity(it) }
                .map { MasterListOfThings.list.add(it) }


        (MasterListOfThings.list[0].soul as ParticleFighterUnitLogic).currentTargetId = MasterListOfThings.list[1].id

        val isInRange = (MasterListOfThings.list[0].soul as ParticleFighterUnitLogic).isInAttackRange()

        assertThat(isInRange).isTrue()
    }

    @Test
    fun `when is not in range inRange will see not inRange`() {
        listOf(Point(0, 0), Point(0, 2))
                .map { newParticleFighter(it, Dimension(10, 10)) }
                .map { newParticleFighterUnitLogic(it) }
                .map { newEntity(it) }
                .map { MasterListOfThings.list.add(it) }

        (MasterListOfThings.list[0].soul as ParticleFighterUnitLogic).currentTargetId = MasterListOfThings.list[1].id

        val isInRange = (MasterListOfThings.list[0].soul as ParticleFighterUnitLogic).isInAttackRange()

        assertThat(isInRange).isFalse()
    }

    @Test
    fun `when not inRange then next action is MoveAction`() {
        val particleFighter = mock(ParticleFighterUnitLogic::class.java)
        `when`(particleFighter.isInAttackRange()).thenReturn(false)
        `when`(particleFighter.determineNextAction()).thenCallRealMethod()

        val nextAction = particleFighter.determineNextAction()
        println(nextAction)

        assertThat(nextAction.javaClass).isEqualTo(MoveAction().javaClass)
    }

    @Test
    fun `when inRange then next action is to AttackAction`() {
        val particleFighter = mock(ParticleFighterUnitLogic::class.java)
        `when`(particleFighter.isInAttackRange()).thenReturn(true)
        `when`(particleFighter.determineNextAction()).thenCallRealMethod()

        val nextAction = particleFighter.determineNextAction()
        println(nextAction)

        assertThat(nextAction.javaClass).isEqualTo(AttackAction().javaClass)
    }

}