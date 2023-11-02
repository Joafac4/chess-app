package common.turn;

import common.Board;
import common.Player;
import common.Position;

import java.util.List;

public class NormalTurn implements Turn{
    @Override
    public Player isTurn(List<Player> players, Position initial, Position finalPosition, Board board) {
        Player player = players.get(0);
        players.remove(0);
        players.add(player);
        return player;
    }
}
