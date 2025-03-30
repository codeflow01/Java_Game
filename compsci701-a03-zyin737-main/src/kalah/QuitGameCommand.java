package kalah;

public class QuitGameCommand implements GameCommand {

    private GameApp _controlApp;


    public QuitGameCommand(GameApp controlApp) {
        this._controlApp = controlApp;
    }


    @Override
    public void execute() {
        _controlApp.quitGame();
    }

}
