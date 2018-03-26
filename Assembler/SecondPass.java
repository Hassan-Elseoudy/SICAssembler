package assemblerPKJ;

import static assemblerPKJ.FirstPass.OPCode;
import static assemblerPKJ.FirstPass.SYMTAB;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecondPass {
	FirstPass x = new FirstPass();

	static StringBuffer intermediateFile2 = new StringBuffer();
	static StringBuffer WriteSicFile2(File file) {
		String endlocator = FirstPass.locator;
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String firstlocator;
			String line2;
			int count = 1;
			while ((line2 = bufferedReader.readLine()) != null) {
				Matcher matcher3 = FirstPass.patten3.matcher(line2);
				if (count > 10) {
					intermediateFile2.append("\n");
					count = 1;
				}
				if (line2.matches(FirstPass.pattern3)) {
					if (matcher3.find()) {
						if (matcher3.group(3).equals("RESB") || matcher3.group(3).equals("RESW")) {
							intermediateFile2.append("\n");
							count = 1;
						}
						if (matcher3.group(3).equals("START")) {
							firstlocator = matcher3.group(5);
							intermediateFile2.append("H " + matcher3.group(1) + " " + firstlocator + " " + Integer.toHexString(
											Integer.parseInt(endlocator, 16) - Integer.parseInt(firstlocator, 16) - 3)+"\n");
						} else {
							if (OPCode.containsKey(matcher3.group(3)) && SYMTAB.containsKey(matcher3.group(5))) {
								intermediateFile2.append(OPCode.get(matcher3.group(3)) + SYMTAB.get(matcher3.group(5)) + " ");
								count++;
							}

							else if (matcher3.group(3).equals("BYTE")) {
								if (matcher3.group(5).charAt(0) == 'C') {
									for (int i = 2; i < matcher3.group(5).length() - 1; i++) {
										intermediateFile2.append(Integer.toHexString((int) (matcher3.group(5).charAt(i))));
									}
									intermediateFile2.append(" ");
									count++;

								} else if (matcher3.group(5).charAt(0) == 'X') {
									intermediateFile2.append(matcher3.group(5).substring(2, matcher3.group(5).length() - 1)+" ");
									count++;
								}
							}

							else if (matcher3.group(3).equals("WORD")) {
								intermediateFile2.append(String.format("%06d",Integer.parseInt(matcher3.group(5),16))+ " ");
								count++;
							}

						}
					}
				}
				if (line2.matches(FirstPass.pattern1)) {
					Matcher matcher1 = FirstPass.patten1.matcher(line2);
					if (matcher1.find()) {
						if (OPCode.containsKey(matcher1.group(1))) {
							intermediateFile2.append(OPCode.get(matcher1.group(1)) + "0000 ");
							count++;
						}
					}
				} else if (line2.matches(FirstPass.pattern2)) {
					Matcher matcher2 = FirstPass.patten2.matcher(line2);
					if (matcher2.find()) {
						if (OPCode.containsKey(matcher2.group(1)) && SYMTAB.containsKey(matcher2.group(3))) {
							intermediateFile2.append(OPCode.get(matcher2.group(1)) + SYMTAB.get(matcher2.group(3)) + " ");
							count++;
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return intermediateFile2;

	}
}