import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

/*
 * Author: Saenthan Parimalakanthan
 * Edited by: Malcolm Shi
 * Teacher:  Mr. Fernandes
 * Course: ICS4U1
 * Date: 12/15/2019
 * Purpose: Displays project related information, the user materials panel which contain all the materials in a current project
 *  and as well a the materials inventory panel which contains all the possible materials
 */

public class MaterialsGUI extends JFrame implements ActionListener, MouseListener {

	private ArrayList<JButton> materialImages = new ArrayList<JButton>();
	private ArrayList<JLabel> materialName = new ArrayList<JLabel>();

	// public int scaledWidth, scaledHeight;
	private JButton addNewMaterialButton, backButton, newMaterialAddButton, addMaterialImageButton,
			addMaterialToProjectButton;
	private JFrame materialInventoryFrame, newMaterialFrame;
	private JTextField[] newMaterialFields = new JTextField[12];
	private JLabel[] newMaterialLabels = new JLabel[12];
	private JPanel materialsPanel;
	private JFrame userMaterialFrame = new JFrame();
	private JPanel userMaterialContent, userMaterialsPanel;
	private JButton addToProjectButton;
	// private int currentProject;
	private Font font2 = new Font("Comic Sans MS", Font.BOLD, 15);
	private JFrame editFrame;
	private Font font = new Font("Comic Sans MS", Font.BOLD, 20);
	private Project currentProject;
	private ArrayList<Project> projectArray = new ArrayList<Project>();
	private ArrayList<Material> materialArray = new ArrayList<Material>();
	private int projectCount;
	private int preMaterialIndex;
	private JButton toEnvironmentalInputScreenButton, backToUserPanelButton, backToStartMenuButton;
	private JLabel addMaterialBackground;
	private JButton tryAgainButton;
	private JLabel errorHandlingPanel;
	private JButton toReportScreenButton;
	private JFrame environmentalInputFrame;
	private boolean status = true;

	public MaterialsGUI(ArrayList<Project> projectArray, ArrayList<Material> materialArray, boolean projectPanelType) {

		if (projectPanelType) {

			this.projectArray = projectArray;
			this.materialArray = materialArray;

			for (int i = 0; i < materialArray.size(); i++) {

				// Adds action listener to all the materials in the inventory panel
				this.materialArray.get(i).getMaterialButton().addActionListener(this);
			}

			// This runs the window that displays all the projects available in the project
			// Database
			projectListWindow();

		} else {

			// If the user wants to create a new project, this method is run
			newProject(projectArray, materialArray);
		}

	}

	// Displays the user inventory panel used to display the user's materials
	public void userMaterialsPanel() {

		// Creates the background for the panel
		JLabel background = new JLabel(new ImageIcon("./backgrounds/pink background.jpg"));

		// Creates the heading for the materials panel as a JLabel
		JLabel heading = new JLabel(new ImageIcon("./backgrounds/Title.png"));
		background.setBounds(0, 0, 1280, 720);

		// Creates the panel itself which contains all the materials
		userMaterialsPanel = new JPanel();

		// Creates a scrollpane to scroll through the user's materials, Can only scroll
		// vertically
		JScrollPane userMaterialScrollPane = new JScrollPane(userMaterialsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		// Creates the preferred size of the scroll pane and materials panel
		userMaterialScrollPane.setPreferredSize(new Dimension(1200, 500));
		userMaterialsPanel.setPreferredSize(new Dimension(1200, 1000));

		// Contains the scroll panel which has all the materials
		JPanel materialContent = new JPanel();

		// Sets the size and location of the heading
		heading.setBounds(150, 30, 1000, 100);
		heading.setVisible(true);

		// Sets the size and the location of the panel that holds the scroll pane
		materialContent.setBounds(35, 120, 1200, 500);

		// Adds the scroll pane to the JPanel
		materialContent.add(userMaterialScrollPane);
		materialContent.setVisible(true);

		// Creates a button to continue to the reports/environmental impact screen
		toEnvironmentalInputScreenButton = new JButton();

		// Sets the image for the next button
		toEnvironmentalInputScreenButton.setIcon(new ImageIcon("./backgrounds/nextBefore.jpg"));

		// Sets the location and size of the next button
		toEnvironmentalInputScreenButton.setBounds(1075, 620, 161, 58);

		// Will not create a solid colour for the button
		toEnvironmentalInputScreenButton.setContentAreaFilled(false);

		// No gap between the button's border and the button itself
		toEnvironmentalInputScreenButton.setMargin(new Insets(0, 0, 0, 0));

		// Adds action listener to the next button
		toEnvironmentalInputScreenButton.addActionListener(this);

		// Adds mouse listener to the button for graphical purposes(Changes colour when
		// mouse hovers over the button)
		toEnvironmentalInputScreenButton.addMouseListener(this);

		// Adds the button to the background
		background.add(toEnvironmentalInputScreenButton);

		// Adds the panel that contains the scroll pane to the background
		background.add(materialContent);

		// Adds the heading/title to the background
		background.add(heading);

		// Creates a button to add a new material to the project
		addMaterialToProjectButton = new JButton();

		/*
		 * Creates the image for the button Sets the location and size for the button
		 * Will create no gap between the border of the button and the button itself
		 * Adds action listener to the button
		 */
		addMaterialToProjectButton.setIcon(new ImageIcon("./backgrounds/add.png"));
		addMaterialToProjectButton.setBounds(10, 10, 200, 200);
		addMaterialToProjectButton.setContentAreaFilled(false);
		addMaterialToProjectButton.setMargin(new Insets(0, 0, 0, 0));
		addMaterialToProjectButton.addActionListener(this);

		// Sets the layout of the panel that contains the materials to null
		userMaterialsPanel.setLayout(null);

		// Adds the add material to project button to the materials panel
		userMaterialsPanel.add(addMaterialToProjectButton);

		// Sets the visibility of the button to true
		addMaterialToProjectButton.setVisible(true);

		// Creates the size of the frame
		userMaterialFrame.setSize(1280, 720);

		// Sets the visibility of the frame to true
		userMaterialFrame.setVisible(true);

		// Sets the visibility of the background to true
		background.setVisible(true);

		// Adds the background to the frame's content pane
		userMaterialFrame.getContentPane().add(background);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	// Creates a panel which contains all the possible materials available
	public void materialInventoryPanel() {

		// Creates a back button to take the user back to the user materials panel
		backToUserPanelButton = new JButton();

		/*
		 * Creates the image for the button Sets the location and size for the button
		 * Will prevent the solid colour of the button Will create no gap between the
		 * border of the button and the button itself Adds action listener to the button
		 * Adds mouse listener for graphical purposes(hovering over the button will
		 * cause it to change colours)
		 */
		backToUserPanelButton.setIcon(new ImageIcon("./backgrounds/backBefore2.jpg"));
		backToUserPanelButton.setBounds(10, 620, 161, 58);
		backToUserPanelButton.setVisible(true);
		backToUserPanelButton.setContentAreaFilled(false);
		backToUserPanelButton.setMargin(new Insets(0, 0, 0, 0));
		backToUserPanelButton.addActionListener(this);
		backToUserPanelButton.addMouseListener(this);

		// Creates the frame for the material inventory frame
		materialInventoryFrame = new JFrame();

		// Creates the panel which contains all the materials
		materialsPanel = new JPanel();

		// Creates a button to add a new material to the materials inventory
		addNewMaterialButton = new JButton();

		// Sets the icon of the add new material button
		addNewMaterialButton.setIcon(new ImageIcon("./backgrounds/add.png"));

		// Creates the background for the frame
		JLabel background = new JLabel(new ImageIcon("./backgrounds/blue background.jpg"));

		// Creates the heading/title for the frame
		JLabel heading = new JLabel(new ImageIcon("./backgrounds/inventory.png"));

		// Sets the location and size of the background
		background.setBounds(0, 0, 1280, 720);

		// materialsPanel.setBounds(0, 0, 1250, 500);
		// Sets the visibility of the panel to true and sets the layout to null
		materialsPanel.setLayout(null);
		materialsPanel.setVisible(true);

		// Creates the scroll pane which contains all the materials in the materials
		// database, Can only scroll down
		JScrollPane materialScrollPane = new JScrollPane(materialsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		// Sets the size of the scroll pane
		materialScrollPane.setPreferredSize(new Dimension(1200, 500));

		// Sets the size of thepanel which contains all the images
		materialsPanel.setPreferredSize(new Dimension(1200, 1000));

		// This methods displays all the materials in the material array
		imageScan();

		/*
		 * materialImages.get(3).setVisible(true);
		 * materialsPanel.add(materialImages.get(3));
		 * materialImages.get(3).setBounds(10, 300, 200, 200);
		 */
		// Repaints the scroll pane and the materials panel to refresh its contents
		materialScrollPane.repaint();
		materialsPanel.repaint();

		// Creates panel which contains the scroll pane
		userMaterialContent = new JPanel();

		// Sets the visibility of the heading to true and sets its size/location
		heading.setBounds(150, 30, 1000, 100);
		heading.setVisible(true);

		// Sets the size and location of the panel which contains the scroll pane
		userMaterialContent.setBounds(35, 120, 1200, 500);

		// Adds the scroll pane to the material scroll pane
		userMaterialContent.add(materialScrollPane);

		// Sets the visibility of the content panel to true
		userMaterialContent.setVisible(true);

		// Adds the content panel to the background
		background.add(userMaterialContent);

		// Adds the background heading
		background.add(heading);

		// adds the background to the content pane of the frame
		materialInventoryFrame.getContentPane().add(background);

		// Adds the back button to the user material frame
		background.add(backToUserPanelButton);

		// Sets the size of the frame and sets the visibility to true
		materialInventoryFrame.setSize(1280, 720);
		materialInventoryFrame.setVisible(true);

		// Sets the visibility of the background to true
		background.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// This method is run to display all the materials user materials panel
	public void addMaterialToUserPanel() {

		// removes all the current materials in the user materials panel
		userMaterialsPanel.removeAll();

		// Removes any materials in the materialArray if it is already in the project
		for (int inventoryCount = 0; inventoryCount < materialArray.size(); inventoryCount++) {

			for (int userMaterialCount = 0; userMaterialCount < currentProject.getMaterials()
					.size(); userMaterialCount++) {

				// Compares the names of the material name to check whether it exists
				if (materialArray.get(inventoryCount).getName()
						.equals(currentProject.getMaterials().get(userMaterialCount).getName())) {

					// Removes the material in the material array if it already exists in the users
					// project
					materialArray.remove(inventoryCount);
				}
			}
		}

		// Creates the buttons for the user materials panel
		for (int materialCount = 0; materialCount < currentProject.getMaterials().size(); materialCount++) {

			// Stores the icon of the materials
			Icon temp2 = currentProject.getMaterials().get(materialCount).getMaterialButton().getIcon();

			// Creates the new button for the user materials panel
			currentProject.getMaterials().get(materialCount).setMaterialButton(new JButton());

			// sets the icon of the materials
			currentProject.getMaterials().get(materialCount).getMaterialButton().setIcon(temp2);
		}

		for (int materialCount = 0; materialCount < currentProject.getMaterials().size(); materialCount++) {

			// Enables all the material buttons
			currentProject.getMaterials().get(materialCount).getMaterialButton().setEnabled(true);

			// Sets the disable icon of the button to prevent a grey colour
			currentProject.getMaterials().get(materialCount).getMaterialButton()
					.setDisabledIcon(currentProject.getMaterials().get(materialCount).getMaterialButton().getIcon());

			// Adds action listener to the buttons
			currentProject.getMaterials().get(materialCount).getMaterialButton().addActionListener(this);
		}
		// Stores the initial location of the materials in the current project
		int tempX = 40;
		int tempY = 18;

		// Counts the number of materials in a row
		int counter = 0;

		// Stores the initial height of the panel
		int panelHeight = 1000;

		// Counts the number of rows in the panel
		int rowCounter = 0;

		// Creates the buttons for the current materials
		for (int materialCount = 0; materialCount < currentProject.getMaterials().size(); materialCount++) {

			// Sets the visibility of the buttons
			currentProject.getMaterials().get(materialCount).getMaterialButton().setVisible(true);

			// Sets the location of the current materials in the project
			currentProject.getMaterials().get(materialCount).getMaterialButton().setBounds(tempX, tempY, 200, 200);

			// Adds the materials to the user panel
			userMaterialsPanel.add(currentProject.getMaterials().get(materialCount).getMaterialButton());

			// Creates the font for the material names
			currentProject.getMaterials().get(materialCount).getNameLabel().setFont(font2);

			// sets the material name labels' visibility to true
			currentProject.getMaterials().get(materialCount).getNameLabel().setVisible(true);

			// repositions the name based on its character length
			if (currentProject.getMaterials().get(materialCount).getName().length() <= 8) {
				currentProject.getMaterials().get(materialCount).getNameLabel().setBounds(tempX + 65, tempY - 21, 200,
						20);
			} else {
				currentProject.getMaterials().get(materialCount).getNameLabel().setBounds(tempX + 20, tempY - 21, 200,
						20);
			}

			// Adds the material name labels to the panel
			userMaterialsPanel.add(currentProject.getMaterials().get(materialCount).getNameLabel());

			// sets the initial quantities of all the materials
			currentProject.getMaterials().get(materialCount)
					.setQuantityField(new JTextField(currentProject.getMaterials().get(materialCount).getQuantity()));

			// displays the text from right to left
			currentProject.getMaterials().get(materialCount).getQuantityField()
					.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

			// Sets the quantity fields visibility to true
			currentProject.getMaterials().get(materialCount).getQuantityField().setVisible(true);

			// Sets the location of the quantity fields
			currentProject.getMaterials().get(materialCount).getQuantityField().setBounds(tempX + 50, tempY + 201, 100,
					25);

			// Adds the quantity fields to the panel
			userMaterialsPanel.add(currentProject.getMaterials().get(materialCount).getQuantityField());

			// Displays the material unit levels
			currentProject.getMaterials().get(materialCount).getUnitLabel().setBounds(tempX + 150, tempY + 187, 50, 50);

			// Sets the font of the unit labels
			currentProject.getMaterials().get(materialCount).getUnitLabel().setFont(font2);

			// sets the visibility of the unit labels to true
			currentProject.getMaterials().get(materialCount).getUnitLabel().setVisible(true);

			// Adds the unit labels to the panel
			userMaterialsPanel.add(currentProject.getMaterials().get(materialCount).getUnitLabel());

			// Moves the next material by 228 pixels
			tempX += 228;

			// Counts the number of rows of the material
			rowCounter++;

			// counts the number of materials in a row
			counter++;

			// If the number of materials in a row has reached 5, this creates a new row
			if (counter == 5) {

				// Moves the row down by 245 pixels down
				tempY += 245;

				// Sets the initial location of the material
				tempX = 40;

				// resets the counter
				counter = 0;
			}

			// Increases the panel size as materials are added
			if (rowCounter == 5) {

				panelHeight += 500;
				rowCounter = 0;

				// Creates the new size of the materials panel
				userMaterialsPanel.setPreferredSize(new Dimension(1200, panelHeight));
			}

		}

		// Positions and adds the add material button
		addMaterialToProjectButton.setBounds(tempX, tempY, 200, 200);
		userMaterialsPanel.add(addMaterialToProjectButton);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// This panel enables the user to add new materials to the material inventory
	public void addNewMaterialPanel() {

		// Button to add the image of the new material
		addMaterialImageButton = new JButton();

		// This button is displayed if the user enters wrong info
		tryAgainButton = new JButton("Click to try again");

		// Adds action listener to the button
		tryAgainButton.addActionListener(this);

		// Creates the frame that stores material fields
		newMaterialFrame = new JFrame();

		// Creates the background for the frame
		addMaterialBackground = new JLabel(new ImageIcon("./backgrounds/pinkAddMaterial.jpg"));

		// Sets the location of the background
		addMaterialBackground.setBounds(0, 0, 800, 800);

		// Creates a back button to go back to the materials inventory panel
		backButton = new JButton();

		// Creates the graphics for the back button
		backButton.setIcon(new ImageIcon("./backgrounds/back.png"));

		// Creates the button to add the material
		newMaterialAddButton = new JButton();

		// sets the icon for the button
		newMaterialAddButton.setIcon(new ImageIcon("./backgrounds/addBefore.png"));

		// Creates labels for the info required
		for (int labelCount = 0; labelCount < newMaterialLabels.length; labelCount++) {
			newMaterialLabels[labelCount] = new JLabel();

		}

		// Creates the text fields for all the materials
		for (int fieldCount = 0; fieldCount < newMaterialFields.length; fieldCount++) {
			newMaterialFields[fieldCount] = new JTextField("0");

		}
		// Stores the text for the field labels
		newMaterialLabels[0].setText("Material Name:");
		newMaterialLabels[1].setText("Manufacturing Location:");
		newMaterialLabels[2].setText("Hazardous Material + (% Composition):");
		newMaterialLabels[3].setText("Appearance & Odour:");
		newMaterialLabels[4].setText("Health Hazards");
		newMaterialLabels[5].setText("Routes of Exposure:");
		newMaterialLabels[6].setText("Autoignition Temperature (C):");
		newMaterialLabels[7].setText("Handling & Storing Options:");
		newMaterialLabels[8].setText("Exposure Control Measures:");
		newMaterialLabels[9].setText("Link To Source:");
		newMaterialLabels[10].setText("Unit Type: Eg. /L, /mL /Kg");
		newMaterialLabels[11].setText("Enter Hazard Rating(1-5); 5 Highest: ");

		// Stores the initial location of the labels
		int tempY = 10;

		// Displays the material field labels, creates the font and adds it to the panel
		for (int materialLabelCounter = 0; materialLabelCounter < newMaterialLabels.length; materialLabelCounter++) {
			newMaterialLabels[materialLabelCounter].setForeground(Color.BLACK);
			newMaterialLabels[materialLabelCounter].setBounds(10, tempY, 500, 25);
			newMaterialLabels[materialLabelCounter].setFont(font);
			newMaterialLabels[materialLabelCounter].setVisible(true);
			addMaterialBackground.add(newMaterialLabels[materialLabelCounter]);

			// Moves the labels down by 51 pixels
			tempY += 51;

		}

		// Stores the initial Y position of the text fields
		tempY = 10;

		for (int materialLabelCounter = 0; materialLabelCounter < newMaterialFields.length; materialLabelCounter++) {
			newMaterialFields[materialLabelCounter].setBounds(400, tempY, 250, 30);
			// Sets the visibility of the fields to true
			newMaterialFields[materialLabelCounter].setVisible(true);
			// Adds the material fields to the background
			addMaterialBackground.add(newMaterialFields[materialLabelCounter]);
			// Shifts the text fields down by 51 pixels
			tempY += 51;

		}

		// Sets the image of the button
		addMaterialImageButton.setIcon(new ImageIcon("./backgrounds/addImage.png"));

		// Prevents the button from being filled
		addMaterialImageButton.setContentAreaFilled(false);

		// No gap between the button border and the button itself
		addMaterialImageButton.setMargin(new Insets(0, 0, 0, 0));

		// Adds action listener to the button
		addMaterialImageButton.addMouseListener(this);

		/*
		 * Sets the initial location of the button Sets the visibility of the back
		 * button to true Adds the button to the background Adds action listener and
		 * mouse listener( when mouse hovers over it, it changes colour)
		 */
		backButton.setBounds(10, 680, 200, 72);
		backButton.setVisible(true);
		backButton.setContentAreaFilled(false);
		backButton.setMargin(new Insets(0, 0, 0, 0));
		addMaterialBackground.add(backButton);
		newMaterialFrame.add(addMaterialBackground);
		backButton.addMouseListener(this);
		backButton.addActionListener(this);

		// Creates the button which enables the user to add a new material
		newMaterialAddButton.setBounds(580, 680, 200, 72);
		newMaterialAddButton.setVisible(true);
		newMaterialAddButton.setContentAreaFilled(false);
		newMaterialAddButton.setMargin(new Insets(0, 0, 0, 0));
		addMaterialBackground.add(newMaterialAddButton);
		newMaterialAddButton.addMouseListener(this);
		newMaterialAddButton.addActionListener(this);

		// Creates a button to enable the user to add an image for the material
		addMaterialImageButton.setBounds(40, 610, 676, 72);
		addMaterialImageButton.addActionListener(this);

		// Adds the button to the background
		addMaterialBackground.add(addMaterialImageButton);

		// Sets the size of the frame
		newMaterialFrame.setSize(800, 800);
		newMaterialFrame.setVisible(true);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// Creates the frame which displays all the projects in the database
	public void projectListWindow() {

		// Creates a back button to go back to the start menu
		backToStartMenuButton = new JButton("Click to Go Back");
		backToStartMenuButton.setBounds(40, 640, 200, 40);
		backToStartMenuButton.setVisible(false);
		backToStartMenuButton.addActionListener(this);

		// Creates the font for the heading labels
		Font headingFont = new Font("Comic Sans MS", Font.BOLD, 17);

		// Creates the frame for the project edit window
		editFrame = new JFrame();

		// Creates the background for the project window
		JLabel background = new JLabel(new ImageIcon("./backgrounds/blue background.jpg"));

		// Creates the main title for the window
		JLabel heading = new JLabel(new ImageIcon("./backgrounds/projects.png"));

		// Sets the location of the background
		background.setBounds(0, 0, 1000, 500);

		// Creates the panel which contains all the projects
		JPanel editPanel = new JPanel();

		// Creates a scroll pane which contains the panel that displays the projects
		// Can only scroll vertically
		JScrollPane editScrollPane = new JScrollPane(editPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		// Sets the size of the scroll pane
		editScrollPane.setPreferredSize(new Dimension(500, 520));

		// Sets the size of the panel that the scroll pane stores
		editPanel.setPreferredSize(new Dimension(500, 520));

		// Creates the panel that stores all the elements
		JPanel masterPanel = new JPanel();

		// Sets the location of the title and its size
		heading.setBounds(50, 30, 500, 100);
		heading.setVisible(true);

		// Sets the location and size of the panel that contains all the elements of the
		// panel
		masterPanel.setBounds(40, 120, 500, 520);
		masterPanel.add(editScrollPane);
		masterPanel.setVisible(true);

		// sets the colour of the scroll pane
		editPanel.setBackground(Color.gray);

		// Adds the main panel to the background
		background.add(masterPanel);

		// Adds heading to the background
		background.add(heading);

		// Stores the initial location of the project edit buttons
		int tempX = 50;
		int tempY = 39;

		// Sets the layout of the panel to null
		editPanel.setLayout(null);

		// Creates the headings for the JTable
		JLabel projectNameLabel = new JLabel("Project Name");
		JLabel dateCreatedLabel = new JLabel("Date Created");

		// Sets the font of the headings
		projectNameLabel.setFont(headingFont);
		dateCreatedLabel.setFont(headingFont);

		// Sets the location, size and its visibility of the labels
		projectNameLabel.setBounds(180, 0, 150, 50);
		dateCreatedLabel.setBounds(320, 0, 150, 50);
		projectNameLabel.setVisible(true);
		dateCreatedLabel.setVisible(true);
		editPanel.add(projectNameLabel);
		editPanel.add(dateCreatedLabel);

		// Sets the position and visibility of the project edit buttons
		for (int projectCount = 0; projectCount < projectArray.size(); projectCount++) {

			projectArray.get(projectCount).getProjectButton().setVisible(true);
			projectArray.get(projectCount).getProjectButton().setBounds(tempX, tempY, 100, 50);
			editPanel.add(projectArray.get(projectCount).getProjectButton());
			projectArray.get(projectCount).getProjectButton().addActionListener(this);
			tempY += 50;
		}

		// Creates a 2D array of the projects to be used for a JTable
		String[][] projectInfo = new String[projectArray.size()][2];

		// Stores project contents in a Jtable
		for (int projectCount = 0; projectCount < projectArray.size(); projectCount++) {
			projectInfo[projectCount][0] = projectArray.get(projectCount).getProjectName();
			projectInfo[projectCount][1] = projectArray.get(projectCount).getDateCreated();

		}

		// Creates the names for each column
		String[] columnName = { "User Name", "Date Created" };

		// Creates the table itself
		JTable projectTable = new JTable(projectInfo, columnName);

		// Sets the position of the project table and its size based on the number of
		// the projects
		projectTable.setBounds(150, 40, 300, projectArray.size() * 50);

		// Creates the height of the table based on the number of its contents
		projectTable.setFillsViewportHeight(true);
		projectTable.setVisible(true);

		// Adds the table to the panel
		editPanel.add(projectTable);

		// Resizes the panel to enable scrolling based on the number of projects
		editPanel.setPreferredSize(new Dimension(800, projectArray.size() * 50 + 110));

		// Centers the contents in a JTable
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		projectTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		projectTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);

		// Sets the height for all the rows of the JTable
		for (int row = 0; row < projectInfo.length; row++) {

			projectTable.setRowHeight(row, 50);

		}

		// Adds the back button to the start menu
		background.add(backToStartMenuButton);

		// Sets the size of the frame
		editFrame.setSize(600, 720);

		// sets the visibility of the frame to true
		editFrame.setVisible(true);

		// Sets the visibility of the background to true
		background.setVisible(true);

		// Adds the background to the content pane of the frame
		editFrame.getContentPane().add(background);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// This method stores all the images in the materials folder(was used for
	// testing purposes)
	public void fillImages() {

		File path = new File("./materials");
		File[] files = path.listFiles();
		for (int i = 0; i < files.length; i++) {

			// this line makes sure that it is an actual file and not a folder
			if (files[i].isFile()) {

				materialImages.add(i,
						new JButton(new ImageIcon("./materials/" + files[i].getName().split("\\.")[0] + ".jpg")));
				materialName.add(i, new JLabel(files[i].getName().split("\\.")[0]));

				materialImages.get(i).addActionListener(this);
			}

		}

	}

	// This displays all the images in the materials inventory panel
	public void imageScan() {

		// removes all the current images in the panel
		materialsPanel.removeAll();
		materialsPanel.revalidate();
		materialsPanel.repaint();

		// Creates the initial location of the buttons hence tempX and tempY
		int tempX = 40;
		int tempY = 18;

		// Creates a counter for the number of materials in a row
		int counter = 0;

		// Creates the initial height of the panel
		int panelHeight = 1000;

		// Creates a counter for the number of rows
		int rowCounter = 0;

		// System.out.println(materialImages.size()+" yes");
		// Sets the colour of the materials panel
		materialsPanel.setBackground(Color.LIGHT_GRAY);

		// Displays the all the contents in the material Array which contains all the
		// materials
		for (int materialCount = 0; materialCount < materialArray.size(); materialCount++) {

			// Sets the text of the materials to upper case letters
			materialArray.get(materialCount).getNameLabel()
					.setText(materialArray.get(materialCount).getName().toUpperCase());

			// Creates the font for the material names
			materialArray.get(materialCount).getNameLabel().setFont(font2);

			// Sets the visibility of the name labels to true
			materialArray.get(materialCount).getNameLabel().setVisible(true);

			// Resizes and changes the location based on the number of characters in the
			// name
			if (materialArray.get(materialCount).getNameLabel().getText().length() <= 8) {
				materialArray.get(materialCount).getNameLabel().setBounds(tempX + 65, tempY - 21, 200, 20);
			} else {
				materialArray.get(materialCount).getNameLabel().setBounds(tempX + 20, tempY - 21, 200, 20);
			}

			// Adds the name labels to the materials panel
			materialsPanel.add(materialArray.get(materialCount).getNameLabel());

			// Sets the visibility of all the materials to true
			materialArray.get(materialCount).getMaterialButton().setVisible(true);

			// Sets the location of the buttons
			materialArray.get(materialCount).getMaterialButton().setBounds(tempX, tempY, 200, 200);

			// Adds the buttons to the materials panel
			materialsPanel.add(materialArray.get(materialCount).getMaterialButton());

			// Increments the new x location of the button
			tempX += 228;

			// Increments row counter
			rowCounter++;

			// increments the counter which keeps track of the number of materials in a row
			counter++;

			// If the material counter reaches 5, it creates a new row
			if (counter == 5) {

				// Moves the materials down by 240 pixels
				tempY += 240;
				tempX = 40;
				counter = 0;
			}

			// If the number of rows reach 5, it creates a new size for the panel height
			if (rowCounter == 5) {
				panelHeight += 500;
				rowCounter = 0;
				// Sets the new dimension of the panel to enable scrolling
				materialsPanel.setPreferredSize(new Dimension(1200, panelHeight));
				materialsPanel.repaint();
			}

		}

		// Creates the button to add a new material to the database
		addNewMaterialButton.setBounds(tempX, tempY, 200, 200);

		// Adds the button to the materials panel
		materialsPanel.add(addNewMaterialButton);

		// Sets the visibility of the new material button to true
		addNewMaterialButton.setVisible(true);

		// Adds action listener to the button
		addNewMaterialButton.addActionListener(this);
	}

	// This method is run when the user wants to upload a new material
	public void newMaterialImage() {

		// Creates a file explorer to enable the user to find the material
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(null);

		// Stores the path of the file
		File f = chooser.getSelectedFile();
		String fileName = f.getAbsolutePath();
		String[] name = fileName.split("\\\\");

		// This method transfers the file itself from its origin to the materials
		// folder, passes the path of the file and the name of the file
		fileTransfer(fileName, name[name.length - 1]);

	}

	/*
	 * This method is run when the user wants to delete a material from the current
	 * project
	 */
	public void removeMaterial() {
		userMaterialsPanel.setVisible(false);
		// Re-adds all the materials have a material as been removed
		addMaterialToUserPanel();
		userMaterialsPanel.setVisible(true);
		// Repaints the panel
		repaint();
		// Revalidates the panel
		revalidate();

	}

	// This is a temporary place holder for the environmental input screen
	public void environmentalInputGUI() {

		// Sets the font of the heading
		Font font = new Font("Helvetica", Font.BOLD, 38);

		// Creates the heading and sets the font and colour
		JLabel heading = new JLabel("Environmental Input");
		heading.setFont(font);
		heading.setForeground(Color.white);

		// Creates the frame for the environmental input
		environmentalInputFrame = new JFrame();

		// Creates the background for the environmental input panel
		JLabel background = new JLabel(new ImageIcon("./backgrounds/pink background.jpg"));

		// Sets the location and the size of the background
		background.setBounds(0, 0, 1280, 720);
		background.setVisible(true);

		// Creates the button to go to the reports screen
		toReportScreenButton = new JButton();
		toReportScreenButton.setIcon(new ImageIcon("./backgrounds/nextBefore.jpg"));
		toReportScreenButton.addActionListener(this);
		toReportScreenButton.setVisible(true);
		toReportScreenButton.setBounds(1075, 620, 161, 58);

		// adds the button to the background
		background.add(toReportScreenButton);
		heading.setBounds(450, 10, 400, 100);
		background.add(heading);
		heading.setVisible(true);

		// adds the background to the content pane of the frame
		environmentalInputFrame.getContentPane().add(background);

		// Sets the visibility of the frame to true
		environmentalInputFrame.setVisible(true);

		// Sets the size of the frame
		environmentalInputFrame.setSize(1280, 720);

		// refreshes the screen after its contents has been updated
		SwingUtilities.updateComponentTreeUI(environmentalInputFrame);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// If the back to user panel button is clicked, the material inventory frame is
		// hidden and the user material frame is visible
		if (e.getSource() == backToUserPanelButton) {
			System.out.println("TEST reach back to user button");

			userMaterialFrame.setVisible(true);
			materialInventoryFrame.setVisible(false);
		}

		// If the user wants to add a new material, the add new material is frame
		if (e.getSource() == addNewMaterialButton) {

			// Sets the visibility of the material inventory frame to false
			materialInventoryFrame.setVisible(false);

			addNewMaterialPanel();
		}

		// If the back back button on the add material frame is clicked, it is hidden
		// and the material inventory frame is displayed
		if (e.getSource() == backButton) {
			System.out.println("TEST reach baackbutton");
			materialInventoryFrame.setVisible(true);
			newMaterialFrame.setVisible(false);
		}

		// If the user wants to add the image of a material, the new material image
		// method is run which transfers the file to the material folder
		if (e.getSource() == addMaterialImageButton) {
			// Stores the initial size of the material array
			preMaterialIndex = materialArray.size();
			newMaterialImage();
		}

		// If the user adds a material, the text field stores the information the user
		// entered for each field
		if (e.getSource() == newMaterialAddButton) {

			materialArray.get(preMaterialIndex).setName(newMaterialFields[0].getText().toString());
			materialArray.get(preMaterialIndex).setManuLocation(newMaterialFields[1].getText());
			materialArray.get(preMaterialIndex).setHazardous(newMaterialFields[2].getText());
			materialArray.get(preMaterialIndex).setAppearanceOdour(newMaterialFields[3].getText());
			materialArray.get(preMaterialIndex).setHealthHazards(newMaterialFields[4].getText());
			materialArray.get(preMaterialIndex).setExposureRoutes(newMaterialFields[5].getText());
			materialArray.get(preMaterialIndex).setAutoIgniteTemp(newMaterialFields[6].getText());
			materialArray.get(preMaterialIndex).setHandlePrecautions(newMaterialFields[7].getText());
			materialArray.get(preMaterialIndex).setExposureMeasures(newMaterialFields[8].getText());
			materialArray.get(preMaterialIndex).setHyperlink(newMaterialFields[9].getText());
			materialArray.get(preMaterialIndex).setUnit(newMaterialFields[10].getText());

			// Checks if the user entered an integer and if the number between 5 and 1 to
			// prevent errors
			try {

				materialArray.get(preMaterialIndex).setHazardRating(Integer.parseInt(newMaterialFields[11].getText()));
				
				if (Integer.parseInt(newMaterialFields[11].getText()) > 5
						|| Integer.parseInt(newMaterialFields[11].getText()) < 0) {

					// This is used for error handling
					// Creates the button to have the user try again
					tryAgainButton.setVisible(true);
					tryAgainButton.setBounds(550, 650, 150, 150);
					addMaterialBackground.add(tryAgainButton);
					
					// Sets the contents initially frame to invisible
					for (int contentCount = 0; contentCount < 12; contentCount++) {
						newMaterialFields[contentCount].setVisible(false);
						newMaterialLabels[contentCount].setVisible(false);
						
					}
					
					addMaterialImageButton.setVisible(false);
					backButton.setVisible(false);
					newMaterialAddButton.setVisible(false);

					// Sets the panel to the warning label
					errorHandlingPanel = new JLabel(new ImageIcon("./backgrounds/errorHandling2.jpg"));
					errorHandlingPanel.setVisible(true);
					errorHandlingPanel.setBounds(175, 175, 450, 450);
					addMaterialBackground.add(errorHandlingPanel);
					
					// Refreshes the screen
					SwingUtilities.updateComponentTreeUI(newMaterialFrame);
					
				} else {
					
					try {
						
						// Writes the newly added material to the material database if it passed the
						// checks above
						new MaterialFileInput(materialArray.get(preMaterialIndex));

					} catch (IOException e1) {

						// TODO Auto-generated catch block

						e1.printStackTrace();

					}
					
					// Sets the unit label and name label of the image based on the image that has
					// been added
					materialArray.get(preMaterialIndex)
							.setNameLabel(new JLabel(materialArray.get(preMaterialIndex).getName()));
					materialArray.get(preMaterialIndex)
							.setUnitLabel(new JLabel(materialArray.get(preMaterialIndex).getUnit()));
					
					// Refreshes all the materials in the material inventory frame
					imageScan();

					// Sets the material inventory frame's visibility to true and the new material
					// frame's visibility to false
					materialInventoryFrame.setVisible(true);
					newMaterialFrame.setVisible(false);
					
				}
				
			} catch (Exception error) {
				
				// If the user doesn't enter a number, this is run
				tryAgainButton.setVisible(true);
				tryAgainButton.setBounds(550, 650, 150, 150);
				addMaterialBackground.add(tryAgainButton);
				
				// Sets all the initial contents in the frame to invisible
				for (int contentCount = 0; contentCount < 12; contentCount++) {
					newMaterialFields[contentCount].setVisible(false);
					newMaterialLabels[contentCount].setVisible(false);
				}
				addMaterialImageButton.setVisible(false);
				backButton.setVisible(false);
				newMaterialAddButton.setVisible(false);
				
				// Displays the panel which contains the error message
				errorHandlingPanel = new JLabel(new ImageIcon("./backgrounds/errorHandling.jpg"));
				errorHandlingPanel.setVisible(true);
				errorHandlingPanel.setBounds(175, 175, 450, 450);
				addMaterialBackground.add(errorHandlingPanel);
				
				// refreshes the current screen
				SwingUtilities.updateComponentTreeUI(newMaterialFrame);

			}

		}
		
		// If the user clicks on try again, they are greeted with the add material page
		if (e.getSource() == tryAgainButton) {
			System.out.println("reach");
			for (int contentCount = 0; contentCount < 12; contentCount++) {
				newMaterialFields[contentCount].setVisible(true);
				newMaterialLabels[contentCount].setVisible(true);
			}
			addMaterialImageButton.setVisible(true);
			backButton.setVisible(true);
			newMaterialAddButton.setVisible(true);
			tryAgainButton.setVisible(false);
			errorHandlingPanel.setVisible(false);
		}
		
		// If the user wants to add a material to the current project, the materials
		// inventory panel is displayed
		if (e.getSource() == addMaterialToProjectButton) {

			materialInventoryPanel();
			userMaterialFrame.setVisible(false);
		}
		
		// If the next button in the user materials panel is clicked, the environmental
		// input screen is displayed
		if (e.getSource() == toEnvironmentalInputScreenButton) {
			
			// Stores the contents of the quantitys of each material
			for (int materialCount = 0; materialCount < currentProject.getMaterials().size(); materialCount++) {
				currentProject.getMaterials().get(materialCount)
						.setQuantity(currentProject.getMaterials().get(materialCount).getQuantityField().getText());
			}
			
			// Adds the current project back to the project array
			projectArray.get(projectCount).projectClone(currentProject);
			
			// Rewrites the project database
			try {
				UnityApplication.createProjectDatabase(projectArray);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			userMaterialFrame.setVisible(false);
			
			// Runs the environmental input GUI
			environmentalInputGUI();

		}
		
		// If the button to go to the reports screen is clicked, the user is taken to
		// the reports screen which passes in the current project
		if (e.getSource() == toReportScreenButton) {

			new ReportScreen(currentProject);
			
			// Sets the current environmental input frame to invisible
			environmentalInputFrame.setVisible(false);
		}
		
		// If the user clicks the back button in the project list window, they are taken
		// to the start menu
		if (e.getSource() == backToStartMenuButton) {
			status = false;
			editFrame.setVisible(false);
			new UnityApplication();
		}

		// If any of the materials is clicked in the materials panel, it adds the
		// material to the current project
		for (int materialCount = 0; materialCount < materialArray.size(); materialCount++) {
			if (e.getSource() == materialArray.get(materialCount).getMaterialButton()) {

				currentProject.getMaterials().add(new Material());
				
				// Clones the newly added material to the current project
				currentProject.getMaterials().get(currentProject.getMaterials().size() - 1)
						.materialClone(materialArray.get(materialCount));
				
				// Refreshes the user materials panel
				addMaterialToUserPanel();
				
				// Sets the material inventory frame to invisible and displays the user material
				// panel
				materialInventoryFrame.setVisible(false);
				userMaterialFrame.setVisible(true);
			}
		}

		// If an edit project button is clicked, it this sets the current project to the
		// project that has been clicked
		for (int projectCount = 0; projectCount < projectArray.size(); projectCount++) {
			
			if (e.getSource() == projectArray.get(projectCount).getProjectButton()) {
				
				// Stores which index the project belongs to
				this.projectCount = projectCount;
				
				// Creates the new project
				currentProject = new Project();
				
				// clones the project
				currentProject.projectClone(projectArray.get(projectCount));
				
				// Sets the visibility of the edit frame to false
				editFrame.setVisible(false);
				
				// Displays the user materials panel
				userMaterialsPanel();
				
				// Displays all the material labels in the current project
				for (int materialCount = 0; materialCount < projectArray.get(projectCount).getMaterials()
						.size(); materialCount++) {
					
					// Sets the headings for all the material to upper case
					currentProject.getMaterials().get(materialCount).getNameLabel()
							.setText(currentProject.getMaterials().get(materialCount).getName().toUpperCase());

				}
				
				// Refreshes the user materials panel
				addMaterialToUserPanel();
				// break;
			}
		}
		
		if (status) {

			// If the user clicks on the material that belongs to a current project, this
			// removes the material from the project and adds it to the material inventory
			for (int materialCount = 0; materialCount < currentProject.getMaterials().size(); materialCount++) {
				
				if (e.getSource() == currentProject.getMaterials().get(materialCount).getMaterialButton()) {
					
					for (int userMaterialCount = 0; userMaterialCount < currentProject.getMaterials()
							.size(); userMaterialCount++) {
						
						// Stores the current quantities of all the fields to prevent it from resetting
						currentProject.getMaterials().get(userMaterialCount).setQuantity(
								currentProject.getMaterials().get(userMaterialCount).getQuantityField().getText());
					}
					
					// Adds the deleted material to the material array
					materialArray.add(new Material());
					materialArray.get(materialArray.size() - 1)
							.materialClone(currentProject.getMaterials().get(materialCount));
					
					// Removes the material from the project
					currentProject.getMaterials().remove(materialCount);
					
					// Resets the content for the materials panel and the user materials panel
					removeMaterial();
				}
			}
		}
	}

	// This is run if the user wants to create a new project, passes in project
	// array and material array called from the unity application class
	public void newProject(ArrayList<Project> projectArray, ArrayList<Material> materialArray) {

		this.projectArray = projectArray;
		this.materialArray = materialArray;

		for (int i = 0; i < materialArray.size(); i++) {
			
			// Adds action listener to all the materials in the inventory panel
			this.materialArray.get(i).getMaterialButton().addActionListener(this);
		}
		
		// Stores the current position of the project that was newly added
		this.projectCount = projectArray.size() - 1;
		
		// Creates a new project for the current project
		currentProject = new Project();
		
		/// Clones the newly added project
		currentProject.projectClone(projectArray.get(projectCount));
		
		// Runs the user materials panel
		userMaterialsPanel();

		for (int materialCount = 0; materialCount < projectArray.get(projectCount).getMaterials()
				.size(); materialCount++) {
			addMaterialToUserPanel();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * The mouse entered and mouse exited methods are used for grahical purposes. It
	 * changes colour when the mouse hover over the button
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		
		if (e.getSource() == backButton) {
			backButton.setIcon(new ImageIcon("./backgrounds/backAfter.png"));
			repaint();
		}
		
		if (e.getSource() == newMaterialAddButton) {
			newMaterialAddButton.setIcon(new ImageIcon("./backgrounds/addAfter.png"));
			repaint();
		}
		
		if (e.getSource() == addMaterialImageButton) {
			addMaterialImageButton.setIcon(new ImageIcon("./backgrounds/addImageAfter.png"));
			repaint();
		}
		
		if (e.getSource() == toEnvironmentalInputScreenButton) {
			toEnvironmentalInputScreenButton.setIcon(new ImageIcon("./backgrounds/nextAfter.jpg"));
			repaint();
		}
		
		if (e.getSource() == backToUserPanelButton) {
			backToUserPanelButton.setIcon(new ImageIcon("./backgrounds/backAfter2.jpg"));
			repaint();
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
		if (e.getSource() == backButton) {
			backButton.setIcon(new ImageIcon("./backgrounds/back.png"));
			repaint();
		}
		
		if (e.getSource() == newMaterialAddButton) {
			newMaterialAddButton.setIcon(new ImageIcon("./backgrounds/addBefore.png"));
			repaint();
		}
		
		if (e.getSource() == addMaterialImageButton) {
			addMaterialImageButton.setIcon(new ImageIcon("./backgrounds/addImage.png"));
			repaint();
		}
		
		if (e.getSource() == toEnvironmentalInputScreenButton) {
			toEnvironmentalInputScreenButton.setIcon(new ImageIcon("./backgrounds/nextBefore.jpg"));
			repaint();
		}
		
		if (e.getSource() == backToUserPanelButton) {
			backToUserPanelButton.setIcon(new ImageIcon("./backgrounds/backBefore2.jpg"));
			repaint();
		}
	}

	/*
	 * This method transfers the material image from its origin to the materials
	 * folder The pathname of the file and the name of the file name itself is
	 * passed on
	 */
	// Resource:Resource:
	// https://www.mkyong.com/java/how-to-move-file-to-another-directory-in-java/
	public void fileTransfer(String pathName, String fileName) {
		
		// Used to read data
		InputStream inStream = null;
		
		// Used to write data
		OutputStream outStream = null;

		try {
			
			// Original location of the file
			File imageOfOrigin = new File(pathName);
			
			// The final location of the file
			File finalPath = new File("./materials/" + fileName);
			
			// Path name of the final location of the file
			String temp = "./materials/" + fileName;
			
			// Reads the image that is to be moved
			inStream = new FileInputStream(imageOfOrigin);
			
			// Writes the image to the final path of the image
			outStream = new FileOutputStream(finalPath);
			
			/*
			 * Reads the file in chunks instead of the whole file to prevent memory errors
			 */
			byte[] buffer = new byte[1024];

			int length;
			
			// copy the file content in bytes
			while ((length = inStream.read(buffer)) > 0) {

				outStream.write(buffer, 0, length);

			}
			
			// Closes the input and output stream
			inStream.close();
			outStream.close();

			/*
			 * Creates a new material, sets the image of the material, adds action listener,
			 * and creates the button that will be used for the reports screen
			 */
			materialArray.add(new Material());
			materialArray.get(preMaterialIndex).setMaterialButton(new JButton());
			materialArray.get(preMaterialIndex).getMaterialButton().setIcon(new ImageIcon(temp));
			materialArray.get(preMaterialIndex).getMaterialButton().addActionListener(this);
			materialArray.get(preMaterialIndex).setReportMaterialButton((new JButton()));
			materialArray.get(preMaterialIndex).getReportMaterialButton().setIcon(new ImageIcon(temp));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}