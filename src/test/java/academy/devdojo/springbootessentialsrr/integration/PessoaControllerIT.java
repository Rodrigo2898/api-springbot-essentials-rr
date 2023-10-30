package academy.devdojo.springbootessentialsrr.integration;

import academy.devdojo.springbootessentialsrr.domain.DevRodUser;
import academy.devdojo.springbootessentialsrr.domain.Pessoa;
import academy.devdojo.springbootessentialsrr.dto.PessoaDTO;
import academy.devdojo.springbootessentialsrr.repository.DevRodUserRepository;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PessoaControllerIT {
    @Autowired
    @Qualifier(value = "testRestTemplateRoleUserCreator")
    private TestRestTemplate testRestTemplateRoleUser;
    @Autowired
    @Qualifier(value = "testRestTemplateRoleAdminCreator")
    private TestRestTemplate testRestTemplateRoleAdmin;
//    @LocalServerPort
//
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private DevRodUserRepository devRodUserRepository;
    private static final DevRodUser USER = DevRodUser.builder()
            .name("test")
            .password("{bcrypt}$2a$10$kdo7ZAXa4rCjiHPAJZ4n0eJZHI3UeSsyz083s7OovNltoErtrhgc6")
            .username("test")
            .authorities("ROLE_USER")
            .build();

    private static final DevRodUser ADMIN = DevRodUser.builder()
            .name("Rodrigo Feitosa")
            .password("{bcrypt}$2a$10$kdo7ZAXa4rCjiHPAJZ4n0eJZHI3UeSsyz083s7OovNltoErtrhgc6")
            .username("rodrigo")
            .authorities("ROLE_ADMIN")
            .build();
    @TestConfiguration
    @Lazy
    static class Config {
        @Bean(name = "testRestTemplateRoleUserCreator")
        public TestRestTemplate testRestTemplateRoleUserCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:"+port)
                    .basicAuthentication("test", "user1");
            return new TestRestTemplate(restTemplateBuilder);
        }

        @Bean(name = "testRestTemplateRoleAdminCreator")
        public TestRestTemplate testRestTemplateRoleAdminCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:"+port)
                    .basicAuthentication("rodrigo", "user1");
            return new TestRestTemplate(restTemplateBuilder);
        }
    }

    @Test
    @DisplayName("findAll returns list of pessoa inside page object when successful")
    void findAll_ReturnsListOfPessoasInsidePageObject_WhenSuccessful() {
        Pessoa savedPessoa = pessoaRepository.save(PessoaCreator.createPessoaToBeSaved());
        devRodUserRepository.save(USER);

        String expectedName = savedPessoa.getName();

        PageableResponse<Pessoa> pessoaPage = testRestTemplateRoleUser.exchange("/pessoas", HttpMethod.GET, null,
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
        devRodUserRepository.save(USER);

        String expectedName = savedPessoa.getName();

        List<Pessoa> pessoaList = testRestTemplateRoleUser.exchange("/pessoas/all", HttpMethod.GET, null,
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
        devRodUserRepository.save(USER);

        Integer expectedId = savedPessoa.getId();

        Pessoa pessoa = testRestTemplateRoleUser.getForObject("/pessoas/{id}", Pessoa.class, expectedId);

        Assertions.assertThat(pessoa).isNotNull();

        Assertions.assertThat(pessoa.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByProfissao returns List of profissao when successful")
    void findByProfissao_ReturnsListOfProfissao_WhenSuccessful() {
        Pessoa savedPessoa = pessoaRepository.save(PessoaCreator.createPessoaToBeSaved());
        devRodUserRepository.save(USER);

        String expectedProfissao = savedPessoa.getProfissao();
        String url = String.format("/pessoas/category?profissao=%s", expectedProfissao);

        List<Pessoa> pessoaList = testRestTemplateRoleUser.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Pessoa>>() {
                }).getBody();

        Assertions.assertThat(pessoaList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(pessoaList.get(0).getProfissao()).isEqualTo(expectedProfissao);
    }

    @Test
    @DisplayName("findByProfissaoAndIdade returns List of profissao and idade when successful")
    void findByProfissaoAndIdade_ReturnsListOfProfissaoAndIdade_WhenSuccessful() {
        Pessoa savedPessoa = pessoaRepository.save(PessoaCreator.createPessoaToBeSaved());
        devRodUserRepository.save(USER);

        String expectedProfissao = savedPessoa.getProfissao();
        Integer expectedIdade = savedPessoa.getIdade();

        String url = String.format("/pessoas/custom?profissao=%s&idade=%d", expectedProfissao, expectedIdade);

        List<Pessoa> pessoaList = testRestTemplateRoleUser.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Pessoa>>() {
                }).getBody();

        Assertions.assertThat(pessoaList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(pessoaList.get(0).getProfissao()).isEqualTo(expectedProfissao);
        Assertions.assertThat(pessoaList.get(0).getIdade()).isEqualTo(expectedIdade);
    }


    @Test
    @DisplayName("findWithFilters returns list of pessoa when successful")
    void findWithFilters_ReturnsListOfPessoas_WhenSuccessful() {
        Pessoa savedPessoa = pessoaRepository.save(PessoaCreator.createPessoaToBeSaved());
        devRodUserRepository.save(USER);

        String expectedName = savedPessoa.getName();
        Integer expectedIdade = savedPessoa.getIdade();
        String expectedProfissao = savedPessoa.getProfissao();

        List<Pessoa> pessoaList = testRestTemplateRoleUser.exchange("/pessoas/getAll", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Pessoa>>() {
                }).getBody();

        Assertions.assertThat(pessoaList).isNotNull();

        Assertions.assertThat(pessoaList)
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(pessoaList.get(0).getName()).isEqualTo(expectedName);
        Assertions.assertThat(pessoaList.get(0).getIdade()).isEqualTo(expectedIdade);
        Assertions.assertThat(pessoaList.get(0).getProfissao()).isEqualTo(expectedProfissao);
    }



    @Test
    @DisplayName("findByProfissao returns an empty List of profissao when pro3fissao is not found")
    void findByProfissao_ReturnsEmptyListOfProfissao_WhenProfissaoIsNotFound() {
        devRodUserRepository.save(USER);
        List<Pessoa> pessoaList = testRestTemplateRoleUser.exchange("/pessoas/category?profissao=dev", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Pessoa>>() {
                }).getBody();

        Assertions.assertThat(pessoaList)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save return pessoa when successful")
    void save_ReturnPessoa_WhenSuccessful() {
        devRodUserRepository.save(ADMIN);

        PessoaDTO pessoaPostRequestBody = PessoaPostRequesBodyCreator.createPessoaPostRequestBody();

        ResponseEntity<Pessoa> pessoaResponseEntity= testRestTemplateRoleAdmin.postForEntity("/pessoas/admin", pessoaPostRequestBody, Pessoa.class);

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
        devRodUserRepository.save(ADMIN);

        savedPessoa.setName("TTTETE");

        ResponseEntity<Void> pessoaResponseEntity= testRestTemplateRoleAdmin.exchange("/pessoas/admin/{id}",
                HttpMethod.DELETE, null, Void.class, savedPessoa.getId());

        Assertions.assertThat(pessoaResponseEntity).isNotNull();
        Assertions.assertThat(pessoaResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("deletePessoa returns 403 when user is not admin")
    void delete_Return403_WhenSuccessful() {
        Pessoa savedPessoa = pessoaRepository.save(PessoaCreator.createPessoaToBeSaved());
        devRodUserRepository.save(USER);

        savedPessoa.setName("TTTETE");

        ResponseEntity<Void> pessoaResponseEntity= testRestTemplateRoleUser.exchange("/pessoas/admin/{id}",
                HttpMethod.DELETE, null, Void.class, savedPessoa.getId());

        Assertions.assertThat(pessoaResponseEntity).isNotNull();
        Assertions.assertThat(pessoaResponseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}