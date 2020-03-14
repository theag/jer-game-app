/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jer.game.app;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author nbp184
 */
public class Game {
    
    final ArrayList<Team> teams;
    final HashMap<Team,BaseFight> fights;
    int roundNumber;
    
    Game(int teamCount, int aiCount) {
        teams = new ArrayList<>();
        for(int i = 0; i < teamCount; i++) {
            if(i < teamCount - aiCount) {
                teams.add(new Team(teamCount, i+1));
            } else {
                teams.add(new AITeam(teamCount, i+1));
            }
        }
        fights = new HashMap<>();
        makeFights();
        roundNumber = 1;
    }
    
    void resolveFights() {
        for(BaseFight f : fights.values()) {
            Team winner = f.resolveFight();
            if(winner != f.defender && f.defender.artifactCount > 0) {
                f.defender.artifactCount--;
                winner.artifactCount++;
            }
        }
    }
    
    void roundEnd() {
        ArrayList<Team> toRemove = new ArrayList<>();
        for(Team t : teams) {
            if(t.artifactCount == 0) {
                t.points -= 1;
                if(t.points == 0) {
                    toRemove.add(t);
                }
            } else if(roundNumber%2 == 0) {
                t.points += t.artifactCount;
            }
        }
        teams.removeAll(toRemove);
        makeFights();
        roundNumber++;
    }

    String fightString(Team base) {
        BaseFight f = fights.get(base);
        String rv = "Base " +f.defender.name +" is attacked by ";
        String ts = "";
        for(Team t : f) {
            if(!ts.isEmpty()) {
                ts += " and ";
            }
            ts += t.name;
        }
        rv += ts +" with " +f.getAttackerTotal() +" people";
        return rv;
    }
    
    private void makeFights() {
        fights.clear();
        for(Team t : teams) {
            fights.put(t, new BaseFight(teams.size(), t));
        }
    }
    
}
