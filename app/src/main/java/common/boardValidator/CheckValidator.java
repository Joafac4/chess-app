package common.boardValidator;

import common.Board;
import common.Piece;
import common.Position;
import common.enums.Color;
import common.enums.Type;

import java.util.ArrayList;
import java.util.List;


public class CheckValidator implements Validator{

    @Override
    public boolean validate(Position initial, Position finalPosition, Board board) {
        Piece piece = board.getPiece(initial);
        Board board1 = board.copy();
        if(piece.getType() != Type.FRSTKING && piece.getType() != Type.KING){
            board1.put(finalPosition, piece);
            board1.put(initial, null);
            Position kingPosition = board1.getKingPosition(piece.getColor());
            return checkChecker(board1, piece, kingPosition);}
        else{
            return validateCheckIfPieceIsntKing(initial, finalPosition, board1, piece);
        }

    }

    private boolean validateCheckIfPieceIsntKing(Position initial, Position finalPosition, Board board1, Piece piece) {
        if(Math.abs(finalPosition.getColumn()- initial.getColumn()) == 2){
        int x = initial.getRow();
        int y = (initial.getColumn() + finalPosition.getColumn())/2;
        Position middlePosition = new Position(x,y);
            return checkChecker(board1, piece, initial) &&
                    checkChecker(board1, piece, finalPosition) &&
                    checkChecker(board1, piece, middlePosition);
        }
        else {
            board1.put(finalPosition, piece);
            board1.put(initial, null);
            return checkChecker(board1, piece, finalPosition);
        }
    }


    private boolean checkChecker(Board board1, Piece piece, Position kingPosition){
        List<Position> position = board1.getAllPositions();
        List<Position> positions = getPositionsByPieceColor(board1, position, piece);
        for (Position position1 : positions){
            Piece piece1 = board1.getPiece(position1);
            if(piece1.moveValidation(position1,kingPosition,board1)){
                return false;
            }
        }
        return true;
    }


    private List<Position> getPositionsByPieceColor(Board board, List<Position> pos, Piece piece) {
        List<Position> positions = new ArrayList<>();
        Color color = null;
        if (piece.getColor() == Color.WHITE) {
            color = Color.BLACK;
        } else {
            color = Color.WHITE;
        }
        for (Position position : pos) {
            Piece piece1 = board.getPiece(position);
            if (piece1 != null) {
                if (piece1.getColor() == color) {
                    if(piece1.getType() != Type.KING && piece1.getType() != Type.FRSTKING){
                        positions.add(position);
                    }}
            }
        }
        return positions;
    }



}

