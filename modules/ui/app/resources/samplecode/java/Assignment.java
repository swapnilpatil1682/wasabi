import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class contains the sample code to get a Wasabi assignments.
 */
public class Assignment {

  public static void main(String[] args) throws IOException, ParseException {
    System.out.print(getAssignment("ApplicationName", "ExperimentName", "UserName"));
  }


  /**
   * This method calls the Wasabi Api to get an assignment for the specified experiment and user.
   *
   * @param application the Applicationname the experiment is running in
   * @param experiment  the name of the Experiment
   * @param user        the user for whom an Assignment should be generated
   * @return the Assignment or <code>null</code> if the configuration was wrong
   * @throws IOException
   * @throws ParseException
   */
  private static Object getAssignment(String application, String experiment, String user) throws IOException, ParseException {

    String urlAssignment = String.format("https://abtesting.intuit.com/api/v1/assignments/applications/%s/experiments/%s/users/%s", application, experiment, user);

    URL url = new URL(urlAssignment);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

    connection.setRequestMethod("GET");

    // give it 500 milliseconds to respond
    connection.setReadTimeout(500);
    connection.connect();

    // read the output from the server
    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

    /*
    The response from the server is a json with the following exemplary entries:
     {"cache":true,
     "payload":null,
     "assignment":"b",
     "context":"PROD",
     "status":"EXISTING_ASSIGNMENT"}
     */
    String line = reader.readLine();

    JSONParser parser = new JSONParser();
    JSONObject assignment = (JSONObject) parser.parse(line);

    return assignment.get("assignment"); // if you are just interested in the assignment take the 'assignment' field
  }

}

