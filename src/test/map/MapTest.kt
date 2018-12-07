package map

import logic.unitLogic.units.ParticleFighterUnitLogic
import org.junit.Test
import properties.ScenerioProperties
import shared.resources.Entity
import shared.resources.Teams
import things.sprite.ParticleFighter
import java.awt.Dimension
import java.awt.Point

class MapTest {

    @Test
    fun canHandleLargeMap_populate(){
        val largeMap = HashMap<Point, List<Entity>>()
        val curTime = System.currentTimeMillis()
        for (x in 0 until 1920){
            for(y in 0 until 1080){
                largeMap.put(Point(x,y), mutableListOf())
            }
        }
        val endTime = System.currentTimeMillis()
        println("Total time -> " + (endTime - curTime))
    }

    @Test
    fun canHandleLargeMap_lookup(){
        val largeMap = HashMap<Point, List<Entity>>()
        for (x in 0 until 1920){
            for(y in 0 until 1080){
                largeMap.put(Point(x,y), mutableListOf())
            }
        }
        val genList = mutableListOf(null)
        for (i in 0 until 500) {
            genList.add(null)
        }

        val thing = genList.map { genTeamPoint(1) }
                .map { newParticleFighter(it, Dimension(10, 10)) }
                .map { newParticleFighterUnitLogic(it) }
                .map { newEntity(it, 1) }

        largeMap[Point(250,760)] = thing

        val curTime = System.currentTimeMillis()
        val list = largeMap[Point(250, 760)]
        val endTime = System.currentTimeMillis()
        println("Total time -> " + (endTime - curTime) + "\n entityList $list")
    }

    private val newParticleFighter: (Point, Dimension) -> ParticleFighter = { p, d -> ParticleFighter(location = p, dimension = d) }
    private val newParticleFighterUnitLogic: (ParticleFighter) -> ParticleFighterUnitLogic = { body -> ParticleFighterUnitLogic(particleFighter = body) }
    private val newEntity: (ParticleFighterUnitLogic, Int) -> Entity = { logic, team -> Entity(body = logic.particleFighter, soul = logic, draw = drawable.two.dimensional.things.sprite.ParticleFighter, team = team) }

    private fun genTeamPoint(team: Int): Point {
        when (team) {
            0 -> {
                val x = (Math.random() * 200).toInt()
                val y = (Math.random() * 1000).toInt()
                return Point(x, y)
            }
            1 -> {
                val x = ((Math.random() * 200) + 1700).toInt()
                val y = (Math.random() * 1000).toInt()
                return Point(x, y)
            }
            else -> {
                val x = (Math.random() * 1900).toInt()
                val y = (Math.random() * 1000).toInt()
                return Point(x, y)
            }
        }
    }

}