
package ca.ulaval.glo2004.gui;

import ca.ulaval.glo2004.domaine.Chalet;
import ca.ulaval.glo2004.domaine.ChaletController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFileChooser;
import java.io.IOException;
import java.util.Objects;
import javax.swing.filechooser.*;


import ca.ulaval.glo2004.domaine.DTO.AccessoireDTO;
import ca.ulaval.glo2004.domaine.DTO.ChaletDTO;
import ca.ulaval.glo2004.domaine.SensToit;
import ca.ulaval.glo2004.domaine.memento.Memento;
import ca.ulaval.glo2004.domaine.DTO.MurDTO;
import ca.ulaval.glo2004.domaine.DTO.ToitDTO;


public class MainWindow extends JFrame {
    private JPanel front;
    private JPanel modificationAccessoire;
    private JPanel back;
    private JPanel left;
    private JPanel right;
    private JPanel top;
    private JPanel panel1;
    private int initialGridSize = 20;

    private JLabel positionX;
    private JLabel positionY;
    private JLabel afficheProprietes;
    private JLabel hauteurAccessoire;
    private JLabel longueurAccessoire;
    private JLabel distanceMinEntreAccessoire;
    private JLabel epaisseurPanneaux;

    private JLabel distanceDeRetrait;

    private JTextField distanceDeRetraitText;
    private JTextField textPositionX;
    private JTextField textPositionY;
    private JTextField textHauteurAccessoire;
    private JTextField textLongueurAccessoire;
    private JButton ajouterAccessoire;
    private int nomAccessoire = -1;
    private JButton button1;

    private ChaletController controller;
    private JButton Button_right;
    private String fenetreOuPorte = "fenetre";
    private String sensDuToit = "DROITE-GAUCHE";
    private JButton frontButton;
    private JButton backButton;
    private JButton leftButton;
    private JButton rightButton;
    private JButton topButton;
    private JButton buttonSTL;
    private String nomMur = "front";
    boolean dragging = false;
    private float newAngle;
    private String accessoireLongueur;
    private String accessoireHauteur;
    private String accessoireEpaisseur;
    private String accessoirePositionX;
    private String accessoirePositionY;
    private String accessoirePositionZ;

    private boolean isGridEnabled = false;

    private boolean montrerTexte;

    private JTextField epaisseurPanneauxText;
    private JTextField distanceMinaleText;
    private JTextField hauteurMurText;

    private JButton buttonSTLFini;

    public MainWindow() {
        this.controller = new ChaletController();

        //this.chalet = this.controller.getChalet();
        Button_right = new JButton("Front");
        Button_right.setSize(new Dimension(40, 40));


        frontButton = new JButton("Front");
        backButton = new JButton("Back");
        leftButton = new JButton("Left");
        rightButton = new JButton("Right");
        topButton = new JButton("Top");

        frontButton.setPreferredSize(new Dimension(120, 60));
        backButton.setPreferredSize(new Dimension(120, 60));
        rightButton.setPreferredSize(new Dimension(120, 60));
        leftButton.setPreferredSize(new Dimension(120, 60));
        topButton.setPreferredSize(new Dimension(120, 60));

        setTitle("ChalCLT");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
        int longeur = (int) (tailleMoniteur.width * 0.95);
        int hauteur = (int) (tailleMoniteur.height * 0.95);
        setSize(longeur, hauteur);
        //Override des methodes paintComponents pour les 5 vues

        top = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                (controller.creerDrawerAfficherDessus(controller)).drawChalet(g);
                if (isGridEnabled) {
                    drawGrid(g, this);
                }
            }
        };
        front = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                (controller.creerDrawerAfficheurMur(controller)).drawFront(g);
                if (isGridEnabled) {
                    drawGrid(g, this);
                }
            }
        };


        left = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                (controller.creerDrawerAfficheurMur(controller)).drawLeft(g);
                if (isGridEnabled) {
                    drawGrid(g, this);
                }
            }
        };
        right = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                (controller.creerDrawerAfficheurMur(controller)).drawRight(g);
                if (isGridEnabled) {
                    drawGrid(g, this);
                }
            }
        };

        back = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                (controller.creerDrawerAfficheurMur(controller)).drawBack(g);
                if (isGridEnabled) {
                    drawGrid(g, this);
                }
            }
        };
        modificationAccessoire = new JPanel();

        frontButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                front.setVisible(true);
                back.setVisible(false);
                left.setVisible(false);
                right.setVisible(false);
                top.setVisible(false);
                nomMur = "front";
                modificationAccessoire.setVisible(false);

            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                front.setVisible(false);
                back.setVisible(true);
                left.setVisible(false);
                right.setVisible(false);
                top.setVisible(false);
                nomMur = "back";
                modificationAccessoire.setVisible(false);
            }
        });

        leftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                front.setVisible(false);
                back.setVisible(false);
                left.setVisible(true);
                right.setVisible(false);
                top.setVisible(false);
                nomMur = "left";
                modificationAccessoire.setVisible(false);
            }
        });

        rightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                front.setVisible(false);
                back.setVisible(false);
                left.setVisible(false);
                right.setVisible(true);
                top.setVisible(false);
                nomMur = "right";
                modificationAccessoire.setVisible(false);
            }
        });

        topButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                front.setVisible(false);
                back.setVisible(false);
                left.setVisible(false);
                right.setVisible(false);
                top.setVisible(true);
                nomMur = "top";
                modificationAccessoire.setVisible(false);
            }
        });

        // Creation du conteneur de tous les panneaux
        JPanel myPanels = new JPanel(new BorderLayout());

        // Creation du panneau contenant tous les boutons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(frontButton);
        buttonPanel.add(backButton);
        buttonPanel.add(leftButton);
        buttonPanel.add(rightButton);
        buttonPanel.add(topButton);


        myPanels.add(buttonPanel, BorderLayout.SOUTH);

        // Creation du panneau de menu
        JPanel accessoire = new JPanel();
        accessoire.setBackground(Color.GRAY);
        accessoire.setLayout(new GridBagLayout());
        GridBagConstraints consraint1 = new GridBagConstraints();
        consraint1.insets = new Insets(40, 10, 10, 10);
        String[] options = {
                "Position en x",
                "Position en y",
                "Largeur de l'accessoire:",
                "Hauteur de l'accessoire",
        };
        JLabel labelTypeAccessoire = new JLabel("Type d'accessoire");
        accessoire.add(labelTypeAccessoire, consraint1);
        consraint1.gridx = 1;
        JComboBox<String> typeAccessoire = new JComboBox<>();
        DefaultComboBoxModel<String> typeAccessoreDefault = new DefaultComboBoxModel<>();
        typeAccessoreDefault.addElement("fenetre");
        typeAccessoreDefault.addElement("Porte");
        typeAccessoire.setModel(typeAccessoreDefault);
        accessoire.add(typeAccessoire, consraint1);
        consraint1.gridx = 0;
        typeAccessoire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fenetreOuPorte = (String) typeAccessoire.getSelectedItem();
                if (fenetreOuPorte == "Porte") {
                    textPositionY.setText("");
                    textPositionY.setEditable(false);
                    textHauteurAccessoire.setText("88\"");
                    textLongueurAccessoire.setText("38\"");
                } else {
                    textPositionY.setText("2'");
                    textPositionY.setEditable(true);
                    textHauteurAccessoire.setText("10\"");
                    textLongueurAccessoire.setText("10\"");
                }
            }
        });

        positionX = new JLabel("Position x");
        textPositionX = new JTextField(10);
        accessoire.add(positionX, consraint1);
        consraint1.gridx = 1;
        accessoire.add(textPositionX, consraint1);
        consraint1.gridx = 0;
        textPositionX.setText("3'");

        positionY = new JLabel("Position y");
        textPositionY = new JTextField(10);
        accessoire.add(positionY, consraint1);
        consraint1.gridx = 1;
        accessoire.add(textPositionY, consraint1);
        consraint1.gridx = 0;
        textPositionY.setText("3'");

        hauteurAccessoire = new JLabel("Hauteur de l'accessoire");
        textHauteurAccessoire = new JTextField(10);
        accessoire.add(hauteurAccessoire, consraint1);
        consraint1.gridx = 1;
        accessoire.add(textHauteurAccessoire, consraint1);
        consraint1.gridx = 0;
        textHauteurAccessoire.setText("10\"");

        longueurAccessoire = new JLabel("Largeur de l'accessoire");
        textLongueurAccessoire = new JTextField(10);
        accessoire.add(longueurAccessoire, consraint1);
        consraint1.gridx = 1;
        accessoire.add(textLongueurAccessoire, consraint1);
        consraint1.gridx = 0;
        textLongueurAccessoire.setText("10\"");

        ajouterAccessoire = new JButton("Ajouter accessoire");
        accessoire.add(ajouterAccessoire, consraint1);
        consraint1.gridx = 0;
        ajouterAccessoire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.saveState();
                if (!(textPositionX.getText().isEmpty() && textHauteurAccessoire.getText().isEmpty() && textLongueurAccessoire.getText().isEmpty())) {
                    accessoirePositionX = textPositionX.getText();
                    if (!(textPositionY.getText().isEmpty())) {
                        accessoirePositionY = textPositionY.getText();
                    }
                    accessoireHauteur = textHauteurAccessoire.getText();
                    accessoireLongueur = textLongueurAccessoire.getText();
                    controller.ajouterAccessoire(nomMur, fenetreOuPorte, accessoireHauteur, accessoireLongueur,
                            accessoirePositionX, accessoirePositionY, accessoirePositionZ);

                    reDraw();
                }
            }
        });

        modificationAccessoire.setLayout(new GridBagLayout());
        modificationAccessoire.setBackground(Color.lightGray);
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.insets = new Insets(40, 10, 10, 10);

        String[] options1 = {
                "Nouvelle largeur",
                "Nouvelle hauteur",
                "Nouvelle position x",
                "Nouvelle position y",
                "Supprimer accessoire",
        };

        for (int i = 0; i <= 3; i++) {
            JLabel label = new JLabel(options1[i]);
            JTextField textField = new JTextField(10);
            modificationAccessoire.add(label, constraint);
            constraint.gridx = 1;
            modificationAccessoire.add(textField, constraint);
            constraint.gridx = 0;
            if (i == 0) {
                textField.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String newSize = textField.getText();
                        controller.saveState();
                        controller.modifierLongueurAccessoire(newSize, nomAccessoire);
                        reDraw();
                    }

                });
            } else if (i == 1) {
                textField.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        boolean resultatAction;
                        String newSize = textField.getText();
                        controller.saveState();
                        controller.modifierHauteurAccessoire(newSize, nomAccessoire);
                        reDraw();
                    }
                });

            } else if (i == 2) {
                textField.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        controller.saveState();
                        controller.modifierPositionXAccessoire(textField.getText(), nomAccessoire);
                        reDraw();
                    }
                });
            } else if (i == 3) {
                textField.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        controller.saveState();
                        controller.modifierPositionYAccessoire(textField.getText(), nomAccessoire);
                        reDraw();
                    }
                });
            }
        }
        JButton supprimer = new JButton("Supprimer");
        modificationAccessoire.add(supprimer, constraint);
        constraint.gridx = 0;
        {
            supprimer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    controller.saveState();
                    controller.supprimerAccessoire(nomAccessoire);
                    modificationAccessoire.setVisible(false);
                    reDraw();
                }
            });
        }

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridBagLayout());
        menuPanel.setBackground(Color.gray);
        GridBagConstraints constraint2 = new GridBagConstraints();
        constraint2.insets = new Insets(40, 10, 10, 10);
        String[] options2 = {
                "Longueur des murs(x) \n Façade Arrière:",
                "Longueur des murs(x) \n Droite et Gauche:",
                "Hauteur du chalet",
                "Angle du toit:",
        };

        constraint2.gridy = 1;
        for (int i = 0; i <= 3; i++) {
            JLabel label = new JLabel(options2[i]);
            JTextField textField = new JTextField(10);
            constraint2.gridx = 0;
            constraint2.gridy++;
            menuPanel.add(label, constraint2);
            constraint2.gridx = 1;
            menuPanel.add(textField, constraint2);
            constraint2.gridx = 0;
            if (i == 0) {
                textField.setText("15'");

                textField.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        controller.saveState();
                        String nouvelSize = textField.getText();
                        controller.modifierLongueurDesMursParalleles(nouvelSize, false);
                        reDraw();
                    }

                });
            } else if (i == 1) {
                //System.out.println(" i 0 : " +1);

                textField.setText("15'");
                textField.addActionListener(new ActionListener() {
                    private Memento memento;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        controller.saveState();
                        String newSize = textField.getText();
                        controller.modifierLongueurDesMursParalleles(newSize, true);
                        reDraw();
                    }
                });

            } else if (i == 2) {
                textField.setText("12'");
                textField.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        controller.saveState();
                        String newHauteur = textField.getText();
                        controller.modifierHauteur(newHauteur);
                        reDraw();
                    }
                });
            } else if (i == 3) {
                textField.setText("15");
                textField.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        controller.saveState();
                        float newAngle = Float.parseFloat(textField.getText());
                        controller.modifierAngle(newAngle);
                        reDraw();
                    }
                });
            }
        }

        JLabel sensToitText = new JLabel("Sens du toit");
        consraint1.gridx = 0;
        menuPanel.add(sensToitText, consraint1);
        consraint1.gridx = 1;
        JComboBox<String> sensToit = new JComboBox<>();
        DefaultComboBoxModel<String> sensToitDefault = new DefaultComboBoxModel<>();
        sensToitDefault.addElement("ARRIERE-FACADE");
        sensToitDefault.addElement(("FACADE-ARRIERE"));
        sensToitDefault.addElement("GAUCHE-DROITE");
        sensToitDefault.addElement("DROITE-GAUCHE");

        sensToit.setModel(sensToitDefault);
        menuPanel.add(sensToit, consraint1);
        sensToit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.saveState();
                sensDuToit = (String) sensToit.getSelectedItem();
                controller.modifierSensToit(sensDuToit);
                reDraw();
            }
        });

        distanceMinEntreAccessoire = new JLabel("Distance minimale entre accessoires");
        distanceMinaleText = new JTextField(10);
        distanceMinaleText.setText("3\"");

        consraint1.gridx = 0;
        menuPanel.add(distanceMinEntreAccessoire, consraint1);
        consraint1.gridx = 1;
        menuPanel.add(distanceMinaleText, consraint1);

        distanceMinaleText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.saveState();
                String valeurDistMin = distanceMinaleText.getText();
                controller.modifierDistMinimaleAccessoire(valeurDistMin);
                reDraw();
            }
        });

        epaisseurPanneaux = new JLabel("Épaisseur des panneaux");
        epaisseurPanneauxText = new JTextField(10);
        epaisseurPanneauxText.setText("3\"");

        consraint1.gridx = 0;
        menuPanel.add(epaisseurPanneaux, consraint1);
        consraint1.gridx = 1;
        menuPanel.add(epaisseurPanneauxText, consraint1);


        epaisseurPanneauxText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.saveState();
                String epaisseur = epaisseurPanneauxText.getText();
                controller.modifierEpaisseur(epaisseur);
                reDraw();
            }
        });

        distanceDeRetrait = new JLabel("Distance supplémentaire de retrait");
        distanceDeRetraitText = new JTextField(10);
        distanceDeRetraitText.setText("1/4");

        consraint1.gridx = 0;
        menuPanel.add(distanceDeRetrait, consraint1);
        consraint1.gridx = 1;
        menuPanel.add(distanceDeRetraitText, consraint1);

        distanceDeRetraitText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.saveState();
                String distanceRetrait = distanceDeRetraitText.getText();
                controller.modifierDistanceRetrait(distanceRetrait);
                reDraw();
            }
        });

        consraint1.gridx = 0;
        buttonSTL = new JButton("Exporter Brut");
        menuPanel.add(buttonSTL, consraint1);
        buttonSTL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.exporterMurBrut(nomMur);
            }
        });

        consraint1.gridx = 1;
        buttonSTLFini = new JButton("Exporter Fini");
        menuPanel.add(buttonSTLFini, consraint1);
        buttonSTLFini.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.exporterMurFini(nomMur);
            }
        });


        consraint1.gridx = 0;
        buttonSTL = new JButton("Exporter toit ");
        menuPanel.add(buttonSTL, consraint1);
        buttonSTL.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.exporterMursBrut();
            }
        });

        consraint1.gridx = 1;
        buttonSTLFini = new JButton("Exporter Retrait");
        menuPanel.add(buttonSTLFini, consraint1);
        buttonSTLFini.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.exporterMurRetrait(nomMur);
            }
        });

        JPanel menuWest = new JPanel();
        menuWest.setLayout(new BoxLayout(menuWest, BoxLayout.Y_AXIS));
        menuWest.add(modificationAccessoire);
        menuWest.add(accessoire);
        myPanels.add(menuWest, BorderLayout.EAST);
        myPanels.add(menuPanel, BorderLayout.WEST);
        modificationAccessoire.setVisible(false);

        // Creation du panneau avec tous les panneaux des vues.

        JPanel panelContainer = new JPanel(new CardLayout()) {

            public void paintComponent(Graphics g) {
                super.paintComponent(g);
            }
        };

        panelContainer.add(front, "front");
        panelContainer.add(back, "back");
        panelContainer.add(left, "left");
        panelContainer.add(right, "right");
        panelContainer.add(top, "top");


        JLabel afficheProprietes = new JLabel("");
//
        afficheProprietes.setText("");

        GridBagConstraints constraint3 = new GridBagConstraints();
        constraint3.insets = new Insets(40, 10, 10, 10);
        constraint3.gridx = 0;
        constraint3.gridy = 0;

        JPanel dimensionsBruts = new JPanel(new BorderLayout());
        dimensionsBruts.add(afficheProprietes, BorderLayout.PAGE_START);  // CENTER
        constraint3.anchor = GridBagConstraints.PAGE_START;   // NORTH
        menuPanel.add(afficheProprietes, constraint3);

        panelContainer.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (controller.getPositionClicked(nomMur, e.getPoint()) != null) {
                    nomAccessoire = controller.getPositionClicked(nomMur, e.getPoint()).getIdAccessoire();
                    modificationAccessoire.setVisible(true);
                    reDraw();
                } else {
                    modificationAccessoire.setVisible(false);
                    reDraw();
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {
                //reDrawMouseLocation(e);
                for (AccessoireDTO accessoire : controller.getAccessoires()) {

                    if (e.getX() >= controller.screenToWorldX(accessoire.getPositionXFloat()) && e.getX() <= controller.screenToWorldX(accessoire.getPositionXFloat()) + controller.convertirImperialEnPixelDTO(accessoire.getLongueur()) &&
                            e.getY() >= controller.screenToWorldY(accessoire.getPositionYFloat()) && e.getY() <= controller.screenToWorldY(accessoire.getPositionYFloat()) + controller.convertirImperialEnPixelDTO(accessoire.getHauteur())) {
                        // Si la souris pointe sur un accessoire alors le selectionner pour faire un drag
                        nomAccessoire = accessoire.getIdAccessoire();
                        break; // sortie du loop apres avoir trouver l'accessoire sur lequel la souris pointe
                    }
                }

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int nomTemp = nomAccessoire;
                nomAccessoire = -1;
                if (nomAccessoire == -1) {
                    nomAccessoire = nomTemp;
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }


            public void mouseDragged(MouseEvent e) {
            }

            public void mouseMoved(MouseEvent e) {
            }
        });

        panelContainer.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (nomAccessoire != -1) {
                    dragging = false;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                dragging = false;
            }
        });
        panelContainer.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (nomAccessoire != -1) {
                    if (!dragging) {
                        dragging = true;
                        controller.saveState();
                    }
                    for (AccessoireDTO accessoire : controller.getAccessoires()) {
                        if (accessoire.getIdAccessoire() == nomAccessoire) {
                            controller.modifierPositionXIntAccessoire(e.getX(), nomAccessoire);
                            controller.modifierPositionYIntAccessoire(e.getY(), nomAccessoire);
                            break;
                        }
                    }
                    reDraw();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                afficheProprietes.setText("");
                // utiliser coordonnees de la souris pour decider si on affiche le mur, un panneau de toit ou rien.
                for (MurDTO mur : controller.getMurs()) {
                    if (Objects.equals(mur.getNom(), nomMur)) {
                        if (e.getX() >= ChaletController.positionInitialeX && e.getX() <= ChaletController.positionInitialeX + controller.convertirImperialEnPixelDTO(mur.getLongueur()) * ChaletController.multiplicateur &&
                                e.getY() >= ChaletController.positionInitialeY && e.getY() <= ChaletController.positionInitialeY + controller.convertirImperialEnPixelDTO(mur.getHauteur()) * ChaletController.multiplicateur) {
                            afficheProprietes.setText(mur.getProprietesDTO());
                        }
                    }
                    if (front.isVisible()) {
                        if (controller.getSens() == SensToit.ARRIERE_FACADE) {
                            for (ToitDTO toit : controller.getToit()) {
                                if (Objects.equals(toit.getNom(), "rallongeVertical")) {
                                    if (e.getX() >= ChaletController.positionInitialeX && e.getX() <= ChaletController.positionInitialeX + (controller.convertirImperialEnPixelDTO(mur.getLongueur()) * ChaletController.multiplicateur) &&
                                            e.getY() <= ChaletController.positionInitialeY && e.getY() >= ChaletController.positionInitialeY - (controller.convertirImperialEnPixelDTO(toit.getHauteur()) * ChaletController.multiplicateur)) {
                                        afficheProprietes.setText(toit.getProprietesDTO());
                                    }
                                }

                                if (Objects.equals(toit.getNom(), "panneauSuperieur")) {
                                    if (e.getX() >= ChaletController.positionInitialeX && e.getX() <= ChaletController.positionInitialeX + (controller.convertirImperialEnPixelDTO(mur.getLongueur()) * ChaletController.multiplicateur) &&
                                            e.getY() <= ChaletController.positionInitialeY - (toit.getHauteur().convertirEnDoubleDTO() * ChaletController.multiplicateur) && e.getY() >= ChaletController.positionInitialeY - (toit.getHauteur().convertirEnDoubleDTO() * ChaletController.multiplicateur) - (controller.convertirImperialEnPixelDTO(toit.getEpaisseurMur()) * ChaletController.multiplicateur)) {
                                        afficheProprietes.setText(toit.getProprietesDTO());
                                    }
                                }
                            }
                        }
                        if (controller.getSens() == SensToit.GAUCHE_DROITE) {
                            for (MurDTO murFront : controller.getMurs()) {
                                if (Objects.equals(mur.getNom(), "right")) {
                                    if (e.getX() >= ChaletController.positionInitialeX + murFront.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur && e.getX() <= ChaletController.positionInitialeX + (controller.convertirImperialEnPixelDTO(mur.getLongueur()) * ChaletController.multiplicateur) + (controller.convertirImperialEnPixelDTO(mur.getEpaisseurMur()) * ChaletController.multiplicateur) &&
                                            e.getY() >= ChaletController.positionInitialeY && e.getY() <= ChaletController.positionInitialeY + controller.convertirImperialEnPixelDTO(mur.getHauteur())) {
                                        afficheProprietes.setText(mur.getProprietesDTO());
                                    }
                                }
                                if (Objects.equals(mur.getNom(), "left")) {
                                    if (e.getX() >= ChaletController.positionInitialeX - (controller.convertirImperialEnPixelDTO(mur.getEpaisseurMur()) * ChaletController.multiplicateur) && e.getX() <= ChaletController.positionInitialeX &&
                                            e.getY() >= ChaletController.positionInitialeY && e.getY() <= ChaletController.positionInitialeY + controller.convertirImperialEnPixelDTO(mur.getHauteur())) {
                                        afficheProprietes.setText(mur.getProprietesDTO());
                                    }
                                }
                            }
                            for (ToitDTO toit : controller.getToit()) {
                                if (Objects.equals(toit.getNom(), "rallongeVertical")) {
                                    if (e.getX() >= ChaletController.positionInitialeX + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur && e.getX() <= ChaletController.positionInitialeX + (controller.convertirImperialEnPixelDTO(mur.getLongueur()) * ChaletController.multiplicateur) + (controller.convertirImperialEnPixelDTO(mur.getEpaisseurMur()) * ChaletController.multiplicateur) &&
                                            e.getY() <= ChaletController.positionInitialeY && e.getY() >= ChaletController.positionInitialeY - (controller.convertirImperialEnPixelDTO(toit.getHauteur()) * ChaletController.multiplicateur)) {
                                        afficheProprietes.setText(toit.getProprietesDTO());
                                    }
                                }
                                if (Objects.equals(toit.getNom(), "panneauSuperieur")) {
                                    if (e.getX() >= ChaletController.positionInitialeX - toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur
                                            && e.getX() <= ChaletController.positionInitialeX + (controller.convertirImperialEnPixelDTO(mur.getLongueur()) * ChaletController.multiplicateur) + toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur
                                            && e.getY() - ChaletController.positionInitialeY <= (((ChaletController.positionInitialeY - toit.getHauteur().convertirEnDoubleDTO() * ChaletController.multiplicateur) - ChaletController.positionInitialeY)
                                            / (ChaletController.positionInitialeX + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur - ChaletController.positionInitialeX))
                                            * (e.getX() - ChaletController.positionInitialeX)

                                            && e.getY() - ChaletController.positionInitialeY + toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur >=

                                            (((ChaletController.positionInitialeY - toit.getHauteur().convertirEnDoubleDTO() * ChaletController.multiplicateur - 3 * toit.getEpaisseurMur().convertirEnDoubleDTO() / 2 * ChaletController.multiplicateur - ChaletController.positionInitialeY - toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur)
                                                    / (ChaletController.positionInitialeX + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur + toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur - ChaletController.positionInitialeX - toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur)))
                                                    * (e.getX() - ChaletController.positionInitialeX - toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur)) {
                                        afficheProprietes.setText(toit.getProprietesDTO());
                                    }
                                }//
                                if (Objects.equals(toit.getNom(), "pignonGauche")) {
                                    if (e.getX() >= ChaletController.positionInitialeX
                                            && e.getX() <= ChaletController.positionInitialeX + (controller.convertirImperialEnPixelDTO(mur.getLongueur()) * ChaletController.multiplicateur)
                                            && e.getY() - ChaletController.positionInitialeY >= (((ChaletController.positionInitialeY - toit.getHauteur().convertirEnDoubleDTO() * ChaletController.multiplicateur) - ChaletController.positionInitialeY) / (ChaletController.positionInitialeX + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur - ChaletController.positionInitialeX)) * (e.getX() - ChaletController.positionInitialeX)
                                            && e.getY() <= ChaletController.positionInitialeY) {
                                        afficheProprietes.setText(toit.getProprietesDTO());
                                    }
                                }
                            }
                        }
                        if (controller.getSens() == SensToit.DROITE_GAUCHE) {
                            for (MurDTO murFront : controller.getMurs()) {
                                if (Objects.equals(mur.getNom(), "right")) {
                                    if (e.getX() >= ChaletController.positionInitialeX + murFront.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur &&
                                            e.getX() <= ChaletController.positionInitialeX + (controller.convertirImperialEnPixelDTO(mur.getLongueur()) * ChaletController.multiplicateur) + (controller.convertirImperialEnPixelDTO(mur.getEpaisseurMur()) * ChaletController.multiplicateur) &&
                                            e.getY() >= ChaletController.positionInitialeY && e.getY() <= ChaletController.positionInitialeY + controller.convertirImperialEnPixelDTO(mur.getHauteur())) {
                                        afficheProprietes.setText(mur.getProprietesDTO());
                                    }
                                }
                                if (Objects.equals(mur.getNom(), "left")) {
                                    if (e.getX() >= ChaletController.positionInitialeX - (controller.convertirImperialEnPixelDTO(mur.getEpaisseurMur()) * ChaletController.multiplicateur) && e.getX() <= ChaletController.positionInitialeX &&
                                            e.getY() >= ChaletController.positionInitialeY && e.getY() <= ChaletController.positionInitialeY + controller.convertirImperialEnPixelDTO(mur.getHauteur())) {
                                        afficheProprietes.setText(mur.getProprietesDTO());
                                    }
                                }
                            }
                            for (ToitDTO toit : controller.getToit()) {
                                double y1 = ChaletController.positionInitialeY - toit.getHauteur().convertirEnDoubleDTO() * ChaletController.multiplicateur;
                                double y2 = ChaletController.positionInitialeY;
                                double x1 = ChaletController.positionInitialeX;
                                double x2 = ChaletController.positionInitialeX + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur ;

                                double y12 = ChaletController.positionInitialeY - toit.getHauteur().convertirEnDoubleDTO() * ChaletController.multiplicateur - 3 * toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur / 2;
                                double y22 = ChaletController.positionInitialeY;
                                double x12 = ChaletController.positionInitialeX - toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur;
                                double x22 = ChaletController.positionInitialeX + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur + toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur;
                                if (Objects.equals(toit.getNom(), "rallongeVertical")) {
                                    if (e.getX() >= ChaletController.positionInitialeX - mur.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur && e.getX() <= ChaletController.positionInitialeX &&
                                            e.getY() <= ChaletController.positionInitialeY && e.getY() >= ChaletController.positionInitialeY - (controller.convertirImperialEnPixelDTO(toit.getHauteur()) * ChaletController.multiplicateur)) {
                                        afficheProprietes.setText(toit.getProprietesDTO());
                                    }
                                }
                                if (Objects.equals(toit.getNom(), "panneauSuperieur")) {
                                    if (e.getX() >= ChaletController.positionInitialeX - toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur
                                            && e.getX() <= ChaletController.positionInitialeX + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur + toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur
                                            && (e.getY() - y12) >= ((y22 - y12) / (x22 - x12)) * (e.getX() - x12)
                                            && (e.getY() - y1) <= ((y2 - y1) / (x2 - x1)) * (e.getX() - x1)
                                    ) {
                                        afficheProprietes.setText(toit.getProprietesDTO());
                                    }
                                }
                                if (Objects.equals(toit.getNom(), "pignonDroite")) {
                                    if (e.getX() >= ChaletController.positionInitialeX
                                            && e.getX() <= ChaletController.positionInitialeX + (controller.convertirImperialEnPixelDTO(mur.getLongueur()) * ChaletController.multiplicateur)
                                            && e.getY() < ChaletController.positionInitialeY
                                            && (e.getY() - y1) >= ((y2 - y1) / (x2 - x1)) * (e.getX() - x1)

                                    ) {
                                        afficheProprietes.setText(toit.getProprietesDTO());
                                    }
                                }
//                            }
                            }
                        }
                    }
                    if (back.isVisible()) {
                        if (controller.getSens() == SensToit.FACADE_ARRIERE) {
                            for (ToitDTO toit : controller.getToit()) {
                                if (Objects.equals(toit.getNom(), "rallongeVertical")) {
                                    if (e.getX() >= ChaletController.positionInitialeX && e.getX() <= ChaletController.positionInitialeX + (controller.convertirImperialEnPixelDTO(mur.getLongueur()) * ChaletController.multiplicateur) &&
                                            e.getY() <= ChaletController.positionInitialeY && e.getY() >= ChaletController.positionInitialeY - (controller.convertirImperialEnPixelDTO(toit.getHauteur()) * ChaletController.multiplicateur)) {
                                        afficheProprietes.setText(toit.getProprietesDTO());
                                    }
                                }

                                if (Objects.equals(toit.getNom(), "panneauSuperieur")) {
                                    if (e.getX() >= ChaletController.positionInitialeX && e.getX() <= ChaletController.positionInitialeX + (controller.convertirImperialEnPixelDTO(mur.getLongueur()) * ChaletController.multiplicateur) &&
                                            e.getY() <= ChaletController.positionInitialeY - (toit.getHauteur().convertirEnDoubleDTO() * ChaletController.multiplicateur) && e.getY() >= ChaletController.positionInitialeY - (toit.getHauteur().convertirEnDoubleDTO() * ChaletController.multiplicateur) - (controller.convertirImperialEnPixelDTO(toit.getEpaisseurMur()) * ChaletController.multiplicateur)) {
                                        afficheProprietes.setText(toit.getProprietesDTO());
                                    }
                                }
                            }
                        }

                        if (controller.getSens() == SensToit.DROITE_GAUCHE) {
                            for (MurDTO murFront : controller.getMurs()) {
                                if (Objects.equals(mur.getNom(), "left")) {
                                    if (e.getX() >= ChaletController.positionInitialeX + murFront.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur && e.getX() <= ChaletController.positionInitialeX + (controller.convertirImperialEnPixelDTO(mur.getLongueur()) * ChaletController.multiplicateur) + (controller.convertirImperialEnPixelDTO(mur.getEpaisseurMur()) * ChaletController.multiplicateur) &&
                                            e.getY() >= ChaletController.positionInitialeY && e.getY() <= ChaletController.positionInitialeY + controller.convertirImperialEnPixelDTO(mur.getHauteur())) {
                                        afficheProprietes.setText(mur.getProprietesDTO());
                                    }
                                }
                                if (Objects.equals(mur.getNom(), "right")) {
                                    if (e.getX() >= ChaletController.positionInitialeX - (controller.convertirImperialEnPixelDTO(mur.getEpaisseurMur()) * ChaletController.multiplicateur) && e.getX() <= ChaletController.positionInitialeX &&
                                            e.getY() >= ChaletController.positionInitialeY && e.getY() <= ChaletController.positionInitialeY + controller.convertirImperialEnPixelDTO(mur.getHauteur())) {
                                        afficheProprietes.setText(mur.getProprietesDTO());
                                    }
                                }
                            }
                            for (ToitDTO toit : controller.getToit()) {
                                if (Objects.equals(toit.getNom(), "rallongeVertical")) {
                                    if (e.getX() >= ChaletController.positionInitialeX + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur && e.getX() <= ChaletController.positionInitialeX + (controller.convertirImperialEnPixelDTO(mur.getLongueur()) * ChaletController.multiplicateur) + (controller.convertirImperialEnPixelDTO(mur.getEpaisseurMur()) * ChaletController.multiplicateur) &&
                                            e.getY() <= ChaletController.positionInitialeY && e.getY() >= ChaletController.positionInitialeY - (controller.convertirImperialEnPixelDTO(toit.getHauteur()) * ChaletController.multiplicateur)) {
                                        afficheProprietes.setText(toit.getProprietesDTO());
                                    }
                                }
                                if (Objects.equals(toit.getNom(), "panneauSuperieur")) {
                                    if (e.getX() >= ChaletController.positionInitialeX && e.getX() <= ChaletController.positionInitialeX + (controller.convertirImperialEnPixelDTO(mur.getLongueur()) * ChaletController.multiplicateur) &&
                                            e.getY() - ChaletController.positionInitialeY <=
                                                    (((ChaletController.positionInitialeY - toit.getHauteur().convertirEnDoubleDTO() * ChaletController.multiplicateur) - ChaletController.positionInitialeY)
                                                            / (ChaletController.positionInitialeX + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur - ChaletController.positionInitialeX))
                                                            * (e.getX() - ChaletController.positionInitialeX)

                                            && e.getY() - ChaletController.positionInitialeY + toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur >=

                                            (((ChaletController.positionInitialeY - toit.getHauteur().convertirEnDoubleDTO() * ChaletController.multiplicateur - toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur - ChaletController.positionInitialeY - toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur)
                                                    / (ChaletController.positionInitialeX + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur + toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur - ChaletController.positionInitialeX - toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur)))
                                                    * (e.getX() - ChaletController.positionInitialeX - toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur)) {
                                        afficheProprietes.setText(toit.getProprietesDTO());
                                    }
                                }//
                                if (Objects.equals(toit.getNom(), "pignonGauche")) {
                                    if (e.getX() >= ChaletController.positionInitialeX && e.getX() <= ChaletController.positionInitialeX + (controller.convertirImperialEnPixelDTO(mur.getLongueur()) * ChaletController.multiplicateur) &&
                                            e.getY() - ChaletController.positionInitialeY >= (((ChaletController.positionInitialeY - toit.getHauteur().convertirEnDoubleDTO() * ChaletController.multiplicateur) - ChaletController.positionInitialeY) / (ChaletController.positionInitialeX + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur - ChaletController.positionInitialeX)) * (e.getX() - ChaletController.positionInitialeX) && e.getY() <= ChaletController.positionInitialeY) {
                                        afficheProprietes.setText(toit.getProprietesDTO());
                                    }
                                }
                            }
                        }

                        if (controller.getSens() == SensToit.GAUCHE_DROITE) {
                            for (MurDTO murFront : controller.getMurs()) {
                                if (Objects.equals(mur.getNom(), "left")) {
                                    if (e.getX() >= ChaletController.positionInitialeX + murFront.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur && e.getX() <= ChaletController.positionInitialeX + (controller.convertirImperialEnPixelDTO(mur.getLongueur()) * ChaletController.multiplicateur) + (controller.convertirImperialEnPixelDTO(mur.getEpaisseurMur()) * ChaletController.multiplicateur) &&
                                            e.getY() >= ChaletController.positionInitialeY && e.getY() <= ChaletController.positionInitialeY + controller.convertirImperialEnPixelDTO(mur.getHauteur())) {
                                        afficheProprietes.setText(mur.getProprietesDTO());
                                    }
                                }
                                if (Objects.equals(mur.getNom(), "right")) {
                                    if (e.getX() >= ChaletController.positionInitialeX - (controller.convertirImperialEnPixelDTO(mur.getEpaisseurMur()) * ChaletController.multiplicateur) && e.getX() <= ChaletController.positionInitialeX &&
                                            e.getY() >= ChaletController.positionInitialeY && e.getY() <= ChaletController.positionInitialeY + controller.convertirImperialEnPixelDTO(mur.getHauteur())) {
                                        afficheProprietes.setText(mur.getProprietesDTO());
                                    }
                                }
                            }
                            for (ToitDTO toit : controller.getToit()) {
                                double y1 = ChaletController.positionInitialeY - toit.getHauteur().convertirEnDoubleDTO() * ChaletController.multiplicateur;
                                double y2 = ChaletController.positionInitialeY;
                                double x1 = ChaletController.positionInitialeX;
                                double x2 = ChaletController.positionInitialeX + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur;

                                double y12 = ChaletController.positionInitialeY - toit.getHauteur().convertirEnDoubleDTO() * ChaletController.multiplicateur - 3 * toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur / 2;
                                double y22 = ChaletController.positionInitialeY;
                                double x12 = ChaletController.positionInitialeX - toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur;
                                double x22 = ChaletController.positionInitialeX + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur + toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur;
                                if (Objects.equals(toit.getNom(), "rallongeVertical")) {
                                    if (e.getX() >= ChaletController.positionInitialeX - mur.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur && e.getX() <= ChaletController.positionInitialeX &&
                                            e.getY() <= ChaletController.positionInitialeY && e.getY() >= ChaletController.positionInitialeY - (controller.convertirImperialEnPixelDTO(toit.getHauteur()) * ChaletController.multiplicateur)) {
                                        afficheProprietes.setText(toit.getProprietesDTO());
                                    }
                                }
                                if (Objects.equals(toit.getNom(), "panneauSuperieur")) {
                                    if (e.getX() >= ChaletController.positionInitialeX - toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur
                                            && e.getX() <= ChaletController.positionInitialeX + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur + toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur
                                            && (e.getY() - y12) >= ((y22 - y12) / (x22 - x12)) * (e.getX() - x12)
                                            && (e.getY() - y1) <= ((y2 - y1) / (x2 - x1)) * (e.getX() - x1)
                                    ) {
                                        afficheProprietes.setText(toit.getProprietesDTO());
                                    }
                                }
                                if (Objects.equals(toit.getNom(), "pignonDroite")) {
                                    if (e.getX() >= ChaletController.positionInitialeX
                                            && e.getX() <= ChaletController.positionInitialeX + (controller.convertirImperialEnPixelDTO(mur.getLongueur()) * ChaletController.multiplicateur)
                                            && e.getY() < ChaletController.positionInitialeY
                                            && (e.getY() - y1) >= ((y2 - y1) / (x2 - x1)) * (e.getX() - x1)

                                    ) {
                                        afficheProprietes.setText(toit.getProprietesDTO());
                                    }

                                }
                            }
                        }
                    }
                        if (right.isVisible()) {
                            if (controller.getSens() == SensToit.GAUCHE_DROITE) {
                                for (ToitDTO toit : controller.getToit()) {
                                    if (Objects.equals(toit.getNom(), "rallongeVertical")) {
                                        if (e.getX() >= ChaletController.positionInitialeX && e.getX() <= ChaletController.positionInitialeX + (controller.convertirImperialEnPixelDTO(mur.getLongueur()) * ChaletController.multiplicateur) &&
                                                e.getY() <= ChaletController.positionInitialeY && e.getY() >= ChaletController.positionInitialeY - (controller.convertirImperialEnPixelDTO(toit.getHauteur()) * ChaletController.multiplicateur)) {
                                            afficheProprietes.setText(toit.getProprietesDTO());
                                        }
                                    }

                                    if (Objects.equals(toit.getNom(), "panneauSuperieur")) {
                                        if (e.getX() >= ChaletController.positionInitialeX && e.getX() <= ChaletController.positionInitialeX + (controller.convertirImperialEnPixelDTO(mur.getLongueur()) * ChaletController.multiplicateur) &&
                                                e.getY() <= ChaletController.positionInitialeY - (toit.getHauteur().convertirEnDoubleDTO() * ChaletController.multiplicateur) && e.getY() >= ChaletController.positionInitialeY - (toit.getHauteur().convertirEnDoubleDTO() * ChaletController.multiplicateur) - (controller.convertirImperialEnPixelDTO(toit.getEpaisseurMur()) * ChaletController.multiplicateur)) {
                                            afficheProprietes.setText(toit.getProprietesDTO());
                                        }
                                    }
                                }
                            }
                            if (controller.getSens() == SensToit.FACADE_ARRIERE) {
                                for (MurDTO murFront : controller.getMurs()) {
                                    if (Objects.equals(mur.getNom(), "back")) {
                                        if (e.getX() >= ChaletController.positionInitialeX + murFront.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur && e.getX() <= ChaletController.positionInitialeX + (controller.convertirImperialEnPixelDTO(mur.getLongueur()) * ChaletController.multiplicateur) + (controller.convertirImperialEnPixelDTO(mur.getEpaisseurMur()) * ChaletController.multiplicateur) &&
                                                e.getY() >= ChaletController.positionInitialeY && e.getY() <= ChaletController.positionInitialeY + controller.convertirImperialEnPixelDTO(mur.getHauteur())) {
                                            afficheProprietes.setText(mur.getProprietesDTO());
                                        }
                                    }
                                    if (Objects.equals(mur.getNom(), "front")) {
                                        if (e.getX() >= ChaletController.positionInitialeX - (controller.convertirImperialEnPixelDTO(mur.getEpaisseurMur()) * ChaletController.multiplicateur) && e.getX() <= ChaletController.positionInitialeX &&
                                                e.getY() >= ChaletController.positionInitialeY && e.getY() <= ChaletController.positionInitialeY + controller.convertirImperialEnPixelDTO(mur.getHauteur())) {
                                            afficheProprietes.setText(mur.getProprietesDTO());
                                        }
                                    }
                                }
                                for (ToitDTO toit : controller.getToit()) {
                                    if (Objects.equals(toit.getNom(), "rallongeVertical")) {
                                        if (e.getX() >= ChaletController.positionInitialeX + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur && e.getX() <= ChaletController.positionInitialeX + (controller.convertirImperialEnPixelDTO(mur.getLongueur()) * ChaletController.multiplicateur) + (controller.convertirImperialEnPixelDTO(mur.getEpaisseurMur()) * ChaletController.multiplicateur) &&
                                                e.getY() <= ChaletController.positionInitialeY && e.getY() >= ChaletController.positionInitialeY - (controller.convertirImperialEnPixelDTO(toit.getHauteur()) * ChaletController.multiplicateur)) {
                                            afficheProprietes.setText(toit.getProprietesDTO());
                                        }
                                    }
                                    if (Objects.equals(toit.getNom(), "panneauSuperieur")) {
                                        if (e.getX() >= ChaletController.positionInitialeX && e.getX() <= ChaletController.positionInitialeX + (controller.convertirImperialEnPixelDTO(mur.getLongueur()) * ChaletController.multiplicateur) &&
                                                e.getY() - ChaletController.positionInitialeY <=
                                                        (((ChaletController.positionInitialeY - toit.getHauteur().convertirEnDoubleDTO() * ChaletController.multiplicateur) - ChaletController.positionInitialeY)
                                                                / (ChaletController.positionInitialeX + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur - ChaletController.positionInitialeX))
                                                                * (e.getX() - ChaletController.positionInitialeX)

                                                && e.getY() - ChaletController.positionInitialeY + toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur >=

                                                (((ChaletController.positionInitialeY - toit.getHauteur().convertirEnDoubleDTO() * ChaletController.multiplicateur - toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur - ChaletController.positionInitialeY - toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur)
                                                        / (ChaletController.positionInitialeX + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur + toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur - ChaletController.positionInitialeX - toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur)))
                                                        * (e.getX() - ChaletController.positionInitialeX - toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur)) {
                                            afficheProprietes.setText(toit.getProprietesDTO());
                                        }
                                    }//
                                    if (Objects.equals(toit.getNom(), "pignonGauche")) {
                                        if (e.getX() >= ChaletController.positionInitialeX && e.getX() <= ChaletController.positionInitialeX + (controller.convertirImperialEnPixelDTO(mur.getLongueur()) * ChaletController.multiplicateur) &&
                                                e.getY() - ChaletController.positionInitialeY >= (((ChaletController.positionInitialeY - toit.getHauteur().convertirEnDoubleDTO() * ChaletController.multiplicateur) - ChaletController.positionInitialeY) / (ChaletController.positionInitialeX + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur - ChaletController.positionInitialeX)) * (e.getX() - ChaletController.positionInitialeX) && e.getY() <= ChaletController.positionInitialeY) {
                                            afficheProprietes.setText(toit.getProprietesDTO());
                                        }
                                    }
                                }
                            }

                            if (controller.getSens() == SensToit.ARRIERE_FACADE) {
                                for (MurDTO murFront : controller.getMurs()) {
                                    if (Objects.equals(mur.getNom(), "back")) {
                                        if (e.getX() >= ChaletController.positionInitialeX + murFront.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur &&
                                                e.getX() <= ChaletController.positionInitialeX + (controller.convertirImperialEnPixelDTO(mur.getLongueur()) * ChaletController.multiplicateur) + (controller.convertirImperialEnPixelDTO(mur.getEpaisseurMur()) * ChaletController.multiplicateur) &&
                                                e.getY() >= ChaletController.positionInitialeY && e.getY() <= ChaletController.positionInitialeY + controller.convertirImperialEnPixelDTO(mur.getHauteur())) {
                                            afficheProprietes.setText(mur.getProprietesDTO());
                                        }
                                    }
                                    if (Objects.equals(mur.getNom(), "front")) {
                                        if (e.getX() >= ChaletController.positionInitialeX - (controller.convertirImperialEnPixelDTO(mur.getEpaisseurMur()) * ChaletController.multiplicateur) && e.getX() <= ChaletController.positionInitialeX &&
                                                e.getY() >= ChaletController.positionInitialeY && e.getY() <= ChaletController.positionInitialeY + controller.convertirImperialEnPixelDTO(mur.getHauteur())) {
                                            afficheProprietes.setText(mur.getProprietesDTO());
                                        }
                                    }
                                }
                                for (ToitDTO toit : controller.getToit()) {
                                    double y1 = ChaletController.positionInitialeY - toit.getHauteur().convertirEnDoubleDTO() * ChaletController.multiplicateur;
                                    double y2 = ChaletController.positionInitialeY;
                                    double x1 = ChaletController.positionInitialeX;
                                    double x2 = ChaletController.positionInitialeX + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur ;

                                    double y12 = ChaletController.positionInitialeY - toit.getHauteur().convertirEnDoubleDTO() * ChaletController.multiplicateur - 3 * toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur / 2;
                                    double y22 = ChaletController.positionInitialeY;
                                    double x12 = ChaletController.positionInitialeX - toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur;
                                    double x22 = ChaletController.positionInitialeX + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur + toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur;
                                    if (Objects.equals(toit.getNom(), "rallongeVertical")) {
                                        if (e.getX() >= ChaletController.positionInitialeX - mur.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur && e.getX() <= ChaletController.positionInitialeX &&
                                                e.getY() <= ChaletController.positionInitialeY && e.getY() >= ChaletController.positionInitialeY - (controller.convertirImperialEnPixelDTO(toit.getHauteur()) * ChaletController.multiplicateur)) {
                                            afficheProprietes.setText(toit.getProprietesDTO());
                                        }
                                    }
                                    if (Objects.equals(toit.getNom(), "panneauSuperieur")) {
                                        if (e.getX() >= ChaletController.positionInitialeX - toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur
                                                && e.getX() <= ChaletController.positionInitialeX + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur + toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur
                                                && (e.getY() - y12) >= ((y22 - y12) / (x22 - x12)) * (e.getX() - x12)
                                                && (e.getY() - y1) <= ((y2 - y1) / (x2 - x1)) * (e.getX() - x1)
                                        ) {
                                            afficheProprietes.setText(toit.getProprietesDTO());
                                        }
                                    }
                                    if (Objects.equals(toit.getNom(), "pignonDroite")) {
                                        if (e.getX() >= ChaletController.positionInitialeX
                                                && e.getX() <= ChaletController.positionInitialeX + (controller.convertirImperialEnPixelDTO(mur.getLongueur()) * ChaletController.multiplicateur)
                                                && e.getY() < ChaletController.positionInitialeY
                                                && (e.getY() - y1) >= ((y2 - y1) / (x2 - x1)) * (e.getX() - x1)

                                        ) {
                                            afficheProprietes.setText(toit.getProprietesDTO());
                                        }
                                    }
//                            }
                                }
                            }
                        }

                        if (left.isVisible()) {
                            if (controller.getSens() == SensToit.DROITE_GAUCHE) {
                                for (ToitDTO toit : controller.getToit()) {
                                    if (Objects.equals(toit.getNom(), "rallongeVertical")) {
                                        if (e.getX() >= ChaletController.positionInitialeX && e.getX() <= ChaletController.positionInitialeX + (controller.convertirImperialEnPixelDTO(mur.getLongueur()) * ChaletController.multiplicateur) &&
                                                e.getY() <= ChaletController.positionInitialeY && e.getY() >= ChaletController.positionInitialeY - (controller.convertirImperialEnPixelDTO(toit.getHauteur()) * ChaletController.multiplicateur)) {
                                            afficheProprietes.setText(toit.getProprietesDTO());
                                        }
                                    }

                                    if (Objects.equals(toit.getNom(), "panneauSuperieur")) {
                                        if (e.getX() >= ChaletController.positionInitialeX && e.getX() <= ChaletController.positionInitialeX + (controller.convertirImperialEnPixelDTO(mur.getLongueur()) * ChaletController.multiplicateur) &&
                                                e.getY() <= ChaletController.positionInitialeY - (toit.getHauteur().convertirEnDoubleDTO() * ChaletController.multiplicateur) && e.getY() >= ChaletController.positionInitialeY - (toit.getHauteur().convertirEnDoubleDTO() * ChaletController.multiplicateur) - (controller.convertirImperialEnPixelDTO(toit.getEpaisseurMur()) * ChaletController.multiplicateur)) {
                                            afficheProprietes.setText(toit.getProprietesDTO());
                                        }
                                    }
                                }
                            }

                            if (controller.getSens() == SensToit.ARRIERE_FACADE) {
                                for (MurDTO murFront : controller.getMurs()) {
                                    if (Objects.equals(mur.getNom(), "front")) {
                                        if (e.getX() >= ChaletController.positionInitialeX + murFront.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur && e.getX() <= ChaletController.positionInitialeX + (controller.convertirImperialEnPixelDTO(mur.getLongueur()) * ChaletController.multiplicateur) + (controller.convertirImperialEnPixelDTO(mur.getEpaisseurMur()) * ChaletController.multiplicateur) &&
                                                e.getY() >= ChaletController.positionInitialeY && e.getY() <= ChaletController.positionInitialeY + controller.convertirImperialEnPixelDTO(mur.getHauteur())) {
                                            afficheProprietes.setText(mur.getProprietesDTO());
                                        }
                                    }
                                    if (Objects.equals(mur.getNom(), "back")) {
                                        if (e.getX() >= ChaletController.positionInitialeX - (controller.convertirImperialEnPixelDTO(mur.getEpaisseurMur()) * ChaletController.multiplicateur) && e.getX() <= ChaletController.positionInitialeX &&
                                                e.getY() >= ChaletController.positionInitialeY && e.getY() <= ChaletController.positionInitialeY + controller.convertirImperialEnPixelDTO(mur.getHauteur())) {
                                            afficheProprietes.setText(mur.getProprietesDTO());
                                        }
                                    }
                                }
                                for (ToitDTO toit : controller.getToit()) {
                                    if (Objects.equals(toit.getNom(), "rallongeVertical")) {
                                        if (e.getX() >= ChaletController.positionInitialeX + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur && e.getX() <= ChaletController.positionInitialeX + (controller.convertirImperialEnPixelDTO(mur.getLongueur()) * ChaletController.multiplicateur) + (controller.convertirImperialEnPixelDTO(mur.getEpaisseurMur()) * ChaletController.multiplicateur) &&
                                                e.getY() <= ChaletController.positionInitialeY && e.getY() >= ChaletController.positionInitialeY - (controller.convertirImperialEnPixelDTO(toit.getHauteur()) * ChaletController.multiplicateur)) {
                                            afficheProprietes.setText(toit.getProprietesDTO());
                                        }
                                    }
                                    if (Objects.equals(toit.getNom(), "panneauSuperieur")) {
                                        if (e.getX() >= ChaletController.positionInitialeX && e.getX() <= ChaletController.positionInitialeX + (controller.convertirImperialEnPixelDTO(mur.getLongueur()) * ChaletController.multiplicateur) &&
                                                e.getY() - ChaletController.positionInitialeY <=
                                                        (((ChaletController.positionInitialeY - toit.getHauteur().convertirEnDoubleDTO() * ChaletController.multiplicateur) - ChaletController.positionInitialeY)
                                                                / (ChaletController.positionInitialeX + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur - ChaletController.positionInitialeX))
                                                                * (e.getX() - ChaletController.positionInitialeX)

                                                && e.getY() - ChaletController.positionInitialeY + toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur >=

                                                (((ChaletController.positionInitialeY - toit.getHauteur().convertirEnDoubleDTO() * ChaletController.multiplicateur - toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur - ChaletController.positionInitialeY - toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur)
                                                        / (ChaletController.positionInitialeX + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur + toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur - ChaletController.positionInitialeX - toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur)))
                                                        * (e.getX() - ChaletController.positionInitialeX - toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur)) {
                                            afficheProprietes.setText(toit.getProprietesDTO());
                                        }
                                    }//
                                    if (Objects.equals(toit.getNom(), "pignonGauche")) {
                                        if (e.getX() >= ChaletController.positionInitialeX && e.getX() <= ChaletController.positionInitialeX + (controller.convertirImperialEnPixelDTO(mur.getLongueur()) * ChaletController.multiplicateur) &&
                                                e.getY() - ChaletController.positionInitialeY >= (((ChaletController.positionInitialeY - toit.getHauteur().convertirEnDoubleDTO() * ChaletController.multiplicateur) - ChaletController.positionInitialeY) / (ChaletController.positionInitialeX + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur - ChaletController.positionInitialeX)) * (e.getX() - ChaletController.positionInitialeX) && e.getY() <= ChaletController.positionInitialeY) {
                                            afficheProprietes.setText(toit.getProprietesDTO());
                                        }
                                    }
                                }
                            }

                            if (controller.getSens() == SensToit.FACADE_ARRIERE) {
                                for (MurDTO murFront : controller.getMurs()) {
                                    if (Objects.equals(mur.getNom(), "front")) {
                                        if (e.getX() >= ChaletController.positionInitialeX + murFront.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur &&
                                                e.getX() <= ChaletController.positionInitialeX + (controller.convertirImperialEnPixelDTO(mur.getLongueur()) * ChaletController.multiplicateur) + (controller.convertirImperialEnPixelDTO(mur.getEpaisseurMur()) * ChaletController.multiplicateur) &&
                                                e.getY() >= ChaletController.positionInitialeY && e.getY() <= ChaletController.positionInitialeY + controller.convertirImperialEnPixelDTO(mur.getHauteur())) {
                                            afficheProprietes.setText(mur.getProprietesDTO());
                                        }
                                    }
                                    if (Objects.equals(mur.getNom(), "back")) {
                                        if (e.getX() >= ChaletController.positionInitialeX - (controller.convertirImperialEnPixelDTO(mur.getEpaisseurMur()) * ChaletController.multiplicateur) && e.getX() <= ChaletController.positionInitialeX &&
                                                e.getY() >= ChaletController.positionInitialeY && e.getY() <= ChaletController.positionInitialeY + controller.convertirImperialEnPixelDTO(mur.getHauteur())) {
                                            afficheProprietes.setText(mur.getProprietesDTO());
                                        }
                                    }
                                }
                                for (ToitDTO toit : controller.getToit()) {
                                    double y1 = ChaletController.positionInitialeY - toit.getHauteur().convertirEnDoubleDTO() * ChaletController.multiplicateur;
                                    double y2 = ChaletController.positionInitialeY;
                                    double x1 = ChaletController.positionInitialeX;
                                    double x2 = ChaletController.positionInitialeX + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur ;

                                    double y12 = ChaletController.positionInitialeY - toit.getHauteur().convertirEnDoubleDTO() * ChaletController.multiplicateur - 3 * toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur / 2;
                                    double y22 = ChaletController.positionInitialeY;
                                    double x12 = ChaletController.positionInitialeX - toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur;
                                    double x22 = ChaletController.positionInitialeX + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur + toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur;
                                    if (Objects.equals(toit.getNom(), "rallongeVertical")) {
                                        if (e.getX() >= ChaletController.positionInitialeX - mur.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur && e.getX() <= ChaletController.positionInitialeX &&
                                                e.getY() <= ChaletController.positionInitialeY && e.getY() >= ChaletController.positionInitialeY - (controller.convertirImperialEnPixelDTO(toit.getHauteur()) * ChaletController.multiplicateur)) {
                                            afficheProprietes.setText(toit.getProprietesDTO());
                                        }
                                    }
                                    if (Objects.equals(toit.getNom(), "panneauSuperieur")) {
                                        if (e.getX() >= ChaletController.positionInitialeX - toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur
                                                && e.getX() <= ChaletController.positionInitialeX + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur + toit.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur
                                                && (e.getY() - y12) >= ((y22 - y12) / (x22 - x12)) * (e.getX() - x12)
                                                && (e.getY() - y1) <= ((y2 - y1) / (x2 - x1)) * (e.getX() - x1)
                                        ) {
                                            afficheProprietes.setText(toit.getProprietesDTO());
                                        }
                                    }
                                    if (Objects.equals(toit.getNom(), "pignonDroite")) {
                                        if (e.getX() >= ChaletController.positionInitialeX
                                                && e.getX() <= ChaletController.positionInitialeX + (controller.convertirImperialEnPixelDTO(mur.getLongueur()) * ChaletController.multiplicateur)
                                                && e.getY() < ChaletController.positionInitialeY
                                                && (e.getY() - y1) >= ((y2 - y1) / (x2 - x1)) * (e.getX() - x1)

                                        ) {
                                            afficheProprietes.setText(toit.getProprietesDTO());
                                        }
                                    }
//                            }
                                }
                            }

                        }

                        if (top.isVisible()) {
                            for (MurDTO murTop : controller.getMurs()) {
                                if (Objects.equals(mur.getNom(), "front")) {
                                    if (e.getX() >= ChaletController.positionInitialeX + (murTop.getEpaisseurMur().convertirEnDoubleDTO() / 2) * ChaletController.multiplicateur
                                            && e.getX() <= ChaletController.positionInitialeX + (murTop.getEpaisseurMur().convertirEnDoubleDTO() / 2) * ChaletController.multiplicateur + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur
                                            && e.getY() <= ChaletController.positionInitialeY + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur
                                            && e.getY() >= ChaletController.positionInitialeY + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur - mur.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur) {
                                        afficheProprietes.setText(mur.getProprietesDTO());
                                    }
                                }
                                if (Objects.equals(mur.getNom(), "back")) {
                                    if (e.getX() >= ChaletController.positionInitialeX + (murTop.getEpaisseurMur().convertirEnDoubleDTO() / 2) * ChaletController.multiplicateur
                                            && e.getX() <= ChaletController.positionInitialeX + (murTop.getEpaisseurMur().convertirEnDoubleDTO() / 2) * ChaletController.multiplicateur + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur
                                            && e.getY() >= ChaletController.positionInitialeY - (murTop.getEpaisseurMur().convertirEnDoubleDTO() / 2) * ChaletController.multiplicateur
                                            && e.getY() <= ChaletController.positionInitialeY + mur.getEpaisseurMur().convertirEnDoubleDTO() / 2 * ChaletController.multiplicateur) {
                                        afficheProprietes.setText(mur.getProprietesDTO());
                                    }
                                }
                                if (Objects.equals(mur.getNom(), "left")) {
                                    if (e.getX() >= ChaletController.positionInitialeX
                                            && e.getX() <= ChaletController.positionInitialeX + murTop.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur
                                            && e.getY() >= ChaletController.positionInitialeY
                                            && e.getY() <= ChaletController.positionInitialeY + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur) {
                                        afficheProprietes.setText(mur.getProprietesDTO());
                                    }
                                }
                                if (Objects.equals(mur.getNom(), "right")) {
                                    if (e.getX() >= ChaletController.positionInitialeX + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur
                                            && e.getX() <= ChaletController.positionInitialeX + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur + murTop.getEpaisseurMur().convertirEnDoubleDTO() * ChaletController.multiplicateur
                                            && e.getY() >= ChaletController.positionInitialeY
                                            && e.getY() <= ChaletController.positionInitialeY + mur.getLongueur().convertirEnDoubleDTO() * ChaletController.multiplicateur) {
                                        afficheProprietes.setText(mur.getProprietesDTO());
                                    }
                                }
                            }
                        }
                        reDraw();
                    }
                }
            });
            // Zoom et dézoome
        panelContainer.addMouseWheelListener(new
            MouseWheelListener() {
                public void mouseWheelMoved (MouseWheelEvent e){
                    float fMouseX = (e.getX());
                    float fMouseY = (e.getY());
                    float fWorldXBeforeZoom = controller.screenToWorldX(fMouseX);
                    float fWorldYBeforeZoom = controller.screenToWorldY(fMouseY);
                    float fScale = controller.getMultiplicateur();
                    if (e.getWheelRotation() == -1) {  // zoom
                        controller.setMultiplicateur(fScale * 1.1f);
//                controller.setGridSize((float) (initialGridSize * 0.9));
                    } else { // Dézoom
                        controller.setMultiplicateur(fScale * 0.9f);
//                controller.setGridSize((float) (initialGridSize * 1.1));

                    }
                    float fWorldXAfterZoom = controller.screenToWorldX(fMouseX);
                    float fWorldYAfterZoom = controller.screenToWorldY(fMouseY);
                    controller.setOffset(fWorldXBeforeZoom - fWorldXAfterZoom,
                            fWorldYBeforeZoom - fWorldYAfterZoom);
                    reDraw();
                }
            });

        myPanels.add(panelContainer,BorderLayout.CENTER);

            add(myPanels);

        front.setVisible(true);
        back.setVisible(false);
        left.setVisible(false);
        right.setVisible(false);
        top.setVisible(false);

            JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(new

            ActionListener() {
                @Override
                public void actionPerformed (ActionEvent e){
                    controller.undo();
                    reDraw();
                }
            });

            JButton redoButton = new JButton("Redo");
        redoButton.addActionListener(new

            ActionListener() {
                @Override
                public void actionPerformed (ActionEvent e){
                    controller.redo();
                    reDraw();
                }
            });
            JButton openButton = new JButton("Open");
            JFileChooser fc_open = new JFileChooser();
        fc_open.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc_open.setMultiSelectionEnabled(false);


        openButton.addActionListener(new

            ActionListener() {
                @Override
                public void actionPerformed (ActionEvent e){
                    int ret = fc_open.showOpenDialog(MainWindow.this);
                    if (ret == fc_open.APPROVE_OPTION) {
                        try {
                            controller.openChalet(fc_open.getSelectedFile());
                            // Remettre à zéro le undo/redo...
                            reDraw();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            });
            JButton saveButton = new JButton("Save");
            FileDialog fd_save = new FileDialog(this, "Choisir un emplacement", FileDialog.LOAD);

        saveButton.addActionListener(new

            ActionListener() {
                @Override
                public void actionPerformed ( final ActionEvent e){
                    fd_save.setVisible(true);
                    String repertoire = fd_save.getDirectory();
                    String nomFichier = fd_save.getFile();
                    if (repertoire == null)
                        System.out.println("You cancelled the choice");
                    else
                        try {
                            controller.saveChalet(repertoire, nomFichier);
                        } catch (IOException f) {
                            f.printStackTrace();
                        }

                }

            });

            JButton nouveauChalet = new JButton("Creer un nouveau chalet");
        nouveauChalet.addActionListener(new

            ActionListener() {
                @Override
                public void actionPerformed (ActionEvent e){
                    controller.creerNouveauChalet();
                    reDraw();
                }
            });

            JButton toggleGridButton = new JButton("Toggle Grid");
        toggleGridButton.addActionListener(new

            ActionListener() {
                @Override
                public void actionPerformed (ActionEvent e){
                    toggleGrid();
                }
            });

            JButton toggleSide = new JButton("Toggle Side");
        toggleSide.addActionListener(new

            ActionListener() {
                @Override
                public void actionPerformed (ActionEvent e){
                    toggleSide();
                }
            });


            JPanel buttonMidPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonMidPanel.add(nouveauChalet);
        buttonMidPanel.add(openButton);
        buttonMidPanel.add(saveButton);
        buttonMidPanel.add(undoButton);
        buttonMidPanel.add(redoButton);
        buttonMidPanel.add(toggleGridButton);
        buttonMidPanel.add(toggleSide);
        myPanels.add(buttonMidPanel,BorderLayout.NORTH);

        }

        private void toggleGrid () {
            isGridEnabled = !isGridEnabled;
            reDraw();
        }

        private void toggleSide () {
            ChaletController.isSideEnabled = !ChaletController.isSideEnabled;
            controller.setSideEnable(ChaletController.isSideEnabled);
            reDraw();
        }

        private void drawGrid (Graphics g, JPanel panel){
            float rulerStartX = ChaletController.positionInitialeX;
            float rulerStartY = ChaletController.positionInitialeY - (float) (controller.getHauteurPanneauSuperieur() * ChaletController.multiplicateur);
            float scale = initialGridSize * ChaletController.multiplicateur;
            int width = panel.getWidth();
            int height = panel.getHeight();
            double feetToPixels = ((scale * 12) / 5);

            Graphics2D g2d = (Graphics2D) g.create();
            float alpha = 0.5f;
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2d.setColor(Color.LIGHT_GRAY);
            for (int i = 0; i < width; i += (int) feetToPixels) {
                g2d.drawLine(i, 0, i, height);
            }
            for (int j = 0; j < height; j += (int) feetToPixels) {
                g2d.drawLine(0, j, width, j);
            }
            g2d.setColor(Color.BLACK);
            //Regle
            g2d.drawLine((int) rulerStartX, height - 15, width, height - 15);
            double pixelsPerInch = feetToPixels / 12;
            for (int i = 0; (int) (rulerStartX + i * feetToPixels) < width; i++) {
                int x = (int) (rulerStartX + i * feetToPixels);
                g2d.drawLine(x, height - 30, x, height - 15);
                g2d.drawString((i) + " ft", x - 10, height - 35);
                for (int j = 1; j < 12; j++) {
                    int inchX = (int) (x + j * pixelsPerInch);
                    if (j == 6) {
                        g2d.drawLine(inchX, height - 25, inchX, height - 15);
                    } else {
                        g2d.drawLine(inchX, height - 20, inchX, height - 15);
                    }
                }
            }

            g2d.drawLine(15, (int) rulerStartY, 15, height);
            for (int i = 0; (int) (rulerStartY + i * feetToPixels) < height; i++) {
                int y = (int) (rulerStartY + i * feetToPixels);
                g2d.drawLine(0, y, 30, y);
                g2d.drawString(i + " ft", 35, y + 5);
                for (int j = 1; j < 12; j++) {
                    int inchY = (int) (y + j * pixelsPerInch);
                    if (j == 6) {
                        g2d.drawLine(15, inchY, 30, inchY);
                    } else {
                        g2d.drawLine(20, inchY, 30, inchY);
                    }
                }
            }

            g2d.dispose();
        }

        private void reDraw () {
            front.repaint();
            back.repaint();
            left.repaint();
            right.repaint();
            top.repaint();
        }


        public void setOffset ( int offset_x, int offset_y){
            this.controller.setOffset(offset_x, offset_y);
        }

        {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
            $$$setupUI$$$();
        }

        /**
         * Method generated by IntelliJ IDEA GUI Designer
         * >>> IMPORTANT!! <<<
         * DO NOT edit this method OR call it in your code!
         *
         * @noinspection ALL
         */
        private void $$$setupUI$$$ () {
            panel1 = new JPanel();
            panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            button1 = new JButton();
            button1.setText("Button");
            panel1.add(button1);
        }

        /**
         * @noinspection ALL
         */
        public JComponent $$$getRootComponent$$$ () {
            return panel1;
        }


    }



