package vo;

import java.util.Objects;

public class DisciplinaVO {
    private int codigo;
    private String nome;
    private int semestre;
    private int cargahoraria;
    private CursoVO curso;

    public DisciplinaVO(){
        this.codigo = '0';
        this.nome = " ";
        this.semestre = '0';
        this.cargahoraria = '0';
        this.curso = null;
    }

    public DisciplinaVO(int codigo, String nome, int semestre, int cargahoraria, CursoVO curso) {
        this.codigo = codigo;
        this.nome = nome;
        this.semestre = semestre;
        this.cargahoraria = cargahoraria;
        this.curso = curso;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public int getCargahoraria() {
        return cargahoraria;
    }

    public void setCargahoraria(int cargahoraria) {
        this.cargahoraria = cargahoraria;
    }

    public CursoVO getCurso() {
        return curso;
    }

    public void setCurso(CursoVO curso) {
        this.curso = curso;
    }

    @Override
    public String toString() {
        return "DisciplinaVO{" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", semestre=" + semestre +
                ", cargahoraria=" + cargahoraria +
                ", curso=" + curso +
                '}';
    }

    public String toString2(){
        return "Disciplina: " +
                "Codigo=" + codigo +
                ", Nome='" + nome + '\'' +
                ", Semestre=" + semestre +
                ", Carga Horaria="+cargahoraria+
                "\n------------------------------------------------------------";
    }

}
