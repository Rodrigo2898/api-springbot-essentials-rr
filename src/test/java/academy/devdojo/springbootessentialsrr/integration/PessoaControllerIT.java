package academy.devdojo.springbootessentialsrr.integration;

import academy.devdojo.springbootessentialsrr.domain.Pessoa;
import academy.devdojo.springbootessentialsrr.dto.PessoaDTO;
import academy.devdojo.springbootessentialsrr.repository.PessoaRepository;
import academy.devdojo.springbootessentialsrr.util.PessoaCreator;
import academy.devdojo.springbootessentialsrr.util.PessoaPostRequesBodyCreator;
import academy.devdojo.springbootessentialsrr.util.PessoaPuttRequesBodyCreator;
import academy.devdojo.springbootessentialsrr.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Collections;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PessoaControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @LocalServerPort
    private int port;
    @Autowired
    private PessoaRepository pessoaRepository;

    @Test
    @DisplayName("findAll returns list of pessoa inside page object when successful")
    void findAll_ReturnsListOfPessoasInsidePageObject_WhenSuccessful() {
        Pessoa savedPessoa = pessoaRepository.save(PessoaCreator.createPessoaToBeSaved());

        String expectedName = savedPessoa.getName();

        PageableResponse<Pessoa> pessoaPage = testRestTemplate.exchange("/pessoas", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Pessoa>>() {
                }).getBody();

        Assertions.assertThat(pessoaPage).isNotNull();

        Assertions.assertThat(pessoaPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(pessoaPage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("listAll returns list of pessoa when successful")
    void listAll_ReturnsListOfPessoas_WhenSuccessful() {
        Pessoa savedPessoa = pessoaRepository.save(PessoaCreator.createPessoaToBeSaved());

        String expectedName = savedPessoa.getName();

        List<Pessoa> pessoaList = testRestTemplate.exchange("/pessoas/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Pessoa>>() {
                }).getBody();

        Assertions.assertThat(pessoaList).isNotNull();

        Assertions.assertThat(pessoaList)
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(pessoaList.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById return pessoa when successful")
    void findById_ReturnPessoa_WhenSuccessful() {
        Pessoa savedPessoa = pessoaRepository.save(PessoaCreator.createPessoaToBeSaved());

        Integer expectedId = savedPessoa.getId();

        Pessoa pessoa = testRestTemplate.getForObject("/pessoas/{id}", Pessoa.class, expectedId);

        Assertions.assertThat(pessoa).isNotNull();

        Assertions.assertThat(pessoa.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByProfissao returns List of profissao when successful")
    void findByProfissao_ReturnsListOfProfissao_WhenSuccessful() {
        Pessoa savedPessoa = pessoaRepository.save(PessoaCreator.createPessoaToBeSaved());

        String expectedProfissao = savedPessoa.getProfissao();
        String url = String.format("/pessoas/category?profissao=%s", expectedProfissao);

        List<Pessoa> pessoaList = testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Pessoa>>() {
                }).getBody();

        Assertions.assertThat(pessoaList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(pessoaList.get(0).getProfissao()).isEqualTo(expectedProfissao);
    }

    @Test
    @DisplayName("findByProfissao returns an empty List of profissao when pro3fissao is not found")
    void findByProfissao_ReturnsEmptyListOfProfissao_WhenProfissaoIsNotFound() {

        List<Pessoa> pessoaList = testRestTemplate.exchange("/pessoas/category?profissao=dev", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Pessoa>>() {
                }).getBody();

        Assertions.assertThat(pessoaList)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save return pessoa when successful")
    void save_ReturnPessoa_WhenSuccessful() {
        PessoaDTO pessoaPostRequestBody = PessoaPostRequesBodyCreator.createPessoaPostRequestBody();

        ResponseEntity<Pessoa> pessoaResponseEntity= testRestTemplate.postForEntity("/pessoas", pessoaPostRequestBody, Pessoa.class);

        Assertions.assertThat(pessoaResponseEntity).isNotNull();
        Assertions.assertThat(pessoaResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(pessoaResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(pessoaResponseEntity.getBody().getId()).isNotNull();
    }

//    @Test
//    @DisplayName("update return pessoa when successful")
//    void update_ReturnPessoa_WhenSuccessful() {
//        Pessoa savedPessoa = pessoaRepository.save(PessoaCreator.createPessoaToBeSaved());
//
//        savedPessoa.setName("TTTETE");
//
//        ResponseEntity<Pessoa> pessoaResponseEntity= testRestTemplate.exchange("/pessoas",
//                HttpMethod.PUT, new HttpEntity<>(savedPessoa),Pessoa.class);
//
//        Assertions.assertThat(pessoaResponseEntity).isNotNull();
//        Assertions.assertThat(pessoaResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
//    }

    @Test
    @DisplayName("deletePessoa removes pessoa when successful")
    void delete_RemovesPessoa_WhenSuccessful() {
        Pessoa savedPessoa = pessoaRepository.save(PessoaCreator.createPessoaToBeSaved());

        savedPessoa.setName("TTTETE");

        ResponseEntity<Void> pessoaResponseEntity= testRestTemplate.exchange("/pessoas/{id}",
                HttpMethod.DELETE, null, Void.class, savedPessoa.getId());

        Assertions.assertThat(pessoaResponseEntity).isNotNull();
        Assertions.assertThat(pessoaResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}