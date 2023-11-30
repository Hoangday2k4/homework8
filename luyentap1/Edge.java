package lab13.baitapthem.luyentap1;

public class Edge {
    private Object startVertex;
    private Object endVertex;

    public Edge(Object startVertex, Object endVertex) {
        this.startVertex = startVertex;
        this.endVertex = endVertex;
    }

    public Object getStartVertex() {
        return startVertex;
    }

    public void setStartVertex(Object startVertex) {
        this.startVertex = startVertex;
    }

    public Object getEndVertex() {
        return endVertex;
    }

    public void setEndVertex(Object endVertex) {
        this.endVertex = endVertex;
    }

    @Override
    public String toString() {
        return "(" + startVertex + "," + endVertex +
                ')';
    }
}
