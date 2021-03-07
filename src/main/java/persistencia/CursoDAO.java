package persistencia;

import vo.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO extends DAO {
    private static PreparedStatement comandoIncluir;
    private static PreparedStatement comandoAlterar;
    private static PreparedStatement comandoExcluir;
    private static PreparedStatement comandoBuscarPorCodigo;
    private static PreparedStatement comandoBuscarPorNome;
    private static PreparedStatement comandoListarCursos;
    private static PreparedStatement comandoListarDisciplinasPorCursos;

    public CursoDAO(ConexaoBD conexao) throws PersistenciaException {
        super(conexao);
        try {
            comandoIncluir = conexao.getConexao().prepareStatement("INSERT INTO curso (nome,descricao)VALUES (?,?)");
            comandoAlterar = conexao.getConexao().prepareStatement("UPDATE curso SET nome=?,descricao=? WHERE codigo=?");
            comandoExcluir = conexao.getConexao().prepareStatement("DELETE FROM curso WHERE codigo=?");
            comandoBuscarPorCodigo = conexao.getConexao().prepareStatement("SELECT * FROM curso WHERE codigo=?");
            comandoBuscarPorNome = conexao.getConexao().prepareStatement("SELECT * FROM curso WHERE UPPER(nome) LIKE ? ORDER BY NOME LIMIT 10");
            comandoListarCursos = conexao.getConexao().prepareStatement("SELECT codigo, nome, descricao\n" +
                    "\tFROM curso;");
            comandoListarDisciplinasPorCursos = conexao.getConexao().prepareStatement("SELECT a.codigo as codigoCurso,\n" +
                    "a.nome as nomeCurso,\n" +
                    "c.codigo as codigoDisciplina,\n" +
                    "c.nome as nomeDisciplina\n" +
                    "FROM curso a , disciplina c\n" +
                    "WHERE a.codigo = c.curso\n" +
                    "AND a.codigo =?");
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao incluir novo curso -- " + ex.getMessage());
        }
    }

    public int incluir(CursoVO cursoVO) throws PersistenciaException {
        int retorno = 0;
        try {
            comandoIncluir.setString(1, cursoVO.getNome());
            comandoIncluir.setString(2,cursoVO.getDescricao());
            retorno = comandoIncluir.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao incluir curso —- " + ex.getMessage());
        }
        return retorno;
    }

    public int alterar(CursoVO cursoVO) throws PersistenciaException {
        int retorno = 0;
        try {
            comandoAlterar.setString(1, cursoVO.getNome());
            comandoAlterar.setString(2,cursoVO.getDescricao());
            comandoAlterar.setInt(3, cursoVO.getCodigo());
            retorno = comandoAlterar.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao alterar o curso —-" + ex.getMessage());
        }
        return retorno;
    }

    public int excluir(int codigo) throws PersistenciaException {
        int retorno = 0;
        try {
            comandoExcluir.setInt(1, codigo);
            retorno = comandoExcluir.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao excluir o curso —- " + ex.getMessage());
        }
        return retorno;
    }

    public CursoVO buscarPorCodigo(int codigo) throws PersistenciaException {
        CursoVO cursoVO = new CursoVO();
        try {
            comandoBuscarPorCodigo.setInt(1, codigo);
            var rs = comandoBuscarPorCodigo.executeQuery();
            if (rs.next()) {
                cursoVO = buildVO(rs);
            }
        } catch (SQLException ex) {
            throw new PersistenciaException(" Erro na selecao por codigo - " + ex.getMessage());
        }
        return cursoVO;
    }


    private CursoVO buildVO(ResultSet rs) throws SQLException {
        var cursoVO = new CursoVO();
        cursoVO.setCodigo(rs.getInt("codigo"));
        cursoVO.setNome(rs.getString("nome").trim());
        cursoVO.setDescricao(rs.getString("descricao").trim());
        return cursoVO;
    }

    public List<CursoVO> buscarPorNome(String nome) throws PersistenciaException {
        var listaCurso = new ArrayList<CursoVO>();
        try {
            comandoBuscarPorNome.setString(1, '%' + nome.trim().toUpperCase() + '%');
            var rs = comandoBuscarPorNome.executeQuery();
            while(rs.next()) {
                listaCurso.add(buildVO(rs));
            }
        }
        catch(SQLException ex) {
            throw new PersistenciaException(" Erro na selecao por nome - " + ex.getMessage());
        }
        return listaCurso;
    }
    public List<CursoVO> printCursos() throws PersistenciaException {
        List<CursoVO> listaCurso = new ArrayList();
        CursoVO alu = null;
        try {
            ResultSet rs = comandoListarCursos.executeQuery();
            while (rs.next()) {
                alu = new CursoVO();
                alu.setCodigo(rs.getInt("codigo"));
                alu.setNome(rs.getString("nome").trim());
                listaCurso.add(alu);
            }
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção por nome — " + ex.getMessage());
        }
        return listaCurso;
    }

    public List<CursoDTO> printDisciplinasPorCurso(int codigo) throws PersistenciaException {
        List<CursoDTO> listaCurso = new ArrayList();
        try {
            comandoListarDisciplinasPorCursos.setInt(1, codigo);
            ResultSet rs = comandoListarDisciplinasPorCursos.executeQuery();
            if (rs.next()) {
                var curso = new CursoDTO();
                curso.setCodigoCurso(rs.getInt(1));
                curso.setNomeCurso(rs.getString(2).trim());
                curso.setCodigoDisciplina(rs.getInt(3));
                curso.setNomeDisciplina(rs.getString(4).trim());
                listaCurso.add(curso);
            }
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na busca por codigo — " + ex.getMessage());
        }
        return listaCurso;
    }

//    private CursoVO montaCursoVO(ResultSet rs) throws PersistenciaException {
//        CursoVO alu = new CursoVO();
//        if (rs != null) {
//            try {
//                alu.setCodigo(rs.getInt(" Codigo "));
//                alu.setNome(rs.getString("Nome").trim());
//                alu.setDescricao(rs.getString("Descricao"));
//            } catch (Exception ex) {
//                throw new PersistenciaException("Erro ao acessar os dados do resultado");
//            }
//        }
//        return alu;
//    }
}
