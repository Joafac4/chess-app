package lan.server.mains

import edu.austral.dissis.chess.MyEngine
import edu.austral.dissis.chess.gui.*
import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage
import lan.client.main.ClientMain
import lan.server.managers.ServerManager
import lan.utils.GameViewBuilder


fun main(args: Array<String>) {
    Application.launch(ServerMain::class.java)
}

class ServerMain : AbstractChessGameApplication() {
    override val gameEngine: GameEngine = MyEngine()
    override val imageResolver = CachedImageResolver(DefaultImageResolver())
    val serverManager = ServerManager()

    init {
        val server = serverManager.createServer()
        server.start()
        serverManager.handleInitialState(serverManager.getInitialState())
    }

    override fun start(primaryStage: Stage) {
        val root = serverManager.getGameViewInstance()
        primaryStage.scene = Scene(root)
        primaryStage.show()
    }



}