package kalah;

import com.qualitascorpus.testsupport.IO;

// code retained for backwards compatibility
public class VerticalBoard {

    private Player[] _players;
    private String _vBorderLine = "+---------------+";
    private String _vMiddleLine = "+-------+-------+";
    private IO _io;


    public VerticalBoard(IO io, GameApp controlApp) {
        this._players = controlApp.getPlayers();
        this._io = io;
    }


    public void displayVerticalBoard() {
        drawVerticalBoard();
    }

    private void drawVerticalBoard() {
        _io.println(_vBorderLine);
        _io.println(getTopRow());
        _io.println(_vMiddleLine);
        String[] middleRows = getMiddleRows();
        for (String row : middleRows) {
            _io.println(row);
        }
        _io.println(_vMiddleLine);
        _io.println(getBottomRow());
        _io.println(_vBorderLine);
    }

    private String displayTopRow(int numOfSeedsInStorePlayer2) {
        String topRow = "";
        String numStoreSeedsDisplayPlayer2 = "";
        numStoreSeedsDisplayPlayer2 = stringFormat(numOfSeedsInStorePlayer2, numStoreSeedsDisplayPlayer2);
        topRow = "|       | P2 " + numStoreSeedsDisplayPlayer2 + " |";
        return topRow;
    }

    private String getTopRow() {
        int numOfSeedsInStorePlayer2 = _players[1].getNumOfSeedsInStore();
        return displayTopRow(numOfSeedsInStorePlayer2);
    }

    private String displayBottomRow(int numOfSeedsInStorePlayer1) {
        String bottomRow = "";
        String numStoreSeedsDisplayPlayer1 = "";
        numStoreSeedsDisplayPlayer1 = stringFormat(numOfSeedsInStorePlayer1, numStoreSeedsDisplayPlayer1);
        bottomRow = "| P1 " + numStoreSeedsDisplayPlayer1 + " |       |";
        return bottomRow;
    }

    private String getBottomRow() {
        int numOfSeedsInStorePlayer1 = _players[0].getNumOfSeedsInStore();
        return displayBottomRow(numOfSeedsInStorePlayer1);
    }

    private String[] displayMiddleRows(int[] numOfSeedsInHousePlayer1, int[] numOfSeedsInHousePlayer2) {
        String[] middleRows = new String[numOfSeedsInHousePlayer1.length];
        String numSeedsDisplayPlayer1 = "";
        String numSeedsDisplayPlayer2 = "";
        for (int i = 0; i < numOfSeedsInHousePlayer1.length; i++) {
            int houseNumPlayer1 = i + 1;
            int houseNumPlayer2 = numOfSeedsInHousePlayer1.length - i;
            int numSeedsPlayer1 = numOfSeedsInHousePlayer1[i];
            numSeedsDisplayPlayer1 = stringFormat(numSeedsPlayer1, numSeedsDisplayPlayer1);
            int numSeedsPlayer2 = numOfSeedsInHousePlayer2[houseNumPlayer2 - 1];
            numSeedsDisplayPlayer2 = stringFormat(numSeedsPlayer2, numSeedsDisplayPlayer2);
            middleRows[i] = "| " + houseNumPlayer1 + "[" + numSeedsDisplayPlayer1 + "] | " + houseNumPlayer2 + "["
                    + numSeedsDisplayPlayer2 + "] |";
        }
        return middleRows;
    }

    private String[] getMiddleRows() {
        int[] numOfSeedsInHousePlayer1 = _players[0].getNumOfSeedsInHouse();
        int[] numOfSeedsInHousePlayer2 = _players[1].getNumOfSeedsInHouse();
        return displayMiddleRows(numOfSeedsInHousePlayer1, numOfSeedsInHousePlayer2);
    }

    // space for single digit number of seeds
    private String stringFormat(int numOfSeeds, String numOfSeedsDisplay) {
        return numOfSeeds < 10 ? " " + numOfSeeds : String.valueOf(numOfSeeds);
    }

}
