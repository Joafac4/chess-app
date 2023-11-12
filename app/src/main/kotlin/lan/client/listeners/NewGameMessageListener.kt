package lan.client.listeners

import edu.austral.dissis.chess.gui.NewGameState
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.MessageListener
import javafx.application.Platform
import lan.client.managers.ClientManager

class NewGameMessageListener(private val clientManager: ClientManager) : MessageListener<NewGameState> {

    override fun handleMessage(message: Message<NewGameState>) {
        Platform.runLater {
            clientManager.handleNewGameState(message.payload)
        }
    }

}