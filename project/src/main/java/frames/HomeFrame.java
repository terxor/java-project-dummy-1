package frames;

import core.DBConstants;
import core.DatabaseUtility;
import frames.CourseFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeFrame {
    DatabaseUtility databaseUtility;
    public HomeFrame(){
        databaseUtility = new DatabaseUtility();

        JFrame frame = new JFrame("Staff Management System Portal");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));

        JButton btnCourses = new JButton("View Courses"),
                btnDepartments = new JButton("View Departments"),
                btnStaff = new JButton("Staff Portal"),
                btnInfo = new JButton("About"),
                btnModerator = new JButton("Moderator Panel");


        //HEADER=================
        JPanel header = new JPanel();
        header.setLayout(new GridLayout(2,1));
        JLabel titleA = new JLabel("      TZ INSTITUTE OF TECHNOLOGY");

        JLabel titleB = new JLabel(" STAFF MANAGEMENT SYSTEM");
        titleA.setFont(new Font("Monospaced", Font.PLAIN, 20));
        titleB.setFont(new Font("Monospaced", Font.PLAIN, 30));
        header.add(titleA);
        header.add(titleB);
        header.setBorder(BorderFactory.createEtchedBorder());
        //======================
        JPanel btnPanelAContainer = new JPanel();
        btnPanelAContainer.setLayout(new BoxLayout(btnPanelAContainer,BoxLayout.Y_AXIS));
        JPanel btnPanelA = new JPanel();
        btnPanelA.setLayout(new GridLayout(1,3,10,10));
        btnPanelA.add(btnStaff);
        btnPanelA.add(btnDepartments);
        btnPanelA.add(btnCourses);
        btnPanelA.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        btnPanelAContainer.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Basic"));
        btnPanelAContainer.add(btnPanelA);
        //======================
        JPanel btnPanelBContainer = new JPanel();
        btnPanelBContainer.setLayout(new BoxLayout(btnPanelBContainer,BoxLayout.Y_AXIS));
        JPanel btnPanelB = new JPanel();
        btnPanelB.setLayout(new GridLayout(1,2,10,20));
        btnPanelB.add(btnModerator);
        btnPanelB.add(btnInfo);
        btnPanelB.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        btnPanelBContainer.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "More"));
        btnPanelBContainer.add(btnPanelB);
        //============================
        btnCourses.addActionListener(new CoursesListener());
        btnDepartments.addActionListener(new DeptListener());
        btnStaff.addActionListener(new StaffListener());
        btnModerator.addActionListener(new ModeratorListener());
        btnInfo.addActionListener(new InfoListener());
        //============================
        container.add(header);
        container.add(Box.createRigidArea(new Dimension(0,30)));
        container.add(btnPanelAContainer);
        container.add(Box.createRigidArea(new Dimension(0,30)));
        container.add(btnPanelBContainer);
        //============================
        frame.getContentPane().add(container);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    class CoursesListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            CourseFrame courseFrame = new CourseFrame(databaseUtility);
        }
    }
    class DeptListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            DeptFrame deptFrame = new DeptFrame(databaseUtility);
        }
    }
    class StaffListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JPanel panel = new JPanel();
                JTextField inputStaffID = new JTextField(15);
                panel.add(new JLabel("Enter staff ID"));
                panel.add(inputStaffID);

                int zzz = JOptionPane.showConfirmDialog(null,panel,"Enter Staff ID",JOptionPane.OK_CANCEL_OPTION);
                if(zzz == JOptionPane.OK_OPTION){
                    String staffID = inputStaffID.getText();
                    String[] values = databaseUtility.getSingleColumnValues("select staff_id from staff where staff_id ='"+staffID+"';");
                    if(values.length == 1){
                        StaffFrame staffFrame = new StaffFrame(databaseUtility,staffID);
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Unable to find staff member with given ID");
                    }
                }
        }
    }
    class ModeratorListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JPasswordField passwordField = new JPasswordField(15);
            JPanel panel = new JPanel();
            panel.add(new JLabel("Enter passphrase for Moderator Level Access"));
            panel.add(passwordField);

            int z = JOptionPane.showConfirmDialog(null,panel,"Authentication",JOptionPane.OK_CANCEL_OPTION);
            if(z == JOptionPane.OK_OPTION){
                String pass = new String(passwordField.getPassword());
                if(pass.equals(DBConstants.MODERATOR_PASS)){
                    ModeratorFrame moderatorFrame = new ModeratorFrame(databaseUtility);
                }
                else{
                    JOptionPane.showMessageDialog(null,"Incorrect passphrase");
                }
            }
        }
    }
    class InfoListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
                JPanel subpanel = new JPanel();
                subpanel.setLayout(new GridLayout(2,3));
                subpanel.add(new JLabel("Tushar Gupta"));
                subpanel.add(new JLabel("COE-1"));
                subpanel.add(new JLabel("2017UCO1530"));
                subpanel.add(new JLabel("Govind Naagar"));
                subpanel.add(new JLabel("COE-1"));
                subpanel.add(new JLabel("2017UCO1531"));

                JPanel panel = new JPanel();
//                panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
                JLabel title = new JLabel("PROJECT CREATED BY");

                panel.add(title);
                panel.add(subpanel);
                JOptionPane.showMessageDialog(null,panel,"About",JOptionPane.PLAIN_MESSAGE);
        }
    }
}
