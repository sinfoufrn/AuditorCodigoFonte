/*
 * Universidade Federal do Rio Grande do Norte
 * Superintendência de Informática
 * Diretoria de Sistemas
 * Equipe Orbitais
 *
 * É proibido usar, copiar, modificar, mesclar, publicar, distribuir, sublicenciar e / ou vender cópias
 * desse Software sem estar de acordo com os termos da cooperação da UFRN.
 *
 * O aviso de copyright acima deve ser incluído em todas as cópias ou partes substanciais do Software.
 *
 * AuditorCodigoFonte
 * br.ufrn.eligere
 * AuditorCodigoFonteApplication
 * 01/09/2020 11:15
 */
package br.ufrn.eligere;

import br.ufrn.eligere.auditoria.AuditoriaCodigoFonte;
import br.ufrn.eligere.gui.GridBagLayoutUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Start the application and create a GUI using Java Swing to audit source code.
 */
@SpringBootApplication
public class AuditorCodigoFonteApplication implements CommandLineRunner {

	public static void main(String[] args) {
		new SpringApplicationBuilder(AuditorCodigoFonteApplication.class).headless(false).run(args);
	}



	/**
	 * Draw the GUI
	 *
	 * @param args
	 */
	@Override
	public void run(String... args) {

		JFrame frame = new JFrame("Auditor de Código");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,450);
		frame.setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);

		GridBagLayout layout = new GridBagLayout();


		/**************************************************
		 *                Components
		 *************************************************/

		JPanel mainPanel = new JPanel(new BorderLayout());

		JLabel infoLabel = new JLabel(
				"<html> " +
						" <div style=\"text-align : center; width: 100%;\"> " +
						" Informe o Hash e Data de Geração Mostrados Durante a Eleição, Selecione os Arquivos com o Código Auditável da Aplicação e Clique em \"Verificar\" " +
						" </div> " +
						"</html>");
		JPanel centerPanel = new JPanel(layout);

		JScrollPane scrollOutPut = initLogTextField();


		centerPanel.setPreferredSize(new Dimension(800, 250));
		scrollOutPut.setPreferredSize(new Dimension(800, 150));

		mainPanel.add(infoLabel, BorderLayout.NORTH);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(scrollOutPut, BorderLayout.SOUTH);


		JLabel hashLabel = new JLabel("Hash Informado: ");
		JTextField hashField = new JTextField();

		JLabel dateLabel = new JLabel("Data de Geração do Hash: ");

		JFormattedTextField dateField = new JFormattedTextField(createFormatter("##/##/#### ##:##:##"));

		JFileChooser fc = new JFileChooser();
		fc.setMultiSelectionEnabled(true);
		fc.setDialogTitle("Selecione os arquivos .class da aplicação");
		JButton chooseFiles = new JButton("Selelecione os arquivos de Auditoria");
		chooseFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectAuditFiles(frame, fc);
			}
		});

		JButton verify = new JButton("Verificar");
		verify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calcHash(auditFiles, dateField.getText(), hashField.getText());
			}
		});

		JButton clear = new JButton("Limpar");
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc.setSelectedFiles(new File[0]);
				output.setText("Aplicação Iniciada ...");
				dateField.setText("");
				hashField.setText("");
				auditFiles = new File[0];
			}
		});

		GridBagLayoutUtil.configureLayout(centerPanel, dateLabel, dateField, hashLabel, hashField, chooseFiles, verify, clear);

		frame.setContentPane(mainPanel);
		frame.setVisible(true);
	}




	/**
	 * Audit files in local computer.
	 */
	File[] auditFiles;

	JTextArea output;

	/**
	 * Open a windown to user select the .class files.
	 */
	private void selectAuditFiles(JFrame frame, JFileChooser fc) {
		int returnVal = fc.showOpenDialog(frame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			auditFiles = fc.getSelectedFiles();
			for(File file :auditFiles){
				if( ! file.getName().endsWith(".class")){
					log("ERRO: Formato do Arquivo inválido. Os arquivos de auditoria são arquivos \".class\" ");
					auditFiles = new File[0];
					return;
				}
			}
		} else {
			log("Cancelado pelo usuário");
		}
	}

	/**
	 * Calculate the hash generated
	 */
	private void calcHash(File[] files, String dataValue, String informedHash) {
		log("");
		log("Calculando o hash ... ");
		log("Se todos os arquivos foram selecionados e a data informada corretamente, o Hash calculado deve ser igual ao hash mostrado pela aplicação.");
		log("");

		if(informedHash == null || informedHash.isEmpty()){
			log("ERRO:  Informe o Hash mostrado pelo sistema !!! ");
			return;
		}

		String timeFactor = "";

		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
			LocalDateTime hashDate = LocalDateTime.parse(dataValue, formatter);
			timeFactor = "" + hashDate.getYear() + hashDate.getMonthValue() + hashDate.getDayOfMonth() + hashDate.getHour() + hashDate.getMinute() + hashDate.getSecond();
		}catch(DateTimeParseException ex){
			log("ERRO:  Informe a data de geração do hash mostrada pelo sistema !!! ");
			return;
		}

		if(files == null || files.length == 0){
			log("ERRO:  Informe os Arquivos de Auditoria !!! ");
			return;
		}


		String hash = new AuditoriaCodigoFonte().calculaHashCodigoAuditavel(files, timeFactor);
		log("");
		log("O Hash calculado foi: "+hash);
		log("");
		if(hash.equals(informedHash)){
			log("---------------------------------------------------------------");
			log("!!!! Hash Válidado !!!!");
			log("---------------------------------------------------------------");
		}else{
			log("-------------------------------------------------------------------------------");
			log("Os Hashes não conferem. O código não pode ser validado");
			log("-------------------------------------------------------------------------------");
		}
		log("");
		log("");

	}


	private MaskFormatter createFormatter(String s) {
		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter(s);
		} catch (java.text.ParseException exc) {
			System.err.println("formatter is bad: " + exc.getMessage());
			System.exit(-1);
		}
		return formatter;
	}

	public JScrollPane initLogTextField() {
		output = new JTextArea();
		output.setForeground(Color.black);
		JScrollPane scrollV = new JScrollPane (output);
		scrollV.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollV.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		log("Aplicação Iniciada ...");
		return scrollV;
	}

	public void log(String message) {
		output.append(message+"\n");
	}

}
