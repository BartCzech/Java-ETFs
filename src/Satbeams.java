import java.io.*;
import java.util.Objects;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Satbeams {

	private final static String url = "https://satbeams.com/satellites";
	
	public static void main(String[] args) {
		try {
			Document doc = Jsoup.connect(url).get();
			
			Elements media = doc.select("table [cellpadding=0][cellspacing=0][class=sat_grid][id=sat_grid]");

			String filename = "satellites.csv";
			File file = new File(filename);
			PrintWriter writer = new PrintWriter(file);

			int counter = 0;
			for (Element row : media.select(".class_tr")) {
				Elements cells = row.select("td");
				String name = cells.get(3).text();
				String position = cells.get(1).text();
				String norad = cells.get(4).text();
				String cospar = cells.get(5).text();
				String model = cells.get(6).text();
				String launchSite = cells.get(8).text();
				String launchDate = cells.get(10).text();
				String producer = "no data";
				String line = String.format("%s", name) + ";" + String.format("%s", position) + ";" + String.format("%s", norad) + ";" + String.format("%s", cospar) + ";" + String.format("%s", model) + ";" + String.format("%s", launchSite) + ";" + String.format("%s", launchDate) + ";" + String.format("%s", producer);
				writer.println(line);
				counter++;
			}
			writer.close();
			
			Object[] columnNames = {"Name","Position","Norad","Cospar","Model","Launch Site","Launch Date","Producer"};
			Object[][] data = new Object[counter][8];
			int lineNumber = 0;
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] tmpLine = line.split(";", -1);
				lineNumber++;
				for (int i=0; i<8; i++) {
					data[lineNumber-1][i] = tmpLine[i];
				}
			}
			scanner.close();
			
			JFrame f = new JFrame();
			f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			f.setTitle("Satellites list");
			DefaultTableModel model = new DefaultTableModel(data,columnNames) {
				@Override
				public Class getColumnClass(int column) {
					return switch (column) {
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
