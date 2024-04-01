    package com.julio.awpag.domain.service;
    
    
    import com.julio.awpag.domain.Exception.NegocioException;
    import com.julio.awpag.domain.model.Cliente;
    import com.julio.awpag.domain.repository.ClienteRepository;
    import lombok.AllArgsConstructor;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    
    @Service
    @AllArgsConstructor
    public class CadastroClienteService {
    
        private final ClienteRepository clienteRepository;
    
        @Transactional
        public Cliente salvar(Cliente cliente){
            boolean emailUso = clienteRepository.findByEmail(cliente.getEmail()).filter(c -> !c.equals(cliente))
                    .isPresent();
    
            if (emailUso){
                throw new NegocioException("Email já cadastrado! ");
            }
            return clienteRepository.save(cliente);
        }
    
        @Transactional
        public void excluir(Long id){
            clienteRepository.deleteById(id);
        }
    
    
        public Cliente buscar(Long id){
            return clienteRepository.findById(id)
                    .orElseThrow(() -> new NegocioException("Cliente não encontrado! "));
        }
    
    }
