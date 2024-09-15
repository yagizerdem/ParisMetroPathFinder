import java.util.ArrayList;
import java.util.Hashtable;

public class WeightedNode implements Comparable<WeightedNode> {
    Hashtable<WeightedNode , Integer> weightMap = new Hashtable<>();
    ArrayList<WeightedNode> neighbors = new ArrayList<>();
    boolean isVisited = false;
    WeightedNode parent = null;
    int distance = Integer.MAX_VALUE;
    String StopId;
    String StopName;
    String ArrivalTime;
    String StopSequence;
    String DirectionId;
    String RouteShortName;
    String RouteLongName;
    String RouteType;

    int index;
    public  WeightedNode(){

    }
    public WeightedNode(String StopId , String StopName , String ArrivalTime
            , String  StopSequence , String DirectionId , String RouteShortName,
                        String RouteLongName , String RouteType){
        this.StopId = StopId;
        this.StopName = StopName;
        this.ArrivalTime =ArrivalTime;
        this.StopSequence = StopSequence;
        this.DirectionId = DirectionId;
        this.RouteShortName = RouteShortName;
        this.RouteLongName = RouteLongName;
        this.RouteType = RouteType;
    }


    public static  int ConvertToAscii(String word){
        int sum = 0;
        char[] arr = word.toCharArray();
        for(char c : arr){
            sum += c;
        }
        return  sum;
    }

    @Override
    public boolean equals(Object o) {
        WeightedNode other = (WeightedNode)o;
        return this.StopId.equals((other.StopId));
    }
    @Override
    public int hashCode() {
        return WeightedNode.ConvertToAscii(this.StopId);
    }
    @Override
    public String toString() {
        return this.StopName;
    }

    public  void ClearFix(){
        this.isVisited = false;
        this.distance = Integer.MAX_VALUE;
        this.parent = null;
    }

    @Override
    public int compareTo(WeightedNode other) {
        return this.distance - other.distance;
    }
}
