import javax.swing.SwingUtilities;

public class Main {
    public static void main(String [] args){
        SwingUtilities.invokeLater(() -> new View()); // create new thread to start new view
    }
}
