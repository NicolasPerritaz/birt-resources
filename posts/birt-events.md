# BIRT Generation phases, tasks and events

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