package minesweeper.controller;

public class CommandEvent
{
    private Command evtCommand;

    public CommandEvent(Command evtCommand)
    {
        this.evtCommand = evtCommand;
    }

    public Command getEvtCommand()
    {
        return evtCommand;
    }
}
