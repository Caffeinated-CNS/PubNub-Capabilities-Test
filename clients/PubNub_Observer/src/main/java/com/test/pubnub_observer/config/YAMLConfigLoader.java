package com.test.pubnub_observer.config;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLParser;

public class YAMLConfigLoader {
	private YAMLConfigLoader() {
	}

	public static <T> T loadBasicYAMLConfig(String configPath, Class<T> valueType) throws IOException {
		YAMLFactory yamlFactory = new YAMLFactory();
		File configFile = new File(configPath);

		System.out.println("Attempting to load YAML config: " + configFile.getAbsolutePath());

		YAMLParser yamlSource = yamlFactory.createParser(configFile);
		YAMLMapper mapper = new YAMLMapper();
		
		return mapper.readValue(yamlSource, valueType);
	}
}
