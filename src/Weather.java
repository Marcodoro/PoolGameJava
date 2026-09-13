import javax.swing.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import org.json.JSONObject;

public class Weather {
    public static void main(String[] args) {
        JFrame frame = new JFrame("My First Java App");
        frame.setSize(900, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Quits app when closed
        frame.setLayout(null);

        System.out.println(calcKelvinToCelcius(50));

        JTextField cityinput = new JTextField(20);
        cityinput.setBounds(50, 50, 200, 30);
        frame.add(cityinput);

        JLabel weatherinfo = new JLabel("Weather Result will appear here");
        weatherinfo.setBounds(50, 100, 300, 30);
        frame.add(weatherinfo);

        JButton search = new JButton("Search");
        search.setBounds(260, 50, 150, 30);
        frame.add(search);

        search.addActionListener(e -> {
            String city = cityinput.getText();

            if (city.isEmpty()) {
                weatherinfo.setText("You didnt input anything try again");
            }
            else {
                try {
                    float thetemperature = getweather(city);
                    weatherinfo.setText("Temperature in " + city + " is " + thetemperature);

                    if (thetemperature == 0) {
                        weatherinfo.setText("City " + city + " not found");
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }

        });


        frame.setVisible(true);
    }
    public static float getweather(String city) {

        try {
            String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=a81d82c8b3b3e471cea9a64d6b50825b";

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String responseBody = response.body();

            JSONObject json = new JSONObject(responseBody);

            double temp = json.getJSONObject("main").getDouble("temp");

            String weatherDesc = json.getJSONArray("weather")
                    .getJSONObject(0)
                    .getString("description");

            System.out.println("Current Temp: " + temp + "°C");
            System.out.println("Conditions: " + weatherDesc);
            System.out.println(temp);

            float ctemp = (float) temp;
            //convert to c cause for some reason it gives the temp in kelvin
            float ctemp2 = (float) (ctemp -273.15);

            System.out.println(ctemp2);

            return ctemp2;

        } catch (Exception e) {
            return 1;
        }
    }
    public static float calcKelvinToCelcius(float degreesc) {
        float degreesResult = (float) (degreesc + 273.15);

        return (float) degreesResult;
    }
}