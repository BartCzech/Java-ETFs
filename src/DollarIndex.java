import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DollarIndex {
    public static void main(String[] args) throws IOException {

        Document doc = Jsoup.connect("https://www.x-rates.com/table/?from=USD&amount=1").timeout(6000).get();
        Elements mainTable = doc.select("table.tablesorter.ratesTable");

        Double eur_usd = Double.parseDouble(mainTable.select("tr:has(td:contains(Euro)) td:nth-of-type(3) a").text());
        Double usd_jpy = Double.parseDouble(mainTable.select("tr:has(td:contains(Japanese Yen)) td:nth-of-type(2) a").text());
        Double gbp_usd = Double.parseDouble(mainTable.select("tr:has(td:contains(British Pound)) td:nth-of-type(3) a").text());
        Double usd_cad = Double.parseDouble(mainTable.select("tr:has(td:contains(Canadian Dollar)) td:nth-of-type(2) a").text());
        Double usd_sek = Double.parseDouble(mainTable.select("tr:has(td:contains(Swedish Krona)) td:nth-of-type(2) a").text());
        Double usd_chf = Double.parseDouble(mainTable.select("tr:has(td:contains(Swiss Franc)) td:nth-of-type(2) a").text());

        Double dollar_index = 50.14348112 * Math.pow(eur_usd, -0.576) * Math.pow(usd_jpy, 0.136) * Math.pow(gbp_usd, -0.119) * Math.pow(usd_cad, 0.091) * Math.pow(usd_sek, 0.042) * Math.pow(usd_chf, 0.036);
        System.out.println(dollar_index);
    }
}