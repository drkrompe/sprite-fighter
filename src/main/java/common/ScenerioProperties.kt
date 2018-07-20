package common

import logic.unitLogic.units.ParticleFighterUnitLogic
import things.Entity
import things.MasterListOfThings
import things.sprite.ParticleFighter
import java.awt.Dimension
import java.awt.Point

object ScenerioProperties {

    val newParticleFighter: (Point, Dimension) -> ParticleFighter = { p, d -> ParticleFighter(p, d) }
    val newParticleFighterUnitLogic: (ParticleFighter) -> ParticleFighterUnitLogic = { it -> ParticleFighterUnitLogic(it) }
    val newEntity: (ParticleFighterUnitLogic) -> Entity = { it -> Entity(it.particleFighter, it) }

    fun setup() {

        val mList = mutableListOf(null)
        for (i in 1..999) {
            mList.add(null)
        }
        mList
                .map { genPoint() }
                .map { newParticleFighter(it, Dimension(30, 30)) }
                .map { newParticleFighterUnitLogic(it) }
                .map { newEntity(it) }
                .map { MasterListOfThings.list.add(it) }

        println("here")
        MasterListOfThings.list.map { printDetails(it) }
        println("done")
    }

    fun genPoint(): Point {
        val x = (Math.random() * 1900).toInt()
        val y = (Math.random() * 1000).toInt()
        return Point(x, y)
    }

    fun printDetails(entity: Entity) {
        println(entity.body.location)
    }

    fun printDetails(pful: ParticleFighterUnitLogic) {
        println(pful.particleFighter.location)
    }

    fun printDetails(pf: ParticleFighter) {
        println(pf.location)
    }


}