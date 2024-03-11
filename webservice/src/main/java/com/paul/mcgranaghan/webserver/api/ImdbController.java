package com.paul.mcgranaghan.webserver.api;

import com.flagsmith.exceptions.FlagsmithClientError;
import com.flagsmith.models.Flags;
import com.paul.mcgranaghan.webserver.dto.Actor;
import com.paul.mcgranaghan.webserver.dto.User;
import com.paul.mcgranaghan.webserver.repository.ActorNameRepository;
import com.paul.mcgranaghan.webserver.service.ActorRolesService;
import com.paul.mcgranaghan.webserver.service.FlagsmithService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/imdb")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class ImdbController {
    @Autowired
    private final ActorNameRepository actorNameRepository;

    private final ActorRolesService actorRolesService;

    @Autowired
    private final FlagsmithService flagsmithService;

    @Operation(summary = "Get List of Roles for an Actor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actor Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "Actors not found",
                    content = @Content)})
    @GetMapping("/getRolesByPerson")
    public Set<String> getRolesByActor(String person, String person2) {
        log.info("Hitting api endpoint");
        return actorRolesService.resolveRolesForActor(person, person2);
    }

    @Operation(summary = "Test a feature flag")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feature enabled",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "501", description = "Feature not enable",
                    content = @Content)})
    @GetMapping("/getNewFeature")
    public String getFeatureStatus() throws FlagsmithClientError {
        boolean featureEnabled;

        try{
            Flags envronmentFlags = flagsmithService.getFeatureFlagClient().getEnvironmentFlags();
            //flagsmithService.getFeatureFlagClient().getEnvironmentFlags()
            featureEnabled = envronmentFlags.isFeatureEnabled("enable_test_feature");
        } catch (FlagsmithClientError e) {
            throw new FlagsmithClientError("Functionality controlled by feature flag, error determining flag state " + e);
        }
        if(featureEnabled){
            return "Feature Enabled";
        }
        throw new NotImplementedException("Feature not enabled");
    }


    @Operation(summary = "Elastic search lookup Actors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actor found",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "501", description = "Actor not found",
                    content = @Content)})
    @GetMapping("/getActorByName")
    public String getActorByName(String person) {
        Page<Actor> articleByAuthorName
                = actorNameRepository.findByName(person, PageRequest.of(0, 10));
        return articleByAuthorName.toString();
    }
}