package assemblerPKJ;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FirstPass {
	static String locator;
	static String pattern1 = "(\\S{1,6})";												  // RSUB <-- Example
	static String pattern2 = "(\\S{1,6})(\\s+)(\\S{1,6})|(\\S{1,6})(\\s+)(\\S{1,6})(,X)"; // LDX ZERO | LDCH   BUFFER,X <-- Examples
	static String pattern3 = "(\\S{1,6})(\\s+)(\\S{1,6})(\\s+)(\\S+)";					  // WRREC  LDX   ZERO <-- Example 
	static HashMap<String, String> OPCode = new HashMap<String, String>();					// Add in OPCODE
	static HashMap<String, String> SYMTAB = new HashMap<String, String>();					// Add in SYMTAB
																							//Maybe we'll need ObjectHashMap
	static void readSicFile(File file) {
		try {
			Pattern patten1 = Pattern.compile(pattern1);
			Pattern patten2 = Pattern.compile(pattern2);
			Pattern patten3 = Pattern.compile(pattern3);

			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
//			StringBuffer stringBuffer = new StringBuffer();
//			StringBuffer intermediateFile = new StringBuffer();
			
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.matches(pattern3)) {
					Matcher matcher3 = patten3.matcher(line); // Matches the first line that contains locator
						if (matcher3.find()) {
							if (matcher3.group(3).equals("START")) {
								locator = matcher3.group(5); // first Location
							}
							else if (matcher3.group(3).equals("WORD")) {
								SYMTAB.put(matcher3.group(1),(locator));
								locator = Integer.toHexString(Integer.parseInt(locator, 16) + 3);	// Add Word Size
																									//Integer to Hex, to addition in Hexa
							}

							else if (matcher3.group(3).equals("RESW")) {
								SYMTAB.put(matcher3.group(1), locator);
								locator = Integer.toHexString(Integer.parseInt(locator, 16) + Integer.parseInt(matcher3.group(5)) * 3);
						//		locator = Integer.toHexString(Integer.parseInt(locator, 16) + 3);
							}

							else if (matcher3.group(3).equals("RESB")) {
								SYMTAB.put(matcher3.group(1), locator);
								locator = Integer.toHexString(Integer.parseInt(locator, 16) + Integer.parseInt(matcher3.group(5)));
							//	locator = Integer.toHexString(Integer.parseInt(locator, 16) + 3);
							}
							
							else if (matcher3.group(3).equals("BYTE")) {
								SYMTAB.put(matcher3.group(1),(locator));
								if(matcher3.group(5).charAt(0) == 'C')
							locator = Integer.toHexString(Integer.parseInt(locator, 16) + (matcher3.group(5).length()-3));
								else {
							locator = Integer.toHexString(Integer.parseInt(locator, 16) + ((matcher3.group(5).length()-3)/2));	
								}

							//	locator = Integer.toHexString(Integer.parseInt(locator, 16) + 3);
							}
							else {
								SYMTAB.put(matcher3.group(1),(locator));
								locator = Integer.toHexString(Integer.parseInt(locator, 16) + 3);
							}
						}
					} 
				else if (line.matches(pattern2)) {
					Matcher matcher2 = patten2.matcher(line);
					if (matcher2.find()) {
						locator = Integer.toHexString(Integer.parseInt(locator, 16) + 3);
					}
				}
				else if (line.matches(pattern1)) {							//Check for condition Like RSUB/JSUB
					Matcher matcher1 = patten1.matcher(line);
					if (matcher1.find() && OPCode.containsKey(matcher1.group(0))) {
						locator = Integer.toHexString(Integer.parseInt(locator, 16) + 3);
					}
				}
//						if (matcher2.group(3).equals("WORD")) {
//							locator = Integer.toHexString(Integer.parseInt(locator, 16) + 3);
//							SYMTAB.put(matcher2.group(1),(locator));
//						}
//
//						else if (matcher2.group(3).equals("RESW")) {
//							locator = Integer.toHexString(Integer.parseInt(locator, 16) + Integer.parseInt(matcher2.group(5)) * 3);
//							SYMTAB.put(matcher2.group(1), locator);
//						}
//
//						else if (matcher2.group(3).equals("RESB")) {
//							locator = Integer.toHexString(Integer.parseInt(locator, 16) + Integer.parseInt(matcher2.group(5)));
//							SYMTAB.put(matcher2.group(1), locator);
//						}
//					}
//				}
//			System.out.println("Test");
//					intermediateFile.append(line);
//					intermediateFile.append("\n");
//				System.out.println(line);
//				stringBuffer.append(line);
//				stringBuffer.append("\n");
				System.out.println(locator);
			}
			System.out.println(Arrays.asList(SYMTAB));
			fileReader.close();
			// System.out.println(intermediateFile.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void initializeOPCODE() { //OPCodes
		OPCode.put("ADD", "18");
		OPCode.put("AND", "40");
		OPCode.put("COMP", "28");
		OPCode.put("DIV", "24");
		OPCode.put("J", "3C");
		OPCode.put("JEQ", "30");
		OPCode.put("JGT", "34");
		OPCode.put("JLT", "38");
		OPCode.put("JSUB", "48");
		OPCode.put("LDCH", "50");
		OPCode.put("LDA", "00");
		OPCode.put("LDL", "08");
		OPCode.put("LDX", "04");
		OPCode.put("MUL", "20");
		OPCode.put("OR", "44");
		OPCode.put("RD", "D8");
		OPCode.put("RSUB", "4C");
		OPCode.put("STA", "0C");
		OPCode.put("STCH", "54");
		OPCode.put("STL", "14");
		OPCode.put("STX", "10");
		OPCode.put("SUB", "1C");
		OPCode.put("TD", "E0");
		OPCode.put("TIX", "2C");
		OPCode.put("WD", "DC");
	}
}