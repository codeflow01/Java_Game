package kalah;

import com.qualitascorpus.testsupport.IO;

public class Board {

    private Player[] _players;
    private String _hBorderLine = "+----+-------+-------+-------+-------+-------+-------+----+";
    private String _hMiddleLine = "|    |-------+-------+-------+-------+-------+-------|   " + " |";
    private IO _io;
    // fields retained for backwards compatibility
    private VerticalBoard _vBoard;
    private boolean _isVerticalBoard;


    public Board(IO io, GameApp controlApp) {
        this._players = controlApp.getPlayers();
        this._vBoard = new VerticalBoard(io, controlApp);
        this._isVerticalBoard = controlApp.getIsVerticalBoard();
        this._io = io;
    }


    public void displayBoard() {
        if (_isVerticalBoard) {
            _vBoard.displayVerticalBoard();
        } else {
            drawHorizontalBoard();
        }
    }

    private void drawHorizontalBoard() {
        _io.println(_hBorderLine);
        _io.println(getRowPlayer2());
        _io.println(_hMiddleLine);
        _io.println(getRowPlayer1());
        _io.println(_hBorderLine);
    }

    // Draw houses for P1
    private String displayHouseSeedsPlayer1(int[] numOfSeedsInHousePlayer1, String rowPlayer1) {
        for (int i = 0; i < numOfSeedsInHousePlayer1.length; i++) {
            int numSeeds = numOfSeedsInHousePlayer1[i];
            int houseNumDisplay = i + 1;
            String numSeedsDisplay = "";
            numSeedsDisplay = stringFormat(numSeeds, numSeedsDisplay);
            rowPlayer1 += " " + houseNumDisplay + "[" + numSeedsDisplay + "] |";
        }
        return rowPlayer1;
    }

    // Draw row for P1
    private String displayRowPlayer1(int numOfSeedsInStorePlayer2, int[] numOfSeedsInHousePlayer1) {
        String rowPlayer1 = "";
        String numStoreSeedsDisplayPlayer2 = "";
        numStoreSeedsDisplayPlayer2 = stringFormat(numOfSeedsInStorePlayer2, numStoreSeedsDisplayPlayer2);
        rowPlayer1 += "| " + numStoreSeedsDisplayPlayer2 + " |";
        rowPlayer1 = displayHouseSeedsPlayer1(numOfSeedsInHousePlayer1, rowPlayer1);
        rowPlayer1 += " P1 |";
        return rowPlayer1;
    }

    // Output completed row for P1
    private String getRowPlayer1() {
        int[] numOfSeedsInHousePlayer1 = _players[0].getNumOfSeedsInHouse();
        int numOfSeedsInStorePlayer2 = _players[1].getNumOfSeedsInStore();
        return displayRowPlayer1(numOfSeedsInStorePlayer2, numOfSeedsInHousePlayer1);
    }

    // Draw houses for P2
    private String displayHouseSeedsPlayer2(int[] numOfSeedsInHousePlayer2, String rowPlayer2) {
        for (int i = numOfSeedsInHousePlayer2.length - 1; i >= 0; i--) {
            int numSeeds = numOfSeedsInHousePlayer2[i];
            int houseNumDisplay = i + 1;
            String numSeedsDisplay = "";
            numSeedsDisplay = stringFormat(numSeeds, numSeedsDisplay);
            rowPlayer2 += " " + houseNumDisplay + "[" + numSeedsDisplay + "] |";
        }
        return rowPlayer2;
    }

    // Draw row for P2
    private String displayRowPlayer2(int numOfSeedsInStorePlayer1, int[] numOfSeedsInHousePlayer2) {
        String rowPlayer2 = "";
        String numStoreSeedsDisplayPlayer1 = "";
        numStoreSeedsDisplayPlayer1 = stringFormat(numOfSeedsInStorePlayer1, numStoreSeedsDisplayPlayer1);
        rowPlayer2 += "| " + "P2" + " |";
        rowPlayer2 = displayHouseSeedsPlayer2(numOfSeedsInHousePlayer2, rowPlayer2);
        rowPlayer2 += " " + numStoreSeedsDisplayPlayer1 + " |";
        return rowPlayer2;
    }

    // Output completed row for P2
    private String getRowPlayer2() {
        int[] numOfSeedsInHousePlayer2 = _players[1].getNumOfSeedsInHouse();
        int numOfSeedsInStorePlayer1 = _players[0].getNumOfSeedsInStore();
        return displayRowPlayer2(numOfSeedsInStorePlayer1, numOfSeedsInHousePlayer2);
    }

    // space for single digit number of seeds
    private String stringFormat(int numOfSeeds, String numOfSeedsDisplay) {
        return numOfSeeds < 10 ? " " + numOfSeeds : String.valueOf(numOfSeeds);
    }

}
