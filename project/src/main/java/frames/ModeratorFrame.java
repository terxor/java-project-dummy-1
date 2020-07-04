package frames;

import core.DatabaseUtility;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModeratorFrame {
    DatabaseUtility databaseUtility;
    JFrame frame;
    JPanel panel;

    //buttons
    JButton addLeave = new JButton("Add Leave");
    JButton addSalary = new JButton("Add Salary");
    JButton addStaff = new JButton("Add Staff");
    JButton viewStaffList = new JButton("View staff list");

    ModeratorFrame(DatabaseUtility dbUtility){
        databaseUtility = dbUtility;
        frame = new JFrame("Moderator Portal");

        panel = new JPanel(new GridLayout(2,2,20,20));

        //add listeners to buttons
        addLeave.addActionListener(new AddLeaveBtnListener());
        addSalary.addActionListener(new AddSalaryBtnListener());
        addStaff.addActionListener(new AddStaffBtnListener());
        viewStaffList.addActionListener(new ViewStaffBtnListener());

        //add buttons to an array for easier adding to panel
        JButton[] buttons = {addLeave,addSalary,addStaff,viewStaffList};
        for(JButton button:buttons){
            panel.add(button);
        }

        JLabel title = new JLabel("MODERATOR PANEL");
        title.setFont(new Font("Monospaced", Font.PLAIN, 30));
        JPanel header = new JPanel();
        header.add(title);
        header.setBorder(BorderFactory.createEtchedBorder());
        title.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        frame.getContentPane().add(header,BorderLayout.NORTH);
        frame.getContentPane().add(panel,BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }

    class AddStaffBtnListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
                JFrame addStaffFrame = new JFrame("Add a staff member");
                //===================
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

                JPanel panelA = new JPanel(new GridLayout(9,2,0,0));

                //===================
                JTable table = new JTable();
                table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                String queryCoursesPartial = "select course_id,course_name,course_desc from courses where floor(course_id/100) =";
                table.setModel(databaseUtility.getData(queryCoursesPartial+"5001;"));
                table.setRowSelectionInterval(0,0);

                //===================

                JTextField inputName = new JTextField(15);
                JTextField inputAddress = new JTextField(15);
                JTextField inputDOB = new JTextField(15);
                JTextField inputPhone = new JTextField(15);

                //===================
                ButtonGroup selectSGroup = new ButtonGroup();
                JRadioButton sChoice1 = new JRadioButton("Male");
                JRadioButton sChoice2 = new JRadioButton("Female");
                selectSGroup.add(sChoice1);
                selectSGroup.add(sChoice2);
                //initially male selected
                sChoice1.setSelected(true);

                JTextField inputJoinDate = new JTextField(15);

                //TYPE SELECTION =====================================
                ButtonGroup selectType = new ButtonGroup();
                JRadioButton typeFaculty = new JRadioButton("Faculty");
                JRadioButton typeNonAcademic = new JRadioButton("Non Academic");
                selectType.add(typeFaculty);
                selectType.add(typeNonAcademic);
                //initially faculty selected
                typeFaculty.setSelected(true);

                String queryDeptFaculty = "select dept_name from departments where dept_type = 'Academic';";
                String queryDeptNA = "select dept_name from departments where dept_type = 'Non Academic';";
                String[] departments = databaseUtility.getSingleColumnValues(queryDeptFaculty);
                JComboBox<String> deptDropdown= new JComboBox<>(departments);
                class TypeFacultyListener implements ActionListener{
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        DefaultComboBoxModel dm =new DefaultComboBoxModel(databaseUtility.getSingleColumnValues(queryDeptFaculty));
                        deptDropdown.setModel(dm);
                        table.setModel(databaseUtility.getData(queryCoursesPartial+"5001;"));
                        table.setRowSelectionInterval(0,0);
                        table.setVisible(true);
                    }
                }
                class TypeNAListener implements ActionListener{
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        DefaultComboBoxModel dm =new DefaultComboBoxModel(databaseUtility.getSingleColumnValues(queryDeptNA));
                        deptDropdown.setModel(dm);
                        table.setVisible(false);
                        table.setModel(new DefaultTableModel(0,0));
                    }
                }
                typeFaculty.addActionListener(new TypeFacultyListener());
                typeNonAcademic.addActionListener(new TypeNAListener());
                //=============================================================

                String[] roles = databaseUtility.getSingleColumnValues("select role_name from roles;");
                JComboBox<String> roleDropdown= new JComboBox<>(roles);

                //================================
                JButton addBtn = new JButton("Add staff member");
                class AddListener implements ActionListener{
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(inputName.getText().trim().equals("") ||
                            inputDOB.getText().trim().equals("") ||
                            inputAddress.getText().trim().equals("") ||
                            inputJoinDate.getText().trim().equals("") ||
                                inputPhone.getText().trim().equals("")
                        ){
                            JOptionPane.showMessageDialog(addStaffFrame,"One or more fields are empty");
                        }
                        else {
                            String[] values = new String[9];
                            values[0] = "'" + inputName.getText() + "'";
                            values[1] = "'" + inputAddress.getText() + "'";
                            values[2] = "'" + inputDOB.getText() + "'";
                            if (sChoice1.isSelected()) values[3] = "'Male'";
                            else values[3] = "'Female'";
                            values[4] = "'" + inputPhone.getText() + "'";
                            values[5] = "'" + inputJoinDate.getText() + "'";
                            values[6] = "null";
                            if (typeFaculty.isSelected()) {
                                values[6] = "'" + table.getValueAt(table.getSelectedRow(), 0).toString() + "'";
                            }
                            String roleName = roleDropdown.getSelectedItem().toString();
                            String[] role = databaseUtility.getSingleRowValues("select * from roles where role_name ='" + roleName + "';");
                            values[7] = "'" + role[0] + "'";

                            String dept = deptDropdown.getSelectedItem().toString();
                            String[] deptInfo = databaseUtility.getSingleRowValues("select * from departments where dept_name ='" + dept + "';");
                            values[8] = "'" + deptInfo[0] + "'";

                            String queryPartial = "insert into staff(staff_name,staff_address,staff_dob,staff_sex,staff_phone,staff_joindate,course_id,role_id,dept_id) values ";
                            StringBuilder xyz = new StringBuilder("(");
                            for (int i = 0; i < values.length - 1; i++) xyz.append(values[i]).append(",");
                            xyz.append(values[values.length - 1]).append(");");
                            queryPartial += xyz;
                            JOptionPane.showMessageDialog(null, queryPartial);
                            int zzz = databaseUtility.executeUpdate(queryPartial);
                            if(zzz == 1){
                                JOptionPane.showMessageDialog(addStaffFrame,"Added member successfully");
                                addStaffFrame.dispose();
                            }
                        }
                    }
                }
                addBtn.addActionListener(new AddListener());
                //===================  ADDING COMPONENTS TO PANEL
                panelA.add(new JLabel("Name"));
                panelA.add(inputName);

                panelA.add(new JLabel("Address"));
                panelA.add(inputAddress);

                panelA.add(new JLabel("Date of Birth"));
                panelA.add(inputDOB);

                panelA.add(sChoice1);
                panelA.add(sChoice2);

                panelA.add(new JLabel("Phone No."));
                panelA.add(inputPhone);

                panelA.add(new JLabel("Join date"));
                panelA.add(inputJoinDate);

                panelA.add(typeFaculty);
                panelA.add(typeNonAcademic);

                panelA.add(new JLabel("Select department"));
                panelA.add(deptDropdown);

                panelA.add(new JLabel("Select role"));
                panelA.add(roleDropdown);

                JScrollPane tablePanel = new JScrollPane(table);
                tablePanel.setPreferredSize(new Dimension(400,200));
                panel.add(panelA);
                panel.add(tablePanel);
                panel.add(addBtn);

                panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
                panelA.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
                tablePanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
                //===================
                class DeptDropdownListener implements ActionListener{
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String dept = deptDropdown.getSelectedItem().toString();
                        String[] deptInfo = databaseUtility.getSingleRowValues("select * from departments where dept_name ='"+dept+"';");
                        int deptId = Integer.parseInt(deptInfo[0]);
                        String query = queryCoursesPartial + deptId + ";";
                        table.setModel(databaseUtility.getData(query));
                    }
                }
            //===================
            deptDropdown.addActionListener(new DeptDropdownListener());
                addStaffFrame.add(panel);
                addStaffFrame.pack();
                addStaffFrame.setResizable(false);
                addStaffFrame.setVisible(true);


        }
    }

    class AddLeaveBtnListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField inputStaff = new JTextField(15);
            String[] leaveTypes = {"Medical","Paid Leave","Unpaid Leave"};
            JComboBox<String> inputType = new JComboBox<>(leaveTypes);
            JTextField inputStartDate = new JTextField(15);
            JTextField inputEndDate = new JTextField(15);

            JPanel subpanel = new JPanel();
            subpanel.setLayout(new GridLayout(4,2));

            subpanel.add(new JLabel("Staff ID"));
            subpanel.add(inputStaff);

            subpanel.add(new JLabel("Leave Type"));
            subpanel.add(inputType);

            subpanel.add(new JLabel("Start Date"));
            subpanel.add(inputStartDate);

            subpanel.add(new JLabel("End Date"));
            subpanel.add(inputEndDate);

            int zz = JOptionPane.showConfirmDialog(null,subpanel,"Add leave entry",JOptionPane.OK_CANCEL_OPTION);
            if(zz == JOptionPane.OK_OPTION){
                String partialQuery = "insert into leaves(staff_id,leave_type,leave_startdate,leave_enddate) values(";
                String[] values = {inputStaff.getText(),inputType.getSelectedItem().toString(),inputStartDate.getText(),inputEndDate.getText()};
                boolean valid = true;
                for(String i : values){
                    if (i.trim().equals("")) {
                        valid = false;
                        break;
                    }
                }
                if(valid){
                    String[] staffValues = databaseUtility.getSingleColumnValues("select staff_id from staff where staff_id ='"+values[0]+"';");
                    if(staffValues.length == 1){
                        partialQuery+= "'"+values[0]+"','"+values[1]+"','"+values[2]+"','"+values[3]+"');";
                        if(databaseUtility.executeUpdate(partialQuery) == 1){
                            JOptionPane.showMessageDialog(null,"Added Entry");
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"Unable to find staff member with given ID");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Unable to add entry");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"Unable to add entry");
                }
            }

        }
    }

    class AddSalaryBtnListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField inputStaff = new JTextField(15);
            JTextField inputAmount = new JTextField(15);
            JTextField inputDate = new JTextField(15);

            JPanel subpanel = new JPanel();
            subpanel.setLayout(new GridLayout(3,2));

            subpanel.add(new JLabel("Staff ID"));
            subpanel.add(inputStaff);

            subpanel.add(new JLabel("Salary Amount"));
            subpanel.add(inputAmount);

            subpanel.add(new JLabel("Pay Date"));
            subpanel.add(inputDate);


            int zz = JOptionPane.showConfirmDialog(null,subpanel,"Add salary",JOptionPane.OK_CANCEL_OPTION);
            if(zz == JOptionPane.OK_OPTION){
                String partialQuery = "insert into salary(staff_id,salary_amount,salary_date) values(";
                String[] values = {inputStaff.getText(),inputAmount.getText(),inputDate.getText()};
                boolean valid = true;
                for(String i : values){
                    if (i.trim().equals("")) {
                        valid = false;
                        break;
                    }
                }
                if(valid){
                    String[] staffValues = databaseUtility.getSingleColumnValues("select staff_id from staff where staff_id ='"+values[0]+"';");
                    if(staffValues.length == 1){
                        partialQuery+= "'"+values[0]+"','"+values[1]+"','"+values[2]+"');";
                        if(databaseUtility.executeUpdate(partialQuery) == 1){
                            JOptionPane.showMessageDialog(null,"Added Entry");
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"Unable to find staff member with given ID");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Unable to add entry");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null,"Unable to add entry");
                }
            }
        }
    }

    class ViewStaffBtnListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JFrame staffListFrame = new JFrame("STAFF LIST");
            staffListFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

            JTable table = new JTable(databaseUtility.getData("select * from staff;"));
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            class RemoveListener implements ActionListener{
                @Override
                public void actionPerformed(ActionEvent e) {
                    int x = table.getSelectedRow();
                    if(x == -1){
                        JOptionPane.showMessageDialog(null,"Please select a staff member from list");
                    }
                    else{
                        String s_id = table.getValueAt(x,0).toString();
                        int z = databaseUtility.executeUpdate("delete from staff where staff_id="+s_id+";");
                        if(z == 1){
                            JOptionPane.showMessageDialog(null,"Staff Member removed");
                        }
                        table.setModel(databaseUtility.getData("select * from staff;"));
                    }

                }
            }
            JButton removeBtn = new JButton("Remove selected staff member");
            removeBtn.addActionListener(new RemoveListener());
            panel.add(new JScrollPane(table));
            JPanel btnPanel = new JPanel();
            btnPanel.add(removeBtn);
            panel.add(btnPanel);
            panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

            panel.setPreferredSize(new Dimension(800,400));
            staffListFrame.add(panel);

            staffListFrame.pack();
            staffListFrame.setVisible(true);
        }
    }
}
