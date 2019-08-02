/*
 *
 *  * Copyright $year Nicholas Talbert
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package minesweeper.view;

import org.pmw.tinylog.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ButtonIcon
{
    public final ImageIcon DEFAULT;
    public final ImageIcon EMPTY;
    public final ImageIcon ONE;
    public final ImageIcon TWO;
    public final ImageIcon THREE;
    public final ImageIcon FOUR;
    public final ImageIcon FIVE;
    public final ImageIcon SIX;
    public final ImageIcon SEVEN;
    public final ImageIcon EIGHT;
    public final ImageIcon FLAG;
    public final ImageIcon MINE;
    public final ImageIcon EXPLODED;
    public final ImageIcon UNFLAGGED_MINE;
    public final ImageIcon SMILEY;
    public final ImageIcon SMILEY_PRESSED;
    public final ImageIcon SMILEY_SUSPENSE;
    public final ImageIcon SMILEY_WIN;
    public final ImageIcon SMILEY_DEAD;
    public final ImageIcon CHECK;
    public final BufferedImage FRAME_ICON;

    public ButtonIcon()
    {
        DEFAULT = read("default.png");
        EMPTY = read("empty.png");
        ONE = read("one.png");
        TWO = read("two.png");
        THREE = read("three.png");
        FOUR = read("four.png");
        FIVE = read("five.png");
        SIX = read("six.png");
        SEVEN = read("seven.png");
        EIGHT = read("eight.png");
        FLAG = read("flag.png");
        MINE = read("mine.png");
        EXPLODED = read("exploded.png");
        UNFLAGGED_MINE = read("unflaggedMine.png");
        SMILEY = read("smiley.png");
        SMILEY_PRESSED = read("smileyPressed.png");
        SMILEY_SUSPENSE = read("smileySuspense.png");
        SMILEY_WIN = read("smileyWin.png");
        SMILEY_DEAD = read("smileyDead.png");
        CHECK = read("check.png");
        FRAME_ICON = readBufferedImg("frame.png");
    }

    private ImageIcon read(String name)
    {
        try
        {
            final String PATH = "img/" + name;
            BufferedImage image = ImageIO.read(getClass().getClassLoader().getResource(PATH));
            return new ImageIcon(image);
        }
        catch (IOException e)
        {
            // TODO try returning blank icon, using whole final String path
            Logger.error("Error reading icon " + name);
            e.printStackTrace();
            return null;
        }
    }

    private BufferedImage readBufferedImg(String name)
    {
        try
        {
            final String PATH = "img/" + name;
            return ImageIO.read(getClass().getClassLoader().getResource(PATH));
        }
        catch (IOException e)
        {
            Logger.error("Error reading icon " + name);
            e.printStackTrace();
            return null;
        }
    }
}