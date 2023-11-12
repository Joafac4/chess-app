package lan.common

import edu.austral.dissis.chess.gui.GameOver
import edu.austral.dissis.chess.gui.GameView
import edu.austral.dissis.chess.gui.InitialState
import edu.austral.dissis.chess.gui.NewGameState

interface Manager {


    fun getGameViewInstance() : GameView

    fun handleInitialState(initialState: InitialState) {
    }

    fun handleNewGameState(newGameState: NewGameState) {
    }

    fun handleGameOver(gameOver: GameOver) {
    }

}