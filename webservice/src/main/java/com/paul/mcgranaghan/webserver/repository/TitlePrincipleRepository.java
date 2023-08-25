package com.paul.mcgranaghan.webserver.repository;

import com.paul.mcgranaghan.webserver.dto.TitlePrinciple;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TitlePrincipleRepository extends JpaRepository<TitlePrinciple, Long> {

    List<TitlePrinciple> findByNconst(String nconst);

}

