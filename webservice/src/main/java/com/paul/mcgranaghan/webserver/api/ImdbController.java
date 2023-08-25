package com.paul.mcgranaghan.webserver.api;

import com.paul.mcgranaghan.webserver.dto.User;
import com.paul.mcgranaghan.webserver.service.ActorRolesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/imdb")
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class ImdbController {

    private final ActorRolesService actorRolesService;

    @Operation(summary = "Get List of Roles for an Actor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actor Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "Actor not found",
                    content = @Content)})
    @GetMapping("/getRolesByPerson")
    public Set<String> getRolesByActor(String person, String person2) {
        log.info("Hitting api endpoint");
        return actorRolesService.resolveRolesForActor(person, person2);
    }
}