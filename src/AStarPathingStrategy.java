import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy implements PathingStrategy
{
    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        List<Point> path = new LinkedList<Point>();
        Map<Point,Node> openMap = new HashMap<>();
        Map<Point,Node> closeMap = new HashMap<>();
        PriorityQueue<Node> openList= new PriorityQueue<>(Comparator.comparingInt(Node::getFvalue));
        List<Node> pathway = new ArrayList<>();//keep track

        //add your starting point
        Node first= new Node(0+heuristic(start,end),0,start,null);
        openList.add(first);
        Node current ;
        //loop through the list
        while(!openList.isEmpty()){
            //get node with lowest fvalue
            current=openList.poll();
            //if you have reached your ind point print the path
            if(withinReach.test(current.getCur_pos(),end)){
                return pathTaken(path,current);

            }
            //find all the nighbors to that point
            List<Point> neighbors = potentialNeighbors.apply(current.getCur_pos())
                    .filter(canPassThrough)
                    .filter(pt ->!pt.equals(end)&& !pt.equals(start) ).collect(Collectors.toList());//!openMap.containsKey(pt)
            for(Point valid: neighbors){
                //check if neighbour is not already checked
                if(!closeMap.containsKey(valid)){
                    //update its g value to a new gvalue becuase we will try a differne path
                    int tempG = current.getGvalue() +1;
                    //check if node is not in the opnemap add it and check ig g value is better
                    if(!openMap.containsKey(valid) || tempG< openMap.get(valid).getGvalue()){
                        //reached it already

                        Node neighNode = new Node(heuristic(valid,end)+tempG,tempG,valid,current);
                        openList.add(neighNode);
                        openList.remove(openMap.get(valid)); // remove this because no longer smallest fvalue
                        openMap.put(valid,neighNode);//update node
                        pathway.add(neighNode);

                    }
                }
                openList.remove(current);//need to remove everything from the list
                closeMap.put(current.getCur_pos(),current);//points visited
            }

        }

        return path;
    }
    public List<Point> pathTaken(List<Point> path,Node node){
        path.add(node.getCur_pos());
        Node temp = node;
        while(temp.getPrevious() !=null){
            path.add(temp.getCur_pos());
            temp= temp.getPrevious();
        }
        return path;
    }
    public int heuristic(Point a, Point b){
        return  Math.abs(b.x-a.x) + Math.abs(b.y-a.y);
    }
}
