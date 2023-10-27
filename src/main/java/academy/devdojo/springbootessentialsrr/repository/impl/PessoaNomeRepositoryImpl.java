package academy.devdojo.springbootessentialsrr.repository.impl;

import academy.devdojo.springbootessentialsrr.domain.Pessoa;
import academy.devdojo.springbootessentialsrr.repository.PessoaNomeRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PessoaNomeRepositoryImpl implements PessoaNomeRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    public PessoaNomeRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Pessoa> findPessoaByName(Set<String> names) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pessoa> pessoaCriteriaQuery = criteriaBuilder.createQuery(Pessoa.class);
        Root<Pessoa> pessoaRoot = pessoaCriteriaQuery.from(Pessoa.class);

        Path<String> namePath = pessoaRoot.get("name");

        List<Predicate> predicates = new ArrayList<>();
        for (String name : names) {
            predicates.add(criteriaBuilder.like(namePath, name));
        }
        pessoaCriteriaQuery.select(pessoaRoot)
                .where(criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()])));
        return entityManager.createQuery(pessoaCriteriaQuery)
                .getResultList();
    }
}
