package ru.javarush.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)    // чтоб конвертер автоматически применялся
public class RatingConverter implements AttributeConverter<Rating, String> {    // конвертер Enum в String и обратно


    @Override
    public String convertToDatabaseColumn(Rating rating) {
        return rating.getTitle();
    }

    @Override
    public Rating convertToEntityAttribute(String s) {
        Rating[] values = Rating.values();
        for (Rating rating : values) {
            if(rating.getTitle().equals(s)) {
                return rating;
            }
        }
        return null;
    }

}
