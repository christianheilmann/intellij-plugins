package com.jetbrains.lang.dart.ide.runner.test;

import com.google.gson.JsonSyntaxException;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.testframework.TestConsoleProperties;
import com.intellij.execution.testframework.sm.runner.BaseSMTRunnerTestCase;
import com.intellij.execution.testframework.sm.runner.GeneralTestEventsProcessor;
import com.intellij.execution.testframework.sm.runner.SMTestLocator;
import com.intellij.execution.testframework.sm.runner.TestProxyPrinterProvider;
import com.intellij.execution.testframework.sm.runner.events.*;
import com.intellij.execution.testframework.sm.runner.ui.MockPrinter;
import com.intellij.execution.testframework.sm.runner.ui.SMTRunnerConsoleView;
import com.intellij.execution.testframework.sm.runner.ui.SMTestRunnerResultsForm;
import com.intellij.execution.testframework.ui.TestsOutputConsolePrinter;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.util.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DartTestEventsConverterTest extends BaseSMTRunnerTestCase {

  // Do not reformat this list.
  private static final String[] Sample1Events = {
    // @formatter:off
    "/usr/local/opt/dart/libexec/bin/dart --ignore-unrecognized-flags --checked --enable-vm-service:51706 --trace_service_pause_events file:///usr/local/opt/dart/libexec/bin/snapshots/pub.dart.snapshot run test:test -r json test/formatter_test.dart -n \"line endings\"\n",
    "Observatory listening on http://127.0.0.1:51706\n",
    "\n",
    "{\"protocolVersion\":\"0.1.0\",\"runnerVersion\":\"0.12.6\",\"type\":\"start\",\"time\":0}\n",
    "{\"test\":{\"id\":0,\"name\":\"loading test/formatter_test.dart\",\"groupIDs\":[],\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"testStart\",\"time\":0}\n",
    "{\"testID\":0,\"result\":\"success\",\"hidden\":true,\"type\":\"testDone\",\"time\":877}\n",
    "{\"group\":{\"id\":1,\"parentID\":null,\"name\":null,\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"group\",\"time\":880}\n",
    "{\"group\":{\"id\":2,\"parentID\":1,\"name\":\"line endings\",\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"group\",\"time\":880}\n",
    "{\"test\":{\"id\":3,\"name\":\"line endings uses given line ending\",\"groupIDs\":[1,2],\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"testStart\",\"time\":880}\n",
    "{\"testID\":3,\"result\":\"success\",\"hidden\":false,\"type\":\"testDone\",\"time\":970}\n",
    "{\"test\":{\"id\":4,\"name\":\"line endings fails once\",\"groupIDs\":[1,2],\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"testStart\",\"time\":971}\n",
    "{\"testID\":4,\"message\":\"something\",\"type\":\"print\",\"time\":973}\n",
    "{\"testID\":4,\"error\":\"boom\",\"stackTrace\":\"package:test                    fail\\ntest/formatter_test.dart 108:7  main.<fn>.<fn>\\n\",\"isFailure\":true,\"type\":\"error\",\"time\":1088}\n",
    "{\"testID\":4,\"result\":\"failure\",\"hidden\":false,\"type\":\"testDone\",\"time\":1088}\n",
    "{\"test\":{\"id\":5,\"name\":\"line endings fails twice\",\"groupIDs\":[1,2],\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"testStart\",\"time\":1088}\n",
    "{\"testID\":5,\"message\":\"message\",\"type\":\"print\",\"time\":1090}\n",
    "{\"testID\":5,\"error\":\"No top-level method 'fail' with matching arguments declared.\\n\\nNoSuchMethodError: incorrect number of arguments passed to method named 'fail'\\nReceiver: top-level\\nTried calling: fail(...)\\nFound: fail(String)\",\"stackTrace\":\"dart:core                       NoSuchMethodError._throwNew\\ntest/formatter_test.dart 112:7  main.<fn>.<fn>\\n\",\"isFailure\":false,\"type\":\"error\",\"time\":1128}\n",
    "{\"testID\":5,\"result\":\"error\",\"hidden\":false,\"type\":\"testDone\",\"time\":1128}\n",
    "{\"test\":{\"id\":6,\"name\":\"line endings fails thrice\",\"groupIDs\":[1,2],\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"testStart\",\"time\":1128}\n",
    "{\"testID\":6,\"error\":\"Expected: 'alphabet\\\\n'\\n  'soup'\\n  Actual: 'alpha\\\\n'\\n  'beta'\\n   Which: is different.\\nExpected: alphabet\\\\nsoup\\n  Actual: alpha\\\\nbeta\\n               ^\\n Differ at offset 5\\n\",\"stackTrace\":\"package:test                    expect\\ntest/formatter_test.dart 115:7  main.<fn>.<fn>\\n\",\"isFailure\":true,\"type\":\"error\",\"time\":1154}\n",
    "{\"testID\":6,\"result\":\"failure\",\"hidden\":false,\"type\":\"testDone\",\"time\":1155}\n",
    "{\"group\":{\"id\":7,\"parentID\":2,\"name\":\"line endings infers\",\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"group\",\"time\":1155}\n",
    "{\"test\":{\"id\":8,\"name\":\"line endings infers \\\\r\\\\n if the first newline uses that\",\"groupIDs\":[1,2,7],\"metadata\":{\"skip\":true,\"skipReason\":null}},\"type\":\"testStart\",\"time\":1155}\n",
    "{\"testID\":8,\"result\":\"success\",\"hidden\":false,\"type\":\"testDone\",\"time\":1156}\n",
    "{\"test\":{\"id\":9,\"name\":\"line endings infers \\\\n if the first newline uses that\",\"groupIDs\":[1,2,7],\"metadata\":{\"skip\":true,\"skipReason\":\"just because\"}},\"type\":\"testStart\",\"time\":1156}\n",
    "{\"testID\":9,\"result\":\"success\",\"hidden\":false,\"type\":\"testDone\",\"time\":1157}\n",
    "{\"test\":{\"id\":10,\"name\":\"line endings defaults to \\\\n if there are no newlines\",\"groupIDs\":[1,2],\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"testStart\",\"time\":1157}\n",
    "{\"testID\":10,\"result\":\"success\",\"hidden\":false,\"type\":\"testDone\",\"time\":1159}\n",
    "{\"test\":{\"id\":11,\"name\":\"line endings handles Windows line endings in multiline strings\",\"groupIDs\":[1,2],\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"testStart\",\"time\":1159}\n",
    "{\"testID\":11,\"result\":\"success\",\"hidden\":false,\"type\":\"testDone\",\"time\":1173}\n",
    "{\"success\":false,\"type\":\"done\",\"time\":1175}\n",
    "\n",
    "Process finished with exit code 1\n",
    // @formatter:on
  };

  // Do not reformat this list.
  private static final String[] Sample1Signals = {
    // @formatter:off
    "start uses given line ending",
    "finish uses given line ending",
    "start fails once",
    "print fails once \nsomething\n",
    "fail fails once false",
    "print fails once package:test                    fail\ntest/formatter_test.dart 108:7  main.<fn>.<fn>\n",
    "start fails twice",
    "print fails twice \nmessage\n",
    "fail fails twice true",
    "print fails twice dart:core                       NoSuchMethodError._throwNew\ntest/formatter_test.dart 112:7  main.<fn>.<fn>\n",
    "start fails thrice",
    "fail fails thrice false",
    "print fails thrice package:test                    expect\ntest/formatter_test.dart 115:7  main.<fn>.<fn>\n",
    "start \\r\\n if the first newline uses that",
    "skip \\r\\n if the first newline uses that Test ignored.",
    "start \\n if the first newline uses that",
    "skip \\n if the first newline uses that just because",
    "start defaults to \\n if there are no newlines",
    "finish defaults to \\n if there are no newlines",
    "start handles Windows line endings in multiline strings",
    "finish handles Windows line endings in multiline strings",
    // @formatter:on
  };

  private static final int[] Sample1Parents = {0, 0, 0, 2, 2, 2, 2, 2, 7, 7, 2, 2};

  // Do not reformat this list.
  private static final String[] Sample2Events = {
    // @formatter:off
    "/usr/local/opt/dart/libexec/bin/dart --ignore-unrecognized-flags --checked --trace_service_pause_events file:///usr/local/opt/dart/libexec/bin/snapshots/pub.dart.snapshot run test:test -r json /Users/messick/src/quiver-dart/test/\n",
    "{\"protocolVersion\":\"0.1.0\",\"runnerVersion\":\"0.12.9-dev (from ../test-master)\",\"type\":\"start\",\"time\":0}\n",
    "{\"suite\":{\"id\":0,\"platform\":\"vm\",\"path\":\"/Users/messick/src/quiver-dart/test/async/collect_test.dart\"},\"type\":\"suite\",\"time\":0}\n",
    "{\"test\":{\"id\":1,\"name\":\"loading /Users/messick/src/quiver-dart/test/async/collect_test.dart\",\"suiteID\":0,\"groupIDs\":[],\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"testStart\",\"time\":0}\n",
    "{\"suite\":{\"id\":2,\"platform\":\"vm\",\"path\":\"/Users/messick/src/quiver-dart/test/async/concat_test.dart\"},\"type\":\"suite\",\"time\":1}\n",
    "{\"test\":{\"id\":3,\"name\":\"loading /Users/messick/src/quiver-dart/test/async/concat_test.dart\",\"suiteID\":2,\"groupIDs\":[],\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"testStart\",\"time\":1}\n",
    "{\"suite\":{\"id\":4,\"platform\":\"vm\",\"path\":\"/Users/messick/src/quiver-dart/test/async/countdown_timer_test.dart\"},\"type\":\"suite\",\"time\":1}\n",
    "{\"test\":{\"id\":5,\"name\":\"loading /Users/messick/src/quiver-dart/test/async/countdown_timer_test.dart\",\"suiteID\":4,\"groupIDs\":[],\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"testStart\",\"time\":2}\n",
    "{\"suite\":{\"id\":6,\"platform\":\"vm\",\"path\":\"/Users/messick/src/quiver-dart/test/async/create_timer_test.dart\"},\"type\":\"suite\",\"time\":2}\n",
    "{\"test\":{\"id\":7,\"name\":\"loading /Users/messick/src/quiver-dart/test/async/create_timer_test.dart\",\"suiteID\":6,\"groupIDs\":[],\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"testStart\",\"time\":2}\n",
    "{\"suite\":{\"id\":8,\"platform\":\"vm\",\"path\":\"/Users/messick/src/quiver-dart/test/async/enumerate_test.dart\"},\"type\":\"suite\",\"time\":2}\n",
    "{\"test\":{\"id\":9,\"name\":\"loading /Users/messick/src/quiver-dart/test/async/enumerate_test.dart\",\"suiteID\":8,\"groupIDs\":[],\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"testStart\",\"time\":2}\n",
    "{\"suite\":{\"id\":10,\"platform\":\"vm\",\"path\":\"/Users/messick/src/quiver-dart/test/async/future_group_test.dart\"},\"type\":\"suite\",\"time\":3}\n",
    "{\"test\":{\"id\":11,\"name\":\"loading /Users/messick/src/quiver-dart/test/async/future_group_test.dart\",\"suiteID\":10,\"groupIDs\":[],\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"testStart\",\"time\":3}\n",
    "{\"suite\":{\"id\":12,\"platform\":\"vm\",\"path\":\"/Users/messick/src/quiver-dart/test/async/future_stream_test.dart\"},\"type\":\"suite\",\"time\":3}\n",
    "{\"test\":{\"id\":13,\"name\":\"loading /Users/messick/src/quiver-dart/test/async/future_stream_test.dart\",\"suiteID\":12,\"groupIDs\":[],\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"testStart\",\"time\":4}\n",
    "{\"suite\":{\"id\":14,\"platform\":\"vm\",\"path\":\"/Users/messick/src/quiver-dart/test/async/iteration_test.dart\"},\"type\":\"suite\",\"time\":4}\n",
    "{\"test\":{\"id\":15,\"name\":\"loading /Users/messick/src/quiver-dart/test/async/iteration_test.dart\",\"suiteID\":14,\"groupIDs\":[],\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"testStart\",\"time\":4}\n",
    "{\"testID\":5,\"result\":\"success\",\"hidden\":true,\"type\":\"testDone\",\"time\":723}\n",
    "{\"group\":{\"id\":16,\"suiteID\":4,\"parentID\":null,\"name\":null,\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"group\",\"time\":730}\n",
    "{\"group\":{\"id\":17,\"suiteID\":4,\"parentID\":16,\"name\":\"CountdownTimer\",\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"group\",\"time\":730}\n",
    "{\"test\":{\"id\":18,\"name\":\"CountdownTimer should countdown\",\"suiteID\":4,\"groupIDs\":[16,17],\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"testStart\",\"time\":732}\n",
    "{\"count\":11,\"type\":\"allSuites\",\"time\":732}",
    "{\"testID\":1,\"result\":\"success\",\"hidden\":true,\"type\":\"testDone\",\"time\":733}\n",
    "{\"group\":{\"id\":19,\"suiteID\":0,\"parentID\":null,\"name\":null,\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"group\",\"time\":733}\n",
    "{\"group\":{\"id\":20,\"suiteID\":0,\"parentID\":19,\"name\":\"collect\",\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"group\",\"time\":734}\n",
    "{\"test\":{\"id\":21,\"name\":\"collect should produce no events for no futures\",\"suiteID\":0,\"groupIDs\":[19,20],\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"testStart\",\"time\":734}\n",
    "{\"testID\":7,\"result\":\"success\",\"hidden\":true,\"type\":\"testDone\",\"time\":734}\n",
    "{\"group\":{\"id\":22,\"suiteID\":6,\"parentID\":null,\"name\":null,\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"group\",\"time\":734}\n",
    "{\"group\":{\"id\":23,\"suiteID\":6,\"parentID\":22,\"name\":\"createTimer\",\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"group\",\"time\":734}\n",
    "{\"test\":{\"id\":24,\"name\":\"createTimer should be assignable to CreateTimer\",\"suiteID\":6,\"groupIDs\":[22,23],\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"testStart\",\"time\":735}\n",
    "{\"testID\":11,\"result\":\"success\",\"hidden\":true,\"type\":\"testDone\",\"time\":735}\n",
    "{\"group\":{\"id\":25,\"suiteID\":10,\"parentID\":null,\"name\":null,\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"group\",\"time\":735}\n",
    "{\"group\":{\"id\":26,\"suiteID\":10,\"parentID\":25,\"name\":\"FutureGroup\",\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"group\",\"time\":735}\n",
    "{\"test\":{\"id\":27,\"name\":\"FutureGroup should complete when all added futures are complete\",\"suiteID\":10,\"groupIDs\":[25,26],\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"testStart\",\"time\":735}\n",
    "{\"testID\":9,\"result\":\"success\",\"hidden\":true,\"type\":\"testDone\",\"time\":737}\n",
    "{\"testID\":3,\"result\":\"success\",\"hidden\":true,\"type\":\"testDone\",\"time\":748}\n",
    "{\"testID\":15,\"result\":\"success\",\"hidden\":true,\"type\":\"testDone\",\"time\":751}\n",
    "{\"testID\":13,\"result\":\"success\",\"hidden\":true,\"type\":\"testDone\",\"time\":755}\n",
    "{\"testID\":24,\"result\":\"success\",\"hidden\":false,\"type\":\"testDone\",\"time\":773}\n",
    "{\"group\":{\"id\":28,\"suiteID\":6,\"parentID\":22,\"name\":\"createTimerPeriodic\",\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"group\",\"time\":774}\n",
    "{\"test\":{\"id\":29,\"name\":\"createTimerPeriodic should be assignable to CreateTimerPeriodic\",\"suiteID\":6,\"groupIDs\":[22,28],\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"testStart\",\"time\":774}\n",
    "{\"testID\":21,\"result\":\"success\",\"hidden\":false,\"type\":\"testDone\",\"time\":776}\n",
    "{\"test\":{\"id\":30,\"name\":\"collect should produce events for future completions in input order\",\"suiteID\":0,\"groupIDs\":[19,20],\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"testStart\",\"time\":777}\n",
    "{\"testID\":29,\"result\":\"success\",\"hidden\":false,\"type\":\"testDone\",\"time\":794}\n",
    "{\"suite\":{\"id\":31,\"platform\":\"vm\",\"path\":\"/Users/messick/src/quiver-dart/test/async/metronome_test.dart\"},\"type\":\"suite\",\"time\":798}\n",
    "{\"test\":{\"id\":32,\"name\":\"loading /Users/messick/src/quiver-dart/test/async/metronome_test.dart\",\"suiteID\":31,\"groupIDs\":[],\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"testStart\",\"time\":798}\n",
    "{\"group\":{\"id\":33,\"suiteID\":8,\"parentID\":null,\"name\":null,\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"group\",\"time\":798}\n",
    "{\"group\":{\"id\":34,\"suiteID\":8,\"parentID\":33,\"name\":\"enumerate\",\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"group\",\"time\":798}\n",
    "{\"test\":{\"id\":35,\"name\":\"enumerate should add indices to its argument\",\"suiteID\":8,\"groupIDs\":[33,34],\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"testStart\",\"time\":799}\n",
    "{\"testID\":27,\"result\":\"success\",\"hidden\":false,\"type\":\"testDone\",\"time\":799}\n",
    "{\"test\":{\"id\":36,\"name\":\"FutureGroup should throw if adding a future after the group is completed\",\"suiteID\":10,\"groupIDs\":[25,26],\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"testStart\",\"time\":801}\n",
    "{\"testID\":36,\"result\":\"success\",\"hidden\":false,\"type\":\"testDone\",\"time\":805}\n",
    "{\"success\":true,\"type\":\"done\",\"time\":4877}\n",
    "\n",
    "Process finished with exit code 1\n",
    // @formatter:on
  };

  // Do not reformat this list.
  private static final String[] Sample2Signals = {
    // @formatter:off
    "start should countdown",
    "start should produce no events for no futures",
    "start should be assignable to CreateTimer",
    "start should complete when all added futures are complete",
    "finish should be assignable to CreateTimer",
    "start should be assignable to CreateTimerPeriodic",
    "finish should produce no events for no futures",
    "start should produce events for future completions in input order",
    "finish should be assignable to CreateTimerPeriodic",
    "start should add indices to its argument",
    "finish should complete when all added futures are complete",
    "start should throw if adding a future after the group is completed",
    "finish should throw if adding a future after the group is completed",
    // @formatter:on
  };

  private SMTRunnerConsoleView myConsole;
  private DartTestEventsConverter myEventsConverter;
  private DartTestEventsProcessor myEventsProcessor;
  private DefaultTreeModel myTreeModel;
  private DefaultMutableTreeNode myParentNode;
  private SMTestRunnerResultsForm myResultsViewer;
  private MockPrinter myMockResettablePrinter;
  private Map<Integer, DefaultMutableTreeNode> myNodes;

  public void testSample1() throws Exception {
    runTest(Sample1Events, Sample1Signals, Sample1Parents);
  }

  public void testSample2() throws Exception {
    runTest(Sample2Events, Sample2Signals, new int[]{});
  }

  public void testLoadFailure() throws Exception {
    String[] events =
      {"/usr/local/opt/dart/libexec/bin/dart --ignore-unrecognized-flags --checked --enable-vm-service:50383 --trace_service_pause_events file:///usr/local/opt/dart/libexec/bin/snapshots/pub.dart.snapshot run test:test -r json test/formatter_test.dart -n \"line endings\"\n",
        "Observatory listening on http://127.0.0.1:50383\n", "\n",
        "{\"protocolVersion\":\"0.1.0\",\"runnerVersion\":\"0.12.6\",\"type\":\"start\",\"time\":0}\n",
        "{\"test\":{\"id\":0,\"name\":\"loading test/formatter_test.dart\",\"groupIDs\":[],\"metadata\":{\"skip\":false,\"skipReason\":null}},\"type\":\"testStart\",\"time\":0}\n",
        "{\"testID\":0,\"error\":\"Failed to load \\\"test/formatter_test.dart\\\":\\nline 117 pos 69: named argument expected\\n    test('infers \\\\\\\\r\\\\\\\\n if the first newline uses that', skip:true, () {\\n                                                                    ^\",\"stackTrace\":\"\",\"isFailure\":false,\"type\":\"error\",\"time\":497}\n",
        "{\"testID\":0,\"result\":\"error\",\"hidden\":false,\"type\":\"testDone\",\"time\":499}\n",
        "{\"success\":false,\"type\":\"done\",\"time\":500}\n", "\n", "Process finished with exit code 1\n",};
    String[] signals = {"start Failed to load", "fail Failed to load false",};
    runTest(events, signals, new int[]{});
  }

  private void runTest(String[] jsonEvents, String[] signals, int[] parents) {
    DartTestEventsConverter parser = myEventsConverter;
    Key key = new Key("stdout");
    for (String event : jsonEvents) {
      try {
        parser.process(event, key);
      }
      catch (JsonSyntaxException ex) {
        // ignored
      }
    }
    assertEquals(signals.length, myEventsProcessor.signals.size());
    int index = 0;
    for (String signal : myEventsProcessor.signals) {
      assertEquals(signals[index++], signal);
    }
    for (int childIdx = 0; childIdx < parents.length; childIdx++) {
      int parentIdx = parents[childIdx];
      if (parentIdx > 0) {
        DefaultMutableTreeNode child = myNodes.get(childIdx);
        DefaultMutableTreeNode parent = myNodes.get(parentIdx);
        assertEquals(parent, child.getParent());
      }
    }
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    myNodes = new HashMap<Integer, DefaultMutableTreeNode>();
    final ExecutionEnvironment environment = new ExecutionEnvironment();
    myMockResettablePrinter = new MockPrinter(true);
    TestConsoleProperties consoleProperties = createConsoleProperties();
    myConsole = new MyConsoleView(consoleProperties, environment);
    myConsole.initUI();
    myResultsViewer = myConsole.getResultsViewer();
    myEventsConverter = new DartTestEventsConverter(DartTestRunningState.DART_FRAMEWORK_NAME, consoleProperties);
    myEventsProcessor = new DartTestEventsProcessor(consoleProperties.getProject(), DartTestRunningState.DART_FRAMEWORK_NAME);
    myEventsProcessor.addEventsListener(myResultsViewer);
    myEventsConverter.setProcessor(myEventsProcessor);
    myTreeModel = myResultsViewer.getTreeView() == null ? null : (DefaultTreeModel)myResultsViewer.getTreeView().getModel();
    assertNotNull(myTreeModel);
    myParentNode = (DefaultMutableTreeNode)myTreeModel.getRoot();
    myEventsProcessor.onStartTesting();
  }

  @Override
  protected void tearDown() throws Exception {
    try {
      Disposer.dispose(myEventsProcessor);
      Disposer.dispose(myConsole);
    }
    finally {
      super.tearDown();
    }
  }

  protected TestConsoleProperties createConsoleProperties() {
    TestConsoleProperties properties = super.createConsoleProperties();
    TestConsoleProperties.HIDE_PASSED_TESTS.set(properties, false);
    TestConsoleProperties.OPEN_FAILURE_LINE.set(properties, false);
    TestConsoleProperties.SCROLL_TO_SOURCE.set(properties, false);
    TestConsoleProperties.SELECT_FIRST_DEFECT.set(properties, false);
    TestConsoleProperties.TRACK_RUNNING_TEST.set(properties, false);
    return properties;
  }

  private class MyConsoleView extends SMTRunnerConsoleView {
    private final TestsOutputConsolePrinter myTestsOutputConsolePrinter;

    private MyConsoleView(final TestConsoleProperties consoleProperties, final ExecutionEnvironment environment) {
      super(consoleProperties);

      myTestsOutputConsolePrinter = new TestsOutputConsolePrinter(MyConsoleView.this, consoleProperties, null) {
        @Override
        public void print(final String text, final ConsoleViewContentType contentType) {
          myMockResettablePrinter.print(text, contentType);
        }
      };
    }

    @Override
    public TestsOutputConsolePrinter getPrinter() {
      return myTestsOutputConsolePrinter;
    }
  }

  private class DartTestEventsProcessor extends GeneralTestEventsProcessor {
    List<String> signals = new ArrayList<String>();

    public DartTestEventsProcessor(Project project, @NotNull String testFrameworkName) {
      super(project, testFrameworkName);
    }

    @Override
    public void onStartTesting() {
      myResultsViewer.performUpdate();
    }

    @Override
    public void onTestsCountInSuite(int count) {
    }

    @Override
    public void onTestStarted(@NotNull TestStartedEvent testStartedEvent) {
      signals.add("start " + testStartedEvent.getName());
      DefaultMutableTreeNode node = new DefaultMutableTreeNode(testStartedEvent.getName());
      myNodes.put(testStartedEvent.getId(), node);
      DefaultMutableTreeNode parentNode = myParentNode;
      int parentId = testStartedEvent.getParentId();
      if (parentId > 0) {
        parentNode = myNodes.get(parentId);
      }
      myTreeModel.insertNodeInto(node, parentNode, parentNode.getChildCount());
      myResultsViewer.performUpdate();
    }

    @Override
    public void onTestFinished(@NotNull TestFinishedEvent testFinishedEvent) {
      signals.add("finish " + testFinishedEvent.getName());
    }

    @Override
    public void onTestFailure(@NotNull TestFailedEvent testFailedEvent) {
      signals.add("fail " + testFailedEvent.getName() + " " + testFailedEvent.isTestError());
    }

    @Override
    public void onTestIgnored(@NotNull TestIgnoredEvent testIgnoredEvent) {
      signals.add("skip " + testIgnoredEvent.getName() + " " + testIgnoredEvent.getIgnoreComment());
    }

    @Override
    public void onTestOutput(@NotNull TestOutputEvent testOutputEvent) {
      signals.add("print " + testOutputEvent.getName() + " " + testOutputEvent.getText());
    }

    @Override
    public void onSuiteStarted(@NotNull TestSuiteStartedEvent suiteStartedEvent) {
      DefaultMutableTreeNode node = new DefaultMutableTreeNode(suiteStartedEvent.getName());
      myNodes.put(suiteStartedEvent.getId(), node);
      DefaultMutableTreeNode parentNode = myParentNode;
      int parentId = suiteStartedEvent.getParentId();
      if (parentId > 0) {
        parentNode = myNodes.get(parentId);
      }
      myTreeModel.insertNodeInto(node, parentNode, parentNode.getChildCount());
      myResultsViewer.performUpdate();
    }

    @Override
    public void onSuiteFinished(@NotNull TestSuiteFinishedEvent suiteFinishedEvent) {
    }

    @Override
    public void onUncapturedOutput(@NotNull String text, Key outputType) {
    }

    @Override
    public void onError(@NotNull String localizedMessage, @Nullable String stackTrace, boolean isCritical) {
      signals.add("error " + localizedMessage + " " + stackTrace);
    }

    @Override
    public void onFinishTesting() {
    }

    @Override
    public void onTestsReporterAttached() {
    }

    @Override
    public void setLocator(@NotNull SMTestLocator locator) {
    }

    @Override
    public void setPrinterProvider(@NotNull TestProxyPrinterProvider printerProvider) {
    }
  }
}
