package org.rib.gamemap.model;

import lombok.Data;

@Data
public class Coordinates {

    private int x;
    private int y;

    public Coordinates(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

}
