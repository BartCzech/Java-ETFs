import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JSoupTest {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://www.imdb.com/chart/top/").timeout(6000).get();
        Elements body = doc.select("tbody.lister-list");
        for(Element e : body.select("tr")) {
            String year = e.select("td.titleColumn span.secondaryInfo").text();
            System.out.println(year);
        }
    }
}
