package negocio;

import persistencia.ConexaoBD;
import persistencia.DisciplinaDAO;
import persistencia.PersistenciaException;
import vo.CursoDTO;
import vo.CursoVO;
import vo.DisciplinaDTO;
import vo.DisciplinaVO;

import java.util.List;

public class DisciplinaNegocio {
    private DisciplinaDAO disciplinaDAO;

    public DisciplinaNegocio() throws NegocioException {
        try {
            this.disciplinaDAO = new DisciplinaDAO(ConexaoBD.getInstance());
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao iniciar a Persistencia - " + ex.getMessage());
        }
    }

    public void inserir(DisciplinaVO disciplinaVO) throws NegocioException {

        String mensagemErros = this.validarDados(disciplinaVO);

        if (!mensagemErros.isEmpty()) {
            throw new NegocioException(mensagemErros);
        }

        try {
            if (disciplinaDAO.incluir(disciplinaVO) == 0) {
                throw new NegocioException("Inclusão nao realizada!!");
            }
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao incluir o disciplina - " + ex.getMessage());
        }
    }

    public void alterar(DisciplinaVO disciplinaVO) throws NegocioException {
        String mensagemErros = this.validarDados(disciplinaVO);
        if (!mensagemErros.isEmpty()) {
            throw new NegocioException(mensagemErros);
        }
        try {
            if (disciplinaDAO.alterar(disciplinaVO) == 0) {
                throw new NegocioException("Alteração nao realizada!!");
            }
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao alterar o disciplina - " + ex.getMessage());
        }
    }

    public void excluir(int codigo) throws NegocioException {
        try {
            if (disciplinaDAO.excluir(codigo) == 0) {
                throw new NegocioException("Alteração não realizada!!");
            }
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao excluir o disciplina - " + ex.getMessage());
        }
    }


    public List<DisciplinaVO> pesquisaPorNome(String parteNome) throws NegocioException {
        try {
            return disciplinaDAO.buscarPorNomeDisciplina(parteNome);
        }
        catch(PersistenciaException ex) {
            throw new NegocioException("Erro ao pesquisar Disciplina pelo nome - " + ex.getMessage());
        }
    }

    public List<DisciplinaVO> pesquisaPorCurso(CursoVO cursoVO) throws NegocioException {
        try {
            return disciplinaDAO.buscarPorCurso(cursoVO);
        }
        catch(PersistenciaException ex) {
            throw new NegocioException(
                    "Erro ao pesquisar por curso - " + ex.getMessage());
        }
    }

    public DisciplinaVO pesquisaCodigoDisci(int codigo) throws NegocioException {
        try {
            return disciplinaDAO.buscarPorCodigo(codigo);
        } catch (PersistenciaException ex) {
            throw new NegocioException(
                    "Erro ao pesquisar disciplina pela codigo - " + ex.getMessage());
        }
    }

    public List<DisciplinaVO> retornaDisciplina() throws NegocioException {
        try {
            return disciplinaDAO.printDisciplina();
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao retornar alunos — " + ex.getMessage()
            );
        }
    }

    public List<DisciplinaDTO> retornaDisciplinasPorAluno(int matricula) throws NegocioException {
        try {
            return disciplinaDAO.printDisciplinasDoAluno(matricula);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Erro ao retornar Alunos por Curso — " + ex.getMessage()
            );
        }
    }

    private String validarDados(DisciplinaVO disciplinaVO) {

        String mensagemErros = "";

        if (disciplinaVO.getNome() == null || disciplinaVO.getNome().length() == 0) {
            mensagemErros += "Nome da disciplina nao pode ser vazio";
        }

        if (disciplinaVO.getSemestre() <= 0) {
            mensagemErros += "\nSemestre deve ser maior que zero";
        }

        if (disciplinaVO.getCargahoraria() <= 0) {
            mensagemErros += "\nCarga Horaria deve ser maior que zero";
        }

        if(disciplinaVO.getCurso() == null)
        {
            mensagemErros += "\n Curso nao pode ser vazio";
        }
        if (disciplinaVO.getCurso().getNome() == null || disciplinaVO.getCurso().getNome().length() == 0) {
            mensagemErros += "\n Nome do curso nao pode ser vazio";
        }

        if (disciplinaVO.getCurso().getDescricao() == null || disciplinaVO.getCurso().getDescricao().length() == 0) {
            mensagemErros += "\nDescrição do curso nao pode ser vazio";
        }

        return mensagemErros;
    }
}

