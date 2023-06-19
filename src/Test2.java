import java.io.*;
import java.util.Objects;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Test2 {

	private final static String url = "https://satbeams.com/satellites";
	
	public static void main(String[] args) {
		try {
			Document doc = Jsoup.connect(url).get();
			
			Elements media = doc.select("table [cellpadding=0][cellspacing=0][class=sat_grid][id=sat_grid]");
			Elements tmp = Objects.requireNonNull(media.first()).select("tr:gt(0)");
			
			//Elements tmp1 = media.select("table td:eq(1)").select("span");
			BufferedWriter writer = new BufferedWriter(new FileWriter("SatBeams.txt"));
			writer.write(tmp.toString());
			writer.close();

			String filename = "satellites.csv";
			File file = new File(filename);
			PrintWriter writer2 = new PrintWriter(file);

			int counter = 0;
			for (Element row : media.select(".class_tr")) {
				Elements cells = row.select("td");
				String name = cells.get(3).text();
				String line = String.format("%s", name);
				writer2.println(line);
				counter++;
			}
			writer2.close();
			Object[] columnNames = {"Nazwa"};
			Object[][] data = new Object[counter-1][1];
			int row = -1;
			int lineNumber = 0;
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				lineNumber++;
				if (lineNumber >= 2) {
					row++;
					data[row][0] = line;
//					String[] fields = line.split(",");
//					data[row][0]="";
//					for(int i=0;i<fields.length;i++){
//						data[row][0] += fields[i];
				//}

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
