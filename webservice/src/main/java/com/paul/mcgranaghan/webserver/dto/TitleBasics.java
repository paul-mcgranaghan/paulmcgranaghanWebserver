package com.paul.mcgranaghan.webserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.BooleanUtils;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
public class TitleBasics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public String _id;
    public String titleType;
    public String primaryTitle;
    public String originalTitle;
    public boolean adult;
    public String startYear;
    public String endYear;
    public String runtimeMinutes;
    public Set<Genres> titleGenre;

    @BsonCreator
    public TitleBasics(@BsonProperty(value = "_id") String _id, @BsonProperty(value = "titleType") String titleType,
                       @BsonProperty(value = "primaryTitle") String primaryTitle, @BsonProperty(value = "originalTitle") String originalTitle,
                       @BsonProperty(value = "isAdult") Integer isAdult,
                       @BsonProperty(value = "startYear") String startYear,
                       @BsonProperty(value = "endYear") String endYear, @BsonProperty(value = "runtimeMinutes") String runtimeMinutes,
                       @BsonProperty(value = "genres") String genres) {


        this._id = _id;
        this.titleType = titleType;
        this.primaryTitle = primaryTitle;
        this.originalTitle = originalTitle;
        this.adult = handleStringOrInt(isAdult);
        this.startYear = startYear;
        this.endYear = endYear;
        this.runtimeMinutes = runtimeMinutes;
        this.titleGenre = Arrays.stream(genres.split(","))
                .map(p -> Genres.getByName(p.toUpperCase())).collect(Collectors.toSet());
    }

    private boolean handleStringOrInt(Integer isAdult) {
/*        if (isAdultS != null) {
            return isAdult.equals("1");

        }*/
        return BooleanUtils.toBoolean((int) isAdult, 1, 0);
    }
}
