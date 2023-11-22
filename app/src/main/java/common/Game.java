package common;

import common.boardMovement.BoardMovement;
import common.boardValidator.Validator;
import common.enums.Color;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private GameMode gameMode;
    private Board board;
    private List<Player> players;
    private boolean isFinished;
    private boolean hadChanged;


    public Game ( GameMode gameMode1, List<Player> players){
        gameMode = gameMode1;
        board = new Board(gameMode.getCasilleros());
        this.players = players;
        this.isFinished = false;
    }

    private Game(GameMode gameMode, BoardResult board, List<Player> players, boolean isFinished) {
        this.gameMode = gameMode;
        this.board = board.getBoardResult();
        this.hadChanged = board.isChanged();
        this.players = players;
        this.isFinished = isFinished;
    }


    public Game move(Position initial, Position finalPosition) {
            boolean finish = false;
            if(isPiece(initial)){return new Game(this.gameMode,new BoardResult(this.board,false),this.players,finish);}
            Piece piece = board.getPiece(initial);
            List<Player> players1 = copyPlayers();


            if(isTurn(piece,initial, finalPosition,players1)){
                return new Game(this.gameMode,new BoardResult(this.board,false),this.players,finish);
            }


            else {

                BoardResult boardResult = this.makeMove(initial,finalPosition);
                finish = checkBoardResult(boardResult,initial,finalPosition,players1);
                return new Game(this.gameMode,boardResult,this.players,finish);

            }



    }



    private BoardResult makeMove(Position initial, Position finalPosition){
        Board board = this.board;

        if (board.mover(initial,finalPosition)){

            List<Validator> validatorList  = gameMode.getValidators();
            if(!checkBoardValidators(validatorList,initial,finalPosition)){
                return new BoardResult(this.board,false);
            }

            BoardResult br = getBoardResult(initial, finalPosition);
            if (br.isChanged()) return br;

            board.put(finalPosition, board.getPiece(initial));
            board.put(initial, null);
            System.out.println("Movimiento valido");
            return new BoardResult(board,true);

    }
    return new BoardResult(board,false);
    }


    private boolean checkBoardResult(BoardResult boardResult,Position initial, Position finalPosition, List<Player> players1){
        if (boardResult.isChanged()) {
            this.players = players1;
            if (checkIsFinished(boardResult.getBoardResult(), initial, finalPosition)) {
                return true;
            }
        }
        return false;
    }


    private BoardResult getBoardResult(Position initial, Position finalPosition) {
        List<BoardMovement> boardMovementList = gameMode.getBoardMovement();
        for (BoardMovement boardMovement: boardMovementList) {
           BoardResult br = boardMovement.move(board, initial, finalPosition);
           if(br.isChanged()){
               System.out.println("Movimiento valido");
               this.board = br.getBoardResult();
               return br;
           }
        }
        return new BoardResult(this.board,false);
    }


    private boolean checkBoardValidators(List<Validator> validatorList,Position initial, Position finalPosition){
        for (Validator validator: validatorList) {
            if (!validator.validate(initial,finalPosition,this.board)){
                return false;
            }
        }
        return true;
    }




    public Board getBoard() {
        return board;
    }

    public List<Player> copyPlayers() {
        List<Player> copiedList = new ArrayList<>();
        for (Player player : players) {
            Player copiedPlayer = new Player(player.getName(), player.getColor());
            copiedList.add(copiedPlayer);
        }
        return copiedList;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Color getTurn(){
        return players.get(0).getColor();
    }

    public boolean getIsFinished(){
        return isFinished;
    }

    private boolean checkIsFinished(Board board, Position initial, Position finalPosition) {
        return gameMode.getWinCondition().winCondition(board,initial,finalPosition);
    }

    public boolean getHadChanged() {
        return hadChanged;
    }


    private boolean isPiece(Position initial) {
        return board.getPiece(initial) == null;
    }

    private boolean isTurn(Piece piece, Position initial, Position finalPosition, List<Player> players) {
        return piece.getColor() != gameMode.getTurn().isTurn(players, initial, finalPosition, board).getColor();
    }

}
