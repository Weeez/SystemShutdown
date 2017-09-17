import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

public class SystemShutdown {
	public static void main(String[] args){		
		SystemShutdown mainProgram = new SystemShutdown();		
	}
	
	public SystemShutdown(){
		JFrame frame = new JFrame("System Shutdown");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(600, 600));
		frame.setResizable(false);
		
		CenterParams center = new CenterParams(frame);
		frame.setLocation(center.getWidth(), center.getHeight());
		
		JPanel mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10,10,10,10);		
				
		/* --Hours-- */
		JLabel inputLabel = new JLabel("Hour");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		mainPanel.add(inputLabel, gbc);
		
		JTextField inputField = new JTextField("0");
		inputField.setCaretPosition(inputField.getText().length());
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.gridheight = 1;
		gbc.weightx = 3.0;
		gbc.weighty = 2.0;
		mainPanel.add(inputField, gbc);		
		
		/* --MINUTES-- */
		JLabel mflLabel = new JLabel("Minutes");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.weightx = 0.5;	
		mainPanel.add(mflLabel, gbc);
		
		JTextField mflField = new JTextField("0");
		mflField.setCaretPosition(mflField.getText().length());
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		gbc.weightx = 3;
		mainPanel.add(mflField, gbc);		
		
		/* --SECONDS-- */
		JLabel outputLabel = new JLabel("Seconds");
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.weightx = 0.5;
		gbc.gridwidth = 1;
		mainPanel.add(outputLabel, gbc);
		
		JTextField outputField = new JTextField("0");
		outputField.setCaretPosition(outputField.getText().length());
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.weightx = 3;
		gbc.gridwidth = 2;
		mainPanel.add(outputField, gbc);		
		
		/* --TEXTAREA--*/
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.ipady = 40;
		gbc.weightx = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 4;
		textArea.setText("Output messages come here...");
		mainPanel.add(textArea, gbc);
		
		/* --SHUTDOWN BUTTON-- */
		JButton shutdownButton = new JButton("SHUTDOWN");
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 4;
		gbc.gridx = 0;
		gbc.gridy = 5;
		mainPanel.add(shutdownButton, gbc);


		/* --CANCEL BUTTON-- */
		JButton cancelButton = new JButton("Cancel Shutdown");					
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 4;
		gbc.gridx = 0;
		gbc.gridy = 6;
		mainPanel.add(cancelButton, gbc);
		
		/* --FUNCTIONS-- */		
		shutdownButton.addActionListener(new ActionListener(){
			
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	String inputHours = inputField.getText();
	        	String inputMinutes = mflField.getText();
	        	String inputSeconds = outputField.getText();
	        	
	        	int hour = 0;
	        	int minutes = 0;
	        	int seconds = 0;
	        	
	        	int calculatedResult = 0;
	        	String error = "";
	        	
	        	try {
	        		hour = Integer.parseInt(inputHours);
		        	minutes = Integer.parseInt(inputMinutes);
		        	seconds = Integer.parseInt(inputSeconds);
		        	
		        	if (hour > 0) {		        		
		        		calculatedResult += hour * 3600;
		        	}
		        	if(minutes > 0) {
		        		if(minutes > 59) {
		        			throw new Exception("minutes cannot be bigger than 59");
		        		} 
		        		calculatedResult += minutes * 60;
		        	}
		        	if(seconds > 0) {
		        		if(seconds > 59) {
		        			throw new Exception("minutes cannot be bigger than 59");
		        		}
		        		calculatedResult += seconds;
		        	}
	        	}catch(Exception ex) {
	        		System.out.println(ex);
	        		error = ex.toString();
	        	}
	        	
	            try {		      	            	
	            	if (error == "") {
		            	ProcessBuilder pb = new ProcessBuilder("cmd", "/C", "shutdown", "/s", "/t", Integer.toString(calculatedResult));
	            		pb.redirectErrorStream(true);
	            		Process p = pb.start();								
						BufferedReader in1 = new BufferedReader(new InputStreamReader(p.getInputStream()));
						StringBuilder string = new StringBuilder();
						String line;
						while ((line = in1.readLine()) != null) {								    
						    string.append(line);
						    string.append(System.getProperty("line.separator"));
						}
						p.waitFor();
						textArea.setText(string.toString());	
						
						//((JFrame)shutdownButton.getTopLevelAncestor()).pack();
	            	}else {
	            		textArea.setText(error);
						//((JFrame)shutdownButton.getTopLevelAncestor()).pack();
	            	}
	            } catch (Exception e1){
	            	e1.printStackTrace();
	            }  
	        }

		});

		cancelButton.addActionListener(new ActionListener(){
	        @Override
	        public void actionPerformed(ActionEvent e) {	            
	            try {		      	            		            	
	            	ProcessBuilder pb = new ProcessBuilder("cmd", "/C", "shutdown", "/a");
            		pb.redirectErrorStream(true);
            		Process p = pb.start();								
					BufferedReader in1 = new BufferedReader(new InputStreamReader(p.getInputStream()));
					StringBuilder string = new StringBuilder();
					String line;
					while ((line = in1.readLine()) != null) {								    
					    string.append(line);
					    string.append(System.getProperty("line.separator"));
					}
					p.waitFor();
					textArea.setText(string.toString());	
					
					//((JFrame)shutdownButton.getTopLevelAncestor()).pack();
	            } catch (Exception e1){
	            	e1.printStackTrace();
	            }  
	        }
		});
	
		
		frame.setContentPane(mainPanel);
		frame.setVisible(true);		
	}
	
	class CenterParams{
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		JFrame frame;
		
		public CenterParams(JFrame frame){
			super();
			this.frame = frame;
		}
		public int getHeight(){
			return dim.height/2-frame.getSize().height/2;
		}
		
		public int getWidth(){
			return dim.width/2-frame.getSize().width/2;
		}		
	}
	
}