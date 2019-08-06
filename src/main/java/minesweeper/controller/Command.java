package minesweeper.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This class defines constants and string names of menu items for menu events.
 */
public enum Command
{
    NEW("New", 0, 0),
    BEGINNER("Beginner", 9, 9),
    INTERMEDIATE("Intermediate", 16, 16),
    EXPERT("Expert", 16, 30),
    EXIT("Exit", 0, 0);

    private String label;
    private int height;
    private int width;
    private static final Map<String, Command> COMMAND_MAP;

    Command(String label, int height, int width)
    {
        this.label = label;
        this.height = height;
        this.width = width;
    }

    public String getLabel()
    {
        return label;
    }

    public int getHeight()
    {
        return height;
    }

    public int getWidth()
    {
        return width;
    }

    static
    {
        Map<String, Command> map = new HashMap<>();

        for (Command instance : Command.values())
        {
            map.put(instance.name(), instance);
        }

        COMMAND_MAP = Collections.unmodifiableMap(map);
    }

    /**
     * @param key String representation of enum name
     * @return enum Command
     */
    public static Command getCommand(String key)
    {
        return COMMAND_MAP.get(key);
    }
}
