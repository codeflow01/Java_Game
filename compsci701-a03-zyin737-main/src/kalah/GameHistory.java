package kalah;

import java.util.List;
import java.util.ArrayList;

public class GameHistory {

    private List<GameMemento> _savedGameList;


    public GameHistory() {
        _savedGameList = new ArrayList<>();
    }


    public void setSavedGameList(GameMemento gameMemento) {
        _savedGameList.add(gameMemento);
    }


    public GameMemento getLatestSavedGame() {
        return _savedGameList.isEmpty() ? null : _savedGameList.get(_savedGameList.size() - 1);
    }


    public void clearSavedGameList() {
        _savedGameList.clear();
    }

}
