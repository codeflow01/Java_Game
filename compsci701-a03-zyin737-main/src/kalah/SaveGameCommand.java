package kalah;

public class SaveGameCommand implements GameCommand {

    private GameApp _controlApp;


    public SaveGameCommand(GameApp controlApp) {
        this._controlApp = controlApp;
    }


    @Override
    public void execute() {
        _controlApp.saveGame();
    }

}
