package dev.birt.examples.event;

import org.eclipse.birt.report.engine.api.script.IReportContext;
import org.eclipse.birt.report.engine.api.script.ScriptException;
import org.eclipse.birt.report.engine.api.script.element.IReportDesign;
import org.eclipse.birt.report.engine.api.script.eventhandler.IReportEventHandler;
import org.eclipse.birt.report.engine.api.script.instance.IPageInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ReportDesignEventHandler implements IReportEventHandler {

    private static final Logger logger = LoggerFactory.getLogger(ReportDesignEventHandler.class);

    @Override
    public void initialize(IReportContext reportContext) throws ScriptException {
        logger.debug("initialize");
    }

    @Override
    public void beforeFactory(IReportDesign iReportDesign, IReportContext iReportContext) throws ScriptException {
        logger.debug("beforeFactory");
    }

    @Override
    public void afterFactory(IReportContext iReportContext) throws ScriptException {
        logger.debug("afterFactory");
    }

    @Override
    public void beforeRender(IReportContext iReportContext) throws ScriptException {
        logger.debug("beforeRender");
    }

    @Override
    public void afterRender(IReportContext iReportContext) throws ScriptException {
        logger.debug("afterRender");
    }

    @Override
    public void onPrepare(IReportContext iReportContext) throws ScriptException {
        logger.debug("onPrepare");
    }

    @Override
    public void onPageStart(IPageInstance iPageInstance, IReportContext iReportContext) throws ScriptException {
        logger.debug("onPageStart");
    }

    @Override
    public void onPageEnd(IPageInstance iPageInstance, IReportContext iReportContext) throws ScriptException {
        logger.debug("onPageEnd");
    }
}
