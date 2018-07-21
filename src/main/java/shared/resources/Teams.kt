package shared.resources

import properties.TeamsProperties
import things.toCopy
import java.util.*
import kotlin.math.roundToInt
import things.Entity as CopiedEntity

object Teams {
    private var list = mutableListOf<Team>()

    fun findOtherEntityCopy(otherId: UUID?, selfTeam: Int): CopiedEntity? {
        var foundEntity: CopiedEntity? = null
        when (otherId) {
            is UUID -> list.map {
                when (it.team == selfTeam) {
                    false -> it.list.getList().map {
                        it.lock.acquire()
                        when (it.id == otherId) {
                            true -> {
                                foundEntity = toCopy(it)
                                it.lock.release()
                                return foundEntity
                            }
                        }
                        it.lock.release()
                    }
                    true -> {
                    }
                }
            }
        }
        return foundEntity
    }

    fun getTeams(): List<Team> {
        return list
    }

    fun getTeam(which: Int): Team {
        return list[which]
    }

    fun createTeam(team: Int): Team {
        list.add(Team(team))
        return list[team]
    }

    fun getOther(self: Int): Team? {
        when (self > TeamsProperties.numberOfTeams - 1) {
            true -> throw Exception("Called getOther with invalid value")
        }
        return when (TeamsProperties.numberOfTeams) {
            1 -> return null
            2 -> {
                when (self) {
                    0 -> getTeam(1)
                    1 -> getTeam(0)
                    else -> null // will never happen
                }
            }
            else -> {
                var other = (Math.random() * TeamsProperties.numberOfTeams).roundToInt()
                while (Math.round(other.toFloat()) == self) {
                    other = (Math.random() * TeamsProperties.numberOfTeams).roundToInt()
                }
                getTeam(other)
            }
        }
    }
}