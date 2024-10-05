package MapGame.Maps;

import MapGame.ObjectCreator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MAP_TestLevel{
    private ObjectCreator objTestFloor;

    private Texture texture;

    public MAP_TestLevel(SpriteBatch batch) {
        texture = new Texture(Gdx.files.internal("blocks/Coll_BlockFloor1.png"));
        objTestFloor = new ObjectCreator(texture, batch, new Vector2(0, -150), 1,true);
    }


    public void render() {
        objTestFloor.renderObject();
    }

    public void dispose() {
        texture.dispose();
    }
}
