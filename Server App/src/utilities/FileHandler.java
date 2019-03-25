package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import configuration.Configuration;

public class FileHandler {

	public static void readConfig(String fileName) {
		
		Configuration.getConfiguration().clearAllConf();

		File file = new File(fileName) ;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file)) ;
			String line = "";
			while((line = reader.readLine()) != null) {
				String[] str = line.split("=");
				String key = str[0] ;
				String value = str[1] ;
				switch (key) {
				case "RW.server":
					key = "server-address" ;
					break;
				case "RW.server.port":
					key = "server-port" ;
					break;
				case "RW.numberOfReaders":
					key = "number-readers" ;
					break;
				case "RW.numberOfWriters":
					key = "number-writers" ;
					break;
				case "RW.numberOfAccesses":
					key = "client-access" ;
					break;
				case "RW.stubName":
					key = "stub-name" ;
					break;
				case "RW.strategyType":
					key = "type" ;
					break;
				default:
					key = key.split(".")[1] ;
					if(key.contains("reader")) {
						String id = key.split("reader")[0] ;
						key = "client-address-" + id ;
						String key_type = "client-type-" + id ;
						Configuration.getConfiguration().addConf(key_type, "read");
					}else {
						String id = key.split("writer")[0] ;
						key = "client-address-" + id ;
						String key_type = "client-type-" + id ;
						Configuration.getConfiguration().addConf(key_type, "write");
					}
					break;
				}
				Configuration.getConfiguration().addConf(key, value);
			}
			int readersCount =  Integer.valueOf(Configuration.getConfiguration().getConf("number-readers")) ;
			int writersCount =  Integer.valueOf(Configuration.getConfiguration().getConf("number-writers"));
			Configuration.getConfiguration().addConf("client-count", String.valueOf(readersCount + writersCount));

			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
