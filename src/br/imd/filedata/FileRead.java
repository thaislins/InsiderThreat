package br.imd.filedata;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import br.imd.profile.ProfileBuilder;

/**
 * Classe responsável pelo leitura dos arquivos
 */
public class FileRead {

	private HashMap<String, ArrayList<Data>> deviceInfo;
	private ArrayList<String> fileInfo;
	private Data data;
	private String csvFile;
	private BufferedReader reader;
	private String line;
	private String csvSplitBy;
	static final String DEVICE_FILE = System.getProperty("user.dir") + "/files/device-reduced.csv";
	static final String HTTP_FILE = System.getProperty("user.dir") + "/files/http-reduced.csv";
	static final String LDAP_FILE = System.getProperty("user.dir") + "/files/ldap.csv";
	static final String LOGON_FILE = System.getProperty("user.dir") + "/files/logon-reduced.csv";

	/**
	 * @return deviceInfo
	 */
	public HashMap<String, ArrayList<Data>> getDeviceInfo() {
		return deviceInfo;
	}

	/**
	 * @param deviceInfo
	 */
	public void setDeviceInfo(HashMap<String, ArrayList<Data>> deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	/**
	 * @return data
	 */
	public Data getData() {
		return data;
	}

	/**
	 * @param data
	 */
	public void setData(Data data) {
		this.data = data;
	}

	/**
	 * Construtor parametizado
	 */
	public FileRead(String csvFile) {
		deviceInfo = new HashMap<>();
		fileInfo = new ArrayList<>();
		this.csvFile = csvFile;
		reader = null;
		line = null;
		csvSplitBy = ",";
		data = null;
	}

	/**
	 * Retorna um objeto de acordo com o tipo de arquivo que foi lido e insere
	 * nele as informações da leitura
	 * 
	 * @param str
	 * @return data
	 */
	public Data dataType(ArrayList<String> str) throws ParseException {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		if (csvFile.equals(DEVICE_FILE))
			return new Device(str.get(0), df.parse(str.get(1)), str.get(2).replace("DTAA/", ""), str.get(3),
					str.get(4));
		else if (csvFile.equals(HTTP_FILE))
			return new HTTP(str.get(0), df.parse(str.get(1)), str.get(2).replace("DTAA/", ""), str.get(3), str.get(4));
		else if (csvFile.equals(LDAP_FILE))
			return new LDAP(str.get(0), str.get(1), str.get(2), str.get(3), str.get(4));
		else
			return new Logon(str.get(0), df.parse(str.get(1)), str.get(2).replace("DTAA/", ""), str.get(3), str.get(4));
	}

	/**
	 * Lê-se a primeira linha que contém somente os tipos de informação do
	 * arquivo pois não serão guardados. Após isso cada linha é lida e
	 * armazenada em um ArrayList que é substituído com novas informações a cada
	 * linha. Inserem-se as informações do ArrayList em um novo objeto e após
	 * isso esse objeto é inserido no HashMap "users" para criar os perfis de
	 * usuários
	 */
	public void read() throws IOException, ParseException {
		reader = new BufferedReader(new FileReader(csvFile));
		String firstLine = reader.readLine();

		ProfileBuilder profile = new ProfileBuilder();
		while ((line = reader.readLine()) != null) {

			fileInfo = new ArrayList<>(Arrays.asList(line.split(csvSplitBy)));
			data = dataType(fileInfo);

			// Verifica qual é o tipo de objeto e chama o método correspondente
			// para adicionar ao HashMap de usuários
			if (data instanceof LDAP)
				profile.addToDatabase((LDAP) data);
			else if (data instanceof Device)
				profile.addToDatabase((Device) data);
			else if (data instanceof Logon)
				profile.addToDatabase((Logon) data);
			else
				profile.addToDatabase((HTTP) data);
		}
	}
}
