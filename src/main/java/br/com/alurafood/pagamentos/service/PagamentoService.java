package br.com.alurafood.pagamentos.service;

import br.com.alurafood.pagamentos.dto.PagamentoDTO;
import br.com.alurafood.pagamentos.model.PagamentoModel;
import br.com.alurafood.pagamentos.model.Status;
import br.com.alurafood.pagamentos.repository.PagamentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class PagamentoService {
    @Autowired
    private PagamentoRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    public Page<PagamentoDTO> obterTodos(Pageable pageable){
        return repository.findAll(pageable).map(p -> modelMapper.map(p, PagamentoDTO.class));
    }

    public PagamentoDTO buscaId(Long id){
        PagamentoModel pagamentoModel = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        return  modelMapper.map(pagamentoModel, PagamentoDTO.class);
    }

    public PagamentoDTO novoPagamento(PagamentoDTO dto){
        PagamentoModel pagamentoModel = modelMapper.map(dto, PagamentoModel.class);
        pagamentoModel.setStatus(Status.CRIADO);
        repository.save(pagamentoModel);

        return modelMapper.map(pagamentoModel, PagamentoDTO.class);
    }

    public PagamentoDTO atualizarPagamento(Long id, PagamentoDTO dto){
        PagamentoModel pagamentoModel = modelMapper.map(dto, PagamentoModel.class);
        pagamentoModel.setId(id);
        pagamentoModel = repository.save(pagamentoModel);

        return  modelMapper.map(pagamentoModel, PagamentoDTO.class);
    }

    public void cancelarPagamento(Long id){
         repository.deleteById(id);
    }
}
