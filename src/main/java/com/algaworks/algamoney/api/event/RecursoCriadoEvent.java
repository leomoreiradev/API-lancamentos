package com.algaworks.algamoney.api.event;

import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

//Evento
public class RecursoCriadoEvent extends ApplicationEvent {

    private HttpServletResponse response;
    private Long codigo;

    //source é o objeto que gerou o evento
    public RecursoCriadoEvent(Object source, HttpServletResponse response,Long codigo) {
        super(source);
        this.response = response;
        this.codigo = codigo;

    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
}
