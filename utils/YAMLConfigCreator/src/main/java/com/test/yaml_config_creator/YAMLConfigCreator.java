package com.test.yaml_config_creator;

import java.io.File;
import java.util.ArrayList;

import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.test.yaml_config_creator.load_target_classes.BasicSubscriberConfigSettings;
import com.test.yaml_config_creator.load_target_classes.PubNubSubscription;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

public class YAMLConfigCreator {
	private static String YAML_OUTPUT_PATH = "./configs_out/";

	public static void main(String[] args) throws Exception {
		System.out.println("Starting YAMLConfigCreator.");

		// Initialize configuration POJO with desired settings
		Object configPOJOtoExport = configPojoPopulate();

		// Metainfo about source POJO
		String sourceConfigPojo = configPOJOtoExport.getClass().getCanonicalName();
		String sourceConfigPojoSimple = configPOJOtoExport.getClass().getSimpleName();
		System.out.println("Exporting YAML config for " + sourceConfigPojo + ".");
		System.out.println("Current " + sourceConfigPojoSimple + " Config: \n\t\t" + configPOJOtoExport.toString());

		// Attempt to write serialized / YAML version of config object
		String outputFilePath = YAML_OUTPUT_PATH + sourceConfigPojoSimple + ".yaml";
		File writeFile = new File(outputFilePath);
		System.out.println("Target YAML filepath: " + writeFile.getAbsolutePath());
		
		YAMLMapper mapper = new YAMLMapper(new YAMLFactory());
		mapper.writeValue(writeFile, configPOJOtoExport);
		
		System.out.println("Write Complete.  Exiting...");
	}

	/**
	 * Function to create configuration POJO for export.
	 * 
	 * This is what to update when updating configuration source and target objects.
	 * 
	 * @return generic reference to config POJO
	 */
	private static Object configPojoPopulate() {
		ArrayList<PubNubSubscription> pubNubSubscriptions = new ArrayList<>();

		pubNubSubscriptions.add(PubNubSubscription.of("Channel1"));

		var a = PubNubSubscription.of("Channel2");
		a.setCursor("1");
		pubNubSubscriptions.add(a);

		pubNubSubscriptions.add(PubNubSubscription.of("Channel3"));

		BasicSubscriberConfigSettings configPOJOtoExport = new BasicSubscriberConfigSettings("basic_listener_username",
				"subscription_key_text", pubNubSubscriptions);

		return configPOJOtoExport;
	}
}
