package dev.birt.examples.engine;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;

import java.util.logging.Level;

public class ReportEngineManager {

    private IReportEngine engine;

    private boolean started = false;

    public void start() {

        final EngineConfig config = new EngineConfig();

        config.setLogConfig("log", Level.FINE);

        try {
            Platform.startup(config);
        } catch (BirtException e) {
            throw new IllegalStateException("unable to start the BIRT Platform", e);
        }
        started = true;

        IReportEngineFactory factory = (IReportEngineFactory) Platform
                .createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
        engine = factory.createReportEngine(config);
        engine.changeLogLevel(Level.WARNING);


    }

    public void shutdown() {
        engine.destroy();
        Platform.shutdown();
        started = false;
    }

    public boolean isStarted() {
        return started;
    }
}
