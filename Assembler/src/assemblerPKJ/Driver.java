package assemblerPKJ;

import java.io.File;
import static assemblerPKJ.FirstPass.*;

public class Driver {
	private static final String FILENAME = "X:\\Smsm\\Assembler\\SIC.txt";

	public static void main(String[] args) {
		initializeOPCODE();
		File file = new File(FILENAME);
		readSicFile(file);

	}

}
