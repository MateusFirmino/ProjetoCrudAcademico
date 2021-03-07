package vo;

public class DisciplinaDTO {
    private int matriculaAluno;
    private String nomeAluno;
    private int codigoDisciplina;
    private String nomeDisciplina;

    public DisciplinaDTO(int matriculaAluno, String nomeAluno, int codigoDisciplina, String nomeDisciplina) {
        this.matriculaAluno = matriculaAluno;
        this.nomeAluno = nomeAluno;
        this.codigoDisciplina = codigoDisciplina;
        this.nomeDisciplina = nomeDisciplina;
    }

    public DisciplinaDTO(){

    }

    public int getMatriculaAluno() {
        return matriculaAluno;
    }

    public void setMatriculaAluno(int matriculaAluno) {
        this.matriculaAluno = matriculaAluno;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public int getCodigoDisciplina() {
        return codigoDisciplina;
    }

    public void setCodigoDisciplina(int codigoDisciplina) {
        this.codigoDisciplina = codigoDisciplina;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    @Override
    public String toString() {
        return "Disciplinas do Aluno: " +
                "Matricula do Aluno=" + matriculaAluno +
                ", Nome do Aluno='" + nomeAluno + '\'' +
                ", Codigo da Disciplina=" + codigoDisciplina +
                ", Nome da Disciplina='" + nomeDisciplina + '\'';
    }
}
