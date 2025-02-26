package com.thishotel.model;

public class Admin extends User {

//    TODO : da capire come implementare e cosa
//    private String adminLevel; // Super Admin, Normal Admin, ecc.

    private boolean hasFullAccess;

    public boolean isHasFullAccess() {
        return hasFullAccess;
    }

    public void setHasFullAccess(boolean hasFullAccess) {
        this.hasFullAccess = hasFullAccess;
    }
}
