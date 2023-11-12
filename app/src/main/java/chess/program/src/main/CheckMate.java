package chess.program.src.main;

import common.Board;
import common.Piece;
import common.Position;
import common.boardValidator.CheckValidator;
import common.boardValidator.Validator;
import common.enums.Color;
import common.enums.Type;
import common.winningCondition.WinCondition;

import java.util.ArrayList;
import java.util.List;

public class CheckMate implements WinCondition {
    private Validator checkValidator = new CheckValidator();
    private Color color ;
    @Override
    public boolean winCondition(Board board, Position initial, Position finalPosition) {
        Piece piece = board.getPiece(finalPosition);
        if (piece.getColor() == Color.WHITE) {
            color = Color.BLACK;
        } else {
            color = Color.WHITE;
        }

        boolean kingIsThreatend = kingIsThreatend(board, finalPosition, color);
        boolean kingIsAbleToMove = kingIsAbleToMove(board, color);
        boolean kingCanNotBeProtected = kingCanNotBeProtected(board, color);

        return (kingIsThreatend && !kingIsAbleToMove && kingCanNotBeProtected);
    }



    private List<Position> getPositionsByPieceColor(Board board, List<Position> pos, Color color) {
        List<Position> positions = new ArrayList<>();
        for (Position position : pos) {
            Piece piece = board.getPiece(position);
            if (piece != null) {
                if (piece.getColor() == color) {
                    if(piece.getType() != Type.KING && piece.getType() != Type.FRSTKING){
                    positions.add(position);
                }}
            }
        }
        return positions;
    }


    private boolean kingIsThreatend(Board board, Position finalPosition, Color color){
        Position kingPosition = board.getKingPosition(color);
        Piece piece = board.getPiece(finalPosition);
        Board board1 = board.copy();
        return piece.moveValidation(finalPosition,kingPosition,board1);
    }
    private boolean kingIsAbleToMove(Board board, Color color){
        Position position = board.getKingPosition(color);
        Piece piece = board.getPiece(position);
        List<Position> positions = board.getAllPositions();
        for (Position position1 : positions) {
            if (position != position1) {
                Board board1 = board.copy();
                if (board1.getPiece(position1) != null) {
                    if(board1.getPiece(position1).getColor() != color){
                        if (piece.moveValidation(position, position1, board1)) {
                            if (checkValidator.validate(position, position1, board1)) {
                                return true;
                            }
                        }
                    }
                }
                else{
                if (piece.moveValidation(position, position1, board1)) {
                        if (checkValidator.validate(position, position1, board1)) {
                            return true;
                        }
                }
            }}
        }
        return false;
    }

    private boolean kingCanNotBeProtected(Board board, Color color){
        List<Position> allPositions = board.getAllPositions();
        List<Position> positions = getPositionsByPieceColor(board,allPositions,color);
        Position kingPosition = board.getKingPosition(color);
        for (Position position : positions) {
            Piece piece = board.getPiece(position);
            for (Position position1 : allPositions) {
                if (position != position1) {
                    if(position1 != kingPosition){
                    Board board1 = board.copy();
                    if (piece.moveValidation(position, position1, board1)) {
                        if (checkValidator.validate(position, position1, board1)) {
                                return false;
                            }
                        }
                    }}
                }
            }

        return true;
    }


}
