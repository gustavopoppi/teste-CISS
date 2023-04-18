package ciss.teste.tecnico.controller;

import ciss.teste.tecnico.dto.DadosFuncionario;
import ciss.teste.tecnico.model.Funcionario;
import ciss.teste.tecnico.service.FuncionarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("funcionarios")
public class FuncionarioController {

    @Autowired
    FuncionarioService funcionarioService;

    @GetMapping
    public ResponseEntity<List<Funcionario>> listaTodosFuncionarios(){
        List<Funcionario> funcionarios = funcionarioService.listaFuncionarios();
        return ResponseEntity.ok(funcionarios);
    }

    @GetMapping("{id}")
    public ResponseEntity<Funcionario> detalhaFuncionario(@PathVariable Long id){
        return ResponseEntity.ok(funcionarioService.detalhaFuncionario(id));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Funcionario> insereFuncionario(@RequestBody @Valid DadosFuncionario dados, UriComponentsBuilder uriBuilder){
        Funcionario funcionario = funcionarioService.insereFuncionario(dados);

        var uri = uriBuilder.path("/funcionarios/{id}").buildAndExpand(funcionario.getId()).toUri();
        return ResponseEntity.created(uri).body(funcionario);
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<?> excluiFuncionario(@PathVariable Long id){
        funcionarioService.excluiFuncionario(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Funcionario> atualizaFuncionario(@RequestBody @Valid DadosFuncionario dados){
        Funcionario funcionario = funcionarioService.atualizaFuncionario(dados);
        return ResponseEntity.ok(funcionario);
    }
}