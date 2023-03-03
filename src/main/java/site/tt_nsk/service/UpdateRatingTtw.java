package site.tt_nsk.service;

//import com.jayway.restassured.path.json.JsonPath;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import site.tt_nsk.entity.Player;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateRatingTtw {

    private final PlayerService playerService;
    

    @Scheduled(cron = "0****MON") // TODO: 03.03.2023 проверить работу метода по расписанию
    public void printMap() throws DOMException, XPathExpressionException, ParserConfigurationException, IOException, SAXException {
        List<String> listIdTtw = playerService.getIdTtw();
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = documentBuilder.parse("C:\\Users\\79130\\IdeaProjects\\tt_nsk\\storage\\xml\\players.xml");
        XPath xpath = XPathFactory.newInstance().newXPath();
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
                Player playerByRatingTtw = playerService.getPlayerByRatingTtw(listIdTtw.get(i));
                playerService.updateRatingTtw(playerByRatingTtw, new BigDecimal(split[8].replaceAll("\\s", "")));
            }
        }
    }
}
