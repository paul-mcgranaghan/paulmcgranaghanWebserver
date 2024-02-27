package com.paul.mcgranaghan.webserver.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Profession {
    SOUNDTRACK("Soundtrack"),
    ACTOR("Actor"),
    ACTRESS("Actress"),
    MISCELLANEOUS("Miscellaneous"),
    PRODUCER("Producer"),
    DIRECTOR("Director"),
    WRITER("Writer"),
    MUSIC_DEPARTMENT("Music Department"),
    MAKE_UP_DEPARTMENT("Make Up Department"),
    ASSISTANT_DIRECTOR("Assistant Director"),
    COMPOSER("Composer"),
    ART_DEPARTMENT("Art Department"),
    ANIMATION_DEPARTMENT("Animation Department"),
    CINEMATOGRAPHER("Cinematographer"),
    TALENT_AGENT("Talent Agent"),
    PODCASTER("Podcaster"),
    MANAGER("Manager"),
    SCRIPT_DEPARTMENT("Script Department"),
    ART_DIRECTOR("Art Director"),
    PRODUCTION_DEPARTMENT("Production Department"),
    PUBLICIST("Publicist"),
    LOCATION_MANAGEMENT("Location Management"),
    VISUAL_EFFECTS("Visual Effects"),
    SPECIAL_EFFECTS("Special Effects"),
    COSTUME_DESIGNER("Costume Designer"),
    CASTING_DIRECTOR("Casting Director"),
    MUSIC_ARTIST("Music Artist"),
    TRANSPORTATION_DEPARTMENT("Transportation Department"),
    PRODUCTION_DESIGNER("Production Designer"),
    EDITORIAL_DEPARTMENT("Editorial Department"),
    CASTING_DEPARTMENT("Casting Department"),
    EXECUTIVE("Executive"),
    LEGAL("Legal"),
    SOUND_DEPARTMENT("Sound Department"),
    EDITOR("Editor"),
    COSTUME_DEPARTMENT("Costume Department"),
    ASSISTANT("Assistant"),
    STUNTS("Stunts"),
    CAMERA_DEPARTMENT("Camera Department"),
    SET_DECORATOR("Set Decorator"),
    PRODUCTION_MANAGER("Production Manager"),
    CHOREOGRAPHER("Choreographer");

    public String description;



}
