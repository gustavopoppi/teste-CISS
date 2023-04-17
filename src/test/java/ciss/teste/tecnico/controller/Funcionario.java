package ciss.teste.tecnico.controller;

public enum Funcionario {

    BELTRANO("BELTRANO", "SILVA", "beltrano@hotmail.com", "12345678911"),
    NOME_MENOR_DOIS_CARACTERES("A", "PAULO", "beltrano@hotmail.com", "12345678911"),
    NOME_MAIOR_TRINTA_CARACTERES("Lorem ipsum dolor sit amet. Sed dolores dolores est velit quia aut sapiente voluptatum! Aut fuga vero non officia omnis", "PAULO", "beltrano@hotmail.com", "12345678911"),
    SOBRENOME_MENOR_DOIS_CARACTERES("BELTRANO", "A", "beltrano@hotmail.com", "12345678911"),
    SOBRENOME_MAIOR_CINQUENTA_CARACTERES("FULANO", "Lorem ipsum dolor sit amet. Sed dolores dolores est velit quia aut sapiente voluptatum! Aut fuga vero non officia omnis", "beltrano@hotmail.com", "12345678911"),
    EMAIL_INVALIDO("FULANO", "DA SILVA", "EMAIL", "12345678911"),
    NULO(null, null, null, "0");

    private final String nome;
    private final String sobreNome;
    private final String email;
    private final String numeroPIS;

    Funcionario(String nome, String sobreNome, String email, String numeroPIS) {
        this.nome = nome;
        this.sobreNome = sobreNome;
        this.email = email;
        this.numeroPIS = numeroPIS;
    }

    public String getNome() {
        return nome;
    }

    public String getSobreNome() {
        return sobreNome;
    }

    public String getEmail() {
        return email;
    }

    public String getNumeroPIS() {
        return numeroPIS;
    }
}