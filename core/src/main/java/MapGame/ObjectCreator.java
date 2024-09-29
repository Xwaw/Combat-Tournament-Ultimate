package MapGame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class ObjectCreator {
    private final Texture textureBlock;
    private Vector2 position;
    private Rectangle hitBox;

    private SpriteBatch batch;
    private float textureWidth;
    private float textureHeight;

    public ObjectCreator(Texture textureBlock, SpriteBatch batchTexture, Vector2 position, float SizeObject, boolean collision) {
        this.textureBlock = textureBlock;
        this.position = position;
        textureWidth = textureBlock.getWidth() * SizeObject;
        textureHeight = textureBlock.getHeight() * SizeObject;

        if(collision) {
            this.hitBox = new Rectangle(position.x, position.y, textureWidth, textureHeight);
        }

        this.batch = batchTexture;
    }

    public void drawAndCreateObject(){
        batch.draw(textureBlock, position.x, position.y, textureWidth, textureHeight);
    }
}
