package academy.devdojo.springbootessentialsrr.repository.impl;

import academy.devdojo.springbootessentialsrr.dto.PessoaDTO;
import academy.devdojo.springbootessentialsrr.repository.PessoaNomeRepositoryCustom;
import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Tests for Pessoa Repository")
@Log4j2
class PessoaNomeRepositoryImplTest {

    @Autowired
    private PessoaNomeRepositoryImpl pessoaNomeRepositoryCustom;


    @Test
    public void testWithFilters() {
        PessoaDTO pessoaDTO = new PessoaDTO("Rod", 25, "Dev");

    }
}