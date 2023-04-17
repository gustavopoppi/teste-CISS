package ciss.teste.tecnico.controller;

import ciss.teste.tecnico.dto.DadosFuncionario;
import ciss.teste.tecnico.model.Funcionario;
import ciss.teste.tecnico.repository.FuncionarioRepository;
import org.junit.jupiter.api.DisplayName;
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

import static ciss.teste.tecnico.controller.Funcionario.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Autowired
    private JacksonTester<DadosFuncionario> dadosCadastroFuncionario;

    @Test
    @DisplayName("Dado requisi��o listar funcion�rios e tiver algum cadastrado ent�o deve retornar status OK e todos funcion�rios cadastrados")
    public void requisicaoListarFuncionariosQuandoTiverFuncionariosCadastrados() throws Exception {
        Funcionario funcionario = criaFuncionario(BELTRANO);

        when(funcionarioRepository.findAll()).thenReturn(List.of(funcionario));
        this.mockMvc.perform(
                        get("/funcionarios"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(montaJsonEsperado(BELTRANO.getNome(), BELTRANO.getSobreNome(), BELTRANO.getEmail(), BELTRANO.getNumeroPIS())));
    }

    @Test
    @DisplayName("Dado requisi��o listar funcion�rios e quando n�o tiver nenhum cadastrado ent�o deve retornar status OK e campos nulos")
    public void requisicaoListarFuncionariosQuandoNaoTiverFuncionariosCadastrados() throws Exception {
        when(funcionarioRepository.findAll()).thenReturn(List.of(new Funcionario()));

        MockHttpServletResponse response = this.mockMvc.perform(
                                                            get("/funcionarios")
                                                            .contentType(MediaType.APPLICATION_JSON)
                                                            .content(dadosCadastroFuncionario.write(
                                                                                                new DadosFuncionario(
                                                                                                        null,
                                                                                                        BELTRANO.getNome(),
                                                                                                        BELTRANO.getSobreNome(),
                                                                                                        BELTRANO.getEmail(),
                                                                                                        BELTRANO.getNumeroPIS()))
                                                                    .getJson()))
                                                        .andReturn()
                                                        .getResponse();

        assertThat(response.getStatus()).isEqualTo(OK.value());
        assertThat(response.getContentAsString()).isEqualTo(montaJsonEsperado(NULO.getNome(), NULO.getSobreNome(), NULO.getEmail(), NULO.getNumeroPIS()));
    }

    @Test
    public void quandoInserirFuncionarioEntaoDeveRetornarStatusCreated() throws Exception {
        when(funcionarioRepository.save(any())).thenReturn(criaFuncionario(BELTRANO));

        this.mockMvc.perform(
                        post("/funcionarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosCadastroFuncionario.write(new DadosFuncionario(null, BELTRANO.getNome(), BELTRANO.getSobreNome(), BELTRANO.getEmail(), BELTRANO.getNumeroPIS()))
                            .getJson()))
                        .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Quando inserir funcion�rio com nome menor que dois caracteres ent�o deve dar mensagem de status BadRequest")
    public void nomeMenorQueDoisCaracteres() throws Exception {
        when(funcionarioRepository.save(any())).thenReturn(criaFuncionario(NOME_MENOR_DOIS_CARACTERES));

        this.mockMvc.perform(post("/funcionarios")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(dadosCadastroFuncionario.write(new DadosFuncionario(null, NOME_MENOR_DOIS_CARACTERES.getNome(), NOME_MENOR_DOIS_CARACTERES.getSobreNome(), NOME_MENOR_DOIS_CARACTERES.getEmail(), NOME_MENOR_DOIS_CARACTERES.getNumeroPIS()))
                        .getJson()))
                    .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Quando inserir funcion�rio com nome maior que trinta caracteres ent�o deve dar mensagem de Status BadRequest")
    public void nomeMaiorQueTrintaCaracteres() throws Exception {
        when(funcionarioRepository.save(any())).thenReturn(criaFuncionario(NOME_MAIOR_TRINTA_CARACTERES));

        this.mockMvc.perform(post("/funcionarios")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(dadosCadastroFuncionario.write(new DadosFuncionario(null, NOME_MAIOR_TRINTA_CARACTERES.getNome(), NOME_MAIOR_TRINTA_CARACTERES.getSobreNome(), NOME_MAIOR_TRINTA_CARACTERES.getEmail(), NOME_MAIOR_TRINTA_CARACTERES.getNumeroPIS()))
                        .getJson()))
                    .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Quando inserir funcion�rio com sobrenome menor que dois caracteres ent�o deve dar mensagem de status BadRequest")
    public void sobreNomeMenorQueDoisCaracteres() throws Exception {
        when(funcionarioRepository.save(any())).thenReturn(criaFuncionario(SOBRENOME_MENOR_DOIS_CARACTERES));

        this.mockMvc.perform(post("/funcionarios")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(dadosCadastroFuncionario.write(new DadosFuncionario(null, SOBRENOME_MENOR_DOIS_CARACTERES.getNome(), SOBRENOME_MENOR_DOIS_CARACTERES.getSobreNome(), SOBRENOME_MENOR_DOIS_CARACTERES.getEmail(), SOBRENOME_MENOR_DOIS_CARACTERES.getNumeroPIS()))
                        .getJson()))
                    .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Quando inserir funcion�rio com nome maior que cinquenta caracteres ent�o deve dar mensagem de status BadRequest")
    public void sobreNomeMaiorQueCinquentaCaracteres() throws Exception {
        when(funcionarioRepository.save(any())).thenReturn(criaFuncionario(SOBRENOME_MAIOR_CINQUENTA_CARACTERES));

        this.mockMvc.perform(post("/funcionarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosCadastroFuncionario.write(new DadosFuncionario(null, SOBRENOME_MAIOR_CINQUENTA_CARACTERES.getNome(), SOBRENOME_MAIOR_CINQUENTA_CARACTERES.getSobreNome(), SOBRENOME_MAIOR_CINQUENTA_CARACTERES.getEmail(), SOBRENOME_MAIOR_CINQUENTA_CARACTERES.getNumeroPIS()))
                                .getJson()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void quandoInserirEmailInvalidoDeveDarMensagemDeStatusBadRequest() throws Exception {
        when(funcionarioRepository.save(any())).thenReturn(criaFuncionario(EMAIL_INVALIDO));

        this.mockMvc.perform(post("/funcionarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dadosCadastroFuncionario.write(new DadosFuncionario(null, EMAIL_INVALIDO.getNome(), EMAIL_INVALIDO.getSobreNome(), EMAIL_INVALIDO.getEmail(), EMAIL_INVALIDO.getNumeroPIS()))
                        .getJson()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void dadoUmIdDeFuncionarioExistenteEntaoExcluirFuncionario() throws Exception {
        Funcionario funcionario = criaFuncionario(BELTRANO);
        funcionario.setId(1L);
        when(funcionarioRepository.findById(any())).thenReturn(Optional.of(funcionario));

        this.mockMvc.perform(
                        delete("/funcionarios/" + funcionario.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void dadoUmIdDeFuncionarioNuloEntaoRetornarStatus400() throws Exception {
        Funcionario funcionario = criaFuncionario(BELTRANO);
        when(funcionarioRepository.findById(any())).thenReturn(Optional.of(funcionario));

        this.mockMvc.perform(
                        delete("/funcionarios/" + funcionario.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private Funcionario criaFuncionario(ciss.teste.tecnico.controller.Funcionario funcionarioEnum){
        return  Funcionario
                .builder()
                .nome(funcionarioEnum.getNome())
                .sobreNome(funcionarioEnum.getSobreNome())
                .email(funcionarioEnum.getEmail())
                .numeroPIS(Long.parseLong(funcionarioEnum.getNumeroPIS()))
                .build();
    }

    private String montaJsonEsperado(String nome, String sobreNome, String email, String numeroPIS) {
        return String.format("[{\"id\":null,\"nome\":%s,\"sobreNome\":%s,\"email\":%s,\"numeroPIS\":%s}]", nome, sobreNome, email, numeroPIS);
    }
}