package MapGame.Maps;

import MapGame.ObjectCreator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MAP_TestLevel{
    private ObjectCreator objTestFloor;
    private SpriteBatch batch;

    private Texture TESTBLOCK;

    public MAP_TestLevel(SpriteBatch batch) {
        this.batch = batch;

        TESTBLOCK = new Texture(Gdx.files.internal("blocks/Coll_BlockFloor1.png"));
        objTestFloor = new ObjectCreator(TESTBLOCK, batch, new Vector2(0, 0), 2,true);
    }


    public void render() {
        objTestFloor.drawAndCreateObject();
    }

    public void dispose() {
        TESTBLOCK.dispose();
    }
}
