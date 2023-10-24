/*package academy.devdojo.springbootessentialsrr.config;

import academy.devdojo.springbootessentialsrr.domain.Pessoa;
import academy.devdojo.springbootessentialsrr.repository.PessoaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class TestConfig implements CommandLineRunner {

    private PessoaRepository pessoaRepository;

    public TestConfig(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Pessoa p1 = new Pessoa(null, "Rodrigo Feitosa", 25, "Dev");
        Pessoa p2 = new Pessoa(null, "Vanessa Gomes", 24, "dev");
        Pessoa p3 = new Pessoa(null, "Bruce Wayne", 45, "Batman");

        pessoaRepository.saveAll(Arrays.asList(p1, p2, p3));
    }
}
*/