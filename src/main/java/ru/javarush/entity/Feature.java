package ru.javarush.entity;

import static java.util.Objects.isNull;

public enum Feature {
    TRAILERS("Trailers"),
    COMMENTARIES("Commentaries"),
    DELETED_SCENES("Deleted Scenes"),
    BEHIND_THE_SCENES("Behind the Scenes");

    private final String title;

    Feature(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static Feature getFeatureByValue(String value) {
        if(value.isEmpty() || isNull(value)) {
            return null;
        }
        Feature[] features = Feature.values();
        for (Feature feature : features) {
            if(feature.title.equals(value)) {
                return feature;
            }
        }
        return null;
    }

}
