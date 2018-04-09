/*
 * StegSolve.java
 *
 * Created on 18-Apr-2011, 08:48:02
 */

package stegsolve;

import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.event.*;
import java.awt.*;

// todo - sort out dimensions in linux
// todo - width/height explorer

/**
 * StegSolve
 * @author Caesum
 */
public class StegSolve extends JFrame
{
    /**
     * Menu option - about
     */
    private JMenuItem about;
    /**
     * Menu option - file exit
     */
    private JMenuItem fileExit;
    /**
     * Menu option - file save
     */
    private JMenuItem fileSave;
    /**
     * Menu option - file save
     */
    private JMenuItem fileOpen;
    /**
     * Menu option - analyse format
     */
    private JMenuItem analyseFormat;
    /**
     * Menu option - extract data
     */
    private JMenuItem analyseExtract;
    /**
     * Menu option - solve stereogram
     */
    private JMenuItem stereoSolve;
    /**
     * Menu option - frame browser
     */
    private JMenuItem frameBrowse;
    /**
     * Menu option - frame browser
     */
    private JMenuItem imageCombine;
    /**
     * Menu bar
     */
    private JMenuBar menuBar;
    /**
     * Sub menu - file
     */
    private JMenu menuFile;
    /**
     * Sub menu - analyse
     */
    private JMenu menuAnalyse;
    /**
     * Sub menu - help
     */
    private JMenu menuHelp;
    /**
     * label that shows the number of the frame currently being shown
     */
    private JLabel nowShowing;
    /**
     * panel for buttons
     */
    private JPanel buttonPanel;
    /**
     * Next frame button
     */
    private JButton forwardButton;
    /**
     * Previous frame button
     */
    private JButton backwardButton;
    /**
     * Panel with image on it
     */
    private DPanel dp;
    /**
     * Scroll bars for panel with image
     */
    private JScrollPane scrollPane;

    /**
     * The image file
     */
    private File sfile = null;
    /**
     * The image
     */
    private BufferedImage bi = null;
    /**
     * The transformation being viewed
     */
    private Transform transform = null;

    /** Creates new form stegsolve */
    public StegSolve()
    {
        initComponents();
    }

    // <editor-fold defaultstate="collapsed" desc="Initcomponents()">
    private void initComponents() {

        menuBar = new JMenuBar();
        menuFile = new JMenu();
        fileOpen = new JMenuItem();
        fileSave = new JMenuItem();
        fileExit = new JMenuItem();
        menuAnalyse = new JMenu();
        analyseFormat = new JMenuItem();
        analyseExtract = new JMenuItem();
        stereoSolve = new JMenuItem();
        frameBrowse = new JMenuItem();
        imageCombine = new JMenuItem();
        menuHelp = new JMenu();
        about = new JMenuItem();
        nowShowing = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        menuFile.setText("File");

        fileOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, 0));
        fileOpen.setText("Open");
        fileOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                fileOpenActionPerformed(evt);
            }
        });
        menuFile.add(fileOpen);

        fileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0));
        fileSave.setText("Save As");
        fileSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                fileSaveActionPerformed(evt);
            }
        });
        menuFile.add(fileSave);

        fileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0));
        fileExit.setText("Exit");
        fileExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                fileExitActionPerformed(evt);
            }
        });
        menuFile.add(fileExit);

        menuBar.add(menuFile);

        menuAnalyse.setText("Analyse");

        analyseFormat.setText("File Format");
        analyseFormat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                analyseFormatActionPerformed(evt);
            }
        });
        menuAnalyse.add(analyseFormat);

        analyseExtract.setText("Data Extract");
        analyseExtract.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                analyseExtractActionPerformed(evt);
            }
        });
        menuAnalyse.add(analyseExtract);

        stereoSolve.setText("Stereogram Solver");
        stereoSolve.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                stereoSolveActionPerformed(evt);
            }
        });
        menuAnalyse.add(stereoSolve);

        frameBrowse.setText("Frame Browser");
        frameBrowse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                frameBrowseActionPerformed(evt);
            }
        });
        menuAnalyse.add(frameBrowse);

        imageCombine.setText("Image Combiner");
        imageCombine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                imageCombineActionPerformed(evt);
            }
        });
        menuAnalyse.add(imageCombine);

        menuBar.add(menuAnalyse);

        menuHelp.setText("Help");

        about.setText("About");
        about.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                aboutActionPerformed(evt);
            }
        });
        menuHelp.add(about);

        menuBar.add(menuHelp);

        setJMenuBar(menuBar);

        setLayout(new BorderLayout());

        this.add(nowShowing, BorderLayout.NORTH);

        buttonPanel = new JPanel();
        backwardButton = new JButton("<");
        backwardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                backwardButtonActionPerformed(evt);
            }
        });
        forwardButton = new JButton(">");
        forwardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                forwardButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(backwardButton);
        buttonPanel.add(forwardButton);

        add(buttonPanel, BorderLayout.SOUTH);

        dp = new DPanel();
        scrollPane = new JScrollPane(dp);
        add(scrollPane, BorderLayout.CENTER);

        backwardButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,0), "back");
        backwardButton.getActionMap().put("back", backButtonPress);
        forwardButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,0), "forward");
        forwardButton.getActionMap().put("forward", forwardButtonPress);
        
        this.setTitle("StegSolve 1.3 by Caesum");
        this.setMaximumSize(getToolkit().getScreenSize());
        pack();
        //setResizable(false);
    }// </editor-fold>

    /**
     * Close the form on file exit
     * @param evt Event
     */
    private void fileExitActionPerformed(ActionEvent evt) {
        dispose();
    }

    /**
     * This is used to map the left arrow key to the back button
     */
    private Action backButtonPress = new AbstractAction()
    {
        public void actionPerformed(ActionEvent e)
        { backwardButtonActionPerformed(e);}
    };

    /**
     * Move back by one image
     * @param evt Event
     */
    private void backwardButtonActionPerformed(ActionEvent evt) {
        if(transform == null) return;
        transform.back();
        updateImage();
    }

    /**
     * This is used to map the right arrow key to the forward button
     */
    private Action forwardButtonPress = new AbstractAction()
    {
        public void actionPerformed(ActionEvent e)
        { forwardButtonActionPerformed(e);}
    };

    /**
     * Move forward by one image
     * @param evt Event
     */
    private void forwardButtonActionPerformed(ActionEvent evt) {
        if(bi == null) return;
        transform.forward();
        updateImage();
    }

    /**
     * Show the help/about frame
     * @param evt Event
     */
    private void aboutActionPerformed(ActionEvent evt) {
        new AboutFrame().setVisible(true);
    }

    /**
     * Open the file format analyser
     * @param evt Event
     */
    private void analyseFormatActionPerformed(ActionEvent evt) {
        new FileAnalysis(sfile).setVisible(true);
    }

    /**
     * Open the stereogram solver
     * @param evt Event
     */
    private void stereoSolveActionPerformed(ActionEvent evt) {
        new Stereo(bi).setVisible(true);
    }

    /**
     * Open the frame browser
     * @param evt Event
     */
    private void frameBrowseActionPerformed(ActionEvent evt) {
        new FrameBrowser(bi, sfile).setVisible(true);
    }

    /**
     * Open the image combiner
     * @param evt Event
     */
    private void imageCombineActionPerformed(ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "jpeg", "gif", "bmp", "png");
        fileChooser.setFileFilter(filter);
        fileChooser.setDialogTitle("Select image to combine with");
        int rVal = fileChooser.showOpenDialog(this);
        System.setProperty("user.dir", fileChooser.getCurrentDirectory().getAbsolutePath());
        if(rVal == JFileChooser.APPROVE_OPTION)
        {
            sfile = fileChooser.getSelectedFile();
            try
            {
                BufferedImage bi2 = null;
                bi2 = ImageIO.read(sfile);
                new Combiner(bi, bi2).setVisible(true);
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(this, "Failed to load file: " +e.toString());
            }
        }
    }

    /**
     * Open the data extractor
     * @param evt Event
     */
    private void analyseExtractActionPerformed(ActionEvent evt) {
        new Extract(bi).setVisible(true);
    }

    /**
     * Save the current transformed image
     * @param evt Event
     */
    private void fileSaveActionPerformed(ActionEvent evt)
    {
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
        fileChooser.setSelectedFile(new File("solved.bmp"));
        int rVal = fileChooser.showSaveDialog(this);
        System.setProperty("user.dir", fileChooser.getCurrentDirectory().getAbsolutePath());
        if(rVal == JFileChooser.APPROVE_OPTION)
        {
            sfile = fileChooser.getSelectedFile();
            try
            {
                bi = transform.getImage();
                int rns = sfile.getName().lastIndexOf(".")+1;
                if(rns==0)
                  ImageIO.write(bi, "bmp", sfile);
                else
                  ImageIO.write(bi, sfile.getName().substring(rns), sfile);
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(this, "Failed to write file: "+e.toString());
            }
        }
    }

    /**
     * Open a file
     * @param evt Event
     */
    private void fileOpenActionPerformed(ActionEvent evt) {
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "jpeg", "gif", "bmp", "png");
        fileChooser.setFileFilter(filter);
        int rVal = fileChooser.showOpenDialog(this);
        System.setProperty("user.dir", fileChooser.getCurrentDirectory().getAbsolutePath());
        if(rVal == JFileChooser.APPROVE_OPTION)
        {
            sfile = fileChooser.getSelectedFile();
            try
            {
                bi = ImageIO.read(sfile);
                transform = new Transform(bi);
                newImage();
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(this, "Failed to load file: " +e.toString());
            }
        }
    }

    /**
     * Reset settings for a new image
     */
    private void newImage()
    {
        nowShowing.setText(transform.getText());
        dp.setImage(transform.getImage());
        dp.setSize(transform.getImage().getWidth(),transform.getImage().getHeight());
        dp.setPreferredSize(new Dimension(transform.getImage().getWidth(),transform.getImage().getHeight()));
        this.setMaximumSize(getToolkit().getScreenSize());
        pack();
        scrollPane.revalidate();
        repaint();
    }

    /**
     * Update the image being shown for new transform
     */
    private void updateImage()
    {
        nowShowing.setText(transform.getText());
        dp.setImage(transform.getImage());
        repaint();
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StegSolve().setVisible(true);
            }
        });
    }

}
