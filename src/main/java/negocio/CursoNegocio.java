package negocio;

import persistencia.ConexaoBD;
import persistencia.CursoDAO;
import persistencia.PersistenciaException;
import vo.AlunoDTO;
import vo.AlunoVO;
import vo.CursoDTO;
import vo.CursoVO;

import java.util.List;

public class CursoNegocio {
    private CursoDAO cursoDAO;

    public CursoNegocio() throws NegocioException {
        try {
            this.cursoDAO = new CursoDAO(ConexaoBD.getInstance());
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao iniciar a Persistencia -— " + ex.getMessage());
        }
    }

    public void inserir(CursoVO cursoVO) throws NegocioException {
        String mensagemErros = this.validarDados(cursoVO);
        if (!mensagemErros.isEmpty()) {
            throw new NegocioException(mensagemErros);
        }
        try {
            if (cursoDAO.incluir(cursoVO) == 0) {
                throw new NegocioException("Inclusao nao realizada!! ");
            }
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao incluir o curso -— " + ex.getMessage());
        }
    }

    public void alterar(CursoVO cursoVO) throws NegocioException {
        String mensagemErros = this.validarDados(cursoVO);
        if (!mensagemErros.isEmpty()) {
            throw new NegocioException(mensagemErros);
        }
        try {
            if (cursoDAO.alterar(cursoVO) == 0) {
                throw new NegocioException("Alteracao nao realizada !! ");
            }
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao alterar o curso —- " + ex.getMessage());
        }
    }

    public void excluir(int codigo) throws NegocioException {
        try {
            if (cursoDAO.excluir(codigo) == 0) {
                throw new NegocioException("Exclusao nao realizada !! ");
            }
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao excluir o curso — " + ex.getMessage());
        }
    }

    public List<CursoVO> pesquisaParteNome(String parteNome) throws NegocioException {
        try {
            return cursoDAO.buscarPorNome(parteNome);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar aluno pelo nome — " + ex.getMessage());
        }
    }

    public CursoVO pesquisaCodigo(int codigo) throws NegocioException {
        try {
            return cursoDAO.buscarPorCodigo(codigo);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar aluno pela matricula — " + ex.getMessage()
            );
        }
    }

    private String validarDados(CursoVO cursoVO) {

        String mensagemErros = "";

        if (cursoVO.getNome() == null || cursoVO.getNome().length() == 0) {
            mensagemErros += "Nome do aluno nao pode ser vazio";
        }

        if (cursoVO.getDescricao() == null || cursoVO.getDescricao().length() == 0) {
            mensagemErros += "Obrigatório ter uma descrição";
        }
        return mensagemErros;
    }

    public List<CursoVO> retornaCursos() throws NegocioException {
        try {
            return cursoDAO.printCursos();
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao retornar alunos — " + ex.getMessage()
            );
        }
    }
    public List<CursoDTO> retornaDisciplinasPorCurso(int codigo) throws NegocioException {
        try {
            return cursoDAO.printDisciplinasPorCurso(codigo);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao retornar Alunos por Curso — " + ex.getMessage()
            );
        }
    }
}
