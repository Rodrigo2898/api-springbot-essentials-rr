package academy.devdojo.springbootessentialsrr.controller;

import academy.devdojo.springbootessentialsrr.domain.Pessoa;
import academy.devdojo.springbootessentialsrr.dto.PessoaDTO;
import academy.devdojo.springbootessentialsrr.service.PessoaService;
import academy.devdojo.springbootessentialsrr.util.DateUtil;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@RestController
@RequestMapping(value = "/pessoas")
public class PessoaController {
    private final DateUtil dateUtil;
    private final PessoaService pessoaService;

    public PessoaController(DateUtil dateUtil, PessoaService pessoaService) {
        this.dateUtil = dateUtil;
        this.pessoaService = pessoaService;
    }

    @GetMapping
    public ResponseEntity<Page<Pessoa>> findAll(Pageable pageable) {
        Page<Pessoa> pessoas = pessoaService.findAll(pageable);
        return ResponseEntity.ok().body(pessoas);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<Pessoa>> listAll() {
        List<Pessoa> pessoas = pessoaService.findAllNonPageable();
//        log.info(dateUtil.formatLocalDateTime(LocalDateTime.now()));
        return ResponseEntity.ok().body(pessoas);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Pessoa> findById(@PathVariable Integer id) {
        Pessoa pessoa = pessoaService.findById(id);
        return ResponseEntity.ok().body(pessoa);
    }

//    @GetMapping(value = "/profissao/{profissao}")
//    public ResponseEntity<List<Pessoa>> findByProfissao(@PathVariable String profissao) {
//        List<Pessoa> pessoas = pessoaService.findByProfissao(profissao);
//        return ResponseEntity.ok().body(pessoas);
//    }

    @GetMapping(value = "/category")
    public ResponseEntity<List<Pessoa>> findByProfissao(@RequestParam String profissao) {
        List<Pessoa> pessoas = pessoaService.findByProfissao(profissao);
        return ResponseEntity.ok().body(pessoas);
    }

    @PostMapping
    public ResponseEntity<Pessoa> save(@RequestBody @Valid PessoaDTO pessoaDTO) {
        Pessoa pessoa = new Pessoa();
        BeanUtils.copyProperties(pessoaDTO, pessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaService.save(pessoa));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Pessoa> deletePessoa(@PathVariable(value = "id") Integer id) {
        pessoaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Pessoa> update(@PathVariable Integer id, @RequestBody @Valid PessoaDTO pessoaDTO) {
        Pessoa pessoa = new Pessoa();
        BeanUtils.copyProperties(pessoaDTO, pessoa);
        pessoa = pessoaService.update(id, pessoa);
        return ResponseEntity.ok().body(pessoa);
    }
}
