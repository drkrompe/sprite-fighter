package properties

import logic.unitLogic.units.ParticleFighterUnitLogic
import shared.resources.Entity
import shared.resources.Teams
import things.sprite.ParticleFighter
import java.awt.Dimension
import java.awt.Point

object ScenarioProperties {

    private val newParticleFighter: (Point, Dimension) -> ParticleFighter = { p, d -> ParticleFighter(location = p, dimension = d) }
    private val newParticleFighterUnitLogic: (ParticleFighter) -> ParticleFighterUnitLogic = { body -> ParticleFighterUnitLogic(particleFighter = body) }
    private val newEntity: (ParticleFighterUnitLogic, Int) -> Entity = { logic, team -> Entity(body = logic.particleFighter, soul = logic, draw = drawable.two.dimensional.things.sprite.ParticleFighter, team = team) }

    fun setup() {

        for (team in 0 until TeamsProperties.numberOfTeams) {
            val genList = mutableListOf(null)
            for (i in 0 until 500) {
                genList.add(null)
            }
            Teams.createTeam(team)
            genList.asSequence().map { genTeamSpawnPoint(team) }
                    .map { newParticleFighter(it, Dimension(10, 10)) }
                    .map { newParticleFighterUnitLogic(it) }
                    .map { newEntity(it, team) }
                    .map { Teams.getTeam(team).entityList.addEntity(it) }.toList()
        }

        println("done")
    }

    private fun genPoint(): Point {
        val x = (Math.random() * 1900).toInt()
        val y = (Math.random() * 1000).toInt()
        return Point(x, y)
    }

    private fun genTeamSpawnPoint(team: Int): Point {
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