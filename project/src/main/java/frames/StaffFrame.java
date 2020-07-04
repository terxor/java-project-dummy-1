package frames;

import core.DatabaseUtility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StaffFrame {
    DatabaseUtility databaseUtility;
    JFrame frame;

    //info panel
    JPanel infoPanel;
        JLabel infoLabelName = new JLabel("Name: ");
        JLabel infoLabelDOB = new JLabel("Date of Birth: ");
        JLabel infoLabelSex = new JLabel("Sex: ");
        JLabel infoLabelPhone = new JLabel("Phone No.: ");
        JLabel infoLabelAddress = new JLabel("Address: ");
        JLabel infoLabelDept = new JLabel("Department: ");
        JLabel infoLabelJoinDate = new JLabel("Date of Joining: ");

        JLabel infoLabelName_val;
        JLabel infoLabelDOB_val;
        JLabel infoLabelSex_val;
        JLabel infoLabelPhone_val;
        JLabel infoLabelAddress_val;
        JLabel infoLabelDept_val;
        JLabel infoLabelJoinDate_val;


    //button panel
    JPanel btnPanel;
        JButton salaryBtn;
        JButton leaveBtn;
        JButton updatePhoneBtn;
        JButton updateAddress;

        private String staffID;
    StaffFrame(DatabaseUtility databaseUtility,String staffID){
        this.databaseUtility = databaseUtility;
        this.staffID = staffID;
        String[] staffInfo = databaseUtility.getSingleRowValues("select * from staff where staff_id="+staffID+";");

        frame = new JFrame("Staff Informmation");

        // infoPanel
        infoPanel = new JPanel();
//        infoPanel.setLayout(new BoxLayout(infoPanel,BoxLayout.Y_AXIS));
        infoPanel.setLayout(new GridLayout(7,2,0,10));

        infoLabelName_val = new JLabel(staffInfo[1]);
        infoLabelAddress_val = new JLabel(staffInfo[2]);
        infoLabelDOB_val = new JLabel(staffInfo[3]);
        infoLabelSex_val = new JLabel(staffInfo[4]);
        infoLabelJoinDate_val = new JLabel(staffInfo[4]);
        infoLabelPhone_val = new JLabel(staffInfo[5]);
        String deptName = databaseUtility.getSingleRowValues("select dept_name from departments where dept_id="+staffInfo[9]+";")[0];
        infoLabelDept_val = new JLabel(deptName);

        infoPanel.add(infoLabelName);
        infoPanel.add(infoLabelName_val);

        infoPanel.add(infoLabelDOB);
        infoPanel.add(infoLabelDOB_val);

        infoPanel.add(infoLabelSex);
        infoPanel.add(infoLabelSex_val);

        infoPanel.add(infoLabelPhone);
        infoPanel.add(infoLabelPhone_val);

        infoPanel.add(infoLabelAddress);
        infoPanel.add(infoLabelAddress_val);

        infoPanel.add(infoLabelDept);
        infoPanel.add(infoLabelDept_val);

        infoPanel.add(infoLabelJoinDate);
        infoPanel.add(infoLabelJoinDate_val);

        infoPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));



        //button panel
        btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(4,1,0,20));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        salaryBtn = new JButton("View salary details");
        leaveBtn = new JButton("View leave details");
        updatePhoneBtn = new JButton("Update phone number");
        updateAddress = new JButton("Update Address");

        btnPanel.add(salaryBtn);
        btnPanel.add(leaveBtn);
        btnPanel.add(updatePhoneBtn);
        btnPanel.add(updateAddress);
        updateAddress.addActionListener(new UpdateAddressBtnListener());
        updatePhoneBtn.addActionListener(new UpdatePhoneBtnListener());
        salaryBtn.addActionListener(new SalaryBtnListener());
        leaveBtn.addActionListener(new LeaveBtnListener());

        JPanel header = new JPanel();
        header.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        JLabel title = new JLabel("STAFF PORTAL");
        title.setFont(new Font("Monospaced", Font.BOLD, 20));
        header.add(title);
        frame.getContentPane().add(header,BorderLayout.NORTH);
        frame.getContentPane().add(infoPanel,BorderLayout.WEST);
        frame.getContentPane().add(btnPanel,BorderLayout.EAST);

        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);



    }
    class SalaryBtnListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = new JTable();
            String query = "select * from salary where staff_id="+staffID+";";
            table.setModel(databaseUtility.getData(query));
            JScrollPane pane = new JScrollPane(table);

            JOptionPane.showMessageDialog(null,pane,"Salary Info",JOptionPane.PLAIN_MESSAGE);
        }
    }

    class LeaveBtnListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JTable table = new JTable();
            String query = "select * from leaves where staff_id="+staffID+";";
            table.setModel(databaseUtility.getData(query));
            JScrollPane pane = new JScrollPane(table);

            JOptionPane.showMessageDialog(null,pane,"Leave Info",JOptionPane.PLAIN_MESSAGE);
        }
    }

    class UpdateAddressBtnListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextArea inputAddress = new JTextArea(3,15);
            JPanel panel = new JPanel();
            panel.add(new JLabel("Enter updated address"));
            panel.add(inputAddress);
            inputAddress.setText(infoLabelAddress_val.getText());

            int zz =JOptionPane.showConfirmDialog(null, panel, "Update Address",JOptionPane.OK_CANCEL_OPTION);
            if(zz == JOptionPane.OK_OPTION){
                if(!inputAddress.getText().trim().equals("")){
                    String query = "update staff set staff_address = \""+inputAddress.getText()+"\" where staff_id="+staffID+";";
                    System.out.println(query);
                    int result = databaseUtility.executeUpdate(query);
                    if(result == 1){
                        JOptionPane.showMessageDialog(null,"Updated successfully");
                        updateValueSet();
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Unable to update address");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"Unable to update address");
                }
            }

        }
    }

    class UpdatePhoneBtnListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField inputPhone = new JTextField(15);
            JPanel panel = new JPanel();
            panel.add(new JLabel("Enter updated phone number"));
            panel.add(inputPhone);
            inputPhone.setText(infoLabelPhone_val.getText());

            int zz =JOptionPane.showConfirmDialog(null, panel, "Update Address",JOptionPane.OK_CANCEL_OPTION);
            if(zz == JOptionPane.OK_OPTION){
                if(!inputPhone.getText().trim().equals("")){
                    String query = "update staff set staff_phone = \""+inputPhone.getText()+"\" where staff_id="+staffID+";";
                    System.out.println(query);
                    int result = databaseUtility.executeUpdate(query);
                    if(result == 1){
                        JOptionPane.showMessageDialog(null,"Updated successfully");
                        updateValueSet();
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Unable to update phone number");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"Unable to update phone number");
                }
            }

        }
    }

    public void updateValueSet(){
        String[] staffInfo = databaseUtility.getSingleRowValues("select * from staff where staff_id="+staffID+";");
        infoLabelAddress_val.setText(staffInfo[2]);
        infoLabelPhone_val.setText(staffInfo[5]);

    }
}
