package br.gov.betim.esporte.controller;

import br.gov.betim.esporte.model.AlmoxarifadoMovimentacao;
import br.gov.betim.esporte.service.AlmoxarifadoService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/almoxarifado")
public class AlmoxarifadoFotoController {
    private static final long LIMITE = 5L * 1024 * 1024;
    private final AlmoxarifadoService service;
    private final Path pasta;
    public AlmoxarifadoFotoController(AlmoxarifadoService service, @Value("${app.almoxarifado.fotos.dir}") String pasta) throws IOException { this.service = service; this.pasta = Paths.get(pasta).toAbsolutePath().normalize(); Files.createDirectories(this.pasta); }

    @PostMapping("/{id}/foto")
    public Map<String, String> enviar(@PathVariable Long id, @RequestParam("arquivo") MultipartFile arquivo, @RequestParam(defaultValue = "false") boolean devolucao) throws IOException {
        AlmoxarifadoMovimentacao item = service.buscar(id);
        if (arquivo.isEmpty() || arquivo.getSize() > LIMITE || arquivo.getContentType() == null || !arquivo.getContentType().toLowerCase().startsWith("image/")) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Envie uma imagem de até 5 MB.");
        String extensao = arquivo.getOriginalFilename() == null ? ".bin" : arquivo.getOriginalFilename().replaceAll(".*(\\.[A-Za-z0-9]+)$", "$1");
        String nome = UUID.randomUUID() + extensao.toLowerCase(); Path destino = pasta.resolve(nome).normalize();
        if (!destino.startsWith(pasta)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Arquivo inválido.");
        arquivo.transferTo(destino); String caminho = "/api/almoxarifado/" + id + "/foto?devolucao=" + devolucao;
        service.definirFoto(item, caminho, devolucao); return Map.of("url", caminho);
    }

    @GetMapping("/{id}/foto")
    public ResponseEntity<Resource> foto(@PathVariable Long id, @RequestParam(defaultValue = "false") boolean devolucao) throws IOException {
        AlmoxarifadoMovimentacao item = service.buscar(id); String caminho = devolucao ? item.getFotoDevolucao() : item.getFotoRetirada();
        if (caminho == null || caminho.isBlank()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Foto não encontrada.");
        String nome = caminho.substring(caminho.lastIndexOf('/') + 1).split("\\?")[0]; Path arquivo = pasta.resolve(nome).normalize();
        if (!arquivo.startsWith(pasta) || !Files.exists(arquivo)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Foto não encontrada.");
        String tipo = Files.probeContentType(arquivo);
        MediaType mediaType = tipo == null ? MediaType.APPLICATION_OCTET_STREAM : MediaType.parseMediaType(tipo);
        return ResponseEntity.ok().contentType(mediaType).body(new UrlResource(arquivo.toUri()));
    }
}
