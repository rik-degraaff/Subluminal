package tech.subluminal.shared.records;

import javafx.scene.paint.Color;

public enum PlayerStatus {
    ONLINE, INLOBBY, INGAME;

    /**
     * Returns the corresponding color to display for a player.
     * @return the color which represents the status of the player.
     */
    public Color getColor() {
        Color color;

        switch (this){
            case INGAME:
                color = Color.RED;
                break;
            case ONLINE:
                color = Color.GREEN;
                break;
            case INLOBBY:
                color = Color.YELLOW;
                break;
            default:
                color = Color.GRAY;
        }
        return color;
    }
}
