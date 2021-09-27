package ro.cburcea.playground.java11;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;

import static java.lang.System.out;

public class ProcessApiFeatures {

    public static void main(String[] args) {
        ProcessHandle self = ProcessHandle.current();

        long pid = self.pid();
        out.println("pid:" + pid);
        ProcessHandle.Info procInfo = self.info();

        Optional<String[]> procInfoArgs = procInfo.arguments();
        procInfoArgs.ifPresent(x -> out.println("procInfoArgs: " + Arrays.toString(x)));

        Optional<String> cmd = procInfo.commandLine();
        cmd.ifPresent(x -> out.println("cmd: " + x));

        Optional<Instant> startTime = procInfo.startInstant();
        startTime.ifPresent(x -> out.println("startTime: " + x));

        Optional<Duration> cpuUsage = procInfo.totalCpuDuration();
        cpuUsage.ifPresent(x -> out.println("cpuUsage: " + x));

        ProcessHandle.current().children()
                .forEach(ProcessHandle::destroy);

    }
}
