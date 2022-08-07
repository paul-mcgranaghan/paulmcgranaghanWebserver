package com.paul.mcgranaghan.webserver.api;

import com.mongodb.client.FindIterable;
import com.paul.mcgranaghan.webserver.dto.NameBasics;
import com.paul.mcgranaghan.webserver.dto.TitleBasics;
import com.paul.mcgranaghan.webserver.dto.TitlePrinciple;
import com.paul.mcgranaghan.webserver.dto.User;
import com.paul.mcgranaghan.webserver.repository.NameBasicDao;
import com.paul.mcgranaghan.webserver.repository.TitleBasicDao;
import com.paul.mcgranaghan.webserver.repository.TitlePrincipleDao;
import com.paul.mcgranaghan.webserver.repository.UserDao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserDao userDao;
    private final NameBasicDao nameBasicsDao;

    private final TitleBasicDao titleBasicDao;

    private final TitlePrincipleDao titlePrincipleDao;

    @Operation(summary = "List all the users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found users",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "Users not found",
                    content = @Content)})
    @GetMapping("/listAll")
    public List<User> getUsers() {
        return userDao.findAll();
    }

    @Operation(summary = "Add a user to the database")
    @PostMapping("/addUser")
    void addUser(@RequestBody User user) {
        userDao.save(user);
    }

    @Operation(summary = "Set Dark mode preference for user")
    @PostMapping("/setDarkMode")
    void setDarkMode(@RequestBody boolean darkMode, String userId) {
        userDao.setDarkMode(darkMode, userId);
    }

    @Operation(summary = "Get Name Basic by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Name Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "Name not found",
                    content = @Content)})
    @GetMapping("/getNameById")
    public NameBasics getNameBasic(String id) {
        return nameBasicsDao.findById(id);
    }

    @Operation(summary = "Get Title by Name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Title Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "Title not found",
                    content = @Content)})
    @GetMapping("/getTitleByName")
    public List<TitleBasics> getTitleBasic(String name) {
        FindIterable<TitleBasics> titleBasics = titleBasicDao.findByTitle(name);
        List<TitleBasics> titleBasicsList = new ArrayList<>();
        titleBasics.forEach(titleBasicsList::add);
        return titleBasicsList;
    }

    @Operation(summary = "Get Roles by person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Roles Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "Roles not found",
                    content = @Content)})
    @GetMapping("/getRolesByPersonId")
    public List<TitlePrinciple> getRoles(String personId) {
        FindIterable<TitlePrinciple> titlePrinciple = titlePrincipleDao.fineRolesForPerson(personId);
        List<TitlePrinciple> titlePrincipleList = new ArrayList<>();
        titlePrinciple.forEach(titlePrincipleList::add);
        return titlePrincipleList;
    }
}