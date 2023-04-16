package ciss.teste.tecnico.service;

import ciss.teste.tecnico.model.Funcionario;
import ciss.teste.tecnico.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    @Autowired
    FuncionarioRepository funcionarioRepository;

    public List<Funcionario> listaFuncionarios() {
        return funcionarioRepository.findAll();
    }
}
