package kalah;

import com.qualitascorpus.testsupport.IO;

public class GameApp {

    public final int NUMOFPLAYERS = 2;
    public final int NUMOFHOUSES = 6;
    public final int NUMOFSEEDS = 4;
    private Player[] _players;
    private GameMemento _gameMemento;
    private GameHistory _gameHistory;
    private boolean _isTurnPlayer1;
    private boolean _isGameRunning;
    private boolean _isNewGameRequested;
    private boolean _isGameSaved;
    private boolean _isGameLoaded;
    private boolean _isLoadInvoked;
    private IO _io;
    // fields retained for backwards compatibility
    private boolean _isVerticalBoard;
    private boolean _isBmfRobot;


    public GameApp(IO io, boolean isVerticalBoard, boolean isBmfRobot) {
        this._io = io;
        this._isVerticalBoard = isVerticalBoard;
        this._isBmfRobot = isBmfRobot;

        _players = new Player[NUMOFPLAYERS];
        _players[0] = new Player(1, NUMOFHOUSES, NUMOFSEEDS);
        _players[1] = new Player(2, NUMOFHOUSES, NUMOFSEEDS);

        this._gameHistory = new GameHistory();
    }


    public void startGame() {
        _isTurnPlayer1 = true;
        _isGameRunning = true;
        _isNewGameRequested = false;
        _isGameLoaded = false;

        while (_isGameRunning) {
            GameState state = new GameState(_io, this);

            while (!_isNewGameRequested && _isGameRunning) {

                if (_isGameLoaded) {
                    _isGameLoaded = !_isGameLoaded;
                    _isTurnPlayer1 = !_isTurnPlayer1;
                    break;
                }

                _isGameRunning = state.isTurn(_isTurnPlayer1);

                if (!_isGameRunning || _isNewGameRequested) {
                    break;
                }

                if (_isGameSaved) {
                    _isGameSaved = !_isGameSaved;
                    continue;
                }

                if (_isLoadInvoked && _gameMemento == null) {
                    _isLoadInvoked = !_isLoadInvoked;
                } else {
                    _isTurnPlayer1 = !_isTurnPlayer1;
                }
            }

            if (_isNewGameRequested) {
                _isTurnPlayer1 = true;
                _isNewGameRequested = !_isNewGameRequested;
            }
        }
    }


    public void resetGame() {
        _players = new Player[NUMOFPLAYERS];
        _players[0] = new Player(1, NUMOFHOUSES, NUMOFSEEDS);
        _players[1] = new Player(2, NUMOFHOUSES, NUMOFSEEDS);
        _isNewGameRequested = true;
        _gameMemento = null;
        _gameHistory.clearSavedGameList();
    }


    public void quitGame() {
        _isGameRunning = false;
        (new GameState(_io, this)).endGameState();
    }


    public void saveGame() {
        _isGameSaved = true;
        _gameMemento = new GameMemento(_players, _isTurnPlayer1);
        _gameHistory.setSavedGameList(_gameMemento);
    }


    public void loadGame() {
        _gameMemento = _gameHistory.getLatestSavedGame();
        if (_gameMemento != null) {
            _players = _gameMemento.getPlayers();
            _isTurnPlayer1 = _gameMemento.getIsTurnPlayer1();
            _isGameLoaded = true;
        } else {
            _io.println("No saved game");
        }
        _isLoadInvoked = true;
    }


    public boolean getIsVerticalBoard() {
        return _isVerticalBoard;
    }

    public boolean getIsBmfRobot() {
        return _isBmfRobot;
    }

    public Player[] getPlayers() {
        return _players;
    }

    public boolean getIsGameRunning() {
        return _isGameRunning;
    }

    public boolean getIsNewGameRequested() {
        return _isNewGameRequested;
    }

}
