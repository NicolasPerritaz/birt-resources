package dev.birt.examples.engine;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class ReportEngineManagerTest {

    @Test
    void startStop() {
        ReportEngineManager engine = new ReportEngineManager();
        engine.start();
        assertThat(engine.isStarted()).isTrue();
        engine.shutdown();
        assertThat(engine.isStarted()).isFalse();
    }

    @Test
    void runReport() {
         ReportEngineManager engine = new ReportEngineManager();
         engine.start();
         String designPath = "reporting/report/events.rptdesign";
         try (InputStream reportDesign = new FileInputStream(designPath)) {
             engine.runReport(reportDesign,
                     new RunConfiguration(true),
                     new RenderConfiguration(OutputFormat.PDF, Path.of("output.pdf")));
             engine.shutdown();

         } catch (IOException e) {
             throw new RuntimeException(e);
         }
    }


}