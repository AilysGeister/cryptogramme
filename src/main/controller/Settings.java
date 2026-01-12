package main.controller;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Settings {
	private static final Path SETTINGS_FILE = getUserSettingsPath();
	
	/**
	 * Get the default user settings path in function of the user's OS.
	 * @return
	 */
	private static Path getUserSettingsPath() {
        String os = getOS();

        if (os.equals("win")) {
        	return Path.of(System.getProperty("user.home"), "cryptogramme", "settings.json");
        } else if (os.equals("mac")) {
            return Path.of(System.getProperty("user.home"), "Library/Application Support/cryptogramme/settings.json");
        } else {
            return Path.of(System.getProperty("user.home"), ".cryptogramme/settings.json");
        }
    }
	
	/**
	 * Initiate the defaults parameters at the first launch.
	 * @throws IOException
	 */
	public static void initialParameters() throws IOException {
		//Initialization:
		String OS = getOS();
		String exportsPath, keysPath;
		
		//Creat the parent folder:
	    Files.createDirectories(SETTINGS_FILE.getParent());

	    //Initialize defaults settings if they don't exists:
	    if (!Files.exists(SETTINGS_FILE)) {
	    	//Defaults directories:
		    switch (OS) {
		        case "win":
		        	exportsPath = Path.of(System.getProperty("user.home"), "cryptogramme", "exports").toString() + System.getProperty("file.separator");
		            keysPath = Path.of(System.getProperty("user.home"), "cryptogramme", "keys").toString() + System.getProperty("file.separator");
		            break;
		        case "linux":
		            exportsPath = Path.of(System.getProperty("user.home"), ".cryptogramme/exports").toString();
		            keysPath = Path.of(System.getProperty("user.home"), ".cryptogramme/keys").toString();
		            break;
		        case "mac":
		            exportsPath = Path.of(System.getProperty("user.home"), "Library/Application Support/cryptogramme/exports").toString();
		            keysPath = Path.of(System.getProperty("user.home"), "Library/Application Support/cryptogramme/keys").toString();
		            break;
		        default:
		            exportsPath = "cryptogramme/exports";
		            keysPath = "cryptogramme/keys";
		            break;
		    }

		    //Creat the /exports and /keys folders:
		    Files.createDirectories(Path.of(exportsPath));
		    Files.createDirectories(Path.of(keysPath));

		    //Creat the settings.json file:
		    JSONObject json = new JSONObject();
		    json.put("initialized", true);
		    json.put("lang", "en");
		    json.put("export_directory", exportsPath);
		    json.put("keys_directory", keysPath);

		    Files.writeString(SETTINGS_FILE, json.toString(4), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
	    }   
    }

	
	/**
	 * Method that allow user to change the settings of the application. And save it into a json file.
	 * @param language
	 * @param displayMode
	 * @throws IOException 
	 */
	public static void updateSettings(boolean initialized, String language, String exportsDirectory, String keysDirectory) throws IOException {
		//Make sure that the parents folder exists:
	    Files.createDirectories(SETTINGS_FILE.getParent());

	    //Creat the JSON attributes:
	    JSONObject json = new JSONObject();
	    json.put("initialized", initialized);
	    json.put("lang", language);
	    json.put("export_directory", exportsDirectory);
	    json.put("keys_directory", keysDirectory);

	    //Write the json file:
	    Files.writeString(SETTINGS_FILE, json.toString(4), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);

	    //Create directories if they don't exist:
	    Path exportsPath = Path.of(exportsDirectory);
	    Path keysPath = Path.of(keysDirectory);

	    if (Files.notExists(exportsPath)) {
	        Files.createDirectories(exportsPath);
	    }
	    if (Files.notExists(keysPath)) {
	        Files.createDirectories(keysPath);
	    }
	}

	
	/**
	 * Return the String value of a saved parameter.
	 * @param parameter The parameter we want to get the value.
	 * @return
	 * @throws IOException
	 */
	public static String getStrParameter(String parameter) throws IOException {
		//Initialization:
		String content = Files.readString(SETTINGS_FILE);
		JSONObject json = new JSONObject(content);
		
		
		//Returning the language:
		return json.getString(parameter);
	}
	
	/**
	 * Return the boolean value of a saved parameter.
	 * @param parameter The parameter we want to get the value.
	 * @return
	 * @throws IOException
	 */
	public static boolean getBoolParameter(String parameter) throws IOException {
		//Initialization:
		String content = Files.readString(SETTINGS_FILE);
		JSONObject json = new JSONObject(content);
		
		
		//Returning the language:
		return json.getBoolean(parameter);
	}
	
	/**
	 * Get the OS on which the program is running.
	 * @return
	 */
	private static String getOS() {
	    String os = System.getProperty("os.name", "").toLowerCase().trim();

	    if (os.startsWith("windows")) return "win";
	    else if (os.contains("nux") || os.contains("nix")) return "linux";
	    else if (os.contains("mac")) return "mac";
	    else return "unidentified";
	}
}
