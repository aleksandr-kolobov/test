import core.Line;
import core.Station;
import junit.extensions.TestSetup;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class RouteCalculatorTests extends TestCase {

    StationIndex stationIndex;

    @Override
    protected void setUp() throws Exception {

        stationIndex = new StationIndex();//карта для тестов: line1 центральная
                    // две другие междусобой не пересекаются пересекают центральную с пересадкой
        Line line1 = new Line(1, "Центральная");
        Line line2 = new Line(2, "Перекрестная");
        Line line3 = new Line(3, "Поперечная");

        Station station = new Station("1ая центральная", line1);
        stationIndex.addStation(station);
        line1.addStation(station);
        station = new Station("2ая центральная", line1);
        stationIndex.addStation(station);
        line1.addStation(station);
        station = new Station("3ая центральная", line1);
        stationIndex.addStation(station);
        line1.addStation(station);
        station = new Station("4ая центральная", line1);
        stationIndex.addStation(station);
        line1.addStation(station);

        station = new Station("1ая перекрестная", line2);
        stationIndex.addStation(station);
        line2.addStation(station);
        station = new Station("2ая перекрестная", line2);
        stationIndex.addStation(station);
        line2.addStation(station);
        station = new Station("3ая перекрестная", line2);
        stationIndex.addStation(station);
        line2.addStation(station);
        station = new Station("4ая перекрестная", line2);
        stationIndex.addStation(station);
        line2.addStation(station);

        station = new Station("1ая поперечная", line3);
        stationIndex.addStation(station);
        line3.addStation(station);
        station = new Station("2ая поперечная", line3);
        stationIndex.addStation(station);
        line3.addStation(station);
        station = new Station("3ая поперечная", line3);
        stationIndex.addStation(station);
        line3.addStation(station);
        station = new Station("4ая поперечная", line3);
        stationIndex.addStation(station);
        line3.addStation(station);

        stationIndex.addLine(line1);
        stationIndex.addLine(line2);
        stationIndex.addLine(line3);

        ArrayList<Station> stations = new ArrayList<>();
        stations.add(stationIndex.getStation("2ая центральная"));
        stations.add(stationIndex.getStation("2ая перекрестная"));
        stationIndex.addConnection(stations);
        stations = new ArrayList<>();
        stations.add(stationIndex.getStation("3ая центральная"));
        stations.add(stationIndex.getStation("3ая поперечная"));
        stationIndex.addConnection(stations);
    }

    public void testGetRouteOnTheSpot() {
        List<Station> route = new ArrayList<>();//путь для теста
        Station station = stationIndex.getStation("3ая перекрестная");
        route.add(station);

        RouteCalculator calculator = new RouteCalculator(stationIndex);
        Station from = stationIndex.getStation("3ая перекрестная");
        Station to = stationIndex.getStation("3ая перекрестная");
        List<Station> actual = calculator.getShortestRoute(from, to);
        List<Station> expected = route;
        assertEquals(expected, actual);
    }

    public void testGetRouteOnTheLine() {
        List<Station> route = new ArrayList<>();//путь для теста
        Station station = stationIndex.getStation("3ая перекрестная");
        route.add(station);
        station = stationIndex.getStation("2ая перекрестная");
        route.add(station);
        station = stationIndex.getStation("1ая перекрестная");
        route.add(station);

        RouteCalculator calculator = new RouteCalculator(stationIndex);
        Station from = stationIndex.getStation("3ая перекрестная");
        Station to = stationIndex.getStation("1ая перекрестная");
        List<Station> actual = calculator.getShortestRoute(from, to);
        List<Station> expected = route;
        assertEquals(expected, actual);
    }

    public void testGetRouteWithOneConnection() {
        List<Station> route = new ArrayList<>();//путь для теста
        Station station = stationIndex.getStation("3ая перекрестная");
        route.add(station);
        station = stationIndex.getStation("2ая перекрестная");
        route.add(station);
        station = stationIndex.getStation("2ая центральная");
        route.add(station);
        station = stationIndex.getStation("3ая центральная");
        route.add(station);

        RouteCalculator calculator = new RouteCalculator(stationIndex);
        Station from = stationIndex.getStation("3ая перекрестная");
        Station to = stationIndex.getStation("3ая центральная");
        List<Station> actual = calculator.getShortestRoute(from, to);
        List<Station> expected = route;
        assertEquals(expected, actual);
    }

    public void testGetRouteWithTwoConnections() {
        List<Station> route = new ArrayList<>();//путь для теста

        Station station = stationIndex.getStation("3ая перекрестная");
        route.add(station);
        station = stationIndex.getStation("2ая перекрестная");
        route.add(station);
        station = stationIndex.getStation("2ая центральная");
        route.add(station);
        station = stationIndex.getStation("3ая центральная");
        route.add(station);
        station = stationIndex.getStation("3ая поперечная");
        route.add(station);
        station = stationIndex.getStation("2ая поперечная");
        route.add(station);

        RouteCalculator calculator = new RouteCalculator(stationIndex);
        Station from = stationIndex.getStation("3ая перекрестная");
        Station to = stationIndex.getStation("2ая поперечная");
        List<Station> actual = calculator.getShortestRoute(from, to);
        List<Station> expected = route;
        assertEquals(expected, actual);
    }

    public void testCalculateDuration(){
        List<Station> route = new ArrayList<>();//путь для теста

        Station station = stationIndex.getStation("4ая центральная");
        route.add(station);
        station = stationIndex.getStation("3ая центральная");
        route.add(station);
        station = stationIndex.getStation("3ая поперечная");
        route.add(station);
        station = stationIndex.getStation("2ая поперечная");
        route.add(station);
        station = stationIndex.getStation("1ая поперечная");
        route.add(station);

        double actual = RouteCalculator.calculateDuration(route);
        double expected = 11.0;
        assertEquals(expected, actual);
    }

    @Override
    protected void tearDown() throws Exception {

    }
}
