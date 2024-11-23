package MapGame.Maps;

import MapGame.ObjectCreator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class MAP_TestLevel{
    private final ObjectCreator objTestFloor;
    private final ObjectCreator objTestWall;

    private final Texture texture;
    private final Texture texture2;

    public MAP_TestLevel(SpriteBatch batch) {
        texture = new Texture(Gdx.files.internal("pngFiles/objects/objectsWithCollisions/redLongRamp.png"));
        texture2 = new Texture(Gdx.files.internal("pngFiles/objects/objectsWithCollisions/grayWall.png"));
        objTestWall = new ObjectCreator(texture2, batch, new Vector2(-500,150), 1, 0f, "Wall");
        objTestFloor = new ObjectCreator(texture, batch, new Vector2(100,-450), 1, 160f, "Floor");
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
