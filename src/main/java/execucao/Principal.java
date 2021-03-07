package execucao;

import negocio.AlunoNegocio;
import negocio.CursoNegocio;
import negocio.DisciplinaNegocio;
import negocio.NegocioException;
import persistencia.CursoDAO;
import vo.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Principal {
    private static AlunoNegocio alunoNegocio;
    private static CursoNegocio cursoNegocio;
    private static DisciplinaNegocio disciplinaNegocio;
    private CursoDAO cursoDAO;

    public static void main(String[] args) {
        try {
            alunoNegocio = new AlunoNegocio();
            cursoNegocio = new CursoNegocio();
            disciplinaNegocio = new DisciplinaNegocio();
        } catch (NegocioException ex) {
            System.out.println("Camada de negocio e persistencia nao iniciada — " + ex.getMessage());
        }
        if (alunoNegocio != null) {
            EnumMenu opcao = EnumMenu.Sair;
            do {
                try {
                    opcao = exibirMenu();
                    switch (opcao) {
                        case IncluirAluno:
                            incluirAluno();
                            break;
                        case AlterarAluno:
                            alterarAluno();
                            break;
                        case ExcluirAluno:
                            excluirAluno();
                            break;
                        case PesqMatricula:
                            pesquisarPorMatricula();
                            break;
                        case PesqNome:
                            pesquisarPorNome();
                            break;
                        case PrintAlunos:
                            retornaAlunos();
                            break;
                        case PrintAlunosPorCurso:
                            retornaAlunosPorCurso();
                            break;
                        case IncluirCurso:
                            incluirCurso();
                            break;
                        case AlterarCurso:
                            alterarCurso();
                            break;
                        case BuscarCurso:
                            pesquisarPorNomeCurso();
                            break;
                        case BuscarCursoPorCod:
                            pesquisarPorCodigo();
                            break;
                        case ExcluirCurso:
                            excluirCurso();
                            break;
                        case PrintCursos:
                            retornaCursos();
                            break;
                        case PrintDisciplinasDoCurso:
                            retornaDisciplinasPorCurso();
                            break;
                        case IncluirDisciplina:
                            incluirDisciplina();
                            break;
                        case AlterarDisciplina:
                            alterarDisciplina();
                            break;
                        case ExcluirDisciplina:
                            excluirDisciplina();
                            break;
                        case PesqPorDisciplina:
                            pesquisarPorNomeDisci();
                            break;
                        case PesqPorCodDisciplina:
                            pesquisarPorCodigoDisci();
                            break;
                        case PesqDisciplinaPorCurso:
                            pesquisarPorCursoDisci();
                            break;
                        case PrintDisciplinas:
                            retornaDisciplinas();
                            break;
                        case PrintDisciplinasDoAluno:
                            retornaDisciplinasDoAluno();

                    }
                } catch (NegocioException ex) {
                    System.out.println("Operacao nao realizada corretamente — " + ex.getMessage());
                }
            } while (opcao != EnumMenu.Sair);
        }
        System.exit(0);
    }

    /**
     * Inclui um novo aluno na base de dados
     *
     * @throws NegocioException
     */
    private static void incluirAluno() throws NegocioException {
        AlunoVO alunoTemp = lerDados();
        alunoNegocio.inserir(alunoTemp);
    }

    /**
     * Permite a alteracao dos dados de um aluno por meio da matricula
     * fornecida.
     *
     * @throws NegocioException
     */
    private static void alterarAluno() throws NegocioException {
        int matricula = 0;
        try {
            matricula = Integer.parseInt(JOptionPane.showInputDialog(null, "Forneceça a matricula do " +
                    "Aluno", "Leitura de Dados", JOptionPane.QUESTION_MESSAGE));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Aluno não localizado");
        }
        AlunoVO alunoVO = alunoNegocio.pesquisaMatricula(matricula);
        if (alunoVO != null) {
            AlunoVO alunoTemp = lerDados(alunoVO);
            alunoNegocio.alterar(alunoTemp);
        } else {
            JOptionPane.showMessageDialog(null, "Aluno nao localizado");
        }
    }

    /**
     * Exclui um aluno por meio de uma matricula fornecida.
     *
     * @throws NegocioException
     */
    private static void excluirAluno() throws NegocioException {
        int matricula = 0;
        try {
            matricula = Integer.parseInt(JOptionPane.showInputDialog(null, "Forneca a matricula do" +
                    "Aluno", " Leitura de Dados", JOptionPane.QUESTION_MESSAGE));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Digitacao inconsistente — " + ex.getMessage());
        }
        AlunoVO alunoVO = alunoNegocio.pesquisaMatricula(matricula);
        if (alunoVO != null) {
            alunoNegocio.excluir(alunoVO.getMatricula());
            System.out.println("Aluno excluido ");
        } else {
            JOptionPane.showMessageDialog(null, "Aluno nao localizado");
        }
    }

    /**
     * Pesquisar um aluno por meio da matricula.
     *
     * @throws NegocioException
     */
    private static void pesquisarPorMatricula() throws NegocioException {
        int matricula = 0;
        try {
            matricula = Integer.parseInt(JOptionPane.showInputDialog(null, "Forneça a matricula do " +
                    "Aluno", "Leitura de Dados", JOptionPane.QUESTION_MESSAGE));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Digitacao inconsistente — " + ex.getMessage());
        }
        AlunoVO alunoVO = alunoNegocio.pesquisaMatricula(matricula);
        if (alunoVO != null) {
            mostrarDados(alunoVO);
        } else {
            JOptionPane.showMessageDialog(null, "Aluno nao localizado");
        }
    }

    /**
     * Le um nome ou parte de um nome de um aluno e busca no banco de dados
     * alunos que possuem esse nome, ou que iniciam com a parte do nome
     * fornecida. Caso nao seja fornecido nenhum valor de entrada sera retornado
     * os 10 primmeiros alunos ordenados pelo nome.
     *
     * @throws NegocioException
     */
    private static void pesquisarPorNome() throws NegocioException {
        String nome = JOptionPane.showInputDialog(null, "Forneça o nome do Aluno ", "Leitura" +
                "de dados", JOptionPane.QUESTION_MESSAGE);
        if (nome != null) {
            List<AlunoVO> listaAlunoVO = alunoNegocio.pesquisaParteNome(nome);
            if (listaAlunoVO.size() > 0) {
                for (AlunoVO alunoVO : listaAlunoVO) {
                    mostrarDados(alunoVO);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Aluno nao localizado");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nome nao pode ser nulo");
        }
    }

    /**
     * Exibe no console da aplicacao os dados dos alunos recebidos pelo
     * parametro alunoVO.
     *
     * @param alunoVO
     */
    private static void mostrarDados(AlunoVO alunoVO) {
        if (alunoVO != null) {
            System.out.println(" Matricula: " + alunoVO.getMatricula());
            System.out.println("Nome: " + alunoVO.getNome());
            System.out.println("Nome da Mae: " + alunoVO.getNomeMae());
            System.out.println("Nome da Pai: " + alunoVO.getNomePai());
            System.out.println("Sexo: " + alunoVO.getSexo().name());
            System.out.println("Curso: " + alunoVO.getCurso().getCodigo());
            if (alunoVO.getEndereco() != null) {
                System.out.println("Logradouro: " + alunoVO.getEndereco().getLogradouro());
                System.out.println("Numero: " + alunoVO.getEndereco().getNumero());
                System.out.println("Bairro: " + alunoVO.getEndereco().getBairro());
                System.out.println("Cidade: " + alunoVO.getEndereco().getCidade());
                System.out.println("UF: " + alunoVO.getEndereco().getUf());
                System.out.println("—————————————-");
            }
        }
    }

    /**
     * Le os dados de um aluno exibindo os dados atuais recebidos pelo parametro
     * alunoTemp. Na alteracao permite que os dados atuais do alunos sejam
     * visualizados. Na inclusão sera exibidos os dados inicializados no AlunoVO.
     *
     * @param alunoTemp
     * @return
     */

    private static AlunoVO lerDados(AlunoVO alunoTemp) {
        String nome, nomeMae, nomePai, logradouro, bairro, cidade;
        int numero, Codcurso;
        EnumSexo sexo;
        EnumUF uf;
        try {
            nome = JOptionPane.showInputDialog(" Forneca o nome do Aluno ", alunoTemp.getNome().trim()
            );
            alunoTemp.setNome(nome);

            nomeMae = JOptionPane.showInputDialog("Forneca o nome da mae do Aluno", alunoTemp.
                    getNomeMae().trim());
            alunoTemp.setNomeMae(nomeMae);

            nomePai = JOptionPane.showInputDialog("Forneca o nome do pai do Aluno", alunoTemp.
                    getNomePai().trim());
            alunoTemp.setNomePai(nomePai);

            sexo = (EnumSexo) JOptionPane.showInputDialog(null, "Escolha uma Opcaoo", "Leitura de Dados",
                    JOptionPane.QUESTION_MESSAGE, null, EnumSexo.values(), alunoTemp.getSexo());
            alunoTemp.setSexo(sexo);

            logradouro = JOptionPane.showInputDialog("Forneca o logradouro do endereco", alunoTemp.
                    getEndereco().getLogradouro().trim());
            alunoTemp.getEndereco().setLogradouro(logradouro);

            numero = Integer.parseInt(JOptionPane.showInputDialog(" Forneça o numero no endereco ",
                    alunoTemp.getEndereco().getNumero()));
            alunoTemp.getEndereco().setNumero(numero);

            bairro = JOptionPane.showInputDialog("ForneA§a o bairro no endereco", alunoTemp.
                    getEndereco().getBairro().trim());
            alunoTemp.getEndereco().setBairro(bairro);

            cidade = JOptionPane.showInputDialog("ForneA§a a cidade no endereco", alunoTemp.
                    getEndereco().getCidade().trim());
            alunoTemp.getEndereco().setCidade(cidade);

            uf = (EnumUF) JOptionPane.showInputDialog(null, "Escolha uma Opcao", "Leitura de Dados",
                    JOptionPane.QUESTION_MESSAGE, null, EnumUF.values(), alunoTemp.getEndereco().
                            getUf());
            alunoTemp.getEndereco().setUf(uf);

            Codcurso = Integer.parseInt(JOptionPane.showInputDialog("Forneça o código do curso"));
            CursoVO curso = cursoNegocio.pesquisaCodigo(Codcurso);
            alunoTemp.setCurso(curso);

        } catch (Exception ex) {
            System.out.println("Digitacao inconsistente — " + ex.getMessage());
        }
        return alunoTemp;
    }

    private static AlunoVO lerDados() {
        AlunoVO alunoTemp = new AlunoVO();
        return lerDados(alunoTemp);
    }

    /**
     * Exibe as opcoes por meio de uma tela de dialogo.
     *
     * @return
     */
    private static EnumMenu exibirMenu() {
        EnumMenu opcao;
        opcao = (EnumMenu) JOptionPane.showInputDialog(null, "Escolha uma Opcao", "Menu",
                JOptionPane.QUESTION_MESSAGE, null, EnumMenu.values(), EnumMenu.values()[0]);
        if (opcao == null) {
            JOptionPane.showMessageDialog(null, "Nenhuma Opcao Escolhida");
            opcao = EnumMenu.Sair;
        }
        return opcao;
    }

    private static void incluirCurso() throws NegocioException {
        CursoVO cursoTemp = lerDadosCurso();
        cursoNegocio.inserir(cursoTemp);
    }

    private static void alterarCurso() throws NegocioException {
        int codigo = 0;
        try {
            codigo = Integer.parseInt((JOptionPane.showInputDialog(null, "Forneça o codigo do " +
                    "curso", "Leitura de Dados", JOptionPane.QUESTION_MESSAGE)));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Digitação inconsistente -- " + ex.getMessage());
        }

        CursoVO cursoVO = cursoNegocio.pesquisaCodigo(codigo);
        if (cursoVO != null) {
            CursoVO cursoTemp = lerDadosCurso(cursoVO);
            cursoNegocio.alterar(cursoTemp);
        } else {
            JOptionPane.showMessageDialog(null, "Curso nao localizado");
        }
    }

    private static void excluirCurso() throws NegocioException {
        int codigo = 0;
        try {
            codigo = Integer.parseInt(JOptionPane.showInputDialog(null, "Forneca a codigo do Curso",
                    "Leitura de Dados", JOptionPane.QUESTION_MESSAGE));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Digitacao inconsistente —- " + ex.getMessage());
        }
        CursoVO cursoVO = cursoNegocio.pesquisaCodigo(codigo);
        if (cursoVO != null) {
            cursoNegocio.excluir(cursoVO.getCodigo());
        } else {
            JOptionPane.showMessageDialog(null, "Curso nao localizado");
        }
    }

    private static void pesquisarPorCodigo() throws NegocioException {
        int codigo = 0;
        try {
            codigo = Integer.parseInt(JOptionPane.showInputDialog(null, "Forneça a codigo do Curso",
                    "Leitura de Dados", JOptionPane.QUESTION_MESSAGE));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Digitacao inconsistente — " + ex.getMessage());
        }
        CursoVO cursoVO = cursoNegocio.pesquisaCodigo(codigo);
        if (cursoVO != null) {
            mostrarDadosCurso(cursoVO);
        } else {
            JOptionPane.showMessageDialog(null, "Curso nao localizado");
        }
    }

    private static void pesquisarPorNomeCurso() throws NegocioException {
        String nome = JOptionPane.showInputDialog(null, "Forneça o nome do Curso", "Leitura de Dados"
                , JOptionPane.QUESTION_MESSAGE);
        if (nome != null) {
            List<CursoVO> listaCursoVO = cursoNegocio.pesquisaParteNome(nome);
            if (listaCursoVO.size() > 0) {
                for (CursoVO cursoVO : listaCursoVO) {
                    mostrarDadosCurso(cursoVO);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Curso nao localizado");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nome nao pode ser nulo");
        }
    }

    private static void mostrarDadosCurso(CursoVO cursoVO) {
        if (cursoVO != null) {
            System.out.println("Codigo : " + cursoVO.getCodigo());
            System.out.println("Nome: " + cursoVO.getNome());
            System.out.println("Descrição: " + cursoVO.getDescricao());
        }
    }

    private static CursoVO lerDadosCurso(CursoVO cursoTemp) {
        String nome, descricao;
        try {
            nome = JOptionPane.showInputDialog(" Forneca o nome do curso ", cursoTemp.getNome().trim());
            cursoTemp.setNome(nome);

            descricao = JOptionPane.showInputDialog("Forneca a descrição do curso", cursoTemp.getDescricao().trim());
            cursoTemp.setDescricao(descricao);


        } catch (Exception ex) {
            System.out.println("Digitação inconsistente -- " + ex.getMessage());
        }
        return cursoTemp;
    }

    private static CursoVO lerDadosCurso() {
        CursoVO cursoTemp = new CursoVO();
        return lerDadosCurso(cursoTemp);
    }

    private static void incluirDisciplina() throws NegocioException {
        DisciplinaVO disciplinaTemp = lerDadosDisciplina();
        disciplinaNegocio.inserir(disciplinaTemp);
    }

    private static void alterarDisciplina() throws NegocioException {
        int codigo = 0;
        try {
            codigo = Integer.parseInt((JOptionPane.showInputDialog(null, "Forneça a codigo da " +
                    "disciplina", "Leitura de Dados", JOptionPane.QUESTION_MESSAGE)));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Digitação inconsistente -- " + ex.getMessage());
        }

        DisciplinaVO disciplinaVO = disciplinaNegocio.pesquisaCodigoDisci(codigo);
        if (disciplinaVO != null) {
            DisciplinaVO disciplina = lerDadosDisciplina(disciplinaVO);
            disciplinaNegocio.alterar(disciplina);
        } else {
            JOptionPane.showMessageDialog(null, "Disciplina nao localizado");
        }
    }

    private static void excluirDisciplina() throws NegocioException {
        int codigo = 0;
        try {
            codigo = Integer.parseInt(JOptionPane.showInputDialog(null, "Forneca a codigo da disciplina",
                    "Leitura de Dados", JOptionPane.QUESTION_MESSAGE));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Digitacao inconsistente —- " + ex.getMessage());
        }
        DisciplinaVO disciplinaVO = disciplinaNegocio.pesquisaCodigoDisci(codigo);
        if (disciplinaVO != null) {
            disciplinaNegocio.excluir(disciplinaVO.getCodigo());
        } else {
            JOptionPane.showMessageDialog(null, "Disciplina nao localizado");
        }
    }

    private static void pesquisarPorCodigoDisci() throws NegocioException {
        int codigo = 0;
        try {
            codigo = Integer.parseInt(JOptionPane.showInputDialog(null, "Forneça a codigo da Disciplina",
                    "Leitura de Dados", JOptionPane.QUESTION_MESSAGE));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Digitacao inconsistente — " + ex.getMessage());
        }
        DisciplinaVO disciplinaVO = disciplinaNegocio.pesquisaCodigoDisci(codigo);
        if (disciplinaVO != null) {
            mostrarDadosDisci(disciplinaVO);
        } else {
            JOptionPane.showMessageDialog(null, "Disciplina nao localizado");
        }
    }

    private static void pesquisarPorNomeDisci() throws NegocioException {
        String nome = JOptionPane.showInputDialog(null, "Forneça o nome da disciplina", "Leitura de Dados"
                , JOptionPane.QUESTION_MESSAGE);
        if (nome != null) {
            List<DisciplinaVO> listaDisciplinaVO = disciplinaNegocio.pesquisaPorNome(nome);
            if (listaDisciplinaVO.size() > 0) {
                for (DisciplinaVO disciplinaVO : listaDisciplinaVO) {
                    mostrarDadosDisci(disciplinaVO);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Disciplina nao localizado");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nome nao pode ser nulo");
        }
    }

    private static void pesquisarPorCursoDisci() {

    }

    private static void mostrarDadosDisci(DisciplinaVO disciplinaVO) {
        if (disciplinaVO != null) {
            System.out.println("Codigo : " + disciplinaVO.getCodigo());
            System.out.println("Nome : " + disciplinaVO.getNome());
            System.out.println("Semestre : " + disciplinaVO.getSemestre());
            System.out.println("Carga Horaria : " + disciplinaVO.getCargahoraria());
            if (disciplinaVO.getCurso() != null) {
                System.out.println("Nome do curso : " + disciplinaVO.getCurso().getNome());
                System.out.println("Descrição : " + disciplinaVO.getCurso().getDescricao());
                System.out.println("Codigo: " + disciplinaVO.getCurso().getCodigo());
                System.out.println("-----------------------------------------------");
            }
        }
    }

    private static DisciplinaVO lerDadosDisciplina(DisciplinaVO disciplinaTemp) {
        String nome;
        Integer semestre, cargaHoraria, cursoCodigo;


        try {
            nome = JOptionPane.showInputDialog(" Forneca o nome da Disciplina ", disciplinaTemp.getNome().trim());
            disciplinaTemp.setNome(nome);

            semestre = Integer.parseInt(JOptionPane.showInputDialog(" Forneça o semestre "));
            // System.out.println(semestre);
            disciplinaTemp.setSemestre(semestre);

            cargaHoraria = Integer.parseInt(JOptionPane.showInputDialog(" Forneça a carga Horaria ", disciplinaTemp.getCargahoraria()));
            // System.out.println(cargaHoraria);
            disciplinaTemp.setCargahoraria(cargaHoraria);

            cursoCodigo = Integer.parseInt(JOptionPane.showInputDialog("Forneça o código do curso"));
            CursoVO curso = cursoNegocio.pesquisaCodigo(cursoCodigo);
            System.out.println(curso);
            disciplinaTemp.setCurso(curso);

            // CursoVO[] cursos = (CursoVO[]) cursoNegocio.pesquisaParteNome("").toArray();            
            // var combobox = new JComboBox<CursoVO>(cursos);
            // CursoVO curso = (CursoVO) combobox.getSelectedItem();
            // disciplinaTemp.setCurso(curso);
            // System.out.println(disciplinaTemp);

        } catch (Exception ex) {
            System.out.println("Digitação inconsistente -- " + ex.getMessage());
        }
        return disciplinaTemp;
    }

    private static DisciplinaVO lerDadosDisciplina() {
        DisciplinaVO disciplinaTemp = new DisciplinaVO();
        return lerDadosDisciplina(disciplinaTemp);
    }

    public static void retornaAlunos() throws NegocioException {
        List<AlunoVO> listaAluno = new ArrayList();
        AlunoVO alu = null;

        try {
            listaAluno = alunoNegocio.retornaAlunos();
            for (AlunoVO aluno : listaAluno) {
//                System.out.println("Matricula: " + aluno.getMatricula());
//                System.out.println("Nome: " +aluno.getNome());
//                System.out.println("Sexo: " +aluno.getSexo());
//                System.out.println("--------------------");
                System.out.println(aluno.toString2());
            }
        } catch (Exception ex) {
            throw new NegocioException("Erro ao printar alunos — " + ex.getMessage());
        }
    }

    public static void retornaAlunosPorCurso() throws NegocioException {
        int codigo = 0;
        try {
            codigo = Integer.parseInt(JOptionPane.showInputDialog(null, "Forneça a codigo do Curso",
                    "Leitura de Dados", JOptionPane.QUESTION_MESSAGE));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Digitacao inconsistente — " + ex.getMessage());
        }
        List<AlunoDTO> alunoDTO = alunoNegocio.retornaAlunosPorCurso(codigo);
        if (alunoDTO  != null) {
            System.out.println(alunoDTO.toString());
        } else {
            JOptionPane.showMessageDialog(null, "Disciplina nao localizado");
        }
    }

    public static void retornaCursos() throws NegocioException {
        List<CursoVO> listaCurso = new ArrayList();
        AlunoVO alu = null;

        try {
            listaCurso = cursoNegocio.retornaCursos();
            for (CursoVO curso : listaCurso) {
                System.out.println(curso.toString2());
            }
        } catch (Exception ex) {
            throw new NegocioException("Erro ao printar alunos — " + ex.getMessage());
        }
    }

    public static void retornaDisciplinas() throws NegocioException {
        List<DisciplinaVO> listaDisciplina = new ArrayList();
        AlunoVO alu = null;

        try {
            listaDisciplina = disciplinaNegocio.retornaDisciplina();
            for (DisciplinaVO disciplina : listaDisciplina) {
                System.out.println(disciplina.toString2());
            }
        } catch (Exception ex) {
            throw new NegocioException("Erro ao printar alunos — " + ex.getMessage());
        }
    }
    public static void retornaDisciplinasPorCurso() throws NegocioException {
        int codigo = 0;
        try {
            codigo = Integer.parseInt(JOptionPane.showInputDialog(null, "Forneça a codigo do Curso",
                    "Leitura de Dados", JOptionPane.QUESTION_MESSAGE));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Digitacao inconsistente — " + ex.getMessage());
        }
        List<CursoDTO> cursoDTO = cursoNegocio.retornaDisciplinasPorCurso(codigo);
        if (cursoDTO  != null) {
            System.out.println(cursoDTO.toString());
        } else {
            JOptionPane.showMessageDialog(null, "Curso nao localizado");
        }
    }

    public static void retornaDisciplinasDoAluno() throws NegocioException {
        int matricula = 0;
        try {
            matricula = Integer.parseInt(JOptionPane.showInputDialog(null, "Forneça a matricula do Aluno",
                    "Leitura de Dados", JOptionPane.QUESTION_MESSAGE));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Digitacao inconsistente — " + ex.getMessage());
        }
        List<DisciplinaDTO> disciplinaDTO = disciplinaNegocio.retornaDisciplinasPorAluno(matricula);
        if (disciplinaDTO  != null) {
            System.out.println(disciplinaDTO.toString());
        } else {
            JOptionPane.showMessageDialog(null, "Aluno nao localizado");
        }
    }


}