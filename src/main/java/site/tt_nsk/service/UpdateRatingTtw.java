package site.tt_nsk.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateRatingTtw {

    private final PlayerService playerService;
    

//    @Scheduled(cron = "0****MON") // TODO: 03.03.2023 проверить работу метода по расписанию
    public void parseRatingWithTTW() throws DOMException, XPathExpressionException, ParserConfigurationException, IOException, SAXException {
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

    @Scheduled(fixedDelay = 86400000) // fixedDelay = 86400000 каждые сутки, cron = " 0 * * * * MON" каждый понедельник
    public void parseRating(){
        List<String> listIdTtw = playerService.getIdTtw();
        for (int i = 0; i < listIdTtw.size(); i++) {
            String url = "http://r.ttw.ru/players/?id=" + listIdTtw.get(i);
            try {
                org.jsoup.nodes.Document document = Jsoup.connect(url)
                        .userAgent("Chrome")
                        .timeout(500000)
                        .referrer("https://google.com")
                        .get();
                Elements ratingTtw = document.getElementsByClass("player-rating-count-cell");
                int iter = 0;
                for (Element el : ratingTtw) {
                    if(iter == 1){
                        String text = el.ownText();
                        String rTtw = text.split(",")[0];
                        Player playerByRatingTtw = playerService.getPlayerByRatingTtw(listIdTtw.get(i));
                        playerService.updateRatingTtw(playerByRatingTtw, new BigDecimal(rTtw));
                        log.info("Это рейтинг TTW: " + rTtw);
                    }
                    iter++;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void parseRatingByClickingOnClient(String idTtw){
            String url = "http://r.ttw.ru/players/?id=" + idTtw;
            try {
                org.jsoup.nodes.Document document = Jsoup.connect(url)
                        .userAgent("Chrome")
                        .timeout(500000)
                        .referrer("https://google.com")
                        .get();
                Elements ratingTtw = document.getElementsByClass("player-rating-count-cell");
                int iter = 0;
                for (Element el : ratingTtw) {
                    if(iter == 1){
                        String text = el.ownText();
                        String rTtw = text.split(",")[0];
                        Player playerByRatingTtw = playerService.getPlayerByRatingTtw(idTtw);
                        playerService.updateRatingTtw(playerByRatingTtw, new BigDecimal(rTtw));
                    }
                    iter++;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

}
