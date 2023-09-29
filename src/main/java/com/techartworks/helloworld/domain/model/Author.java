package com.techartworks.helloworld.domain.model;


import jakarta.validation.constraints.NotNull;

import java.util.List;

public record Author(
        @NotNull String name,
        String personalName,
        String deathDate,
        List<String> alternateNames,
        String key,
        String birthDate,
        AuthorType type,
        RemoteIds remoteIds,
        List<Integer> photos,
        Bio bio,
        int latestRevision,
        int revision,
        Created created,
        LastModified lastModified
) {
    public record AuthorType(String key) {
    }

    public record RemoteIds(
            String wikidata,
            String isni,
            String viaf
    ) {
    }

    public record Bio(
            String type,
            String value
    ) {
    }

    public record Created(
            String type,
            String value
    ) {
    }

    public record LastModified(
            String type,
            String value
    ) {
    }
}
