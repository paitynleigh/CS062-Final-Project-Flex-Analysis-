import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;


public class Flex {
    private Map<String, Map<String, List<Transaction>>> data;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy HH:mm");

    public Flex(){
        data = new HashMap<>();

    }

    public void rowParse(String line){
        //Split cols in csv
        String[] cols = line.split(",");

        //Parse each row into column objects
        String date = cols[0].trim();
        String location = cols[1].trim();
        double amount = Double.parseDouble(cols[3].trim());
        String school = cols[8].trim();

        //Format Date Correctly
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

        // Add new Location or update map of existing location
        if(!data.containsKey(location)){
            data.put(location, new HashMap<String, List<Transaction>>());
        }

        Map<String, List<Transaction>> schoolMap = data.get(location);

        // Add new School or update map of existing school
        if(!schoolMap.containsKey(school)){
            schoolMap.put(school, new ArrayList<>());
        }

        List<Transaction> list = schoolMap.get(school);

        //Create Transaction
        Transaction t = new Transaction(dateTime, amount, location, school);
        list.add(t);
    }

    public void loadCSV(String filename){
        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
            String line;

            while((line = br.readLine()) != null){
                if(!line.trim().isEmpty()){
                    rowParse(line);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /* 
    *
    * Getter Method for data
    */
     public Map<String, Map<String, List<Transaction>>> getData() {
        return data;
    }

}

