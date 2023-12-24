package common;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonUtils {

	public boolean checkErrorMessage(String responseBody, String expectedMessage) {
		String[] splittedResponseBody = responseBody.split("\"");
		  boolean isChecked = false;
		  for (int i =0; i<splittedResponseBody.length;i++) {
			  if(splittedResponseBody[i].equals(expectedMessage)) {
				  isChecked=true;
			  }
		  }
		  return isChecked;		
	}
	
	//Get value from Json body by key
	public String getValueByKey(String responseBody, String key) {
		 JSONParser parser = new JSONParser();
		 String value ="";
		  try {
		  JSONObject responseBodyObj = (JSONObject) parser.parse(responseBody);
		  value = responseBodyObj.get(key).toString();
		  } catch (Exception e) {
			  System.out.println("Response body is null.");
			  e.printStackTrace();
		  }
		 return value;		
	}
	
	public void copyJsonFile(File sourceFile, File destinationFile) {
		 if(destinationFile.exists()) {
			  destinationFile.delete();
		  }
		  try {
		  Files.copy(sourceFile.toPath(), destinationFile.toPath());
		  System.out.println("Copy successfully");
		  } catch(Exception e) {
			  System.out.println("Json request body is not found");
		  }
		  
	}
	
	//Pass value by fieldName
	public String changeValueByFieldName(File file, String fieldName, String value) {
		String resultFile = null;
		String filePath = file.getAbsolutePath();
		Path path = Paths.get(filePath);

		try {
		String originalFile = new String (Files.readAllBytes(path));
		
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = (JSONObject) parser.parse(originalFile);
		if (value.equals("missing")) {
			jsonObject.remove(fieldName);
		} else if(value.equals("null")) {
			jsonObject.put(fieldName, null);
		} else if(value.equals("true")) {
			jsonObject.put(fieldName, true);
		} else if (value.equals("\"\"" )) {
			jsonObject.put(fieldName, "");
		} else if (isNumeric(value)) {
			jsonObject.put(fieldName, Double.parseDouble(value));
		}
		else {
		jsonObject.put(fieldName, value);
		}
		resultFile=jsonObject.toJSONString();
		
		} catch (Exception e) {
			System.out.println("File not found");
		}
		
		return resultFile;
		
	}
	
	public String readJsonFile(String filePath) {
		String fileContent = "";
		Path path = Paths.get(filePath);
		try {
			fileContent = new String ((Files.readAllBytes(path)));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fileContent;
	}
	
	public static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
}
