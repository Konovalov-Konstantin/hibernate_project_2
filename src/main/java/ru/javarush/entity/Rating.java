package ru.javarush.entity;

public enum Rating {
    G ("G"),
    PG ("PG"),
    PG_13("PG-13"),
    R ("R"),
    NC_17 ("NC-17");

    private final String title;

    public String getTitle() {
        return title;
    }

    Rating(String title) {
        this.title = title;
    }

}
