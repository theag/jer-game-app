/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jer.game.app;

import java.util.Scanner;

/**
 *
 * @author nbp184
 */
public class JerGameApp {
    
    static final int NUMBER_OF_ROUNDS = 2;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int teams = 0, ai = -1;
        while(teams == 0) {
            System.out.print("Enter number of teams\n>");
            try {
                teams = Integer.parseInt(s.nextLine());
                if(teams <= 1) {
                    System.out.println("Must have at least two teams.");
                    teams = 0;
                }
            } catch(NumberFormatException ex) {
                System.out.println("Must enter an integer.");
            }
        }
        while(ai < 0) {
            System.out.print("Enter number of ai teams\n>");
            try {
                ai = Integer.parseInt(s.nextLine());
                if(ai < 0) {
                    System.out.println("Can't have a negative number of ai teams.");
                } else if (ai > teams) {
                    System.out.println("Can't have more ai teams than total teams.");
                    ai = -1;
                }
            } catch(NumberFormatException ex) {
                System.out.println("Must enter an integer.");
            }
        }
        Game g = new Game(teams, ai);
        while(g.roundNumber <= NUMBER_OF_ROUNDS && g.teams.size() > 1) {
            System.out.println();
            for(int i = 0; i < g.teams.size(); i++) {
                Team t = g.teams.get(i);
                System.out.println(t.name +" action");
                if(t instanceof AITeam) {
                    AITeam.GameAction ga = ((AITeam)t).getGameAction(g);
                    g.fights.get(t).defenders = ga.defends;
                    if(ga.attacks > 0) {
                        g.fights.get(ga.help).addAttacker(t, ga.attacks);
                    }
                } else {
                    int attacks = -1;
                    while(attacks < 0) {
                        System.out.print("Enter number of attackers (out of " +t.members + " members)\n>");
                        try {
                            attacks = Integer.parseInt(s.nextLine());
                            if(attacks < 0) {
                                System.out.println("Can't have a negative number of attackers.");
                            } else if(attacks > t.members) {
                                System.out.println("Can't have more attackers than team members.");
                                attacks = -1;
                            }
                        } catch(NumberFormatException ex) {
                            System.out.println("Must enter an integer");
                        }
                    }
                    g.fights.get(t).defenders = t.members - attacks;
                    if(attacks > 0) {
                        for(Team d : g.teams) {
                            if(!d.equals(t)) {
                                System.out.println(d.name);
                            }
                        }
                        Team h = null;
                        while(h == null) {
                            System.out.print("Enter the team name to attack\n>");
                            String help = s.nextLine();
                            int hi = g.teams.indexOf(new Team(help));
                            if(hi >= 0) {
                                h = g.teams.get(hi);
                            } else {
                                System.out.println("That isn't a valid team name.");
                            }
                        }
                        g.fights.get(h).addAttacker(t, attacks);
                    }
                }
                System.out.println();
            }
            System.out.println();
            for(Team t : g.teams) {
                System.out.println(g.fightString(t));
            }
            g.resolveFights();
            g.roundEnd();
            System.out.println();
            for(int i = 0; i < g.teams.size(); i++) {
                System.out.println("Team " +(i+1) +" has " +g.teams.get(i).artifactCount +" artifacts and " +g.teams.get(i).points +" points");
            }
        }
    }
    
}
