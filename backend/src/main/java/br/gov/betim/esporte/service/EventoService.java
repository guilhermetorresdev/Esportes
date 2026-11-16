package br.gov.betim.esporte.service;

import br.gov.betim.esporte.dto.EventoRequest;
import br.gov.betim.esporte.model.Evento;
import br.gov.betim.esporte.repository.EventoRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EventoService {

    private final EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    public List<Evento> listar() {
        return eventoRepository.findAll();
    }

    public Evento criar(EventoRequest request) {
        Evento evento = new Evento();
        preencher(evento, request);
        return eventoRepository.save(evento);
    }

    public Evento atualizar(Long id, EventoRequest request) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado."));
        preencher(evento, request);
        return eventoRepository.save(evento);
    }

    public void excluir(Long id) {
        if (!eventoRepository.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado.");
        eventoRepository.deleteById(id);
    }

    public Evento buscar(Long id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Evento não encontrado."));
    }

    private void preencher(Evento evento, EventoRequest request) {
        if (request == null || vazio(request.nome()) || vazio(request.categoria()) || request.dataEvento() == null
                || vazio(request.hora()) || vazio(request.local()) || vazio(request.responsavel())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Preencha nome, categoria, data, hora, local e responsável.");
        }
        evento.setNome(request.nome());
        evento.setCategoria(request.categoria());
        evento.setDescricao(request.descricao());
        evento.setDataEvento(request.dataEvento());
        evento.setHora(request.hora());
        evento.setLocal(request.local());
        evento.setResponsavel(request.responsavel());
        evento.setMateriais(request.materiais());
    }

    private static boolean vazio(String valor) { return valor == null || valor.isBlank(); }
}
