package common.movement2.strategyMovement;

import common.Board;
import common.Piece;
import common.Position;
import common.movement2.Movement2;

public class DiagonalObligatoryTrepassin implements Movement2 {

    @Override
    public boolean move(Board board, Position initial, Position finalPosition) {
        return !sameColorTrepassin(initial,finalPosition,board);
    }


    private boolean sameColorTrepassin(Position initial, Position finalPosition, Board board ) {
        int x = finalPosition.getRow() - initial.getRow();
        int y = finalPosition.getColumn() - initial.getColumn();
        if (Math.abs(x) != Math.abs(y) ){return true;}

        int rowIncrement = x > 0 ? 1 : -1;
        int colIncrement = y > 0 ? 1 : -1;

        int row = initial.getRow() + rowIncrement;
        int col = initial.getColumn() + colIncrement;

        while (row != finalPosition.getRow() || col != finalPosition.getColumn()) {
            Position position = new Position(row, col);
            Piece pieceInBetween = board.getPiece(position);
            if (pieceInBetween != null && pieceInBetween.getColor() != board.getPiece(initial).getColor()) {
                return false; // Hay una pieza en el camino diagonal, retorna false
            }
            row += rowIncrement;
            col += colIncrement;
        }

        return true; // No hay piezas en el camino diagonal, retorna true
    }
}
