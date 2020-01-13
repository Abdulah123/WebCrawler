package crawling;

import java.util.concurrent.ConcurrentSkipListSet;

public class Response {

    public ConcurrentSkipListSet<String> pageLinks = new ConcurrentSkipListSet<String>();
    public ConcurrentSkipListSet<String> externalLinks = new ConcurrentSkipListSet<String>();

    /**
     * addPageLink is to add page lin to the xml
     *
     * @param url
     */
    public void addPageLink(String url) {
        pageLinks.add(url);
    }

    /**
     * addExternalLink to add external links to the xml
     *
     * @param url
     */
    public void addExternalLink(String url) {
        externalLinks.add(url);
    }


    @Override
    public String toString() {
        return "\n" + "pageLinks=" + pageLinks + "\n" +
                ", externalLinks=" + externalLinks + "\n" +
                '}';
    }


}

