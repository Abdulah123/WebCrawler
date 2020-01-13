package crawling;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;


public class Crawler {

    private int maxPages = 500;
    private int crawlers = 1;
    private String tempDir;
    private int delay = 200;
    private Response response;

    public Crawler(String tempDir) {

        this.tempDir = tempDir;
        this.response = new Response();
    }

    /**
     * crawl method is derived from Response class which configure the url, control it and start the process
     *
     * @param url
     * @return
     * @throws Exception
     */
    public Response crawl(String url) throws Exception {

        Spider.configure(response, url);
        CrawlController controller = getCrawlController();
        controller.addSeed(url);
        controller.start(Spider.class, crawlers);

        return response;
    }

    /**
     * getCrawlController is a method from built in class CrawlController which controls the crawler
     * according to the parameters which are set
     *
     * @return
     * @throws Exception
     */
    private CrawlController getCrawlController() throws Exception {

        CrawlConfig config = new CrawlConfig();
        config.setPolitenessDelay(delay);
        config.setCrawlStorageFolder(tempDir);
        config.setMaxPagesToFetch(maxPages);
        config.setFollowRedirects(true);

        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        robotstxtConfig.setEnabled(false);
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);

        return new CrawlController(config, pageFetcher, robotstxtServer);
    }

    /**
     * setMaxPages is a set method to set the max number of the pages
     *
     * @param maxPages
     */
    public void setMaxPages(int maxPages) {
        this.maxPages = maxPages;
    }

    /**
     * setCrawlers is a set method to set the max number of the crawlers
     *
     * @param crawlers
     */
    public void setCrawlers(int crawlers) {
        this.crawlers = crawlers;
    }

    /**
     * setDelay is a set method to set the delay time of crawling
     *
     * @param delay
     */
    public void setDelay(int delay) {
        this.delay = delay;
    }
}
