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

    public Funcionario detalhaFuncionario(Long id) {
        return retornaFuncionarioSeExistir(id).get();
    }

    public Funcionario insereFuncionario(DadosFuncionario dados) {
        Funcionario funcionario = new Funcionario(dados);
        funcionarioRepository.save(funcionario);
        return funcionario;
    }

    public void excluiFuncionario(Long id) {
        funcionarioRepository.delete(retornaFuncionarioSeExistir(id).get());
    }

    public Funcionario atualizaFuncionario(@Valid DadosFuncionario dados) {
        Optional<Funcionario> funcionario = retornaFuncionarioSeExistir(dados.id());
        atualizaDados(dados, funcionario.get());
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

    private void atualizaDados(DadosFuncionario dados, Funcionario funcionario) {
        funcionario.setNome(dados.nome());
        funcionario.setSobreNome(dados.sobreNome());
        funcionario.setEmail(dados.email());
        funcionario.setNumeroPIS(Long.parseLong(dados.numeroPIS()));

        funcionarioRepository.save(funcionario);
    }

    private Optional<Funcionario> retornaFuncionarioSeExistir(Long id) {
        Optional<Funcionario> optionalFuncionario = getFuncionarioById(id);
        verificaSeExisteFuncionario(optionalFuncionario);
        return optionalFuncionario;
    }
}
