package kalah;

public class NewGameCommand implements GameCommand {

    private GameApp _controlApp;


    public NewGameCommand(GameApp controlApp) {
        this._controlApp = controlApp;
    }


    @Override
    public void execute() {
        _controlApp.resetGame();
    }

}
