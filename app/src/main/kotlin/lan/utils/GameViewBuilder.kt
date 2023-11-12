package lan.utils

import edu.austral.dissis.chess.gui.CachedImageResolver
import edu.austral.dissis.chess.gui.DefaultImageResolver
import edu.austral.dissis.chess.gui.GameView
import lan.client.managers.ClientManager
import lan.server.listeners.MoveMessageListener
import lan.server.managers.ServerManager

class GameViewBuilder  {

    val imageResolver = CachedImageResolver(DefaultImageResolver())




    fun buildClientGameView(clientManager: ClientManager) : GameView {
        val gameView = GameView(imageResolver)
        gameView.addListener(ClientGameEventListener(clientManager))

        return gameView
    }

    fun buildServerGameView(serverManager: ServerManager) : GameView {
        val gameView = GameView(imageResolver)
        return gameView
    }


}