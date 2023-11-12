package lan.client.listeners

import edu.austral.dissis.chess.gui.GameView
import edu.austral.ingsis.clientserver.ClientConnectionListener

class ClientConnectionListenerImpl: ClientConnectionListener {

    override fun handleConnection() {
        println("Connected to server")
    }

    override fun handleConnectionClosed() {
        println("Disconnected from server")
    }

}