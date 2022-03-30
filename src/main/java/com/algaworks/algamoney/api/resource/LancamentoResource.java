package com.algaworks.algamoney.api.resource;


import com.algaworks.algamoney.api.event.RecursoCriadoEvent;
import com.algaworks.algamoney.api.model.Categoria;
import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.repository.LancamentoRepository;
import com.algaworks.algamoney.api.repository.filter.LancamentoFilter;
import com.algaworks.algamoney.api.service.LancamentoService;
import com.algaworks.algamoney.api.service.PessoaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/lancamentos")
public class LancamentoResource {

    @Autowired
    private LancamentoService lancamentoService;

    //Para disparar o evento, publicador de evento de aplicacao
    @Autowired
    private ApplicationEventPublisher publisher;


    @PostMapping
    public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
        Lancamento lancamentoSalvo = lancamentoService.savar(lancamento);
        //this = source(objeto que gerou o evento)
        //Objeto que gerou o evento = LancamentoResource
        publisher.publishEvent(new RecursoCriadoEvent(this, response,lancamentoSalvo.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
    }

    @GetMapping
    public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable) {
        Page<Lancamento> lancamentos = lancamentoService.filtrar(lancamentoFilter, pageable);
        return lancamentos;
    }

    @GetMapping(value = "/{codigo}")
    public ResponseEntity<Lancamento> buscarPeloCodigo(@PathVariable Long codigo) {
        Lancamento lancamento = lancamentoService.findById(codigo);
        return ResponseEntity.ok().body(lancamento);
    }

    @DeleteMapping(value = "/{codigo}")
    public ResponseEntity<Void> deletar(@PathVariable Long codigo) {
        lancamentoService.deletById(codigo);
        return ResponseEntity.noContent().build();
    }

}
