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
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author fetouh
 */
public class Huffman {

    /**
     * @param args the command line arguments
     */
    static List<String> characters = new ArrayList<String>();
    static List<Integer> frequency = new ArrayList<Integer>();
    static List<Node> nodes = new ArrayList<Node>();
    static String toBeCompressed;
    static Node rootNode = new Node();
static int extraZeros;
    private static String getFileName() {
        System.out.print("Enter file name\n");
        Scanner scan = new Scanner(System.in);
        return scan.next();

    }

    private static int compressOrDecompress() {
        System.out.print("Enter 1 to compress input file and 2 to decompress file\n");
        Scanner scan = new Scanner(System.in);
        return scan.nextInt();
    }

    private static String readTextFile(String fileName) throws FileNotFoundException, IOException {
        FileReader fileReader = new FileReader("/Users/fetouh/Desktop/huffman/" + fileName + ".txt");
        BufferedReader bufferReader = new BufferedReader(fileReader);
        String textFile = "";
        String line;
        while ((line = bufferReader.readLine()) != null) {
            textFile += line + "\n";
        }
        bufferReader.close();
        toBeCompressed = textFile;
        return textFile;
    }

    private static void countCharactersFrequency(String textFile) {

        String character;
        for (int i = 0; i < textFile.length() - 1; i++) {
            character = String.valueOf(textFile.charAt(i));
            int j = 0, flag = 0;
            while (j < characters.size()) {
                if (characters.get(j).equals(character)) {
                    flag = 1;

                    frequency.set(j, frequency.get(j) + 1);
                }
                j++;
            }

            if (flag == 0) {
                characters.add(character);
                frequency.add(1);
            }
            flag = 0;

        }

    }

    public static void loadArrayList() {

        for (int j = 0; j < frequency.size(); j++) {
            Node node = new Node();
            node.setCharacter(characters.get(j));
            node.setFrequency(frequency.get(j));
            node.setOldcode(Integer.toBinaryString((int)characters.get(j).charAt(0)));
            nodes.add(node);
        }
        Collections.sort(nodes);
    }

    public static void printCode(Node root, String s) {

        if (root.left == null && root.right == null && root.getCharacter() != null) {
            int i = 0;
            while (i < nodes.size()) {
                if (nodes.get(i).equals(root)) {
                    nodes.get(i).setCode(s);
                    break;
                }

                i++;
            }

            return;
        }

        printCode(root.left, s + "0");
        printCode(root.right, s + "1");
    }

    public static void huffman() {
        List<Node> huffmanTree = new ArrayList<>(nodes);
        Node root;
        while (huffmanTree.size() != 1) {
            Node x = new Node();
            Node y = new Node();
            Node z = new Node();
            x = huffmanTree.get(0);
            y = huffmanTree.get(1);
            z.setFrequency(x.getFrequency() + y.getFrequency());
            z.left = x;
            z.right = y;

            huffmanTree.add(z);

            huffmanTree.remove(0);
            huffmanTree.remove(0);
            Collections.sort(huffmanTree);

        }
        root = huffmanTree.get(0);
        printCode(root, "");
    }

    public static void saveCompressedFile(String fileName) throws IOException {
        FileWriter fw = new FileWriter("/Users/fetouh/Desktop/huffman/compressedFile" + fileName + ".txt");
        PrintWriter pw = new PrintWriter(fw);
        //pw.println("Character Frequency Code");
        String temp = "Character Frequency OldCode NewCode\n";
String temp2 = "";

                             
        int i = 0;
        while (i < nodes.size()) {
            temp+=nodes.get(i).getCharacter() + " " + nodes.get(i).getFrequency() + " " + nodes.get(i).getOldcode()+" "+nodes.get(i).getCode()+"\n";

         //   pw.println(nodes.get(i).getCharacter() + " " + nodes.get(i).getFrequency() + " " + nodes.get(i).getCode());

            i++;
        }
     //   pw.println("*");
temp+="***\n";
        String character;
        for (i = 0; i < toBeCompressed.length() - 1; i++) {
            character = String.valueOf(toBeCompressed.charAt(i));
            int j = 0;
            while (j < nodes.size()) {
                if (character.equals(nodes.get(j).getCharacter())) {

                   //pw.print(nodes.get(j).getCode());
                   
temp2+=nodes.get(j).getCode();
                }

                j++;
            }
        }
      //  pw.close();

      
      String s2 = "";   
char nextChar;
        System.out.println(temp2);
for( i = 0; i <= temp2.length()-8; i += 8) //this is a little tricky.  we want [0, 7], [9, 16], etc (increment index by 9 if bytes are space-delimited)
{ 
     nextChar = (char)Integer.parseInt(temp2.substring(i, i+8), 2);
     s2 += nextChar;
}
//s2+=(char)Integer.parseInt(temp2.substring(i), 2);
 //extraZeros=8-temp2.substring(i).length();
//String temp3 =  temp2.substring(i)  ;
//for(int j=0;j<extraZeros;j++)
//{          temp3+="0";    }
    //s2+=(char)Integer.parseInt(temp3,2);
  
     // pw.write(extraZeros+"\n"+temp);
     pw.write(temp);
      pw.write(s2);
      pw.close();
 
    }

  
                 
    public static void main(String[] args) throws IOException {
        String fileName = getFileName();
        int choice;
        do {
            choice = compressOrDecompress();
        } while (choice != 1 && choice != 2);

        if (choice == 1) {
           long startTime = System.nanoTime();
            countCharactersFrequency(readTextFile(fileName));
            loadArrayList();
            huffman();
            saveCompressedFile(fileName);
long endTime = System.nanoTime();
long duration = (endTime - startTime); 
            System.out.println(duration+"ns");
        } else if (choice == 2) {
           long startTime = System.nanoTime();
        Decompress d = new Decompress();
         String data = d.readCompressedFile(fileName);
      d.visualizeTree();
      d.decodeCharsToFile("decompressed", data);
            long endTime = System.nanoTime();
long duration = (endTime - startTime); 
            System.out.println(duration+"ns");
        }

    }



}
