package ciss.teste.tecnico.model;

import ciss.teste.tecnico.dto.DadosFuncionario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String sobreNome;
    private String email;

    @Column(name = "numero_pis")
    private long numeroPIS;

    public Funcionario(DadosFuncionario dados) {
        this.nome = dados.nome();
        this.sobreNome = dados.sobreNome();
        this.email = dados.email();
        this.numeroPIS = Long.parseLong(dados.numeroPIS());
    }
}
