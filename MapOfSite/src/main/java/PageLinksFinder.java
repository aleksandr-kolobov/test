import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicLong;

public class PageLinksFinder extends RecursiveTask<Set<String>> {
    private static final Set<String> sumResults = new HashSet<>();//может тут вместо final
    private static final AtomicLong begin = new AtomicLong();//надо ставить volotile???
    private final Set<String> result;
    private final String attribute;
    private final String self;
    private final boolean flag;

    public PageLinksFinder(String attribute, String self) {
        this.attribute = attribute;
        this.self = self;
        result = new HashSet<>();
        result.add(self);
        synchronized (sumResults) {
            if (sumResults.contains(self)) {
                flag = true;
            } else {
                sumResults.add(self);
                flag = false;
            }
        }
    }

    @Override
    protected Set<String> compute() {
        if (flag) {
            return result;
        }
        List<PageLinksFinder> taskList = new ArrayList<>();
        try {
            long now = System.currentTimeMillis();
            long delay = Math.max(begin.get() + 50 - now, 0);
            Thread.sleep(delay);
            begin.set(now);
            Document doc = Jsoup.connect(self).get();
            Elements elements = doc.select("a[href]");
            for(Element el : elements){
                String str = el.attr("abs:href");
                boolean f;
                synchronized (sumResults) {
                    f = !sumResults.contains(str);
                }
                String tail = str.substring(str.length() - 4);
                if (f && str.contains(attribute) && !str.contains("#") && !str.contains("?")
                        && !tail.equals(".pdf") && !tail.equals(".jpg") && !tail.equals(".png")) {
                    PageLinksFinder task = new PageLinksFinder(attribute, str);
                    task.fork();
                    taskList.add(task);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        for (PageLinksFinder task : taskList) {
            synchronized (result) {
                result.addAll(task.join());
            }
        }
        return result;
    }
}
