package kalah;

public class LoadGameCommand implements GameCommand {

    private GameApp _controlApp;


    public LoadGameCommand(GameApp controlApp) {
        this._controlApp = controlApp;
    }


    @Override
    public void execute() {
        _controlApp.loadGame();
    }

}
