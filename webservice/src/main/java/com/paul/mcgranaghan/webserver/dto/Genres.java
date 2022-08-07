package com.paul.mcgranaghan.webserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum Genres {
    DOCUMENTARY("DOCUMENTARY"),
    ANIMATION("ANIMATION"),
    COMEDY("COMEDY"),
    SHORT("SHORT"),
    ROMANCE("ROMANCE"),
    NEWS("NEWS"),
    DRAMA("DRAMA"),
    FANTASY("FANTASY"),
    HORROR("HORROR"),
    BIOGRAPHY("BIOGRAPHY"),
    MUSIC("MUSIC"),
    CRIME("CRIME"),
    FAMILY("FAMILY"),
    ADVENTURE("ADVENTURE"),
    ACTION("ACTION"),
    HISTORY("HISTORY"),
    MYSTERY("MYSTERY"),
    MUSICAL("MUSICAL"),
    WAR("WAR"),
    WESTERN("WESTERN"),
    THRILLER("THRILLER"),
    SPORT("SPORT"),
    ADULT("ADULT"),
    SCI_FI("SCI-FI"),
    FILM_NOIR("FILM-NOIR"),
    TALK_SHOW("TALK-SHOW"),
    GAME_SHOW("GAME-SHOW"),
    REALITY_TV("REALITY-TV"),
    UNKNOWN("UNKNOWN");

    public final String value;


    public static Genres getByName(String text) {
        return Arrays.stream(values())
                .filter(bl -> bl.value.equalsIgnoreCase(text))
                .findFirst().orElse(UNKNOWN);
    }
}
