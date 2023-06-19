import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GPW {
    //private static Logger logger = LogManager.getLogger(Log4jDemo.class);

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://www.biznesradar.pl/spolki-wskazniki-wartosci-rynkowej/indeks:sWIG80").timeout(6000).get();
        //Document doc = Jsoup.connect("https://www.gpw.pl/wskazniki").timeout(6000).get();
        Elements table = doc.select(".qTableFull");
        String filename = "dataCompanies.csv";
        File file = new File(filename);
        PrintWriter writer = new PrintWriter(file);

        int dupa= 0;
        for (Element row : table.select("tr")) {
            try {
                Elements cells = row.select("td");
                String profil = cells.get(0).text();
                String raport = cells.get(1).text();
                String pbv = cells.get(2).text();
                String line = String.format("%s,%s,%s", profil, raport, pbv);
                writer.println(line);
                dupa++;
            } catch (Exception IndexOutOfBoundsException) {

            }

        }

        writer.close();
        Object[] columnNames = {"Profil", "Raport", "Price/Book Value"};
        Object[][] data = new Object[dupa-1][3];
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
                data[row][0] = fields[0];
                data[row][1] = fields[1];
                try {
                    data[row][2] = Double.parseDouble(fields[2]);
                } catch (Exception ArrayIndexOutOfBoundsException) {

                }
            }
        }
        scanner.close();
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setTitle("sWIG80");
        DefaultTableModel model = new DefaultTableModel(data,columnNames) {
            @Override
            public Class getColumnClass(int column) {
                return switch (column) {
                    case 2 -> Double.class;
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
