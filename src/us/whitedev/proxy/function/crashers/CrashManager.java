package us.whitedev.proxy.function.crashers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CrashManager {
    private List<Crasher> crashers = new ArrayList<>();
    private static CrashManager crashManager;

    public void addMethod(Crasher... crashers) {
        this.crashers.addAll(Arrays.asList(crashers));
    }

    public static CrashManager getManager() {
        if (crashManager == null) {
            crashManager = new CrashManager();
        }
        return crashManager;
    }

    public void handleMethod(String name, int packets) {
        Optional<Crasher> methodOptional = crashers.stream().filter((crasher) -> crasher.check(name)).findFirst();
        if (methodOptional.isPresent()) {
            Crasher crasher = methodOptional.get();
            crasher.onMethod(name, packets);
        }
    }

    public List<String> allMethodsList() {
        List<String> crasherNames = new ArrayList<>();

        for (Crasher crasher : this.crashers) {
            crasherNames.add(crasher.getName());
        }

        return crasherNames;
    }
}
