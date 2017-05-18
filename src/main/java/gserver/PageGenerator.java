package gserver;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import java.util.Objects;

/**
 * @author Alexander Kanunnikov
 */

public class PageGenerator {

    private static final Logger LOGGER = Logger.getLogger(PageGenerator.class);
    private static final Configuration CFG = new Configuration(Configuration.VERSION_2_3_23);
    private static final String HTML_DIR = "templates";

    static {
        try {
            CFG.setDirectoryForTemplateLoading(new File(HTML_DIR));
        } catch (IOException e) {
            e.printStackTrace();
        }
        CFG.setDefaultEncoding("UTF-8");
        CFG.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        CFG.setLogTemplateExceptions(false);
    }

    public static String getPage(String fileName, Map<String, Object> data) {
        Writer stream = new StringWriter();
        try {
            Template template = CFG.getTemplate(fileName);
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            LOGGER.error("Ошибка при подготовке страницы.", e);
        }
        return stream.toString();
    }

}