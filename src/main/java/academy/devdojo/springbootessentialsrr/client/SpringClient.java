package academy.devdojo.springbootessentialsrr.client;

import academy.devdojo.springbootessentialsrr.domain.Pessoa;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        ResponseEntity<Pessoa> pessoaResponseEntity =
                new RestTemplate().getForEntity("http://localhost:8080/pessoas/1", Pessoa.class);
        log.info(pessoaResponseEntity);

        Pessoa obj = new RestTemplate().getForObject("http://localhost:8080/pessoas/1", Pessoa.class);

        log.info(obj);

        ResponseEntity<List<Pessoa>> exchange = new RestTemplate().exchange(
                "http://localhost:8080/pessoas/all",
                HttpMethod.GET, null
                , new ParameterizedTypeReference<List<Pessoa>>() {
                });
        log.info(exchange.getBody());

        //Pessoa pessoaNovo = Pessoa.builder().name("Frank Castle").idade(20).profissao("Justiceiro").build();
        Pessoa p1 = new Pessoa(null, "Frank Castle", 40, "Justiceiro");
        Pessoa pessoaSaved = new RestTemplate().postForObject("http://localhost:8080/pessoas/", p1, Pessoa.class);

        log.info("Pessoa saved {}", pessoaSaved);
    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
