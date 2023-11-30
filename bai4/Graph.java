package lab13.baitap.bai4;

import java.util.*;

class Graph {

    class Edge {
        int startVertex;
        int endVertex;
        int weight;

        public Edge(int startVertex, int endVertex, int weight) {
            this.startVertex = startVertex;
            this.endVertex = endVertex;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "[" + startVertex + ", " + endVertex + ", " + weight + ']';
        }
    }
    private int numVertices;
    private List<List<Edge>> adjacencyList;

    public Graph(int numVertices) {
        this.numVertices = numVertices;
        this.adjacencyList = new ArrayList<>(numVertices);
        for (int i = 0; i < numVertices; i++) {
            this.adjacencyList.add(new ArrayList<>());
        }
    }

    public void addEdge(int source, int destination) {
        this.adjacencyList.get(source).add(new Edge(source, destination, 0));
    }

    public void addWeightedEdge(int source, int destination, int weight) {
        // Thêm thông tin về trọng số vào danh sách kề
        this.adjacencyList.get(source).add(new Edge(source, destination, weight));
    }

    public List<Integer> dfs(int startVertex) {
        List<Integer> result = new ArrayList<>();
        boolean[] visited = new boolean[numVertices];
        dfsHelper(startVertex, visited, result);
        return result;
    }

    private void dfsHelper(int vertex, boolean[] visited, List<Integer> result) {
        visited[vertex] = true;
        result.add(vertex);
        for (Edge neighbor : adjacencyList.get(vertex)) {
            if (!visited[neighbor.endVertex]) {
                dfsHelper(neighbor.endVertex, visited, result);
            }
        }
    }

    public List<Integer> bfs(int startVertex) {
        List<Integer> result = new ArrayList<>();
        boolean[] visited = new boolean[numVertices];
        Queue<Integer> queue = new LinkedList<>();
        visited[startVertex] = true;
        queue.add(startVertex);

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();
            result.add(currentVertex);

            for (Edge neighbor : adjacencyList.get(currentVertex)) {
                if (!visited[neighbor.endVertex]) {
                    visited[neighbor.endVertex] = true;
                    queue.add(neighbor.endVertex);
                }
            }
        }

        return result;
    }

    public List<Integer> dijkstra(int startVertex, int endVertex) {
        List<Integer> path = new ArrayList<>();
        PriorityQueue<Edge> minHeap = new PriorityQueue<>(Comparator.comparingInt(o -> o.weight));
        int[] distances = new int[numVertices];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[startVertex] = 0;

        minHeap.offer(new Edge(startVertex, startVertex, 0));

        while (!minHeap.isEmpty()) {
            Edge current = minHeap.poll();
            int currentVertex = current.endVertex;
            int currentDistance = current.weight;

            if (currentVertex == endVertex) {
                // Đã đến đỉnh đích, thoát khỏi vòng lặp
                break;
            }

            if (currentDistance > distances[currentVertex]) {
                // Nếu khoảng cách hiện tại lớn hơn khoảng cách đã biết, bỏ qua
                continue;
            }

            for (Edge neighbor : adjacencyList.get(currentVertex)) {
                int newDistance = currentDistance + neighbor.weight;
                if (newDistance < distances[neighbor.endVertex]) {
                    // Cập nhật khoảng cách và thêm đỉnh vào hàng đợi
                    distances[neighbor.endVertex] = newDistance;
                    minHeap.offer(new Edge(currentVertex, neighbor.endVertex, newDistance));
                }
            }
        }

        // Tạo đường đi từ đỉnh xuất phát đến đỉnh đích
        int currentVertex = endVertex;
        while (currentVertex != startVertex) {
            path.add(currentVertex);
            for (Edge neighbor : adjacencyList.get(currentVertex)) {
                if (distances[currentVertex] == distances[neighbor.endVertex] + neighbor.weight) {
                    currentVertex = neighbor.endVertex;
                    break;
                }
            }
        }
        path.add(startVertex);
        Collections.reverse(path);

        return path;
    }

    public List<Integer> hamiltonianCycle() {
        boolean[] visited = new boolean[numVertices];
        List<Integer> path = new ArrayList<>();

        // Bắt đầu từ mọi đỉnh để kiểm tra xem có chu trình Hamilton nào không
        for (int startVertex = 0; startVertex < numVertices; startVertex++) {
            visited[startVertex] = true;
            path.add(startVertex);

            // Nếu từ đỉnh này có thể tạo chu trình Hamilton, trả về
            if (hamiltonianCycleUtil(startVertex, visited, path)) {
                return path;
            }

            // Nếu không, quay lui bằng cách hủy bỏ thay đổi
            visited[startVertex] = false;
            path.clear();
        }

        // Nếu không có chu trình Hamilton, trả về danh sách rỗng
        return new ArrayList<>();
    }

    private boolean hamiltonianCycleUtil(int vertex, boolean[] visited, List<Integer> path) {
        // Nếu tất cả các đỉnh đã được thăm, kiểm tra xem có tạo chu trình Hamilton không
        if (path.size() == numVertices) {
            return true;
        }

        for (Edge neighbor : adjacencyList.get(vertex)) {
            if (!visited[neighbor.endVertex]) {
                visited[neighbor.endVertex] = true;
                path.add(neighbor.endVertex);

                // Kiểm tra nếu tiếp tục từ đỉnh hàng xóm có thể tạo ra chu trình Hamilton
                if (hamiltonianCycleUtil(neighbor.endVertex, visited, path)) {
                    return true;
                }

                // Nếu không, quay lui bằng cách hủy bỏ thay đổi
                visited[neighbor.endVertex] = false;
                path.remove(path.size() - 1);
            }
        }

        return false;
    }


    public List<Integer> eulerianCycle() {
        // TODO: Cài đặt thuật toán tìm chu trình Euler
        // ... (Đoạn mã Eulerian Cycle)

        return new ArrayList<>(); // Thay thế bằng chu trình Euler tìm được
    }

    public List<int[]> minimumSpanningTree() {
        List<int[]> mstEdges = new ArrayList<>();
        PriorityQueue<Edge> minHeap = new PriorityQueue<>(Comparator.comparingInt(o -> o.weight));
        boolean[] visited = new boolean[numVertices];

        // Chọn một đỉnh bất kỳ làm đỉnh xuất phát
        int startVertex = 0;
        visited[startVertex] = true;

        // Thêm tất cả các cạnh kề của đỉnh xuất phát vào hàng đợi ưu tiên
        for (Edge neighbor : adjacencyList.get(startVertex)) {
            minHeap.offer(neighbor);
        }

        while (!minHeap.isEmpty()) {
            Edge edge = minHeap.poll();
            int currentVertex = edge.endVertex;

            if (visited[currentVertex]) {
                continue;
            }

            visited[currentVertex] = true;
            mstEdges.add(new int[]{edge.startVertex, currentVertex, edge.weight});

            // Thêm tất cả các cạnh kề của đỉnh mới được thêm vào hàng đợi ưu tiên
            for (Edge neighbor : adjacencyList.get(currentVertex)) {
                if (!visited[neighbor.endVertex]) {
                    minHeap.offer(neighbor);
                }
            }
        }

        return mstEdges;
    }

    public static void main(String[] args) {
        Graph graph = new Graph(5);

        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 3);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 0);

        graph.addWeightedEdge(0, 1, 2);
        graph.addWeightedEdge(0, 2, 1);
        graph.addWeightedEdge(1, 3, 4);
        graph.addWeightedEdge(2, 3, 2);
        graph.addWeightedEdge(3, 4, 3);

        System.out.println("DFS traversal: " + graph.dfs(0));
        System.out.println("BFS traversal: " + graph.bfs(0));

        System.out.println("Dijkstra Shortest Path: " + graph.dijkstra(0, 4));
        System.out.println("Hamiltonian Cycle: " + graph.hamiltonianCycle());
        System.out.println("Eulerian Cycle: " + graph.eulerianCycle());
        System.out.println("Minimum Spanning Tree: " + graph.minimumSpanningTree());
    }
}