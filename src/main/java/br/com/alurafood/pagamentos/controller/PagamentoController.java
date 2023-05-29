package br.com.alurafood.pagamentos.controller;

import br.com.alurafood.pagamentos.dto.PagamentoDTO;
import br.com.alurafood.pagamentos.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequestMapping(path = "/api/v1/aluraFood/pagamento")
public class PagamentoController {
    @Autowired
    private PagamentoService service;

    @GetMapping(value = "/todosOsPagamento")
    public Page<PagamentoDTO> todosOsPagamentos(@PageableDefault(size = 10)Pageable pageable){
        return service.obterTodos(pageable);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PagamentoDTO> buscaPorId(@PathVariable @NotNull Long id){
        PagamentoDTO dto = service.buscaId(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PagamentoDTO> novoPagamento(@RequestBody @Valid PagamentoDTO dto, UriComponentsBuilder uriComponentsBuilder){
        PagamentoDTO pagamentoDTO = service.novoPagamento(dto);
        URI endereco = uriComponentsBuilder.path("/pagamentos/{id}").buildAndExpand(pagamentoDTO.getId()).toUri();


        return  ResponseEntity.created(endereco).body(pagamentoDTO);
    }

    @PutMapping
    public ResponseEntity<PagamentoDTO> atualizaPagamento(@PathVariable @NotNull Long id, @RequestBody @Valid PagamentoDTO dto){
        PagamentoDTO pagamentoDTO = service.atualizarPagamento(id, dto);
        return ResponseEntity.ok(pagamentoDTO);
    }

    @DeleteMapping
    public ResponseEntity<PagamentoDTO> cancelaPagamento(@PathVariable @NotNull Long id){
       service.cancelarPagamento(id);
       return ResponseEntity.noContent().build();
    }


}
