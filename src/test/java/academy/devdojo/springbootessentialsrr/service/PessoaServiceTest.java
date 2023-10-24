package academy.devdojo.springbootessentialsrr.service;

import academy.devdojo.springbootessentialsrr.controller.PessoaController;
import academy.devdojo.springbootessentialsrr.domain.Pessoa;
import academy.devdojo.springbootessentialsrr.exceptions.BadRequestException;
import academy.devdojo.springbootessentialsrr.repository.PessoaRepository;
import academy.devdojo.springbootessentialsrr.util.PessoaCreator;
import academy.devdojo.springbootessentialsrr.util.PessoaPostRequesBodyCreator;
import academy.devdojo.springbootessentialsrr.util.PessoaPuttRequesBodyCreator;
import jakarta.validation.ConstraintViolationException;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class PessoaServiceTest {
    @InjectMocks
    private PessoaService pessoaServiceMock;
    @Mock
    private PessoaRepository pessoaRepositoryMock;

    @BeforeEach
    void setUp() {
        PageImpl<Pessoa> pessoaPage = new PageImpl<>(List.of(PessoaCreator.createValidPessoa()));
        BDDMockito.when(pessoaRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(pessoaPage);

        BDDMockito.when(pessoaRepositoryMock.findAll())
                .thenReturn(List.of(PessoaCreator.createValidPessoa()));

        BDDMockito.when(pessoaRepositoryMock.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.of(PessoaCreator.createValidPessoa()));

        BDDMockito.when(pessoaRepositoryMock.findByProfissao(ArgumentMatchers.anyString()))
                .thenReturn(List.of(PessoaCreator.createValidPessoa()));

        BDDMockito.when(pessoaRepositoryMock.save(ArgumentMatchers.any(Pessoa.class)))
                .thenReturn(PessoaCreator.createValidPessoa());

        BDDMockito.doNothing().when(pessoaRepositoryMock).delete(ArgumentMatchers.any(Pessoa.class));
    }

    @Test
    @DisplayName("findAll returns list of pessoa inside page object when successful")
    void findAll_ReturnsListOfPessoasInsidePageObject_WhenSuccessful() {
        String expectedName = PessoaCreator.createValidPessoa().getName();

        Page<Pessoa> pessoaPage = pessoaServiceMock.findAll(PageRequest.of(1, 1));

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

        List<Pessoa> pessoaList = pessoaServiceMock.findAllNonPageable();

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

        Pessoa pessoa = pessoaServiceMock.findById(1);

        Assertions.assertThat(pessoa).isNotNull();

        Assertions.assertThat(pessoa.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findById return pessoa ThrowsBadRequestException when pessoa is not found")
    void findById_ReturnPessoa_ThrowsBadRequestException_WhenPessoaIsNotFound() {
        BDDMockito.when(pessoaRepositoryMock.findById(ArgumentMatchers.anyInt()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> pessoaServiceMock.findById(1));
    }

    @Test
    @DisplayName("findByProfissao returns List of profissao when successful")
    void findByProfissao_ReturnsListOfProfissao_WhenSuccessful() {
        String expectedProfissao = PessoaCreator.createValidPessoa().getProfissao();

        List<Pessoa> pessoaList = pessoaServiceMock.findByProfissao("Vocalista");

        Assertions.assertThat(pessoaList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(pessoaList.get(0).getProfissao()).isEqualTo(expectedProfissao);
    }

    @Test
    @DisplayName("findByProfissao returns an empty List of profissao when pro3fissao is not found")
    void findByProfissao_ReturnsEmptyListOfProfissao_WhenProfissaoIsNotFound() {
        BDDMockito.when(pessoaRepositoryMock.findByProfissao(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Pessoa> pessoaList = pessoaServiceMock.findByProfissao("Vocalista");

        Assertions.assertThat(pessoaList)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save return pessoa when successful")
    void save_ReturnPessoa_WhenSuccessful() {
        Pessoa pessoa = pessoaServiceMock.save(PessoaPostRequesBodyCreator.createPessoaPostServiceRequestBody());

        Assertions.assertThat(pessoa).isNotNull().isEqualTo(PessoaCreator.createValidPessoa());
    }

//    @Test
//    @DisplayName("update return pessoa when successful")
//    void update_ReturnPessoa_WhenSuccessful() {
//        Assertions.assertThatCode(() -> pessoaServiceMock.update(1,
//                        PessoaPuttRequesBodyCreator.createPessoaPutServiceRequestBody()))
//                .doesNotThrowAnyException();
//
////        Pessoa pessoa = pessoaServiceMock.update(PessoaCreator.createValidUpdatedPessoa().getId(),
////                PessoaPuttRequesBodyCreator.createPessoaPutServiceRequestBody()).getBody();
////
////        Assertions.assertThat(pessoa).isNotNull();
//    }

    @Test
    @DisplayName("delete removes pessoa when successful")
    void delete_RemovesPessoa_WhenSuccessful() {
        Assertions.assertThatCode(() -> pessoaServiceMock.delete(1))
                .doesNotThrowAnyException();
    }
}