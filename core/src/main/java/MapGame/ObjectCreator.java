package MapGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import ct.game.main.GameMain;

import static ct.game.main.GameMain.PPM;

public class ObjectCreator {
    private final Texture textureBlock;

    private SpriteBatch batch;
    private float textureWidth;
    private float textureHeight;

    private Vector2 position;

    private Body body;

    public ObjectCreator(Texture textureBlock, SpriteBatch batchTexture, Vector2 position, float SizeObject, float friction, String nameOfObject) {
        this.textureBlock = textureBlock;
        textureWidth = textureBlock.getWidth() * SizeObject;
        textureHeight = textureBlock.getHeight() * SizeObject;
        this.position = position;

        if (nameOfObject != null) {
            body = GameMain.getPhysicsManager().createBoxBodyForObject(position, new float[]{textureWidth,textureHeight}, friction, nameOfObject);
        }

        this.batch = batchTexture;
    }

    public void renderObject(){
        batch.draw(textureBlock, position.x - textureWidth /2 / PPM, position.y - textureHeight/2 / PPM, textureWidth / PPM, textureHeight / PPM);
    }
}
