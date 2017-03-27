import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class contains the sample code to post a Wasabi impression.
 */
public class Impression {

  public static void main(String[] args) throws IOException {
    System.out.print(postImpression("ApplicationName", "ExperimentName", "UserName") ? "Impression recorded" : "Impression not recorded");
  }


  /**
   * This method calls the Wasabi Api to post an impression for the specified experiment and user.
   *
   * @param application the ApplicationName the experiment is running in
   * @param experiment  the name of the Experiment
   * @param user        the user for whom the Impression should be posted
   * @return <code>true</code> if the impression was recorded <code>false</code> otherwise
   * @throws IOException
   */
  private static boolean postImpression(String application, String experiment, String user) throws IOException {

    String urlImpression = String.format("https://abtesting.intuit.com/api/v1/events/applications/%s/experiments/%s/users/%s", application, experiment, user);

    URL url = new URL(urlImpression);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestProperty("content-type", "application/json");
    connection.setRequestMethod("POST");
    connection.setDoOutput(true);

    OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
    writer.write("{\"events\":[{\"name\":\"IMPRESSION\"}]}");
    writer.flush();

    // give it 500 milliseconds to respond
    connection.setReadTimeout(500);
    connection.connect();

    return connection.getResponseCode() == 201 ? true : false;
  }

}
