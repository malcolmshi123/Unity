import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/* Malcolm, Saenthan, Richard, Steven
 * June 16, 2020
 * 
 * The Unity Application is a program that allows aspiring artists to organize a variety of materials to create projects,
 * while also being aware of the impact each material has on the environment.
 * 
 * Basic features:
 * - Create a new project
 * - Materials Screen (chooses materials)
 * - Databases (stores information of materials and projects)
 * - Report Screen (lists details of selected project and its materials, as well as environmental impact
 * 
 * Extra features:
 * - Detailed instructions pages with images
 * - Edit an existing project
 * - Project date created and edited fields based on real-time date
 * - Add a new material to material database (including its image)
 * - Use of API (Apache POI) to store project database in excel sheet
 * - If a material is selected from the database, it is removed from it and moves to the user materials screen
 * - If a material is deleted from the user materials screen, it moves to the database
 * - Navigate between material information on report screen by clicking material image button scroll panel
 * - MSDS sheet hyper link for each material on report screen
 * - Save report screen information in a text file
 * 
 * Areas of concern:
 * - Lack of error checking throughout program
 * - Environmental input screen incomplete
 */

// runs the main method of Unity project and splash screen components
public class UnityApplication extends JFrame implements ActionListener {

	// initialize material array
	public static ArrayList<Material> materialArray = new ArrayList<Material>();

	// initialize project array
	public static ArrayList<Project> projectArray = new ArrayList<Project>();
	
	// GUI components
	private static JButton createNewProjectButton, editProjectButton, continueToUserPanelButton, backToStartMenuButton,
			instructionsButton;
	private static JTextField projectNameField, userNameField;
	private static JFrame frame;
	private static JLabel background, projectInfoLabel, projectNameLabel, userNameLabel, heading;
	private static JButton[] nextButton = new JButton[3];

	// main method
	public static void main(String[] args) throws IOException {

		// input materials from csv
		new MaterialFileInput(materialArray);
		
		// create start menu
		new UnityApplication();

		/*
		 * If user clicks on create button, checks if project file already exists if it
		 * does, create a new project and go to material gui, if not create project
		 * database If user clicks edit projects, read everything from the project file
		 */
		
		File tempFile = new File("Project Database.xlsx");
		boolean exists = tempFile.exists();

		if (exists) {
			readProjectDatabase();
		} else {
			createProjectDatabase(projectArray);
		}

	}


	// read materials from csv if called
	public void materialRead() {
		new MaterialFileInput(materialArray);
	}

	//This creates the start menu for the application
	public UnityApplication() {
		
		// Creates the next buttons for the instructions
		for (int buttonCount = 0; buttonCount < 3; buttonCount++) {
			nextButton[buttonCount] = new JButton();
			nextButton[buttonCount].setIcon(new ImageIcon("./backgrounds/nextBefore.jpg"));
			nextButton[buttonCount].setBounds(1075, 620, 161, 58);
			nextButton[buttonCount].addActionListener(this);

		}
		
		// Creates a new JButton to display the instructions
		instructionsButton = new JButton();
		
		// Sets the icon for the button
		instructionsButton.setIcon(new ImageIcon("./backgrounds/instructionsButton.png"));
		
		// Creates the heading for the panel as well as its position and sizing
		heading = new JLabel(new ImageIcon("./backgrounds/unityHeading.png"));
		heading.setBounds(180, 10, 450, 100);
		
		// Creates the frame for the start menu
		frame = new JFrame();
		
		// Sets the background for the frame
		background = new JLabel(new ImageIcon("./backgrounds/pink background.jpg"));
		
		// sets the sizing and positioning of the background
		background.setBounds(0, 0, 1280, 720);
		/*
		 * Creates the button to create a new project Sets the icon of the button, its
		 * sizing and positioning Adds actionlistener to the button Adds the button to
		 * the background
		 */
		createNewProjectButton = new JButton();
		createNewProjectButton.setIcon(new ImageIcon("./backgrounds/createProjectLabel.png"));
		createNewProjectButton.setBounds(245, 200, 300, 100);
		createNewProjectButton.setVisible(true);
		background.add(createNewProjectButton);
		createNewProjectButton.addActionListener(this);
		
		/*
		 * Creates the button to create edit a project Sets the icon of the button, its
		 * sizing and positioning Adds actionlistener to the button Adds the button to
		 * the background
		 */

		editProjectButton = new JButton();
		editProjectButton.setIcon(new ImageIcon("./backgrounds/editProjectLabel.png"));

		editProjectButton.setBounds(245, 350, 300, 100);
		editProjectButton.setVisible(true);
		background.add(editProjectButton);
		editProjectButton.addActionListener(this);
		instructionsButton.setBounds(245, 500, 300, 100);

		instructionsButton.setVisible(true);
		background.add(instructionsButton);
		instructionsButton.addActionListener(this);

		heading.setVisible(true);
		background.add(heading);

		// Sets the size of the frame, adds the background to the content pane of the
		// frame
		frame.setSize(800, 720);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(background);
		background.setVisible(true);

	}
	
	// create project excel sheet
	public static void createProjectDatabase(ArrayList<Project> projectArrayBackup) throws IOException {

		String[] columns = { "Project Name", "User Name", "Create Date", "Edit Date", "Materials" };

		// Create a Workbook
		Workbook workbook = new XSSFWorkbook();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("Project");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setColor(IndexedColors.RED.getIndex());

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		// Create a Row
		Row headerRow = sheet.createRow(0);

		// Create cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
		}

		// Create Other rows and cells with employees data
		int rowNum = 1;
		for (Project project : projectArrayBackup) {
			Row row = sheet.createRow(rowNum++);

			row.createCell(0).setCellValue(project.getProjectName());

			row.createCell(1).setCellValue(project.getUserName());

			row.createCell(2).setCellValue(project.getDateCreated());

			row.createCell(3).setCellValue(project.getDateEdited());

			// row.createCell(4).setCellValue(materialArray.get(0).getName()); // will
			// change later

			for (int index = 0; index < project.getMaterials().size(); index++)

				row.createCell(4).setCellValue(project.materialsToString());

		}

		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream("Project Database.xlsx");
		workbook.write(fileOut);
		fileOut.close();

		// Closing the workbook
		workbook.close();
		projectArray = projectArrayBackup;
	}

	// read projects from excel sheet
	private static void readProjectDatabase() {

		try {

			// input existing file content
			FileInputStream fileIn = new FileInputStream("Project Database.xlsx");
			Workbook workbook = new XSSFWorkbook(fileIn);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.rowIterator();

			// skip first row
			iterator.next();

			// iterate through each cell of sheet and read values
			int rowNum = 1;
			while (iterator.hasNext()) {

				Row currentRow = iterator.next();

				projectArray.add(new Project());
				projectArray.get(rowNum - 1).setProjectName(currentRow.getCell(0).getStringCellValue());
				projectArray.get(rowNum - 1).setUserName(currentRow.getCell(1).getStringCellValue());
				projectArray.get(rowNum - 1).setDateCreated(currentRow.getCell(2).getStringCellValue());
				projectArray.get(rowNum - 1).setDateEdited(currentRow.getCell(3).getStringCellValue());

				// split user's material array into string array, indexes separated by commas
				String[] userMaterialArray = currentRow.getCell(4).getStringCellValue().split(",");
				
				// check if material exists in database
				for (int x = 0; x < userMaterialArray.length; x++) {
					// Stores the material name and its quantity
					String[] temp = userMaterialArray[x].split("-");
					for (int y = 0; y < materialArray.size(); y++) {
						if (temp[0].equals(materialArray.get(y).getName())) {

							// add materials to project
							projectArray.get(rowNum - 1).getMaterials().add(materialArray.get(y));
							// Sets the quantity of the material
							projectArray.get(rowNum - 1).getMaterials()
									.get(projectArray.get(rowNum - 1).getMaterials().size() - 1).setQuantity(temp[1]);
						}
					}
				}

				rowNum++;

			}

			workbook.close(); // closes workbook

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	//Creates the panel for the user to enter information about the project
	public void projectInfoPanel() {
		
		/*
		 * Creates a back button to go back to the start menu Sets its position and size
		 * Adds action listener Adds it to the background
		 */
		backToStartMenuButton = new JButton("Back");
		backToStartMenuButton.addActionListener(this);
		backToStartMenuButton.setBounds(100, 600, 200, 50);
		backToStartMenuButton.setVisible(true);
		background.add(backToStartMenuButton);
		
		/*
		 * Creates a continue to user panel button to go back to the start menu Sets its
		 * position and size Adds action listener Adds it to the background
		 */
		continueToUserPanelButton = new JButton("Click to continue");
		continueToUserPanelButton.setBounds(550, 600, 200, 50);
		continueToUserPanelButton.setVisible(true);
		background.add(continueToUserPanelButton);
		continueToUserPanelButton.addActionListener(this);
		
		// Creates the heading for the frame, its positioning and sizing.
		projectInfoLabel = new JLabel(new ImageIcon("./backgrounds/projectInfo.png"));
		projectInfoLabel.setBounds(30, 0, 700, 150);
		projectInfoLabel.setVisible(true);
		background.add(projectInfoLabel);
		
		// Creates the label for project name, its positioning and sizing.
		projectNameLabel = new JLabel(new ImageIcon("./backgrounds/projectNameLabel.png"));
		projectNameLabel.setBounds(280, 200, 200, 50);
		projectNameLabel.setVisible(true);
		background.add(projectNameLabel);
		
		// Creates the label for user name name, its positioning and sizing.
		userNameLabel = new JLabel(new ImageIcon("./backgrounds/userNameLabel.png"));
		userNameLabel.setBounds(280, 400, 200, 50);
		userNameLabel.setVisible(true);
		background.add(userNameLabel);
		
		/*
		 * Creates the fields to have the user enter information about the project This
		 * includes the project name, and the users name
		 */
		projectNameField = new JTextField("Enter Project Name");
		projectNameField.setBounds(280, 250, 200, 50);
		projectNameField.setHorizontalAlignment(JTextField.CENTER);
		projectNameField.setVisible(true);

		userNameField = new JTextField("Enter your name");
		userNameField.setBounds(280, 450, 200, 50);
		userNameField.setHorizontalAlignment(JTextField.CENTER);
		
		// Adds the text fields to the background
		background.add(userNameField);
		background.add(projectNameField);
		
		// Sets the size of the frame
		frame.setSize(800, 720);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		// If the create new project button is clicked, the project info panel is
		// displayed and the initial content is hidden
		if (e.getSource() == createNewProjectButton) {
			frame.setSize(1280, 720);

			heading.setVisible(false);
			instructionsButton.setVisible(false);
			createNewProjectButton.setVisible(false);
			editProjectButton.setVisible(false);
			projectInfoPanel();
		}
		
		/*
		 * If the continue to user panel button is clicked in the project info panel
		 * this creates a new project and runs the material GUI panel
		 */
		if (e.getSource() == continueToUserPanelButton) {
			
			// Stores the date of the current day
			String currentDate = java.time.LocalDate.now().toString();
			
			// Creates the project and adds it to the project Array, sets the current date
			projectArray.add(new Project(projectNameField.getText(), userNameField.getText(), currentDate, currentDate,
					new ArrayList<Material>()));
			
			// Writes to the project database
			try {
				createProjectDatabase(projectArray);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			// Runs the material GUI, set to false because the user is creating a project
			frame.setVisible(false);
			new MaterialsGUI(projectArray, materialArray, false);
		}
		
		// If the back to startmenu button is clicked, this reruns the application
		if (e.getSource() == backToStartMenuButton) {
			frame.setVisible(false);
			new UnityApplication();
		}
		
		// If the edit project button is clicked it sets the current frame visibility to
		// false and runs the materials gui
		if (e.getSource() == editProjectButton) {

			frame.setVisible(false);
			// It is set to true because the user is creating a project
			new MaterialsGUI(projectArray, materialArray, true);

		}
		
		// If the instructions button is clicked, the instructions for the application
		// is displayed
		if (e.getSource() == instructionsButton) {
			
			// Removes all the contents currently in the frame
			heading.setVisible(false);
			instructionsButton.setVisible(false);
			createNewProjectButton.setVisible(false);
			editProjectButton.setVisible(false);
			removeAll();
			frame.remove(background);
			
			// Adds the instructions to the frame
			background = new JLabel(new ImageIcon("./backgrounds/instructions1.png"));
			
			// Sets the size and location of the instructions
			background.setBounds(0, 0, 1280, 740);
			
			// Refreshes the frame
			SwingUtilities.updateComponentTreeUI(frame);
			
			// Adds the next button to the frame
			background.add(nextButton[0]);
			nextButton[0].setVisible(true);
			
			// Adds the background to the frame
			frame.add(background);
			
			// Sets the size of the frame
			frame.setSize(1280, 740);

		}
		
		// If the first next button is clicked, the second instructions is displayed
		if (e.getSource() == nextButton[0]) {
			nextButton[0].setVisible(false);
			frame.remove(background);
			background = new JLabel(new ImageIcon("./backgrounds/instructions2.jpg"));
			background.setBounds(0, 0, 1280, 740);
			SwingUtilities.updateComponentTreeUI(frame);
			background.add(nextButton[1]);
			nextButton[1].setVisible(true);
			frame.add(background);
		}
		
		// If the second next button is clicked, the third instructions is displayed
		if (e.getSource() == nextButton[1]) {
			nextButton[1].setVisible(false);
			nextButton[2].setBounds(1099, 638, 150, 55);

			frame.remove(background);
			background = new JLabel(new ImageIcon("./backgrounds/instructions3.jpg"));
			background.setBounds(0, 0, 1280, 740);
			SwingUtilities.updateComponentTreeUI(frame);
			background.add(nextButton[2]);
			nextButton[2].setVisible(true);
			frame.add(background);
		}
		
		// If the third next button is clicked, it takes the user back to the main menu
		if (e.getSource() == nextButton[2]) {
			frame.setVisible(false);
			frame.removeAll();
			new UnityApplication();
		}
	}

}
