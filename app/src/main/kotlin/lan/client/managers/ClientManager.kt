package lan.client.managers

import com.fasterxml.jackson.core.type.TypeReference
import edu.austral.dissis.chess.gui.*
import edu.austral.ingsis.clientserver.Client
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.netty.client.NettyClientBuilder
import lan.client.listeners.*
import lan.common.Manager
import lan.utils.GameViewBuilder
import java.net.InetSocketAddress

class ClientManager : Manager{
    lateinit var client: Client
    val GameView = GameViewBuilder().buildClientGameView(this)



    override fun getGameViewInstance() : GameView {
        return GameView
    }

    fun getClientInstance() : Client {
        return client
    }

    override fun handleInitialState(initialState: InitialState) {
        GameView.handleInitialState(initialState)
    }

    override fun handleNewGameState(newGameState: NewGameState) {
        GameView.handleMoveResult(newGameState)
    }

    override fun handleGameOver(gameOver: GameOver) {
        GameView.handleMoveResult(gameOver)
    }

    fun handleInvalidMovement(invalidMove : InvalidMove ){
        GameView.handleMoveResult(invalidMove)
    }
    fun createClient() : Client {
        this.client = NettyClientBuilder.createDefault()
                .withConnectionListener(ClientConnectionListenerImpl())
                .addMessageListener("clientConnection", object : TypeReference<Message<InitialState>>() {}, ClientConnectionMessageListener(this))
                .addMessageListener("move", object : TypeReference<Message<NewGameState>>() {}, NewGameMessageListener(this))
                .addMessageListener("finish", object : TypeReference<Message<GameOver>>() {}, GameOverMessageListener(this))
                .addMessageListener("invalid", object : TypeReference<Message<InvalidMove>>() {}, NewInvalidMovementListener(this))
                .withAddress(InetSocketAddress("localhost", 8080))
                .build()

        return client
    }



}