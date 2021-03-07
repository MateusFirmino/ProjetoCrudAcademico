package vo;

public class AlunoDTO {
    private int matricula;
    private String nome;
    private int codigoCurso;
    private String nomeCurso;

    public AlunoDTO(int matricula, String nome, int codigoCurso, String nomeCurso) {
        this.matricula = matricula;
        this.nome = nome;
        this.codigoCurso = codigoCurso;
        this.nomeCurso = nomeCurso;
    }

    public AlunoDTO(){

    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCodigoCurso() {
        return codigoCurso;
    }

    public void setCodigoCurso(int codigoCurso) {
        this.codigoCurso = codigoCurso;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    @Override
    public String toString() {
        return "Aluno: " +
                "Matricula= " + matricula +
                ", Nome='" + nome + '\'' +
                ", Curso: "+
                " Codigo do Curso=" + codigoCurso +
                ", Nome do Curso='" + nomeCurso + '\'' +
                "\n------------------------------------------------------------";

    }
}
