package academy.devdojo.springbootessentialsrr.repository;

import academy.devdojo.springbootessentialsrr.domain.Pessoa;
import academy.devdojo.springbootessentialsrr.util.PessoaCreator;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;


@DataJpaTest
@DisplayName("Tests for Pessoa Repository")
@Log4j2
class PessoaRepositoryTest {
    @Autowired
    private PessoaRepository pessoaRepository;

    @Test
    @DisplayName("Save persists pessao when successful")
    void save_PersistPessoa_WhenSuccessful() {
        Pessoa pessoaToBeSaved = PessoaCreator.createPessoaToBeSaved();
        Pessoa pessoaSaved = this.pessoaRepository.save(pessoaToBeSaved);

        Assertions.assertThat(pessoaSaved).isNotNull();

        Assertions.assertThat(pessoaSaved.getId()).isNotNull();

        Assertions.assertThat(pessoaSaved.getName()).isEqualTo(pessoaToBeSaved.getName());

        Assertions.assertThat(pessoaSaved.getIdade()).isEqualTo(pessoaToBeSaved.getIdade());

        Assertions.assertThat(pessoaSaved.getProfissao()).isEqualTo(pessoaToBeSaved.getProfissao());
    }

    @Test
    @DisplayName("Save update pessao when successful")
    void save_UpdatePessoa_WhenSuccessful() {
        Pessoa pessoaToBeSaved = PessoaCreator.createPessoaToBeSaved();
        Pessoa pessoaSaved = this.pessoaRepository.save(pessoaToBeSaved);

        pessoaSaved.setName("Phil");

        Pessoa pessoaUpdated = this.pessoaRepository.save(pessoaToBeSaved);

        log.info(pessoaUpdated.getName());

        Assertions.assertThat(pessoaUpdated).isNotNull();

        Assertions.assertThat(pessoaUpdated.getId()).isNotNull();

        Assertions.assertThat(pessoaUpdated.getName()).isEqualTo(pessoaToBeSaved.getName());

        Assertions.assertThat(pessoaUpdated.getIdade()).isEqualTo(pessoaToBeSaved.getIdade());

        Assertions.assertThat(pessoaUpdated.getProfissao()).isEqualTo(pessoaSaved.getProfissao());
    }

    @Test
    @DisplayName("Delete pessoa when successful")
    void save_RemovePessoa_WhenSuccessful() {
        Pessoa pessoaToBeSaved = PessoaCreator.createPessoaToBeSaved();
        Pessoa pessoaSaved = this.pessoaRepository.save(pessoaToBeSaved);

        this.pessoaRepository.delete(pessoaToBeSaved);

        Optional<Pessoa> pessoaOptional = this.pessoaRepository.findById(pessoaSaved.getId());

        Assertions.assertThat(pessoaOptional).isEmpty();

    }

    @Test
    @DisplayName("Find by Profissao returns Pessoa when successful")
    void findByProfissao_ReturnListOfPessoa_WhenSuccessful() {
        Pessoa pessoaToBeSaved = PessoaCreator.createPessoaToBeSaved();
        Pessoa pessoaSaved = this.pessoaRepository.save(pessoaToBeSaved);

        String name = pessoaSaved.getProfissao();

        List<Pessoa> pessoas = this.pessoaRepository.findByProfissao(name);

        Assertions.assertThat(pessoas)
                .isNotEmpty()
                .contains(pessoaSaved);

        Assertions.assertThat(pessoas).contains(pessoaSaved);
    }

    @Test
    @DisplayName("Find by Profissao returns empty Pessoa when pessoa is not found")
    void findByProfissao_ReturnEmptyList_WhenPessoaIsNotFound() {
        List<Pessoa> pessoas = this.pessoaRepository.findByProfissao("EBEB");

        Assertions.assertThat(pessoas).isEmpty();
    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when name is empty")
    void save_ThrowConstraintViolationException_WhenNameisEmpty() {
        Pessoa pessoa = new Pessoa();
        Assertions.assertThatThrownBy(() -> this.pessoaRepository.save(pessoa))
                .isInstanceOf(ConstraintViolationException.class);
    }

//    private Pessoa createPessoa() {
//      //  Pessoa p1 = new Pessoa(null, "Bruce", 62, "Vocalista");
//        return Pessoa.builder()
//                .name("Bruce")
//                .idade(62)
//                .profissao("Vocalista")
//                .build();
//        //return p1;
//    }
}