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

public class SwingTester {
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
                " src=\"/media/cc0-images/grapefruit-slice-332-332.jpg\"" + // Noncompliant {{If possible, utilisation of svg image format is recommended over other image format.}}
                " alt=\"Grapefruit slice atop a pile of other slices\">" +
                " <img class=\"fit-picture\"" +
                " src=\"/media/cc0-images/grapefruit-slice-332-332.ico\" alt=\"Grapefruit slice atop a pile of other slices\">" +   // Noncompliant {{If possible, utilisation of svg image format is recommended over other image format.}}
                " src=\"/media/cc0-images/grapefruit-slice-332-332.bmp\" alt=\"Grapefruit slice atop a pile of other slices\">" +   // Noncompliant {{If possible, utilisation of svg image format is recommended over other image format.}}
                " src=\"/media/cc0-images/grapefruit-slice-332-332.tiff\" alt=\"Grapefruit slice atop a pile of other slices\">" +  // Noncompliant {{If possible, utilisation of svg image format is recommended over other image format.}}
                " src=\"/media/cc0-images/grapefruit-slice-332-332.webp\" alt=\"Grapefruit slice atop a pile of other slices\">" +  // Noncompliant {{If possible, utilisation of svg image format is recommended over other image format.}}
                " src=\"/media/cc0-images/grapefruit-slice-332-332.png\" alt=\"Grapefruit slice atop a pile of other slices\">" +   // Noncompliant {{If possible, utilisation of svg image format is recommended over other image format.}}
                " src=\"/media/cc0-images/grapefruit-slice-332-332.jpeg\" alt=\"Grapefruit slice atop a pile of other slices\">" +  // Noncompliant {{If possible, utilisation of svg image format is recommended over other image format.}}
                " src=\"/media/cc0-images/grapefruit-slice-332-332.jfif\" alt=\"Grapefruit slice atop a pile of other slices\">" +  // Noncompliant {{If possible, utilisation of svg image format is recommended over other image format.}}
                " src=\"/media/cc0-images/grapefruit-slice-332-332.pjpeg\" alt=\"Grapefruit slice atop a pile of other slices\">" + // Noncompliant {{If possible, utilisation of svg image format is recommended over other image format.}}
                " src=\"/media/cc0-images/grapefruit-slice-332-332.pjp\" alt=\"Grapefruit slice atop a pile of other slices\">" +   // Noncompliant {{If possible, utilisation of svg image format is recommended over other image format.}}
                " src=\"/media/cc0-images/grapefruit-slice-332-332.gif\" alt=\"Grapefruit slice atop a pile of other slices\">" +   // Noncompliant {{If possible, utilisation of svg image format is recommended over other image format.}}
                " src=\"/media/cc0-images/grapefruit-slice-332-332.avif\" alt=\"Grapefruit slice atop a pile of other slices\">" +  // Noncompliant {{If possible, utilisation of svg image format is recommended over other image format.}}
                " src=\"/media/cc0-images/grapefruit-slice-332-332.apng\" alt=\"Grapefruit slice atop a pile of other slices\">" +  // Noncompliant {{If possible, utilisation of svg image format is recommended over other image format.}}
                "</html>");

        JScrollPane jScrollPane = new JScrollPane(jEditorPane);
        jScrollPane.setPreferredSize(new Dimension(540, 400));

        panel.add(jScrollPane);
        panel.add(jScrollPane2);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
    }
}