import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.List;

/**
 * Write a description of class DialogueBox here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */


import greenfoot.*;
import java.util.ArrayList;
import java.util.List;

public class DialogueBox extends Actor {
    private int width;
    private int height;
    private Color boxColor;
    private Color textColor;
    private Font font;
    private String text;
    
    // Track whether the dialogue box is currently visible
    private boolean isVisible = true;

    public DialogueBox(int width, int height, Color boxColor, String text, Color textColor, Font font) {
        this.width = width;
        this.height = height;
        this.boxColor = boxColor;
        this.text = text;
        this.textColor = textColor;
        this.font = font;
        
        updateImage();
    }

    /**
     * Changes the text and redraws the textbox.
     */
    public void setText(String newText) {
        this.text = newText;
        updateImage(); 
    }

    /**
     * Makes the dialogue box visible on screen.
     */
    public void show() {
        this.isVisible = true;
        if (getImage() != null) {
            getImage().setTransparency(255); // Reset transparency to fully visible
        }
    }

    /**
     * Hides the dialogue box without removing the actor from the world.
     */
    public void hide() {
        this.isVisible = false;
        if (getImage() != null) {
            getImage().setTransparency(0); // Set transparency to completely invisible
        }
    }

    /**
     * Returns true if the dialogue box is currently visible.
     */
    public boolean isCurrentlyVisible() {
        return this.isVisible;
    }

    /**
     * Clears the old image and draws the new state.
     */
    private void updateImage() {
        GreenfootImage img = new GreenfootImage(width, height);
        
        // 1. Draw the background
        img.setColor(boxColor);
        img.fillRect(0, 0, width, height);
        
        // 2. Set text properties
        img.setFont(font);
        img.setColor(textColor);
        
        // 3. Approximate max characters per line
        int fontSize = font.getSize();
        int padding = 15;
        int usableWidth = width - (padding * 2);
        
        int estimatedCharWidth = (int) (fontSize * 0.55);
        if (estimatedCharWidth < 1) estimatedCharWidth = 1;
        int maxCharsPerLine = usableWidth / estimatedCharWidth;
        
        // 4. Word-wrap the text
        String[] words = text.split(" ");
        List<String> lines = new ArrayList<>();
        StringBuilder currentLine = new StringBuilder();
        
        for (String word : words) {
            if (currentLine.length() + word.length() > maxCharsPerLine) {
                lines.add(currentLine.toString().trim());
                currentLine = new StringBuilder();
            }
            currentLine.append(word).append(" ");
        }
        if (currentLine.length() > 0) {
            lines.add(currentLine.toString().trim());
        }
        
        // 5. Draw lines
        int startX = padding;
        int startY = padding + fontSize;
        int lineSpacing = (int) (fontSize * 1.3);
        
        for (int i = 0; i < lines.size(); i++) {
            int currentY = startY + (i * lineSpacing);
            if (currentY < (height - padding)) {
                img.drawString(lines.get(i), startX, currentY);
            }
        }
        
        // 6. Ensure the newly generated image respects the current visibility state
        if (!isVisible) {
            img.setTransparency(0);
        } else {
            img.setTransparency(255);
        }
        
        setImage(img);
    }
}