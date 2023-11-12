package lan.server.listeners

import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.ServerConnectionListener
import lan.server.managers.ServerManager

class ServerConnectionListenerImpl(private val serverManager: ServerManager): ServerConnectionListener {

    override fun handleClientConnection(clientId: String) {
        serverManager.getServerInstance().sendMessage(clientId, Message("clientConnection",serverManager.getInitialState()))
    }

    override fun handleClientConnectionClosed(clientId: String) {
        println("Client $clientId disconnected")
    }


}