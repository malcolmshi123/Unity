import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

// File input - sets variables based on information from csv file
public class MaterialFileInput {
	
	// Constructor
	public MaterialFileInput(ArrayList<Material> materialArray) {
		
		//
		try {
			
			Scanner input = new Scanner(new File("material.csv")); // file input
			input.useDelimiter(","); // use comma to separate different pieces of info
			
			input.nextLine(); // skip first line
			
			int i = 0;
			
			// set variables for each material
			while(input.hasNextLine()) {
				
				// new material for each index in materialArray
				materialArray.add(new Material());
				
				// removes text formatting for all the material names
				String temp = input.next().replaceAll("\\r\\n|\\r|\\n|  ", "");
				char[]temp2 = temp.toCharArray();
			
				if(temp2[0] == ' ') {
					StringBuilder sb = new StringBuilder(temp);
					sb.deleteCharAt(0);
					temp = sb.toString();
				}
				if(temp2[temp2.length-1] == ' ') {
					StringBuilder sb = new StringBuilder(temp);
					sb.deleteCharAt(temp2.length-1);
					temp = sb.toString();
				}
				
				// set material fields
				materialArray.get(i).setName(temp);
				materialArray.get(i).setManuLocation(input.next());
				materialArray.get(i).setHazardous(input.next());
				materialArray.get(i).setAppearanceOdour(input.next());
				materialArray.get(i).setHealthHazards(input.next());
				materialArray.get(i).setExposureRoutes(input.next());
				materialArray.get(i).setAutoIgniteTemp(input.next());
				materialArray.get(i).setHandlePrecautions(input.next());
				materialArray.get(i).setExposureMeasures(input.next());
				materialArray.get(i).setUnit(input.next());
				materialArray.get(i).setUnitLabel(new JLabel(materialArray.get(i).getUnit()));
				materialArray.get(i).setHazardRating(Integer.parseInt(input.next()));
				materialArray.get(i).setHyperlink(input.next());
				materialArray.get(i).setMaterialButton(new JButton());
				materialArray.get(i).getMaterialButton().setIcon(new ImageIcon("./materials/" + 
						materialArray.get(i).getName()+".jpg"));
				materialArray.get(i).setReportMaterialButton(new JButton());
				materialArray.get(i).getReportMaterialButton().setIcon(new ImageIcon("./materials/" + 
						materialArray.get(i).getName()+".jpg"));
				materialArray.get(i).setNameLabel(new JLabel(materialArray.get(i).getName()));	
				System.out.println(	materialArray.get(i).toString());
				
				i++;
				
			}
			
			input.close(); // close file
			
		} catch(FileNotFoundException error) {
			System.out.println("Invalid file!"); // file error
		}

	}
	
	// add new material to csv
	public MaterialFileInput(Material newMaterial) throws IOException {

		// extract csv file as string
		FileWriter output = new FileWriter(new File("material.csv"), true);
		
		// add new material string to csv string
		output.append(",\n" + newMaterial.toString());

		// close fileWriter
		output.close();
		
	}
}