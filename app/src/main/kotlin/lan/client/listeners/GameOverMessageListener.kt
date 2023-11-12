package lan.client.listeners

import edu.austral.dissis.chess.gui.GameOver
import edu.austral.dissis.chess.gui.NewGameState
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.MessageListener
import javafx.application.Platform
import lan.client.managers.ClientManager

class GameOverMessageListener (private val clientManager: ClientManager) : MessageListener<GameOver> {

    override fun handleMessage(message: Message<GameOver>) {
        Platform.runLater {
            clientManager.handleGameOver(message.payload)
        }
    }

}