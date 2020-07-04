package frames;

import core.DatabaseUtility;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CourseFrame {
    private final DatabaseUtility dbUtility;
    private final JFrame frame;
    private final JLabel titleLabel;
    private final JTable table;
    private final JButton viewBtn;

    public CourseFrame(DatabaseUtility dbUtility){
        this.dbUtility = dbUtility;

        frame = new JFrame("Courses");
        titleLabel = new JLabel("COURSES");
        titleLabel.setFont(new Font("Monospaced", Font.PLAIN, 20));
        table = new JTable();
        viewBtn = new JButton("View Teachers for selected course");
        JScrollPane tableContainer = new JScrollPane(table);
        JPanel btnContainer = new JPanel();
        btnContainer.add(viewBtn);

        frame.setSize(new Dimension(800,600));
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        titleLabel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        tableContainer.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        btnContainer.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        frame.getContentPane().add(titleLabel,BorderLayout.NORTH);
        frame.getContentPane().add(tableContainer,BorderLayout.CENTER);
        frame.getContentPane().add(btnContainer,BorderLayout.SOUTH);

        DefaultTableModel tableModel = dbUtility.getData("select * from courses;");
        table.setModel(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        viewBtn.addActionListener(new ViewBtnListener());
        frame.setResizable(false);
        frame.setVisible(true);

    }

    class ViewBtnListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int i = table.getSelectedRow();
            if(i==-1){
                JOptionPane.showMessageDialog(frame,"Please select a course");
            }
            else{
                String id = table.getValueAt(i,0).toString();
                String query = "select staff_id,staff_name,course_name from staff,courses where staff.course_id = courses.course_id and courses.course_id = "+id+";";
                JPanel panel = new JPanel();
                JTable table = new JTable();
                DefaultTableModel tableModel = dbUtility.getData(query);
                table.setModel(tableModel);
                table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                panel.add(new JScrollPane(table));
                JOptionPane.showMessageDialog(frame,panel,"Showing teachers",JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

}
