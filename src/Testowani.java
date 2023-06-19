import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Testowani  {
    public static void main(String[] args) throws IOException {
        int dupa= 0;
        String url = "http://www.90minut.pl/ranking_uefa.php";
        Document doc = Jsoup.connect(url).get();
        Elements table = doc.select("table");
        String filename = "dataCountries.csv";
        File file = new File(filename);
        PrintWriter writer = new PrintWriter(file);
        for (Element row : table.select("tr")) {
            Elements cells = row.select("td");
            String position = cells.get(0).text();
            String countryName = cells.get(1).text();
            String year1 = cells.get(2).text().replace(",",".");    // 18/19
            String year2 = cells.get(3).text().replace(",",".");    // 19/20
            String year3 = cells.get(4).text().replace(",",".");    // 20/21
            String year4 = cells.get(5).text().replace(",",".");    // 21/22
            String year5 = cells.get(6).text().replace(",",".");    // 22/23
            String sum = cells.get(7).text().replace(",",".");      //wszystkie sezony
            String team = cells.get(8).text().replace(",",".");     //ile druzyn
            String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s", position, countryName, year1, year2,year3, year4,year5, sum, team);
            writer.println(line);
            dupa++;
        }
        writer.close();
        Object[] columnNames = {"Poz.", "Kraj", "2018/2019", "2019/2020", "2020/2021","2021/2022", "2022/2023", "Razem", "Ile drużyn"};
        Object[][] data = new Object[dupa-1][9];
        int row = -1;
        int lineNumber = 0;
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lineNumber++;
            if (lineNumber >= 2) {
                row++;
                String[] fields = line.split(",");
                System.out.println(fields[0]);
                data[row][0] = Integer.parseInt(fields[0]);
                data[row][1] = fields[1];
                data[row][2] = Double.parseDouble(fields[2]);
                data[row][3] = Double.parseDouble(fields[3]);
                data[row][4] = Double.parseDouble(fields[4]);
                data[row][5] = Double.parseDouble(fields[5]);
                data[row][6] = Double.parseDouble(fields[6]);
                data[row][7] = Double.parseDouble(fields[7]);
                data[row][8] = fields[8];
            }
        }
        scanner.close();
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setTitle("UEFA RANKING");
        DefaultTableModel model = new DefaultTableModel(data,columnNames) {
            @Override
            public Class getColumnClass(int column) {
                return switch (column) {
                    case 0 -> Integer.class;
                    case 2,3,4,5,6,7 -> Double.class;
                    default -> String.class;
                };
            }
        };
        JTable table1 = new JTable(model);
        JScrollPane scroll = new JScrollPane(table1);
        table1.setAutoCreateRowSorter(true);
        f.add(scroll);
        f.setSize(900,900);
        f.setVisible(true);

    }
}
