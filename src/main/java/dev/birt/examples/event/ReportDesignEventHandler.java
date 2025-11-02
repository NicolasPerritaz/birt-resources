package dev.birt.examples.event;

import org.eclipse.birt.report.engine.api.IEngineTask;
import org.eclipse.birt.report.engine.api.script.IReportContext;
import org.eclipse.birt.report.engine.api.script.ScriptException;
import org.eclipse.birt.report.engine.api.script.element.IReportDesign;
import org.eclipse.birt.report.engine.api.script.eventhandler.IReportEventHandler;
import org.eclipse.birt.report.engine.api.script.instance.IPageInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


public class ReportDesignEventHandler implements IReportEventHandler {

    private static final Logger logger = LoggerFactory.getLogger(ReportDesignEventHandler.class);
    private static final Map<Integer, String> ENGINE_TASKS = Map.of(
            IEngineTask.TASK_RUN, "Run",
            IEngineTask.TASK_RENDER, "Render",
            IEngineTask.TASK_RUNANDRENDER, "Run and Render"
    );

    @Override
    public void initialize(IReportContext reportContext) throws ScriptException {
        logger.debug("{} - initialize", ENGINE_TASKS.get(reportContext.getTaskType()));
    }

    @Override
    public void beforeFactory(IReportDesign iReportDesign, IReportContext reportContext) throws ScriptException {
        logger.debug("{} - beforeFactory", ENGINE_TASKS.get(reportContext.getTaskType()));
    }

    @Override
    public void afterFactory(IReportContext reportContext) throws ScriptException {
        logger.debug("{} - afterFactory", ENGINE_TASKS.get(reportContext.getTaskType()));
    }

    @Override
    public void beforeRender(IReportContext reportContext) throws ScriptException {
        logger.debug("{} - beforeRender", ENGINE_TASKS.get(reportContext.getTaskType()));
    }

    @Override
    public void afterRender(IReportContext reportContext) throws ScriptException {
        logger.debug("{} - afterRender", ENGINE_TASKS.get(reportContext.getTaskType()));
    }

    @Override
    public void onPrepare(IReportContext reportContext) throws ScriptException {
        logger.debug("{} - onPrepare", ENGINE_TASKS.get(reportContext.getTaskType()));
    }

    @Override
    public void onPageStart(IPageInstance iPageInstance, IReportContext reportContext) throws ScriptException {
        logger.debug("{} - onPageStart", ENGINE_TASKS.get(reportContext.getTaskType()));
    }

    @Override
    public void onPageEnd(IPageInstance iPageInstance, IReportContext reportContext) throws ScriptException {
        logger.debug("{} - onPageEnd", ENGINE_TASKS.get(reportContext.getTaskType()));
    }
}
