import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;



public class Flex {
    String file = "Data/Stored_Value_Transaction_by_Customer__11_39_2025-10-17_11_40_52(Stored_Value_Transaction_by_Cus).csv";
    String line;

    DateTimeFormatter dater = DateTimeFormatter.ofPattern("MM/dd/yyyy");


    try(BufferedReader br = new BufferedReader(new FileReader(file))){
        br.readLine();
        while((line = br.readline()) != null){

        }
    }
}
