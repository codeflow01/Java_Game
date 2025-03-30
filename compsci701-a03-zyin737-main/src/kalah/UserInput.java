package kalah;

import com.qualitascorpus.testsupport.IO;

public class UserInput {

    private Player _currentPlayer;
    private GameApp _controlApp;
    private boolean _isMoving;
    private String _userInput;
    private int _selectedHouseIndex;
    private IO _io;


    public UserInput(IO io, GameApp controlApp) {
        this._controlApp = controlApp;
        this._io = io;
    }


    public int validateUserInput(Player currentPlayer) {
        this._currentPlayer = currentPlayer;
        _isMoving = true;
        while (_isMoving) {
            _userInput = _io.readFromKeyboard("").toLowerCase();

            GameCommand command = determineUserOption(_userInput);
            if (command != null) {
                command.execute();
                // act as a flag
                return Integer.MAX_VALUE;
            }

            if (!_isMoving) break;

            handleInvalidInput();
        }

        return _selectedHouseIndex;
    }

    private void handleInvalidInput() {
        try {
            int _selectedHouseNum = Integer.parseInt(_userInput);
            _selectedHouseIndex = _selectedHouseNum - 1;
            if (_selectedHouseNum < 1 || _selectedHouseNum > 6) throw new NumberFormatException();

            int selectedHouseSeeds = _currentPlayer.getNumOfSeedsInHouse()[_selectedHouseIndex];
            if (selectedHouseSeeds == 0) throw new IllegalArgumentException();

            _isMoving = false;

        } catch (NumberFormatException e) {
            _io.println("The house number does not exist.");
            (new Board(_io, _controlApp)).displayBoard();
            displayUserOptions(_currentPlayer);
        } catch (IllegalArgumentException e) {
            _io.println("House is empty. Move again.");
            (new Board(_io, _controlApp)).displayBoard();
            displayUserOptions(_currentPlayer);
        }
    }


    public GameCommand determineUserOption(String userInput) {
        switch (userInput) {
            case "q":
                return new QuitGameCommand(_controlApp);
            case "n":
                return new NewGameCommand(_controlApp);
            case "s":
                return new SaveGameCommand(_controlApp);
            case "l":
                return new LoadGameCommand(_controlApp);
            default:
                return null;
        }
    }

    public void displayUserOptions(Player currentPlayer) {
        _io.println("Player P" + currentPlayer.getPlayerId());
        _io.println("    (1-6) - house number for move");
        _io.println("    N - New game");
        _io.println("    S - Save game");
        _io.println("    L - Load game");
        _io.println("    q - Quit");
        _io.print("Choice:");
    }

}
