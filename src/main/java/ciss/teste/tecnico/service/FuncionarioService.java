package ciss.teste.tecnico.service;

import ciss.teste.tecnico.dto.DadosFuncionario;
import ciss.teste.tecnico.model.Funcionario;
import ciss.teste.tecnico.repository.FuncionarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    FuncionarioRepository funcionarioRepository;

    public List<Funcionario> listaFuncionarios() {
        return funcionarioRepository.findAll();
    }

    public Funcionario insereFuncionario(DadosFuncionario dados) {
        Funcionario funcionario = new Funcionario(dados);
        funcionarioRepository.save(funcionario);
        return funcionario;
    }

    public void excluiFuncionario(Long id) {
        Optional<Funcionario> optionalFuncionario = getFuncionarioById(id);
        verificaSeExisteFuncionario(optionalFuncionario);

        funcionarioRepository.delete(optionalFuncionario.get());
    }

    public Funcionario atualizaFuncionario(@Valid DadosFuncionario dados) {
        Optional<Funcionario> funcionario = getFuncionarioById(dados.id());

        verificaSeExisteFuncionario(funcionario);
        atualizaDados(dados, funcionario);
        funcionarioRepository.save(funcionario.get());

        return funcionario.get();
    }

    private Optional<Funcionario> getFuncionarioById(Long id) {
        return funcionarioRepository.findById(id);
    }

    private void verificaSeExisteFuncionario(Optional<Funcionario> funcionario) {
        if (funcionario.isEmpty()) {
            throw new RuntimeException("Funcionário não existe");
        }
    }

    private void atualizaDados(DadosFuncionario dados, Optional<Funcionario> funcionario) {
        funcionario.get().setNome(dados.nome());
        funcionario.get().setSobreNome(dados.sobreNome());
        funcionario.get().setEmail(dados.email());
        funcionario.get().setNumeroPIS(Long.parseLong(dados.numeroPIS()));
    }
}
