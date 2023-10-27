package academy.devdojo.springbootessentialsrr.response;

import academy.devdojo.springbootessentialsrr.domain.Pessoa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class ProfissaoIdadeBetween {
    @Setter
    private List<Pessoa> pessoas;
    //private Integer idade;

    public List<Object> getPessoas() {
        return pessoas.stream()
                .map(pessoa -> {
                    Map<String, Object> pMap = new HashMap<>();
                    pMap.put("profissao", pessoa.getProfissao());
                    pMap.put("name", pessoa.getName());
                    pMap.put("idade", pessoa.getIdade());
                    return pMap;
                }).collect(Collectors.toList());
    }
}
