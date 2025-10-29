package dev.birt.examples.event;

import dev.birt.examples.engine.ReportEngineManager;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class ReportDesignEventHandlerIT {

    private ReportEngineManager engine = new ReportEngineManager();

    @BeforeEach
    void setup() {
        engine.start();
    }

    void testReportDesignEventHandler() {

    }

    void tearDown() {
        engine.shutdown();
    }

}