package academy.devdojo.springbootessentialsrr.controller;

import academy.devdojo.springbootessentialsrr.domain.Pessoa;
import academy.devdojo.springbootessentialsrr.dto.PessoaDTO;
import academy.devdojo.springbootessentialsrr.service.PessoaService;
import academy.devdojo.springbootessentialsrr.util.PessoaCreator;
import academy.devdojo.springbootessentialsrr.util.PessoaPostRequesBodyCreator;
import academy.devdojo.springbootessentialsrr.util.PessoaPuttRequesBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class PessoaControllerTest {
    @InjectMocks
    private PessoaController pessoaControllerMock;
    @Mock
    private PessoaService pessoaServiceMock;

    @BeforeEach
    void setUp() {
        PageImpl<Pessoa> pessoaPage = new PageImpl<>(List.of(PessoaCreator.createValidPessoa()));
        BDDMockito.when(pessoaServiceMock.findAll(ArgumentMatchers.any()))
                .thenReturn(pessoaPage);

        BDDMockito.when(pessoaServiceMock.findAllNonPageable())
                .thenReturn(List.of(PessoaCreator.createValidPessoa()));

        BDDMockito.when(pessoaServiceMock.findById(ArgumentMatchers.anyInt()))
                .thenReturn(PessoaCreator.createValidPessoa());

        BDDMockito.when(pessoaServiceMock.findByProfissao(ArgumentMatchers.anyString()))
                .thenReturn(List.of(PessoaCreator.createValidPessoa()));

        BDDMockito.when(pessoaServiceMock.save(ArgumentMatchers.any(Pessoa.class)))
                .thenReturn(PessoaCreator.createValidPessoa());

        BDDMockito.when(pessoaServiceMock.update(ArgumentMatchers.anyInt(), ArgumentMatchers.any(Pessoa.class)))
                .thenReturn(PessoaCreator.createValidUpdatedPessoa());

        BDDMockito.doNothing().when(pessoaServiceMock).delete(ArgumentMatchers.anyInt());
    }

    @Test
    @DisplayName("finAll returns list of pessoa inside page object when successful")
    void findAll_ReturnsListOfPessoasInsidePageObject_WhenSuccessful() {
        String expectedName = PessoaCreator.createValidPessoa().getName();

        Page<Pessoa> pessoaPage = pessoaControllerMock.findAll(null).getBody();

        Assertions.assertThat(pessoaPage).isNotNull();

        Assertions.assertThat(pessoaPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(pessoaPage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("listAll returns list of pessoa when successful")
    void listAll_ReturnsListOfPessoas_WhenSuccessful() {
        String expectedName = PessoaCreator.createValidPessoa().getName();

        List<Pessoa> pessoaList = pessoaControllerMock.listAll().getBody();

        Assertions.assertThat(pessoaList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(pessoaList.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById return pessoa when successful")
    void findById_ReturnPessoa_WhenSuccessful() {
        Integer expectedId = PessoaCreator.createValidPessoa().getId();

        Pessoa pessoa = pessoaControllerMock.findById(1).getBody();

        Assertions.assertThat(pessoa).isNotNull();

        Assertions.assertThat(pessoa.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByProfissao returns List of profissao when successful")
    void findByProfissao_ReturnsListOfProfissao_WhenSuccessful() {
        String expectedProfissao = PessoaCreator.createValidPessoa().getProfissao();

        List<Pessoa> pessoaList = pessoaControllerMock.findByProfissao("Vocalista").getBody();

        Assertions.assertThat(pessoaList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(pessoaList.get(0).getProfissao()).isEqualTo(expectedProfissao);
    }

    @Test
    @DisplayName("findByProfissao returns an empty List of profissao when pro3fissao is not found")
    void findByProfissao_ReturnsEmptyListOfProfissao_WhenProfissaoIsNotFound() {
        BDDMockito.when(pessoaServiceMock.findByProfissao(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Pessoa> pessoaList = pessoaControllerMock.findByProfissao("Vocalista").getBody();

        Assertions.assertThat(pessoaList)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save return pessoa when successful")
    void save_ReturnPessoa_WhenSuccessful() {
        Pessoa pessoa = pessoaControllerMock.save(PessoaPostRequesBodyCreator.createPessoaPostRequestBody()).getBody();

        Assertions.assertThat(pessoa).isNotNull().isEqualTo(PessoaCreator.createValidPessoa());
    }

    @Test
    @DisplayName("update return pessoa when successful")
    void update_ReturnPessoa_WhenSuccessful() {
        Assertions.assertThatCode(() -> pessoaControllerMock.update(PessoaCreator.createValidUpdatedPessoa().getId(),
                PessoaPuttRequesBodyCreator.createPessoaPutRequestBody()).getBody())
                .doesNotThrowAnyException();

        Pessoa pessoa = pessoaControllerMock.update(PessoaCreator.createValidUpdatedPessoa().getId(),
                PessoaPuttRequesBodyCreator.createPessoaPutRequestBody()).getBody();

        Assertions.assertThat(pessoa).isNotNull();
    }

    @Test
    @DisplayName("deletePessoa removes pessoa when successful")
    void delete_RemovesPessoa_WhenSuccessful() {
        Assertions.assertThatCode(() -> pessoaControllerMock.deletePessoa(1))
                .doesNotThrowAnyException();

        ResponseEntity<Pessoa> pessoa = pessoaControllerMock.deletePessoa(1);

        Assertions.assertThat(pessoa).isNotNull();

        Assertions.assertThat(pessoa.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}