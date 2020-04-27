package file;

import java.util.ArrayList;

import map.*;

/**
 * Gson-serializable class to represent the state of the editor.
 */
public class MapAppFile {

    private ArrayList<OptionCollection> history;
    private int active = 0;

    /**
     * @param initialState The first state of the undo hierarchy
     */
    public MapAppFile(OptionCollection initialState) {
        history = new ArrayList<>();
        history.add(initialState);
    }

    /**
     * Mark a state as the current one and erase all the redo history.
     *
     * @param state New state of the editor
     */
    public void update(OptionCollection state) {

        // Remove all actions that are in the future from the active element
        
        history.subList(active + 1, history.size()).clear();

        history.add(state);
        active++;

    }

    /**
     * @return An OptionCollection representing the current state of editor
     */
    public OptionCollection state() {
        return history.get(active);
    }

    /**
     * @return Boolean answer to the question: is it possible to undo?
     */
    public boolean canUndo() {
        return active > 0;
    }

    /**
     * @return Boolean answer to the question: is it possible to redo?
     */
    public boolean canRedo() {
        return active + 1 < history.size();
    }

    /**
     * Set the previous state as the current one. If there is no previous state, do nothing.
     */
    public void undo() {
        if (canUndo()) {
            active--;
        }
    }

    /**
     * Set the next state as the current one. If there is no next state, do nothing.
     */
    public void redo() {
        if (canRedo()) {
            active++;
        }
    }

}
