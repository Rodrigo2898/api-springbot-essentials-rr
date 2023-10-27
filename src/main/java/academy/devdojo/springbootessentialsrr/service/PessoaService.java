package academy.devdojo.springbootessentialsrr.service;

import academy.devdojo.springbootessentialsrr.domain.Pessoa;
import academy.devdojo.springbootessentialsrr.dto.PessoaDTO;
import academy.devdojo.springbootessentialsrr.exceptions.BadRequestException;
import academy.devdojo.springbootessentialsrr.repository.PessoaRepository;
import academy.devdojo.springbootessentialsrr.service.exceptions.DatabaseException;
import academy.devdojo.springbootessentialsrr.service.exceptions.ResourceNotFoundException;
import academy.devdojo.springbootessentialsrr.service.exceptions.ResourceNotFoundExceptionProfissao;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public Page<Pessoa> findAll(Pageable pageable) {
        return pessoaRepository.findAll(pageable);
    }

    public List<Pessoa> findAllNonPageable() {
        return pessoaRepository.findAll();
    }

//    public Pessoa findById(Integer id) {
//        return pessoaRepository.findById(id)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pessoa not found"));
//    }

    public Pessoa findById(Integer id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Pessoa not Found"));
    }

    public List<Pessoa> findByProfissao(String profissao) {
        return pessoaRepository.findByProfissao(profissao);
    }



    public List<Pessoa> findByProfissaoAndIdade(String profissao, Integer idade) {
        return pessoaRepository.findByProfissaoAndIdade(profissao, idade);
    }


//    public List<Object> findByProfissaoAndIdadeBetween20And40() {
//        return pessoaRepository.findByProfissaoAndIdadeBetween20And40();
//    }

    public List<Pessoa> findPessoaByName(Set<String> name) {
        return pessoaRepository.findPessoaByName(name);
    }


    @Transactional
    public Pessoa save(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

//    @Transactional
//    public void delete(Integer id) {
//        pessoaRepository.deleteById(id);
//    }

    @Transactional
    public void delete(Integer id) {
        try {
            pessoaRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Transactional
    public Pessoa update(Integer id, Pessoa obj) {
        try {
            Pessoa entity = pessoaRepository.getReferenceById(id);
            updateData(entity, obj);
            return pessoaRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Pessoa entity, Pessoa obj) {
        entity.setName(obj.getName());
        entity.setIdade(obj.getIdade());
        entity.setProfissao(obj.getProfissao());
    }

}
