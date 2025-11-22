import java.util.List;
import java.util.Map;

public class TestFlex {
    public static void main(String[] args){
        Flex testFlex = new Flex();

        testFlex.loadCSV("Data/Stored_Value_Transaction_by_Customer__11_39_2025-10-17_11_40_52(Stored_Value_Transaction_by_Cus).csv");

        Map<String, Map<String, List<Transaction>>> data = testFlex.getData();

        // Example: print all transactions at a location and school
        List<Transaction> list = data.get("Coop Store").get("POM");
        for(Transaction t : list){
            System.out.println(t);  // uses Transaction.toString()
        }
    }
}   
