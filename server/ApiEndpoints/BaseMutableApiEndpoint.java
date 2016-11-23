package ApiEndpoints;

import GameState.Engine;

/**
 * Created by Austin Seber2 on 11/20/2016.
 */
public abstract class BaseMutableApiEndpoint implements BaseApiEndpoint {

    Engine engine;

    public final void execute(String saveName) {

        engine = loadState(saveName);
        doExecute(engine);
        saveState(saveName);

    }

    private Engine loadState(String saveName) {

        return Engine.fromJson(saveName);

    }

    protected abstract void doExecute(Engine engine);

    private void saveState(String saveName) {

        Engine.toJson(saveName, engine);

    }

}
