package com.thishotel.model;

public class Cleaner extends User {

    private String cleaningArea;    //To extend with stanza da pulire, stanze da pulire urgentemente, danno in piscina da pulire,
                                    // inolre da implementare la cosa delle notifiche, la possibilita di un inserviente di prendere l urgenza, etc

    public String getCleaningArea() {
        return cleaningArea;
    }

    public void setCleaningArea(String cleaningArea) {
        this.cleaningArea = cleaningArea;
    }

}
