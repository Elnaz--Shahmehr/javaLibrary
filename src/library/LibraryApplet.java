package library;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class LibraryApplet extends JFrame{

	private JPanel welcome;
	private JPanel mainMenu;
	private JPanel newBook;
	private JPanel newBorrower;
	private JPanel borrowBook;

	private Library smallLibrary = new Library();

	public void init(){
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("Closing...");
				if (smallLibrary.getDb() != null)
					smallLibrary.getDb().close();

			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});

		welcome = createWelcomePanel();
		add(welcome);
		welcome.setVisible(true);
	}

	private JPanel createMainMenu(){
		JPanel panel = new JPanel();

		JButton newBorrowerB = new JButton("New Borrower");
		newBorrowerB.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				LibraryApplet.this.mainMenu.setVisible(false);
				LibraryApplet.this.newBorrower = LibraryApplet.this.createNewBorrower();
				LibraryApplet.this.add(LibraryApplet.this.newBorrower);
				LibraryApplet.this.newBorrower.setVisible(true);
			}

		});
		panel.add(newBorrowerB);

		JButton newBookB = new JButton("New Book");
		newBookB.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				LibraryApplet.this.mainMenu.setVisible(false);
				LibraryApplet.this.newBook = LibraryApplet.this.createNewBook();
				LibraryApplet.this.add(LibraryApplet.this.newBook);
				LibraryApplet.this.newBook.setVisible(true);
			}

		});

		panel.add(newBookB);

		JButton borrowBookB = new JButton("Borrow Book");
		borrowBookB.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				LibraryApplet.this.mainMenu.setVisible(false);
				LibraryApplet.this.borrowBook = LibraryApplet.this.createBorroweBook();
				LibraryApplet.this.add(LibraryApplet.this.borrowBook);
				LibraryApplet.this.borrowBook.setVisible(true);
			}

		});

		panel.add(borrowBookB);

		return panel;
	}

	protected JPanel createNewBook() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		panel.add( new JLabel("New Book"), setSettings(c,1,0,.1,1));

		panel.add( new JLabel("ISBN:"), setSettings(c,0,1,.1,1));
		final JTextField isbn = new JTextField();
		panel.add(isbn,setSettings(c,1,1,1.5,1));

		panel.add( new JLabel("Title:"), setSettings(c,0,2,.1,1));
		final JTextField title = new JTextField();
		panel.add(title,setSettings(c,1,2,1.5,1));

		panel.add( new JLabel("Author:"), setSettings(c,0,3,.1,1));
		final JTextField author = new JTextField();
		panel.add(author,setSettings(c,1,3,1.5,1));

		JButton nextB = new JButton("Add");
		nextB.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String result;

				result = Validation.isValidISBN(isbn.getText());
				if (result != null) {
					JOptionPane.showMessageDialog(LibraryApplet.this, result);
					return;
				}

				result = Validation.isValidAuthor(author.getText());
				if (result != null) {
					JOptionPane.showMessageDialog(LibraryApplet.this, result);
					return;
				}

				result = Validation.isValidTitle(title.getText());
				if (result != null) {
					JOptionPane.showMessageDialog(LibraryApplet.this, result);
					return;
				}
				Book book = new Book(isbn.getText(),title.getText(),author.getText());
				DbManager db = smallLibrary.getDb();
				db.insertBook(book);
				JOptionPane.showMessageDialog(LibraryApplet.this, "New Book '" + title.getText() + "' successfully added!");
				LibraryApplet.this.newBook.setVisible(false);
				LibraryApplet.this.mainMenu.setVisible(true);
			}
		});
		panel.add(nextB, setSettings(c, 1, 4, 0, 1));


		return panel;
	}

	protected JPanel createBorroweBook() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		panel.add( new JLabel("Borrow a book"), setSettings(c,1,0,.1,1));

		panel.add( new JLabel("ISBN:"), setSettings(c,0,1,.1,1));
		final JTextField isbn = new JTextField();
		panel.add(isbn,setSettings(c,1,1,1.5,1));

		panel.add( new JLabel("library card number:"), setSettings(c,0,2,.1,1));
		final JTextField libraryCardNumber = new JTextField();

		panel.add(libraryCardNumber,setSettings(c,1,2,1.5,1));

		JButton nextB = new JButton("Borrow");
		nextB.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (!libraryCardNumber.getText().matches("[\\d]+")) {
					JOptionPane.showMessageDialog(LibraryApplet.this, "Library card number should be a number!");
					return;
				}
				String result;
				DbManager db = smallLibrary.getDb();
				result = db.borrowBook(isbn.getText(), Integer.parseInt(libraryCardNumber.getText()));

				if (result != null) {
					JOptionPane.showMessageDialog(LibraryApplet.this, result);
					return;
				}

				JOptionPane.showMessageDialog(LibraryApplet.this, "The book was borrowed!");

				LibraryApplet.this.borrowBook.setVisible(false);
				LibraryApplet.this.mainMenu.setVisible(true);
			}
		});
		panel.add(nextB, setSettings(c, 1, 4, 0, 1));


		return panel;
	}

	protected JPanel createNewBorrower() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		panel.add( new JLabel("New Borrower"), setSettings(c,1,0,.1,1));

		panel.add( new JLabel("Name:"), setSettings(c,0,1,.1,1));
		final JTextField name = new JTextField();
		panel.add(name,setSettings(c,1,1,1.5,1));

		panel.add( new JLabel("Street Address:"), setSettings(c,0,2,.1,1));
		final JTextField streetAddress = new JTextField();
		panel.add(streetAddress,setSettings(c,1,2,1.5,1));

		panel.add( new JLabel("PostalCode:"), setSettings(c,0,3,.1,1));
		final JTextField postalCode = new JTextField();
		panel.add(postalCode,setSettings(c,1,3,1.5,1));

		panel.add( new JLabel("City:"), setSettings(c,0,4,.1,1));
		final JTextField city = new JTextField();
		panel.add(city,setSettings(c,1,4,1.5,1));

		JButton nextB = new JButton("Add");
		nextB.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Borrower newBorrower = new Borrower(name.getText(),streetAddress.getText(),postalCode.getText(),city.getText());
				DbManager db = smallLibrary.getDb();
				db.insertBorrower(newBorrower);
				JOptionPane.showMessageDialog(LibraryApplet.this, "New borrower '" + name.getText() + "' successfully added!");
				LibraryApplet.this.newBorrower.setVisible(false);
				LibraryApplet.this.mainMenu.setVisible(true);
			}
		});
		panel.add(nextB, setSettings(c, 1, 5, 0, 1));


		return panel;
	}
	private JPanel createWelcomePanel(){
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		panel.add( new JLabel("Welcome to Small Library"), setSettings(c,1,0,0,0));
		panel.add( new JLabel("Please fill out your database credentials below:"), setSettings(c,1,1,0,1));

		panel.add( new JLabel("Book file:"), setSettings(c,0,2,.1,1));
		final JTextField bookPath = new JTextField();
		panel.add(bookPath,setSettings(c,1,2,1.5,1));
		JButton browse = new JButton("...");

		browse.addActionListener( new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setMultiSelectionEnabled(false);
				if(jfc.showOpenDialog(LibraryApplet.this) == JFileChooser.APPROVE_OPTION){
					bookPath.setText(jfc.getSelectedFile().getAbsolutePath());
				}
			}
		});
		panel.add(browse,setSettings(c,2,2,.01,1));

		panel.add( new JLabel("Borrower file:"), setSettings(c,0,3,.1,1));
		final JTextField borrowerPath = new JTextField();
		panel.add(borrowerPath,setSettings(c,1,3,1.5,1));
		JButton browseB = new JButton("...");

		browseB.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setMultiSelectionEnabled(false);
				if(jfc.showOpenDialog(LibraryApplet.this) == JFileChooser.APPROVE_OPTION){
					borrowerPath.setText(jfc.getSelectedFile().getAbsolutePath());
				}
			}
		});
		panel.add(browseB,setSettings(c,2,3,.01,1));


		panel.add( new JLabel("DataBase name:"), setSettings(c,0,4,.1,1));
		final JTextField dbName = new JTextField();
		panel.add(dbName,setSettings(c,1,4,1.5,1));

		panel.add( new JLabel("root Username:"), setSettings(c,0,5,.1,1));
		final JTextField userName = new JTextField();
		userName.setText("root");
		panel.add(userName,setSettings(c,1,5,1.5,1));

		panel.add( new JLabel("Password:"), setSettings(c,0,6,.1,1));
		final JPasswordField password = new JPasswordField();
		panel.add(password,setSettings(c,1,6,1.5,1));

		JButton nextB = new JButton("Next");
		nextB.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				smallLibrary.doPartOne(bookPath.getText(), borrowerPath.getText(), dbName.getText(), userName.getText(), new String(password.getPassword()));
				LibraryApplet.this.welcome.setVisible(false);
				LibraryApplet.this.mainMenu = LibraryApplet.this.createMainMenu();
				LibraryApplet.this.add(LibraryApplet.this.mainMenu);
				LibraryApplet.this.mainMenu.setVisible(true);
			}
		});
		panel.add(nextB, setSettings(c, 1, 7, 0, 1));


		return panel;
	}

	private GridBagConstraints setSettings(GridBagConstraints c,int x,int y, double weightx,double weighty){
		c.gridx =x;
		c.gridy =y;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = weighty;   //request any extra vertical space
		c.weightx = weightx;
		return c;
	}
	private GridBagConstraints setSettings(GridBagConstraints c,int x,int y, double weightx,double weighty,int ipadx,int ipady,int gridwidth){
		c.gridx =x;
		c.gridy =y;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = ipady;       //reset to default
		c.ipadx = ipadx;
		c.weighty = weighty;   //request any extra vertical space
		c.weightx = weightx;
		c.gridwidth = gridwidth;   //2 columns wide
		return c;
	}

}