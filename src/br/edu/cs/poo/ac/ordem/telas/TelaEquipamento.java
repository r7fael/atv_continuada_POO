package br.edu.cs.poo.ac.ordem.telas;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.MessageBox;
import java.text.DecimalFormat;
import java.text.ParseException;

import br.edu.cs.poo.ac.ordem.entidades.Desktop;
import br.edu.cs.poo.ac.ordem.entidades.Notebook;
import br.edu.cs.poo.ac.ordem.mediators.EquipamentoMediator;
import br.edu.cs.poo.ac.ordem.mediators.ResultadoMediator;
import br.edu.cs.poo.ac.utils.ListaString;
import br.edu.cs.poo.ac.utils.StringUtils;

public class TelaEquipamento {

    protected Shell shell;
    private Text txtSerial;
    private Text txtDescricao;
    private Text txtValorEstimado;
    private Combo cmbTipo;

    private Button radioEhNovoNao;
    private Button radioEhNovoSim;

    private Group groupNotebook;
    private Button radioDadosSensiveisNao;
    private Button radioDadosSensiveisSim;

    private Group groupDesktop;
    private Button radioServidorNao;
    private Button radioServidorSim;

    private Button btnNovo;
    private Button btnBuscar;
    private Button btnIncluir;
    private Button btnAlterar;
    private Button btnExcluir;
    private Button btnCancelar;

    private EquipamentoMediator mediator = EquipamentoMediator.getInstancia();
    private DecimalFormat df = new DecimalFormat("#,##0.00");

    public static void main(String[] args) {
        try {
            TelaEquipamento window = new TelaEquipamento();
            window.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void open() {
        Display display = Display.getDefault();
        createContents();
        shell.open();
        shell.layout();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    protected void createContents() {
        shell = new Shell();
        shell.setSize(500, 438);
        shell.setText("CRUD de Equipamento");

        Label lblTipo = new Label(shell, SWT.NONE);
        lblTipo.setBounds(10, 26, 30, 15);
        lblTipo.setText("Tipo:");

        cmbTipo = new Combo(shell, SWT.READ_ONLY);
        cmbTipo.setItems(new String[] {"Notebook", "Desktop"});
        cmbTipo.setBounds(46, 23, 91, 23);
        cmbTipo.select(0);

        Label lblSerial = new Label(shell, SWT.NONE);
        lblSerial.setBounds(155, 26, 35, 15);
        lblSerial.setText("Serial:");

        txtSerial = new Text(shell, SWT.BORDER);
        txtSerial.setBounds(196, 23, 115, 21);

        Label lblDescricao = new Label(shell, SWT.NONE);
        lblDescricao.setBounds(10, 67, 55, 15);
        lblDescricao.setText("Descrição:");

        txtDescricao = new Text(shell, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
        txtDescricao.setBounds(71, 64, 402, 70);

        Label lblEhNovo = new Label(shell, SWT.NONE);
        lblEhNovo.setBounds(10, 150, 45, 15);
        lblEhNovo.setText("É novo:");

        radioEhNovoNao = new Button(shell, SWT.RADIO);
        radioEhNovoNao.setSelection(true);
        radioEhNovoNao.setBounds(71, 150, 45, 16);
        radioEhNovoNao.setText("NÃO");

        radioEhNovoSim = new Button(shell, SWT.RADIO);
        radioEhNovoSim.setBounds(122, 150, 45, 16);
        radioEhNovoSim.setText("SIM");

        Label lblValor = new Label(shell, SWT.NONE);
        lblValor.setBounds(200, 150, 80, 15);
        lblValor.setText("Valor Estimado:");

        txtValorEstimado = new Text(shell, SWT.BORDER);
        txtValorEstimado.setBounds(286, 147, 100, 21);

        groupNotebook = new Group(shell, SWT.NONE);
        groupNotebook.setText("Carrega dados sensíveis");
        groupNotebook.setBounds(10, 180, 220, 60);

        radioDadosSensiveisNao = new Button(groupNotebook, SWT.RADIO);
        radioDadosSensiveisNao.setSelection(true);
        radioDadosSensiveisNao.setText("NÃO");
        radioDadosSensiveisNao.setBounds(10, 25, 45, 16);

        radioDadosSensiveisSim = new Button(groupNotebook, SWT.RADIO);
        radioDadosSensiveisSim.setText("SIM");
        radioDadosSensiveisSim.setBounds(61, 25, 45, 16);

        groupDesktop = new Group(shell, SWT.NONE);
        groupDesktop.setText("É Servidor");
        groupDesktop.setBounds(250, 180, 223, 60);

        radioServidorNao = new Button(groupDesktop, SWT.RADIO);
        radioServidorNao.setSelection(true);
        radioServidorNao.setText("NÃO");
        radioServidorNao.setBounds(10, 25, 45, 16);

        radioServidorSim = new Button(groupDesktop, SWT.RADIO);
        radioServidorSim.setText("SIM");
        radioServidorSim.setBounds(61, 25, 45, 16);

        btnNovo = new Button(shell, SWT.NONE);
        btnNovo.setBounds(317, 22, 75, 25);
        btnNovo.setText("Novo");

        btnBuscar = new Button(shell, SWT.NONE);
        btnBuscar.setBounds(398, 22, 75, 25);
        btnBuscar.setText("Buscar");

        btnIncluir = new Button(shell, SWT.NONE);
        btnIncluir.setBounds(10, 340, 75, 25);
        btnIncluir.setText("Incluir");

        btnAlterar = new Button(shell, SWT.NONE);
        btnAlterar.setBounds(91, 340, 75, 25);
        btnAlterar.setText("Alterar");

        btnExcluir = new Button(shell, SWT.NONE);
        btnExcluir.setBounds(172, 340, 75, 25);
        btnExcluir.setText("Excluir");

        btnCancelar = new Button(shell, SWT.NONE);
        btnCancelar.setBounds(398, 340, 75, 25);
        btnCancelar.setText("Cancelar");

        definirEstadoInicial();
        addListeners();
        atualizarCamposDinamicos();
    }

    private void addListeners() {
        cmbTipo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                atualizarCamposDinamicos();
            }
        });

        txtValorEstimado.addVerifyListener(new VerifyListener() {
            public void verifyText(VerifyEvent e) {
                String currentText = txtValorEstimado.getText();
                String newText = currentText.substring(0, e.start) + e.text + currentText.substring(e.end);

                if (e.text.isEmpty()) {
                    e.doit = true;
                    return;
                }

                for (char ch : e.text.toCharArray()) {
                    if (!Character.isDigit(ch) && ch != ',') {
                        e.doit = false;
                        return;
                    }
                }

                int commaIndex = newText.indexOf(',');
                if (commaIndex != -1) {
                    String parteInteira = newText.substring(0, commaIndex);
                    String parteDecimal = newText.substring(commaIndex + 1);

                    if (parteDecimal.indexOf(',') != -1) {
                        e.doit = false;
                        return;
                    }
                    if (parteInteira.length() > 10 || parteDecimal.length() > 2) {
                        e.doit = false;
                        return;
                    }
                } else {
                    if (newText.length() > 10) {
                        e.doit = false;
                        return;
                    }
                }

                e.doit = true;
            }
        });

        btnNovo.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                String id = getIdCompleto();
                if (StringUtils.estaVazia(txtSerial.getText())) {
                    limparCamposDados();
                    definirEstadoInclusao();
                    return;
                }
                Object eq;
                if (isNotebook()) {
                    eq = mediator.buscarNotebook(id);
                } else {
                    eq = mediator.buscarDesktop(id);
                }
                if (eq != null) {
                    exibirMensagemSimples("Erro", "Equipamento já cadastrado!");
                } else {
                    limparCamposDados();
                    definirEstadoInclusao();
                }
            }
        });

        btnBuscar.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                String id = getIdCompleto();
                if (isNotebook()) {
                    Notebook note = mediator.buscarNotebook(id);
                    if (note != null) {
                        preencherTela(note);
                        definirEstadoAlteracaoExclusao();
                    } else {
                        exibirMensagemSimples("Erro", "Notebook não encontrado!");
                    }
                } else {
                    Desktop desk = mediator.buscarDesktop(id);
                    if (desk != null) {
                        preencherTela(desk);
                        definirEstadoAlteracaoExclusao();
                    } else {
                        exibirMensagemSimples("Erro", "Desktop não encontrado!");
                    }
                }
            }
        });

        btnIncluir.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                if (isNotebook()) {
                    Notebook note = montarNotebook();
                    ResultadoMediator res = mediator.incluirNotebook(note);
                    handleResultado(res, "Inclusão");
                } else {
                    Desktop desk = montarDesktop();
                    ResultadoMediator res = mediator.incluirDesktop(desk);
                    handleResultado(res, "Inclusão");
                }
            }
        });

        btnAlterar.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                if (isNotebook()) {
                    Notebook note = montarNotebook();
                    ResultadoMediator res = mediator.alterarNotebook(note);
                    handleResultado(res, "Alteração");
                } else {
                    Desktop desk = montarDesktop();
                    ResultadoMediator res = mediator.alterarDesktop(desk);
                    handleResultado(res, "Alteração");
                }
            }
        });

        btnExcluir.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                String id = getIdCompleto();
                ResultadoMediator res;
                if(isNotebook()){
                    res = mediator.excluirNotebook(id);
                } else {
                    res = mediator.excluirDesktop(id);
                }

                if (res.isOperacaoRealizada()) {
                    exibirMensagemSimples("Sucesso", "Exclusão realizada com sucesso");
                    limparTelaToda();
                    definirEstadoInicial();
                } else {
                    exibirMensagemSimples("Erro", "Exclusão não pôde ser realizada");
                }
            }
        });

        btnCancelar.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                limparTelaToda();
                definirEstadoInicial();
            }
        });
    }

    private void handleResultado(ResultadoMediator res, String operacao) {
        if (res.isOperacaoRealizada()) {
            exibirMensagemSimples("Sucesso", operacao + " realizada com sucesso");
            limparTelaToda();
            definirEstadoInicial();
        } else {
            exibirMensagensErro("Erros de Validação", res.getMensagensErro());
        }
    }

    private Notebook montarNotebook() {
        String serial = txtSerial.getText();
        String descricao = txtDescricao.getText();
        boolean ehNovo = radioEhNovoSim.getSelection();
        double valor = 0.0;
        try {
            valor = df.parse(txtValorEstimado.getText()).doubleValue();
        } catch (ParseException e) {}
        boolean carregaDadosSensiveis = radioDadosSensiveisSim.getSelection();
        return new Notebook(serial, descricao, ehNovo, valor, carregaDadosSensiveis);
    }

    private Desktop montarDesktop() {
        String serial = txtSerial.getText();
        String descricao = txtDescricao.getText();
        boolean ehNovo = radioEhNovoSim.getSelection();
        double valor = 0.0;
        try {
            valor = df.parse(txtValorEstimado.getText()).doubleValue();
        } catch (ParseException e) {}
        boolean ehServidor = radioServidorSim.getSelection();
        return new Desktop(serial, descricao, ehNovo, valor, ehServidor);
    }

    private void preencherTela(Notebook note) {
        txtDescricao.setText(note.getDescricao());
        radioEhNovoSim.setSelection(note.isEhNovo());
        radioEhNovoNao.setSelection(!note.isEhNovo());
        txtValorEstimado.setText(df.format(note.getValorEstimado()));
        radioDadosSensiveisSim.setSelection(note.isCarregaDadosSensiveis());
        radioDadosSensiveisNao.setSelection(!note.isCarregaDadosSensiveis());
    }

    private void preencherTela(Desktop desk) {
        txtDescricao.setText(desk.getDescricao());
        radioEhNovoSim.setSelection(desk.isEhNovo());
        radioEhNovoNao.setSelection(!desk.isEhNovo());
        txtValorEstimado.setText(df.format(desk.getValorEstimado()));
        radioServidorSim.setSelection(desk.isEhServidor());
        radioServidorNao.setSelection(!desk.isEhServidor());
    }

    private void limparCamposDados() {
        txtDescricao.setText("");
        radioEhNovoNao.setSelection(true);
        txtValorEstimado.setText("");
        radioDadosSensiveisNao.setSelection(true);
        radioServidorNao.setSelection(true);
    }

    private void limparTelaToda() {
        cmbTipo.select(0);
        txtSerial.setText("");
        limparCamposDados();
        atualizarCamposDinamicos();
    }

    private boolean isNotebook() {
        if (cmbTipo.getSelectionIndex() == 0) {
            return true;
        }
        return false;
    }

    private String getIdCompleto() {
        String idTipo;
        if (isNotebook()) {
            idTipo = "NO";
        } else {
            idTipo = "DE";
        }
        return idTipo + txtSerial.getText();
    }

    private void atualizarCamposDinamicos() {
        boolean isNote = isNotebook();
        groupNotebook.setVisible(isNote);
        groupDesktop.setVisible(!isNote);
    }

    private void definirEstadoTela(boolean acesso, boolean areaDados, boolean novo, boolean buscar,
                                   boolean incluir, boolean alterar, boolean excluir) {
        cmbTipo.setEnabled(acesso);
        txtSerial.setEnabled(acesso);

        txtDescricao.setEnabled(areaDados);
        radioEhNovoNao.setEnabled(areaDados);
        radioEhNovoSim.setEnabled(areaDados);
        txtValorEstimado.setEnabled(areaDados);
        groupNotebook.setEnabled(areaDados);
        groupDesktop.setEnabled(areaDados);

        btnNovo.setEnabled(novo);
        btnBuscar.setEnabled(buscar);
        btnIncluir.setEnabled(incluir);
        btnAlterar.setEnabled(alterar);
        btnExcluir.setEnabled(excluir);
    }

    private void definirEstadoInicial() {
        definirEstadoTela(true, false, true, true, false, false, false);
    }

    private void definirEstadoInclusao() {
        definirEstadoTela(true, true, false, false, true, false, false);
    }

    private void definirEstadoAlteracaoExclusao() {
        definirEstadoTela(false, true, false, false, false, true, true);
    }

    private void exibirMensagemSimples(String titulo, String mensagem) {
        MessageBox messageBox = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
        messageBox.setText(titulo);
        messageBox.setMessage(mensagem);
        messageBox.open();
    }

    private void exibirMensagensErro(String titulo, ListaString mensagens) {
        Display display = Display.getCurrent();
        Shell dialogShell = new Shell(display, SWT.TITLE | SWT.CLOSE | SWT.RESIZE | SWT.APPLICATION_MODAL);
        dialogShell.setText(titulo);
        dialogShell.setLayout(new GridLayout(1, false));

        List listErros = new List(dialogShell, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
        listErros.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        for (int i = 0; i < mensagens.tamanho(); i++) {
            listErros.add(mensagens.buscar(i));
        }

        dialogShell.setSize(400, 220);
        dialogShell.open();
        while (!dialogShell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }
}