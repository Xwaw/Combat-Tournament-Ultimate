package MapGame.Maps;

import MapGame.ObjectCreator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class MAP_TestLevel{
    private final ObjectCreator objTestFloor;
    private final ObjectCreator objTestWall;

    private final Texture texture;
    private final Texture texture2;

    public MAP_TestLevel(SpriteBatch batch) {
        texture = new Texture(Gdx.files.internal("blocks/Coll_Floor1.png"));
        texture2 = new Texture(Gdx.files.internal("blocks/Coll_Wall.png"));
        objTestFloor = new ObjectCreator(texture, batch, new Vector2(0,-450), 2, 10f, "Floor");
        objTestWall = new ObjectCreator(texture2, batch, new Vector2(550,100), 1, 0f, "Wall");
    }


    public void render() {
        objTestFloor.renderObject();
        objTestWall.renderObject();
    }

    public void dispose() {
        texture.dispose();
        texture2.dispose();
    }
}
