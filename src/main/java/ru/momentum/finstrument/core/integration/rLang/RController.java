package ru.momentum.finstrument.core.integration.rLang;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class RController {

    public void compute(){
        try {
            RConnection c =new RConnection();
            c.eval("a <- c(1, 3, 3, 4, 4, 2, 5, 3)");
            final REXP x = c.eval("as.data.frame(table(a))");
            //System.out.println(x.asString());
        } catch (RserveException  e) {
            e.printStackTrace();
        }
    }
}
