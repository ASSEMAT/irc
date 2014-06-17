package com.cfranc.irc.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.Utilities;

import com.cfranc.irc.IfClientServerProtocol;
import com.cfranc.irc.Smiley;
import com.cfranc.irc.client.IfSenderModel;
import com.cfranc.irc.server.User;

public class SimpleChatFrameClient extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Document documentModel;
	//private static ListModel<String> listModel;
	private static ListModel<User> listModel;
	
	IfSenderModel sender;
	private String senderName;	
	private JTextPane textArea;
	private JPanel contentPane;
	private JTextField textField;
	private JLabel lblSender;
	private JToolBar toolBar;
	private final ResourceAction sendAction = new SendAction();
	private final ResourceAction lockAction = new LockAction();
	private final ResourceAction AffichageToolbarAction = new AffichageToolbarAction();
	private String pseudoReception="";
	private String pseudo="";
	private JImagePanel emoticon1;
	private JImagePanel emoticon2;
	private JImagePanel emoticon3;
	private JImagePanel emoticon4;
	private JImagePanel emoticon5;
	private JImagePanel emoticon6;
	
	public JImagePanel getEmoticon1() {
		return emoticon1;
	}

	public void setEmoticon1(JImagePanel emoticon1) {
		this.emoticon1 = emoticon1;
	}

	public JImagePanel getEmoticon2() {
		return emoticon2;
	}

	public void setEmoticon2(JImagePanel emoticon2) {
		this.emoticon2 = emoticon2;
	}

	public JImagePanel getEmoticon3() {
		return emoticon3;
	}

	public void setEmoticon3(JImagePanel emoticon3) {
		this.emoticon3 = emoticon3;
	}

	public JImagePanel getEmoticon4() {
		return emoticon4;
	}

	public void setEmoticon4(JImagePanel emoticon4) {
		this.emoticon4 = emoticon4;
	}

	public JImagePanel getEmoticon5() {
		return emoticon5;
	}

	public void setEmoticon5(JImagePanel emoticon5) {
		this.emoticon5 = emoticon5;
	}

	public JImagePanel getEmoticon6() {
		return emoticon6;
	}

	public void setEmoticon6(JImagePanel emoticon6) {
		this.emoticon6 = emoticon6;
	}

	
	private boolean isScrollLocked=true;

	
	/**
	 * Launch the application.
	 * @throws BadLocationException 
	 */
	public static void main(String[] args) throws BadLocationException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SimpleChatFrameClient frame = new SimpleChatFrameClient(new SimpleChatClientApp());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});		

		Scanner sc=new Scanner(System.in);
		String line=""; //$NON-NLS-1$
		while(!line.equals(".bye")){ //$NON-NLS-1$
			line=sc.nextLine();			
		}
	}

	public static void sendMessage(String user, String line, Style styleBI,
			Style styleGP) {
        try {
			documentModel.insertString(documentModel.getLength(), user+" : ", styleBI); //$NON-NLS-1$
			documentModel.insertString(documentModel.getLength(), line+"\n", styleGP); //$NON-NLS-1$
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}				        	
	}
	
	public void sendMessage() {
		System.out.println("send message : " +  pseudoReception);
		sender.setMsgToSend(textField.getText(),pseudoReception);

	}

	public void close() {
		this.dispose();
	}

	/**
	 * @wbp.parser.constructor
	 */
	public SimpleChatFrameClient(SimpleChatClientApp listener) {
		this(null, new DefaultListModel<User>(), SimpleChatClientApp.defaultDocumentModel(),IfClientServerProtocol.MESSAGE_ALL_USERS,listener);
	}

	/**
	 * Create the frame.
	 */
	public SimpleChatFrameClient(IfSenderModel sender, ListModel<User> clientListModel, Document documentModel, String clientPseudo, SimpleChatClientApp listener) {
		this.sender=sender;
		this.pseudo = clientPseudo;
		
		this.documentModel=documentModel;
		this.listModel=clientListModel;
		setTitle(Messages.getString("SimpleChatFrameClient.4")); //$NON-NLS-1$
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 420);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu(Messages.getString("SimpleChatFrameClient.5")); //$NON-NLS-1$
		mnFile.setMnemonic('F');
		menuBar.add(mnFile);
		/**
		 * Rajout de l'option d'affichage
		 */
		JMenu mAffichage = new JMenu(Messages.getString("SimpleChatFrameClient.14")); //$NON-NLS-1$
		mAffichage.setMnemonic('A');
		menuBar.add(mAffichage);

		JMenuItem mntmAffichercacherToolbar = new JMenuItem(Messages.getString("SimpleChatFrameClient.15")); //$NON-NLS-1$
		mntmAffichercacherToolbar.setAction(AffichageToolbarAction);
		mAffichage.add(mntmAffichercacherToolbar);

		
		JMenuItem mntmEnregistrerSous = new JMenuItem(Messages.getString("SimpleChatFrameClient.6")); //$NON-NLS-1$
		mnFile.add(mntmEnregistrerSous);
				
		JMenu mnOutils = new JMenu(Messages.getString("SimpleChatFrameClient.7")); //$NON-NLS-1$
		mnOutils.setMnemonic('O');
		menuBar.add(mnOutils);
		
		JMenuItem mntmEnvoyer = new JMenuItem(Messages.getString("SimpleChatFrameClient.8")); //$NON-NLS-1$
		mntmEnvoyer.setAction(sendAction);
		mnOutils.add(mntmEnvoyer);
		
		JSeparator separator = new JSeparator();
		mnOutils.add(separator);
		JCheckBoxMenuItem chckbxmntmNewCheckItem = new JCheckBoxMenuItem(lockAction);
		mnOutils.add(chckbxmntmNewCheckItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JSplitPane splitPane = new JSplitPane();
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		JList<User> list = new JList<User>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setCellRenderer(new ListCellRenderer<User>() {
			@Override
			public Component getListCellRendererComponent(JList<? extends User> list, User value, int index,boolean isSelected, boolean cellHasFocus) {
				// TODO Auto-generated method stub
				JLabel avatar = new JLabel(value.getPseudo());
				ImageIcon imgUser = new ImageIcon(value.getPic());
				Image imgReduite = imgUser.getImage().getScaledInstance(60, 30, java.awt.Image.SCALE_SMOOTH);
				avatar.setIcon(new ImageIcon(imgReduite));
				System.out.println("value.getLogin() "  + value.getLogin());
				System.out.println("value.getPseudo() "  + value.getPseudo());
				avatar.setText(value.getPseudo());
				return avatar;
			}
		});
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				int iFirstSelectedElement=((JList)e.getSource()).getSelectedIndex();
				if(iFirstSelectedElement>=0 && iFirstSelectedElement<listModel.getSize()){
					senderName=listModel.getElementAt(iFirstSelectedElement).getPseudo();

					if (!senderName.equals(pseudo)){
						pseudoReception = senderName;
						getLblSender().setText(senderName);
						getLblSender().setText(Messages.getString("SimpleChatFrameClient.21") + " " + senderName);
					}
					else
					{
						pseudoReception = IfClientServerProtocol.MESSAGE_ALL_USERS;
						getLblSender().setText(Messages.getString("SimpleChatFrameClient.20") + " " + senderName);

					}
				}
				else{
					getLblSender().setText("?"); //$NON-NLS-1$
				}
			}
		});
		list.setMinimumSize(new Dimension(100, 0));
		splitPane.setLeftComponent(list);
		
		textArea = new JTextPane((StyledDocument)documentModel);
		textArea.setEnabled(false);

		documentModel.addDocumentListener(new DocumentListener(){
            public void insertUpdate(DocumentEvent event) {
                final DocumentEvent e=event;
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        if (e.getDocument() instanceof StyledDocument) {
                            try {
                                StyledDocument doc=(StyledDocument) e.getDocument();
                                int start= Utilities.getRowStart(textArea,Math.max(0,e.getOffset()-1));
                                int end=Utilities.getWordStart(textArea,e.getOffset()+e.getLength());
                                String text=doc.getText(start, end-start);
 
                                int i=text.indexOf(Smiley.HAPPY);
                                while(i>=0) {
                                    final SimpleAttributeSet attrs=new SimpleAttributeSet(doc.getCharacterElement(start+i).getAttributes());
                                    if (StyleConstants.getIcon(attrs)==null) {
                                    	StyleConstants.setIcon(attrs, new ImageIcon(SimpleChatFrameClient.class.getResource("happy.gif")));
                                        doc.remove(start+i, 3);
                                        doc.insertString(start+i,Smiley.HAPPY, attrs);
                                    }
                                    i=text.indexOf(Smiley.HAPPY, i+3);
                                 }
                                
                                int isad=text.indexOf(Smiley.SAD);
                                while(isad>=0) {
                                    final SimpleAttributeSet attrs=new SimpleAttributeSet(doc.getCharacterElement(start+isad).getAttributes());
                                    if (StyleConstants.getIcon(attrs)==null) {
                                    	StyleConstants.setIcon(attrs, new ImageIcon(SimpleChatFrameClient.class.getResource("sad.gif")));
                                        doc.remove(start+isad, 3);
                                        doc.insertString(start+isad,Smiley.SAD, attrs);
                                    }
                                    isad=text.indexOf(Smiley.SAD, isad+3);
                                }   
                                
                                int ilol=text.indexOf(Smiley.LOL);
                                while(ilol>=0) {
                                    final SimpleAttributeSet attrs=new SimpleAttributeSet(doc.getCharacterElement(start+ilol).getAttributes());
                                    if (StyleConstants.getIcon(attrs)==null) {
                                    	StyleConstants.setIcon(attrs, new ImageIcon(SimpleChatFrameClient.class.getResource("lol.gif")));
                                        doc.remove(start+ilol, 3);
                                        doc.insertString(start+ilol,Smiley.LOL, attrs);
                                    }
                                    ilol=text.indexOf(Smiley.LOL, ilol+3);
                                }   
                                
                                int icry=text.indexOf(Smiley.CRY);
                                while(icry>=0) {
                                    final SimpleAttributeSet attrs=new SimpleAttributeSet(doc.getCharacterElement(start+icry).getAttributes());
                                    if (StyleConstants.getIcon(attrs)==null) {
                                    	StyleConstants.setIcon(attrs, new ImageIcon(SimpleChatFrameClient.class.getResource("cry.gif")));
                                        doc.remove(start+icry, 3);
                                        doc.insertString(start+icry,Smiley.CRY, attrs);
                                    }
                                    icry=text.indexOf(Smiley.CRY, icry+3);
                                }   
                                
                                int ibadday=text.indexOf(Smiley.BADDAY);
                                while(ibadday>=0) {
                                    final SimpleAttributeSet attrs=new SimpleAttributeSet(doc.getCharacterElement(start+ibadday).getAttributes());
                                    if (StyleConstants.getIcon(attrs)==null) {
                                    	StyleConstants.setIcon(attrs, new ImageIcon(SimpleChatFrameClient.class.getResource("badday.gif")));
                                        doc.remove(start+ibadday, 3);
                                        doc.insertString(start+ibadday,Smiley.BADDAY, attrs);
                                    }
                                    ibadday=text.indexOf(Smiley.BADDAY, ibadday+3);
                                }   
                                
                                int ihello=text.indexOf(Smiley.HELLO);
                                while(ihello>=0) {
                                    final SimpleAttributeSet attrs=new SimpleAttributeSet(doc.getCharacterElement(start+ihello).getAttributes());
                                    if (StyleConstants.getIcon(attrs)==null) {
                                    	StyleConstants.setIcon(attrs, new ImageIcon(SimpleChatFrameClient.class.getResource("welcome.gif")));
                                        doc.remove(start+ihello, 3);
                                        doc.insertString(start+ihello,Smiley.HELLO, attrs);
                                    }
                                    ihello=text.indexOf(Smiley.HELLO, ihello+3);
                                }   
                                
                                
                            } catch (BadLocationException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                });
            }
            public void removeUpdate(DocumentEvent e) {
            }
            public void changedUpdate(DocumentEvent e) {
            }
        });
		
		
		JTabbedPane jOnglet = new JTabbedPane();
		JScrollPane scrollPaneText=new JScrollPane(textArea);
		jOnglet.addTab("salon général", scrollPaneText);

		
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(textArea, popupMenu);
		
		JCheckBoxMenuItem chckbxmntmLock = new JCheckBoxMenuItem(Messages.getString("SimpleChatFrameClient.10")); //$NON-NLS-1$
		chckbxmntmLock.setEnabled(isScrollLocked);
		popupMenu.add(chckbxmntmLock);
		chckbxmntmLock.addActionListener(lockAction);
		
		scrollPaneText.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				if(isScrollLocked){
					e.getAdjustable().setValue(e.getAdjustable().getMaximum());
				}				
			}
		});
		
		splitPane.setRightComponent(jOnglet);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.SOUTH);
		
		JPanel panel = new JPanel();
		panel_1.add(panel);
		
		lblSender = new JLabel("?"); //$NON-NLS-1$
		lblSender.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSender.setHorizontalTextPosition(SwingConstants.CENTER);
		lblSender.setPreferredSize(new Dimension(100, 14));
		lblSender.setMinimumSize(new Dimension(100, 14));
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		textField.getInputMap().put(KeyStroke.getKeyStroke(
                KeyEvent.VK_ENTER, 0),
                Messages.getString("SimpleChatFrameClient.12")); //$NON-NLS-1$
		textField.getActionMap().put(Messages.getString("SimpleChatFrameClient.13"), sendAction); //$NON-NLS-1$
		
		JButton btnSend = new JButton(sendAction);
		btnSend.setMnemonic(KeyEvent.VK_ENTER);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(lblSender, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSend, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(20)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
						.addComponent(lblSender, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
						.addComponent(btnSend, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
		);
		panel.setLayout(gl_panel);
		
		toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		JButton button = toolBar.add(sendAction);
		Dimension dim = new Dimension(200, 100);
		
		JPanel panelEmoticon = new JPanel();
		panelEmoticon.setSize(new Dimension(350, 100));
		panelEmoticon.setPreferredSize(new Dimension(350, 100));
		panelEmoticon.setMinimumSize(new Dimension(350, 100));
		panelEmoticon.setMaximumSize(new Dimension(350, 100));

		emoticon1 = new JImagePanel();
		emoticon1.setPreferredSize(new Dimension(60, 60));
		emoticon1.setSize(new Dimension(60, 60));
		emoticon1.setMinimumSize(new Dimension(60, 60));
		emoticon1.setMaximumSize(new Dimension(60, 60));
		
		emoticon2 = new JImagePanel();
		emoticon2.setPreferredSize(new Dimension(20, 20));
		emoticon2.setSize(new Dimension(20, 20));
		emoticon2.setMinimumSize(new Dimension(20, 20));
		emoticon2.setMaximumSize(new Dimension(20, 20));

		emoticon3 = new JImagePanel();
		emoticon3.setPreferredSize(new Dimension(20, 20));
		emoticon3.setSize(new Dimension(20, 20));
		emoticon3.setMinimumSize(new Dimension(20, 20));
		emoticon3.setMaximumSize(new Dimension(20, 20));

		emoticon4 = new JImagePanel();
		emoticon4.setPreferredSize(new Dimension(20, 20));
		emoticon4.setSize(new Dimension(20, 20));
		emoticon4.setMinimumSize(new Dimension(20, 20));
		emoticon4.setMaximumSize(new Dimension(20, 20));

		emoticon5 = new JImagePanel();
		emoticon5.setPreferredSize(new Dimension(20, 20));
		emoticon5.setSize(new Dimension(20, 20));
		emoticon5.setMinimumSize(new Dimension(20, 20));
		emoticon5.setMaximumSize(new Dimension(20, 20));


		emoticon6 = new JImagePanel();
		emoticon6.setPreferredSize(new Dimension(50, 50));
		emoticon6.setSize(new Dimension(50, 50));
		emoticon6.setMinimumSize(new Dimension(50, 50));
		emoticon6.setMaximumSize(new Dimension(50, 50));

		System.out.println(SimpleChatFrameClient.class.getResource("send_16_16.jpg").getPath());
		System.out.println(SimpleChatFrameClient.class.getResource("badday.gif").getPath());
		emoticon1.setImage(new ImageIcon(SimpleChatFrameClient.class.getResource("badday.gif")).getImage());
		emoticon2.setImage(new ImageIcon(SimpleChatFrameClient.class.getResource("cry.gif")).getImage());
		emoticon3.setImage(new ImageIcon(SimpleChatFrameClient.class.getResource("happy.gif")).getImage());
		emoticon4.setImage(new ImageIcon(SimpleChatFrameClient.class.getResource("lol.gif")).getImage());
		emoticon5.setImage(new ImageIcon(SimpleChatFrameClient.class.getResource("sad.gif")).getImage());
		emoticon6.setImage(new ImageIcon(SimpleChatFrameClient.class.getResource("welcome.gif")).getImage());
		panelEmoticon.add(emoticon1);
		emoticon1.addMouseListener(listener);
		panelEmoticon.add(emoticon2);
		emoticon2.addMouseListener(listener);
		panelEmoticon.add(emoticon3);
		emoticon3.addMouseListener(listener);			
		panelEmoticon.add(emoticon4);
		emoticon4.addMouseListener(listener);			
		panelEmoticon.add(emoticon5);
		emoticon5.addMouseListener(listener);			
		panelEmoticon.add(emoticon6);
		emoticon6.addMouseListener(listener);			
		
		repaint();
		
		panel_2.add(panelEmoticon);
		
		
		this.setAlwaysOnTop(true);
	}

	public JTextField getTextField() {
		return textField;
	}

	public void setTextField(String str) {
		this.textField.setText(str);
	}

	public JLabel getLblSender() {
		return lblSender;
	}
	


	private abstract class ResourceAction extends AbstractAction {
		public ResourceAction() {
		}
	}
	
	private class SendAction extends ResourceAction{	
		private Icon getIcon(){
			return new ImageIcon(SimpleChatFrameClient.class.getResource("send_16_16.jpg")); //$NON-NLS-1$
		}
		public SendAction(){
			putValue(NAME, Messages.getString("SimpleChatFrameClient.3")); //$NON-NLS-1$
			putValue(SHORT_DESCRIPTION, Messages.getString("SimpleChatFrameClient.2")); //$NON-NLS-1$
			putValue(SMALL_ICON, getIcon());
		}
		public void actionPerformed(ActionEvent e) {
			sendMessage();
			if (textField.getText().equals(".bye")){
				close();
			}
			else{
				textField.setText(""); 
			}
			
		}
	}

	private class AffichageToolbarAction extends ResourceAction{	
		public AffichageToolbarAction(){
			putValue(NAME, Messages.getString("SimpleChatFrameClient.15")); //$NON-NLS-1$
			putValue(SHORT_DESCRIPTION, Messages.getString("SimpleChatFrameClient.16")); //$NON-NLS-1$
		}
		public void actionPerformed(ActionEvent e) {
			if (toolBar.isShowing()){
				toolBar.setVisible(false);
			}
			else{
				toolBar.setVisible(true);
			}
		}
	}

	
	private class LockAction extends ResourceAction{	
		public LockAction(){
			putValue(NAME, Messages.getString("SimpleChatFrameClient.1")); //$NON-NLS-1$
			putValue(SHORT_DESCRIPTION, Messages.getString("SimpleChatFrameClient.0")); //$NON-NLS-1$
		}
		public void actionPerformed(ActionEvent e) {
			isScrollLocked=(!isScrollLocked);
		}
	}
	
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
