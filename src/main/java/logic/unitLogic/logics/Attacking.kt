package logic.unitLogic.logics

import things.Entity
import things.sprite.ParticleFighter

object Attacking {

    fun attack(other: Entity?, attackVal: Int) {
        when (other?.body) {
            is ParticleFighter -> {
                other.body.health -= attackVal
                when (other.body.health < 0) {
                    true -> {
                        other.dead = true
                    }
                }
            }
        }
    }
}