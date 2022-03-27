package com.algaworks.algamoney.api.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;


//Escutador do evento RecursoCriadoEvent
@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent> {
    @Override
    public void onApplicationEvent(RecursoCriadoEvent recursoCriadoEvent) {
        HttpServletResponse response = recursoCriadoEvent.getResponse();
        Long codigo = recursoCriadoEvent.getCodigo();
        adicionaHeaderLocation(response,codigo);
    }

    private void adicionaHeaderLocation(HttpServletResponse response, Long codigo) {
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{codigo}")
                .buildAndExpand(codigo)
                .toUri();
        //Setando no header a url na chave location
        response.setHeader("Location",uri.toASCIIString());
    }
}
