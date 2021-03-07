package persistencia;

import vo.CursoDTO;
import vo.CursoVO;
import vo.DisciplinaDTO;
import vo.DisciplinaVO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaDAO extends DAO {

    private static PreparedStatement comandoIncluir;
    private static PreparedStatement comandoAlterar;
    private static PreparedStatement comandoExcluir;
    private static PreparedStatement comandoBuscarPorCodigoDisciplina;
    private static PreparedStatement comandoBuscarPorNomeDisciplina;
    private static PreparedStatement comandoBuscarPorCurso;
    private static PreparedStatement comandoListarDisciplinas;
    private static PreparedStatement comandoListarDisciplinasDoAluno;
    private CursoDAO cursoDAO; //aqui voce cria


    public DisciplinaDAO(ConexaoBD conexao) throws PersistenciaException {
        super(conexao);
        cursoDAO = new CursoDAO(conexao); //aqui voce atribui valor
        try {
            comandoIncluir = conexao.getConexao().prepareStatement("INSERT INTO disciplina (nome, semestre,cargahoraria,curso) VALUES (?,?,?,?)");
            comandoAlterar = conexao.getConexao().prepareStatement("UPDATE disciplina SET nome=?, semestre=?, cargahoraria=?, curso=? WHERE codigo=?");
            comandoExcluir = conexao.getConexao().prepareStatement("DELETE FROM disciplina WHERE codigo=?");
            comandoBuscarPorCodigoDisciplina = conexao.getConexao().prepareStatement("SELECT * FROM disciplina WHERE codigo =?");
            comandoBuscarPorNomeDisciplina = conexao.getConexao().prepareStatement("SELECT * FROM disciplina WHERE UPPER(nome) LIKE ? ORDER BY NOME LIMIT 10");
            comandoBuscarPorCurso = conexao.getConexao().prepareStatement("SELECT * FROM " +
                    "disciplina WHERE curso=?");
            comandoListarDisciplinas = conexao.getConexao().prepareStatement("SELECT codigo, nome, semestre, cargahoraria\n" +
                    "\tFROM disciplina;");
            comandoListarDisciplinasDoAluno = conexao.getConexao().prepareStatement("SELECT a.matricula as matriculaAluno,\n" +
                    "a.nome as nomeAluno,\n" +
                    "c.codigo as codigoDisciplina,\n" +
                    "c.nome as nomeDisciplina\n" +
                    "FROM aluno a , disciplina c\n" +
                    "WHERE a.curso = c.curso\n" +
                    "AND a.matricula =?");
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao incluir nova disciplina -- " + ex.getMessage());
        }
    }

    public int incluir(DisciplinaVO disciplinaVO) throws PersistenciaException {
        int retorno = 0;
        try {
            comandoIncluir.setString(1, disciplinaVO.getNome());
            comandoIncluir.setInt(2, disciplinaVO.getSemestre());
            comandoIncluir.setInt(3, disciplinaVO.getCargahoraria());
            comandoIncluir.setInt(4, disciplinaVO.getCurso().getCodigo());
            retorno = comandoIncluir.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao incluir Disciplina —- " + ex.getMessage());
        }
        return retorno;
    }

    public int alterar(DisciplinaVO disciplinaVO) throws PersistenciaException {
        int retorno = 0;
        try {
            comandoAlterar.setString(1, disciplinaVO.getNome());
            comandoAlterar.setInt(2, disciplinaVO.getSemestre());
            comandoAlterar.setInt(3, disciplinaVO.getCargahoraria());
            comandoAlterar.setInt(4, disciplinaVO.getCurso().getCodigo());
            comandoAlterar.setInt(5, disciplinaVO.getCodigo());
            retorno = comandoAlterar.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao alterar o Disciplina —-" + ex.getMessage());
        }
        return retorno;
    }

    public int excluir(int codigo) throws PersistenciaException {
        int retorno = 0;
        try {
            comandoExcluir.setInt(1, codigo);
            retorno = comandoExcluir.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao excluir a disciplina - " + ex.getMessage());
        }
        return retorno;
    }

    public DisciplinaVO buscarPorCodigo(int codigo) throws PersistenciaException {
        DisciplinaVO disciplinaVO = null;
        try {
            comandoBuscarPorCodigoDisciplina.setInt(1, codigo);
            var rs = comandoBuscarPorCodigoDisciplina.executeQuery();
            if (rs.next()) {
                disciplinaVO = buildVO(rs);
            }
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro na busca de disciplina por código - " + ex.getMessage());
        }
        return disciplinaVO;
    }

    public List<DisciplinaVO> buscarPorNomeDisciplina(String nome) throws PersistenciaException {
        var disciplinas = new ArrayList<DisciplinaVO>();
        try {
            comandoBuscarPorNomeDisciplina.setString(1, "%" + nome.trim().toUpperCase() + "%");
            var rs = comandoBuscarPorNomeDisciplina.executeQuery();
            while (rs.next()) {
                disciplinas.add(buildVO(rs));
            }
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao buscar por nome - " + ex.getMessage());
        }
        return disciplinas;
    }

    public List<DisciplinaVO> buscarPorCurso(CursoVO cursoVO) throws PersistenciaException {
        var disciplinas = new ArrayList<DisciplinaVO>();
        try {
            comandoBuscarPorCurso.setInt(1, cursoVO.getCodigo());
            var rs = comandoBuscarPorCurso.executeQuery();
            while (rs.next()) {
                disciplinas.add(buildVO(rs));
            }
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao buscar por curso - " + ex.getMessage());
        }
        return disciplinas;
    }

    public List<DisciplinaDTO> printDisciplinasDoAluno(int matricula) throws PersistenciaException {
        List<DisciplinaDTO> listaDisciplina = new ArrayList();
        try {
            comandoListarDisciplinasDoAluno.setInt(1, matricula);
            ResultSet rs = comandoListarDisciplinasDoAluno.executeQuery();
            if (rs.next()) {
                var disciplina = new DisciplinaDTO();
                disciplina.setMatriculaAluno(rs.getInt(1));
                disciplina.setNomeAluno(rs.getString(2).trim());
                disciplina.setCodigoDisciplina(rs.getInt(3));
                disciplina.setNomeDisciplina(rs.getString(4).trim());
                listaDisciplina.add(disciplina);
            }
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na busca por codigo — " + ex.getMessage());
        }
        return listaDisciplina;
    }


//    private DisciplinaVO montaDisciplinaVO(ResultSet rs) throws PersistenciaException {
//        DisciplinaVO alu = new DisciplinaVO();
//        if (rs != null) {
//            try {
//                alu.setCodigo(rs.getInt(" Codigo "));
//                alu.setNome(rs.getString("Nome").trim());
//                alu.setSemestre(rs.getInt("semestre"));
//                alu.setCargahoraria(rs.getInt("CargaHoraria"));
//                alu.setCurso(alu.getCurso());
//
//            } catch (Exception ex) {
//                throw new PersistenciaException("Erro ao acessar os dados do resultado");
//            }
//        }
//        return alu;
//    }

    public List<DisciplinaVO> printDisciplina() throws PersistenciaException {
        List<DisciplinaVO> listaDisciplina= new ArrayList();
        DisciplinaVO alu = null;
        try {
            ResultSet rs = comandoListarDisciplinas.executeQuery();
            while (rs.next()) {
                alu = new DisciplinaVO();
                alu.setCodigo(rs.getInt("codigo"));
                alu.setNome(rs.getString("nome").trim());
                alu.setSemestre(rs.getInt("semestre"));
                alu.setCargahoraria(rs.getInt("cargahoraria"));
                listaDisciplina.add(alu);
            }
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção por nome — " + ex.getMessage());
        }
        return listaDisciplina;
    }


    private DisciplinaVO buildVO(ResultSet rs) throws SQLException, PersistenciaException {
        var disciplina = new DisciplinaVO();
        if (rs != null) {
            try {
                disciplina.setCodigo(rs.getInt("codigo"));
                disciplina.setNome(rs.getString("nome"));
                disciplina.setSemestre(rs.getInt("semestre"));
                disciplina.setCargahoraria(rs.getInt("cargahoraria"));
                disciplina.setCurso(cursoDAO.buscarPorCodigo(rs.getInt("curso")));
            } catch (Exception ex) {
                throw new PersistenciaException("Erro ao acessar os dados do resultado");

            }
        }
        return disciplina;
    }
}
