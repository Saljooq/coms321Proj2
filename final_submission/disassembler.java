import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.nio.file.Files;

class set{
    Integer[] in = new Integer[5];
    public set(){

    }
}

public class disassembler
{
        private static ArrayList <Integer> labelnum = new ArrayList <Integer>();
        private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

	public static void main(String[] args) throws IOException
        {

                File input = new File(args[0]);


		ArrayList<String> input_string = new ArrayList<>();

		byte[] fileBytes = Files.readAllBytes(input.toPath());

		for(int i = 0; i < fileBytes.length; i += 4)
		{
			byte[] instruction = Arrays.copyOfRange(fileBytes, i, i+4);

			//Test to make sure everything was read properly
			//System.out.println(bytesToHex(instruction));

			input_string.add(toBinaryString(instruction));
		}

                ArrayList<set> stor = new ArrayList<set>();
		ArrayList<String> Instructions = new ArrayList<String>();
		// ArrayList<String> input_string = new  ArrayList<String>();
                //
		// input_string.add("10001011000111110000000000010100"); //-> translates to ADD X20, X0, X31
		// input_string.add("10001011000111100000000000010100"); //-> translates to ADD X20, X0, X30
		// input_string.add("10110100000000000000000001100000"); //-> translates to CBZ X0, done
		// input_string.add("11010001000000000000010000001001"); //-> translates to SUBI X9, X0, #1
                //
		// input_string.add("10110101000000000000000001001001"); //-> translates to CBNZ X9, body
		// input_string.add("11010110000000000000001111000000"); //-> translates to BR LR
		// input_string.add("11010001000000000110001110011100"); //-> translates to SUBI SP, SP, #24
                //
		// input_string.add("10001011000111110000000000010100"); //-> translates to ADD X20, X0, X31
		// input_string.add("11010001000000000000101001100000"); //-> translates to SUBI X0, X19, #2
		// input_string.add("10010111111111111111111111110011"); //-> translates to BL -13
                //
                //
		// input_string.add("10001011000101000000000000000000"); //-> translates to ADD X0, X0, X20
		// input_string.add("11111000010000000000001110010100"); //-> translates to LDUR X20, [X28, #0]



		String[][] data =
			{
				{"000101", 		"B", 		"B"	},
				{"01010100", 	"B.cond", 	"CB"},
				{"100101",		"BL", 		"B"	},
				{"11010110000",	"BR", 		"R"	},
				{"10110101",	"CBNZ", 	"CB"	},
				{"10110100",	"CBZ", 		"CB"	},


				{"10001011000", "ADD", 		"R"	},
				{"1001000100",	"ADDI", 	"I"	},

				{"11001011000",	"SUB", 		"R"	},
				{"1101000100",	"SUBI", 	"I"	},
				{"1111000100",	"SUBIS", 	"I"	},
				{"11101011000",	"SUBS", 	"R"	},


				{"10101010000",	"ORR", 		"R"	},
				{"1011001000",	"ORRI", 	"I"	},

				{"11001010000",	"EOR", 		"R"	},
				{"1101001000",	"EORI", 	"I"	},


				{"10001010000",	"AND", 		"R"	},
				{"1001001000",	"ANDI", 	"I"	},

				{"11111000010",	"LDUR", 	"D"	},
				{"11111000000",	"STUR", 	"D"	},

				{"11010011011",	"LSL", 		"R"	},
				{"11010011010",	"LSR", 		"R"	},

				{"11111111101",	"PRNT", 	"R"	},
				{"11111111100",	"PRNL", 	"R"	},
				{"11111111110",	"DUMP", 	"R"	},
				{"11111111111",	"HALT", 	"R"	},

				{"10011011000",	"MUL", 	"R"	}

			};

		String[][] core =
			{
					{"R", "Rm", "20", "16", "Shamt", "15", "10", "Rn", "9", "5", "Rd", "4", "0"},

					{"I", "ALU_immediate", "21", "10", "Rn", "9", "5", "Rd", "4", "0"},

					{"D", "DT_address", "20", "12","op", "11","10", "Rn", "9", "5", "Rt", "4", "0"},

					{"B", "BR_address", "25", "0"},

					{"CB","COND_BR_ADDRESS", "23", "5","Rt", "4", "0"},

					{"I", "MOV_immediate", "20", "5", "Rd", "4", "0"}
			};

		//Scanner scnr = new Scanner(System.in);
		//String inst = scnr.next();

		for (int reader = 0; reader < input_string.size(); reader++)
		{
			set tempSet = new set();
			String inst = input_string.get(reader);
			int found = -1;
			for (int j = 0; j < inst.length() && found == -1; j++)
			{
				String temp = inst.substring(0, j);
				//System.out.println(temp);
				for (int i = 0; i < data.length; i++)
				{
					if (data[i][0].equals(temp))
					{
						//System.out.println("Instruction name: "+data[i][1]+"     Instruction Type: "+data[i][2]);
						found = i;
						break;
					}
				}
			}

			Instructions.add(data[found][1]);

			int offset1;
			int offset2;
			int reg_num = 0;
			for (int i = 0; i < core.length; i++)
			{
				if (data[found][2].equals(core[i][0])) {

					int j = 1;
					while (j < core[i].length)
					{

						//System.out.print(core[i][j]+": ");
						j++;

						offset1 = Integer.parseInt(core[i][j]);
						j++;
						offset1 = 32 - offset1-1;
						offset2 = Integer.parseInt(core[i][j]);
						j++;
						offset2 = 32 - offset2;

						int decimalVal = get_register(offset1, offset2, inst);

						String register = "X" + decimalVal;

						tempSet.in[reg_num] = decimalVal;
						reg_num++;
						//System.out.print(register+"   ");
					}

				}
			}
			//System.out.println("\n");
			stor.add(tempSet);

		}

                for (int i = 0; i < Instructions.size(); i++)
		{
                        Integer[] in = stor.get(i).in;

                        String temp = Instructions.get(i);

                        if (temp.equals("B") || temp.equals("BL") || temp.equals("B.cond") || temp.equals("CBZ") || temp.equals("CBNZ"))
                        {
                                if (labelnum.contains(i+in[0]))
                                {
                                        //do nothing for now
                                }
                                else
                                {
                                        labelnum.add(i+in[0]);
                                }
                        }

                }


		/////////////////////////////////////
		//CODE FOR PRINTING CAN GO HERE//////
		/////////////////////////////////////

		//SAMPLE PRINT
		for (int i = 0; i < Instructions.size()+1; i++)
		{
                        if (labelnum.contains(i))
                        {
                                System.out.println("\n\n"+labels(i,0)+":");
                        }
			if (i==Instructions.size()) break;

                        //System.out.print("  "+i+"  ");

			String temp = Instructions.get(i); //temp = "ADDI"
			Integer[] in = stor.get(i).in;

			if (temp.equals("ADD")) System.out.println("ADD "+ check_name(in[3]) +", "+ check_name(in[2]) + ", " + check_name(in[0]) );
			else if (temp.equals("ADDI")) System.out.println("ADDI " + check_name(in[2]) + ", " + check_name(in[1]) + ", #" + in[0]);
			else if (temp.equals("AND")) System.out.println("AND " + check_name(in[3]) + ", " + check_name(in[2]) + ", " + check_name(in[0]));
			else if (temp.equals("ANDI")) System.out.println("ANDI " + check_name(in[2]) + ", " + check_name(in[1]) + ", #" + in[0]);
			else if (temp.equals("B")) System.out.println("B " + labels(i,in[0]));
			else if (temp.equals("B.cond")){
                                if (in[1]>15) in[1] -= 16;//correction for the first bit since if its 0 or 1 - the last four will determine the condition
                		System.out.print("B.");
                		if(in[1] == 0) System.out.print("EQ");
                		else if(in[1] == 1) System.out.print("NE");
                		else if(in[1] == 2) System.out.print("HS");
                		else if(in[1] == 3) System.out.print("LO");
                		else if(in[1] == 4) System.out.print("MI");
                		else if(in[1] == 5) System.out.print("PL");
                		else if(in[1] == 6) System.out.print("VS");
                		else if(in[1] == 7) System.out.print("VC");
                		else if(in[1] == 8) System.out.print("HI");
                		else if(in[1] == 9) System.out.print("LS");
                		else if(in[1] == 10) System.out.print("GE"); //listed as 'a' (base 16) on assignment spec is it fine to leave as 10?
                		else if(in[1] == 11) System.out.print("LT"); //listed as 'b' (base 16) on assignment spec is it fine to leave as 11?
                		else if(in[1] == 12) System.out.print("GT"); //listed as 'c' (base 16) on assignment spec is it fine to leave as 12?
                		else if(in[1] == 13) System.out.print("LE"); //listed as 'd' (base 16) on assignment spec is it fine to leave as 13?
                		System.out.println(" "+ labels(i,in[0]));
			}
			else if (temp.equals("BL")) System.out.println("BL " + labels(i,in[0]));
			else if (temp.equals("BR")) System.out.println("BR " + check_name(in[2])); //Says this should be an R instruction but not sure how that works??
			else if (temp.equals("CBNZ")) System.out.println("CBNZ " + check_name(in[1]) + ", " + labels(i,in[0]));
			else if (temp.equals("CBZ")) System.out.println("CBZ " + check_name(in[1]) + ", " + labels(i,in[0]));
			else if (temp.equals("EOR")) System.out.println("EOR " + check_name(in[3]) + ", " + check_name(in[2]) + ", " + check_name(in[0]));
			else if (temp.equals("EORI")) System.out.println("EORI " + check_name(in[2]) + ", " + check_name(in[1]) + ", #" + in[0]);
			else if (temp.equals("LDUR")) System.out.println("LDUR " + check_name(in[3]) + ", [" + check_name(in[2]) + ", #" + in[0] + "]");
			else if (temp.equals("LSL")) System.out.println("LSL " + check_name(in[3]) + ", " + check_name(in[2]) + ", #" + in[1]);
			else if (temp.equals("LSR")) System.out.println("LSR " + check_name(in[3]) + ", " + check_name(in[2]) + ", #" + in[1]);
			else if (temp.equals("ORR")) System.out.println("ORR " + check_name(in[3]) + ", " + check_name(in[2]) + ", " + check_name(in[0]));
			else if (temp.equals("ORRI")) System.out.println("ORRI " + check_name(in[2]) + ", " + check_name(in[1]) + ", #" + in[0]);
			else if (temp.equals("STUR")) System.out.println("STUR " + check_name(in[3]) + ", [" + check_name(in[2]) + ", #" + in[0] + "]");
			else if (temp.equals("SUB")) System.out.println("SUB " + check_name(in[3]) + ", " + check_name(in[2]) + ", " + check_name(in[0]));
			else if (temp.equals("SUBI")) System.out.println("SUBI " + check_name(in[2]) + ", " + check_name(in[1]) + ", #" + in[0]);
			else if (temp.equals("SUBIS")) System.out.println("SUBIS " + check_name(in[2]) + ", " + check_name(in[1]) + ", #" + in[0]); //How to set flags in this one?
                        else if (temp.equals("SUBS")) System.out.println("SUBS " + check_name(in[3]) + ", " + check_name(in[2]) + ", " + check_name(in[0]));
			else if (temp.equals("MUL")) System.out.println("MUL " + check_name(in[3]) + ", " + check_name(in[2]) + ", " + check_name(in[0]));
			else if (temp.equals("PRNT")) System.out.println("PRNT "+ check_name(in[3]));
			else if (temp.equals("PRNL")) System.out.println("PRNL");
			else if (temp.equals("DUMP")) System.out.println("DUMP");
			else if (temp.equals("HALT")) System.out.println("HALT");
                        else System.out.println("NOTHING");


			////////////////////////////////////
			////ADD MORE ELSE IF STATEMENTS HERE
			//////////////////////////////////

		}

	}

	private static String check_name(int register)
	{
	  if(register == 0) {return "X0";}
	  else if(register == 1) {return "X1";}
	  else if(register == 2) {return "X2";}
	  else if(register == 3) {return "X3";}
	  else if(register == 4) {return "X4";}
	  else if(register == 5) {return "X5";}
	  else if(register == 6) {return "X6";}
	  else if(register == 7) {return "X7";}
	  else if(register == 8) {return "X8";}
	  else if(register == 9) {return "X9";}
	  else if(register == 10) {return "X10";}
	  else if(register == 11) {return "X11";}
	  else if(register == 12) {return "X12";}
	  else if(register == 13) {return "X13";}
	  else if(register == 14) {return "X14";}
	  else if(register == 15) {return "X15";}
	  else if(register == 16) {return "X16";}
	  else if(register == 17) {return "X17";}
	  else if(register == 18) {return "X18";}
	  else if(register == 19) {return "X19";}
	  else if(register == 20) {return "X20";}
	  else if(register == 21) {return "X21";}
	  else if(register == 22) {return "X22";}
	  else if(register == 23) {return "X23";}
	  else if(register == 24) {return "X24";}
	  else if(register == 25) {return "X25";}
	  else if(register == 26) {return "X26";}
	  else if(register == 27) {return "X27";}
	  else if(register == 28) {return "SP";}
	  else if(register == 29) {return "FP";}
	  else if(register == 30) {return "LR";}
	  else if(register == 31) {return "XZR";}
          else return "not found";
	}


	private static int get_register(int off1, int off2, String inst) {

		String temp = inst.substring(off1, off2);
		int num = Integer.parseInt(temp, 2);
		//System.out.print("---check:"+(off2-off1+1)+"---");

		if ((off2-off1)>6) { //check if the size is greater that 6, which is what shamt need, and 5 for registers, the remainder are assumed signed
			if (inst.charAt(off1)=='1') {
				//System.out.print("off1:"+off1+ "  off2:"+off2+"---coverting to negative---"+num+"-->upper>>"+(power(2,(off2 - off1))));
				num = (num - (power(2,(off2 - off1))));
				//System.out.print("---new value:"+num);
			}
		}

		return num;
	}

	private static int power(int base, int exp) {
		int res = 1;
		for (int i = 0; i < exp; i++) {
			res *= base;
		}

		return res;
	}

        public static String toBinaryString(byte[] arr)
        {
                String result = "";
                for(int i = 0; i < arr.length; i++)
                {
                        StringBuilder sb = new StringBuilder("00000000");
                        for (int bit = 0; bit < 8; bit++)
                        {
                                if (((arr[i] >> bit) & 1) > 0)
                                {
                                        sb.setCharAt(7 - bit, '1');
                                }
                        }
                        result += sb.toString();
                }
                return result;
        }

        public static String bytesToHex(byte[] bytes)
        {
            char[] hexChars = new char[bytes.length * 2];
            for (int j = 0; j < bytes.length; j++)
            {
                int v = bytes[j] & 0xFF;
                hexChars[j * 2] = HEX_ARRAY[v >>> 4];
                hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
            }
            return new String(hexChars);
        }

        private static String labels(int inst, int a)
        {
                int loc = inst + a;

                for (int i = 0; i < labelnum.size(); i++)
                {
                        if (loc==labelnum.get(i))
                        {
                                return "label_"+i;
                        }
                }

                return "no_label";
        }

}
