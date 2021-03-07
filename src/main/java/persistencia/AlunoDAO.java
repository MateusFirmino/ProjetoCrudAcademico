package persistencia;

import vo.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO extends DAO {

    private static PreparedStatement comandoIncluir;
    private static PreparedStatement comandoAlterar;
    private static PreparedStatement comandoExcluir;
    private static PreparedStatement comandoBuscaMatricula;
    private static PreparedStatement comandoBuscarPorNome;
    private static PreparedStatement comandoListarAlunos;
    private static PreparedStatement comandoListarAlunosPorCurso;
    private CursoDAO cursoDAO;

    public AlunoDAO(ConexaoBD conexao) throws PersistenciaException {
        super(conexao);
        cursoDAO = new CursoDAO(conexao);
        try {
            comandoIncluir = conexao.getConexao().prepareStatement("INSERT INTO aluno ( nome , nomeMae , nomePai , sexo ,logradouro , numero , bairro , cidade , uf, curso )VALUES ( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? )");
            comandoAlterar = conexao.getConexao().prepareStatement("UPDATE aluno SET nome=?, nomemae=?, " +
                    "nomepai=?, sexo=?, logradouro=?, numero=?, bairro=?, cidade=?, uf=? ,curso=? WHERE matricula=?");
            comandoExcluir = conexao.getConexao().prepareStatement("DELETE FROM aluno WHERE matricula =?");
            comandoBuscaMatricula = conexao.getConexao().prepareStatement("SELECT * FROM aluno WHERE matricula =?");
            comandoBuscarPorNome = conexao.getConexao().prepareStatement("SELECT * FROM aluno WHERE UPPER (nome) LIKE ? ORDER BY NOME LIMIT 10 ");
            comandoListarAlunos = conexao.getConexao().prepareStatement("SELECT matricula, nome, sexo\n" +
                    "\tFROM aluno");
            comandoListarAlunosPorCurso = conexao.getConexao().prepareStatement("SELECT c.codigo as codidoCurso,\n" +
                    "c.nome as nomeCurso,\n" +
                    "a.matricula as matriculaAluno,\n" +
                    "a.nome as nomeAluno\n" +
                    "FROM aluno a ,curso c\n" +
                    "WHERE a.curso = c.codigo\n" +
                    "AND c.codigo =?");
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao incluir novo aluno -- " + ex.getMessage());
        }
    }

    public int incluir(AlunoVO alunoVO) throws PersistenciaException {
        int retorno = 0;
        try {
            comandoIncluir.setString(1, alunoVO.getNome());
            comandoIncluir.setString(2, alunoVO.getNomeMae());
            comandoIncluir.setString(3, alunoVO.getNomePai());
            comandoIncluir.setInt(4, alunoVO.getSexo().ordinal());
            comandoIncluir.setString(5, alunoVO.getEndereco().getLogradouro());
            comandoIncluir.setInt(6, alunoVO.getEndereco().getNumero());
            comandoIncluir.setString(7, alunoVO.getEndereco().getBairro());
            comandoIncluir.setString(8, alunoVO.getEndereco().getCidade());
            comandoIncluir.setString(9, alunoVO.getEndereco().getUf().name());
            comandoIncluir.setInt(10,alunoVO.getCurso().getCodigo());
            retorno = comandoIncluir.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao incluir aluno —- " + ex.getMessage());
        }
        return retorno;
    }

    public int alterar(AlunoVO alunoVO) throws PersistenciaException {
        int retorno = 0;
        try {
            comandoAlterar.setString(1, alunoVO.getNome());
            comandoAlterar.setString(2, alunoVO.getNomeMae());
            comandoAlterar.setString(3, alunoVO.getNomePai());
            comandoAlterar.setInt(4, alunoVO.getSexo().ordinal());
            comandoAlterar.setString(5, alunoVO.getEndereco().getLogradouro());
            comandoAlterar.setInt(6, alunoVO.getEndereco().getNumero());
            comandoAlterar.setString(7, alunoVO.getEndereco().getBairro());
            comandoAlterar.setString(8, alunoVO.getEndereco().getCidade());
            comandoAlterar.setString(9, alunoVO.getEndereco().getUf().name());
            comandoAlterar.setInt(10,alunoVO.getCurso().getCodigo());
            comandoAlterar.setInt(11, alunoVO.getMatricula());

            retorno = comandoAlterar.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao alterar o aluno —-" + ex.getMessage());
        }
        return retorno;
    }

    public int excluir(int matricula) throws PersistenciaException {
        int retorno = 0;
        try {
            comandoExcluir.setInt(1, matricula);
            retorno = comandoExcluir.executeUpdate();
        } catch (SQLException ex) {
            throw new PersistenciaException("Erro ao excluir o aluno —- " + ex.getMessage());
        }
        return retorno;
    }

    public AlunoVO buscarPorMatricula(int matricula) throws PersistenciaException {
        AlunoVO alu = null;
        List<AlunoVO> listaAluno = new ArrayList();
        try {
            comandoBuscaMatricula.setInt(1, matricula);
            ResultSet rs = comandoBuscaMatricula.executeQuery();
            if (rs.next()) {
                alu = this.montaAlunoVO(rs);
                listaAluno.add(alu);
            }
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção por codigo -— " + ex.getMessage());
        }
        return alu;
    }

    public List<AlunoVO> printAlunos() throws PersistenciaException {
        List<AlunoVO> listaAluno = new ArrayList();
        AlunoVO alu = null;
        try {
            ResultSet rs = comandoListarAlunos.executeQuery();
            while (rs.next()) {
                alu = new AlunoVO();
                alu.setMatricula(rs.getInt("matricula"));
                alu.setNome(rs.getString("nome").trim());
                alu.setSexo(EnumSexo.values()[rs.getInt("sexo")]);
                listaAluno.add(alu);
            }
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção por nome — " + ex.getMessage());
        }
        return listaAluno;
    }

    public List<AlunoDTO> printAlunosPorCurso(int codigo) throws PersistenciaException {
        List<AlunoDTO> listaAluno = new ArrayList();
        try {
            comandoListarAlunosPorCurso.setInt(1, codigo);
            ResultSet rs = comandoListarAlunosPorCurso.executeQuery();
            if (rs.next()) {
                var aluno = new AlunoDTO();
                aluno.setCodigoCurso(rs.getInt(1));
                aluno.setNomeCurso(rs.getString(2).trim());
                aluno.setMatricula(rs.getInt(3));
                aluno.setNome(rs.getString(4).trim());
                listaAluno.add(aluno);
            }
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na busca por codigo — " + ex.getMessage());
        }
        return listaAluno;
    }


    public List<AlunoVO> buscarPorNome(String nome) throws PersistenciaException {
        List<AlunoVO> listaAluno = new ArrayList();
        AlunoVO alu = null;

        try {
           /* PreparedStatement comando = conexao.getConexao().prepareStatement(comandoSQL);
            ResultSet rs = comando.executeQuery();
            while (rs.next()) {
                alu = this.montaAlunoVO(rs);
                listaAluno.add(alu);
            }
            comando.close();
       */
            comandoBuscarPorNome.setString(1, "%" + nome.trim().toUpperCase() + "%");
            ResultSet rs = comandoBuscarPorNome.executeQuery();
            while (rs.next()) {
                alu = this.montaAlunoVO(rs);
                listaAluno.add(alu);
            }
        } catch (Exception ex) {
            throw new PersistenciaException("Erro na seleção por nome — " + ex.getMessage());
        }
        return listaAluno;
    }

    private AlunoVO montaAlunoVO(ResultSet rs) throws PersistenciaException {
        AlunoVO alu = new AlunoVO();
        if (rs != null) {
            try {
                alu.setMatricula(rs.getInt("matricula"));
                alu.setNome(rs.getString("nome").trim());
                alu.setNomeMae(rs.getString("nomemae"));
                alu.setNomePai(rs.getString("nomepai"));
                alu.setSexo(EnumSexo.values()[rs.getInt("sexo")]);
                alu.getEndereco().setLogradouro(rs.getString("logradouro"));
                alu.getEndereco().setNumero(rs.getInt("numero"));
                alu.getEndereco().setBairro(rs.getString("bairro"));
                alu.getEndereco().setCidade(rs.getString("cidade"));
                alu.getEndereco().setUf(EnumUF.valueOf(rs.getString("uf")));
                alu.setCurso(cursoDAO.buscarPorCodigo(rs.getInt("curso")));
            } catch (Exception ex) {
                throw new PersistenciaException("Erro ao acessar os dados do resultado");
            }
        }
        return alu;
    }
}
