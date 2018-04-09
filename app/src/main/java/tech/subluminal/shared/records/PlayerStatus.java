package tech.subluminal.shared.records;

import javafx.scene.paint.Color;

public enum PlayerStatus {

    ONLINE(Color.GREEN), INLOBBY(Color.YELLOW), INGAME(Color.RED);

    private final Color color;

    PlayerStatus(Color color){
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
