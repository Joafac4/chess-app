package checkers;

import common.*;
import common.enums.Color;
import common.enums.Type;
import common.movement2.*;
import common.movement2.strategyMovement.*;
import common.turn.Turn;

import java.util.ArrayList;
import java.util.List;

public class CheckersTurn implements Turn {

    @Override
    public Player isTurn(List<Player> players, Position initial, Position finalPosition,Board board) {
        Player player = players.get(0);
        if (isThereAnyPieceToEat(player.getColor(),initial,finalPosition,board)){
            return player;
        }
        else{
            players.remove(0);
            players.add(player);
            return player;}
    }

    private boolean isThereAnyPieceToEat(Color color, Position initial, Position finalPosition, Board board) {
        int deltaRow = Math.abs(finalPosition.getRow() - initial.getRow());
        if(deltaRow == 1){return false;}
        Board board1 = board.copy();
        Piece piece = board1.getPiece(initial);
        board1.put(finalPosition,piece);
        removeEatenPiece(board1,initial,finalPosition);
        board1.put(initial,null);


        Movement2 eatMovement = getEatMovements(color,finalPosition,board);
        return canStillEat(finalPosition, board1, eatMovement,color);
    }




    private Movement2 getEatMovements(Color color, Position finalPosition, Board board){
        List<Movement2> validateMovements = new ArrayList<>();
        validateMovements.add(new NoEatStrategy());
        validateMovements.add(new DiagonalObligatoryTrepassin());
        if (color == Color.WHITE){
            if(finalPosition.getRow() == 8){validateMovements.add(new DiagonalMovement(2, 2, 2, 2));}
            else{validateMovements.add(new DiagonalMovement(2, 2, 2, 0));}
            return new ComposeMovement(validateMovements);
        }
        else{
            if(finalPosition.getRow() == 1){validateMovements.add(new DiagonalMovement(2, 2, 2, 2));}
            else{ validateMovements.add(new DiagonalMovement(2, 2, 0, 2));}
            return new ComposeMovement(validateMovements);
        }
    }

    private static boolean checkRow(Position finalPosition, int row) {
        return finalPosition.getRow() == row;
    }

    private static boolean canStillEat(Position finalPosition,Board board, Movement2 eatMovement,Color color){
        List<Position> positions = board.getAllPositions();
        for (Position position : positions) {
            Piece piece1 = board.getPiece(finalPosition);
                    if(piece1 != null)
                        if (piece1.getColor() == color && eatMovement.move(board, finalPosition, position)){// && eatMovement.checkMoveStrategies(board, finalPosition, position)) {
                            return true;
                    }
                }

        return false;
    }

    private void removeEatenPiece(Board board1, Position inicial, Position finalPosition){
        int desplazamientox = finalPosition.getRow() - inicial.getRow();
        int desplazamientoy = finalPosition.getColumn() - inicial.getColumn();
        if (desplazamientox > 0 && desplazamientoy > 0){ board1.put(new Position(inicial.getRow()+1,inicial.getColumn()+1),null);}
        if (desplazamientox > 0 && desplazamientoy < 0){ board1.put(new Position(inicial.getRow()+1,inicial.getColumn()-1),null);}
        if (desplazamientox < 0 && desplazamientoy > 0){ board1.put(new Position(inicial.getRow()-1,inicial.getColumn()+1),null);}
        if (desplazamientox < 0 && desplazamientoy < 0){ board1.put(new Position(inicial.getRow()-1,inicial.getColumn()-1),null);}
    }

}
