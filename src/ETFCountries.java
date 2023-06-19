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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ETFCountries {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://etfdb.com/etfs/bond/").timeout(6000).get();
        //Document doc = Jsoup.connect("https://www.gpw.pl/wskazniki").timeout(6000).get();
        Elements table = doc.select("tbody");
        String filename = "dataCompanies.csv";
        File file = new File(filename);
        PrintWriter writer = new PrintWriter(file);

        int dupa= 0;
        for (Element row : table.select("tr")) {
            try {
                Elements cells = row.select("td");
                String country = cells.get(0).text();
                String fundflowrank = cells.get(1).text();
                String threemonthflow = cells.get(3).text().replace(",","").replace("$","");
                String returnrank = cells.get(4).text();
                String avgthreemonthreturn = "";
                if (!cells.get(6).text().equals("N/A")) {
                    avgthreemonthreturn = cells.get(6).text().replace("%", "");
                } else {
                    avgthreemonthreturn = "0";
                }
                String totalassets = cells.get(9).text().replace(",","").replace("$","");
                String avgexpense = cells.get(12).text().replace("%", "");
                String dividendrank = cells.get(13).text();
                String avgdividend = cells.get(15).text().replace("%", "");
                String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s", country, fundflowrank, threemonthflow, returnrank, avgthreemonthreturn,
                        totalassets, avgexpense, dividendrank, avgdividend);
                writer.println(line);
                dupa++;
            } catch (Exception IndexOutOfBoundsException) {

            }

        }

        writer.close();
        Object[] columnNames = {"Country", "Fund Flow Rank", "3-Month Flow [$MM]", "Return rank", "Avg 3-month return [%]",
        "Total assets [$MM]", "Average expense [%]", "Dividend rank", "Average dividend [%]"};
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
                data[row][0] = fields[0];
                data[row][1] = Integer.parseInt(fields[1]);
                data[row][2] = Double.parseDouble(fields[2]);
                data[row][3] = Integer.parseInt(fields[3]);
                data[row][4] = Double.parseDouble(fields[4]);
                data[row][5] = Double.parseDouble(fields[5]);
                data[row][6] = Double.parseDouble(fields[6]);
                data[row][7] = Integer.parseInt(fields[7]);
                try {
                    data[row][8] = Double.parseDouble(fields[8]);
                } catch (Exception caught) {
                    data[row][8] = 0;
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
                    case 1,3,7 -> Integer.class;
                    case 2,4,5,6,8 -> Double.class;
                    default -> String.class;
                };
            }
        };
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );

        JTable table1 = new JTable(model);
        table1.setDefaultRenderer(String.class, centerRenderer);
        JScrollPane scroll = new JScrollPane(table1);
        table1.setAutoCreateRowSorter(true);
        f.add(scroll);
        f.setSize(1200,900);
        f.setVisible(true);
    }
}
