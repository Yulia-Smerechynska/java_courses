
import java.util.ArrayList;

public class SystemCheck {
    private int brokenServer = -1;
    private int brokenNode = -1;
    private boolean allServersWorkFine = false;

    public SystemCheck() {
        Cluster cluster = new Cluster(10);
        cluster.sendMessage();
        FailSearchEngine failSearchEngine = new FailSearchEngine(cluster);
       ArrayList<Integer> result =  failSearchEngine.search();


        if(result.size() > 0) {
            this.brokenServer = result.get(0);
            this.brokenNode = result.get(1);
        } else {
            this.allServersWorkFine = true;
        }

    }
}
