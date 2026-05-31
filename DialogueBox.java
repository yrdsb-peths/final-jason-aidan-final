import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.List;

/**
 * Write a description of class DialogueBox here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */


public class DialogueBox extends Actor {
    // Store properties as fields so they can be reused when text changes
    private int width;
    private int height;
    private Color boxColor;
    private Color textColor;
    private Font font;
    private String text;

    public DialogueBox(int width, int height, Color boxColor, String text, Color textColor, Font font) {
        this.width = width;
        this.height = height;
        this.boxColor = boxColor;
        this.text = text;
        this.textColor = textColor;
        this.font = font;
        
        // Draw the initial textbox image
        updateImage();
    }

    /**
     * Changes the text and automatically redraws the textbox.
     */
    public void setText(String newText) {
        this.text = newText;
        updateImage(); // Re-trigger the drawing logic with the new text
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
        
        setImage(img);
    }
}