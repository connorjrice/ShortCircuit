package ShortCircuit.Threading;

import ShortCircuit.MapXML.EnemyParams;
import ShortCircuit.MapXML.Parser;
import ShortCircuit.MapXML.ShortCircuitParser;
import java.util.concurrent.Callable;
import javax.xml.xpath.XPath;
import org.w3c.dom.Document;

/**
 *
 * @author Development
 */
public class GetEnemyParams implements Callable {
    private Parser parser;
    
    public GetEnemyParams(XPath xpath, Document doc) {
        this.parser = new ShortCircuitParser(xpath, doc);
    }

    public Object call() throws Exception {
        return new EnemyParams(parser.parseCreepList());
    }


    
}
