package academy.devdojo.springbootessentialsrr.repository;

import academy.devdojo.springbootessentialsrr.domain.Pessoa;

import java.util.List;
import java.util.Set;

public interface PessoaNomeRepositoryCustom {
    List<Pessoa> findPessoaByName(Set<String> names);
}
