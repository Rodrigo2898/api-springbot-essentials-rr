package academy.devdojo.springbootessentialsrr.repository;

import academy.devdojo.springbootessentialsrr.domain.Pessoa;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PessoaCustomRepository {
    private final EntityManager entityManager;

    public PessoaCustomRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Pessoa> find(Integer id, String name, Integer idade, String profissao) {
        String query = "select P from Pessoa as p";
        String condicao = "where";

        if (id != null) {
            query += condicao + " P.id = :id";
            condicao = " and ";
        }

        if (name != null) {
            query += condicao + " P.name = :name";
            condicao = " and ";
        }

        if (idade != null) {
            query += condicao + " p.idade = :idade";
            condicao = " and ";
        }

        if (profissao != null) {
            query += condicao + " p.profissao = :profissao";
        }

        var q = entityManager.createQuery(query, Pessoa.class);

        // "Alimentando a query"
        if (id != null) {
            q.setParameter("id", id);
        }

        if (name != null) {
            q.setParameter("name", name);
        }

        if (idade != null) {
            q.setParameter("idade", idade);
        }

        if (profissao != null) {
            q.setParameter("profissao", profissao);
        }

        return q.getResultList();
    }
}
