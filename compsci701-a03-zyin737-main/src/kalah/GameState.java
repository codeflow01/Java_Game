package kalah;

import com.qualitascorpus.testsupport.IO;

public class GameState {

    private GameApp _controlApp;
    private IO _io;
    // field retained for backwards compatibility
    private boolean _isBmfRobot;


    public GameState(IO io, GameApp controlApp) {
        this._controlApp = controlApp;
        this._isBmfRobot = controlApp.getIsBmfRobot();
        this._io = io;
    }


    public boolean isTurn(boolean isTurnPlayer1) {
        (new Board(_io, _controlApp)).displayBoard();
        Player currentPlayer = getCurrentPlayer(isTurnPlayer1);
        int selectedHouseIndex;
        boolean isRowPlayer1 = isTurnPlayer1;
        int[] currentRow = getCurrentRow(isRowPlayer1);

        // check houses empty
        if (hasHousesEmpty(currentPlayer)) return false;

        if (_isBmfRobot && currentPlayer.getPlayerId() == 2) {
            selectedHouseIndex = (new BmfRobot(_io, _controlApp)).getBestMoveFirst();
        } else {
            (new UserInput(_io, _controlApp)).displayUserOptions(currentPlayer);
            selectedHouseIndex = (new UserInput(_io, _controlApp)).validateUserInput(currentPlayer);
        }

        // check game states after Command execution
        if (!_controlApp.getIsGameRunning()) return false;
        if (_controlApp.getIsNewGameRequested()) return true;
        if (selectedHouseIndex == Integer.MAX_VALUE) return true;


        int seedsInHand = currentRow[selectedHouseIndex];
        currentRow[selectedHouseIndex] = 0;
        int nextSowingHouseIndex = selectedHouseIndex + 1;
        // value updated by while loop
        int lastSowingHouseIndex = selectedHouseIndex;

        while (seedsInHand != 0) {
            if (nextSowingHouseIndex < currentRow.length) {
                seedsInHand = seedsInHand - 1;
                currentRow[nextSowingHouseIndex] = currentRow[nextSowingHouseIndex] + 1;
                lastSowingHouseIndex = lastSowingHouseIndex + 1;
                nextSowingHouseIndex = nextSowingHouseIndex + 1;
            } else {
                // check enter own store
                if (isRowPlayer1 == isTurnPlayer1) {
                    seedsInHand--;
                    currentPlayer.setNumOfSeedsInStore(currentPlayer.getNumOfSeedsInStore() + 1);
                    // check if current player can continue to move
                    if (seedsInHand == 0) {
                        return isTurn(isTurnPlayer1);
                    }
                }
                // otherwise, turn to the other row
                isRowPlayer1 = !isRowPlayer1;
                currentRow = getCurrentRow(isRowPlayer1);
                // indicates current player's store in the current row
                lastSowingHouseIndex = -1;
                // the first house of the other row
                nextSowingHouseIndex = 0;
            }
        }

        // check capture
        if (isRowPlayer1 == isTurnPlayer1 && lastSowingHouseIndex != -1 && currentRow[lastSowingHouseIndex] == 1) {
            captureSeeds(currentPlayer, isRowPlayer1, currentRow, lastSowingHouseIndex);
        }

        return true;
    }

    private Player getCurrentPlayer(boolean isTurnPlayer1) {
        return isTurnPlayer1 ? _controlApp.getPlayers()[0] : _controlApp.getPlayers()[1];
    }

    private int[] getCurrentRow(boolean isRowPlayer1) {
        return isRowPlayer1 ? _controlApp.getPlayers()[0].getNumOfSeedsInHouse() : _controlApp.getPlayers()[1].getNumOfSeedsInHouse();
    }


    // case capture
    private int[] getOppositeRow(boolean isRowPlayer1) {
        return isRowPlayer1 ? _controlApp.getPlayers()[1].getNumOfSeedsInHouse() : _controlApp.getPlayers()[0].getNumOfSeedsInHouse();
    }

    private void captureSeeds(Player currentPlayer, boolean isRowPlayer1, int[] currentRow, int lastSowingHouseIndex) {
        int oppositeHouseIndex = (currentRow.length - 1) - lastSowingHouseIndex;
        int[] oppositeRow = getOppositeRow(isRowPlayer1);
        int numOfSeedsInOppositeHouse = oppositeRow[oppositeHouseIndex];
        int numOfSeedsInStoreCaptured = currentPlayer.getNumOfSeedsInStore();

        if (numOfSeedsInOppositeHouse > 0) {
            // the one seed in current player's house and row
            numOfSeedsInStoreCaptured += currentRow[lastSowingHouseIndex];
            // after capture case, this house is empty
            currentRow[lastSowingHouseIndex] = 0;
            // capture the seeds in the opposite house
            numOfSeedsInStoreCaptured += oppositeRow[oppositeHouseIndex];
            oppositeRow[oppositeHouseIndex] = 0;
            // update and store the num of seeds captured in the current player's store
            currentPlayer.setNumOfSeedsInStore(numOfSeedsInStoreCaptured);
        }
    }


    // case all houses owned by the player are empty
    private boolean hasSeedsInHouse(Player currentPlayer) {
        int[] numHouseSeedsPlayer = currentPlayer.getNumOfSeedsInHouse();
        int numSeedsInPlayerHouse = 0;
        for (int seedsInHouse : numHouseSeedsPlayer) {
            numSeedsInPlayerHouse += seedsInHouse;
        }
        return numSeedsInPlayerHouse != 0;
    }

    private boolean hasHousesEmpty(Player currentPlayer) {
        boolean hasHouseSeeds = hasSeedsInHouse(currentPlayer);
        if (!hasHouseSeeds) {
            displayResult(true);
            return true;
        }
        return false;
    }


    private void displayResult(boolean displayScore) {
        _io.println("Game over");
        (new Board(_io, _controlApp)).displayBoard();

        if (displayScore) {
            Player[] players = _controlApp.getPlayers();
            int p1Score = players[0].getScore();
            int p2Score = players[1].getScore();
            _io.println("\tplayer 1:" + p1Score);
            _io.println("\tplayer 2:" + p2Score);
            _io.println(
                    p1Score > p2Score ? "Player 1 wins!" : (p1Score < p2Score ? "Player 2 wins!" : "A tie!")
            );
        }
    }


    public void endGameState() {
        displayResult(false);
    }

}
