package lan.client.main
import edu.austral.dissis.chess.MyEngine
import edu.austral.dissis.chess.gui.*
import javafx.application.Application.launch
import javafx.scene.Scene
import javafx.stage.Stage
import lan.client.managers.ClientManager


fun main(args: Array<String>) {
    launch(ClientMain::class.java)
}

class ClientSecondMain : AbstractChessGameApplication() {
    override val gameEngine: GameEngine = MyEngine()
    override val imageResolver = CachedImageResolver(DefaultImageResolver())
    val clientManager = ClientManager()

    init {
        val client = clientManager.createClient()
        Thread.sleep(10000)
        client.connect()
    }



    override fun start(primaryStage: Stage) {
        val root = clientManager.getGameViewInstance()
        primaryStage.scene = Scene(root)
        primaryStage.show()
    }






}



