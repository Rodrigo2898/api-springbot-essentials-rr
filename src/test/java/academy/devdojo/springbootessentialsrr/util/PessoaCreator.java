package academy.devdojo.springbootessentialsrr.util;

import academy.devdojo.springbootessentialsrr.domain.Pessoa;

public class PessoaCreator {

    public static Pessoa createPessoaToBeSaved() {
       // Pessoa p1 = new Pessoa(null, "Bruce", 62, "Vocalista");
        return Pessoa.builder()
                .name("Bruce")
                .idade(62)
                .profissao("Vocalista")
                .build();
    }

    public static Pessoa createValidPessoa() {
        //Pessoa p1 = new Pessoa(null, "Bruce", 62, "Vocalista");
        return Pessoa.builder()
                .name("Bruce")
                .idade(62)
                .profissao("Vocalista")
                .id(1)
                .build();
    }

    public static Pessoa createValidUpdatedPessoa() {
        //Pessoa p1 = new Pessoa(null, "Bruce", 62, "Vocalista");
        return Pessoa.builder()
                .name("Bruce D")
                .idade(62)
                .profissao("Vocalista")
                .id(1)
                .build();
    }
}
