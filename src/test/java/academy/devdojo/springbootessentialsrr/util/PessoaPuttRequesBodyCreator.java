package academy.devdojo.springbootessentialsrr.util;

import academy.devdojo.springbootessentialsrr.domain.Pessoa;
import academy.devdojo.springbootessentialsrr.dto.PessoaDTO;

public class PessoaPuttRequesBodyCreator {
    public static PessoaDTO createPessoaPutRequestBody() {
        return new PessoaDTO(PessoaCreator.createValidUpdatedPessoa().getName(),
                PessoaCreator.createValidUpdatedPessoa().getIdade(),
                PessoaCreator.createValidUpdatedPessoa().getProfissao());
    }

    public static Pessoa createPessoaPutServiceRequestBody() {
        return new Pessoa( null,
                PessoaCreator.createValidUpdatedPessoa().getName(),
                PessoaCreator.createValidUpdatedPessoa().getIdade(),
                PessoaCreator.createValidUpdatedPessoa().getProfissao()
        );
    }
}
