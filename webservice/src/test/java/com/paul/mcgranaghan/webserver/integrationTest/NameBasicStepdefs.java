package com.paul.mcgranaghan.webserver.integrationTest;

import com.paul.mcgranaghan.webserver.dto.NameBasics;
import com.paul.mcgranaghan.webserver.dto.Profession;
import com.paul.mcgranaghan.webserver.dto.TitleBasics;
import com.paul.mcgranaghan.webserver.dto.TitlePrinciple;
import com.paul.mcgranaghan.webserver.integrationTest.dao.ImdbTestDao;
import com.paul.mcgranaghan.webserver.repository.NameBasicDao;
import com.paul.mcgranaghan.webserver.service.ActorRolesService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.compress.utils.Sets;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class NameBasicStepdefs {

    @Autowired
    NameBasicDao nameBasicDao;

    @Autowired
    ImdbTestDao imdbTestDao;

    @Autowired
    ActorRolesService actorRolesService;

    @Given("the following titles exist:")
    public void the_following_titles_exist(io.cucumber.datatable.DataTable dataTable) {
        List<List<String>> rows = dataTable.asLists(String.class);

        for (List<String> column : rows) {
            imdbTestDao.saveTitleBasic(TitleBasics.builder()
                    ._id(column.get(0))
                    .primaryTitle(column.get(1))
                    .originalTitle(column.get(1))
                    .titleGenre("Action")
                    .runtimeMinutes("120")
                    .isAdult(false)
                    .titleType("Movie")
                    .build());
        }
    }

    @Given("There is a person {string} with {string} who was a {string} of {string}")
    public void there_is_a_person_name_with_nameid_who_was_an_title_role_on_title_id(String name, String nameId, String titleRole, String titleId) {
        imdbTestDao.saveNameBasic(NameBasics.builder()
                .primaryName(name)
                ._id(nameId)
                .birthYear("1979")
                .deathYear(null)
                .primaryProfessions(Sets.newHashSet(Profession.valueOf(titleRole)))
                .knownForTitles(titleId)
                .nConst(nameId)
                .build());
        imdbTestDao.saveTitlePrinciple(TitlePrinciple.builder()
                ._id(titleId)
                .tconst(titleId)
                .nconst(nameId)
                .job(titleRole)
                .build());
    }

    @When("I search for that user by the {string}")
    public void i_search_for_that_user_by_the(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("I should find person with id {string} result")
    public void i_should_find_person_with_id_result(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

}
