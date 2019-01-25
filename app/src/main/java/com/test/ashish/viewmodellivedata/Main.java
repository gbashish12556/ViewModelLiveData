package com.test.ashish.viewmodellivedata;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Main {
    public static Map<String,Integer> processData(ArrayList<String> array) {

        HashMap<String,String> storeData = new HashMap<>();
        HashMap<String,Integer> retVal = new HashMap<>();
        for(int i=0;i<array.size();i++){
             String[] element = array.get(i).split(", ");
             if(retVal.containsKey(element[2].trim())){
                String[] val = storeData.get(element[2].trim()).split(",");
                if(Integer.parseInt(val[1])<Integer.parseInt(element[3])){
                    storeData.put(element[2].trim(),String.valueOf(element[0])+","+String.valueOf(element[4]));
                }
             }else{
                 storeData.put(element[2].trim(),String.valueOf(element[0])+","+String.valueOf(element[4]));
             }
        }
        Iterator<Map.Entry<String, String>> it = storeData.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> pair = (Map.Entry<String, String>) it.next();
            String[] data = pair.getValue().split(",");
            retVal.put(pair.getKey(),Integer.parseInt(data[0]));
        }
        return retVal;
    }

    public static void main (String[] args) {
        ArrayList<String> inputData = new ArrayList<String>();
        String line;
        try {
            // Scanner in = new Scanner(new BufferedReader(new FileReader("input.txt")));
            // while(in.hasNextLine())
            //     inputData.add(in.nextLine());
            inputData.add("22, Rajan Anand, Engineering, 1600000");
            inputData.add("23, Swati Patil, Testing, 800000");
            inputData.add("27, Vijay Chawda, Engineering, 800000");
            inputData.add("29, Basant Mahapatra, Engineering, 600000");
            inputData.add("32, Ajay Patel, Testing, 350000");
            inputData.add("34, Swaraj Birla, Testing, 350000");


            Map<String,Integer> retVal = processData(inputData);
            PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter("output.txt")));
            for(Map.Entry<String,Integer> e: retVal.entrySet())
                output.println(e.getKey() + ": " + e.getValue());
            output.close();
        } catch (IOException e) {
            System.out.println("IO error in input.txt or output.txt");
        }
    }
}