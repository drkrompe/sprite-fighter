package threads

import properties.TeamsProperties
import java.util.concurrent.Semaphore

object TeamSyncSemaphore : Semaphore(TeamsProperties.numberOfTeams)