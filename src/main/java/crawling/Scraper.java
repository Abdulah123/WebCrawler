package crawling;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Scraper {

    private CommandLine cmd;
    public String link;
    private String tempDirectory = "tmp/";
    static Logger logger = LoggerFactory.getLogger(Scraper.class);


    /**
     * Constructor
     * @param args Url to analyze
     */
    public Scraper(String args[]) {

        this.cmd = configureCLI(args);
        link = cmd.getOptionValue("url");
    }


    /**
     * Start method, its work is to run methods in Crawler class, Resposne class
     * and SavaToXml
     */
    public Response start() throws Exception {
        try {

            Crawler crawler = getCrawler();
            return crawler.crawl(link);

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
    }



    /**
     * Crawler method is from Crawler class which extends an object "crawler"
     */
    private Crawler getCrawler() {
        Crawler crawler = new Crawler(tempDirectory);

        if (cmd.getOptionValue("crawlers") != null) {
            crawler.setCrawlers(2);
        }

        if (cmd.getOptionValue("max-pages") != null) {
            crawler.setMaxPages(300);
        }
        return crawler;
    }

    /**
     * This method is to make option for the command
     *
     * @param args
     * @return
     */
    private static CommandLine configureCLI(String[] args) {

        Options options = new Options();

        options.addOption(
                Option.builder("u")
                        .longOpt("url")
                        .required(true)
                        .hasArg(true)
                        .desc("website to crawl (http://example.com)")
                        .build()
        );

        options.addOption(
                Option.builder("o")
                        .longOpt("output-file")
                        .hasArg(true)
                        .desc("output file - by default: websitemap.xml")
                        .build()
        );

        options.addOption(
                Option.builder("c")
                        .longOpt("crawlers")
                        .hasArg(true)
                        .desc("number of crawlers - by default: 1")
                        .build()
        );

        options.addOption(
                Option.builder("m")
                        .longOpt("max-pages")
                        .hasArg(true)
                        .desc("max pages to fetch - by default: 500")
                        .build()
        );

        options.addOption(
                Option.builder("t")
                        .longOpt("temp-dir")
                        .hasArg(true)
                        .desc("temporary directory - by default: tmp/")
                        .build()
        );
        options.addOption(
                Option.builder("d")
                        .longOpt("delay")
                        .hasArg(true)
                        .desc("delay in ms - by default: 200ms")
                        .build()
        );

        CommandLineParser parser = new DefaultParser();

        try {
            return parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println("Parsing failed.  Reason: " + e.getMessage());
            System.exit(1);
        }
        return null;
    }

}

