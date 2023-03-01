package site.tt_nsk.service;

//import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.json.JsonPath;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.stereotype.Service;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class JsonFromXmlServer {

    private final PlayerService playerService;

    //    @Value("C:\\Users\\79130\\IdeaProjects\\tt_nsk\\storage\\xml")
    private static Path storagePathXml = Path.of("C:\\Users\\79130\\IdeaProjects\\tt_nsk\\storage\\xml");

    //    @Value("${storage.location}/json")
    private static Path storagePathJson = Path.of("C:\\Users\\79130\\IdeaProjects\\tt_nsk\\storage\\json");;
    //    @Value("xml\\players.xml")
    private static String xmlFileName = "players.xml";
    private static String jsonFileName = "player.json";

    public void getJson() {
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

    public List<String> getListPlayersFromJson(String idTtw){
//        JSONObject jsonFromStorage = getJsonFromStorage();
//        JSONArray jsonArray = jsonFromStorage.getJSONArray("Player");

        try {
            return JsonPath.from(
                    new String(Files.readAllBytes(storagePathJson.
                            resolve(jsonFileName)))).get("Players.Player.findAll{Player -> Player.id == '"  + idTtw + "'}");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public List<String> getListPlayersFromJson(Integer idTtw){
        try {
            return JsonPath.from(
                    new String(Files.readAllBytes(storagePathJson.
                            resolve(jsonFileName)))).get("Players.Player.findAll{Player -> Player.id == "  + idTtw + "}");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getListRatingFromJson(){
        try {
            return JsonPath.from(
                    new String(Files.readAllBytes(storagePathJson.
                            resolve(jsonFileName)))).get("Players.Player.rating");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public List<String> getListIdFromJson(){
        try {
            return JsonPath.from(
                    new String(Files.readAllBytes(storagePathJson.
                            resolve(jsonFileName)))).get("Players.Player.id");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, List<String>> getDataPlayersTtwByIdTtw(){
        List<String> listIdTtw = playerService.getIdTtw();
//        List<String> listId = getListIdFromJson();
//        List<String> listRating = getListRatingFromJson();
        Map<String, List<String>> dataPlayersTtwByIdTtw = new HashMap<>();
        for (String id : listIdTtw) {
            if (!id.chars().allMatch(Character::isDigit)) {
                dataPlayersTtwByIdTtw.put(id, getListPlayersFromJson(id));
            }
            if (id.chars().allMatch(Character::isDigit)) dataPlayersTtwByIdTtw.put(id, getListPlayersFromJson(Integer.parseInt(id)));
//            getListPlayersFromJson(Integer.parseInt(id));
        }
        log.info(String.valueOf(dataPlayersTtwByIdTtw.size()));
        return dataPlayersTtwByIdTtw;
    }

    public JSONObject getJsonFromStorage() throws IOException {
        Path pathJson = storagePathJson.resolve(jsonFileName);
        String jsonString = new String(Files.readAllBytes(pathJson));
        return new JSONObject(jsonString);
    }

    public Map<String, String> printMap() throws DOMException, XPathExpressionException, ParserConfigurationException, IOException, SAXException {
        List<String> listIdTtw = playerService.getIdTtw();
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//        Document document = documentBuilder.parse((InputStream) storagePathXml.resolve(xmlFileName));
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
                dataPlayersTtwByIdTtw.put(listIdTtw.get(i), n.getTextContent());
        }
        }
        return dataPlayersTtwByIdTtw;
    }

}
