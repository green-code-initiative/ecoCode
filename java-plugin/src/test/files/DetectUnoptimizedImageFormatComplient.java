import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.io.IOException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class DetectUnoptimizedImageFormat {

    private String img_svg = "test/image.svg";    // Complient

    private String getImagePath(String image) {
        return "path/to/" + image;
    }

    private static void testImage() {
        String img1 = getImagePath("test/image.svg");   // Complient
        String img2 = getImagePath("image.svg");        // Complient
    }

    private static void createWindow() {
        JFrame frame = new JFrame("Swing Tester");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createUI(frame);
        frame.setSize(560, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void createUI(final JFrame frame) {
        JPanel panel = new JPanel();
        LayoutManager layout = new FlowLayout();
        panel.setLayout(layout);

        JEditorPane jEditorPane = new JEditorPane();
        jEditorPane.setEditable(false);

        jEditorPane.setContentType("text/html");
        jEditorPane.setText("<html>Le titre avec <b>une partie en gras</b>," +
                " et <span style=\"color:blue;\">une autre partie en bleu</span>." +
                " <img class=\"fit-picture\"" +
                " src=\"/media/cc0-images/grapefruit-slice-332-332.svg\"" + // Complient
                " alt=\"Grapefruit slice atop a pile of other slices\">" +
                "</html>");

        JEditorPane jEditorPane1 = new JEditorPane();
        jEditorPane1.setEditable(false);

        jEditorPane1.setContentType("text/html");
        jEditorPane1.setText("<html>Le titre avec <b>une partie en gras</b>," +
                " et <span style=\"color:blue;\">une autre partie en bleu</span>." +
                " <svg width=\"100\" height=\"100\">" +  // Complient
                " <circle cx=\"50\" cy=\"50\" r=\"40\" stroke=\"green\" stroke-width=\"4\" fill=\"yellow\" />" +
                "</html>");

        JScrollPane jScrollPane = new JScrollPane(jEditorPane);
        jScrollPane.setPreferredSize(new Dimension(540, 400));

        panel.add(jScrollPane);
        panel.add(jScrollPane2);
        panel.add(jScrollPane3);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
    }
}