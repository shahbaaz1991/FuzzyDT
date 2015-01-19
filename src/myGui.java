import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class myGui {

	private JFrame frame;
	private JTextField fuzzField;
	private JMenuBar menuBar = new JMenuBar();
	private JMenu mnNewMenu = new JMenu("File");
	private JMenuItem mntmLoadDataSet = new JMenuItem("Load DataSet");
	JTextArea textArea = new JTextArea();
	JCheckBox chckbxMinmax = new JCheckBox("Min-Max");
	JCheckBox chckbxMaxproduct = new JCheckBox("Max-Product");
	private DataStore store;
	private RuleStore rules;
	private Fuzzy fuzz;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					myGui window = new myGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public myGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 554, 357);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			//	store.equals(fuzz.store);
			//	store.equals(fuzz.rule);
				fuzz = new Fuzzy();
				fuzz.n = Double.parseDouble(fuzzField.getText());
			    //fuzz.setFuzz(Double.parseDouble(fuzzField.getText()));
				if(chckbxMinmax.isSelected()==true)
				{
					fuzz.maxmin=true;
				}
				else if(chckbxMaxproduct.isSelected()==true)
				{
					fuzz.maxproduct=true;
				}
				else
				{
					fuzz.maxmin=false;
					fuzz.maxproduct=false;
				}
				fuzz.createFuzzySets();
				textArea.setText(fuzz.DisplayRes());
				
			}
		});
		btnRun.setBounds(439, 86, 89, 23);
		frame.getContentPane().add(btnRun);
		
		
		textArea.setBounds(10, 137, 518, 171);
		textArea.setText("");
		frame.getContentPane().add(textArea);
		
		JLabel lblFuzziness = new JLabel("Fuzziness:");
		lblFuzziness.setBounds(10, 48, 61, 14);
		frame.getContentPane().add(lblFuzziness);
		
		fuzzField = new JTextField();
		fuzzField.setText("0.0");
		fuzzField.setBounds(81, 45, 86, 20);
		frame.getContentPane().add(fuzzField);
		fuzzField.setColumns(10);
		
		JLabel lblInference = new JLabel("Inference:");
		lblInference.setBounds(10, 76, 61, 14);
		frame.getContentPane().add(lblInference);
		chckbxMinmax.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		
		chckbxMinmax.setBounds(81, 72, 97, 23);
		frame.getContentPane().add(chckbxMinmax);
		
	
		chckbxMaxproduct.setBounds(81, 98, 124, 23);
		frame.getContentPane().add(chckbxMaxproduct);
		
		JCheckBox chckbxProbabilityWeights = new JCheckBox("Probability Weights");
		chckbxProbabilityWeights.setBounds(241, 44, 124, 23);
		frame.getContentPane().add(chckbxProbabilityWeights);
		
		
		menuBar.setBounds(0, 0, 97, 21);
		frame.getContentPane().add(menuBar);
		
		
		menuBar.add(mnNewMenu);

		
		
		mntmLoadDataSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileOpen = new JFileChooser();
				fileOpen.showOpenDialog(null);
				fileOpen.cancelSelection();
				store = new DataStore(fileOpen.getSelectedFile().toString());
				textArea.setText("Data Set Loaded");
			}
		});
		mnNewMenu.add(mntmLoadDataSet);
		
		JMenuItem mntmLoadRules = new JMenuItem("Load Rules");
		mntmLoadRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileOpen = new JFileChooser();
				fileOpen.showOpenDialog(null);
				rules = new RuleStore(fileOpen.getSelectedFile().toString());
				textArea.setText("Rules Loaded");
			}
		});
		mnNewMenu.add(mntmLoadRules);
		
		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnNewMenu.add(mntmClose);
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
