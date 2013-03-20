import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;


public class SleepTimer {

	private JFrame frame;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SleepTimer window = new SleepTimer();
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
	public SleepTimer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblEnterNumberOf = new JLabel("time till sleep:");
		GridBagConstraints gbc_lblEnterNumberOf = new GridBagConstraints();
		gbc_lblEnterNumberOf.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnterNumberOf.anchor = GridBagConstraints.EAST;
		gbc_lblEnterNumberOf.gridx = 0;
		gbc_lblEnterNumberOf.gridy = 0;
		frame.getContentPane().add(lblEnterNumberOf, gbc_lblEnterNumberOf);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.anchor = GridBagConstraints.WEST;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		frame.getContentPane().add(textField, gbc_textField);
		textField.setColumns(10);
		
		JButton btnSleep = new JButton("Sleep");
		btnSleep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					executePSScript("sleep.ps1", textField.getText());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnSleep = new GridBagConstraints();
		gbc_btnSleep.gridx = 1;
		gbc_btnSleep.gridy = 1;
		frame.getContentPane().add(btnSleep, gbc_btnSleep);
	}
	
	public static String executePSScript(String scriptFilename, String args) throws Exception {
		if (!new File(scriptFilename).exists())
			throw new Exception("Script file doesn't exist: " + scriptFilename);
		
		String cmd = "cmd /c powershell -ExecutionPolicy RemoteSigned -noprofile -noninteractive -file \"" + scriptFilename + "\"";
		if (args != null && args.length() > 0)
			cmd += " " + args;
		return exec(cmd);
	}
	
	private static String exec(String command) throws Exception {
    	StringBuffer sbInput = new StringBuffer();
    	StringBuffer sbError = new StringBuffer();
    
	Runtime runtime = Runtime.getRuntime();
	Process proc = runtime.exec(command);
	proc.getOutputStream().close();
    	InputStream inputstream = proc.getInputStream();        
    	InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
    	BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
    
    	String line;
    	while ((line = bufferedreader.readLine()) != null) {
    		sbInput.append(line + "\n");
    	}
    
    	inputstream = proc.getErrorStream();
    	inputstreamreader = new InputStreamReader(inputstream);
    	bufferedreader = new BufferedReader(inputstreamreader);
    	while ((line = bufferedreader.readLine()) != null) {
        		sbError.append(line + "\n");
    	}
    
    	if (sbError.length() > 0)
    		throw new Exception("The command [" + command + "] failed to execute!\n\nResult returned:\n" + sbError.toString());

   		return "The command [" + command + "] executed successfully!\n\nResult returned:\n" + sbInput.toString();
}

}
