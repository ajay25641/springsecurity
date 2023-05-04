package org.example;

/**
 * Hello world!
 *
 */

import java.util.*;
import java.io.*;
public class App 
{
    public static void main( String[] args ) throws IOException {

        String path="C:\\Users\\ajay1.kumar\\Desktop\\easyBankSql.txt";

        BufferedReader br=new BufferedReader(new FileReader(path));


        ArrayList<String>arr=new ArrayList<String>();

        String line=br.readLine();

        while(line!=null){
            line=line
                    .replaceAll("`","")
                    .replaceAll("create_dt","created_At")
                    .replaceAll("CURDATE\\(\\)","CURRENT_DATE")
                    .replaceAll("mobile_number","mobile_num")
                    .replaceAll("pwd","password")
                    .replaceAll("date","TIMESTAMP")
                    .replaceAll("1865764534","2780")
                    .replaceAll("INTERVAL 30 DAY","30");
            arr.add(line);

            line=br.readLine();

        }

        path="C:\\Users\\ajay1.kumar\\Desktop\\easyBankSql_updated.txt";

        FileWriter fr=new FileWriter(path);

        for(String str:arr){
            fr.write(str+System.lineSeparator());
        }

        fr.close();
        br.close();

    }
}
