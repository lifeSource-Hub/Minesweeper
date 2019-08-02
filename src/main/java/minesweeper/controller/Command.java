/*
 * Copyright 2019 Nicholas Talbert
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

    // public static void printMap()
    // {
    //     Logger.debug("Keys:");
    //
    //     for (String key : COMMAND_MAP.keySet())
    //     {
    //         Logger.debug("\t" + key);
    //     }
    //
    //     Logger.debug("\nValues:");
    //
    //     for (Command value : COMMAND_MAP.values())
    //     {
    //         Logger.debug("\t" + value);
    //     }
    // }

    // /**
    //  * @return Return sentence case string of enum constant
    //  */
    // @Override
    // public String toString()
    // {
    //     return label;
    // }
}
