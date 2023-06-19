import org.apache.log4j.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.io.IOException;


/**
 * Window class is treated like JFrame and contains CardPanel,
 * which is using panelContainer with all Panels in application:
 * HomeScreen, HighestScoresPanel, PlayerStatsPanel, TeamStatsPanel and TodayScoresPanel
 */
public class Window extends JFrame {

    /**
     * container for all Panels working in CardLayout
     */
    private final JPanel panelContainer;

    /**
     * logger for Window class
     */
    private static final Logger logger = Logger.getLogger(Window.class);

    /**
     * Window class constructor, basically creates window and
     * attaches Panels to it
     */
    public Window() {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenHeight = (int) screenSize.getHeight();
        int screenWidth = (int) screenSize.getWidth();
        int windowHeight = 600;
        int windowWidth = 800;
        logger.info("Screen resolution & window size set");

        ImageIcon logo = new ImageIcon("images/ETF.png");

        this.setTitle("ETF Statistics");
        this.setIconImage(logo.getImage());

        CardLayout cl = new CardLayout();

        panelContainer = new JPanel();
        panelContainer.setLayout(cl);

        JMenuBar menuBar = new JMenuBar();

        JMenu home = new JMenu("Home");
        JMenu RegionalOverview = new JMenu("Regions");
        JMenu Resources = new JMenu("Resources");
        JMenu Industrial = new JMenu("Industrial");
        JMenu RealEstate = new JMenu("Real Estate");
        JMenu Bonds = new JMenu("Bonds");


        JMenuItem exitItem = new JMenuItem("Exit");
        JMenuItem etfCountriesItem = new JMenuItem("Countries");
        JMenuItem etfRegionsItem = new JMenuItem("Regions");
        JMenuItem etfCommoditiesItem = new JMenuItem("Commodities");
        JMenuItem etfResourcesItem = new JMenuItem("Natural Resources");
        JMenuItem etfSectorItem = new JMenuItem("Sector");
        JMenuItem etfIndustryItem = new JMenuItem("Industry");
        JMenuItem etfRealEstateItem = new JMenuItem("Real Estate by Region");
        JMenuItem etfBondsItem = new JMenuItem("All Bonds");


        RegionalOverview.add(etfCountriesItem);
        RegionalOverview.add(etfRegionsItem);

        Resources.add(etfCommoditiesItem);
        Resources.add(etfResourcesItem);

        Industrial.add(etfSectorItem);
        Industrial.add(etfIndustryItem);

        RealEstate.add(etfRealEstateItem);

        Bonds.add(etfBondsItem);

        home.add(exitItem);

        menuBar.add(home);
        menuBar.add(RegionalOverview);
        menuBar.add(Resources);
        menuBar.add(Industrial);
        menuBar.add(RealEstate);
        menuBar.add(Bonds);


        exitItem.setEnabled(false);
        etfCountriesItem.setEnabled(false);
        etfRegionsItem.setEnabled(false);
        etfCommoditiesItem.setEnabled(false);
        etfResourcesItem.setEnabled(false);
        etfSectorItem.setEnabled(false);
        etfIndustryItem.setEnabled(false);
        etfRealEstateItem.setEnabled(false);
        etfBondsItem.setEnabled(false);

        HomeScreenThread homeScreenThread = new HomeScreenThread();
        homeScreenThread.start();

        try {
            homeScreenThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        exitItem.setEnabled(true);
        etfCountriesItem.setEnabled(true);
        etfRegionsItem.setEnabled(true);
        etfCommoditiesItem.setEnabled(true);
        etfResourcesItem.setEnabled(true);
        etfSectorItem.setEnabled(true);
        etfIndustryItem.setEnabled(true);
        etfRealEstateItem.setEnabled(true);
        etfBondsItem.setEnabled(true);


        cl.show(panelContainer, "Home");

        exitItem.addActionListener(e -> {
            logger.info("Closed application by clicking Exit");
            System.exit(0);
        });

        etfCountriesItem.addActionListener(e -> {
            try {
                TableTemplate ETF = new TableTemplate("https://etfdb.com/etfs/country/", "ETF Countries", "Country");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            logger.info("Clicked ETF Countries");
        });

        etfRegionsItem.addActionListener(e -> {
            try {
                TableTemplate ETF = new TableTemplate("https://etfdb.com/etfs/region/", "ETF Regions", "Region");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            logger.info("Clicked ETF Regions");
        });

        etfCommoditiesItem.addActionListener(e -> {
            try {
                TableTemplate ETF = new TableTemplate("https://etfdb.com/etfs/commodity/", "ETF Commodities", "Commodity");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            logger.info("Clicked ETF Commodities");
        });

        etfResourcesItem.addActionListener(e -> {
            try {
                TableTemplate ETF = new TableTemplate("https://etfdb.com/etfs/natural-resources/", "ETF Natural Resources", "Resource");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            logger.info("Clicked ETF Natural Resources");
        });

        etfSectorItem.addActionListener(e -> {
            try {
                TableTemplate ETF = new TableTemplate("https://etfdb.com/etfs/sector/", "ETF Sectors", "Sector");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            logger.info("Clicked ETF Sectors");
        });

        etfIndustryItem.addActionListener(e -> {
            try {
                TableTemplate ETF = new TableTemplate("https://etfdb.com/etfs/industry/", "ETF Industries", "Industry");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            logger.info("Clicked ETF Industries");
        });

        etfRealEstateItem.addActionListener(e -> {
            try {
                TableTemplate ETF = new TableTemplate("https://etfdb.com/etfs/real-estate-region/", "ETF Real Estate", "Region");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            logger.info("Clicked ETF Real Estate by Region");
        });

        etfBondsItem.addActionListener(e -> {
            try {
                TableTemplate ETF = new TableTemplate("https://etfdb.com/etfs/bond/", "ETF Bonds", "Bond type");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            logger.info("Clicked ETF Bonds");
        });

        this.setJMenuBar(menuBar);
        this.add(panelContainer);
        this.setSize(windowWidth, windowHeight);
        this.setLocation((screenWidth - windowWidth)/2, (screenHeight - windowHeight)/2);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        logger.info("Window created successfully");
    }

    /**
     * Thread for creating and running HomeScreen
     */
    private class HomeScreenThread extends  Thread {
        @Override
        public void run() {
            JPanel homePanel = new JPanel(new GridLayout(1,2));
            HomeScreen homeScreen = new HomeScreen();
            homePanel.add(homeScreen.logoLabel);
            homePanel.add(homeScreen.textLabel);
            panelContainer.add(homePanel, "Home");
            logger.info("Home Panel - successfully created");
        }
    }
}