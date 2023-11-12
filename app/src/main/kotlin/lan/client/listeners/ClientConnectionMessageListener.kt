package lan.client.listeners

import edu.austral.dissis.chess.gui.InitialState
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.MessageListener
import javafx.application.Platform
import lan.client.managers.ClientManager

class ClientConnectionMessageListener(private val clientManager: ClientManager) : MessageListener<InitialState> {

    override fun handleMessage(message: Message<InitialState>) {
        Platform.runLater {
            clientManager.handleInitialState(message.payload)
        }
    }
}