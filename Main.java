package com.company;
import java.util.*;


class Vertex<V>{
    private V value;
    private Map<Vertex<V>, Double> adjVertices = new HashMap<>();

    public Vertex(V value) {
        this.value = value;
    }

    public void insertAdjVertex(Vertex<V> direction, double value){
        adjVertices.put(direction, value);
    }

    public Map<Vertex<V>, Double> getAdjVertices() {
        return adjVertices;
    }

    public V getValue() {
        return value;
    }
}

class BFS<T> extends Search<T>{

    public BFS(WGraph<T> wGraph, T root) {
        super(new Vertex<T>(root));
        bfs(wGraph, root);
    }

    private void bfs(WGraph<T> wGraph, T cur) {
        markedVertices.add(new Vertex<T>(cur));
        Queue<Vertex<T>> queueOfVertices = new LinkedList<>();
        queueOfVertices.add(new Vertex<T>(cur));
        while (!queueOfVertices.isEmpty()) {
            Vertex<T> v = queueOfVertices.remove();
            for(Map.Entry<Vertex<T>, Double> entry : wGraph.map.get(wGraph.map.indexOf(v)).getAdjVertices().entrySet()){
                Vertex<T> vertex = entry.getKey();
                if (!markedVertices.contains(vertex)) {
                    markedVertices.add(vertex);
                    EdgeFromTo.put(vertex, v);
                    queueOfVertices.add(vertex);
                }
            }
        }
    }
}

class DFS<T> extends Search<T> {

    public DFS(WGraph<T> wGraph, T root) {
        super(new com.company.Vertex<T>(root));
        dfs(wGraph, root);
    }

    private void dfs(WGraph<T> wGraph, T cur) {
        markedVertices.add(new Vertex<T>(cur));
        quantity++;

        Vertex<T>currentVertex = new Vertex<T>(cur);
        for(Map.Entry<Vertex<T>, Double> ent : wGraph.map.get(wGraph.map.indexOf(currentVertex)).getAdjVertices().entrySet()){
            Vertex<T> vertex = ent.getKey();
            if (!markedVertices.contains(vertex)) {
                EdgeFromTo.put(vertex, new Vertex<T>(cur));
                dfs(wGraph, vertex.getValue());
            }
        }
    }

}

class Search<T> {
    protected int quantity;
    protected Set<Vertex<T>> markedVertices;
    protected Map<Vertex<T>, Vertex<T>> EdgeFromTo;
    protected final Vertex<T> root;

    public Search(Vertex<T> root) {
        this.root = root;
        markedVertices = new HashSet<>();
        EdgeFromTo = new HashMap<>();
    }

    public boolean pathExists(Vertex<T> v) {
        return markedVertices.contains(v);
    }

    public Iterable<Vertex<T>> pathToVertex(T vertex) {
        if (!pathExists(new Vertex<T>(vertex))) return null;
        LinkedList<Vertex<T>> list = new LinkedList<>();
        for (Vertex<T> vi = new Vertex<T>(vertex); vi != root; vi = EdgeFromTo.get(vi)) {
            list.push(vi);
        }
        list.push(root);

        return list;
    }

    public int getQuantity() {
        return quantity;
    }
}

class WGraph<T> {//Weighted Graph
    private final boolean undirected;
    public ArrayList<Vertex<T>> map = new ArrayList<>();
    public WGraph() {
        this.undirected = true;
    }

    public WGraph(boolean undirected) {
        this.undirected = undirected;
    }

    public void insertVertex(T v) {
        map.add(new Vertex<T>(v));
    }

    public void insertEdge(T v1, T v2, double value) {
        if (!isVertexExists(v1))
            insertVertex(v1);

        if (!isVertexExists(v2))
            insertVertex(v2);

        if (isEdgeExists(v1, v2)
                || v1.equals(v2))
            return;

        if(!map.contains(new Vertex<T>(v1))){
            return;
        }

        map.get(map.indexOf(new Vertex<T>(v1))).insertAdjVertex(new Vertex<T>(v2), value);


        if (undirected)
            map.get(map.indexOf(new Vertex<T>(v2))).insertAdjVertex(new Vertex<T>(v1), value);
    }

    public int getNumberOfVertices() {
        return map.size();
    }

    public int getNumberOfEdges() {
        int number = 0;

        for(Vertex<T> to : map){
            number += to.getAdjVertices().size();
        }


        if (undirected) number /= 2;

        return number;
    }


    public boolean isVertexExists(T vertex) {
        return map.contains(new Vertex<T>(vertex));
    }

    public boolean isEdgeExists(T v1, T v2) {
        if (!isVertexExists(v1))
            return false;

        return map.get(map.indexOf(new Vertex<T>(v1))).getAdjVertices().containsKey(new Vertex<T>(v2));
    } }

