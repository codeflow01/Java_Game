package kalah;

import com.qualitascorpus.testsupport.IO;


// code retained for backwards compatibility
public class BmfRobot {

    private GameApp _controlApp;
    private IO _io;


    public BmfRobot(IO io, GameApp controlApp) {
        this._controlApp = controlApp;
        this._io = io;
    }


    public int getBestMoveFirst() {
        int selectedHouseIndex;
        selectedHouseIndex = getAddMove();
        if (selectedHouseIndex == -1) {
            selectedHouseIndex = getCaptureMove();
        }
        if (selectedHouseIndex == -1) {
            selectedHouseIndex = getLegalMove();
        }
        return selectedHouseIndex;
    }


    private int getLegalMove() {
        Player[] players = _controlApp.getPlayers();
        int selectedHouseIndex = -1;
        for (int i = 0; i < players[1].getNumOfSeedsInHouse().length; i++) {
            int seedsInRobot = players[1].getNumOfSeedsInHouse()[i];
            if (seedsInRobot > 0) {
                selectedHouseIndex = i;
                int selectedHouseNum = selectedHouseIndex + 1;
                _io.println("Player P2 (Robot) chooses house #" + selectedHouseNum + " because it is the first legal move");
                break;
            }
        }
        return selectedHouseIndex;
    }

    private int getAddMove() {
        Player[] players = _controlApp.getPlayers();
        int selectedHouseIndex = -1;
        for (int i = 0; i < players[1].getNumOfSeedsInHouse().length; i++) {
            int seedsInRobot = players[1].getNumOfSeedsInHouse()[i];
            if (seedsInRobot == 0) {
                continue;
            }
            // A completed cycle on the board = 2 * length + 1
            // When the condition that 'distance to store = number of seeds left in robot's row' met, get one more move
            int distanceToStore = players[1].getNumOfSeedsInHouse().length - i;
            int seedsLeftInRobot = seedsInRobot % (2 * (players[1].getNumOfSeedsInHouse().length) + 1);
            if (seedsLeftInRobot == distanceToStore) {
                selectedHouseIndex = i;
                int selectedHouseNum = selectedHouseIndex + 1;
                _io.println("Player P2 (Robot) chooses house #" + selectedHouseNum + " because it leads to an extra move");
                break;
            }
        }
        return selectedHouseIndex;
    }

    // Conditions to trigger a capture: 'distance between the chosen house and empty house = number of seeds in the chosen house' + opposite house not null
    private int getCaptureMove() {
        Player[] players = _controlApp.getPlayers();
        int selectedHouseIndex = -1;
        // start from the chosen house
        for (int i = 0; i < players[1].getNumOfSeedsInHouse().length; i++) {
            // find each empty house
            for (int j = 0; j < players[1].getNumOfSeedsInHouse().length; j++) {
                int seedsInRobot = players[1].getNumOfSeedsInHouse()[j];
                if (seedsInRobot != 0 || i == j) {
                    continue;
                }
                // Scenario 1 - capture happens within the current row
                if (i < j) {
                    if (getNumSeedsOpposite(j) == 0) {
                        continue;
                    }
                    if ((j - i) == players[1].getNumOfSeedsInHouse()[i]) {
                        selectedHouseIndex = i;
                        int selectedHouseNum = selectedHouseIndex + 1;
                        _io.println("Player P2 (Robot) chooses house #" + selectedHouseNum + " because it leads to a capture");
                        return selectedHouseIndex;
                    }
                }
                // Scenario 2 - capture happens in one wrap
                if (i > j) {
                    // Captures happen within a single wrap or less around the board
                    int distanceToEndHouse = players[1].getNumOfSeedsInHouse().length - 1 - i;
                    int numSeedsToCapture = distanceToEndHouse + players[0].getNumOfSeedsInHouse().length + 1 + j + 1;
                    if (numSeedsToCapture == players[1].getNumOfSeedsInHouse()[i]) {
                        selectedHouseIndex = i;
                        int selectedHouseNum = selectedHouseIndex + 1;
                        _io.println("Player P2 (Robot) chooses house #" + selectedHouseNum + " because it leads to a capture");
                        return selectedHouseIndex;
                    }
                }
            }
        }
        return selectedHouseIndex;
    }

    private int getNumSeedsOpposite(int emptyHouseIndex) {
        Player[] players = _controlApp.getPlayers();
        int numSeedsOpposite = -1;
        int houseIndexOpposite = players[0].getNumOfSeedsInHouse().length - 1 - emptyHouseIndex;
        return numSeedsOpposite = players[0].getNumOfSeedsInHouse()[houseIndexOpposite];
    }

}
