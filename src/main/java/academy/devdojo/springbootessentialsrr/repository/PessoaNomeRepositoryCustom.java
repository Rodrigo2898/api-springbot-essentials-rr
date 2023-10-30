package academy.devdojo.springbootessentialsrr.repository;

import academy.devdojo.springbootessentialsrr.domain.Pessoa;
import academy.devdojo.springbootessentialsrr.dto.PessoaDTO;

import java.util.List;
import java.util.Set;

public interface PessoaNomeRepositoryCustom {
    List<Pessoa> getWithFilters(PessoaDTO pessoaDTO);
}
