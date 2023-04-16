package ciss.teste.tecnico.controller;

import ciss.teste.tecnico.model.Funcionario;
import ciss.teste.tecnico.repository.FuncionarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class FuncionarioControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    FuncionarioRepository funcionarioRepository;

    @Test
    public void tendoFuncionarioCadastradoDeveRetornarStatusOkERetornarTodosFuncionarios() throws Exception {
        Funcionario funcionario = Funcionario
                                    .builder()
                                    .nome("Carlos")
                                    .sobreNome("Alberto")
                                    .email("carlosalberto@hotmail.com")
                                    .numeroPIS(12345678911L)
                                    .build();

        Mockito.when(funcionarioRepository.findAll()).thenReturn(List.of(funcionario));
        this.mockMvc.perform(
                get("/funcionarios"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{id:null," +
                                                    "nome:Carlos," +
                                                    "sobreNome:Alberto," +
                                                    "email:carlosalberto@hotmail.com," +
                                                    "numeroPIS:12345678911}]"));
    }

    @Test
    public void quandoNaoTiveFuncionarioCadastradoDeveRetornarStatusOkERetornarCamposNulo() throws Exception {
        Mockito.when(funcionarioRepository.findAll()).thenReturn(List.of(new Funcionario()));
        this.mockMvc.perform(
                get("/funcionarios"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{id:null," +
                                                    "nome:null," +
                                                    "sobreNome:null," +
                                                    "email:null," +
                                                    "numeroPIS:0}]"));
    }

}