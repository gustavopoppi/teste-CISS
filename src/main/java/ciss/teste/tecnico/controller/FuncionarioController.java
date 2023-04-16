package ciss.teste.tecnico.controller;

import ciss.teste.tecnico.model.Funcionario;
import ciss.teste.tecnico.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("funcionarios")
public class FuncionarioController {

    @Autowired
    FuncionarioService funcionarioService;

    @GetMapping
    public ResponseEntity listaFuncionarios(){
        List<Funcionario> funcionarios = funcionarioService.listaFuncionarios();
        return ResponseEntity.ok(funcionarios);
    }
}