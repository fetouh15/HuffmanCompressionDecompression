/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author fetouh
 */
public class Decompress {
    static Node rootNode = new Node();
     void visualizeTree() {
        printRecursive(rootNode);
    }

     void printRecursive(Node root) {
        if (root != null) {
            printRecursive(root.left);
            System.out.println(root.getCharacter());
            printRecursive(root.right);
        }
    }

     void insertNodeToHuffman(Node node, char[] code) {
        Node temp = rootNode;

        for (int i = 0; i < code.length; i++) {
            // Reached leaf so set node.
            if (i == code.length - 1) {
                if (code[i] == '1') {
                    temp.right = node;
                } else if (code[i] == '0') {
                    temp.left = node;
                }

            } // Did not reach leaf so set null
            else {

                if (code[i] == '1') {
                    if (temp.right == null) {
                        temp.right = new Node();
                        temp.setCharacter(null);
                    }
                    temp = temp.right;
                } else if (code[i] == '0') {
                    if (temp.left == null) {
                        temp.left = new Node();
                        temp.setCharacter(null);
                    }
                    temp = temp.left;
                }
            }
        }
    }

     String readCompressedFile(String compressedFileName) {
        String data = "";
        int flagNewLine = 0;
int extraZero=0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("/Users/fetouh/Desktop/huffman/" + compressedFileName + ".txt")))) {
            String line;

            boolean header = true;
     //  extraZero=Integer.parseInt(bufferedReader.readLine());
       bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                /*
                 *** is the delimiter between header and data in input file
                 */
                if (line.equals("***")) {
                    header = false;
                    continue;
                }
                if (line.equals("")) {
                    flagNewLine = 1;
                    continue;
                }

                if (header == true) {

                    if (flagNewLine == 1) {
                        line = line.replaceFirst(" ", "");
                    }
                    String[] tokens = line.split(" ");
                    Node node = new Node();
                    
                    
                    if (flagNewLine == 1) {
                        flagNewLine = 0;
                        node.setCharacter("\n");
                        node.setFrequency(Integer.parseInt(tokens[0]));
                        char[] code = tokens[2].toCharArray();
                        insertNodeToHuffman(node, code);
                   visualizeTree();

                    } else {
                        
                        if (tokens[0].equals("") && tokens[1].equals("")) {
                            node.setCharacter(" ");
                            node.setFrequency(Integer.parseInt(tokens[2]));
                            char[] code = tokens[4].toCharArray();
                            insertNodeToHuffman(node, code);

                        } else {
                            node.setCharacter(tokens[0]);
                            node.setFrequency(Integer.parseInt(tokens[1]));
                            char[] code = tokens[3].toCharArray();
                            insertNodeToHuffman(node, code);
                        }

                       visualizeTree();
                    }

                } else {

//                    byte[] array = Files.readAllBytes(new File("/Users/fetouh/Desktop/huffman/" + compressedFileName + ".txt").toPath());
                    data += line+"\n";
                    
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
       /*char[] x=data.toCharArray();
    int[]y = new int[x.length];
    data="";
       int i=0;
       while(i<x.length)
       {  y[i]=(int)x[i]  ;     
           System.out.println(  Integer.toBinaryString(y[i]).length());
     data+= Integer.toBinaryString(y[i]);
     i++;
       }*/
 data=data.substring(0, data.length()-1);
       String encodedBinary="";
      for (int i = 0; i < data.length(); i++) { 
          
             /*   if (i == data.length() - 1) {
                    char c = data.charAt(i);
                     String cbinary = Integer.toBinaryString((int)c);
                    int remain = 8 - extraZero;
                    for (int j = 0; j < remain; j++) {
                        encodedBinary += cbinary.charAt(j);
                    }*/
               // } else {
                    char c = data.charAt(i);
                   // String cbinary = String.format("%08d", Integer.parseInt(Integer.toBinaryString(c)));
                                    String cbinary = Integer.toBinaryString((int)c);
while(cbinary.length()<8)
{cbinary="0"+cbinary;

}
                    encodedBinary += cbinary;

               // }
            } 
       
         System.out.println(encodedBinary);
        return encodedBinary;
    }

    static void decodeCharsToFile(String decompressedFileName, String data) {
String debug="";
        String original = "";
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(decompressedFileName)))) {

            Node temp = rootNode;
            for (int i = 0; i < data.length(); i++) {
                if (data.charAt(i) == '0') {
                    temp = temp.left;
        
                } else {
                    temp = temp.right;
             
                }

                // Reached leaf node so add the character to output
                if (temp.left == null && temp.right == null) 
                {
                
                   
                    bufferedWriter.write(temp.getCharacter());
                    original += temp.getCharacter();
                    //
                    temp = rootNode;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
