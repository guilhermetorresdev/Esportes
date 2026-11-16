package br.gov.betim.esporte.controller;

import br.gov.betim.esporte.dto.AlmoxarifadoDevolucaoRequest;
import br.gov.betim.esporte.dto.AlmoxarifadoMovimentacaoRequest;
import br.gov.betim.esporte.model.AlmoxarifadoMovimentacao;
import br.gov.betim.esporte.service.AlmoxarifadoService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/almoxarifado")
public class AlmoxarifadoController {
    private final AlmoxarifadoService service;
    public AlmoxarifadoController(AlmoxarifadoService service) { this.service = service; }
    @GetMapping public List<AlmoxarifadoMovimentacao> listar() { return service.listar(); }
    @GetMapping("/{id}") public AlmoxarifadoMovimentacao buscar(@PathVariable Long id) { return service.buscar(id); }
    @PostMapping("/retiradas") @ResponseStatus(HttpStatus.CREATED)
    public AlmoxarifadoMovimentacao retirar(@RequestBody AlmoxarifadoMovimentacaoRequest request, Authentication auth) { return service.retirar(request, auth.getName()); }
    @PostMapping("/{id}/devolucao")
    public AlmoxarifadoMovimentacao devolver(@PathVariable Long id, @RequestBody AlmoxarifadoDevolucaoRequest request) { return service.devolver(id, request); }
}
