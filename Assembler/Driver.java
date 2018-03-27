package assemblerPKJ;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import static assemblerPKJ.FirstPass.*;
import static assemblerPKJ.SecondPass.*;

public class Driver {
	private static final String FILENAME = "SIC.txt";
	private static final String FILENAME1 = "Formatted!.txt";
	// private static final String FILENAME2 = "pre_pass2.txt";

	public static void main(String[] args) throws IOException {
		initializeOPCODE();
		File file = new File(FILENAME);
		File file1 = new File(FILENAME1);
		formatTheFileNow(file);
		try (PrintWriter out = new PrintWriter("pass1.txt")) {
			String[] columns = readSicFile(file1).toString().split("\n");
			for (int i = 0; i < columns.length; i++)
				if (columns[i].length() != 0)
					out.println(columns[i]);
			out.print(lastline);
		}
		try (PrintWriter out = new PrintWriter("SYMTAB.txt")) {
			for (String key : SYMTAB.keySet())
				out.println(key + " = " + SYMTAB.get(key) + "\n");
		}
		try (PrintWriter out = new PrintWriter("pass2.txt")) {
			String[] columns = WriteSicFile2(file1).toString().split("\n");
			for (int i = 0; i < columns.length; i++)
				if (columns[i].length() != 0)
					out.println(columns[i]);
		}	
	}
}