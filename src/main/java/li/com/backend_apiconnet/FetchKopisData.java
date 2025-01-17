/*
package li.com.backend_apiconnet;

import li.com.backend_apiconnet.entity.KopisFesEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kopis")
public class FetchKopisData {

    @Value("${kopis.api.service-key}")
    private String serviceKey;

    private final KopisFesEntity kopisFesEntity;

    @GetMapping("/performances")
    public List<KopisFesEntity> getKopisFesEntity() {

        List<KopisFesEntity> kopisFesEntity = new ArrayList<>();

        try {
            // API 호출 URL
            String apiUrl = String.format(
                    "http://www.kopis.or.kr/openApi/restful/pblprfr?service=%s&stdate=20160101&eddate=20160630&rows=10&cpage=1",
                    serviceKey
            );

            // HTTP 연결
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/xml");

            // 응답 읽기
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            reader.close();

            // XML 파싱
            String xmlResponse = responseBuilder.toString();
            kopisFesEntity = parseXmlToEntity(xmlResponse);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return kopisFesEntity;
    }

    private List<KopisFesEntity> parseXmlToEntity(String xml) throws Exception {
        List<KopisFesEntity> kopiseEntity = new ArrayList<>();

        // XML 파싱을 위한 DocumentBuilder 생성
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new java.io.ByteArrayInputStream(xml.getBytes()));

        // <db> 태그 가져오기
        NodeList dbElements = document.getElementsByTagName("db");

        // 각 <db> 태그의 데이터 추출
        for (int i = 0; i < dbElements.getLength(); i++) {
            Element element = (Element) dbElements.item(i);

            // 태그에서 데이터 추출
            String mt20id = getTagValue("mt20id", element);
            String prfnm = getTagValue("prfnm", element);
            String prfpdfrom = getTagValue("prfpdfrom", element);
            String prfpdto = getTagValue("prfpdto", element);
            String fcltynm = getTagValue("fcltynm", element);
            String poster = getTagValue("poster", element);
            String area = getTagValue("area", element);
            String genrenm = getTagValue("genrenm", element);
            String openrun = getTagValue("openrun", element);
            String prfstate = getTagValue("prfstate", element);

            // Performance 객체 생성
            kopiseEntity.add(new KopisFesEntity(mt20id, prfnm, prfpdfrom, prfpdto, fcltynm, poster, area, genrenm, openrun, prfstate));
        }

        return kopiseEntity;
    }

    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "없음";
    }

    // 공연 정보를 담는 클래스
    */
/*public static class Performance {


        public Performance(String mt20id, String prfnm, String prfpdfrom, String prfpdto,
                           String fcltynm, String poster, String area, String genrenm,
                           String openrun, String prfstate) {
            this.mt20id = mt20id;
            this.prfnm = prfnm;
            this.prfpdfrom = prfpdfrom;
            this.prfpdto = prfpdto;
            this.fcltynm = fcltynm;
            this.poster = poster;
            this.area = area;
            this.genrenm = genrenm;
            this.openrun = openrun;
            this.prfstate = prfstate;
        }

        // Getter methods
        public String getMt20id() { return mt20id; }
        public String getPrfnm() { return prfnm; }
        public String getPrfpdfrom() { return prfpdfrom; }
        public String getPrfpdto() { return prfpdto; }
        public String getFcltynm() { return fcltynm; }
        public String getPoster() { return poster; }
        public String getArea() { return area; }
        public String getGenrenm() { return genrenm; }
        public String getOpenrun() { return openrun; }
        public String getPrfstate() { return prfstate; }
    }*//*

}
*/
