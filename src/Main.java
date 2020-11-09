import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        //initializam scanner
        File inputFile = new File("map.in");
        Scanner scanner = new Scanner(inputFile);

        //citim numarul de strazi si de noduri
        int numberOfStreets = scanner.nextInt();
        int numberOfPoints = scanner.nextInt();

        //initializam o harta
        HartaGraf.Harta map = new HartaGraf.Harta(numberOfPoints);
        while(numberOfStreets != 0){
            String srcPoint = scanner.next();
            String destPoint = scanner.next();
            int speedCost = scanner.nextInt();
            int gabaritLimit = scanner.nextInt();
            map.addStreet(srcPoint,destPoint,speedCost,gabaritLimit);
            numberOfStreets--;
        }


        //citim pana la sfarsitul fisierului
        String current = scanner.nextLine();
        while(current != null){
            //prelucram comenzile
            String command = current.split(" ")[0];
            if(command.equals("accident") || command.equals("trafic") || command.equals("blocaj")){
                //adauga ambuteiaj
                String src = current.split(" ")[1];
                String dest = current.split(" ")[2];
                int cost = Integer.parseInt(current.split(" ")[3]);
                map.addRestriction(command,src,dest, cost);
            } else if (command.equals("drive")){
                //facem drive
                String vehicle = current.split(" ")[1];
                String src = current.split(" ")[2];
                String dest = current.split(" ")[3];
                //apelam drive in functie de vehicul
                if(vehicle.equals("a")){
                    Autoturism a = new Autoturism();
                    map.drive(a,src, dest);
                }
                if(vehicle.equals("b")){
                    Bicicleta a = new Bicicleta();
                    map.drive(a,src, dest);
                }
                if(vehicle.equals("m")){
                    Motocicleta a = new Motocicleta();
                    map.drive(a,src, dest);
                }
                if(vehicle.equals("c")){
                    Camion a = new Camion();
                    map.drive(a,src, dest);
                }
            }
            if(!scanner.hasNext()){
                break;
            }
            current = scanner.nextLine();
        }

    }
}
