package academy.devdojo.springbootessentialsrr.repository.impl;

import academy.devdojo.springbootessentialsrr.domain.Pessoa;
import academy.devdojo.springbootessentialsrr.dto.PessoaDTO;
import academy.devdojo.springbootessentialsrr.repository.PessoaNomeRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class PessoaNomeRepositoryImpl implements PessoaNomeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Pessoa> getWithFilters(PessoaDTO pessoaDTO) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pessoa> criteriaQuery = criteriaBuilder.createQuery(Pessoa.class);
        Root<Pessoa> pessoaRoot = criteriaQuery.from(Pessoa.class);
        List<Predicate> predicates = new ArrayList<>();


        /*
        * Verificando se os parametros foram preenchidos, caso for,
        * adicionamos na lista de predicados
        * */
        if (pessoaDTO.getName() != null) {
            predicates.add(criteriaBuilder.like(pessoaRoot.get("name"), "%" + pessoaDTO.getName() + "%"));
        }

        if (pessoaDTO.getIdade() != null) {
            predicates.add(criteriaBuilder.like(pessoaRoot.get("idade"), "%" + pessoaDTO.getIdade() + "%"));
        }

        if (pessoaDTO.getProfissao() != null) {
            predicates.add(criteriaBuilder.like(pessoaRoot.get("profissao"), "%" + pessoaDTO.getProfissao() + "%"));
        }

        /*
        * Verificamos os predicados para adicionar a cláusula where
        * */
        if (!predicates.isEmpty()) {
            criteriaQuery.select(pessoaRoot).where(predicates.stream().toArray(Predicate[]::new));
        }

        TypedQuery<Pessoa> queryResult = entityManager.createQuery(criteriaQuery);


        return queryResult.getResultList();
    }
}
