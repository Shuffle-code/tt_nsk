package site.tt_nsk;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import site.tt_nsk.entity.Score;
import com.jayway.restassured.path.json.JsonPath;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.junit.jupiter.api.Test;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class testStarting {

    private static Path storagePathXml = Path.of("C:\\Users\\79130\\IdeaProjects\\tt_nsk\\storage\\xml");

    //    @Value("${storage.location}/json")
    private static Path storagePathJson = Path.of("C:\\Users\\79130\\IdeaProjects\\tt_nsk\\storage\\json");;
    //    @Value("xml\\players.xml")
    private static String xmlFileName = "players.xml";
    private static String jsonFileName = "player.json";


    public testStarting() throws IOException {
    }

    //    private JsonFromXml jsonFromXml;
    public static void main(String[] args) throws IOException, XPathExpressionException, ParserConfigurationException, SAXException {
//        Integer a = 2744120;
//        List<String> players = JsonPath.from(
//                new String(Files.readAllBytes(storagePathJson.
//                        resolve(jsonFileName)))).get("Players.Player.rating{Player -> Player.id == '26c0268'}");
//        System.out.println(players);
//        List<String> listResultTour = getMapPlayersFromJson();
//        System.out.println(listResultTour);
//        JSONObject inJson = getInJson();
//        JSONArray jsonArray = inJson.getJSONArray("Player");
//        System.out.println(jsonArray);
        getMapPlayersFromJson();
        printMap();
    }

    static public Map<String, String> printMap() throws DOMException, XPathExpressionException, ParserConfigurationException, IOException, SAXException {
        List<String> listIdTtw = new ArrayList<>();
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.parse("C:\\Users\\79130\\IdeaProjects\\tt_nsk\\storage\\xml\\players.xml");
        XPath xpath = XPathFactory.newInstance().newXPath();
        Map<String, String> dataPlayersTtwByIdTtw = new HashMap<>();
        for (int i = 0; i < listIdTtw.size(); i++) {
            int finalI = i;
            xpath.setXPathVariableResolver(new XPathVariableResolver() {
                @Override
                public Object resolveVariable(QName variableName) {
                    if (variableName.getLocalPart().equals("id"))
                        return listIdTtw.get(finalI);
                    else
                        return "";
                }
            });
            XPathExpression expr = xpath.compile("Players/Player[@id=$id]");
            NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
            for (int j = 0; j < nodes.getLength(); j++) {
                Node n = nodes.item(j);
                String textContent = n.getTextContent();
                String[] split = textContent.split("\n");
                dataPlayersTtwByIdTtw.put(listIdTtw.get(i), split[8]);
            }
            dataPlayersTtwByIdTtw.forEach((k, v) -> System.out.println(k + ": " + v));
        }
        return dataPlayersTtwByIdTtw;
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


   @Test
    public static List<String> getListResultTour(Score score) {
        String scoreString = score.toString();
        List<String> listScore = new ArrayList<>(Arrays.asList(scoreString.split("', ")));
        return listScore;
    }

}
