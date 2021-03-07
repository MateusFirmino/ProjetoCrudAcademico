package vo;

public class CursoDTO {
    private int codigoCurso;
    private String nomeCurso;
    private int codigoDisciplina;
    private String nomeDisciplina;

    public CursoDTO(int codigoCurso, String nomeCurso, int codigoDisciplina, String nomeDisciplina) {
        this.codigoCurso = codigoCurso;
        this.nomeCurso = nomeCurso;
        this.codigoDisciplina = codigoDisciplina;
        this.nomeDisciplina = nomeDisciplina;
    }

    public CursoDTO(){

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
        return "Curso: " +
                " Codigo do Curso=" + codigoCurso +
                ", Nome do Curso='" + nomeCurso + '\'' +
                ", Codigo da Disciplina=" + codigoDisciplina +
                ", Nome da Disciplina='" + nomeDisciplina + '\'';
    }
}
