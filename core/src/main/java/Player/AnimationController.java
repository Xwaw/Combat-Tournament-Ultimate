package Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class AnimationController {
    public void parseJson(String path){
        FileHandle file = Gdx.files.internal(path); // Podaj ścieżkę do swojego JSONa
        JsonReader jsonReader = new JsonReader();
        JsonValue fileJSON = jsonReader.parse(file);

        JsonValue framesJSON = fileJSON.get("frames");

        for(int i = 1; i <= framesJSON.size; i++){
            JsonValue symbolJSON = framesJSON.get("symbol" + i);
            JsonValue frameJSON = symbolJSON.get("frame");

            int x = frameJSON.getInt("x");
            int y = frameJSON.getInt("y");
            int width = frameJSON.getInt("w");
            int height = frameJSON.getInt("h");
        }
    }
}
