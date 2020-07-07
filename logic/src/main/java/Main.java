import com.hsm.service.BootstrapService;

public class Main {

    public static void main(String[] args) {

        BootstrapService service = new BootstrapService();
        service.startGuavaCache();
    }
}
