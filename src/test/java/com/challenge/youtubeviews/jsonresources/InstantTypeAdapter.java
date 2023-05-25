package com.challenge.youtubeviews.jsonresources;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class InstantTypeAdapter implements JsonSerializer<Instant>, JsonDeserializer<Instant> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;

    @Override
    public JsonElement serialize(Instant instant, Type srcType,
                                 JsonSerializationContext context) {

        return new JsonPrimitive(formatter.format(instant));
    }

    @Override
    public Instant deserialize(JsonElement json, Type typeOfT,
                                     JsonDeserializationContext context) throws JsonParseException {

        return Instant.parse(json.getAsString());
    }
}
