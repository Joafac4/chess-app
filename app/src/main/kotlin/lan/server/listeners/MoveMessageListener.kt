package lan.server.listeners

import common.conector.Conector
import common.enums.Color
import edu.austral.dissis.chess.gui.GameOver
import edu.austral.dissis.chess.gui.Move
import edu.austral.dissis.chess.gui.NewGameState
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.MessageListener
import lan.server.managers.ServerManager

class MoveMessageListener(private val serverManager: ServerManager) : MessageListener<Move> {

    override fun handleMessage(message: Message<Move>) {
        handleMove(message)
    }


    private fun handleMove(message: Message<Move>){
        val move = message.payload
        val oldPos = Conector.getPos(move.from)
        val newPos = Conector.getPos(move.to)
        var game = serverManager.getGameInstance()
        val playerColor = game.turn
        game = game.move(oldPos, newPos)
        val newGameState = NewGameState(Conector.getPieces(game.board),Conector.adaptColour(game.turn))

        if(game.isFinished) {
            finishGame(playerColor)
        }
        else{nonFinishGame(newGameState)}
    }


    private fun nonFinishGame(newGameState: NewGameState){
        serverManager.getServerInstance().broadcast(Message("move", newGameState))
        serverManager.handleNewGameState(newGameState)
    }

    private fun finishGame(color: Color){
        val gameOver = GameOver(Conector.adaptColour(color))
        serverManager.getServerInstance().broadcast(Message("finish", gameOver))
        serverManager.handleGameOver(gameOver)
    }




}