package assemblerPKJ;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static assemblerPKJ.FirstPass.*;

public class Driver {
	private static final String FILENAME = "X:\\Smsm\\Assembler\\SIC.txt";

	public static void main(String[] args) throws FileNotFoundException {
		initializeOPCODE();
		File file = new File(FILENAME);
//		System.out.println(readSicFile(file));
		try (PrintWriter out = new PrintWriter("filename.txt")) {
		    out.println(readSicFile(file));
		}

		System.out.println("-------");
		System.out.println(SYMTAB);

	}

}
