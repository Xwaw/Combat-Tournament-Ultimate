package Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;

public class AnimationController {
    String pathJSON;
    Texture spriteSheet;

    public AnimationController(String pathJSON, Texture spriteSheet) {
        this.pathJSON = pathJSON;
        this.spriteSheet = spriteSheet;
    }

    private ArrayList<int[]> parseFlashJSONIntoArrayList(String path){
        ArrayList<int[]> framesPositions = new ArrayList<>();

        FileHandle file = Gdx.files.internal(path); //Json path
        JsonReader jsonReader = new JsonReader();
        JsonValue fileJSON = jsonReader.parse(file);

        JsonValue framesJSON = fileJSON.get("frames");

        for(int i = 0; i <= framesJSON.size - 1; i++){
            JsonValue symbolJSON = framesJSON.get("Symbol " + i);
            JsonValue frameJSON = symbolJSON.get("frame");

            int x = frameJSON.getInt("x");
            int y = frameJSON.getInt("y");
            int width = frameJSON.getInt("w");
            int height = frameJSON.getInt("h");

            framesPositions.add(new int[]{x, y, width, height});
        }

        return framesPositions;
    }

    private Array<TextureRegion> createTextureRegionArray(ArrayList<int[]> framesPositions, Texture texture) {
        Array<TextureRegion> frames = new Array<>();

        for (int[] framePosition : framesPositions) {
            TextureRegion frame = new TextureRegion(texture, framePosition[0], framePosition[1], framePosition[2], framePosition[3]);
            frames.add(frame);
        }

        return frames;
    }

    public Animation<TextureRegion> createAnimation(float frameDuration) {
        Array<TextureRegion> standFrames = createTextureRegionArray(parseFlashJSONIntoArrayList(pathJSON), spriteSheet);

        return new Animation<>(frameDuration, standFrames, Animation.PlayMode.LOOP);
    }
}
