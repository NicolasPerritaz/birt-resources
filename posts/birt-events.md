---
title: BIRT Generation phases, tasks and events
menu_order: 1
taxonomy:
    doc_category: BIRT
---


## Overview



## Report Generation Phases

BIRT report generation consists of three main phases:

1. Preparation Phase

- Parameter processing
- Resource initialization
- Report setup

2. Generation Phase

- Data source connection
- Data fetching
- Report item creation
- Data binding

3. Presentation Phase

- Layout calculation
- Style application
- Content rendering
- Output generation

## Tasks

BIRT provides different ways to execute report generation:

RunTask:

- Executes Preparation and Generation phases
- Creates report document with data but no formatting
- Useful for preparing reports that will be rendered later

```java
// Create and run a RunTask
IReportEngine engine = // ... initialize engine
IRunTask runTask = engine.createRunTask(reportDesign);
runTask.setParameterValue("paramName", "paramValue");
runTask.run("output.rptdocument");
runTask.close();
```


RenderTask:

- Executes Presentation phase only
- Takes a report document as input
- Applies formatting and generates final output

```java
// Create and run a RenderTask
IReportEngine engine = // ... initialize engine
IReportDocument reportDoc = engine.openReportDocument("input.rptdocument");
IRenderTask renderTask = engine.createRenderTask(reportDoc);
renderTask.setRenderOption(new PDFRenderOption());
renderTask.render("output.pdf");
renderTask.close();
reportDoc.close();
```

RunAndRenderTask:

- Executes all three phases in sequence (Preparation, Generation, Presentation)
- Combines functionality of RunTask and RenderTask
- Most commonly used for direct report generation and viewing

```java
// Create and run a RunAndRenderTask
IReportEngine engine = // ... initialize engine
IRunAndRenderTask runAndRenderTask = engine.createRunAndRenderTask(reportDesign);
runAndRenderTask.setParameterValue("paramName", "paramValue");
runAndRenderTask.setRenderOption(new PDFRenderOption());
runAndRenderTask.run();
runAndRenderTask.close();
```

## Events

In BIRT, an event is a point in the report generation or rendering process where you can inject custom logic (usually
with JavaScript or Java) to modify the behavior of the report.

### Report Design events

Let's create a simple event handler that will log the event name and the task type. We will use both the separated
run and render tasks, as well as the run and render tasks combined.

```java
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
```

Separated run and render task:

```
11:23:51.436 [main] DEBUG d.b.e.event.ReportDesignEventHandler - Run - initialize
11:23:51.442 [main] DEBUG d.b.e.event.ReportDesignEventHandler - Run - onPrepare
11:23:51.473 [main] DEBUG d.b.e.event.ReportDesignEventHandler - Run - beforeFactory
11:23:52.202 [main] DEBUG d.b.e.event.ReportDesignEventHandler - Run - onPageStart
11:23:52.202 [main] DEBUG d.b.e.event.ReportDesignEventHandler - Run - onPageEnd
11:23:52.206 [main] DEBUG d.b.e.event.ReportDesignEventHandler - Run - afterFactory
11:23:52.229 [main] DEBUG d.b.e.event.ReportDesignEventHandler - Render - initialize
11:23:52.230 [main] DEBUG d.b.e.event.ReportDesignEventHandler - Render - beforeRender
11:23:52.298 [main] DEBUG d.b.e.event.ReportDesignEventHandler - Render - afterRender
```

Combined run and render task:

```
11:34:03.503 [main] DEBUG d.b.e.event.ReportDesignEventHandler - Run and Render - initialize
11:34:03.510 [main] DEBUG d.b.e.event.ReportDesignEventHandler - Run and Render - onPrepare
11:34:03.543 [main] DEBUG d.b.e.event.ReportDesignEventHandler - Run and Render - beforeFactory
11:34:03.543 [main] DEBUG d.b.e.event.ReportDesignEventHandler - Run and Render - beforeRender
11:34:04.302 [main] DEBUG d.b.e.event.ReportDesignEventHandler - Run and Render - onPageStart
11:34:04.303 [main] DEBUG d.b.e.event.ReportDesignEventHandler - Run and Render - onPageEnd
11:34:04.310 [main] DEBUG d.b.e.event.ReportDesignEventHandler - Run and Render - afterRender
11:34:04.312 [main] DEBUG d.b.e.event.ReportDesignEventHandler - Run and Render - afterFactory
```

Notice that when running separated run and render tasks the `initialize` event is fired twice, once for the run task and
once for the render task
whereas when running the combined run and render task the `initialize` event is fired only once.
The order of the events is also different: In the combined run and render task the `beforeRender` and `afterRender`
events
are fired before the `afterFactory` event and the `onPageStart` and `onPageEnd` events are fired between the
`beforeRender` and `afterRender` events.
It's important to note that the same code may not have the same behavior when running the combined run and render task
as when running the separated run and render tasks.

Let's break down the events and their use cases:

initialize (Preparation Phase)

- Fired: Once at the beginning of report execution before any data is fetched
- Use cases: Initialize global variables, set report parameters, configure data sources
- Warning: Cannot access report data or elements as they haven't been created yet

beforeFactory (Generation Phase)

- Fired: Before data fetching and report item instantiation begins
- Use cases: Modify data source settings, set up data filtering conditions
- Warning: Report elements are not yet created, cannot modify their properties

beforeRender (Generation Phase)

- Fired: Before the report starts rendering but after data is fetched
- Use cases: Modify report data, calculate totals, prepare rendering settings
- Warning: Page layout is not yet determined, cannot access page-specific information

afterFactory (Generation Phase)

- Fired: After data is fetched but before rendering
- Use cases: Post-process data, modify report item instances
- Warning: Visual properties and layout not yet applied

afterRender (Presentation Phase)

- Fired: After report rendering is complete
- Use cases: Clean up resources, export report to different formats
- Warning: Too late to modify report content or layout

onPageStart (Presentation Phase)

- Fired: At the beginning of each page during rendering
- Use cases: Add page-specific headers, modify page layout
- Warning: Cannot modify content of other pages

onPageEnd (Presentation Phase)

- Fired: At the end of each page during rendering
- Use cases: Add page footers, page numbering, verify page content
- Warning: Cannot modify content of previous pages