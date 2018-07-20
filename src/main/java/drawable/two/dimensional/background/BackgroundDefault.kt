package drawable.two.dimensional.background

import properties.PanelProperties
import java.awt.Color
import java.awt.Graphics

object BackgroundDefault {

    val draw: (Graphics?) -> Unit = { g ->
        g?.color = Color.lightGray
        g?.fillRect(0, 0, PanelProperties.width, PanelProperties.height)
    }
}