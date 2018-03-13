package org.iqra.operationsapp.util;

public class FindMinimalSubstring {
	
	public static void main(String args[]){

        String mainString = "My name is Frank";

        String paternString = "My name is Frank";

        String resultString = null;

        int max = 0;
        int min =0;
        int newStartIndex = 0; 
        int index=-1;
        String intermediateResult = mainString;

        while(true){
            
            intermediateResult = intermediateResult.substring(newStartIndex);
            
            for(int i=0; i< paternString.length();i++){

            		index = intermediateResult.indexOf(paternString.charAt(i));
                if(index == -1){
                		
                    break;
                }
                if(i==0){
                    min = intermediateResult.indexOf(paternString.charAt(i));
                    max = min;
                }
                if(min > index){
                    min = index;
                }
                if(max < index){
                    max = index;
                }
            }
            
            if(index == -1){
                break;
            }
            newStartIndex = max;
            
            if(resultString == null || resultString.length() > intermediateResult.substring(min,max+1).length()) {
            		resultString = intermediateResult.substring(min,max+1);
            }
            
        }
        
        if(resultString!=null)
        		System.out.println(resultString);
        else
        		System.out.println("Substring not present in main String");

    }

}
