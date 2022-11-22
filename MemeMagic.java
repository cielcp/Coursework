import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * MemeMagic Graphical User Interface 
 * 
 * This class contains the graphical user interface for the Meme Magic Software
 * 
 * You will need to implement certain portions of this class, marked with comments starting with "TODO" to connect 
 * it with your existing code. 
 * 
 * This class provides an example layout for the GUI. You are encouraged to be creative in your design. 
 * More information about Swing is online at: 
 * https://docs.oracle.com/javase/tutorial/uiswing/components/componentlist.html.
 * 
 * 
 */
public class MemeMagic extends JFrame {

    /**
     * Serialization string required by extending JFrame
     */
    private static final long serialVersionUID = 1L;
    
    private User user;
    private GraphicalMeme currentMeme;
    private String backgroundImageFilename;

    private BorderLayout panelLayout;
    private JLabel backgroundImageFileNameLabel;
    private JLabel imageDisplayLabel;
    private JPanel controlPanel;
    private JPanel memeViewPanel;
    private JPanel panelPane;
    
    
    
    public MemeMagic() {
        this.user = new User();
    }
    
    public MemeMagic(User user) {
        this.user = user;
    }


    /**
     * Main method.  This method initializes a PhotoViewer, loads images into a PhotographContainer, then
     * initializes the Graphical User Interface.
     * 
     * @param args  Optional command-line arguments
     */
    public static void main(String[] args) {
        
        // Create a User object for this instance of Meme Magic
        User user = new User();

        // Instantiate the PhotoViewer Class
        MemeMagic myViewer = new MemeMagic(user);
        
        // Invoke and start the Graphical User Interface
        javax.swing.SwingUtilities.invokeLater(() -> myViewer.initialize());
    }

    /**
     * Initialize all the GUI components.  This method will be called by
     * SwingUtilities when the application is started.
     */
    private void initialize() {

        // Tell Java to exit the program when the window is closed
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Tell Java to title the window to Meme Magic
        this.setTitle("Meme Magic");

        // We will use border layout on the main panel, since it is much easier for organizing panels.
        panelLayout = new BorderLayout();
        panelPane = new JPanel(panelLayout);

        // Create a label to display the full image.
        imageDisplayLabel = new JLabel();
        imageDisplayLabel.setHorizontalAlignment(JLabel.CENTER);
        imageDisplayLabel.setPreferredSize(new Dimension(550, 550));

        // Create a panel on which to display the full image
        memeViewPanel = new JPanel(new BorderLayout());
        memeViewPanel.setPreferredSize(new Dimension(550, 550));
        memeViewPanel.add(imageDisplayLabel, BorderLayout.CENTER);

        // Create a panel on which to display the controls for building a Meme
        controlPanel = new JPanel(new BorderLayout());
        
        
        
        // Create a panel that holds BackgroundImage information and give it a title
        JPanel backgroundImagePanel = new JPanel(new BorderLayout());
        backgroundImagePanel.setBorder(BorderFactory.createTitledBorder("Background Image"));
        
        // Create a panel that provides input for the BackgroundImage fileName
        JPanel backgroundImageFilePanel = new JPanel();
        
        // Label
        JLabel backgroundImageFileLabel = new JLabel("Filename: ");
        backgroundImageFileLabel.setPreferredSize(new Dimension(100, 20));
        backgroundImageFilePanel.add(backgroundImageFileLabel);
        
        // Button
        JButton backgroundImageButton = new JButton("Browse");
        backgroundImageFilePanel.add(backgroundImageButton);
        backgroundImageButton.setPreferredSize(new Dimension(85, 20));
        
        // TODO The button needs a listener
        OpenButtonListener openButtonListener = new OpenButtonListener();
        backgroundImageButton.addActionListener(openButtonListener);
        
        // Label that will contain the filename of the image
        backgroundImageFileNameLabel = new JLabel("<choose>");
        backgroundImageFilePanel.add(backgroundImageFileNameLabel);
        backgroundImageFileNameLabel.setPreferredSize(new Dimension(265, 20));
        
        // Add the panel about the BackgroundImage fileName to the BackgroundImage information panel
        backgroundImagePanel.add(backgroundImageFilePanel, BorderLayout.NORTH);
        
        // Create a panel that provides input for the BackgroundImage title
        JPanel backgroundImageTitlePanel = new JPanel();
        
        // Label
        JLabel backgroundImageTitle = new JLabel("Title: ");
        backgroundImageTitle.setPreferredSize(new Dimension(100, 20));
        backgroundImageTitlePanel.add(backgroundImageTitle);
        
        // TextField that allows the user to enter the title they want
        JTextField backgroundImageTitleTextField = new JTextField();
        backgroundImageTitlePanel.add(backgroundImageTitleTextField);
        backgroundImageTitleTextField.setPreferredSize(new Dimension(360, 20));
        
        // Add the panel about the BackgroundImage title to the BackgroundImage information panel
        backgroundImagePanel.add(backgroundImageTitlePanel, BorderLayout.CENTER);
        
        // Create a panel that provides input for the BackgroundImage description
        JPanel backgroundImageDescriptionPanel = new JPanel();
        
        // Label
        JLabel backgroundImageDescription = new JLabel("Description: ");
        backgroundImageDescription.setPreferredSize(new Dimension(100, 20));
        backgroundImageDescriptionPanel.add(backgroundImageDescription);
        
        // TextField that allows the user to enter the description they want
        JTextField backgroundImageDescriptionTextField = new JTextField();
        backgroundImageDescriptionPanel.add(backgroundImageDescriptionTextField);
        backgroundImageDescriptionTextField.setPreferredSize(new Dimension(360, 20));
        
        // Add the panel about the BackgroundImage description to the BackgroundImage information panel
        backgroundImagePanel.add(backgroundImageDescriptionPanel, BorderLayout.SOUTH);
        
        
        
        // Create a panel that holds Meme information and give it a caption
        JPanel memePanel = new JPanel(new BorderLayout());
        memePanel.setBorder(BorderFactory.createTitledBorder("Meme"));
        
        // Create a panel that provides input for the Meme caption
        JPanel memeCaptionPanel = new JPanel();
        
        // Label
        JLabel memeCaption = new JLabel("Caption: ");
        memeCaption.setPreferredSize(new Dimension(100, 20));
        memeCaptionPanel.add(memeCaption);
        
        // TextField that allows the user to enter the title they want
        JTextField memeCaptionTextField = new JTextField();
        memeCaptionPanel.add(memeCaptionTextField);
        memeCaptionTextField.setPreferredSize(new Dimension(360, 20));
        
        // Add the panel about the Meme caption to the Meme information panel
        memePanel.add(memeCaptionPanel, BorderLayout.NORTH);
        
        // Create a panel that provides input for the Meme verticalAlign
        JPanel memeVerticalAlignPanel = new JPanel();
        
        // Label
        JLabel memeVerticalAlign = new JLabel("Vertical Align: ");
        memeVerticalAlign.setPreferredSize(new Dimension(100, 20));
        memeVerticalAlignPanel.add(memeVerticalAlign);
        
        // ComboBox that allows the user to select the verticalAlign they want
        String[] verticalAligns = {"top", "middle", "bottom"};
        JComboBox memeVerticalAlignComboBox = new JComboBox(verticalAligns);
        memeVerticalAlignPanel.add(memeVerticalAlignComboBox);
        memeVerticalAlignComboBox.setPreferredSize(new Dimension(360, 20));
        
        // Add the panel about the Meme verticalAlign to the Meme information panel
        memePanel.add(memeVerticalAlignPanel, BorderLayout.CENTER);
        
        
        
        // TODO Complete the Control Panel implementation (with Background Image and Meme panels)
        controlPanel.add(backgroundImagePanel, BorderLayout.NORTH);
        controlPanel.add(memePanel, BorderLayout.CENTER);
        
        // Create a panel that contains Generate button
        JPanel generatePanel = new JPanel();
        
        // Button
        JButton generateButton = new JButton("Generate");
        generatePanel.add(generateButton);
        generateButton.setPreferredSize(new Dimension(100, 20));
        
        // Listener
        generateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                String c = memeCaptionTextField.getText();
                String bgFilename = backgroundImageFilename;
                String bgTitle = backgroundImageTitleTextField.getText();
                String bgDescription = backgroundImageDescriptionTextField.getText();
                BackgroundImage bg = new BackgroundImage(bgFilename, bgTitle, bgDescription);
                User u = user;
                String captionVerticalAlign = (String)memeVerticalAlignComboBox.getSelectedItem();
                GraphicalMeme graphicalMeme = new GraphicalMeme(bg, c, u);
                graphicalMeme.setCaptionVerticalAlign(captionVerticalAlign);
                currentMeme = graphicalMeme;
                
                try {
                    BufferedImage gm = graphicalMeme.compileMeme(550);
                    imageDisplayLabel = new JLabel(new ImageIcon(gm));
                    memeViewPanel.add(imageDisplayLabel, BorderLayout.WEST);
                    memeViewPanel.repaint();
                } 
                catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("Input/Output operation was interrupted");
                }
                catch (java.lang.NullPointerException e) {
                    System.err.println("Empty/Incomplete GraphicalMeme");
                }
                catch (IllegalArgumentException e) {
                    System.err.println("No convertable Meme was created");
                }
            }
        });
        
        // Add the generatePanel to the control panel
        controlPanel.add(generatePanel, BorderLayout.SOUTH);
        
        //Also add Save button to generatePanel
        // Button
        JButton saveButton = new JButton("Save");
        generatePanel.add(saveButton);
        saveButton.setPreferredSize(new Dimension(100, 20));
        
        // Listener
        SaveButtonListener saveButtonListener = new SaveButtonListener();
        saveButton.addActionListener(saveButtonListener);
                
        
        
        // Add all the panels to the main display based on BorderLayout
        controlPanel.setPreferredSize(new Dimension(500,570));
        panelPane.add(controlPanel, BorderLayout.WEST);
        panelPane.add(memeViewPanel, BorderLayout.CENTER);

        // Add the panelPane to the contentPane of the Frame (Window)
        this.getContentPane().add(panelPane);

        // Set the preferred size and show the main application window
        this.setPreferredSize(new Dimension(1150, 570));
        this.pack();
        this.setVisible(true);
    }
    
    
    /**
     * ActionListener for the open button.  When the button is pressed, this ActionListener
     * opens a FileChooser, asks the user to choose a JPG image file, then
     * sets the field backgroundImageFilename in the main class.
     */
    private class OpenButtonListener implements ActionListener {
        /**
         * Action performed operation.  Opens a save FileChooser, asks the user to choose a JPG image file, then
         * sets the field backgroundImageFilename in the main class.
         * 
         * @param evt The event that was performed
         */
        @Override
        public void actionPerformed(ActionEvent evt) {
            JFileChooser chooser2 = new JFileChooser();
            chooser2.setDialogTitle("Choose a Background Image");
            chooser2.setFileFilter(new FileNameExtensionFilter("JPEG Images", "jpg", "jpeg"));
            int returnVal = chooser2.showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                backgroundImageFilename = chooser2.getSelectedFile().getAbsolutePath();
                backgroundImageFileNameLabel.setText(backgroundImageFilename);
            }

        }
    }
    
    /**
     * ActionListener for the save button.  When the button is pressed, this ActionListener
     * opens a save FileChooser, asks the user to choose a location and filename, then
     * writes the graphical meme data to a PNG image file.
     */
    private class SaveButtonListener implements ActionListener {
        /**
         * Action performed operation.  Opens a save FileChooser, asks the user to choose
         * a location and filename, then writes the graphical meme data to a PNG file.
         * 
         * @param evt The event that was performed
         */
        @Override
        public void actionPerformed(ActionEvent evt) {
            JFileChooser chooser2 = new JFileChooser();
            chooser2.setDialogTitle("Save Meme");
            chooser2.setFileFilter(new FileNameExtensionFilter("PNG Images", "png"));
            int returnVal = chooser2.showSaveDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                String destinationFile = chooser2.getSelectedFile().getAbsolutePath();
                
                // TODO: Writing an image throws a checked exception that must be handled.
                //       Catch the exceptions and provide the user with an appropriate message
                try {
                    ImageIO.write(currentMeme.compileMeme(), "png", new File(destinationFile));
                } 
                catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("Input/Output operation was interrupted");
                } 
                catch (java.lang.NullPointerException e) {
                    System.err.println("Empty/Incomplete BufferedImage");
                }
                catch (IllegalArgumentException e) {
                    System.err.println("No savable BufferedImage created");
                }

            }

        }
    }

}