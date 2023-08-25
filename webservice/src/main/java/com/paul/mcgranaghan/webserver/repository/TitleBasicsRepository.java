package com.paul.mcgranaghan.webserver.repository;

import com.paul.mcgranaghan.webserver.dto.TitleBasics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TitleBasicsRepository extends JpaRepository<TitleBasics, Long> {
    List<TitleBasics> findBy_idIn(List<String> _id);

}
