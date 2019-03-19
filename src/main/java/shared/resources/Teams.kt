package shared.resources

import properties.TeamsProperties
import things.toCopy
import java.util.*
import things.Entity as CopiedEntity

object Teams {
    private var list = mutableListOf<Team>()

    fun findOtherEntityByUUID(otherId: UUID?, selfTeamId: Int): CopiedEntity? {
        var copyOfFoundEntity: CopiedEntity? = null

        list.map { targetedTeam ->
            if (targetedTeam.teamId != selfTeamId) {
                targetedTeam.entityList.getList()
                        .find { entity -> entity.id == otherId }
                        ?.let { foundEntity ->
                            foundEntity.lock.acquire()
                            copyOfFoundEntity = toCopy(foundEntity)
                            foundEntity.lock.release()
                        }
            }
        }

        return copyOfFoundEntity
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

    fun getOtherTeam(self: Int): Team? {
        if (self < 0 || self > TeamsProperties.numberOfTeams - 1) {
            throw Exception("Called getOtherTeam with invalid value")
        }
        var teamNumberGuess = 0
        while (self == teamNumberGuess) {
            teamNumberGuess = (teamNumberGuess + 1) % TeamsProperties.numberOfTeams
        }
        return getTeam(teamNumberGuess)
    }
}