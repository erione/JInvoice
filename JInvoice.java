/**
 * @(#)collection_book.java
 *
 *
 * @author Brijesh Patel 
 * @version 1.00 2010/7/19
 */

import java.sql.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class JInvoice 
{
	String userName = null, password = null, s1 = null,s2 = null,s3 = null,s4 = null,s5 = null,s6 = null,n = null;
	Connection conn = null;
	public void pass()
	{
		try
		{
		   pass = new JFrame("Login");
		   String nativeLF = UIManager.getSystemLookAndFeelClassName(); // Install the look and feel 
			 UIManager.setLookAndFeel( nativeLF);
			   Image im = Toolkit.getDefaultToolkit().getImage("icons/sign-up-icon_32.png");
				pass.setIconImage( im );
           	   p1 = new JTextField();
           	   p2 = new JPasswordField();
           	   b1 = new JButton("OK");
           	   b1.setToolTipText("Login");
           	   pass.add( new JLabel("Enter Username : ",SwingConstants.CENTER) );
           	   pass.add( p1 );
           	   pass.add( new JLabel("Enter Password : ",SwingConstants.CENTER) );
           	   pass.add( p2 );
           	   pass.add( new JLabel("") );
               		pass.add( b1 );
           	   
           	   KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,false);
           	   p1.registerKeyboardAction(new passListener(), ks, JComponent.WHEN_FOCUSED);
			   p2.registerKeyboardAction(new passListener(), ks, JComponent.WHEN_FOCUSED);
			   b1.registerKeyboardAction(new passListener(), ks, JComponent.WHEN_FOCUSED);
           	   b1.addActionListener( new passListener() );
      		   pass.setLocation(250,250);
           	   pass.setLayout( new GridLayout(3,2,3,3) );
           	   pass.setVisible(true);
           	   pass.setSize(250,115);
           	   pass.setAlwaysOnTop(true);
           	   pass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		}
		
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	class passListener implements ActionListener 
	{
		public void actionPerformed( ActionEvent event )
		{
			try
			{
				userName = p1.getText();
				password = p2.getText();
				String url = "jdbc:mysql://localhost/collection";
            			Class.forName ("com.mysql.jdbc.Driver").newInstance ();
            			conn = DriverManager.getConnection (url, userName, password);
			        Statement st1 = conn.createStatement();
        			st1.executeUpdate("CREATE TABLE IF NOT EXISTS account (id_name CHAR(40) PRIMARY KEY, detail CHAR(50), fees_given INT, total_fees INT)");
			        st1.executeUpdate("CREATE TABLE IF NOT EXISTS invoice(det CHAR(50),fees INT, options ENUM('Y','N') , date DATE, id_name CHAR(40))");
		        	System.out.println ("Database connection established");	
			        pass.hide();
				main_frame();
			}
			catch(Exception e)
			{
				System.err.println( e.getMessage() );
			}
		}
	}
	public void main_frame()
	{
		try
		{
			main = new JFrame("Collection Book");
			Image im = Toolkit.getDefaultToolkit().getImage("icons/check-icon.png");
			main.setIconImage( im );
			Statement s = conn.createStatement();
			s.executeQuery("SELECT id_name FROM account ORDER BY id_name");
			ResultSet rs = s.getResultSet();
			main.setLayout( new BorderLayout(10,10) );
			main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			icon = new ImageIcon("icons/invoice-icon_256.png");
			panel1 = new JPanel( new GridLayout(1,2,5,5));
			panel1.setBorder( new TitledBorder( new EtchedBorder(),"Payment Details" ) );
			panel2 = new JPanel( new GridLayout(6,2,3,3))
			{
				protected void paintComponent(Graphics g)
				{	//Dimension d = main.getSize();
					g.drawImage(icon.getImage(),0,0,null);
					super.paintComponent(g);
				}
			};
			panel2.setOpaque( false );
			panel2.setBorder( new TitledBorder( new EtchedBorder(), "Invoice"  ) );
			r1 = new JRadioButton( "Company wise payment details" );
			r2 = new JRadioButton( "Date wise payment details" );
			ButtonGroup b_group = new ButtonGroup();
			b_group.add(r1);
			b_group.add(r2);
			panel1.add( r1 );
			panel1.add( r2 );
			l1 = new JComboBox();
			j2 = new JTextField( "2010-07-22" );
			j3 = new JTextField();
			j4 = new JTextField();
			j5 = new JTextField();
			b2 = new JButton(" OK ");
			while(rs.next())
			{
				l1.addItem(rs.getString("id_name"));
			}
			l1.setSelectedItem(null);
			panel2.add( new JLabel(" Date (YYYY-MM-DD) : ",SwingConstants.RIGHT));
			panel2.add( j2 );
			panel2.add( new JLabel(" Name ( Press 'Insert' to add new a/c.) : ",SwingConstants.RIGHT));
			panel2.add( l1 );
			panel2.add( new JLabel(" Details : ",SwingConstants.RIGHT));
			panel2.add( j3 );
			panel2.add( new JLabel(" Fees : ",SwingConstants.RIGHT));
			panel2.add( j4 );
			panel2.add( new JLabel(" Cheque (Y/N) : ",SwingConstants.RIGHT));
			panel2.add( j5 );
			panel2.add( new JLabel(""));
			panel2.add( b2 );
			main.getContentPane().add(panel1,BorderLayout.NORTH);
			main.getContentPane().add( panel2 , BorderLayout.CENTER);
			l1.addActionListener( new comboListener() );
			b2.addActionListener( new addListener() );
			r1.addActionListener( new companyWiseListener() );
			r2.addActionListener( new dateWiseListener() );
			KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,false);
				r1.registerKeyboardAction( new companyWiseListener(), ks, JComponent.WHEN_FOCUSED);
				r2.registerKeyboardAction( new dateWiseListener(), ks, JComponent.WHEN_FOCUSED);
	  			j2.registerKeyboardAction( new addListener(), ks, JComponent.WHEN_FOCUSED);
		    	j3.registerKeyboardAction( new addListener(), ks, JComponent.WHEN_FOCUSED);
		    	j4.registerKeyboardAction( new addListener(), ks, JComponent.WHEN_FOCUSED);
    			j5.registerKeyboardAction( new addListener(), ks, JComponent.WHEN_FOCUSED);
    			b2.registerKeyboardAction( new addListener(), ks, JComponent.WHEN_FOCUSED);
    		KeyStroke ks1 = KeyStroke.getKeyStroke(KeyEvent.VK_INSERT,0,false);
    		 	l1.registerKeyboardAction( new addNewListener() , ks1, JComponent.WHEN_IN_FOCUSED_WINDOW );
			main.setLocation(200,150);
			main.setSize(500,375);
			main.setVisible(true);
		}		
		catch(Exception e)
		{	
			System.err.println(e.getMessage());
		}
	}
	class comboListener implements ActionListener 
	{
		public void actionPerformed( ActionEvent event )
		{
			s6 = (String) l1.getSelectedItem();
		}
	}
	class addNewListener implements ActionListener 
	{
		public void actionPerformed( ActionEvent event )
		{
			addAcc = new JFrame( " Add new Account ");
			Image im = Toolkit.getDefaultToolkit().getImage("icons/newAcc.png");
			addAcc.setIconImage( im );
			addAcc.setLayout( new GridLayout(5,2,3,3) );
			js2 = new JTextField();
			js3 = new JTextField();
			js4 = new JTextField();
			js5 = new JTextField();
			b3 = new JButton("OK");
			b3.setIcon( new ImageIcon("icons/newAcc.png"));
			addAcc.add( new JLabel( " Name : " ,SwingConstants.RIGHT));
			addAcc.add( js2 );
			addAcc.add( new JLabel( " Company Details : ",SwingConstants.RIGHT ));
			addAcc.add( js3 );
			addAcc.add( new JLabel( " Fees/year : " ,SwingConstants.RIGHT));
			addAcc.add( js4 );
			addAcc.add( new JLabel( " Fees given : " ,SwingConstants.RIGHT));
			addAcc.add( js5 );
			addAcc.add( new JLabel( "" ));
			addAcc.add( b3 );
			b3.addActionListener( new addAccountListener() );
			KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,false);
	 			js2.registerKeyboardAction( new addAccountListener(), ks, JComponent.WHEN_FOCUSED);
		    	js3.registerKeyboardAction( new addAccountListener(), ks, JComponent.WHEN_FOCUSED);
		    	js4.registerKeyboardAction( new addAccountListener(), ks, JComponent.WHEN_FOCUSED);
    			js5.registerKeyboardAction( new addAccountListener(), ks, JComponent.WHEN_FOCUSED);
    			b3.registerKeyboardAction( new addAccountListener(), ks, JComponent.WHEN_FOCUSED);		
    		addAcc.setLocation( 300,200 );
    		addAcc.setDefaultCloseOperation( JFrame.HIDE_ON_CLOSE );
    		addAcc.setSize( 400,250 );
    		addAcc.setVisible( true );
		}
	}
	
	class addAccountListener implements ActionListener 
	{
		public void actionPerformed( ActionEvent event )
		{
			try
			{
				s2 = js2.getText();
				s3 = js3.getText();
				s4 = js4.getText();
				s5 = js5.getText();
				PreparedStatement s;
				s = conn.prepareStatement( "INSERT INTO account(id_name,detail,total_fees,fees_given) VALUES(?,?,?,?)");
				s.setString(1,s2);
				s.setString(2,s3);
				s.setInt(3,Integer.parseInt(s4));
				s.setInt(4,Integer.parseInt(s5));
				s.executeUpdate();
				s.close();
				addAcc.hide();
				main.hide();
				main_frame();
			}
			catch( Exception e)
			{	
				System.out.println( e.getMessage() );
			}
		}
	}
	public void addData()
	{	try
		{
			PreparedStatement sp1;
			int fees = 0;
			s1 = j2.getText();
			s3 = j3.getText();
			s4 = j4.getText();
			s5 = j5.getText();
			sp1 = conn.prepareStatement("INSERT INTO invoice(date,id_name,det,fees,options) VALUES(?,?,?,?,?)");
			sp1.setString(1,s1);
			sp1.setString(2,s6);
			sp1.setString(3,s3);
			sp1.setInt(4,Integer.parseInt(s4));
			sp1.setString(5,s5);
			sp1.executeUpdate();
			Statement s = conn.createStatement();
			String sql = new String("SELECT fees FROM invoice WHERE id_name = '" + s6 +"'" );
			s.executeQuery(sql);
			ResultSet rs = s.getResultSet();
			while(rs.next())
			{
				fees = fees + rs.getInt("fees");
			}
			sp1 = conn.prepareStatement( "UPDATE account SET fees_given = ? WHERE id_name = ?" );
			sp1.setInt(1,fees);
			sp1.setString(2,s6);
			sp1.executeUpdate();
			l1.setSelectedItem( null );
			j2.setText("");
			j3.setText("");
			j4.setText("");
			j5.setText("");
			rs.close();
			s.close();
			sp1.close();
		}
		catch(Exception e)
		{	
			System.err.println( e.getMessage() );
		}
		
	}
	class addListener implements ActionListener 
	{
		public void actionPerformed( ActionEvent event )
		{
			addData();
		}
	}
	class companyWiseListener implements ActionListener
	{
		public void actionPerformed( ActionEvent event )
	{
			try
			{
				comp_w = new JFrame( "Payment Details");
				comp_w.setLayout( new BorderLayout( 5,5 ));
				Image im = Toolkit.getDefaultToolkit().getImage("icons/check-icon.png");
				comp_w.setIconImage( im );
				panel3 = new JPanel();
				panel3.setLayout( new BorderLayout( 5,5 ));
				panel3.setBorder( new TitledBorder( new EtchedBorder(), "Select Party"));
				panel4 = new JPanel();
				panel4.setLayout( new BorderLayout( 5,5 ) );
				panel4.setBorder( new TitledBorder( new EtchedBorder(), "Party wise payment details"));
				Statement st2 = conn.createStatement();
				st2.executeQuery(" SELECT id_name FROM account ORDER BY id_name ");
				ResultSet rst2 = st2.getResultSet();
				int count = 0;
				l2 = new JComboBox();
				while( rst2.next() )
				{
					l2.addItem( (String)rst2.getString( "id_name" ) );
					++count;
				}
				panel3.add( l2,BorderLayout.NORTH );
				comp_w.add(panel3,BorderLayout.NORTH);
				rst2.close();
				st2.close();
				comp_w.setSize( 800,550 );
				comp_w.setLocation( 150,75 );
				comp_w.setDefaultCloseOperation( JFrame.HIDE_ON_CLOSE );
				comp_w.setVisible( true );
				l2.setSelectedItem( null );
				l2.setAutoscrolls(true);	
				l2.addActionListener( new combo2Listener() );
				KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_F2,0,false);
	 			l2.registerKeyboardAction( new updateAccListener(), ks, JComponent.WHEN_FOCUSED);				
				

				
			}
			catch(Exception e)
			{
				System.err.println( e.getMessage() );
			}
		}
	}
	class combo2Listener implements ActionListener 
	{
		public void actionPerformed( ActionEvent event )
		{
			try
			{	
				n = (String) l2.getSelectedItem();
				PreparedStatement st1;
				st1 = conn.prepareStatement(" SELECT date,id_name,det,fees,options FROM invoice WHERE id_name = ? ORDER BY date ");
				st1.setString(1,n);
				st1.executeQuery();
				ResultSet rst1 = st1.getResultSet();
				int count = 0,sum = 0;
				while(rst1.next())
					++count;
				PreparedStatement st;
				st = conn.prepareStatement(" SELECT date,id_name,det,fees,options FROM invoice WHERE id_name = ? ORDER BY date ");
				st.setString(1,n);
				st.executeQuery();
				ResultSet rst = st.getResultSet();
				String [][] row = new String[count+2][5];
				String [] column = { "Date","Party Name","Payment Details","Payment","Cheque( Y/N )" };
				count = 0;
				while(rst.next())
				{	
					row[count][0] = rst.getString("date");
					row[count][1] = rst.getString("id_name");
					row[count][2] = rst.getString("det");
					row[count][3] = String.valueOf( rst.getInt("fees") );
					row[count][4] = rst.getString("options");
					sum = sum + rst.getInt("fees");
					++count;
				}
				row[count+1][2] = "Total Payment Received : ";
				row[count+1][3] = String.valueOf( sum );
				t1 = new JTable(row,column);
				t1.setAutoscrolls(true);
				t1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
				t1.getColumn("Party Name").setMinWidth(200);
				t1.getColumn("Payment Details").setMinWidth(200);
				KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,false);
	 			t1.registerKeyboardAction( new editListener(), ks, JComponent.WHEN_FOCUSED);
				JScrollPane j = JTable.createScrollPaneForTable(t1);
				j.setWheelScrollingEnabled(true);
				panel4.add(j,BorderLayout.NORTH,0);
				comp_w.add(panel4,BorderLayout.SOUTH);
				comp_w.show();
				rst.close();
				st.close();
				rst1.close();
				st1.close();
				
			}
			catch(Exception e)
			{	
				System.err.println( e.getMessage() );
			}
		}
	}
	class dateWiseListener implements ActionListener
	{
		public void actionPerformed( ActionEvent event )
		{
			try
			{
				date_w = new JFrame( "Payment Details");
				Image im = Toolkit.getDefaultToolkit().getImage("icons/check-icon.png");
				date_w.setIconImage( im );
				date_w.setLayout( new BorderLayout( 5,5 ));
				panel5 = new JPanel();
				panel5.setLayout( new BorderLayout( 5,5 ));
				panel5.setBorder( new TitledBorder( new EtchedBorder(), "Date wise payment details"));
				Statement s1 = conn.createStatement();
				s1.executeQuery(" SELECT date,id_name,det,fees,options FROM invoice ORDER BY date ");
				ResultSet rs1 = s1.getResultSet();
				int c = 0,sum = 0;
				while( rs1.next() )
					++c;
				Statement s = conn.createStatement();
				s.executeQuery(" SELECT date,id_name,det,fees,options FROM invoice ORDER BY date ");
				ResultSet rs = s.getResultSet();
				String [][] row = new String[c][5];
				String [] column = { "Date","Party Name","Payment Details","Payment","Cheque( Y/N )" };
				c=0;
				while(rs.next())
				{	
					row[c][0] = rs.getString("date");
					row[c][1] = rs.getString("id_name");
					row[c][2] = rs.getString("det");
					row[c][3] = String.valueOf( rs.getInt("fees") );
					row[c][4] = rs.getString("options");
					sum = sum + rs.getInt("fees");
					++c;
				}
				t1 = new JTable(row,column);
				t1.setAutoscrolls(true);
				t1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
				t1.getColumn("Party Name").setMinWidth(200);
				t1.getColumn("Payment Details").setMinWidth(200);
				KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,false);
	 			t1.registerKeyboardAction( new editListener(), ks, JComponent.WHEN_FOCUSED);
				JScrollPane j = JTable.createScrollPaneForTable(t1);
				j.setWheelScrollingEnabled(true);
				panel5.add(j,BorderLayout.NORTH);
				panel5.add(new JLabel("Total Payment received : Rs. " + sum + "",SwingConstants.CENTER),BorderLayout.CENTER,0);
				date_w.add(panel5,BorderLayout.CENTER);
				date_w.setSize( 750,500 );
				date_w.setLocation( 150,75 );
				date_w.setDefaultCloseOperation( JFrame.HIDE_ON_CLOSE );
				date_w.setVisible( true );
				rs1.close();
				s1.close();
				rs.close();
				s.close();
			}
			catch(Exception e)
			{	
				System.err.println( e.getMessage() );
			}
		}
	}
	class editListener implements ActionListener 
	{
		public void actionPerformed( ActionEvent event )
		{
			try
			{	
				int r = t1.getSelectedRow();
				edit = new JFrame((String)t1.getValueAt(r,1));
				edit.setLayout( new GridLayout(6,2,3,3) );
				Image im = Toolkit.getDefaultToolkit().getImage("icons/invoice-icon.png");
				edit.setIconImage( im );
				j1 = new JTextField((String)t1.getValueAt(r,1));
				j1.setEditable(false);
				je2 = new JTextField((String)t1.getValueAt(r,0));
				je3 = new JTextField((String)t1.getValueAt(r,2));
				je4 = new JTextField((String)t1.getValueAt(r,3));
				je5 = new JTextField((String)t1.getValueAt(r,4));
				b4 = new JButton( " OK ");
				del = new JButton( "Delete Invoice");
				edit.add( new JLabel(" Party Name : ",SwingConstants.RIGHT));
				edit.add( j1 );
				edit.add( new JLabel(" Date (YYYY-MM-DD) : ",SwingConstants.RIGHT));
				edit.add( je2 );
				edit.add( new JLabel(" Details : ",SwingConstants.RIGHT));
				edit.add( je3 );
				edit.add( new JLabel(" Fees : ",SwingConstants.RIGHT));
				edit.add( je4 );
				edit.add( new JLabel(" Cheque (Y/N) : ",SwingConstants.RIGHT));
				edit.add( je5 );
				edit.add( b4);
				edit.add( del );
				b4.addActionListener( new updateInvoiceListener() );
				del.addActionListener( new delAccListener() );
				KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,false);
				j1.registerKeyboardAction( new updateInvoiceListener(), ks, JComponent.WHEN_FOCUSED);
	 			je2.registerKeyboardAction( new updateInvoiceListener(), ks, JComponent.WHEN_FOCUSED);
			    	je3.registerKeyboardAction( new updateInvoiceListener(), ks, JComponent.WHEN_FOCUSED);
			    	je4.registerKeyboardAction( new updateInvoiceListener(), ks, JComponent.WHEN_FOCUSED);
    				je5.registerKeyboardAction( new updateInvoiceListener(), ks, JComponent.WHEN_FOCUSED);
    				b4.registerKeyboardAction( new updateInvoiceListener(), ks, JComponent.WHEN_FOCUSED);		
    				del.registerKeyboardAction( new delAccListener(), ks, JComponent.WHEN_FOCUSED);			
	    			edit.setLocation( 300,200 );
    				edit.setDefaultCloseOperation( JFrame.HIDE_ON_CLOSE );
    				edit.setSize( 400,250 );
    				edit.setVisible( true );
    				
			}
			catch(Exception e)
			{	
				System.err.println( e.getMessage() );
			}
		}
	}
	class updateInvoiceListener implements ActionListener
	{
		public void actionPerformed( ActionEvent event )
		{
			try
			{	
				int r = t1.getSelectedRow();
				int fees = 0;
				PreparedStatement ps;
				ps = conn.prepareStatement("UPDATE invoice set date = ?,det = ?,fees = ?,options = ? WHERE id_name = ? AND date = ? AND det = ? AND fees = ? AND options = ? ");				
				ps.setString(1,je2.getText() );
				ps.setString(2,je3.getText() );
				ps.setInt(3,Integer.parseInt(je4.getText()) );
				ps.setString(4,je5.getText() );
				ps.setString(5,j1.getText() );
				ps.setString(6,(String)t1.getValueAt(r,0));
				ps.setString(7,(String)t1.getValueAt(r,2));
				ps.setInt(8,Integer.parseInt( (String)t1.getValueAt(r,3)));
				ps.setString( 9, (String)t1.getValueAt(r,4));
				ps.executeUpdate();
				Statement s = conn.createStatement();
				String sql = new String("SELECT fees FROM invoice WHERE id_name = '" + j1.getText() +"'" );
				s.executeQuery(sql);
				ResultSet rs = s.getResultSet();
				while(rs.next())
				{
					fees = fees + rs.getInt("fees");
				}
				ps = conn.prepareStatement( "UPDATE account SET fees_given = ? WHERE id_name = ?" );
				ps.setInt(1,fees);
				ps.setString(2,j1.getText());
				ps.executeUpdate();
				edit.hide();
				t1.setValueAt(je2.getText(),r,0);
				t1.setValueAt(je3.getText(),r,2);
				t1.setValueAt(je4.getText(),r,3);
				t1.setValueAt(je5.getText(),r,4);
				s.close();
				rs.close();
				ps.close();
			}
			catch(Exception e)
			{
				System.err.println( e.getMessage() );
			}
		}
	}
	class delAccListener implements ActionListener
	{
		public void actionPerformed( ActionEvent event )
		{
			try
			{
				int r = t1.getSelectedRow();
				int fees = 0;
				PreparedStatement ps;
				ps = conn.prepareStatement("DELETE FROM invoice WHERE id_name = ? AND date = ? AND det = ? AND fees = ? AND options = ? ");
				ps.setString(1,j1.getText() );
				ps.setString(2,je2.getText() );
				ps.setString(3,je3.getText() );
				ps.setInt(4,Integer.parseInt(je4.getText()) );
				ps.setString(5,je5.getText() );
				ps.executeUpdate();
				edit.hide();
				Statement s = conn.createStatement();
				String sql = new String("SELECT fees FROM invoice WHERE id_name = '" + j1.getText() +"'" );
				s.executeQuery(sql);
				ResultSet rs = s.getResultSet();
				while(rs.next())
				{
					fees = fees + rs.getInt("fees");
				}
				ps = conn.prepareStatement( "UPDATE account SET fees_given = ? WHERE id_name = ?" );
				ps.setInt(1,fees);
				ps.setString(2,j1.getText());
				ps.executeUpdate();
				t1.setValueAt(null,r,0);
				t1.setValueAt(null,r,1);
				t1.setValueAt(null,r,2);
				t1.setValueAt(null,r,3);
				t1.setValueAt(null,r,4);
				s.close();
				rs.close();
				ps.close();
	
			}
			catch(Exception e)
			{
				System.err.println( e.getMessage() );
			}
		}
	}
	class updateAccListener implements ActionListener
	{
		public void actionPerformed( ActionEvent event )
		{
			try
			{
				userName = (String)l2.getSelectedItem();
				PreparedStatement ps;
				ps = conn.prepareStatement("SELECT * FROM account WHERE id_name = ?");
				ps.setString(1,userName );
				ps.executeQuery();
				ResultSet rs = ps.getResultSet();				
				update = new JFrame(userName);
				update.setLayout( new GridLayout(4,2,3,3) );
				js1 = new JTextField(userName);
				js1.setEditable(false);
				while(rs.next())
				{
					js2 = new JTextField(rs.getString("detail"));
					js3 = new JTextField(String.valueOf(rs.getInt("total_fees")));
					js4 = new JTextField(String.valueOf(rs.getInt("fees_given")));
				}
				js4.setEditable( false );
				update.add( new JLabel(" Party Name : ",SwingConstants.RIGHT));
				update.add( js1 );
				update.add( new JLabel(" Party Details : ",SwingConstants.RIGHT));
				update.add( js2 );
				update.add( new JLabel(" Fees/year : ",SwingConstants.RIGHT));
				update.add( js3 );
				update.add( new JLabel(" Fees given : ",SwingConstants.RIGHT));
				update.add( js4 );
				KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0,false);
					js1.registerKeyboardAction( new updateListener(), ks, JComponent.WHEN_FOCUSED);
	 				js2.registerKeyboardAction( new updateListener(), ks, JComponent.WHEN_FOCUSED);
			    	js3.registerKeyboardAction( new updateListener(), ks, JComponent.WHEN_FOCUSED);
			    	js4.registerKeyboardAction( new updateListener(), ks, JComponent.WHEN_FOCUSED);
    				update.setLocation( 300,200 );
    				update.setDefaultCloseOperation( JFrame.HIDE_ON_CLOSE );
    				update.setSize( 350,175 );
    				update.setVisible( true );
				rs.close();
				ps.close();
			}
			catch(Exception e)
			{
				System.err.println( e.getMessage() );
			}
		}
	}
	
	class updateListener implements ActionListener
	{
		public void actionPerformed( ActionEvent event )
		{
			try
			{
				PreparedStatement ps;
				ps = conn.prepareStatement("UPDATE account SET detail = ?,total_fees = ?,fees_given = ? WHERE id_name = ?");
				ps.setString(1,js2.getText() );
				ps.setInt(2,Integer.parseInt(js3.getText()) );
				ps.setInt(3,Integer.parseInt(js4.getText()) );
				ps.setString(4,js1.getText() );
				ps.executeUpdate();
				ps.close();
				update.hide();
			}
			catch(Exception e)
			{
				System.err.println( e.getMessage() );
			}
		}
	}
	public static void main( String [] args)
	{
		JInvoice jinv = new JInvoice();
		jinv.pass();
	}
	JComboBox l1,l2;
	JFrame pass,main,addAcc,comp_w,date_w,edit,update;
	JTextField p1,j1,j2,j3,j4,j5,js1,js2,js3,js4,js5,je2,je3,je4,je5;
	JPasswordField p2;
	JRadioButton r1,r2;
	JButton b1,b2,b3,acc,b4,del;
	ActionListener lis1;
	JPanel panel1,panel2,panel3,panel4,panel5;
	JTable t1;
	ImageIcon icon;
}

    
    
    
