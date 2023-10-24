package academy.devdojo.springbootessentialsrr.util;

import academy.devdojo.springbootessentialsrr.domain.Pessoa;
import academy.devdojo.springbootessentialsrr.dto.PessoaDTO;

public class PessoaPostRequesBodyCreator {
    public static PessoaDTO createPessoaPostRequestBody() {
        return new PessoaDTO(PessoaCreator.createPessoaToBeSaved().getName(),
                PessoaCreator.createPessoaToBeSaved().getIdade(),
                PessoaCreator.createPessoaToBeSaved().getProfissao());
//        return Pessoa.builder()
//                .name(PessoaCreator.createPessoaToBeSaved().getName())
//                .idade(PessoaCreator.createPessoaToBeSaved().getIdade())
//                .profissao(PessoaCreator.createPessoaToBeSaved().getProfissao())
//                .build();
    }

    public static Pessoa createPessoaPostServiceRequestBody() {
        return new Pessoa( null,
                PessoaCreator.createPessoaToBeSaved().getName(),
                PessoaCreator.createPessoaToBeSaved().getIdade(),
                PessoaCreator.createPessoaToBeSaved().getProfissao());
    }
}
