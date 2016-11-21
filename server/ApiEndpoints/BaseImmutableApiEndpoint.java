package ApiEndpoints;

import GameState.Engine;

/**
 * Created by Austin Seber2 on 11/20/2016.
 */
public abstract class BaseImmutableApiEndpoint implements BaseApiEndpoint {

    Engine engine;

    public final void execute(String saveName) {

        engine = loadState(saveName);
        doExecute(engine);

    }

    private final Engine loadState(String saveName) {

        return Engine.fromJson(saveName);

    }

    protected abstract void doExecute(Engine engine);

}
