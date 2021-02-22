import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

// report screen - final screen of application
public class ReportScreen extends JFrame implements ActionListener {
	
	// objects
	private Project currentProject;
	private JPanel reportsPanel;
	
	// fonts
	private Font font = new Font("Helvetica", Font.BOLD, 19);
	private Font font2 = new Font("Helvetica", Font.BOLD, 20);
	private Font font3 = new Font("Helvetica", Font.BOLD, 26);
	
	// GUI components
	private JButton linkButton=new JButton();
	private JButton saveReportButton=new JButton();
	private JFrame reportsFrame;
	private Material currentMaterial;
	private JLabel[]hazardLevelLabels=new JLabel[3];
	private JLabel[]hazardLevelImage=new JLabel[3];
	private JLabel currentHazardLevel,currentHazardLevelLabel;
	private JLabel currentHazardLevelBackground;
	private JLabel background; 
	
	//Creates the screen for the reports panel, passes in the project of the user
	public ReportScreen(Project project) {
		
		//Sets the graphics of the link button
		linkButton.setIcon(new ImageIcon("./backgrounds/linkButton.jpg"));
		
		//Sets the graphics for the save report button
		saveReportButton.setIcon(new ImageIcon("./backgrounds/saveButton.jpg"));
		
		//Creates a label which displays the current graphics level of the materials
		currentHazardLevel=new JLabel();
		
		//Creates a background for the hazard level background for graphical purposes, sets the location and size
		currentHazardLevelBackground=new JLabel(new ImageIcon("./backgrounds/currentHazardLevelBackground.jpg"));
		currentHazardLevelBackground.setBounds(940,0,210,110);
		currentHazardLevelBackground.setVisible(true);
	
		//Adds action listener to the save report button
		saveReportButton.addActionListener(this);
		linkButton.addActionListener(this);
		
		//Creates a new project and clones the user's project
		currentProject=new Project();
		currentProject.projectClone(project);
		
		//Creates the frame for the reports panel
		reportsFrame = new JFrame();
		
		//Sets the background for the reports panel
		background = new JLabel(new ImageIcon("./backgrounds/reportBackground.jpg"));
		
		//Sets the location of the background
		background.setBounds(0, 0, 1280, 720);
		
		//Creates the panel that stores all the contents in the frame
		JPanel masterMaterialPanel = new JPanel();
		
		//Creates a panel which contains all the materials in the current project
		JPanel materialPanel = new JPanel();
		
		//Creates a scroll pane which enables the user to scroll through the materials only vertically
		JScrollPane materialsScrollPane = new JScrollPane(materialPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		//Creates the size of the materials panel and the scroll pane
		materialsScrollPane.setPreferredSize(new Dimension(205, 700));
		materialPanel.setPreferredSize(new Dimension(205, 1000));
		
		//Adds the scroll panel to the master panel
		masterMaterialPanel.add(materialsScrollPane);
		masterMaterialPanel.setBounds(10, 120, 205, 700);
		
		//Adds the panel to the background
		background.add(masterMaterialPanel);
		
		//Stores the initial location for material buttons
		int tempY = 0;
		
		//counts the number of materials
		int counter = 0;
		
		//Stores the height of the panel
		int scrollPaneHeight = 1000;
		
		//Displays all the current materials and adds it to the material panel
		//Sets the location of the materials, adds action listener to the buttons and sets visibility to true
		for (int materialCount = 0; materialCount < currentProject.getMaterials().size(); materialCount++) {
			currentProject.getMaterials().get(materialCount).getReportMaterialButton().setBounds(0, tempY, 200, 200);
			currentProject.getMaterials().get(materialCount).getReportMaterialButton().setVisible(true);
			currentProject.getMaterials().get(materialCount).getReportMaterialButton().addActionListener(this);

			materialPanel.add(currentProject.getMaterials().get(materialCount).getReportMaterialButton());
			
			//Increments the number of materials in the scroll panel as materials are added
			counter++;
			
			//If it reaches 4, the materials panel is resized
			if (counter == 4) {
				
				//Resets the counter
				counter = 0;
				
				//Increases the height of the panel
				scrollPaneHeight += 500;
				materialPanel.setPreferredSize(new Dimension(205, scrollPaneHeight));
			}

		}
		
		//Creates the panel for the reports
		JPanel panelForReportPanel = new JPanel();
		reportsPanel = new JPanel();
		
		//Creates the scroll pane which stores information about the materials
		JScrollPane reportsScrollPane = new JScrollPane(reportsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		reportsPanel.setLayout(null);	
		
		//Sets the dimensions and location of the scrollpane, scroll panel and reports panel
		reportsScrollPane.setPreferredSize(new Dimension(1000, 700));
		reportsPanel.setPreferredSize(new Dimension(1000, 1000));
		panelForReportPanel.add(reportsScrollPane);
		panelForReportPanel.setBounds(250, 120, 1000, 700);
		
		//Adds the reports panel to the background
		background.add(panelForReportPanel);
		
		//Adds the background to the frame's content pane
		reportsFrame.getContentPane().add(background);
		background.setVisible(true);
		
		//Sets the size of the frame
		reportsFrame.setSize(1280, 885);
		reportsFrame.setVisible(true);
		reportsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Creates a label for the current hazard label
		//Sets the location, size dimensions and font of the label
		currentHazardLevelLabel=new JLabel("Current Level= ");
		currentHazardLevelLabel.setFont(font3);
		currentHazardLevelLabel.setForeground(Color.white);
		currentHazardLevelLabel.setBounds(750, 35, 200, 25);
		currentHazardLevelLabel.setVisible(true);
		background.add(currentHazardLevelLabel);
		
		//Creates a legend for the hazard levels
		JLabel hazardLevel=new JLabel("Hazard Levels");
		hazardLevel.setFont(font3);
		hazardLevel.setForeground(Color.white);
		hazardLevel.setBounds(175, 0, 200, 25);
		hazardLevel.setVisible(true);
		background.add(hazardLevel);
		
		//Creates 3 labels of 3 different colours and its name( low, med, high)
		for(int levelCount=0;levelCount<3;levelCount++) {
			hazardLevelLabels[levelCount]=new JLabel();
			hazardLevelImage[levelCount]=new JLabel();

		}
		
		//Sets the text of the labels
		hazardLevelLabels[0]=new JLabel("Low= ");
		hazardLevelLabels[1]=new JLabel("Medium= ");
		hazardLevelLabels[2]=new JLabel("High= ");
		
		//Initial location of the labels
		int tempX=5;
		
		//Sets the font of the labels, location, size, and adds it to the background
		for(int levelCount=0;levelCount<3;levelCount++) {
			hazardLevelLabels[levelCount].setFont(font2);
			hazardLevelLabels[levelCount].setBounds(tempX, 35, 150, 50);
			hazardLevelLabels[levelCount].setVisible(true);
			hazardLevelLabels[levelCount].setForeground(Color.white);
			background.add(hazardLevelLabels[levelCount]);
			
			//Moves the next label by 200 pixels
			tempX+=200;

		}
		//Sets the image icons for the hazard level images
		hazardLevelImage[0]=new JLabel(new ImageIcon("./backgrounds/low.jpg"));
		hazardLevelImage[1]=new JLabel(new ImageIcon("./backgrounds/medium.jpg"));
		hazardLevelImage[2]=new JLabel(new ImageIcon("./backgrounds/high.jpg"));
		
		//Stores the initial location of the hazard level images
		tempX=125;
		
		//Sets the location of the hazard level images, sets the visibility to true, and adds the labels to the background
		for(int levelCount=0;levelCount<3;levelCount++) {
		
			hazardLevelImage[levelCount].setVisible(true);
			hazardLevelImage[levelCount].setBounds(tempX, 35, 50, 50);
			
			//Adds the images to the background
			background.add(	hazardLevelImage[levelCount]);
			
			//Moves the images by 200 pixels right
			tempX+=200;

		}
		background.add(currentHazardLevelBackground);
		
		//Clones the first material of the project to display its information
		currentMaterial=new Material();
		currentMaterial.materialClone(project.getMaterials().get(0));
		reportsPanel.removeAll();
		repaint();
		revalidate();
		
		//Displays the hazard level of the current material
		hazardLevel(currentMaterial);
		
		//Displays the report panel
		reportPanelDisplay(currentMaterial);
	}
	
	//This method displays the hazard level of the current material
	public void hazardLevel(Material currentMaterial) {
		
		//Checks where the hazard level lands. Depending on the level it sets the icon
		if(currentMaterial.getHazardRating()>=0&&currentMaterial.getHazardRating()<=2) {
			currentHazardLevel.setIcon(new ImageIcon("./backgrounds/low.jpg"));
		}
		else if(currentMaterial.getHazardRating()==3) {
			currentHazardLevel.setIcon((new ImageIcon("./backgrounds/medium.jpg")));

		}
		else {
			currentHazardLevel.setIcon(new ImageIcon("./backgrounds/high.jpg"));

		}
		
		//Refreshes the screen
		SwingUtilities.updateComponentTreeUI(reportsFrame);
		
		//Sets the location of the current hazard level
		currentHazardLevel.setBounds(5,5,200,100);
		//Adds the current hazard level to the background
		currentHazardLevelBackground.add(currentHazardLevel);
		currentHazardLevel.setVisible(true);
	}
	
//This method displays the information for each of the materials
	public void reportPanelDisplay(Material currentMaterial){
		
		//Clears what is currently in the reports panel
		reportsPanel.removeAll();
		
		//Stores the current material info in a jlabel
		JLabel materialInfo=new JLabel(currentMaterial.reportDisplay());
		
		//Sets the font of the material info
		materialInfo.setFont(font);
		
		//Sets the dimensions of the panel based on the size of the material info
		reportsPanel.setPreferredSize(new Dimension((int)materialInfo.getPreferredSize().getWidth()+50,(int)materialInfo.getPreferredSize().getHeight()+50));
		reportsPanel.removeAll();
		
	//Adds the material info to the reports panel
		reportsPanel.add(materialInfo);
		
		//Sets the location of the material info
		materialInfo.setBounds(0, 0, (int)materialInfo.getPreferredSize().getWidth(), (int)materialInfo.getPreferredSize().getHeight());
		
		//Sets the visibility of the material info to true
		materialInfo.setVisible(true);
		
		//Sets the location of the save button and the link button and adds it to the panel
		linkButton.setBounds(10, (int)materialInfo.getPreferredSize().getHeight()+20, 250, 100);
		saveReportButton.setBounds(600, (int)materialInfo.getPreferredSize().getHeight()+20, 250, 100);
		reportsPanel.add(linkButton);
		reportsPanel.add(saveReportButton);

		repaint();
		revalidate();
		
		linkButton.setVisible(true);
		
		//Refreshes the screen
		SwingUtilities.updateComponentTreeUI(reportsFrame);

		
		
	}
	//If the user saves the report, this method writes the material contents to a text file
	public void saveReport() {
		
		try {
			
			//Stores and formats the data
			String data="";
			data+="Project Name: "+currentProject.getProjectName()+"\n";
			data+="Project Creator: "+currentProject.getUserName()+"\n";
			data+="Date Created: "+currentProject.getDateCreated()+"\n\n";
			
			//Stores all the contents of the material information for all the materials in a string
			for(int materialCount=0;materialCount<currentProject.getMaterials().size();materialCount++) {
				data+=currentProject.getMaterials().get(materialCount).saveReport();
			}
			
			//Creates the file based on the project name
            File newTextFile = new File("./Reports/"+currentProject.getProjectName()+".txt");
            
            //Writes the file to the text file that was just creating
            FileWriter fw = new FileWriter(newTextFile);
            fw.write(data);
            
            //closes the file writer
            fw.close();

        } catch (IOException iox) {
        	
            //do stuff with exception
            iox.printStackTrace();
        }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//If a material button was clicked, the reports panel would display its information
		for (int materialCount = 0; materialCount < currentProject.getMaterials().size(); materialCount++) {
			if (e.getSource() == currentProject.getMaterials().get(materialCount).getReportMaterialButton()) {
				
				//Stores the current material to the material that was just clicked
				currentMaterial=new Material();
				currentMaterial.materialClone(currentProject.getMaterials().get(materialCount));
				reportsPanel.removeAll();
				repaint();
				revalidate();
				
				//displays the hazard level of the current material
				hazardLevel(currentMaterial);
				
				//Displays the material info in the reports panel
				reportPanelDisplay(currentProject.getMaterials().get(materialCount));
			}
		}
		
		//if the link button is clicked, it would take the user to the source of the material's information/store
		if(e.getSource()==linkButton) {
			
			try {
			    Desktop.getDesktop().browse(new URL(currentMaterial.getHyperlink()).toURI());
			} catch (Exception error) {
				
			}
		}
		//Saves the report if the save report button is clicked
		if(e.getSource()==saveReportButton) {
			saveReport();
		}
	}
}