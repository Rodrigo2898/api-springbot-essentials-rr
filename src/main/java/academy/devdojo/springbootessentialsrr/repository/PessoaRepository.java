package academy.devdojo.springbootessentialsrr.repository;

import academy.devdojo.springbootessentialsrr.domain.Pessoa;
import academy.devdojo.springbootessentialsrr.dto.PessoaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
    List<Pessoa> findByProfissao(String profissao);

    @Query("from Pessoa p where p.profissao = :profissao and p.idade = :idade")
    List<Pessoa> findByProfissaoAndIdade(@Param("profissao") String profissao, @Param("idade") Integer idade);

//    @Query("SELECT p.profissao, p.name, p.idade FROM Pessoa p WHERE p.idade >= 25 AND p.idade <= 40 AND p.profissao = 'Dev'")
////    @Query("SELECT NEW academy.devdojo.springbootessentialsrr.dto.PessoaDTO (p.profissao, p.name) FROM Pessoa p WHERE p.idade = 20 AND p.idade = 30")
//    List<Object> findByProfissaoAndIdadeBetween20And40();
}
