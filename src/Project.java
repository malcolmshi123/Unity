import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

// project object
public class Project {
	
	// fields
	private String projectName;
	private String userName;
	private String dateCreated; // change to actual date later
	private String dateEdited; // change to actual date later
	private ArrayList<Material> materials = new ArrayList<Material>();
	private JButton projectButton;
	private JLabel projectNameLabel;
	
	// constructors
	public Project() {
		this.projectButton=new JButton("edit");
	}
	
	public Project(String projectName, String userName, String createDate, String editDate, ArrayList<Material> materials) {
		super();
		this.projectName = projectName;
		this.userName = userName;
		this.dateCreated = createDate;
		this.dateEdited = editDate;
		this.materials = materials;
		this.projectButton=new JButton("edit");
		this.projectNameLabel=new JLabel(projectName);
	}
	
	// getters and setters
	public String getDateCreated() {
		return dateCreated;
	}
	
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public String getDateEdited() {
		return dateEdited;
	}
	
	public void setDateEdited(String dateEdited) {
		this.dateEdited = dateEdited;
	}

	public JLabel getProjectNameLabel() {
		return projectNameLabel;
	}

	public void setProjectNameLabel(JLabel projectNameLabel) {
		this.projectNameLabel = projectNameLabel;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public ArrayList<Material> getMaterials() {
		return materials;
	}

	public void setMaterials(ArrayList<Material> materials) {
		this.materials = materials;
		for(int materialCount=0;materialCount<this.materials.size();materialCount++) {
			this.materials.get(materialCount).setQuantityField(new JTextField(this.getMaterials().get(materialCount).getQuantity()));
		}
	}

	public JButton getProjectButton() {
		return projectButton;
	}

	public void setProjectButton(JButton projectButton) {
		this.projectButton = projectButton;
	}
	
	// clone project - similar to constructor
	public void projectClone(Project projectToBeCloned){
		this.projectName = projectToBeCloned.getProjectName();
		this.userName = projectToBeCloned.getUserName();
		this.dateCreated = projectToBeCloned.getDateCreated();
		this.dateEdited = projectToBeCloned.getDateEdited();
		this.projectButton=projectToBeCloned.getProjectButton();
		this.projectNameLabel=projectToBeCloned.getProjectNameLabel();

	}
	
	// toString method
	@Override
	public String toString() {
		return "Project [projectName=" + projectName + ", userName=" + userName + ", createDate=" + dateCreated
				+ ", editDate=" + dateEdited + ", materials=" + materials + ", projectButton=" + projectButton + "]";
	}
	
	// convert material names to string
	 public String materialsToString() {

		 String materials = "";

		 for(int index = 0; index < this.getMaterials().size(); index++) {
		 materials += getMaterials().get(index).getName()+"-"+getMaterials().get(index).getQuantity();

		 if(index != this.getMaterials().size() - 1)
		 materials += ",";
		 }

		 return materials;
		 
	 }


}