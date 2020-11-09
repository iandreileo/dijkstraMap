import javafx.util.Pair;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class HartaGraf {

    static class Edge {
        private String source;
        private String destination;
        private int weight;
        private int costRestrictii = 0;
        private int limitaGabarit = 0;

        public int getCostRestrictii() {
            return costRestrictii;
        }

        public void setCostRestrictii(int costRestrictii) {
            this.costRestrictii = costRestrictii;
        }

        public int getLimitaGabarit() {
            return limitaGabarit;
        }

        public void setLimitaGabarit(int limitaGabarit) {
            this.limitaGabarit = limitaGabarit;
        }

        public Edge(String source, String destination, int weight, int limitaGabarit) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
            this.limitaGabarit = limitaGabarit;
        }
    }

    static class Harta {
        int vertices;
        LinkedList<Edge> [] adjacencylist;

        Harta(int vertices) {
            this.vertices = vertices;
            adjacencylist = new LinkedList[vertices];
            //initializam lista de adiacenta
            for (int i = 0; i < vertices ; i++) {
                adjacencylist[i] = new LinkedList<>();
            }
        }

        public void addStreet(String start, String end, int cost, int size) {
            //creem o strada
            Edge edge = new Edge(start, end, cost, size);
            //o adaugam
            adjacencylist[Integer.parseInt(start.substring(1))].addFirst(edge);
        }
        public void addRestriction(String type, String start, String end, int cost){
            LinkedList<Edge> list = adjacencylist[Integer.parseInt(start.substring(1))];
            for (Edge edge : list) {
                if (edge.destination.equals(end)) {
                    //setam restrictia pentru strada
                    edge.setCostRestrictii(edge.getCostRestrictii() + cost);
                }
            }
        }
        public void drive(Proprietate vehicle, String start, String end){
            //parsam sursa
            int sourceVertex = Integer.parseInt(start.substring(1));
            //parsam destinatia
            int destinationVertex = Integer.parseInt(end.substring(1));

            boolean[] SPT = new boolean[vertices];
            //cream un vector de dsitante
            int [] distance = new int[vertices];

            int [] parentVertex = new int[vertices];

            //parintele v. soursa va fi -1
            parentVertex[sourceVertex] = -1;

            //Initializam distantele cu int maxim
            for (int i = 0; i <vertices ; i++) {
                distance[i] = Integer.MAX_VALUE;
            }

            //initializam coada de prioritati
            PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>(vertices, new Comparator<Pair<Integer, Integer>>() {
                @Override
                public int compare(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) {
                    //sortam dupa distanta
                    int key1 = p1.getKey();
                    int key2 = p2.getKey();
                    return key1-key2;
                }
            });

            //cream perechea pentru sursa
            distance[sourceVertex] = 0;
            Pair<Integer, Integer> p0 = new Pair<>(distance[sourceVertex],sourceVertex);

            //o adaugam in coada
            pq.offer(p0);

            //cat timp coada nu este goala
            while(!pq.isEmpty()){
                //scoatem minimul
                Pair<Integer, Integer> extractedPair = pq.poll();

                //extragem strada
                int extractedVertex = extractedPair.getValue();

                if(!SPT[extractedVertex]) {
                    SPT[extractedVertex] = true;

                    //trecem prin toate adiacentele si le updatam key-ul
                    LinkedList<Edge> list = adjacencylist[extractedVertex];
                    for (int i = 0; i < list.size(); i++) {
                        Edge edge = list.get(i);
                        int destination = Integer.parseInt(edge.destination.substring(1));
                        //daca respectam conditiile de gabarit adaugam
                        if (!SPT[destination] && vehicle.getGabarit() <= edge.getLimitaGabarit()) {

                            int newKey;
                            if(distance[extractedVertex] == Integer.MAX_VALUE){
                                newKey =  vehicle.getCost() * edge.weight + edge.getCostRestrictii();
                            } else {
                                newKey =  distance[extractedVertex] + vehicle.getCost() * edge.weight + edge.getCostRestrictii();
                            }

                            int currentKey = distance[destination];
                            if(currentKey > newKey){
                                Pair<Integer, Integer> p = new Pair<>(newKey, destination);
                                pq.offer(p);
                                distance[destination] = newKey ;
                                parentVertex[destination] = extractedVertex;
                            }
                        }
                    }
                }
            }
            //setam toate distantele neprelucrate cu -1
            for(int i = 0; i < distance.length; i++){
                if(distance[i] == Integer.MAX_VALUE){
                    distance[i] = -1;
                }
            }
            //printam drumul
            printPathUtil(parentVertex, destinationVertex, sourceVertex);

            //printam costul drumului
            System.out.print(distance[destinationVertex] != -1 ? distance[destinationVertex] : "null");
            System.out.println();
        }

        public void printPathUtil(int parent[], int destination, int sourceVertex){

            //conditia de iesire din recursivitate
            if(parent[destination] == -1) {
                System.out.print("P" + sourceVertex + " ");
                return;
            }

            //apelam recursiv functia de afisare a drumului
            printPathUtil(parent, parent[destination], sourceVertex);
            System.out.print("P" + destination + " ");
        }

    }
}