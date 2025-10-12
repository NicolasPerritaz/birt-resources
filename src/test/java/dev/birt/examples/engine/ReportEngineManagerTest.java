package dev.birt.examples.engine;

import org.junit.jupiter.api.Test;

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


}