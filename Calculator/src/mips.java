import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
 
public class MIPS {
        public static void main(String[] args) throws IOException {
                FileInputStream fr = null;
                InputStreamReader fp = null;
                BufferedReader br = null;
                try {
                        String line = "";
                        fr = new FileInputStream("test.txt");
                        fp = new InputStreamReader(fr);
                        br = new BufferedReader(fp);                                                                                      
                        line = br.readLine();
                                boolean x = false;                             
 
                        if (line.equals("0"))
                                        x = true;
         
                                int[] bits = new int[32];
                                String[] store = new String[32];
                                Arrays.fill(bits, 0);
                                Arrays.fill(store, "");
                                while ((line = br.readLine()) != null) {
                                        int tmp = Integer.parseInt(line, 16);
                                        String binary = Integer.toBinaryString(tmp);
                                        if(binary.length() < 32){
                                                int l = 32 - binary.length();
                                                for(int k = 0; k < l; k++){
                                                        binary = "0" + binary;
                                                }
                                        }
                                        String opcode = binary.substring(0, 6);
                                        String funct = binary.substring(26);
                                        if (opcode.equals("000000")) { // R-Type Instructions
                                                if (funct.equals("000000")) { //sll
                                                        String first = binary.substring(11, 16);
                                                        String second = binary.substring(16, 21);
                                                        String third = binary.substring(21, 26);
                                                        int nu4 = Integer.parseInt(third, 2);
                                                        int nu2 = Integer.parseInt(first, 2);
                                                        int nu3 = Integer.parseInt(second, 2);
                                                        int result = bits[nu2] * (int) Math.pow(2, nu4);
                                                        bits[nu3] = result;
                                                        store[nu3] = Integer.toHexString(result);
                                                }
                                                if(funct.equals("000010")){ //srl
                                                        String first = binary.substring(11, 16);
                                                        String second = binary.substring(16, 21);
                                                        String third = binary.substring(21, 26);
                                                        int nu4 = Integer.parseInt(third, 2);
                                                        int nu2 = Integer.parseInt(first, 2);
                                                        int nu3 = Integer.parseInt(second, 2);
                                                        int result = bits[nu2] / (int) Math.pow(2, nu4);
                                                        bits[nu3] = result;
                                                        store[nu3] = Integer.toHexString(result);
                                                }
                                                if(funct.equals("100001")){ //addu
                                                        String num1 = binary.substring(6, 11);
                                                        String first = binary.substring(11, 16);
                                                        String second = binary.substring(16, 21);
                                                        int nu1 = Integer.parseInt(num1, 2);
                                                        int nu2 = Integer.parseInt(first, 2);
                                                        int nu3 = Integer.parseInt(second, 2);
                                                        bits[nu3] = bits[nu1] + bits[nu2];
                                                        store[nu3] = Integer.toHexString(bits[nu3]);
                                                }
                                                if(funct.equals("100000")){ //add
                                                        String num1 = binary.substring(6, 11);
                                                        String first = binary.substring(11, 16);
                                                        String second = binary.substring(16, 21);
                                                        int nu1 = Integer.parseInt(num1, 2);
                                                        int nu2 = Integer.parseInt(first, 2);
                                                        int nu3 = Integer.parseInt(second, 2);
                                                        bits[nu3] = bits[nu1] + bits[nu2];
                                                        store[nu3] = Integer.toHexString(bits[nu3]);
                                                }
                                                if(funct.equals("100100")){ // and
                                                        String num1 = binary.substring(6, 11);
                                                        String first = binary.substring(11, 16);
                                                        String second = binary.substring(16, 21);
                                                        int nu1 = Integer.parseInt(num1, 2);
                                                        int nu2 = Integer.parseInt(first, 2);
                                                        int nu3 = Integer.parseInt(second, 2);
                                                        bits[nu3] = bits[nu1] & bits[nu2];
                                                        store[nu3] = Integer.toHexString(bits[nu3]);
                                                }
                                                if(funct.equals("100111")){ //nor
                                                        String num1 = binary.substring(6, 11);
                                                        String first = binary.substring(11, 16);
                                                        String second = binary.substring(16, 21);
                                                        int nu1 = Integer.parseInt(num1, 2);
                                                        int nu2 = Integer.parseInt(first, 2);
                                                        int nu3 = Integer.parseInt(second, 2);
                                                        bits[nu3] = bits[nu1] ^ bits[nu2];
                                                        store[nu3] = Integer.toHexString(bits[nu3]);
                                                }
                                                if(funct.equals("101010")){ //slt
                                                        String num1 = binary.substring(6, 11);
                                                        String first = binary.substring(11, 16);
                                                        String second = binary.substring(16, 21);
                                                        int nu1 = Integer.parseInt(num1, 2);
                                                        int nu2 = Integer.parseInt(first, 2);
                                                        int nu3 = Integer.parseInt(second, 2);
                                                        if(bits[nu1] < bits[nu2]){
                                                                bits[nu3] = 1;
                                                        }
                                                        else{
                                                                bits[nu3] = 0;
                                                        }
                                                        store[nu3] = Integer.toHexString(bits[nu3]);
                                                }
                                                if(funct.equals("101011")){ //sltu
                                                        String num1 = binary.substring(6, 11);
                                                        String first = binary.substring(11, 16);
                                                        String second = binary.substring(16, 21);
                                                        int nu1 = Integer.parseInt(num1, 2);
                                                        int nu2 = Integer.parseInt(first, 2);
                                                        int nu3 = Integer.parseInt(second, 2);
                                                        if(bits[nu1] < bits[nu2]){
                                                                bits[nu3] = 1;
                                                        }
                                                        else{
                                                                bits[nu3] = 0;
                                                        }
                                                        store[nu3] = Integer.toHexString(bits[nu3]);
                                                }
                                        }
                                        //I-Type Instructions
                                        if (opcode.equals("001001")) { //addiu
                                                String num1 = binary.substring(6, 11);
                                                String first = binary.substring(11, 16);
                                                String second = binary.substring(16);
                                                int nu1 = Integer.parseInt(num1, 2);
                                                int nu2 = Integer.parseInt(first, 2);
                                                int nu3 = Integer.parseInt(second, 2);
                                                bits[nu1] = bits[nu2] + nu3;
                                                store[nu1] = Integer.toHexString(bits[nu1]);
                                        }
                                        if(opcode.equals("001010")){ //slti
                                                String num1 = binary.substring(6, 11);
                                                String first = binary.substring(11, 16);
                                                String second = binary.substring(16);
                                                int nu1 = Integer.parseInt(num1, 2);
                                                int nu2 = Integer.parseInt(first, 2);
                                                int nu3 = Integer.parseInt(second, 2);
                                                if(bits[nu2] < nu3){
                                                        bits[nu1] = 1;
                                                }
                                                else{
                                                        bits[nu1] = 0;
                                                }
                                                store[nu1] = Integer.toHexString(bits[nu1]);
                                        }
                                        if(opcode.equals("001111")){ //lui
                                                String first = binary.substring(11, 16);
                                                String second = binary.substring(16);
                                                int nu2 = Integer.parseInt(first, 2);
                                                int nu3 = Integer.parseInt(second, 2);
                                                bits[nu2] = nu3 * 65536;
                                                store[nu2] = Integer.toHexString(bits[nu2]);
                                        }
                                        if(!x)
                                        for (int i = 0; i < 32; i++) {
                                            System.out.print("$" + i + ":");
                                            if (store[i].equals("")) {
                                                    System.out.print("0x00000000  ");
                                            }
                                            if (store[i].length() == 1) {
                                                    System.out.print("0x");
                                                    System.out.print("0000000");
                                                    System.out.print(store[i] + "  ");
                                            }
                                            if (store[i].length() == 2) {
                                                    System.out.print("0x");
                                                    System.out.print("000000");
                                                    System.out.print(store[i] + "  ");
                                            }
                                            if (i == 3 || i == 7 || i == 11 || i == 15 || i == 19
                                                            || i == 23 || i == 27) {
                                                    System.out.println();
                                            }
                                                        if (i == 31) {
                                                                        System.out.println();
                                                                        for (int j = 0; j < 40; j++) {
                                                                                System.out.print("=");
                                                                        }
                                                                        System.out.println();
                                                                }
 
                                    }
                                }
                                if(x)
                                    for (int i = 0; i < 32; i++) {
                                        System.out.print("$" + i + ":");
                                        if (store[i].equals("")) {
                                                System.out.print("0x00000000  ");
                                        }
                                        if (store[i].length() == 1) {
                                                System.out.print("0x");
                                                System.out.print("0000000");
                                                System.out.print(store[i] + "  ");
                                        }
                                        if (store[i].length() == 2) {
                                                System.out.print("0x");
                                                System.out.print("000000");
                                                System.out.print(store[i] + "  ");
                                        }
                                        if (i == 3 || i == 7 || i == 11 || i == 15 || i == 19
                                                        || i == 23 || i == 27) {
                                                System.out.println();
                                        }
                                                        if (i == 31) {
                                                                        System.out.println();
                                                                        for (int j = 0; j < 40; j++) {
                                                                                System.out.print("=");
                                                                        }
                                                                        System.out.println();
                                                                }
 
                                }
                               
                       
                }
                catch (FileNotFoundException e) {
                        System.out.println("File not Found");
                } catch (IOException e) {
                        System.out.println("IO Exception");
                } finally {
                        try {
                                br.close();
                                fp.close();
                                fr.close();
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                }
        }
}