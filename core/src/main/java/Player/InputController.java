package Player;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class InputController implements InputProcessor {
    private boolean isPressedD;
    private boolean isPressedS;
    private boolean isPressedA;
    private boolean isPressedW;

    public boolean isPressedD() {
        return isPressedD;
    }

    public boolean isPressedW() {
        return isPressedW;
    }

    public boolean isPressedA() {
        return isPressedA;
    }

    public boolean isPressedS() {
        return isPressedS;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.D -> isPressedD = true;
            case Input.Keys.W -> isPressedW = true;
            case Input.Keys.S -> isPressedS = true;
            case Input.Keys.A -> isPressedA = true;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.D -> isPressedD = false;
            case Input.Keys.W -> isPressedW = false;
            case Input.Keys.S -> isPressedS = false;
            case Input.Keys.A -> isPressedA = false;
        }

        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
