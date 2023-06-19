import javax.swing.JLabel;
import javax.swing.ImageIcon;

/**
 * HomeScreen class creates a basic GUI
 */
public class HomeScreen {

    public JLabel logoLabel;
    public JLabel textLabel;

    public HomeScreen() {
        ImageIcon logo = new ImageIcon("images/ETFlogo.png");
        textLabel = new JLabel(
                "<html><div style='font-family:Roboto;font-size:35px;font-weight:bold'>"+"""
                <br>ETF</br>
                <br>STATISTICS</br>
                <br>APP</br>"""
                        + "</div>"
                        + "<div style='font-family:Times;font-size:14px;font-weight:normal'>" + """
                <br></br>
                <br>• Use the menu above to navigate.</br>
                <br></br>
                <br>• Check the desired ETF statistics.</br>
                <br></br>
                <br>• Once selected a table, click a title to sort the Funds by a desired column.</br>
                <br></br>""");
        logoLabel = new JLabel(logo);
    }
}