package lab13.baitapthem.luyentap1;

import java.util.ArrayList;
import java.util.List;

public class AdjList {
    private int numVertices;
    private int numEdges;
    private List<Edge> edges;

    public AdjList(int numVertices, int numEdges, List<Edge> edges) {
        this.numVertices = numVertices;
        this.numEdges = numEdges;
        this.edges = edges;
    }

    public int getNumVertices() {
        return numVertices;
    }

    public void setNumVertices(int numVertices) {
        this.numVertices = numVertices;
    }

    public int getNumEdges() {
        return numEdges;
    }

    public void setNumEdges(int numEdges) {
        this.numEdges = numEdges;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    private List<Object> getVertices() {
        List<Object> vertices = new ArrayList<>();
        for (int i = 0; i < edges.size(); i++) {
            if (!vertices.contains(edges.get(i).getStartVertex())) {
                vertices.add(edges.get(i).getStartVertex());
            }

            if (!vertices.contains(edges.get(i).getEndVertex())) {
                vertices.add(edges.get(i).getEndVertex());
            }
        }
        vertices.sort(null);
        return vertices;
    }

    public List<List<Object>> getAdjList() {
        List<Object> vertices = getVertices();
        List<List<Object>> adjList = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {
            List<Object> tempList = new ArrayList<>();
            for (int j = 0; j < edges.size(); j++) {
                Object start = edges.get(j).getStartVertex();
                Object end = edges.get(j).getEndVertex();
                if (vertices.get(i).equals(start) && !tempList.contains(end)) {
                    tempList.add(end);
                }

                if (vertices.get(i).equals(end) && !tempList.contains(start)) {
                    tempList.add(start);
                }
            }
            adjList.add(tempList);
            adjList.get(i).sort(null);
        }
        return adjList;
    }

    public static void main(String[] args) {
        AdjList adjList = new AdjList(5, 7, new ArrayList<>());

        adjList.getEdges().add(new Edge(0, 1));
        adjList.getEdges().add(new Edge(0, 4));
        adjList.getEdges().add(new Edge(4, 1));
        adjList.getEdges().add(new Edge(4, 3));
        adjList.getEdges().add(new Edge(1, 3));
        adjList.getEdges().add(new Edge(1, 2));
        adjList.getEdges().add(new Edge(3, 2));

        System.out.println(adjList.getAdjList());

        AdjList adjList2 = new AdjList(4, 3, new ArrayList<>());
        adjList2.getEdges().add(new Edge(0, 3));
        adjList2.getEdges().add(new Edge(0, 2));
        adjList2.getEdges().add(new Edge(2, 1));

        System.out.println(adjList2.getAdjList());
    }
}
