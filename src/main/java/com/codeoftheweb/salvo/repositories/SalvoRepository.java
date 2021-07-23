package com.codeoftheweb.salvo.repositories;

import com.codeoftheweb.salvo.models.Salvo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface SalvoRepository extends JpaRepository<Salvo, Long>{

}
