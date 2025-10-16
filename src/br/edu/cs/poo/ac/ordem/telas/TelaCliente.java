package br.edu.cs.poo.ac.ordem.telas;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.text.MaskFormatter;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.MessageBox;

import br.edu.cs.poo.ac.ordem.entidades.Cliente;
import br.edu.cs.poo.ac.ordem.entidades.Contato;
import br.edu.cs.poo.ac.ordem.mediators.ClienteMediator;
import br.edu.cs.poo.ac.ordem.mediators.ResultadoMediator;
import br.edu.cs.poo.ac.utils.ListaString;
import br.edu.cs.poo.ac.utils.StringUtils;
import org.eclipse.swt.graphics.Point;

public class TelaCliente {

    protected Shell shell;
    private Text txtCpfCnpj;
    private Text txtNome;
    private Text txtEmail;
    private Text txtCelular;
    private Text txtDataCadastro;
    private Button chkEhZap;

    private Button btnNovo;
    private Button btnBuscar;
    private Button btnIncluir;
    private Button btnAlterar;
    private Button btnExcluir;
    private Button btnCancelar;

    private ClienteMediator mediator = ClienteMediator.getInstancia();

    public static void main(String[] args) {
        try {
            TelaCliente window = new TelaCliente();
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
        shell.setSize(500, 361);
        shell.setText("CRUD de Cliente");

        Label lblCpfCnpj = new Label(shell, SWT.NONE);
        lblCpfCnpj.setBounds(10, 26, 64, 15);
        lblCpfCnpj.setText("CPF/CNPJ:");

        txtCpfCnpj = new Text(shell, SWT.BORDER);
        txtCpfCnpj.setBounds(80, 23, 154, 21);

        Label lblNome = new Label(shell, SWT.NONE);
        lblNome.setBounds(10, 67, 55, 15);
        lblNome.setText("Nome:");

        txtNome = new Text(shell, SWT.BORDER);
        txtNome.setBounds(80, 64, 316, 21);

        Label lblEmail = new Label(shell, SWT.NONE);
        lblEmail.setBounds(10, 106, 55, 15);
        lblEmail.setText("E-mail:");

        txtEmail = new Text(shell, SWT.BORDER);
        txtEmail.setBounds(80, 103, 316, 21);

        Label lblCelular = new Label(shell, SWT.NONE);
        lblCelular.setBounds(10, 145, 55, 15);
        lblCelular.setText("Celular:");

        txtCelular = new Text(shell, SWT.BORDER);
        txtCelular.setBounds(80, 142, 154, 21);

        Label lblEhZap = new Label(shell, SWT.NONE);
        lblEhZap.setBounds(255, 145, 45, 15);
        lblEhZap.setText("É Zap?");

        chkEhZap = new Button(shell, SWT.CHECK);
        chkEhZap.setBounds(306, 144, 15, 16);

        Label lblData = new Label(shell, SWT.NONE);
        lblData.setBounds(10, 184, 90, 15);
        lblData.setText("Data Cadastro:");

        txtDataCadastro = new Text(shell, SWT.BORDER);
        txtDataCadastro.setBounds(106, 181, 128, 21);

        btnNovo = new Button(shell, SWT.NONE);
        btnNovo.setBounds(260, 22, 75, 25);
        btnNovo.setText("Novo");

        btnBuscar = new Button(shell, SWT.NONE);
        btnBuscar.setBounds(341, 22, 75, 25);
        btnBuscar.setText("Buscar");

        btnIncluir = new Button(shell, SWT.NONE);
        btnIncluir.setBounds(10, 226, 75, 25);
        btnIncluir.setText("Incluir");

        btnAlterar = new Button(shell, SWT.NONE);
        btnAlterar.setBounds(91, 226, 75, 25);
        btnAlterar.setText("Alterar");

        btnExcluir = new Button(shell, SWT.NONE);
        btnExcluir.setBounds(172, 226, 75, 25);
        btnExcluir.setText("Excluir");

        btnCancelar = new Button(shell, SWT.NONE);
        btnCancelar.setBounds(341, 226, 75, 25);
        btnCancelar.setText("Cancelar");

        definirEstadoInicial();
        addListeners();
    }

    private void addListeners() {
        txtCpfCnpj.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                formatarCpfCnpj();
            }
        });

        txtDataCadastro.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtDataCadastro.setText(removerNaoDigitos(txtDataCadastro.getText()));
            }
            @Override
            public void focusLost(FocusEvent e) {
                String data = removerNaoDigitos(txtDataCadastro.getText());
                if (data.length() == 8) {
                    try {
                        MaskFormatter mascara = new MaskFormatter("##/##/####");
                        mascara.setValueContainsLiteralCharacters(false);
                        txtDataCadastro.setText(mascara.valueToString(data));
                    } catch (java.text.ParseException ex) {
                    }
                }
            }
        });

        btnNovo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String cpfCnpj = removerNaoDigitos(txtCpfCnpj.getText());
                if (StringUtils.estaVazia(cpfCnpj)) {
                    limparCamposDados();
                    definirEstadoInclusao();
                    return;
                }

                Cliente c = mediator.buscar(cpfCnpj);
                if (c != null) {
                    exibirMensagemSimples("Erro", "Cliente já cadastrado!");
                } else {
                    limparCamposDados();
                    definirEstadoInclusao();
                }
            }
        });

        btnBuscar.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String cpfCnpj = removerNaoDigitos(txtCpfCnpj.getText());
                Cliente c = mediator.buscar(cpfCnpj);
                if (c == null) {
                    exibirMensagemSimples("Erro", "Cliente não encontrado!");
                } else {
                    preencherTela(c);
                    definirEstadoAlteracaoExclusao();
                }
            }
        });

        btnIncluir.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                Cliente cliente = montarClienteDaTela();
                ResultadoMediator res = mediator.incluir(cliente);
                if (res.isOperacaoRealizada()) {
                    exibirMensagemSimples("Sucesso", "Inclusão realizada com sucesso");
                    limparTelaToda();
                    definirEstadoInicial();
                } else {
                    exibirMensagensErro("Erros de Validação", res.getMensagensErro());
                }
            }
        });

        btnAlterar.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                Cliente cliente = montarClienteDaTela();
                ResultadoMediator res = mediator.alterar(cliente);
                if (res.isOperacaoRealizada()) {
                    exibirMensagemSimples("Sucesso", "Alteração realizada com sucesso");
                    limparTelaToda();
                    definirEstadoInicial();
                } else {
                    exibirMensagensErro("Erros de Validação", res.getMensagensErro());
                }
            }
        });

        btnExcluir.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String cpfCnpj = removerNaoDigitos(txtCpfCnpj.getText());
                ResultadoMediator res = mediator.excluir(cpfCnpj);
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
            @Override
            public void widgetSelected(SelectionEvent e) {
                limparTelaToda();
                definirEstadoInicial();
            }
        });
    }
    private String removerNaoDigitos(String texto) {
        if (texto == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (char c : texto.toCharArray()) {
            if (Character.isDigit(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private Cliente montarClienteDaTela() {
        String cpfCnpj = removerNaoDigitos(txtCpfCnpj.getText());
        String nome = txtNome.getText();
        String email = txtEmail.getText();
        String celular = txtCelular.getText();
        boolean ehZap = chkEhZap.getSelection();

        LocalDate dataCadastro = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dataCadastro = LocalDate.parse(txtDataCadastro.getText(), formatter);
        } catch (DateTimeParseException e) {
        }

        Contato contato = new Contato(email, celular, ehZap);
        return new Cliente(cpfCnpj, nome, contato, dataCadastro);
    }

    private void preencherTela(Cliente c) {
        if (c.getNome() != null) {
            txtNome.setText(c.getNome());
        } else {
            txtNome.setText("");
        }

        if (c.getContato() != null) {
            if (c.getContato().getEmail() != null) {
                txtEmail.setText(c.getContato().getEmail());
            } else {
                txtEmail.setText("");
            }

            if (c.getContato().getCelular() != null) {
                txtCelular.setText(c.getContato().getCelular());
            } else {
                txtCelular.setText("");
            }
            chkEhZap.setSelection(c.getContato().isEhZap());
        }

        if (c.getDataCadastro() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            txtDataCadastro.setText(c.getDataCadastro().format(formatter));
        }
    }

    private void limparCamposDados() {
        txtNome.setText("");
        txtEmail.setText("");
        txtCelular.setText("");
        txtDataCadastro.setText("");
        chkEhZap.setSelection(false);
    }

    private void limparTelaToda() {
        txtCpfCnpj.setText("");
        limparCamposDados();
    }

    private void formatarCpfCnpj() {
        String texto = removerNaoDigitos(txtCpfCnpj.getText());
        try {
            MaskFormatter mascara = null;
            if (texto.length() == 11) {
                mascara = new MaskFormatter("###.###.###-##");
            } else if (texto.length() == 14) {
                mascara = new MaskFormatter("##.###.###/####-##");
            }

            if (mascara != null) {
                mascara.setValueContainsLiteralCharacters(false);
                txtCpfCnpj.setText(mascara.valueToString(texto));
            }
        } catch (java.text.ParseException ex) {
        }
    }

    private void definirEstadoTela(boolean cpfCnpj, boolean areaDados, boolean novo, boolean buscar,
                                   boolean incluir, boolean alterar, boolean excluir) {
        txtCpfCnpj.setEnabled(cpfCnpj);
        txtNome.setEnabled(areaDados);
        txtEmail.setEnabled(areaDados);
        txtCelular.setEnabled(areaDados);
        txtDataCadastro.setEnabled(areaDados);
        chkEhZap.setEnabled(areaDados);

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

        dialogShell.setSize(350, 200);
        dialogShell.open();
        while (!dialogShell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }
}