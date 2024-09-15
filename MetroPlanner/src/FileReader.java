import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    public ArrayList<String> ReadAllLines(){
        ArrayList<String> allLines  = new ArrayList<>();
        try{
            String filePath = "Paris_RER_Metro_v2.csv";
            allLines = (ArrayList<String>)Files.readAllLines(Paths.get(filePath));
            allLines.remove(0);
        }catch (IOException e){
            e.printStackTrace();
        }
        return  allLines;
    }
}
