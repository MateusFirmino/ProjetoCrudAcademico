package vo;

public class AlunoVO {
    private int matricula;
    private String nome;
    private String nomeMae;
    private String nomePai;
    private EnumSexo sexo;
    private EnderecoVO endereco;
    private CursoVO curso;

    public AlunoVO() {
        this.endereco = new EnderecoVO();
        this.matricula = 0;
        this.nome = " ";
        this.nomeMae = " ";
        this.nomePai = " ";
        this.sexo = EnumSexo.FEMINIMO;
        this.curso = null;
    }

    public AlunoVO(int matricula, String nome, EnumSexo sexo, CursoVO curso) {
        this();
        this.matricula = matricula;
        this.nome = nome;
        this.sexo = sexo;
        this.curso = curso;
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

    public String getNomeMae() {
        return nomeMae;
    }

    public void setNomeMae(String nomeMae) {
        this.nomeMae = nomeMae;
    }

    public String getNomePai() {
        return nomePai;
    }

    public void setNomePai(String nomePai) {
        this.nomePai = nomePai;
    }

    public EnumSexo getSexo() {
        return sexo;
    }

    public void setSexo(EnumSexo sexo) {
        this.sexo = sexo;
    }

    public EnderecoVO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoVO endereco) {
        this.endereco = endereco;
    }

    public CursoVO getCurso() {
        return curso;
    }

    public void setCurso(CursoVO curso) {
        this.curso = curso;
    }

    @Override
    public String toString() {
        return "AlunoVO{" +
                "matricula=" + matricula +
                ", nome='" + nome + '\'' +
                ", nomeMae='" + nomeMae + '\'' +
                ", nomePai='" + nomePai + '\'' +
                ", sexo=" + sexo +
                ", endereco=" + endereco +
                ", curso=" + curso +
                '}';
    }

    public String toString2() {
        return "Aluno: " +
                "Matricula=" + matricula +
                ", Nome='" + nome + '\'' +
                ", Sexo=" + sexo +
                "\n------------------------------------------------------------";
    }

    public String toString3() {
        return "Curso: " +
                "Codigo=" + curso.getCodigo() +
                ", Nome do Curso='" + curso.getNome() + '\'' +
                "Aluno: " +
                "Matricula=" + matricula +
                ", Nome='" + nome + '\'' +
                "\n------------------------------------------------------------";
    }

}
