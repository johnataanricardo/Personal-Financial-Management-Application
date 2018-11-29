package info.seufinanceiro.model;

import java.util.ArrayList;

public class MovementData {
    private ArrayList<MovementResponse> data;

    public MovementData() {
    }

    public ArrayList<MovementResponse> getMovementResponses() {
        return data;
    }

    public void setMovementResponses(ArrayList<MovementResponse> data) {
        this.data = data;
    }
}
