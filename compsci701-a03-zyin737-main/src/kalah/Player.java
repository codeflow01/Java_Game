package kalah;

public class Player {

    private int _playerId;
    private int[] _numOfSeedsInHouse;
    private int _numOfSeedsInStore;


    public Player(int playerId, int numOfHouses, int numOfSeedsPerHouse) {
        this._playerId = playerId;
        this._numOfSeedsInHouse = new int[numOfHouses];
        for (int i = 0; i < numOfHouses; i++) {
            this._numOfSeedsInHouse[i] = numOfSeedsPerHouse;
        }
        this._numOfSeedsInStore = 0;
    }


    public Player(Player other) {
        this._playerId = other._playerId;
        this._numOfSeedsInHouse = other._numOfSeedsInHouse.clone();
        this._numOfSeedsInStore = other._numOfSeedsInStore;
    }


    public int getPlayerId() {
        return _playerId;
    }

    public int[] getNumOfSeedsInHouse() {
        return _numOfSeedsInHouse;
    }

    public int getNumOfSeedsInStore() {
        return _numOfSeedsInStore;
    }

    public void setNumOfSeedsInStore(int _numOfSeedsInStore) {
        this._numOfSeedsInStore = _numOfSeedsInStore;
    }

    public int getScore() {
        int numSeedsInPlayerHouses = 0;
        for (int seedsInHouse : this._numOfSeedsInHouse) {
            numSeedsInPlayerHouses += seedsInHouse;
        }
        return numSeedsInPlayerHouses + this._numOfSeedsInStore;
    }

}
