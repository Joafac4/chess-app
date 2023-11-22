package lan.client.listeners

import edu.austral.dissis.chess.gui.GameEventListener
import edu.austral.dissis.chess.gui.Move
import edu.austral.ingsis.clientserver.Message
import lan.client.managers.ClientManager

class ClientGameEventListener(private val clientManager: ClientManager) : GameEventListener {

    override fun handleMove(move: Move) {
        clientManager.getClientInstance().send(Message("move",move))
    }

}