package org.example.model.unit.enums;

public enum GunShot {
    HIGH(12),
    MIDDLE(9),
    LOW(6),
    ZERO(0);
    private int gunShot;

    GunShot(int gunShot) {
        this.gunShot = gunShot;
    }

    public int getGunShot() {
        return gunShot;
    }
}
