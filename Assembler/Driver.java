package assemblerPKJ;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static assemblerPKJ.FirstPass.*;
import static assemblerPKJ.SecondPass.*;

public class Driver {
	private static final String FILENAME = "SIC.txt";

	public static void main(String[] args) throws FileNotFoundException {
		initializeOPCODE();
		File file = new File(FILENAME);
		StringBuffer toBeAnObjectFile = readSicFile(file);
		try (PrintWriter out = new PrintWriter("pass1.txt")) {
			String[] columns = toBeAnObjectFile.toString().split("\n");
			for (int i = 0; i < columns.length; i++)
				if (columns[i].length() != 0)
					out.println(columns[i]);

		}
		try (PrintWriter out = new PrintWriter("SYMTAB.txt")) {
			String[] columns = SYMTAB.toString().split(",");
			for (int i = 0; i < columns.length; i++)
				if (columns[i].length() != 0)
					out.println(columns[i]);
		}
		try (PrintWriter out = new PrintWriter("pass2.txt")) {
			String[] columns = WriteSicFile2(file).toString().split("\n");
			for (int i = 0; i < columns.length; i++)
				if (columns[i].length() != 0)
					out.println(columns[i]);
		}
	}

}
