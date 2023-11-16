package lan.server.managers

import com.fasterxml.jackson.core.type.TypeReference
import common.Game
import common.conector.Conector
import edu.austral.dissis.chess.*
import edu.austral.dissis.chess.gui.*
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.Server
import edu.austral.ingsis.clientserver.netty.server.NettyServerBuilder
import javafx.application.Platform
import lan.common.Manager
import lan.server.listeners.MoveMessageListener
import lan.server.listeners.ServerConnectionListenerImpl
import lan.utils.GameViewBuilder

class ServerManager : Manager {
    lateinit var server : Server
    private var myGame : Game = createCheckersGame(createCheckersGM())
    val GameView = GameViewBuilder().buildServerGameView(this)

    fun getServerInstance() : Server {
        return server
    }

    fun getInitialState() : InitialState {
        return InitialState(Conector.adaptBoard(myGame.board), Conector.getPieces(myGame.board), PlayerColor.WHITE)
    }

    fun getGameInstance() : Game {
        return myGame
    }

    fun createServer() : Server {
        this.server = NettyServerBuilder.createDefault()
            .withPort(8080)
            .withConnectionListener(ServerConnectionListenerImpl(this))
            .addMessageListener("move", object :TypeReference<Message<Move>>() {},MoveMessageListener(this))
            .build()

        return server
    }

    override fun getGameViewInstance(): GameView {
        return GameView
    }

    override fun handleInitialState(initialState: InitialState) {
        GameView.handleInitialState(initialState)
    }

    override fun handleNewGameState(newGameState: NewGameState) {
        GameView.handleMoveResult(newGameState)
    }

    override fun handleGameOver(gameOver: GameOver) {
        Platform.runLater {
            GameView.handleMoveResult(gameOver)
        }
    }

}