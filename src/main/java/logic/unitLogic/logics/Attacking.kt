package logic.unitLogic.logics

import things.Entity
import things.sprite.ParticleFighter

object Attacking {
    fun attack(other: Entity?, attackVal: Int) {
        if (other?.body is ParticleFighter) {
            other.body.health -= attackVal
            if (other.isDead()) {
                other.dead = true
            }
        }
    }

    private fun Entity.isDead(): Boolean {
        return this.body.health < 0
    }
}