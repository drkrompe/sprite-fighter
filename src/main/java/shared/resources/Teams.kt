package shared.resources

import properties.TeamsProperties
import things.toCopy
import java.util.*
import kotlin.math.roundToInt
import things.Entity as CopiedEntity

object Teams {
    private var list = mutableListOf<Team>()

    fun findOtherEntityAndMakeCopy(otherId: UUID?, selfTeamId: Int): CopiedEntity? {
        var foundEntity: CopiedEntity? = null

        list.map { targetedTeam ->
            if (targetedTeam.teamId != selfTeamId) {
                targetedTeam.entityList.getList().map { entity ->
                    entity.lock.acquire()
                    if (entity.id == otherId) {
                        foundEntity = toCopy(entity)
                        entity.lock.release()
                        return foundEntity
                    }
                    entity.lock.release()

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
        if (self < 0 || self > TeamsProperties.numberOfTeams - 1) {
            throw Exception("Called getOther with invalid value")
        }

        return if (TeamsProperties.numberOfTeams == 2) {
            if (self == 0) {
                getTeam(1)
            } else {
                getTeam(0)
            }
        } else {
            // TODO not do stupid things like this. Find better way of handling > 2 teams.
            var other = (Math.random() * TeamsProperties.numberOfTeams).roundToInt()
            while (Math.round(other.toFloat()) == self) {
                other = (Math.random() * TeamsProperties.numberOfTeams).roundToInt()
            }
            getTeam(other)
        }
    }
}