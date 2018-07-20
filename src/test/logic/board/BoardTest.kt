package logic.board

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.awt.Point

class BoardTest {

    @Test
    fun `when call relativeToDrawPosition far left will return correct position`() {

        val inputGameLocation = Point(0, 0)

        val expectedOutputDrawLocation = Point(0, 0)

        val returnedLocation = Board().relativeToDrawPosition(inputGameLocation)

        assertThat(returnedLocation).isEqualTo(expectedOutputDrawLocation)
    }

    @Test
    fun `when call relativeToDrawPosition middle will return correct position`() {

        val inputGameLocation = Point(10, 10)

        val expectedOutputDrawLocation = Point(100, 100)

        val returnedLocation = Board().relativeToDrawPosition(inputGameLocation)

        assertThat(returnedLocation).isEqualTo(expectedOutputDrawLocation)
    }
}