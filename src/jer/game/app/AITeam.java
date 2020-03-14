/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jer.game.app;

import java.util.Random;

/**
 *
 * @author nbp184
 */
public class AITeam extends Team {
    
    private static final Random rand = new Random();
    
    AITeam(int teamCount, int number) {
        super(teamCount, number);
    }
    
    GameAction getGameAction(Game g) {
        GameAction ga = new GameAction();
        if(artifactCount >= 1 && artifactCount < g.teams.size()) {
            ga.attacks = rand.nextInt(members) + 1;
            ga.defends = members - ga.attacks;
        } else {
            ga.attacks = members;
            ga.defends = 0;
        }
        int me = g.teams.indexOf(this);
        int help = me;
        while(help == me || g.teams.get(help).artifactCount == 0) {
            help = rand.nextInt(g.teams.size());
        }
        ga.help = g.teams.get(help);
        return ga;
    }
    
    public class GameAction {
        public int attacks;
        public int defends;
        public Team help;
    }
    
}
