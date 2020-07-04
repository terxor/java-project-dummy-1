package frames;

import core.DatabaseUtility;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeptFrame {
    private DatabaseUtility dbUtility;
    private JFrame frame;
    private JLabel titleLabel;
    private JTable table;
    private JButton viewBtn;



    public DeptFrame(DatabaseUtility dbUtility){
        this.dbUtility = dbUtility;

        frame = new JFrame("Departments");
        titleLabel = new JLabel("DEPARTMENTS");
        titleLabel.setFont(new Font("Monospaced", Font.PLAIN, 20));
        table = new JTable();
        viewBtn = new JButton("View Staff working in selected department");
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

        DefaultTableModel tableModel = dbUtility.getData("select * from departments;");
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
                JOptionPane.showMessageDialog(frame,"Please select a department");
            }
            else{
                String id = table.getValueAt(i,0).toString();
                String query = "select staff_id,staff_name,dept_name from staff,departments where staff.dept_id = departments.dept_id and departments.dept_id = "+id+";";
                JPanel panel = new JPanel();
                JTable table = new JTable();
                DefaultTableModel tableModel = dbUtility.getData(query);
                table.setModel(tableModel);
                table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                panel.add(new JScrollPane(table));
                JOptionPane.showMessageDialog(frame,panel,"Showing staff",JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

}
