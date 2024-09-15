import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        FileReader customFileReader = new FileReader();
        ArrayList<String> allLines = customFileReader.ReadAllLines();
        ArrayList<WeightedNode> allLineDTO = new ArrayList<>();
        Hashtable<String , WeightedNode> stations =  new Hashtable<>();
        int counter = 0;
        for (String row : allLines) {
            String[] col = row.split(",");
            WeightedNode weightedNode = new WeightedNode();
            weightedNode.StopId = col[0];
            weightedNode.StopName = col[1];
            weightedNode.ArrivalTime = col[2];
            weightedNode.StopSequence = col[3];
            weightedNode.DirectionId = col[4];
            weightedNode.RouteShortName = col[5];
            weightedNode.RouteLongName = col[6];
            weightedNode.RouteType = col[7];
            weightedNode.index = counter++;
            allLineDTO.add(weightedNode);
            if(!stations.containsKey(weightedNode.StopName)){
                stations.put(weightedNode.StopName , weightedNode);
            }
        }

        DirectedGraph directedGraph = new DirectedGraph(stations , allLineDTO);
        directedGraph.ConsturctPath();

//          directedGraph.Visualize();

        WeightedNode target = directedGraph.dijkstras("Charles de Gaulle-Etoile" , "Odéon");
//        WeightedNode target = directedGraph.BFS("Bérault" , "Château de Vincennes");

        if(target == null){
            System.out.println("no path found");
        }else{
            directedGraph.VisualizePath(target);
        }
        int a = 10;

    }
}