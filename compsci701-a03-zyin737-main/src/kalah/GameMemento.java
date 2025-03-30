package kalah;

public class GameMemento {

    private Player[] _players;
    private boolean _isTurnPlayer1;


    public GameMemento(Player[] players, boolean isTurnPlayer1) {
        this._players = savePlayers(players);
        this._isTurnPlayer1 = isTurnPlayer1;
    }


    private Player[] savePlayers(Player[] players) {
        Player[] savedPlayers = new Player[players.length];
        for (int i = 0; i < players.length; i++) {
            savedPlayers[i] = new Player(players[i]);
        }
        return savedPlayers;
    }


    public Player[] getPlayers() {
        return _players;
    }

    public boolean getIsTurnPlayer1() {
        return _isTurnPlayer1;
    }

}
