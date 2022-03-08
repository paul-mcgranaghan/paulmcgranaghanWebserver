package com.paul.mcgranaghan.webserver.api;

import com.paul.mcgranaghan.webserver.dto.User;
import com.paul.mcgranaghan.webserver.repository.UserDao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserDao userDao;

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

/*
    @Operation(summary = "Home Redirect with Auth0 Integration")
    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal OidcUser principal) {
        if (principal != null) {
            model.addAttribute("profile", principal.getClaims());
        }
        return "index";
    }
*/

    public boolean existsById(String id) {
        return userDao.existsById(id);
    }
}