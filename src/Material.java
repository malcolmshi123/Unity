import javax.swing.*;

// material object
public class Material {
	
	// fields
	private String name;
	private String manuLocation;
	private String hazardous;
	private String appearanceOdour;
	private String healthHazards;
	private String exposureRoutes;
	private String autoIgniteTemp;
	private String handlePrecautions;
	private String exposureMeasures;
	private String unit;
	private String hyperlink;
	private String imageFile;
	private JButton materialButton;
	private JLabel nameLabel;
	private JTextField quantityField;
	private JLabel unitLabel;
	private JButton reportMaterialButton;
	private String quantity;
	private int hazardRating;
	
	// constructor
	public Material() {
		this.quantity="0";
	}

	// getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManuLocation() {
		return manuLocation;
	}

	public void setManuLocation(String manuLocation) {
		this.manuLocation = manuLocation;
	}

	public String getHazardous() {
		return hazardous;
	}

	public void setHazardous(String hazardous) {
		this.hazardous = hazardous;
	}

	public String getAppearanceOdour() {
		return appearanceOdour;
	}

	public void setAppearanceOdour(String appearanceOdour) {
		this.appearanceOdour = appearanceOdour;
	}

	public String getHealthHazards() {
		return healthHazards;
	}

	public void setHealthHazards(String healthHazards) {
		this.healthHazards = healthHazards;
	}

	public String getExposureRoutes() {
		return exposureRoutes;
	}

	public void setExposureRoutes(String exposureRoutes) {
		this.exposureRoutes = exposureRoutes;
	}

	public String getAutoIgniteTemp() {
		return autoIgniteTemp;
	}

	public void setAutoIgniteTemp(String autoIgniteTemp) {
		this.autoIgniteTemp = autoIgniteTemp;
	}

	public String getHandlePrecautions() {
		return handlePrecautions;
	}

	public void setHandlePrecautions(String handlePrecautions) {
		this.handlePrecautions = handlePrecautions;
	}

	public String getExposureMeasures() {
		return exposureMeasures;
	}

	public void setExposureMeasures(String exposureMeasures) {
		this.exposureMeasures = exposureMeasures;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getHyperlink() {
		return hyperlink;
	}

	public void setHyperlink(String hyperlink) {
		this.hyperlink = hyperlink;
	}

	public String getImageFile() {
		return imageFile;
	}

	public void setImageFile(String imageFile) {
		this.imageFile = imageFile;
	}
	
	public JTextField getQuantityField() {
		return quantityField;
	}

	public void setQuantityField(JTextField quantityField) {
		this.quantityField = quantityField;
	}
	
	public JButton getMaterialButton() {
		return materialButton;
	}

	public void setMaterialButton(JButton materialButton) {
		this.materialButton = materialButton;
	}
	
	public JLabel getNameLabel() {
		return nameLabel;
	}

	public void setNameLabel(JLabel nameLabel) {
		this.nameLabel = nameLabel;
	}
	
	public JLabel getUnitLabel() {
		return unitLabel;
	}

	public void setUnitLabel(JLabel unitLabel) {
		this.unitLabel = unitLabel;
	}
	
	public JButton getReportMaterialButton() {
		return reportMaterialButton;
	}

	public void setReportMaterialButton(JButton reportMaterialButton) {
		this.reportMaterialButton = reportMaterialButton;
	}
	
	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	public int getHazardRating() {
		return hazardRating;
	}

	public void setHazardRating(int hazardRating) {
		this.hazardRating = hazardRating;
	}

	// toString method
	@Override
	public String toString() {
		return name + "," + manuLocation + "," + hazardous + "," + appearanceOdour + "," + healthHazards + ","
				+ exposureRoutes + "," + autoIgniteTemp + "," + handlePrecautions + "," + exposureMeasures + ","+unit+"," +hazardRating+","
			+ hyperlink;
	}

	public void materialClone(Material material) {
		  this.name=material.getName();
		  this.manuLocation=material.getManuLocation();
		  this.hazardous=material.getHazardous();
		  this.appearanceOdour=material.getAppearanceOdour();
		  this.healthHazards=material.getHealthHazards();
		  this.exposureRoutes=material.getExposureRoutes();
		  this.autoIgniteTemp=material.getAutoIgniteTemp();
		  this.handlePrecautions=material.getHandlePrecautions();
		  this.exposureMeasures=material.getExposureMeasures();
		  this.unit=material.getUnit();
		  this.hyperlink=material.getHyperlink();
		  this.imageFile=material.getImageFile();
		  this.materialButton=material.getMaterialButton();
		  this.nameLabel=material.getNameLabel();
		  this.quantityField=material.getQuantityField();
		  this.unitLabel=material.getUnitLabel();
		  this.reportMaterialButton=material.getReportMaterialButton();
		  this.quantity=material.getQuantity();
		  this.hazardRating=material.getHazardRating();
	}
	
	//Used to display information in the reports screen
	public String reportDisplay() {
		
		return "<html>Material Name: " + name  +"<br><br> Quantity: "+quantity+unit+"<br><br> Manufacturing Location: " + manuLocation + "<br><br> Hazardous Materials: " + hazardous
				+ "<br><br> Appearance and Odour: " + appearanceOdour + "<br><br> Health Hazards: " + healthHazards + "<br><br> Exposure Routes: "
				+ exposureRoutes + "<br><br> Auto Ignition Temperature: " + autoIgniteTemp + "<br><br> Handling Temperature: " + handlePrecautions
				+ "<br><br> Exposure Measures: " + exposureMeasures + "</html>";
	}
	
	//Uses to save information to a text file
	public String saveReport() {
		
		return "Material Name: " + name + "\nQuantity: "+quantity+unit+"\n Manufacturing Location: " + manuLocation + "\n Hazardous Materials: " + hazardous
				+ "\n Appearance and Odour: " + appearanceOdour + "\n Health Hazards: " + healthHazards + "\n Exposure Routes: "
				+ exposureRoutes + "\n Auto Ignition Temperature: " + autoIgniteTemp + "\n Handling Temperature: " + handlePrecautions
				+ "\n Exposure Measures: " + exposureMeasures +"\nLink To Source: "+hyperlink+"\n\n";
	}
	
}