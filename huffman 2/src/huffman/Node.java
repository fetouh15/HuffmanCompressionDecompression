/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffman;

/**
 *
 * @author fetouh
 */
public class Node implements Comparable {
    
    private String code;
    private String oldcode;

    public String getOldcode() {
        return oldcode;
    }

    public void setOldcode(String oldcode) {
        this.oldcode = oldcode;
    }
    
    private int frequency;
private String character;
Node left,right;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }


   public int compareTo(Node comparestu) {
        int compareage=((Node)comparestu).getFrequency();
        /* For Ascending order*/
        return this.frequency-compareage;

        /* For Descending order do like this */
        //return compareage-this.studentage;
    }    

    @Override
    public int compareTo(Object comparestu) {
       int compareage=((Node)comparestu).getFrequency();
        /* For Ascending order*/
        return this.frequency-compareage;}

  
   
    
    
    
}
