package com.algaworks.algamoney.api.service;


import com.algaworks.algamoney.api.model.Lancamento;
import com.algaworks.algamoney.api.model.Pessoa;
import com.algaworks.algamoney.api.repository.LancamentoRepository;
import com.algaworks.algamoney.api.repository.PessoaRepository;
import com.algaworks.algamoney.api.repository.filter.LancamentoFilter;
import com.algaworks.algamoney.api.service.exception.PessoaInexistenteOuInativaException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LancamentoService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private LancamentoRepository lancamentoRepository;

    public Lancamento savar(Lancamento lancamento) {
        Pessoa pessoa = pessoaRepository.getById(lancamento.getPessoa().getCodigo());
        if(pessoa == null || pessoa.isInativo()) {
            throw new PessoaInexistenteOuInativaException();
        }
        return lancamentoRepository.save(lancamento);
    }

    public List<Lancamento> findAll() {
        return lancamentoRepository.findAll();
    }

    public Lancamento findById(Long codigo) {
        Optional<Lancamento> obj = lancamentoRepository.findById(codigo);
        Lancamento lancamento = obj.orElseThrow(() -> new EntityNotFoundException());
        return lancamento;
    }

    public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter) {
        return lancamentoRepository.filtrar(lancamentoFilter);
    }

    public void deletById(Long codigo) {
        Optional<Lancamento> obj = lancamentoRepository.findById(codigo);
        Lancamento lancamento = obj.orElseThrow(() -> new EntityNotFoundException());

        lancamentoRepository.delete(lancamento);

    }
}
