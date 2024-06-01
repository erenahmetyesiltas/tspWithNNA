import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    static Random random = new Random();

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File(args[0]);

        Scanner getData = new Scanner(new File(args[0]));

        // Create an arraylist to store each data of the city in the input file
        ArrayList<City> cityArrayList = new ArrayList<>();

        // File is read line by line
        while (getData.hasNextLine()){
            //System.out.println(getData.nextLine());
            String line = getData.nextLine();

            int counter = 0;

            City city = new City();

            String data = "";

            for (int i = 0; i < line.length(); i++) {

                if(line.charAt(i) != ' '){
                    data += line.charAt(i);

                }if(line.charAt(i) == ' ' || i == line.length()-1){

                    if(counter == 0){
                        // getting id data of each city
                        city.setId(Integer.valueOf(data));
                        counter++;
                    }else if(counter == 1){
                        // getting x-coordinate data of each city
                        city.setX_coordinate(Integer.valueOf(data));
                        counter++;
                    }else{
                        // getting y-coordinate data of each city
                        city.setY_coordinate(Integer.valueOf(data));
                    }
                    data = "";
                    continue;
                }
            }

            cityArrayList.add(city);
        }

        int[][] distances = new int[cityArrayList.size()][cityArrayList.size()];

        for (int i = 0; i < cityArrayList.size(); i++) {
            for (int j = 0; j < cityArrayList.size(); j++) {

                if(i == j){
                    break;
                }else{
                    distances[i][j] = computeDistanceBetweenTwoCities(cityArrayList.get(i),cityArrayList.get(j));
                    distances[j][i] = distances[i][j];
                }

            }
        }

        int randomCityIdForFirstTraveller = random.nextInt(cityArrayList.size());
        int randomCityIdForSecondTraveller = random.nextInt(cityArrayList.size());

        while(randomCityIdForFirstTraveller == randomCityIdForSecondTraveller){
            randomCityIdForSecondTraveller = random.nextInt(cityArrayList.size());
        }

        Traveller traveller1 = new Traveller(cityArrayList.get(randomCityIdForFirstTraveller));
        Traveller traveller2 = new Traveller(cityArrayList.get(randomCityIdForSecondTraveller));

        // Remove the initial cities
        cityArrayList.remove(cityArrayList.get(randomCityIdForFirstTraveller));
        cityArrayList.remove(findCityById(randomCityIdForSecondTraveller,cityArrayList));

        // Generating routes according to nearest neighbor algorithm
        travellersRoutes(distances,traveller1,traveller2,cityArrayList);

        // Create the output text document
        generatingOutput(traveller1,traveller2,args[1]);
    }

    public static int computeDistanceBetweenTwoCities(City curLoc,City nextLoc){

        int x = curLoc.getX_coordinate() - nextLoc.getX_coordinate();
        int y = curLoc.getY_coordinate() - nextLoc.getY_coordinate();

        // System.out.println(Math.sqrt(Math.pow(x,2) + Math.pow(y,2)));

        return (int) Math.round(Math.sqrt(Math.pow(x,2) + Math.pow(y,2)));
    }

    public static void generatingOutput(Traveller trav1, Traveller trav2, String outputFile) throws FileNotFoundException {
        PrintWriter output = new PrintWriter(new File(outputFile));

        output.println(trav1.getTotalDistance() + trav2.getTotalDistance());

        output.println(trav1.getTotalDistance() + " " + (trav1.getCityIdList().size() + 1));
        output.println(trav1.getInitialCityId());
        for (int i = 0; i < trav1.getCityIdList().size(); i++) {
            output.println(trav1.getCityIdList().get(i));
        }

        output.println();

        output.println(trav2.getTotalDistance() + " " + (trav2.getCityIdList().size() + 1));
        output.println(trav2.getInitialCityId());
        for (int i = 0; i < trav2.getCityIdList().size(); i++) {
            output.println(trav2.getCityIdList().get(i));
        }

        //output.println();
        output.close();

    }

    public static City findCityById(int id,ArrayList<City> cityArrayList){
        for (int i = 0; i < cityArrayList.size(); i++) {
            if(id == cityArrayList.get(i).getId()){
                return cityArrayList.get(i);
            }
        }
        return null;
    }

    public static void travellersRoutes(int[][] distances, Traveller traveller1, Traveller traveller2, ArrayList<City> cityArrayList){

        boolean[] isVisitedCity = new boolean[cityArrayList.size()+2];

        isVisitedCity[traveller1.getInitialCityId()] = true;
        isVisitedCity[traveller2.getInitialCityId()] = true;

        // Set the current cities for each traveller
        int currentCity1 = traveller1.getInitialCityId();
        int currentCity2 = traveller2.getInitialCityId();


        for (int i = 0; i < cityArrayList.size(); i++) {

            // Set the max integer value to the variables to think as it is infinity
            int minDistForFirstTraveller = 2147483647;
            int minDistForSecondTraveller = 2147483647;

            // These must be equal a number with not include value which be contained in the arrays.
            int nextCityForFirstTraveller = cityArrayList.size()+2;
            int nextCityForSecondTraveller = cityArrayList.size()+2;

            //In this loop j refers a city which will be visited
            for (int j = 0; j < cityArrayList.size() + 2; j++) {

                if(!isVisitedCity[j]){

                    // Find the nearest neighbour for the first traveller
                    if(distances[currentCity1][j] < minDistForFirstTraveller){
                        minDistForFirstTraveller = distances[currentCity1][j];
                        nextCityForFirstTraveller = j;
                    }

                    // Find the nearest neighbour for the second traveller
                    if(distances[currentCity2][j] < minDistForSecondTraveller){
                        minDistForSecondTraveller = distances[currentCity2][j];
                        nextCityForSecondTraveller = j;
                    }
                }

            }

            if(minDistForFirstTraveller <= minDistForSecondTraveller){
                traveller1.getCityIdList().add(nextCityForFirstTraveller);
                currentCity1 = nextCityForFirstTraveller;
                isVisitedCity[nextCityForFirstTraveller] = true;
            }else{
                traveller2.getCityIdList().add(nextCityForSecondTraveller);
                currentCity2 = nextCityForSecondTraveller;
                isVisitedCity[nextCityForSecondTraveller] = true;
            }

        }
        traveller1.calculateTotalDistance(cityArrayList);
        traveller2.calculateTotalDistance(cityArrayList);

    }
}