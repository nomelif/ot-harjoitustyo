package file;

import java.util.ArrayList;

import map.*;

public class MapAppFile {

    private ArrayList<OptionCollection> history;
    private int active = 0;

    public MapAppFile(OptionCollection initialState) {
        history = new ArrayList<>();
        history.add(initialState);
    }

    public void update(OptionCollection state) {

        // Remove all actions that are in the future from the active element
        
        history.subList(active + 1, history.size()).clear();

        history.add(state);
        active++;

    }

    public OptionCollection state() {
        return history.get(active);
    }

    public boolean canUndo() {
        return active > 0;
    }

    public boolean canRedo() {
        return active + 1 < history.size();
    }

    public void undo() {
        if (canUndo()) {
            active--;
        }
    }

    public void redo() {
        if (canRedo()) {
            active++;
        }
    }

}
