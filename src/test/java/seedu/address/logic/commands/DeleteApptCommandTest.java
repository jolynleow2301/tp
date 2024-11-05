package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalPatients.getTypicalClinicConnectSystem;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Appt;
import seedu.address.model.healthservice.HealthService;

public class DeleteApptCommandTest {

    private Model model = new ModelManager(getTypicalClinicConnectSystem(), new UserPrefs());

    @Test
    public void execute_validNricAndDateTime_deleteSuccessful() throws CommandException {
        // setup
        Nric nric = new Nric("S1234567A");
        Appt appt = new Appt(LocalDateTime.of(2024, 11, 15, 14, 0), 
            new HealthService("Consult"));
        DeleteApptCommand deleteApptCommand = new DeleteApptCommand(nric, appt.getDateTime());

        // execute
        CommandResult commandResult = deleteApptCommand.execute(model);

        // verify
        assertEquals(String.format(DeleteApptCommand.MESSAGE_DELETE_APPT_SUCCESS, appt), 
            commandResult.getFeedbackToUser());
        assertFalse(appt.equals(null));
    }

    @Test
    public void execute_invalidNric_throwsCommandException() {
        DeleteApptCommand deleteApptCommand = new DeleteApptCommand(new Nric("S9999999B"), 
            LocalDateTime.now());
        assertThrows(CommandException.class, () -> deleteApptCommand.execute(model), 
            Messages.MESSAGE_INVALID_PATIENT_NRIC);
    }

    @Test
    public void execute_invalidDateTime_throwsCommandException() {
        Nric validNric = new Nric("S1234567A");
        LocalDateTime invalidDateTime = LocalDateTime.MAX;
        DeleteApptCommand deleteApptCommand = new DeleteApptCommand(validNric, invalidDateTime);
        assertThrows(CommandException.class, () -> deleteApptCommand.execute(model), 
            Messages.MESSAGE_INVALID_APPT_DATETIME);
    }

    @Test
    public void equals() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 25, 10, 30);
        Nric nric1 = new Nric("S8888888C");
        Nric nric2 = new Nric("S7777777D");

        DeleteApptCommand deleteApptCommand1 = new DeleteApptCommand(nric1, dateTime);
        DeleteApptCommand deleteApptCommand2 = new DeleteApptCommand(nric1, dateTime);
        DeleteApptCommand deleteApptCommand3 = new DeleteApptCommand(nric2, dateTime);

        assertTrue(deleteApptCommand1.equals(deleteApptCommand1)); // same object
        assertTrue(deleteApptCommand1.equals(deleteApptCommand2)); // same values
        assertFalse(deleteApptCommand1.equals(deleteApptCommand3)); // different NRIC
        assertFalse(deleteApptCommand1.equals(null)); // null
        assertFalse(deleteApptCommand1.equals(new BookApptCommand(nric2, new Appt(dateTime, 
            new HealthService("Consult"))))); // different command type
    }
}
