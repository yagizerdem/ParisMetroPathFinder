import java.security.PublicKey;
import java.util.*;
import java.util.concurrent.RecursiveTask;

public class DirectedGraph {
    Hashtable<String, WeightedNode> stations = new Hashtable<>();
    ArrayList<WeightedNode> allNodeDTO = new ArrayList<>();


    public DirectedGraph(Hashtable<String, WeightedNode> stations, ArrayList<WeightedNode> allNodeDTO) {
        this.stations = stations;
        this.allNodeDTO = allNodeDTO;
    }

    public int GetSize() {
        return stations.size();
    }

    public void ConsturctPath() {
        this.ConstructTransportEdges(0);
    }

    public void AddWeightedEdge(String firstStationName, String secondStationName) {
        WeightedNode first = this.stations.get(firstStationName);
        WeightedNode second = this.stations.get(secondStationName);
        Integer weight = Math.abs(Integer.valueOf(first.ArrivalTime) - Integer.valueOf(second.ArrivalTime));
        // create weighted edge
        first.neighbors.add(second);
        first.weightMap.put(second, weight);
    }

    private void ConstructTransportEdges(int startIndex) {
        if (startIndex >= allNodeDTO.size() - 1) return;
        WeightedNode current = allNodeDTO.get(startIndex), prev = null;
        String directionId = current.DirectionId;
        String routeShortName = current.RouteShortName;
        while (directionId.equals(current.DirectionId) && routeShortName.equals(current.RouteShortName)) {
            if (prev != null) {
                WeightedNode currentFromStation = stations.get(current.StopName);
                WeightedNode prevFromStation = stations.get(prev.StopName);
                if (!currentFromStation.neighbors.contains(prevFromStation)) {
                    AddWeightedEdge(currentFromStation.StopName, prevFromStation.StopName);
                }
            }
            prev = current;
            if (current.index + 1 >= allNodeDTO.size()) break;
            current = allNodeDTO.get(current.index + 1);
        }
        ConstructTransportEdges(current.index);
    }

    public WeightedNode GetStationByName(String stopName) {
        return  this.stations.get(stopName);
    }

    public void Visualize() {
        Enumeration<String> e = this.stations.keys();
        while (e.hasMoreElements()) {
            String key = e.nextElement();
            WeightedNode current = this.stations.get(key);
            System.out.print("Starting station " + current.StopName + "(" + current.StopId + ")" + " : ");
            for (WeightedNode neighbor : current.neighbors) {
                System.out.print(neighbor + "(" + neighbor.StopId + ")" + " | ");
            }
            System.out.println();
        }
    }

    public WeightedNode BFS(String startStation , String destinationStation){
        this.ClearAllNodeProperties();
        WeightedNode startNode = this.stations.get(startStation);
        WeightedNode dest = this.stations.get(destinationStation);
        Queue<WeightedNode> queue = new LinkedList<>();
        queue.add(startNode);
        while (!queue.isEmpty()){
            WeightedNode current = queue.remove();
            current.isVisited = true;
            if(current.StopName.equals(destinationStation)){
                return  current;
            }
            for(WeightedNode neighbor : current.neighbors){
                if(!neighbor.isVisited && !queue.contains(neighbor)){
                    neighbor.parent = current;
                    queue.add(neighbor);
                }
            }
        }
        return  null;
    }
    // clear proerties realted to path finidng
    private void ClearAllNodeProperties(){
        Enumeration<String> iterator = this.stations.keys();
        while (iterator.hasMoreElements()) {
            String key = iterator.nextElement();
            WeightedNode node = this.stations.get(key);
            node.ClearFix();
        }
    }

    public void VisualizePath(WeightedNode node){
        ArrayList<WeightedNode> list = new ArrayList<>();
        this.VisualizePathHelper(node , list);
        StringBuilder builder = new StringBuilder();
        WeightedNode current = null , prev = null;
        int lineCounter = 1;
        for(int i = 0; i < list.size() ; i++){
            prev = current;
            current = list.get(i);
            if(prev == null){
                builder.append("Line " + (lineCounter++) +": \n ");
                builder.append(current.StopName + "->");
            }
            else if(prev != null && !prev.RouteShortName.equals(current.RouteShortName)){
                builder.setLength(builder.length()-2);
                builder.append("\n Line " + (lineCounter++) +": \n ");
                builder.append(current.StopName+"->");
            }
            else{
                builder.append(current.StopName+"->");
            }
        }
        builder.setLength(builder.length()-2);
        System.out.println(builder);
    }
    private void VisualizePathHelper(WeightedNode node , ArrayList<WeightedNode> list){
        if(node == null){
            return;
        }
        VisualizePathHelper(node.parent , list);
        list.add(node);
    }

    public  WeightedNode dijkstras(String startStation , String destinationStation){
        this.ClearAllNodeProperties();
        WeightedNode start = this.stations.get(startStation);
        start.distance = 0;
        PriorityQueue<WeightedNode> queue = new PriorityQueue<>();
        queue.addAll(stations.values());
        while (!queue.isEmpty()){
            WeightedNode current = queue.remove();
            current.isVisited = true;
            if(current.StopName.equals(destinationStation)){
                return  current;
            }
            // visit
            for(WeightedNode neighbor : current.neighbors){
                if(queue.contains(neighbor)){
                    if(neighbor.distance > current.distance + current.weightMap.get(neighbor)){
                        neighbor.distance = current.distance + current.weightMap.get(neighbor);
                        neighbor.parent = current;
                        queue.remove(neighbor);
                        queue.add(neighbor);
                    }
                }
            }
        }
        return  null;
    }

}
