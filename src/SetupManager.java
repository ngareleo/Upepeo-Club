import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class SetupManager {
    private JPanel infoPanel;
    private JPanel SetupManager;
    private JButton doneButton;
    private JLabel text1;
    private JLabel text2;
    private JLabel text3;
    private JLabel text4;
    private JLabel text5;
    private JLabel text6;
    private JButton chooseButton;
    private JLabel status1;
    private JLabel status2;
    private JLabel status3;
    private JLabel status4;
    private JLabel status5;
    private JPanel setupCompletePanel;
    private JPanel processPanel;
    private JLabel label_two;
    private JLabel errorLabel;
    private JLabel status6;
    private JLabel chosenLocation;
    JFrame setupFrame = new JFrame();
    Color green = new Color(18,127,29);
    String fs;

    SetupManager(){

        prepareSetupApplet();
        processPanel.setVisible(false);
        setupCompletePanel.setVisible(false);
        chosenLocation.setVisible(false);
        chooseButton.addActionListener( e -> {
            if (fs != null){
                int confirm = JOptionPane.showConfirmDialog(setupFrame, "Do you wish to change the location?");
                if(confirm == 2 || confirm == 1)
                    return;
            }
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choose resource folder location");
            fileChooser.setCurrentDirectory(new java.io.File("../"));
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.setAcceptAllFileFilterUsed(false);
            if(fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                fs = fileChooser.getSelectedFile().getAbsolutePath();
                System.out.println(fs);
            }
            if(!fs.equals("")){
                chooseButton.setText("change");
                processPanel.setVisible(true);
                chosenLocation.setVisible(true);
                chosenLocation.setText("Chosen location : " + fs);
                try {
                    startSetup();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        doneButton.addActionListener( e -> {
            try {
                new App();
                setupFrame.dispose();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
    public String getFs(){
        return fs;
    }
    private void prepareSetupApplet(){
        setupFrame.setTitle("Setup");
        setupFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setupFrame.setSize(700, 500);
        setupFrame.revalidate();
        setupFrame.setVisible(true);
        setupFrame.add(this.SetupManager);
    }

    private void startSetup() throws IOException {
        createFolders();
        setupDatabase();
        File configFile = new File("./res/CONFIG.config");
        configFile.createNewFile();
        FileWriter writer = new FileWriter(fs + "/CONFIG.config");
        writer.write("path_to_res=" + fs);
        writer = new FileWriter("./res/CONFIG.config");
        writer.write("path_to_res=" + fs + "\nsetup=True");
        writer.close();
        status4.setText("done");
        status4.setForeground(green);
        status5.setForeground(green);
        status5.setText("done");
        status6.setForeground(green);
        status6.setText("done");

        setupCompletePanel.setVisible(true);
    }
    private void createFolders(){
        File resLocation = new File(fs), resLocationTwo = new File(fs + "/res"), dotRes = new File("./res");
        resLocation.mkdir();
        resLocationTwo.mkdir();
        dotRes.mkdir();
        status1.setText("done");
        status1.setForeground(green);
    }

    private void setupDatabase() throws IOException {
        String dbFileLocation = fs + "/res/Upepeo.db";
        System.out.println(dbFileLocation);
        File dbFile = new File(dbFileLocation);
        dbFile.createNewFile();
        status2.setText("done");
        status2.setForeground(green);
        Setup setup = new Setup(dbFileLocation);
        setup.createDB();
        status3.setText("done");
        status3.setForeground(green);
    }
}
