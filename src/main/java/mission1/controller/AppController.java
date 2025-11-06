package mission1.controller;

import mission1.service.QuoteService;
import mission1.view.InputView;
import mission1.view.OutputView;

public class AppController {

    private final QuoteService service = new QuoteService();
    private final QuoteHandler handler = new QuoteHandler(service);

    public void run() {
        OutputView.printHeader();

        while (true) {
            String command = InputView.commandInput();

            if (checkExit(command)) break;

            handler.handleCommand(command);
        }
    }

    private boolean checkExit(String command) {
        if ("종료".equals(command)) {
            OutputView.printExit();
            return true;
        }
        return false;
    }
}