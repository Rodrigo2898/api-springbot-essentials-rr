package academy.devdojo.springbootessentialsrr.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class PessoaDTO {

    @NotEmpty(message = "The name cannot be empty")
    @NotNull(message = "The name cannot be empty")
    private String name;
    private Integer idade;
    @NotEmpty(message = "The profissao cannot be empty")
    @NotNull(message = "The profissao cannot be empty")
    private String profissao;

    public PessoaDTO() {
    }

    public PessoaDTO(String name, Integer idade, String profissao) {
        this.name = name;
        this.idade = idade;
        this.profissao = profissao;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }
}
