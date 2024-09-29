package MapGame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class LevelEditorManager {
    private final Texture textureBlock;
    private Vector2 positionOnMap;
    private Rectangle hitBoxOfObj;

    private SpriteBatch batchTexture;
    private float textureWidth;
    private float textureHeight;

    public LevelEditorManager(Texture textureBlock, SpriteBatch batchTexture, Vector2 positionOnMap, boolean collision) {
        this.textureBlock = textureBlock;
        this.positionOnMap = positionOnMap;

        if(collision) {
            textureWidth = textureBlock.getWidth();
            textureHeight = textureBlock.getHeight();
            this.hitBoxOfObj = new Rectangle(positionOnMap.x, positionOnMap.y, textureWidth, textureHeight);
        }

        this.batchTexture = batchTexture;
    }

    public void drawAndCreateObject(){
        batchTexture.draw(textureBlock, positionOnMap.x, positionOnMap.y, textureWidth, textureHeight);
    }
}
