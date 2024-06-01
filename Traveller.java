import java.util.ArrayList;

public class Traveller {
    private ArrayList<Integer> cityIdList;
    //private int currentCityId;
    private City initialCity;
    private int totalDistance;
    //private int fitnessValue;

    public Traveller(City city){
        initialCity = city;
        cityIdList = new ArrayList<>();
        cityIdList.add(this.getInitialCityId());
        //cityIdList.add(firstCityId);
    }

    public Traveller(){

    }

    public ArrayList<Integer> getCityIdList() {
        return cityIdList;
    }

    public void setCityIdList(ArrayList<Integer> cityIdList) {
        this.cityIdList = cityIdList;
    }

    //public void addNewCity(int newCityId, Traveller traveller1, Traveller traveller2, ArrayList<City> allCities) {
//
    //    if(!isTheCityVisitedBefore(newCityId, traveller1.getCityIdList(), traveller2.getCityIdList())){
//
    //        City curCity = new City();
//
    //        for (int i = 0; i < allCities.size(); i++) {
    //            if(allCities.get(i).getId() == currentCityId){
    //                curCity = allCities.get(i);
    //            }
    //        }
//
    //        cityIdList.add(newCityId);
    //        setCurrentCityId(newCityId);
//
    //        City nextCity = new City();
//
    //        for (int i = 0; i < allCities.size(); i++) {
    //            if(allCities.get(i).getId() == currentCityId){
    //                nextCity = allCities.get(i);
    //            }
    //        }
//
    //        setTotalDistance(computeDistanceBetweenTwoCities(curCity,nextCity));
    //    }else{
    //        throw new CityAlreadyVisitedException();
    //    }
//
    //}
    //
    public void setInitialCity(City city) {
        this.initialCity = city;
    }
    public int getInitialCityId(){
        return initialCity.getId();
    }
    public City getInitialCity() {
        return initialCity;
    }

    public void calculateTotalDistance(ArrayList<City> allCities){

        //City cityFirstVisited = findCityInCityArrayList(cityIdList.get(0),allCities);
//
        //int totalDist = computeDistanceBetweenTwoCities(initialCity,cityFirstVisited);
//
        //for (int i = 0; i < cityIdList.size()-1; i++) {
        //    City city1 = findCityInCityArrayList(cityIdList.get(i),allCities);
        //    City city2 = findCityInCityArrayList(cityIdList.get(i+1),allCities);
        //    totalDist += computeDistanceBetweenTwoCities(city1,city2);
        //}
//
        ////City initalCity = findCityInCityArrayList(getInitialCityId(),allCities);
        //City finalCity = findCityInCityArrayList(cityIdList.get(cityIdList.size()-1),allCities);
//
        //totalDist += computeDistanceBetweenTwoCities(finalCity,initialCity);
        //setTotalDistance(totalDist);

        cityIdList.remove(cityIdList.get(0));

        City cityFirstVisited = findCityInCityArrayList(cityIdList.get(0),allCities);

        int totalDist = computeDistanceBetweenTwoCities(initialCity,cityFirstVisited);

        for (int i = 0; i < cityIdList.size()-1; i++) {
            City city1 = findCityInCityArrayList(cityIdList.get(i),allCities);
            City city2 = findCityInCityArrayList(cityIdList.get(i+1),allCities);
            totalDist += computeDistanceBetweenTwoCities(city1,city2);
        }

        City finalCity = findCityInCityArrayList(cityIdList.get(cityIdList.size()-1),allCities);
        totalDist += computeDistanceBetweenTwoCities(finalCity,getInitialCity());
        setTotalDistance(totalDist);

    }

    //public boolean isTheCityVisitedBefore(int id, ArrayList<Integer> trav1List, ArrayList<Integer> trav2List){
    //    boolean isVisited = false;
//
    //    if(trav1List.contains(id) || trav2List.contains(id)){
    //        isVisited = true;
    //    }
//
    //    return isVisited;
    //}

    public int computeDistanceBetweenTwoCities(City curLoc,City nextLoc){

        int x = curLoc.getX_coordinate() - nextLoc.getX_coordinate();
        int y = curLoc.getY_coordinate() - nextLoc.getY_coordinate();

        // System.out.println(Math.sqrt(Math.pow(x,2) + Math.pow(y,2)));

        return (int) Math.round(Math.sqrt(Math.pow(x,2) + Math.pow(y,2)));
    }

    //public int getCurrentCityId() {
    //    return currentCityId;
    //}

    //private void setCurrentCityId(int currentCityId) {
    //    this.currentCityId = currentCityId;
    //}

    public int getTotalDistance() {
        return totalDistance;
    }

    private void setTotalDistance(int newDistance) {
        this.totalDistance += newDistance;
    }

    private City findCityInCityArrayList(int cityId, ArrayList<City> cityArrayList){

        City city = new City();

        for (int i = 0; i < cityArrayList.size(); i++) {
            if(cityId == cityArrayList.get(i).getId()){
                city = new City(cityArrayList.get(i).getX_coordinate(),cityArrayList.get(i).getY_coordinate());
            }
        }

        return city;
    }
}
