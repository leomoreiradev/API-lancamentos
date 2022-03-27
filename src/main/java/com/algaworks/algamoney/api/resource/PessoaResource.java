package com.algaworks.algamoney.api.resource;


import com.algaworks.algamoney.api.event.RecursoCriadoEvent;
import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.PessoaRepository;
import com.algaworks.algamoney.api.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/pessoas")
public class PessoaResource {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaService pessoaService;

    //Para disparar o evento, publicador de evento de aplicacao
    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<Pessoa> listar() {
        return pessoaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
        Pessoa pessoaSalva = pessoaRepository.save(pessoa);
        //this = source(objeto que gerou o evento)
        //Objeto que gerou o evento = PessoaResource
        publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
    }

    @GetMapping(value = "/{codigo}")
    public ResponseEntity<Pessoa> buscarPeloCodigo(@PathVariable Long codigo) {
        Pessoa pessoa = pessoaRepository.findById(codigo).get();
        return pessoa != null ? ResponseEntity.ok().body(pessoa) : ResponseEntity.notFound().build();
    }

    @DeleteMapping(value = "/{codigo}")
    public ResponseEntity<Void> remover(@PathVariable Long codigo) {
        pessoaRepository.deleteById(codigo);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{codigo}")
    public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo, @Valid @RequestBody Pessoa pessoa) {
        Pessoa pessoaSalva = pessoaService.atualizar(codigo, pessoa);
        return ResponseEntity.ok().body(pessoaSalva);
    }

    @PutMapping(value = "/{codigo}/ativo")
    public ResponseEntity<Void> atualizarPropriedadeAtivo(@PathVariable Long codigo, @Valid @RequestBody Boolean ativo) {
        pessoaService.atualizarPropriedadeAtivo(codigo, ativo);
        return ResponseEntity.noContent().build();
    }

}
