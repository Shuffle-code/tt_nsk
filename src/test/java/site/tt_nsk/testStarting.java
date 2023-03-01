package site.tt_nsk;

import site.tt_nsk.entity.Score;
import com.jayway.restassured.path.json.JsonPath;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class testStarting {


    public testStarting() throws IOException {
    }

    //    private JsonFromXml jsonFromXml;
    public static void main(String[] args) throws IOException {
//        Integer a = 2744120;
//        List<String> players = JsonPath.from(
//                new String(Files.readAllBytes(storagePathJson.
//                        resolve(jsonFileName)))).get("Players.Player.rating{Player -> Player.id == '26c0268'}");
//        System.out.println(players);
//        List<String> listResultTour = getMapPlayersFromJson();
//        System.out.println(listResultTour);
        JSONObject inJson = getInJson();
        JSONArray jsonArray = inJson.getJSONArray("Player");
        System.out.println(jsonArray);
    }

    public static List<String> getMapPlayersFromJson(){
        try {
            return JsonPath.from(
                    new String(Files.readAllBytes(storagePathJson.
                            resolve(jsonFileName)))).get("Players.Player.id");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

        private static Path storagePathXml = Path.of("C:\\Users\\79130\\IdeaProjects\\tt_nsk\\storage\\xml");

        //    @Value("${storage.location}/json")
        private static Path storagePathJson = Path.of("C:\\Users\\79130\\IdeaProjects\\tt_nsk\\storage\\json");;
        //    @Value("xml\\players.xml")
        private static String xmlFileName = "players.xml";
        private static String jsonFileName = "player.json";

    public static JSONObject getInJson() throws IOException {
        Path pathJson = storagePathJson.resolve(jsonFileName);
        String jsonString = new String(Files.readAllBytes(pathJson));
        return new JSONObject(jsonString);
    }
        public static void getJson() {
            Path pathXml = storagePathXml.resolve(xmlFileName);
            Path path = storagePathJson.resolve(jsonFileName);
            String xmlString = null;
            try {
                xmlString = new String(Files.readAllBytes(pathXml));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            JSONObject jsonObj = XML.toJSONObject(xmlString);
            FileWriter file = null;
            try {
                file = new FileWriter(String.valueOf(path));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                file.write(String.valueOf(jsonObj));
                file.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        List<Map> players = JsonPath.from(new String(Files.readAllBytes(storagePathJson.resolve(jsonFileName)))).get("Players.Player.rating");


//        PlayService playService = new PlayService();
//        Score score = new Score();
//        score.setX1y2("2.3");
//        score.setX1y3("2.3");
//        score.setX1y4("6,2");
//        score.setX2y3("3-2");
//
//        JSONObject jsonObj = new JSONObject(score);
//        System.out.println(jsonObj);
//        System.out.println(score);
//        System.out.println(getListResultTour(score));
//        System.out.println(getListResultTour(score).size());
//        System.out.println(getListResultTour(score).get(1));
//        String $3$1 = "3/2".replaceFirst("(.)(.*)(.)", "$3$2$1");
////        String $2$1 = "3/2".replace(b);
//        System.out.println($3$1);
//        System.out.println(new LegUp());
//        JsonFromXml jsonFromXml = new JsonFromXml();
//        get
//        Path pathXml = storagePathXml.resolve(fileName);
//        System.out.println(storagePathXml.resolve(fileName));
//        String content = Files.readString(storagePathXml.resolve("xml\\players.xml"), StandardCharsets.UTF_8);
//        String data = FileUtils.readFileToString(new File("c:\\student.xml"), "UTF-8");
//        getJson();
//        writeJson();
//        System.out.println(content);
//    }






//    public static void writeJson() throws IOException {
//        JSONObject jsonObj = getJson();
//        try(FileOutputStream fos = new FileOutputStream(String.valueOf(storagePathJson.resolve("Players.json")))) {
//            fos.write(jsonObj.toString().getBytes());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


   @Test
    public static List<String> getListResultTour(Score score) {
        String scoreString = score.toString();
        List<String> listScore = new ArrayList<>(Arrays.asList(scoreString.split("', ")));
        return listScore;
    }

}
