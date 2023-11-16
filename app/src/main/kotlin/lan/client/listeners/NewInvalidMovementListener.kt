package lan.client.listeners

import edu.austral.dissis.chess.gui.GameOver
import edu.austral.dissis.chess.gui.InvalidMove
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.MessageListener
import javafx.application.Platform
import lan.client.managers.ClientManager

class NewInvalidMovementListener (private val clientManager: ClientManager) : MessageListener<InvalidMove> {
    override fun handleMessage(message: Message<InvalidMove>) {
        Platform.runLater {
            clientManager.handleInvalidMovement(message.payload)
        }
    }
}