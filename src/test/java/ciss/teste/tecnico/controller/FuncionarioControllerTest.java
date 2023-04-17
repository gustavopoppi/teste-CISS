package ciss.teste.tecnico.controller;

import ciss.teste.tecnico.dto.DadosFuncionario;
import ciss.teste.tecnico.model.Funcionario;
import ciss.teste.tecnico.repository.FuncionarioRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
//TODO GUSTAVO se der tempo refatorar essa classe criando os testes em subclasses, testes para cada requisição (listar, inserir, excluir e listar);
class FuncionarioControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    FuncionarioRepository funcionarioRepository;

    @Autowired
    private JacksonTester<DadosFuncionario> dadosCadastroFuncionario;

    @Test
    public void tendoFuncionarioCadastradoDeveRetornarStatusOkERetornarTodosFuncionarios() throws Exception {
        Funcionario funcionario = criaFuncionario();

        when(funcionarioRepository.findAll()).thenReturn(List.of(funcionario));
        this.mockMvc.perform(
                        get("/funcionarios"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(montaJsonEsperado("Carlos", "Alberto", "carlosalberto@hotmail.com", "12345678911")));
    }

    @Test
    public void quandoNaoTiveFuncionarioCadastradoDeveRetornarStatusOkERetornarCamposNulo() throws Exception {
        when(funcionarioRepository.findAll()).thenReturn(List.of(new Funcionario()));

        MockHttpServletResponse response = this.mockMvc.perform(
                                                            get("/funcionarios")
                                                            .contentType(MediaType.APPLICATION_JSON)
                                                            .content(dadosCadastroFuncionario.write(new DadosFuncionario(null, "Carlos", "Alberto", "carlosalberto@hotmail.com", "12345678911"))
                                                                .getJson()))
                                                        .andReturn()
                                                        .getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        //TODO GUSTAVO se der tempo, montar um ENUM de funcionario e criar exemplos, igual foi feito na objective quando ficavamos testando os dados de FULANO, CICLANO;
        Assertions.assertThat(response.getContentAsString()).isEqualTo(montaJsonEsperado("null", "null", "null", "0"));
    }

    @Test
    public void quandoInserirFuncionarioEntaoDeveRetornarStatusCreated() throws Exception {
        when(funcionarioRepository.save(any())).thenReturn(criaFuncionario());

        MockHttpServletResponse response = this.mockMvc.perform(
                                                            post("/funcionarios")
                                                            .contentType(MediaType.APPLICATION_JSON)
                                                            .content(dadosCadastroFuncionario.write(new DadosFuncionario(null, "Carlos", "Alberto", "carlosalberto@hotmail.com", "12345678911"))
                                                                .getJson()))
                                                        .andReturn()
                                                        .getResponse();

        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    public void dadoUmIdDeFuncionarioExistenteEntaoExcluirFuncionario() throws Exception {
        Funcionario funcionario = criaFuncionario();
        funcionario.setId(1L);
        when(funcionarioRepository.findById(any())).thenReturn(Optional.of(funcionario));

        this.mockMvc.perform(
                        delete("/funcionarios/" + funcionario.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void dadoUmIdDeFuncionarioNuloEntaoRetornarStatus400() throws Exception {
        Funcionario funcionario = criaFuncionario();
        when(funcionarioRepository.findById(any())).thenReturn(Optional.of(funcionario));

        this.mockMvc.perform(
                        delete("/funcionarios/" + funcionario.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void dadoUmIdDeFuncionarioInexistenteEntaoNaoExcluirFuncionario() throws Exception {
        Funcionario funcionario = criaFuncionario();

        funcionario.setId(1L);
        when(funcionarioRepository.findById(any())).thenReturn(Optional.of(funcionario));

        Assertions.fail("Implementar");

//        this.mockMvc.perform(
//                        delete("/funcionarios/99")
//                                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
    }

    private Funcionario criaFuncionario() {
        Funcionario funcionario = Funcionario
                .builder()
                .nome("Carlos")
                .sobreNome("Alberto")
                .email("carlosalberto@hotmail.com")
                .numeroPIS(12345678911L)
                .build();
        return funcionario;
    }

    private String montaJsonEsperado(String nome, String sobreNome, String email, String numeroPIS) {
        return String.format("[{\"id\":null,\"nome\":%s,\"sobreNome\":%s,\"email\":%s,\"numeroPIS\":%s}]", nome, sobreNome, email, numeroPIS);
    }
}